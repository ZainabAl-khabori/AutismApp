package worldontheotherside.wordpress.com.autismapp.Activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import worldontheotherside.wordpress.com.autismapp.API.Article;
import worldontheotherside.wordpress.com.autismapp.API.Comments;
import worldontheotherside.wordpress.com.autismapp.Adapters.CommentsAdapter;
import worldontheotherside.wordpress.com.autismapp.R;

public class ArticleDetailsActivity extends AppCompatActivity {
    ArrayList<Comments> commentsArrayList;

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.article_details_activity);

        Article selectedArticle = (Article) getIntent ().getExtras ().getSerializable ("selectedArticle");

        ListView lvCommentsListView = findViewById (R.id.lv_comments_list);

        TextView tvToolbarTitle = findViewById (R.id.tv_article_title);
        TextView tvArticleDetails = findViewById (R.id.tv_article_details);
        TextView tvArticleWriterName = findViewById (R.id.tv_article_writer_name);
        TextView tvArticleContent = findViewById (R.id.tv_article_content);
        ImageView ivArticleWriterImage = findViewById (R.id.iv_article_writer_image);
        ImageView ivArticleImage = findViewById (R.id.iv_article_image);

        tvToolbarTitle.setText (selectedArticle.getArticleTitle ());
        tvArticleDetails.setText (selectedArticle.getArticleDetails ());
        tvArticleWriterName.setText (selectedArticle.getArticleWriterName ());
        tvArticleContent.setText (selectedArticle.getArticleContent ());

        if (selectedArticle.getArticleImageURL () != null && !selectedArticle.getArticleImageURL ().isEmpty ()) {
            Picasso.get().load (selectedArticle.getArticleImageURL ()).into (ivArticleImage);
        }
        if (selectedArticle.getArticleWriterImageURL () != null && !selectedArticle.getArticleWriterImageURL ().isEmpty ()) {
            Picasso.get().load (selectedArticle.getArticleWriterImageURL ()).into (ivArticleWriterImage);
        }


        selectedArticle.getCommentsList ().values ();

        commentsArrayList = new ArrayList<> ();

        commentsArrayList.addAll (selectedArticle.getCommentsList ().values ());

        lvCommentsListView.setAdapter (new CommentsAdapter (ArticleDetailsActivity.this, commentsArrayList));


        final EditText etComment = findViewById (R.id.et_comment);

        // Write a message to the database
        FirebaseDatabase database = FirebaseDatabase.getInstance ();
        final DatabaseReference myRef = database.getReference ("articlesList");


        FloatingActionButton fab = (FloatingActionButton) findViewById (R.id.iv_add_comment);
        fab.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick (View view) {
              /*  Snackbar.make (view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction ("Action", null).show ();*/
                if (etComment.getVisibility () == View.GONE) {
                    etComment.setVisibility (View.VISIBLE);
                } else {
                    Comments newComment = new Comments ();

                    newComment.setCommentId ("wkao");
                    newComment.setCommentContent (etComment.getText ().toString ());
                    newComment.setCommentDetails ("Test created on 21-3-2018");
                    newComment.setCommentWriterWriterName ("Someone");
                    newComment.setCommentWriterImageURL ("http://slklibrary.xyz/apis/images/LOGO_purchea.png");

                    myRef.child ("A1").child ("commentsList").child (newComment.getCommentId ()).setValue (newComment);

                }
            }
        });

    }
}
