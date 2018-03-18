package worldontheotherside.wordpress.com.autismapp.API;

import com.google.firebase.database.DataSnapshot;

/**
 * Created by زينب on 3/17/2018.
 */

public class Note {

    private String text;
    private String date;
    private String timeCreated;
    private boolean important;

    public Note()
    {
        //
    }

    public Note(DataSnapshot dataSnapshot)
    {
        for(DataSnapshot snapshot: dataSnapshot.getChildren())
        {
            Note note = snapshot.getValue(Note.class);

            this.text = note.text;
            this.date = note.date;
            this.timeCreated = note.timeCreated;
            this.important = note.important;
        }
    }

    public String getText() { return text; }

    public void setText(String text) { this.text = text; }

    public String getDate() { return date; }

    public void setDate(String date) { this.date = date; }

    public String getTimeCreated() { return timeCreated; }

    public void setTimeCreated(String timeCreated) { this.timeCreated = timeCreated; }

    public boolean isImportant() { return important; }

    public void setImportant(boolean important) { this.important = important; }
}
