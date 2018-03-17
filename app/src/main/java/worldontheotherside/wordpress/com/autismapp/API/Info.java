package worldontheotherside.wordpress.com.autismapp.API;

import java.io.Serializable;

/**
 * Created by OJP on 3/9/2018.
 */

public class Info implements Serializable {

    private String InfoTitle;
    private String InfoDescription;
    private String InfoPageURL;
    private String InfoImageURL;


    public String getInfoTitle () {
         return InfoTitle;
    }

    public void setInfoTitle (String infoTitle) {
        InfoTitle = infoTitle;
    }

    public String getInfoDescription () {
        return InfoDescription;
    }

    public void setInfoDescription (String infoDescription) {
        InfoDescription = infoDescription;
    }

    public String getInfoPageURL () {
        return InfoPageURL;
    }

    public void setInfoPageURL (String infoPageURL) {
        InfoPageURL = infoPageURL;
    }

    public String getInfoImageURL () {
        return InfoImageURL;
    }

    public void setInfoImageURL (String infoImageURL) {
        InfoImageURL = infoImageURL;
    }



}
