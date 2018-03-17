package worldontheotherside.wordpress.com.autismapp.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.firebase.ui.auth.AuthUI;

import java.util.Arrays;
import java.util.List;

import worldontheotherside.wordpress.com.autismapp.Fragments.LoginOptionsDialogFragment;
import worldontheotherside.wordpress.com.autismapp.R;

public class StartupActivity extends AppCompatActivity {



    public static StartupActivity startupActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_startup);

        startupActivity = this;
    }

    public void loginAction(View v)
    {
        LoginOptionsDialogFragment dialogFragment = LoginOptionsDialogFragment.newInstance();
        dialogFragment.show(getSupportFragmentManager(), "LOGIN_OPTIONS");
    }

    public void signUpAction(View view)
    {
        Intent intent = new Intent(this, SignUpActivity.class);
        startActivity(intent);
    }
}
