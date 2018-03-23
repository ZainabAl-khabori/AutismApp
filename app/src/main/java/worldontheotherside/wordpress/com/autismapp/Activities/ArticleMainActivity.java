package worldontheotherside.wordpress.com.autismapp.Activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import worldontheotherside.wordpress.com.autismapp.API.Article;
import worldontheotherside.wordpress.com.autismapp.API.Articles;
import worldontheotherside.wordpress.com.autismapp.Adapters.ArticlesAdapter;
import worldontheotherside.wordpress.com.autismapp.Data.Constants;
import worldontheotherside.wordpress.com.autismapp.Database.AppAPI;
import worldontheotherside.wordpress.com.autismapp.Database.DBManip;
import worldontheotherside.wordpress.com.autismapp.Fragments.ProgressDialogFragment;
import worldontheotherside.wordpress.com.autismapp.R;

public class ArticleMainActivity extends AppCompatActivity {
    ArrayList<Article> articlesList;

    private ListView lvArticlesListView;

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.artical_main_design);

        // Write a message to the database
        final FirebaseDatabase database = FirebaseDatabase.getInstance ();
        final DatabaseReference myRef = database.getReference ("articlesList");


        FrameLayout flToolBar = (FrameLayout) findViewById (R.id.toolbar);
        final TextView tvToolBarTitle = (TextView) findViewById (R.id.tv_search_title);
        final EditText etToolBarSearchKey = (EditText) findViewById (R.id.search_bar);
        lvArticlesListView = findViewById (R.id.lv_articles_list);
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
                Intent intent = new Intent(ArticleMainActivity.this, NewArticleActivity.class);
                startActivityForResult(intent, Constants.NEW_ARTICLE_REQ);
            }
        });

        new LoadingForum().execute();
    }

    private class LoadingForum extends AsyncTask<Void, Void, Void>
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

            DBManip.getData(AppAPI.ARTICLES, new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    Articles articles = new Articles(dataSnapshot);
                    articlesList = articles.getArticles();

                    lvArticlesListView.setAdapter(new ArticlesAdapter(ArticleMainActivity.this, articlesList));
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            progressDialogFragment.dismiss();
        }
    }
}
