package worldontheotherside.wordpress.com.autismapp.Fragments;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.design.widget.TextInputLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import worldontheotherside.wordpress.com.autismapp.API.EventDays;
import worldontheotherside.wordpress.com.autismapp.Activities.MainActivity;
import worldontheotherside.wordpress.com.autismapp.Data.Constants;
import worldontheotherside.wordpress.com.autismapp.Database.AppAPI;
import worldontheotherside.wordpress.com.autismapp.Database.DBManip;
import worldontheotherside.wordpress.com.autismapp.R;

/**
 * Created by زينب on 3/16/2018.
 */

public class LoginOptionsDialogFragment extends BottomSheetDialogFragment implements View.OnClickListener,
        EmailLoginDialogFragment.OnButtonClickedListener {

    private final int RC_SIGN_IN = 123;

    private EmailLoginDialogFragment emailLoginDialogFragment;

    private Intent intent;

    private String email;
    private String password;

    private ArrayList<String> eventDaysList;

    public LoginOptionsDialogFragment()
    {
        //
    }

    public static LoginOptionsDialogFragment newInstance() { return new LoginOptionsDialogFragment(); }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_login_options_dialog_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        view.findViewById(R.id.linearLayoutEmail).setOnClickListener(this);
        view.findViewById(R.id.linearLayoutOthers).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.linearLayoutEmail)
        {
            emailLoginDialogFragment = EmailLoginDialogFragment.newInstance();
            emailLoginDialogFragment.setOnButtonClickedListener(this);
            emailLoginDialogFragment.show(getFragmentManager(), "EMAIL_LOGIN");
            emailLoginDialogFragment.setCancelable(false);
        }
        else if(view.getId() == R.id.linearLayoutOthers)
        {
            List<AuthUI.IdpConfig> providers = Arrays.asList(
                    new AuthUI.IdpConfig.PhoneBuilder().build(),
                    new AuthUI.IdpConfig.GoogleBuilder().build(),
                    new AuthUI.IdpConfig.FacebookBuilder().build());

            startActivityForResult(AuthUI.getInstance().createSignInIntentBuilder()
                    .setAvailableProviders(providers)
                    .build(), RC_SIGN_IN);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == RC_SIGN_IN){
            if(resultCode == getActivity().RESULT_OK){
                login();
            }
            if(resultCode == getActivity().RESULT_CANCELED){
                Log.v("SIGNIN", "Signin failed");
            }
            return;
        }
        Log.v("SIGNIN", "unknown response");
    }

    private void login()
    {
        Intent intent = new Intent(getActivity(), MainActivity.class);
        startActivity(intent);
        getActivity().finish();
    }

    @Override
    public void onLoginButtonClicked(TextInputLayout emailLayout, TextInputLayout passwordLayout, String email, String password) {

        Log.v("LOGIN_BUTTON", "email: " + email + "     password: " + password);

        if(email.isEmpty() || password.isEmpty())
        {
            if(email.isEmpty())
            {
                emailLayout.setError(getString(R.string.this_field_cannot_be_empty));
                emailLayout.setErrorEnabled(true);
            }

            if(password.isEmpty())
            {
                passwordLayout.setError(getString(R.string.this_field_cannot_be_empty));
                passwordLayout.setErrorEnabled(true);
            }
        }
        else
        {
            emailLayout.setErrorEnabled(false);
            passwordLayout.setErrorEnabled(false);

            this.email = email;
            this.password = password;

            new LoggingIn().execute();
        }
    }

    @Override
    public void onCancelButtonClicked() {
        emailLoginDialogFragment.dismiss();
    }

    private class LoggingIn extends AsyncTask<Void, Void, Void>
    {
        private ProgressDialogFragment progressDialogFragment;
        private boolean done = false;

        private boolean data = false;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            emailLoginDialogFragment.dismiss();

            progressDialogFragment = ProgressDialogFragment.newProgressDialogFragment(getString(R.string.logging_in));
            progressDialogFragment.show(getFragmentManager(), "PROGRESS_DIALOG");
            progressDialogFragment.setCancelable(false);
        }

        @Override
        protected Void doInBackground(Void... voids) {

            intent = new Intent(getContext(), MainActivity.class);

            emailLogin();

            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            progressDialogFragment.dismiss();

            if(done)
            {
                startActivity(intent);
                getActivity().finish();
            }
        }

        private void emailLogin()
        {
            final FirebaseAuth auth = FirebaseAuth.getInstance();
            auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(Task<AuthResult> task) {
                    if(task.isSuccessful())
                    {
                        FirebaseUser user = auth.getCurrentUser();
                        getUserData(user);
                    }
                    else
                    {
                        Log.v("LOGIN_FAILED", task.getException().getMessage());
                        Toast.makeText(getContext(), R.string.login_failed, Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

        private void getUserData(FirebaseUser user)
        {
/*            while(!data)
            {*/
                DBManip.getData(AppAPI.EVENT_DAYS, user.getEmail(), new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        EventDays eventDays = new EventDays(dataSnapshot);
                        eventDaysList = eventDays.getEventDays();
                        if(!eventDaysList.isEmpty())
                            data = true;
                        intent.putStringArrayListExtra(Constants.EVENT_DAYS_LIST, eventDaysList);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
/*            }*/

            done = true;
        }
    }
}
