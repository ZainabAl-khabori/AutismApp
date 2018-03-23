package worldontheotherside.wordpress.com.autismapp.API;

import com.google.firebase.database.DataSnapshot;

/**
 * Created by زينب on 3/17/2018.
 */

public class Event {

    private String title;
    private String description;
    private String date;
    private String time;
    private String ringtone;
    private int id;

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
            description = event.description;
            date = event.date;
            time = event.time;
            ringtone = event.ringtone;
            id = event.id;
        }
    }

    public String getTitle() { return title; }

    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }

    public void setDescription(String description) { this.description = description; }

    public String getDate() { return date; }

    public void setDate(String date) { this.date = date; }

    public String getTime() { return time; }

    public void setTime(String time) { this.time = time; }

    public String getRingtone() { return ringtone; }

    public void setRingtone(String ringtone) { this.ringtone = ringtone; }

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }
}
