package worldontheotherside.wordpress.com.autismapp.API;

/**
 * Created by زينب on 3/18/2018.
 */

public class EventDisplayItem {

    private String title;
    private int type;

    public EventDisplayItem(String title, int type) {
        this.title = title;
        this.type = type;
    }

    public String getTitle() { return title; }

    public int getType() { return type; }
}
