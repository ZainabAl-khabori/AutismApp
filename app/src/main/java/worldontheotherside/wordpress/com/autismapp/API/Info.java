package worldontheotherside.wordpress.com.autismapp.API;

import java.io.Serializable;

/**
 * Created by OJP on 3/9/2018.
 */

public class Info implements Serializable {

    private String infoTitle;
    private String infoDescription;
    private String infoPageURL;
    private String infoImageURL;


    public String getInfoTitle () {
         return infoTitle;
    }

    public void setInfoTitle (String infoTitle) {
        this.infoTitle = infoTitle;
    }

    public String getInfoDescription () {
        return infoDescription;
    }

    public void setInfoDescription (String infoDescription) {
        this.infoDescription = infoDescription;
    }

    public String getInfoPageURL () {
        return infoPageURL;
    }

    public void setInfoPageURL (String infoPageURL) {
        this.infoPageURL = infoPageURL;
    }

    public String getInfoImageURL () {
        return infoImageURL;
    }

    public void setInfoImageURL (String infoImageURL) {
        this.infoImageURL = infoImageURL;
    }



}
