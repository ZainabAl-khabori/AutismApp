package worldontheotherside.wordpress.com.autismapp.Database;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by زينب on 2/23/2018.
 */

public class DBManip {

    private static DatabaseReference db;

    public static void getData(String url, ValueEventListener valueEventListener)
    {
        db = FirebaseDatabase.getInstance().getReferenceFromUrl(url);
        db.addValueEventListener(valueEventListener);
    }

    public static void getData(String url, String child, ValueEventListener valueEventListener)
    {
        db = FirebaseDatabase.getInstance().getReferenceFromUrl(url).child(child);
        db.addValueEventListener(valueEventListener);
    }

    public static void findData(String url, String field, String param, ValueEventListener valueEventListener)
    {
        db = FirebaseDatabase.getInstance().getReferenceFromUrl(url);
        Query q = db.orderByChild(field).equalTo(param);
        q.addValueEventListener(valueEventListener);
    }

    public static void addData(String url, String email, Object data, DatabaseReference.CompletionListener completionListener)
    {
        db = FirebaseDatabase.getInstance().getReferenceFromUrl(url);
        db.child(email).setValue(data, completionListener);
    }

    public static void updateData(String url, String email, Object data, DatabaseReference.CompletionListener completionListener)
    {
        db = FirebaseDatabase.getInstance().getReferenceFromUrl(url).child(email);
        db.setValue(data, completionListener);
    }

    public static void deleteData(String url, Object data, DatabaseReference.CompletionListener completionListener)
    {
        db = FirebaseDatabase.getInstance().getReferenceFromUrl(url);
        db.setValue(null, completionListener);
    }

    public static void deleteData(String url, String field, Object data, DatabaseReference.CompletionListener completionListener)
    {
        db = FirebaseDatabase.getInstance().getReferenceFromUrl(url).child(field);
        db.setValue(null, completionListener);
    }
}
