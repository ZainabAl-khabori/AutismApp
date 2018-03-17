package worldontheotherside.wordpress.com.autismapp.Database;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by زينب on 2/23/2018.
 */

public class AppAPI {
    private static DatabaseReference db = FirebaseDatabase.getInstance().getReference();

    public static String NOTES = db.child("notes").toString();
    public static String CHILDREN = db.child("children").toString();
    public static String EVENTS = db.child("events").toString();
}
