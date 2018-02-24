package worldontheotherside.wordpress.com.autismapp.API;

import com.google.firebase.database.DataSnapshot;

import java.util.ArrayList;

/**
 * Created by زينب on 2/23/2018.
 */

public class Children {

    private ArrayList<Child> children;

    public Children()
    {
        //
    }

    public Children(DataSnapshot dataSnapshot)
    {
        children = new ArrayList<>();

        for(DataSnapshot snapshot: dataSnapshot.getChildren())
            children.add(snapshot.getValue(Child.class));
    }

    public ArrayList<Child> getChildren() {
        return children;
    }

    public void setChildren(ArrayList<Child> children) {
        this.children = children;
    }
}
