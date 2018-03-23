package worldontheotherside.wordpress.com.autismapp.API;

import java.util.HashMap;

/**
 * Created by OJP on 3/21/2018.
 */

public class ArticleBase {

    private Article mArticle = new Article ();
    private HashMap<String, Article> articleHashMap = new HashMap<> ();

    public HashMap<String, Article> getArticleHashMap () {
        return articleHashMap;
    }

    public void setArticleHashMap (HashMap<String, Article> articleHashMap) {
        this.articleHashMap = articleHashMap;
    }

    public Article getmArticle () {
        return mArticle;
    }

    public void setmArticle (Article mArticle) {
        this.mArticle = mArticle;
    }
}
