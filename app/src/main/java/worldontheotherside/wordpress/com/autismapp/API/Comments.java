package worldontheotherside.wordpress.com.autismapp.API;

import java.io.Serializable;

public class Comments implements Serializable{
   /* "articleId": "wa",
            "articleContent": "this this something comment",
            "articleWriterImageURL": "http: //slklibrary.xyz/apis/images/LOGO_purchea.png",
            "articleDetails": "Test created on 21 - 3 - 2018",
            "articleWriterName": "Someone"*/

    private String commentId;
    private String commentContent;
    private String commentDetails;
    private String commentWriterImageURL;
    private String commentWriterWriterName;


    public String getCommentId () {
        return commentId;
    }

    public void setCommentId (String commentId) {
        this.commentId = commentId;
    }

    public String getCommentContent () {
        return commentContent;
    }

    public void setCommentContent (String commentContent) {
        this.commentContent = commentContent;
    }

    public String getCommentDetails () {
        return commentDetails;
    }

    public void setCommentDetails (String commentDetails) {
        this.commentDetails = commentDetails;
    }

    public String getCommentWriterImageURL () {
        return commentWriterImageURL;
    }

    public void setCommentWriterImageURL (String commentWriterImageURL) {
        this.commentWriterImageURL = commentWriterImageURL;
    }

    public String getCommentWriterWriterName () {
        return commentWriterWriterName;
    }

    public void setCommentWriterWriterName (String commentWriterWriterName) {
        this.commentWriterWriterName = commentWriterWriterName;
    }
}