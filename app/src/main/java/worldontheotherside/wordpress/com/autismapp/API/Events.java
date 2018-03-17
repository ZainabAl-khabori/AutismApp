package worldontheotherside.wordpress.com.autismapp.API;

import com.google.firebase.database.DataSnapshot;

import java.util.ArrayList;

/**
 * Created by زينب on 3/17/2018.
 */

public class Events {

    ArrayList<Event> events;

    public Events()
    {
        //
    }

    public Events(DataSnapshot dataSnapshot)
    {
        events = new ArrayList<>();

        for(DataSnapshot snapshot: dataSnapshot.getChildren())
            events.add(snapshot.getValue(Event.class));
    }

    public ArrayList<Event> getEvents() { return events; }

    public void setEvents(ArrayList<Event> events) { this.events = events; }
}
