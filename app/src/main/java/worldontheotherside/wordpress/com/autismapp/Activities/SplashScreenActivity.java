package worldontheotherside.wordpress.com.autismapp.Activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;
import java.util.List;

import worldontheotherside.wordpress.com.autismapp.R;

public class SplashScreenActivity extends AppCompatActivity {

    private FirebaseUser user;
    private Intent intent;

    private static final int RC_SIGN_IN = 123;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        user = FirebaseAuth.getInstance().getCurrentUser();

        new Loading().execute();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == RC_SIGN_IN){
            if(resultCode == RESULT_OK){
                login();
            }
            if(resultCode == RESULT_CANCELED){
                Log.v("SIGNIN", "Signin failed");
            }
            return;
        }
        Log.v("SIGNIN", "unknown response");
    }

    private void login()
    {
        intent = new Intent(SplashScreenActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
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

/*                List<AuthUI.IdpConfig> providers = Arrays.asList(
                        new AuthUI.IdpConfig.EmailBuilder().build(),
                        new AuthUI.IdpConfig.PhoneBuilder().build(),
                        new AuthUI.IdpConfig.GoogleBuilder().build(),
                        new AuthUI.IdpConfig.FacebookBuilder().build());

                startActivityForResult(AuthUI.getInstance().createSignInIntentBuilder()
                        .setAvailableProviders(providers)
                        .setLogo(R.drawable.ic_help)
                        .build(), RC_SIGN_IN);*/
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
