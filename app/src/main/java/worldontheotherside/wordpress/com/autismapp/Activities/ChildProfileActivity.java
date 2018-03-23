package worldontheotherside.wordpress.com.autismapp.Activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.provider.MediaStore;
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
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import worldontheotherside.wordpress.com.autismapp.API.Child;
import worldontheotherside.wordpress.com.autismapp.API.CircularDrawable;
import worldontheotherside.wordpress.com.autismapp.Database.AppAPI;
import worldontheotherside.wordpress.com.autismapp.Database.DBManip;
import worldontheotherside.wordpress.com.autismapp.R;

public class ChildProfileActivity extends AppCompatActivity {

    private ImageView imageViewDp;
    private ImageView imageViewEditDp;
    private ImageView imageViewEdit;
    private EditText editTextName;
    private EditText editTextAge;
    private TextView textViewChildProfile;
    private TextView textViewGender;
    private TextView textViewScore;

    private FirebaseUser user;
    private Child newChild;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_child_profile);

        imageViewDp = (ImageView) findViewById(R.id.imageViewDp);
        imageViewEditDp = (ImageView) findViewById(R.id.imageViewEditDp);
        imageViewEdit = (ImageView) findViewById(R.id.imageViewEdit);
        editTextAge = (EditText) findViewById(R.id.editTextAge);
        editTextName = (EditText) findViewById(R.id.editTextChildName);
        textViewChildProfile = (TextView) findViewById(R.id.textViewChildProfile);
        textViewGender = (TextView) findViewById(R.id.textViewGender);
        textViewScore = (TextView) findViewById(R.id.textViewScore);

        user = FirebaseAuth.getInstance().getCurrentUser();
        newChild = new Child();

        DBManip.getData(AppAPI.CHILDREN, user.getEmail(), new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Child child = new Child(dataSnapshot);

                Target target = new Target() {
                    @Override
                    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                        CircularDrawable drawable = new CircularDrawable(bitmap);
                        imageViewDp.setImageResource(android.R.color.transparent);
                        imageViewDp.setBackground(drawable);
                    }

                    @Override
                    public void onBitmapFailed(Exception e, Drawable errorDrawable) {

                    }

                    @Override
                    public void onPrepareLoad(Drawable placeHolderDrawable) {

                    }
                };

                Picasso.get().load(child.getPhoto()).into(target);

                String profile = getString(R.string.profile_of) + child.getName();

                textViewChildProfile.setText(profile);
                editTextAge.setText(String.valueOf(child.getAge()));
                editTextName.setText(child.getName());
                textViewGender.setText(child.getGender());
                textViewScore.setText(String.valueOf(child.getAutismSpectrumScore()));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void editDpAction(View view)
    {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, 0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_OK && requestCode == 0)
        {
            Target target = new Target() {
                @Override
                public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                    CircularDrawable drawable = new CircularDrawable(bitmap);
                    imageViewDp.setImageResource(android.R.color.transparent);
                    imageViewDp.setBackground(drawable);
                }

                @Override
                public void onBitmapFailed(Exception e, Drawable errorDrawable) {
                    Log.v("BITMAP_FAILED", e.getMessage());
                }

                @Override
                public void onPrepareLoad(Drawable placeHolderDrawable) {

                }
            };

            Picasso.get().load(data.getData()).into(target);
            newChild.setPhoto(data.getData().toString());
        }
    }

    public void editSaveAction(View view)
    {
        if(!editTextName.isEnabled())
        {
            imageViewEditDp.setVisibility(View.VISIBLE);
            imageViewEdit.setImageResource(R.drawable.ic_done);
            editTextName.setEnabled(true);
            editTextAge.setEnabled(true);
            editTextName.requestFocus();
            editTextAge.setImeOptions(EditorInfo.IME_ACTION_DONE);
        }
        else
        {
            imageViewEditDp.setVisibility(View.GONE);
            imageViewEdit.setImageResource(R.drawable.ic_edit);
            editTextAge.setEnabled(false);
            editTextName.setEnabled(false);

            newChild.setAge(Integer.valueOf(editTextAge.getText().toString()));
            newChild.setName(editTextName.getText().toString());
            newChild.setGender(textViewGender.getText().toString());
            newChild.setAutismSpectrumScore(Integer.valueOf(textViewScore.getText().toString()));

            Log.v("CHILD_DATA", new Gson().toJson(newChild));

            if(newChild.getPhoto() != null)
            {
                FirebaseStorage storage = FirebaseStorage.getInstance();
                Uri uri = Uri.parse(newChild.getPhoto());

                storage.getReference().child("dp/" + uri.getLastPathSegment()).putFile(uri)
                        .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                newChild.setPhoto(taskSnapshot.getDownloadUrl().toString());
                                Log.v("DP_DOWNLOAD_A", newChild.getPhoto());

                                DBManip.updateDataWithEmail(AppAPI.CHILDREN, user.getEmail(), newChild,
                                        new DatabaseReference.CompletionListener() {
                                    @Override
                                    public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                                        //
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

                DBManip.updateDataWithEmail(AppAPI.CHILDREN, user.getEmail(), newChild,
                        new DatabaseReference.CompletionListener() {
                            @Override
                            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                                //
                            }
                        });
            }
        }
    }
}
