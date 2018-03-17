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
import worldontheotherside.wordpress.com.autismapp.Fragments.LoginOptionsDialogFragment;
import worldontheotherside.wordpress.com.autismapp.Fragments.PersonalInfoFragment;
import worldontheotherside.wordpress.com.autismapp.Fragments.VerifyCodeDialogFragment;
import worldontheotherside.wordpress.com.autismapp.R;

public class SignUpActivity extends AppCompatActivity implements PersonalInfoFragment.OnRecyclerReceivedListener,
        DBManip.OnDoneVerificationListener, VerifyCodeDialogFragment.OnDialogShowingListener, DBManip.OnExitListener {

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
        LoginOptionsDialogFragment dialogFragment = LoginOptionsDialogFragment.newInstance();
        dialogFragment.show(getSupportFragmentManager(), "LOGIN_OPTIONS");
    }
}
