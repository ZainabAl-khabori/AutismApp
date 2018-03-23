package worldontheotherside.wordpress.com.autismapp.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import worldontheotherside.wordpress.com.autismapp.API.Article;
import worldontheotherside.wordpress.com.autismapp.Activities.ArticleDetailsActivity;
import worldontheotherside.wordpress.com.autismapp.R;

/**
 * Created by OJP on 3/21/2018.
 */

public class ArticlesAdapter extends BaseAdapter {
    Context context;
    ArrayList<Article> articlesList;

    public ArticlesAdapter (Context context, ArrayList<Article> articlesList) {
        this.articlesList = articlesList;
        this.context = context;
    }

    @Override
    public int getCount () {
        return articlesList.size ();
    }

    @Override
    public Object getItem (int i) {
        return null;
    }

    @Override
    public long getItemId (int i) {
        return 0;
    }

    @Override
    public View getView (int i, View articleListItem, ViewGroup viewGroup) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService (Context.LAYOUT_INFLATER_SERVICE);
        articleListItem = inflater.inflate (R.layout.artical_list_item, null);

        final Article article = articlesList.get (i);

        TextView tvArticleTitle = articleListItem.findViewById (R.id.tv_article_title);
        TextView tvArticleDescription = articleListItem.findViewById (R.id.tv_article_content);
        ImageView ivArticleImage = articleListItem.findViewById (R.id.iv_article_image);

        tvArticleTitle.setText (article.getArticleTitle ());
        tvArticleDescription.setText (article.getArticleContent ());

        if (article.getArticleImageURL () != null && !article.getArticleImageURL ().isEmpty ()) {
            Picasso.get().load (article.getArticleImageURL ()).into (ivArticleImage);
        }

        articleListItem.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick (View view) {
                Intent go = new Intent (context, ArticleDetailsActivity.class);
                go.putExtra ("selectedArticle", article);
                context.startActivity (go);
            }
        });

        return articleListItem;
    }
}
