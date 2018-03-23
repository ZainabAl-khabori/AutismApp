package worldontheotherside.wordpress.com.autismapp.API;

import com.google.firebase.database.DataSnapshot;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Calendar;

import worldontheotherside.wordpress.com.autismapp.Data.Constants;

/**
 * Created by زينب on 3/22/2018.
 */

public class EventDays {

    private ArrayList<String> eventDays;

    public EventDays()
    {
        //
    }

    public EventDays(DataSnapshot dataSnapshot)
    {
        eventDays = new ArrayList<>();

        for(DataSnapshot snapshot: dataSnapshot.getChildren())
            eventDays.add(snapshot.getValue(String.class));
    }

    public ArrayList<String> getEventDays() {
        return eventDays;
    }

    public static ArrayList<Calendar> getEventCalendars(ArrayList<String> strings)
    {
        ArrayList<Calendar> list = new ArrayList<>();
        for(int i = 0; i < strings.size(); i++)
        {
            if(!strings.get(i).equals(Constants.EVENT_DAYS_LIST))
            {
                String json = strings.get(i);
                Calendar calendar = new Gson().fromJson(json, Calendar.class);
                list.add(calendar);
            }
        }
        return list;
    }

    public void setEventDays(ArrayList<String> eventDays) {
        this.eventDays = eventDays;
    }
}
