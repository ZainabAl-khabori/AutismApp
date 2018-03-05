package worldontheotherside.wordpress.com.autismapp.Activities;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import java.util.ArrayList;

import worldontheotherside.wordpress.com.autismapp.API.InfoItem;
import worldontheotherside.wordpress.com.autismapp.Adapters.TabsPagerAdapter;
import worldontheotherside.wordpress.com.autismapp.Fragments.PersonalInfoFragment;
import worldontheotherside.wordpress.com.autismapp.R;

public class SignUpActivity extends AppCompatActivity implements PersonalInfoFragment.OnRecyclerReceivedListener {

    private TabsPagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        TabLayout tabLayoutTabs = (TabLayout) findViewById(R.id.tabLayoutTabs);
        ViewPager viewPagerTabs = (ViewPager) findViewById(R.id.viewPagerTabs);

        adapter = new TabsPagerAdapter(getSupportFragmentManager(), viewPagerTabs.getId());
        viewPagerTabs.setAdapter(adapter);
        tabLayoutTabs.setupWithViewPager(viewPagerTabs);
    }

    @Override
    public void onRecyclerReceived(RecyclerView recyclerView, ArrayList<InfoItem> items) {
        adapter.onRecyclerReceived(recyclerView, items);
    }
}
