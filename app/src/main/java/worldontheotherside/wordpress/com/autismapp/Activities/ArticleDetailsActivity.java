package worldontheotherside.wordpress.com.autismapp.Activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import worldontheotherside.wordpress.com.autismapp.API.Article;
import worldontheotherside.wordpress.com.autismapp.R;

public class ArticleDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.article_details_activity);


        // Write a message to the database
        FirebaseDatabase database = FirebaseDatabase.getInstance ();
        final DatabaseReference myRef = database.getReference ("articlesList");

        TextView tvToolbarTitle = findViewById (R.id.tv_search_title);

        final EditText etComment = findViewById (R.id.et_comment);

        FloatingActionButton fab = (FloatingActionButton) findViewById (R.id.iv_add_comment);
        fab.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick (View view) {
              /*  Snackbar.make (view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction ("Action", null).show ();*/
                if (etComment.getVisibility () == View.GONE) {
                    etComment.setVisibility (View.VISIBLE);
                } else {
                    Article newComment = new Article ();

                    newComment.setArticleId ("wa");
                    newComment.setArticleContent ("this this something comment");
                    newComment.setArticleDetails ("Test created on 21-3-2018");
                    newComment.setArticleWriterName ("Someone");
                    newComment.setArticleWriterImageURL ("http://slklibrary.xyz/apis/images/LOGO_purchea.png");

                    myRef.child ("A1").child ("commentsList").child (newComment.getArticleId ()).setValue (newComment);

                }
            }
        });

    }
}
