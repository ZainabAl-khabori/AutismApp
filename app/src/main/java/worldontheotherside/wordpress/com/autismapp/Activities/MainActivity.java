package worldontheotherside.wordpress.com.autismapp.Activities;

import android.content.Intent;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.applandeo.materialcalendarview.EventDay;
import com.applandeo.materialcalendarview.exceptions.OutOfDateRangeException;
import com.applandeo.materialcalendarview.listeners.OnDayClickListener;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import worldontheotherside.wordpress.com.autismapp.API.DrawerItem;
import worldontheotherside.wordpress.com.autismapp.API.User;
import worldontheotherside.wordpress.com.autismapp.Adapters.DrawerRecyclerAdapter;
import worldontheotherside.wordpress.com.autismapp.Data.Constants;
import worldontheotherside.wordpress.com.autismapp.Database.AppAPI;
import worldontheotherside.wordpress.com.autismapp.Database.DBManip;
import worldontheotherside.wordpress.com.autismapp.R;

public class MainActivity extends AppCompatActivity implements OnDayClickListener {

    private RecyclerView recyclerViewDrawer;
    private DrawerLayout drawerLayoutDrawer;
    private Toolbar toolbarDrawer;

    private FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerViewDrawer = (RecyclerView) findViewById(R.id.recyclerViewDrawer);
        drawerLayoutDrawer = (DrawerLayout) findViewById(R.id.drawerLayoutDrawer);
        toolbarDrawer = (Toolbar) findViewById(R.id.toolbarDrawer);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        DBManip.getData(AppAPI.USERS, firebaseUser.getEmail(), new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User user = User.fromDB(dataSnapshot);
                setupDrawer(user);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        com.applandeo.materialcalendarview.CalendarView calendarViewCalendar;
        Calendar calendar = Calendar.getInstance();
        calendar.set(2010, 1, 1);

        calendarViewCalendar = (com.applandeo.materialcalendarview.CalendarView) findViewById(R.id.calendarViewCalendar);
        calendarViewCalendar.setOnDayClickListener(this);
        calendarViewCalendar.setMinimumDate(calendar);
        try { calendarViewCalendar.setDate(new Date()); }
        catch (OutOfDateRangeException e) { e.printStackTrace(); }
    }

    public void signoutAction(View v)
    {
        AuthUI.getInstance().signOut(this).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(Task<Void> task) {
                Log.v("SIGNOUT", "signed out");
            }
        });
    }

    private void setupDrawer(User user)
    {
        ArrayList<DrawerItem> items = new ArrayList<>();
        items.add(new DrawerItem("", 0, Constants.DRAWER_HEADER, null));
/*        items.add(new DrawerItem(getString(R.string.my_profile), R.drawable.ic_username,
                Constants.DRAWER_ITEM, MyProfileActivity.class));
        items.add(new DrawerItem(getString(R.string.my_child_s_profile), R.drawable.ic_child,
                Constants.DRAWER_ITEM, ChildProfileActivity.class));*/
        items.add(new DrawerItem(Constants.KNOWLEDGE, R.drawable.ic_score,
                Constants.DRAWER_ITEM, InfoMainActivity.class));
/*        items.add(new DrawerItem(getString(R.string.forum), R.drawable.ic_forum,
                Constants.DRAWER_ITEM, ForumActivity.class));
        items.add(new DrawerItem(getString(R.string.contact), R.drawable.ic_contact,
                Constants.DRAWER_ITEM, ContactActivity.class));*/

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        DrawerRecyclerAdapter adapter = new DrawerRecyclerAdapter(items, user, this);

        recyclerViewDrawer.setLayoutManager(layoutManager);
        recyclerViewDrawer.setAdapter(adapter);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayoutDrawer, toolbarDrawer,
                R.string.drawer_open, R.string.drawer_close){
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }
        };

        drawerLayoutDrawer.addDrawerListener(toggle);
        toggle.syncState();
    }

    @Override
    public void onDayClick(EventDay eventDay) {
        Calendar calendar = eventDay.getCalendar();
        String date = new SimpleDateFormat("dd MMMM yyyy", Locale.getDefault()).format(calendar.getTime());

        Intent intent = new Intent(this, DayViewActivity.class);
        intent.putExtra(Constants.DAY_DATE, date);
        startActivity(intent);
    }
}
