package worldontheotherside.wordpress.com.autismapp.Adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import worldontheotherside.wordpress.com.autismapp.Data.Constants;
import worldontheotherside.wordpress.com.autismapp.Fragments.DayNotesFragment;
import worldontheotherside.wordpress.com.autismapp.Fragments.EventsFragment;

/**
 * Created by زينب on 3/17/2018.
 */

public class DayViewPagerAdapter extends FragmentPagerAdapter {

    private String[] tabs = {Constants.NOTES, Constants.EVENTS};

    private DayNotesFragment dayNotesFragment;
    private EventsFragment eventsFragment;

    public DayViewPagerAdapter(FragmentManager fm, String date) {
        super(fm);
        dayNotesFragment = DayNotesFragment.newInstance(date);
        eventsFragment = EventsFragment.newInstance(date);
    }

    @Override
    public Fragment getItem(int position) {

        switch(position)
        {
            case 0:
                return dayNotesFragment;
            case 1:
                return eventsFragment;
            default:
                return null;
        }
    }

    @Override
    public int getCount() { return 2; }

    @Override
    public CharSequence getPageTitle(int position) { return tabs[position]; }
}
