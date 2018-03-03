package worldontheotherside.wordpress.com.autismapp.API;

/**
 * Created by زينب on 3/2/2018.
 */

public class InfoItem {
    private String title;
    private int type;
    private int icon;

    public InfoItem(String title, int type, int icon) {
        this.title = title;
        this.type = type;
        this.icon = icon;
    }

    public String getTitle() { return title; }

    public int getType() { return type; }

    public int getIcon() { return icon; }
}
