package worldontheotherside.wordpress.com.autismapp.API;

import java.io.Serializable;
import java.util.HashMap;

/**
 * Created by OJP on 3/18/2018.
 */


public class Article implements Serializable {

     private String articleId;
    private String articleTitle;
    private String articleContent;
    private String articleDetails;
    private String articleImageURL;

    private String articleWriterName;
    private String articleWriterImageURL;


    private HashMap<String, Comments> commentsList = new HashMap<> ();
    private Comments mComment = new Comments ();

    public String getArticleTitle () {
        return articleTitle;
    }

    public void setArticleTitle (String articleTitle) {
        this.articleTitle = articleTitle;
    }

    public String getArticleContent () {
        return articleContent;
    }

    public void setArticleContent (String articleContent) {
        this.articleContent = articleContent;
    }

    public String getArticleDetails () {
        return articleDetails;
    }

    public void setArticleDetails (String articleDetails) {
        this.articleDetails = articleDetails;
    }

    public String getArticleImageURL () {
        return articleImageURL;
    }

    public void setArticleImageURL (String articleImageURL) {
        this.articleImageURL = articleImageURL;
    }

    public String getArticleWriterName () {
        return articleWriterName;
    }

    public void setArticleWriterName (String articleWriterName) {
        this.articleWriterName = articleWriterName;
    }

    public String getArticleWriterImageURL () {
        return articleWriterImageURL;
    }

    public void setArticleWriterImageURL (String articleWriterImageURL) {
        this.articleWriterImageURL = articleWriterImageURL;
    }

    public String getArticleId () {
        return articleId;
    }

    public void setArticleId (String articleId) {
        this.articleId = articleId;
    }



    public Comments getmComment () {
        return mComment;
    }

    public void setmComment (Comments mComment) {
        this.mComment = mComment;
    }

    public HashMap<String, Comments> getCommentsList () {
        return commentsList;
    }

    public void setCommentsList (HashMap<String, Comments> commentsList) {
        this.commentsList = commentsList;
    }
}

