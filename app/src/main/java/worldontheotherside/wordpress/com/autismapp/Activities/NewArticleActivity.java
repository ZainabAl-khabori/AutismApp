package worldontheotherside.wordpress.com.autismapp.Activities;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import worldontheotherside.wordpress.com.autismapp.API.Article;
import worldontheotherside.wordpress.com.autismapp.API.User;
import worldontheotherside.wordpress.com.autismapp.Data.Constants;
import worldontheotherside.wordpress.com.autismapp.Database.AppAPI;
import worldontheotherside.wordpress.com.autismapp.Database.DBManip;
import worldontheotherside.wordpress.com.autismapp.Fragments.ProgressDialogFragment;
import worldontheotherside.wordpress.com.autismapp.R;

public class NewArticleActivity extends AppCompatActivity {

    private TextInputLayout textInputLayoutTitle;
    private TextInputEditText editTextTitle;
    private TextView textViewContent;
    private EditText editTextContent;
    private TextView textViewUrl;
    private ImageView imageViewPicture;

    private Article article;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_article);

        textInputLayoutTitle = (TextInputLayout) findViewById(R.id.textInputLayoutTitle);
        textViewUrl = (TextView) findViewById(R.id.textViewUrl);
        textViewContent = (TextView) findViewById(R.id.textViewContent);
        editTextTitle = (TextInputEditText) findViewById(R.id.editTextTitle);
        editTextContent = (EditText) findViewById(R.id.editTextContent);
        imageViewPicture = (ImageView) findViewById(R.id.imageViewPicture);

        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        article = new Article();

        DBManip.getData(AppAPI.USERS, firebaseUser.getEmail(), new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User user = User.fromDB(dataSnapshot);
                article.setArticleWriterName(user.getUsername());
                article.setArticleWriterImageURL(user.getDpUri());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void browseAction(View view)
    {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, 0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_OK && requestCode == 0)
        {
            textViewUrl.setText(data.getData().toString());
            Picasso.get().load(data.getData()).into(imageViewPicture);
        }
    }

    public void publishAction(View view)
    {
        if(allFilled())
        {
            String date = new SimpleDateFormat("dd MMMM yyyy, hh:mm a", Locale.getDefault()).format(new Date());
            String details = "Created on " + date + " by " + article.getArticleWriterName();
            article.setArticleTitle(editTextTitle.getText().toString());
            article.setArticleContent(editTextContent.getText().toString());
            article.setArticleDetails(details);

            new Adding().execute();
        }
    }

    private boolean allFilled()
    {
        boolean allFilled = true;

        if(editTextTitle.getText().toString().isEmpty())
        {
            textInputLayoutTitle.setError(Constants.EMPTY_FIELD_ERROR);
            textInputLayoutTitle.setErrorEnabled(true);
            allFilled = false;
        }
        else
            textInputLayoutTitle.setErrorEnabled(false);

        if(editTextContent.getText().toString().isEmpty())
        {
            textViewContent.setTextColor(Color.RED);
            allFilled = false;
        }
        else
            textViewContent.setTextColor(ContextCompat.getColor(this, R.color.colorPrimaryDark));

        return allFilled;
    }

    private class Adding extends AsyncTask<Void, Void, Void>
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

            DBManip.addData(AppAPI.ARTICLES, article, new DatabaseReference.CompletionListener() {
                @Override
                public void onComplete(DatabaseError databaseError, final DatabaseReference databaseReference) {
                    Log.v("ARTICLE_ADDING", "success");
                    article.setArticleId(databaseReference.getKey());

                    if(!textViewUrl.getText().toString().equals(getString(R.string.add_a_picture)))
                    {
                        FirebaseStorage storage = FirebaseStorage.getInstance();
                        Uri uri = Uri.parse(textViewUrl.getText().toString());

                        storage.getReference().child("articles/" + article.getArticleId() + "-" +
                                uri.getLastPathSegment()).putFile(uri)
                                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                    @Override
                                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                        article.setArticleImageURL(taskSnapshot.getDownloadUrl().toString());
                                        databaseReference.setValue(article);
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(Exception e) {
                                        Log.v("IMAGE_UPLOAD_ERROR", e.getMessage());
                                    }
                                });
                    }

                    databaseReference.setValue(article);
                }
            });

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            progressDialogFragment.dismiss();

            setResult(RESULT_OK, new Intent());
            finish();
        }
    }
}
