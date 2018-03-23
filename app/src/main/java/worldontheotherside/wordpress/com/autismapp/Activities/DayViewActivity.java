package worldontheotherside.wordpress.com.autismapp.Activities;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import worldontheotherside.wordpress.com.autismapp.Adapters.DayViewPagerAdapter;
import worldontheotherside.wordpress.com.autismapp.Data.Constants;
import worldontheotherside.wordpress.com.autismapp.R;

public class DayViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_day_view);

        TextView textViewDate = (TextView) findViewById(R.id.textViewDate);
        TabLayout tabLayoutTabs = (TabLayout) findViewById(R.id.tabLayoutTabs);
        ViewPager viewPagerTabs = (ViewPager) findViewById(R.id.viewPagerTabs);

        String date = getIntent().getStringExtra(Constants.DAY_DATE);
        String calendar = getIntent().getStringExtra(Constants.CALENDAR);
        DayViewPagerAdapter adapter = new DayViewPagerAdapter(getSupportFragmentManager(), date, calendar);

        viewPagerTabs.setAdapter(adapter);
        tabLayoutTabs.setupWithViewPager(viewPagerTabs);
        date = date.replace('-', ' ');
        textViewDate.setText(date);
    }
}
