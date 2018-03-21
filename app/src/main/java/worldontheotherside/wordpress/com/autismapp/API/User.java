package worldontheotherside.wordpress.com.autismapp.API;

import com.google.firebase.database.DataSnapshot;

/**
 * Created by زينب on 3/20/2018.
 */

public class User {

    private static User user = new User();

    private String username;
    private String dpUri;

    public User()
    {
        //
    }

    public static User fromDB(DataSnapshot dataSnapshot)
    {
        User newUser = dataSnapshot.getValue(User.class);

        user.username = newUser.username;
        user.dpUri = newUser.dpUri;

        return user;
    }

    public static User getUser() { return user; }

    public String getUsername() { return username; }

    public void setUsername(String username) { this.username = username; }

    public String getDpUri() { return dpUri; }

    public void setDpUri(String dpUri) { this.dpUri = dpUri; }
}
