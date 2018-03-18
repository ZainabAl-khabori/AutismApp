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
import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import worldontheotherside.wordpress.com.autismapp.API.Event;
import worldontheotherside.wordpress.com.autismapp.API.Events;
import worldontheotherside.wordpress.com.autismapp.Activities.EditEventActivity;
import worldontheotherside.wordpress.com.autismapp.Adapters.EventsRecyclerAdapter;
import worldontheotherside.wordpress.com.autismapp.Data.Constants;
import worldontheotherside.wordpress.com.autismapp.Database.AppAPI;
import worldontheotherside.wordpress.com.autismapp.Database.DBManip;
import worldontheotherside.wordpress.com.autismapp.R;

/**
 * Created by زينب on 3/17/2018.
 */

public class EventsFragment extends Fragment {

    private RecyclerView recyclerViewEvents;
    private FloatingActionButton floatingActionButtonAdd;
    private EventsRecyclerAdapter adapter;
    private ArrayList<Event> data;

    private String date;

    public static EventsFragment newInstance(String date)
    {
        EventsFragment eventsFragment = new EventsFragment();
        Bundle args = new Bundle();
        args.putString(Constants.DAY_DATE, date);
        eventsFragment.setArguments(args);
        return eventsFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_day_events, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerViewEvents = (RecyclerView) view.findViewById(R.id.recyclerViewEvents);
        floatingActionButtonAdd = (FloatingActionButton) view.findViewById(R.id.floatingActionButtonAdd);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerViewEvents.setLayoutManager(layoutManager);

        date = getArguments().getString(Constants.DAY_DATE);
        String email = FirebaseAuth.getInstance().getCurrentUser().getEmail();
        email = email.replace('@', '_');
        email = email.replace('.', '_');
        DBManip.getData(AppAPI.EVENTS, email + "/" + date, new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Events events = new Events(dataSnapshot);
                data = events.getEvents();

                floatingActionButtonAdd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String time = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());
                        Intent intent = new Intent(getContext(), EditEventActivity.class);
                        intent.putExtra(Constants.DAY_DATE, date);
                        intent.putExtra(Constants.NEW_EVENT, true);
                        startActivityForResult(intent, Constants.DELETE_ACTION_REQ);
                    }
                });

                adapter = new EventsRecyclerAdapter(data);
                recyclerViewEvents.setAdapter(adapter);
                adapter.setOnItemClickListener(new EventsRecyclerAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        String eventJSON = new Gson().toJson(data.get(position));
                        Intent intent = new Intent(getContext(), EditEventActivity.class);
                        intent.putExtra(Constants.DAY_DATE, date);
                        intent.putExtra(Constants.NEW_EVENT, false);
                        intent.putExtra(Constants.EVENT, eventJSON);
                        startActivityForResult(intent, Constants.DELETE_ACTION_REQ);
                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.v("EVENTS_BRINGING_ERROR", databaseError.getMessage());
            }
        });
    }
}
