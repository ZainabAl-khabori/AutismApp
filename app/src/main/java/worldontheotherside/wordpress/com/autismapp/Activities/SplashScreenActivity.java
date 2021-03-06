package worldontheotherside.wordpress.com.autismapp.Activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

import worldontheotherside.wordpress.com.autismapp.API.EventDays;
import worldontheotherside.wordpress.com.autismapp.Adapters.EventViewRecyclerAdapter;
import worldontheotherside.wordpress.com.autismapp.Data.Constants;
import worldontheotherside.wordpress.com.autismapp.Data.Keys;
import worldontheotherside.wordpress.com.autismapp.Database.AppAPI;
import worldontheotherside.wordpress.com.autismapp.Database.DBManip;
import worldontheotherside.wordpress.com.autismapp.R;

public class SplashScreenActivity extends AppCompatActivity {

    private FirebaseUser user;
    private Intent intent;

    private ArrayList<String> eventDaysList;

    public static HashMap<String, String> STRING_CONSTANTS = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        user = FirebaseAuth.getInstance().getCurrentUser();

        new Loading().execute();
    }

    private class Loading extends AsyncTask<Void, Void, Void>
    {
        private boolean data = false;

        @Override
        protected void onPreExecute() { super.onPreExecute(); }

        @Override
        protected Void doInBackground(Void... voids) {
            if(user != null)
            {
                intent = new Intent(SplashScreenActivity.this, MainActivity.class);

                while(!data)
                {
                    DBManip.getData(AppAPI.EVENT_DAYS, user.getEmail(), new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            EventDays eventDays = new EventDays(dataSnapshot);
                            eventDaysList = eventDays.getEventDays();
                            if(!eventDaysList.isEmpty())
                                data = true;
                            intent.putStringArrayListExtra(Constants.EVENT_DAYS_LIST, eventDaysList);
                            Log.v("EVENTS_LIST", ""+data);
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }
            }
            else
                intent = new Intent(SplashScreenActivity.this, StartupActivity.class);

            STRING_CONSTANTS.put(Keys.NAME, getString(R.string.name));
            STRING_CONSTANTS.put(Keys.USERNAME, getString(R.string.username));
            STRING_CONSTANTS.put(Keys.EMAIL, getString(R.string.email_address));
            STRING_CONSTANTS.put(Keys.PASSWORD, getString(R.string.password));
            STRING_CONSTANTS.put(Keys.RE_PASSWORD, getString(R.string.repeat_password));
            STRING_CONSTANTS.put(Keys.PHONE, getString(R.string.phone_number));
            STRING_CONSTANTS.put(Keys.AGE, getString(R.string.age));
            STRING_CONSTANTS.put(Keys.GENDER, getString(R.string.gender));
            STRING_CONSTANTS.put(Keys.CHILD_PHOTO, getString(R.string.my_child_s_photo));
            STRING_CONSTANTS.put(Keys.AUSTISM_SPECTRUM_SCORE, getString(R.string.autism_spectrum_score));
            STRING_CONSTANTS.put(Keys.CREATE_ACCOUNT, getString(R.string.create_account));
            STRING_CONSTANTS.put(Keys.NEXT_INFO, getString(R.string.fill_in_my_child_s_info));
            STRING_CONSTANTS.put(Keys.EMPTY_FIELD_ERROR, getString(R.string.this_field_cannot_be_empty));
            STRING_CONSTANTS.put(Keys.INVALID_PHONE_NUMBER_ERROR, getString(R.string.registration_failed_invalid_phone_number));
            STRING_CONSTANTS.put(Keys.QUOTA_EXCEEDED_ERROR, getString(R.string.registration_failed_quota_exceeded));
            STRING_CONSTANTS.put(Keys.WRONG_CODE_ERROR, getString(R.string.code_is_incorrect));
            STRING_CONSTANTS.put(Keys.PERSONAL_INFO, getString(R.string.personal_info));
            STRING_CONSTANTS.put(Keys.CHILD_INFO, getString(R.string.my_child_s_info));
            STRING_CONSTANTS.put(Keys.NOTES, getString(R.string.notes));
            STRING_CONSTANTS.put(Keys.EVENTS, getString(R.string.events));
            STRING_CONSTANTS.put(Keys.EVENT, getString(R.string.event));
            STRING_CONSTANTS.put(Keys.DATE, getString(R.string.date));
            STRING_CONSTANTS.put(Keys.TIME, getString(R.string.time));
            STRING_CONSTANTS.put(Keys.EVENT_DESCRIPTION, getString(R.string.event_description));
            STRING_CONSTANTS.put(Keys.RINGTONE, getString(R.string.ringtone));
            STRING_CONSTANTS.put(Keys.PROFILE, getString(R.string.my_profile));
            STRING_CONSTANTS.put(Keys.CHILD_PROFILE, getString(R.string.my_child_s_profile));
            STRING_CONSTANTS.put(Keys.KNOWLEDGE, getString(R.string.knowledge_base));
            STRING_CONSTANTS.put(Keys.FORUM, getString(R.string.forum));
            STRING_CONSTANTS.put(Keys.CONTACT, getString(R.string.contact));
            STRING_CONSTANTS.put(Keys.WAIT_MESSAGE, getString(R.string.please_wait_a_moment));
            STRING_CONSTANTS.put(Keys.SIGN_OUT, getString(R.string.sign_out));
            STRING_CONSTANTS.put(Keys.EVENT_REMINDER, getString(R.string.event_reminder));

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Log.v("SPLASHSCREEN", "starting next activity");
                    startActivity(intent);
                    finish();
                }
            }, 3000);
        }
    }
}
