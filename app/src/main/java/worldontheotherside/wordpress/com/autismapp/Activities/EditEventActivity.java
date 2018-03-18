package worldontheotherside.wordpress.com.autismapp.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.google.gson.Gson;

import java.util.ArrayList;

import worldontheotherside.wordpress.com.autismapp.API.Event;
import worldontheotherside.wordpress.com.autismapp.API.EventDisplayItem;
import worldontheotherside.wordpress.com.autismapp.Adapters.EventViewRecyclerAdapter;
import worldontheotherside.wordpress.com.autismapp.Data.Constants;
import worldontheotherside.wordpress.com.autismapp.R;

public class EditEventActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_event);

        String date = getIntent().getStringExtra(Constants.DAY_DATE);
        boolean newEvent = getIntent().getBooleanExtra(Constants.NEW_EVENT, false);
        String eventJSON = getIntent().getStringExtra(Constants.EVENT);

        Event event;

        if(newEvent)
        {
            event = new Event();
            event.setDate(date);
        }
        else
            event = new Gson().fromJson(eventJSON, Event.class);

        ArrayList<EventDisplayItem> items = new ArrayList<>();
        items.add(new EventDisplayItem(Constants.EVENT, Constants.EVENT_VIEW_RECYCLER_FIELD));
        items.add(new EventDisplayItem(Constants.EVENT_TITLE, Constants.EVENT_VIEW_RECYCLER_BODY));
        items.add(new EventDisplayItem(Constants.DATE, Constants.EVENT_VIEW_RECYCLER_FIELD));
        items.add(new EventDisplayItem(Constants.TIME, Constants.EVENT_VIEW_RECYCLER_FIELD));
        items.add(new EventDisplayItem(Constants.EVENT_DESCRIPTION, Constants.EVENT_VIEW_RECYCLER_FIELD));
        items.add(new EventDisplayItem(Constants.EVENT_INFO, Constants.EVENT_VIEW_RECYCLER_BODY));
        items.add(new EventDisplayItem(Constants.RINGTONE, Constants.EVENT_VIEW_RECYCLER_FIELD));

        RecyclerView recyclerViewEventDisplay = (RecyclerView) findViewById(R.id.recyclerViewEventDisplay);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        EventViewRecyclerAdapter adapter = new EventViewRecyclerAdapter(items, event, this, newEvent);

        recyclerViewEventDisplay.setLayoutManager(layoutManager);
        recyclerViewEventDisplay.setAdapter(adapter);
    }
}
