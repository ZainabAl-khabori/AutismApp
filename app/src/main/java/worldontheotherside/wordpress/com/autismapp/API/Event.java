package worldontheotherside.wordpress.com.autismapp.API;

import com.google.firebase.database.DataSnapshot;

/**
 * Created by زينب on 3/17/2018.
 */

public class Event {

    private String title;
    private String date;
    private String time;

    public Event()
    {
        //
    }

    public Event(DataSnapshot dataSnapshot)
    {
        for(DataSnapshot snapshot: dataSnapshot.getChildren())
        {
            Event event = snapshot.getValue(Event.class);

            title = event.title;
            date = event.date;
            time = event.time;
        }
    }

    public String getTitle() { return title; }

    public void setTitle(String title) { this.title = title; }

    public String getDate() { return date; }

    public void setDate(String date) { this.date = date; }

    public String getTime() { return time; }

    public void setTime(String time) { this.time = time; }
}
