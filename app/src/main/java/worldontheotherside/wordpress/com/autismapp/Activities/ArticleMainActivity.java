package worldontheotherside.wordpress.com.autismapp.Activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import worldontheotherside.wordpress.com.autismapp.API.Article;
import worldontheotherside.wordpress.com.autismapp.R;

public class ArticleMainActivity extends AppCompatActivity {

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.artical_main_design);

        // Write a message to the database
        FirebaseDatabase database = FirebaseDatabase.getInstance ();
        final DatabaseReference myRef = database.getReference ("articlesList");


        FrameLayout flToolBar = (FrameLayout) findViewById (R.id.toolbar);
        final TextView tvToolBarTitle = (TextView) findViewById (R.id.tv_search_title);
        final EditText etToolBarSearchKey = (EditText) findViewById (R.id.search_bar);
        ImageView imgvwSearchIcon = (ImageView) findViewById (R.id.imgvw_search_icon);

        flToolBar.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick (View v) {

                if (etToolBarSearchKey.getVisibility () == View.GONE) {
                    tvToolBarTitle.setVisibility (View.GONE);
                    etToolBarSearchKey.setVisibility (View.VISIBLE);
                }
            }
        });


        imgvwSearchIcon.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick (View v) {

                if (etToolBarSearchKey.getVisibility () == View.GONE) {
                    tvToolBarTitle.setVisibility (View.GONE);
                    etToolBarSearchKey.setVisibility (View.VISIBLE);
                } else {
                    //  Intent go = new Intent (getApplicationContext (),SearchBooksActivity.class);
                }
            }
        });


        FloatingActionButton fab = (FloatingActionButton) findViewById (R.id.fab);
        fab.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick (View view) {
              /*  Snackbar.make (view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction ("Action", null).show ();
*/

                Article newArticle = new Article ();

                newArticle.setArticleId ("A2");
                newArticle.setArticleTitle ("Next Test");
                newArticle.setArticleContent ("this is some test content for test");
                newArticle.setArticleDetails ("Test created on 21-3-2018");
                newArticle.setArticleImageURL ("http://slklibrary.xyz/apis/images/IMG_story.png");
                newArticle.setArticleWriterName ("JIM");
                newArticle.setArticleWriterImageURL ("http://slklibrary.xyz/apis/images/IMG_story.png");

                myRef.child (newArticle.getArticleId ()).setValue (newArticle);

            }
        });
    }

}
