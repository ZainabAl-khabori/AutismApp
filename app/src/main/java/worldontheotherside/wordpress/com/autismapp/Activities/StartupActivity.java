package worldontheotherside.wordpress.com.autismapp.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.firebase.ui.auth.AuthUI;

import java.util.Arrays;
import java.util.List;

import worldontheotherside.wordpress.com.autismapp.R;

public class StartupActivity extends AppCompatActivity {

    private static final int RC_SIGN_IN = 123;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_startup);
    }

    public void loginAction(View v)
    {
        List<AuthUI.IdpConfig> providers = Arrays.asList(
                new AuthUI.IdpConfig.EmailBuilder().build(),
                new AuthUI.IdpConfig.PhoneBuilder().build(),
                new AuthUI.IdpConfig.GoogleBuilder().build(),
                new AuthUI.IdpConfig.FacebookBuilder().build());

        startActivityForResult(AuthUI.getInstance().createSignInIntentBuilder()
                .setAvailableProviders(providers)
                .build(), RC_SIGN_IN);
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
        Intent intent = new Intent(StartupActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
