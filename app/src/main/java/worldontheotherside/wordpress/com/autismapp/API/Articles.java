package worldontheotherside.wordpress.com.autismapp.API;

import com.google.firebase.database.DataSnapshot;

import java.util.ArrayList;

/**
 * Created by زينب on 3/22/2018.
 */

public class Articles {

    ArrayList<Article> articles;

    public Articles()
    {
        //
    }

    public Articles(DataSnapshot dataSnapshot)
    {
        articles = new ArrayList<>();

        for(DataSnapshot snapshot: dataSnapshot.getChildren())
            articles.add(snapshot.getValue(Article.class));
    }

    public ArrayList<Article> getArticles() { return articles; }

    public void setArticles(ArrayList<Article> articles) { this.articles = articles; }
}
