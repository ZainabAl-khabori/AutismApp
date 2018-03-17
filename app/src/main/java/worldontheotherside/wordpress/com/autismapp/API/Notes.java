package worldontheotherside.wordpress.com.autismapp.API;

import com.google.firebase.database.DataSnapshot;

import java.util.ArrayList;

/**
 * Created by زينب on 3/17/2018.
 */

public class Notes {

    private ArrayList<Note> notes;

    public Notes()
    {
        //
    }

    public Notes(DataSnapshot dataSnapshot)
    {
        notes = new ArrayList<>();

        for(DataSnapshot snapshot: dataSnapshot.getChildren())
            notes.add(snapshot.getValue(Note.class));
    }

    public ArrayList<Note> getNotes() { return notes; }

    public void setNotes(ArrayList<Note> notes) { this.notes = notes; }
}
