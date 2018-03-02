package worldontheotherside.wordpress.com.autismapp.Adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import worldontheotherside.wordpress.com.autismapp.Fragments.ChildInfoFragment;
import worldontheotherside.wordpress.com.autismapp.Fragments.PersonalInfoFragment;

/**
 * Created by زينب on 3/1/2018.
 */

public class TabsPagerAdapter extends FragmentPagerAdapter {

    private String[] tabs = {"Personal info", "My child's info"};

    public TabsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {

        switch(position)
        {
            case 0:
                return PersonalInfoFragment.newPersonalInfoFragment(1);
            case 1:
                return ChildInfoFragment.newChildInfoFragment(2);
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
}
