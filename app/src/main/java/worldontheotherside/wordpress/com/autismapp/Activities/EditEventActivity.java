package worldontheotherside.wordpress.com.autismapp.Activities;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TimePicker;

import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import worldontheotherside.wordpress.com.autismapp.API.Event;
import worldontheotherside.wordpress.com.autismapp.API.EventDisplayItem;
import worldontheotherside.wordpress.com.autismapp.Adapters.EventViewRecyclerAdapter;
import worldontheotherside.wordpress.com.autismapp.Data.Constants;
import worldontheotherside.wordpress.com.autismapp.R;

public class EditEventActivity extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener {

    private OnTimeSelectedListener onTimeSelectedListener;
    private OnRingtoneSelectedListener onRingtoneSelectedListener;

    private Ringtone ringtone;

    public interface OnTimeSelectedListener { void onTimeSelected(TimePicker view, int hr, int min); }
    public interface OnRingtoneSelectedListener {
        void onRingtoneSelected(Ringtone ringtone, Uri uri);
        void onActivityStopped();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_event);

        String date = getIntent().getStringExtra(Constants.DAY_DATE);
        boolean newEvent = getIntent().getBooleanExtra(Constants.NEW_EVENT, false);
        String eventJSON = getIntent().getStringExtra(Constants.EVENT);
        String calendarJSON = getIntent().getStringExtra(Constants.CALENDAR);

        Log.v("JSON", calendarJSON);

        Event event;

        if(newEvent)
        {
            event = new Event();
            event.setDate(date);
            event.setRingtone(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION).toString());
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
        EventViewRecyclerAdapter adapter = new EventViewRecyclerAdapter(items, event, this, newEvent,
                getSupportFragmentManager(), calendarJSON);

        recyclerViewEventDisplay.setLayoutManager(layoutManager);
        recyclerViewEventDisplay.setAdapter(adapter);
        onTimeSelectedListener = (OnTimeSelectedListener) adapter;
        onRingtoneSelectedListener = (OnRingtoneSelectedListener) adapter;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == Constants.RQS_RINGTONE_PICKER && resultCode == RESULT_OK)
        {
            Uri uri = data.getParcelableExtra(RingtoneManager.EXTRA_RINGTONE_PICKED_URI);
            Ringtone ringtone = RingtoneManager.getRingtone(getApplicationContext(), uri);
            onRingtoneSelectedListener.onRingtoneSelected(ringtone, uri);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        onRingtoneSelectedListener.onActivityStopped();
    }

    @Override
    public void onTimeSet(TimePicker timePicker, int i, int i1) {
        onTimeSelectedListener.onTimeSelected(timePicker, i, i1);
    }
}
