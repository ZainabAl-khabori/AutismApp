package worldontheotherside.wordpress.com.autismapp.Database;

import android.net.Uri;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import worldontheotherside.wordpress.com.autismapp.Data.Constants;
import worldontheotherside.wordpress.com.autismapp.Fragments.VerifyCodeDialogFragment;

/**
 * Created by زينب on 2/23/2018.
 */

public class DBManip {

    private static DatabaseReference db;

    public static void getData(String url, ValueEventListener valueEventListener)
    {
        db = FirebaseDatabase.getInstance().getReferenceFromUrl(url);
        db.addValueEventListener(valueEventListener);
    }

    public static void getData(String url, String child, ValueEventListener valueEventListener)
    {
        db = FirebaseDatabase.getInstance().getReferenceFromUrl(url).child(child);
        db.addValueEventListener(valueEventListener);
    }

    public static void findData(String url, String field, String param, ValueEventListener valueEventListener)
    {
        db = FirebaseDatabase.getInstance().getReferenceFromUrl(url);
        Query q = db.orderByChild(field).equalTo(param);
        q.addValueEventListener(valueEventListener);
    }

    public static void addData(String url, String email, Object data, DatabaseReference.CompletionListener completionListener)
    {
        db = FirebaseDatabase.getInstance().getReferenceFromUrl(url);
        db.child(email).setValue(data, completionListener);
    }

    public static void updateData(String url, String email, Object data, DatabaseReference.CompletionListener completionListener)
    {
        db = FirebaseDatabase.getInstance().getReferenceFromUrl(url).child(email);
        db.setValue(data, completionListener);
    }

    public static void deleteData(String url, Object data, DatabaseReference.CompletionListener completionListener)
    {
        db = FirebaseDatabase.getInstance().getReferenceFromUrl(url);
        db.setValue(null, completionListener);
    }

    public static void deleteData(String url, String field, Object data, DatabaseReference.CompletionListener completionListener)
    {
        db = FirebaseDatabase.getInstance().getReferenceFromUrl(url).child(field);
        db.setValue(null, completionListener);
    }

    public interface OnDoneVerificationListener {
        void notifyActivity(PhoneAuthProvider.OnVerificationStateChangedCallbacks callbacks,
                            boolean verificationInProgress,
                            String phone);
    }

    private static boolean verificationInProgress = false;

    public static void updateUserProfile(final FirebaseUser user, final HashMap<String, String> data,
                                         final AppCompatActivity activity, OnCompleteListener onCompleteListener)
    {
        UserProfileChangeRequest.Builder builder = new UserProfileChangeRequest.Builder();

        if(data.containsKey(Constants.USERNAME))
            builder.setDisplayName(data.get(Constants.USERNAME));

        if(data.containsKey(Constants.DP))
            builder.setPhotoUri(Uri.parse(data.get(Constants.DP)));

        if(data.containsKey(Constants.EMAIL))
            user.updateEmail(data.get(Constants.EMAIL));

        if(data.containsKey(Constants.PASSWORD))
            user.updatePassword(data.get(Constants.PASSWORD));

        if(data.containsKey(Constants.PHONE))
        {
            final OnDoneVerificationListener onDoneVerificationListener = (OnDoneVerificationListener) activity;

            final PhoneAuthProvider.OnVerificationStateChangedCallbacks callbacks;
            callbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                String id;
                String code;

                @Override
                public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                    id = s;
                    FragmentManager manager = activity.getSupportFragmentManager();
                    final VerifyCodeDialogFragment dialog = VerifyCodeDialogFragment.newInstance();
                    dialog.setOnVerifyListener(new VerifyCodeDialogFragment.OnVerifyListener() {
                        @Override
                        public void onVerify(EditText editTextCode) {
                            if(editTextCode.getText().toString().isEmpty())
                                editTextCode.setError(Constants.EMPTY_FIELD_ERROR);
                            else
                            {
                                code = editTextCode.getText().toString();
                                dialog.dismiss();
                            }
                        }
                    });
                    dialog.show(manager, "VERIFY_DIALOG");
                }

                @Override
                public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
                    verificationInProgress = false;
                    onDoneVerificationListener.notifyActivity(this, verificationInProgress, data.get(Constants.PHONE));
                    user.linkWithCredential(phoneAuthCredential).addOnCompleteListener(activity,
                            new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(Task<AuthResult> task) {
                            FirebaseAuthUserCollisionException e = (FirebaseAuthUserCollisionException)task.getException();
                            PhoneAuthCredential credential = (PhoneAuthCredential)e.getUpdatedCredential();
                            if(!task.isSuccessful() && credential != null)
                                user.linkWithCredential(credential);
                        }
                    });
                }

                @Override
                public void onVerificationFailed(FirebaseException e) {
                    verificationInProgress = false;

                    if(e instanceof FirebaseAuthInvalidCredentialsException)
                        Toast.makeText(activity, Constants.INVALID_PHONE_NUMBER_ERROR, Toast.LENGTH_SHORT).show();
                    else if(e instanceof FirebaseTooManyRequestsException)
                        Toast.makeText(activity, Constants.QUOTA_EXCEEDED_ERROR, Toast.LENGTH_SHORT).show();
                }
            };

            PhoneAuthProvider.getInstance().verifyPhoneNumber(data.get(Constants.PHONE), 90, TimeUnit.SECONDS, activity, callbacks);
            verificationInProgress = true;
            onDoneVerificationListener.notifyActivity(callbacks, verificationInProgress, data.get(Constants.PHONE));
        }

        UserProfileChangeRequest request = builder.build();
        user.updateProfile(request).addOnCompleteListener(onCompleteListener);
    }
}
