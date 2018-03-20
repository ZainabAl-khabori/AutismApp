package worldontheotherside.wordpress.com.autismapp.API;

/**
 * Created by زينب on 3/19/2018.
 */

public class DrawerItem {

    private String title;
    private int icon;
    private Class<?> activityClass;
    private int type;

    public DrawerItem(String title, int icon, int type, Class<?> activityClass) {
        this.title = title;
        this.icon = icon;
        this.type = type;
        this.activityClass = activityClass;
    }

    public String getTitle() { return title; }

    public int getIcon() { return icon; }

    public int getType() { return type; }

    public Class<?> getActivityClass() { return activityClass; }
}
