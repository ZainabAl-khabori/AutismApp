package worldontheotherside.wordpress.com.autismapp.Activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CalendarView;

import com.applandeo.materialcalendarview.EventDay;
import com.applandeo.materialcalendarview.exceptions.OutOfDateRangeException;
import com.applandeo.materialcalendarview.listeners.OnDayClickListener;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import worldontheotherside.wordpress.com.autismapp.Data.Constants;
import worldontheotherside.wordpress.com.autismapp.R;

public class MainActivity extends AppCompatActivity implements OnDayClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
            public void onComplete(@NonNull Task<Void> task) {
                Log.v("SIGNOUT", "signed out");
            }
        });
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
