package worldontheotherside.wordpress.com.autismapp.Activities;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

import worldontheotherside.wordpress.com.autismapp.API.User;
import worldontheotherside.wordpress.com.autismapp.Data.Constants;
import worldontheotherside.wordpress.com.autismapp.Database.AppAPI;
import worldontheotherside.wordpress.com.autismapp.Database.DBManip;
import worldontheotherside.wordpress.com.autismapp.Fragments.ProgressDialogFragment;
import worldontheotherside.wordpress.com.autismapp.R;

public class ProfileActivity extends AppCompatActivity {

    private ImageView imageViewDp;
    private ImageView imageViewEditDp;
    private TextView textViewUserProfile;
    private ImageView imageViewEdit;
    private TextView textViewEmail;
    private TextView textViewPhone;
    private EditText editTextUsername;

    private HashMap<String, String> user;;

    private FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        imageViewDp = (ImageView) findViewById(R.id.imageViewDp);
        imageViewEditDp = (ImageView) findViewById(R.id.imageViewEditDp);
        textViewUserProfile = (TextView) findViewById(R.id.textViewUserProfile);
        imageViewEdit = (ImageView) findViewById(R.id.imageViewEdit);
        textViewEmail = (TextView) findViewById(R.id.textViewEmail);
        textViewPhone = (TextView) findViewById(R.id.textViewPhone);
        editTextUsername = (EditText) findViewById(R.id.editTextUsername);

        user = new HashMap<>();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        DBManip.getData(AppAPI.USERS, firebaseUser.getEmail(), new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User userData = User.fromDB(dataSnapshot);
                Picasso.get().load(userData.getDpUri()).into(imageViewDp);
                String profile = userData.getUsername() + "'s profile";
                textViewUserProfile.setText(profile);
                textViewEmail.setText(firebaseUser.getEmail());
                textViewPhone.setText(firebaseUser.getPhoneNumber());
                editTextUsername.setText(userData.getUsername());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_OK && requestCode == 0)
        {
            Picasso.get().load(data.getData()).into(imageViewDp);
            user.put(Constants.DP, data.getData().toString());
        }
    }

    public void editSaveAction(View view)
    {
        if(!editTextUsername.isEnabled())
        {
            imageViewEdit.setImageResource(R.drawable.ic_done);
            imageViewEditDp.setVisibility(View.VISIBLE);
            editTextUsername.setBackground(ContextCompat.getDrawable(this, R.drawable.border_color_primary_thick));
            editTextUsername.setEnabled(true);
            editTextUsername.requestFocus();
            editTextUsername.setImeOptions(EditorInfo.IME_ACTION_DONE);
        }
        else
        {
            imageViewEdit.setImageResource(R.drawable.ic_edit);
            imageViewEditDp.setVisibility(View.GONE);
            editTextUsername.setBackground(null);
            editTextUsername.setEnabled(false);
            user.put(Constants.USERNAME, editTextUsername.getText().toString());
            new LoadProfile().execute();
        }
    }

    public void editDpAction(View view)
    {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, 0);
    }

    private class LoadProfile extends AsyncTask<Void, Void, Void>
    {
        private ProgressDialogFragment progressDialogFragment;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialogFragment = ProgressDialogFragment.newProgressDialogFragment(getString(R.string.please_wait_a_moment));
            progressDialogFragment.show(getSupportFragmentManager(), "PROGRESS_DIALOG");
            progressDialogFragment.setCancelable(false);
        }

        @Override
        protected Void doInBackground(Void... voids) {

            if(user.containsKey(Constants.DP))
            {
                FirebaseStorage storage = FirebaseStorage.getInstance();
                Uri path = Uri.parse(user.get(Constants.DP));

                storage.getReference().child("dp/" + path.getLastPathSegment()).putFile(path)
                        .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                user.remove(Constants.DP);
                                user.put(Constants.DP, taskSnapshot.getDownloadUrl().toString());
                                Log.v("DP_DOWNLOAD_A", user.get(Constants.DP));

                                DBManip.updateUserProfile(firebaseUser, user, ProfileActivity.this, new DatabaseReference.CompletionListener() {
                                    @Override
                                    public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                                        Log.v("PROFILE_UPDATED", "success");
                                    }
                                });
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(Exception e) {
                                Log.v("DP_ERROR_CAUSE", e.getCause().toString());
                                Log.v("DP_ERROR_LOCALISED", e.getLocalizedMessage());
                                Log.v("DP_ERROR_MESSAGE", e.getMessage());
                                Log.v("DP_ERROR_CLASS", e.getClass().toString());
                            }
                        });
            }
            else
            {
                Log.v("DP_DOWNLOAD_B", "no dp in user");

                DBManip.updateUserProfile(firebaseUser, user, ProfileActivity.this, new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                        Log.v("PROFILE_UPDATED", "success");
                    }
                });
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            progressDialogFragment.dismiss();
        }
    }
}
