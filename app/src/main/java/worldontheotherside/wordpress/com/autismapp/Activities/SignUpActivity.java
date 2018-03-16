package worldontheotherside.wordpress.com.autismapp.Activities;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import worldontheotherside.wordpress.com.autismapp.API.InfoItem;
import worldontheotherside.wordpress.com.autismapp.Adapters.TabsPagerAdapter;
import worldontheotherside.wordpress.com.autismapp.Data.Constants;
import worldontheotherside.wordpress.com.autismapp.Data.Keys;
import worldontheotherside.wordpress.com.autismapp.Database.DBManip;
import worldontheotherside.wordpress.com.autismapp.Fragments.PersonalInfoFragment;
import worldontheotherside.wordpress.com.autismapp.Fragments.VerifyCodeDialogFragment;
import worldontheotherside.wordpress.com.autismapp.R;

public class SignUpActivity extends AppCompatActivity implements PersonalInfoFragment.OnRecyclerReceivedListener,
        DBManip.OnDoneVerificationListener, VerifyCodeDialogFragment.OnDialogShowingListener, DBManip.OnExitListener {

    private final int RC_SIGN_IN = 123;

    private TabsPagerAdapter adapter;

    private boolean verificationInProgress = false;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks callbacks;
    private String phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        TabLayout tabLayoutTabs = (TabLayout) findViewById(R.id.tabLayoutTabs);
        ViewPager viewPagerTabs = (ViewPager) findViewById(R.id.viewPagerTabs);
        LinearLayout linearLayoutProgressBar = (LinearLayout) findViewById(R.id.linearLayoutProgressBar);

        adapter = new TabsPagerAdapter(getSupportFragmentManager(), viewPagerTabs.getId(), linearLayoutProgressBar.getId());
        viewPagerTabs.setAdapter(adapter);
        tabLayoutTabs.setupWithViewPager(viewPagerTabs);
    }

    @Override
    public void onStart() {
        super.onStart();

        if(verificationInProgress)
        {
            PhoneAuthProvider.getInstance().verifyPhoneNumber(phone, 90, TimeUnit.SECONDS, this, callbacks);
            verificationInProgress = true;
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(Keys.VERIFICATION_IN_PROGRESS, verificationInProgress);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        verificationInProgress = savedInstanceState.getBoolean(Keys.VERIFICATION_IN_PROGRESS);
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

    @Override
    public void onRecyclerReceived(RecyclerView recyclerView, ArrayList<InfoItem> items) {
        adapter.onRecyclerReceived(recyclerView, items);
    }

    @Override
    public void notifyActivity(PhoneAuthProvider.OnVerificationStateChangedCallbacks callbacks, boolean verificationInProgress,
                               String phone) {
        this.verificationInProgress = verificationInProgress;
        this.callbacks = callbacks;
        this.phone = phone;
    }

    @Override
    public void onDialogShowing(boolean showing) {
        Log.v("DIALOG_SHOWING_ACTIVITY", "" + showing);
        adapter.onDialogShowing(showing);
    }

    @Override
    public void onExit(boolean done) {
        adapter.onExit(done);
    }

    public void loginAction(View view)
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

    private void login()
    {
        Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
