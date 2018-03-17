package worldontheotherside.wordpress.com.autismapp.API;

import com.google.firebase.database.DataSnapshot;

/**
 * Created by زينب on 3/17/2018.
 */

public class Note {

    private String text;
    private long date;
    private boolean important;

    public Note()
    {
        //
    }

    public Note(DataSnapshot dataSnapshot)
    {
        for(DataSnapshot snapshot: dataSnapshot.getChildren())
        {
            Note note = new Note();

            this.text = note.text;
            this.date = note.date;
            this.important = note.important;
        }
    }

    public String getText() { return text; }

    public void setText(String text) { this.text = text; }

    public long getDate() { return date; }

    public void setDate(long date) { this.date = date; }

    public boolean isImportant() { return important; }

    public void setImportant(boolean important) { this.important = important; }
}