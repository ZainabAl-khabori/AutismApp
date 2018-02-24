package worldontheotherside.wordpress.com.autismapp.Activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


import worldontheotherside.wordpress.com.autismapp.R;

public class SplashScreenActivity extends AppCompatActivity {

    private FirebaseUser user;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        user = FirebaseAuth.getInstance().getCurrentUser();

        new Loading().execute();
    }

    private class Loading extends AsyncTask<Void, Void, Void>
    {
        @Override
        protected void onPreExecute() { super.onPreExecute(); }

        @Override
        protected Void doInBackground(Void... voids) {
            if(user != null)
                intent = new Intent(SplashScreenActivity.this, MainActivity.class);
            else
            {
                intent = new Intent(SplashScreenActivity.this, StartupActivity.class);


            }

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
