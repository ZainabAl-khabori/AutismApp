package worldontheotherside.wordpress.com.autismapp.Adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

import worldontheotherside.wordpress.com.autismapp.API.InfoItem;
import worldontheotherside.wordpress.com.autismapp.Fragments.ChildInfoFragment;
import worldontheotherside.wordpress.com.autismapp.Fragments.PersonalInfoFragment;

/**
 * Created by زينب on 3/1/2018.
 */

public class TabsPagerAdapter extends FragmentPagerAdapter implements PersonalInfoFragment.OnRecyclerReceivedListener {

    private String[] tabs = {"Personal info", "My child's info"};

    private int id;
    private PersonalInfoFragment personalInfoFragment;
    private ChildInfoFragment childInfoFragment;

    public TabsPagerAdapter(FragmentManager fm, int id) {
        super(fm);
        this.id = id;
        personalInfoFragment = PersonalInfoFragment.newPersonalInfoFragment(id);
        childInfoFragment = ChildInfoFragment.newChildInfoFragment(id);
    }

    @Override
    public Fragment getItem(int position) {

        switch(position)
        {
            case 0:
                return personalInfoFragment;
            case 1:
                return childInfoFragment;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabs[position];
    }

    @Override
    public void onRecyclerReceived(RecyclerView recyclerView, ArrayList<InfoItem> items) {
        childInfoFragment.onRecyclerReceived(recyclerView, items);
    }
}
