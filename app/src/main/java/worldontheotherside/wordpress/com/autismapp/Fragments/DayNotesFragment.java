package worldontheotherside.wordpress.com.autismapp.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import worldontheotherside.wordpress.com.autismapp.API.Note;
import worldontheotherside.wordpress.com.autismapp.API.Notes;
import worldontheotherside.wordpress.com.autismapp.Activities.EditNoteActivity;
import worldontheotherside.wordpress.com.autismapp.Adapters.DayNotesRecyclerAdapter;
import worldontheotherside.wordpress.com.autismapp.Data.Constants;
import worldontheotherside.wordpress.com.autismapp.Database.AppAPI;
import worldontheotherside.wordpress.com.autismapp.Database.DBManip;
import worldontheotherside.wordpress.com.autismapp.R;

/**
 * Created by زينب on 3/17/2018.
 */

public class DayNotesFragment extends Fragment {

    private RecyclerView recyclerViewNotes;
    private FloatingActionButton floatingActionButtonAdd;
    private DayNotesRecyclerAdapter adapter;
    private ArrayList<Note> data;

    private String date;

    public static DayNotesFragment newInstance(String date)
    {
        DayNotesFragment dayNotesFragment = new DayNotesFragment();
        Bundle args = new Bundle();
        args.putString(Constants.DAY_DATE, date);
        dayNotesFragment.setArguments(args);
        return dayNotesFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_day_notes, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerViewNotes = (RecyclerView) view.findViewById(R.id.recyclerViewNotes);
        floatingActionButtonAdd = (FloatingActionButton) view.findViewById(R.id.floatingActionButtonAdd);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerViewNotes.setLayoutManager(layoutManager);

        date = getArguments().getString(Constants.DAY_DATE);
        String email = FirebaseAuth.getInstance().getCurrentUser().getEmail();
        email = email.replace('@', '_');
        email = email.replace('.', '_');
        DBManip.getData(AppAPI.NOTES, email + "/" + date, new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Notes notes = new Notes(dataSnapshot);
                data = notes.getNotes();

                floatingActionButtonAdd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getContext(), EditNoteActivity.class);
                        intent.putExtra(Constants.DAY_DATE, date);
                        intent.putExtra(Constants.NEW_NOTE, true);
                        intent.putExtra(Constants.RECYCLER_POSITION, data.size());
                        startActivity(intent);
                    }
                });

                adapter = new DayNotesRecyclerAdapter(data);
                recyclerViewNotes.setAdapter(adapter);
                adapter.setOnItemClickListener(new DayNotesRecyclerAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Intent intent = new Intent(getContext(), EditNoteActivity.class);
                        intent.putExtra(Constants.DAY_DATE, date);
                        intent.putExtra(Constants.NEW_NOTE, false);
                        intent.putExtra(Constants.NOTE_BODY, data.get(position).getText());
                        intent.putExtra(Constants.IMPORTANT, data.get(position).isImportant());
                        intent.putExtra(Constants.RECYCLER_POSITION, position);
                        startActivity(intent);
                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.v("NOTES_BRINGING_ERROR", databaseError.getMessage());
            }
        });
    }
}
