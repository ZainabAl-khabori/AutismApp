package worldontheotherside.wordpress.com.autismapp.Fragments;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

import worldontheotherside.wordpress.com.autismapp.API.Child;
import worldontheotherside.wordpress.com.autismapp.API.InfoItem;
import worldontheotherside.wordpress.com.autismapp.Activities.MainActivity;
import worldontheotherside.wordpress.com.autismapp.Adapters.ChildInfoRecyclerAdapter;
import worldontheotherside.wordpress.com.autismapp.Data.Constants;
import worldontheotherside.wordpress.com.autismapp.Data.Keys;
import worldontheotherside.wordpress.com.autismapp.Database.AppAPI;
import worldontheotherside.wordpress.com.autismapp.Database.DBManip;
import worldontheotherside.wordpress.com.autismapp.R;

public class ChildInfoFragment extends Fragment implements ChildInfoRecyclerAdapter.OnItemClickListener,
        PersonalInfoFragment.OnRecyclerReceivedListener, VerifyCodeDialogFragment.OnDialogShowingListener, DBManip.OnExitListener {

    private static final String PAGER_ID = "pager id";
    private static final String LINEAR_ID = "linear id";

    private int pagerId;
    private int linearId;
    private ArrayList<InfoItem> items;
    private ArrayList<InfoItem> list;
    private ViewPager viewPagerParent;
    private RecyclerView recyclerViewChildInfo;
    private RecyclerView recyclerViewPersonalInfo;

    private CircularImageView imageViewDp;
    private TextView textViewUri;

    private ArrayList<TextInputEditText> editTextsPersonal;
    private HashMap<String, String> personalInfo;
    private HashMap<String, String> childInfo;
    private TextInputLayout textInputLayoutPassword;
    private TextInputLayout textInputLayoutRePassword;

    private int passIndex = 0;
    private int rePassIndex = 1;
    private FirebaseAuth auth;

    private boolean done;

    public ChildInfoFragment()
    {
        // Required empty public constructor
    }

    public static ChildInfoFragment newChildInfoFragment(int pagerId, int linearId) {
        ChildInfoFragment fragment = new ChildInfoFragment();
        Bundle args = new Bundle();
        args.putInt(PAGER_ID, pagerId);
        args.putInt(LINEAR_ID, linearId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null)
        {
            pagerId = getArguments().getInt(PAGER_ID, 0);
            linearId = getArguments().getInt(LINEAR_ID, 0);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_child_info, container, false);
        viewPagerParent = (ViewPager) getActivity().findViewById(pagerId);
        items = new ArrayList<>();

        items.add(new InfoItem(Constants.NAME, Constants.CHILD_INFO_RECYCLER_TEXT_INPUT, R.drawable.ic_username));
        items.add(new InfoItem(Constants.AGE, Constants.CHILD_INFO_RECYCLER_TEXT_INPUT, R.drawable.ic_age));
        items.add(new InfoItem(Constants.GENDER, Constants.CHILD_INFO_RECYCLER_GENDER, R.drawable.ic_gender));
        items.add(new InfoItem(Constants.CHILD_PHOTO, Constants.CHILD_INFO_RECYCLER_PHOTO_INPUT, R.drawable.ic_dp));
        items.add(new InfoItem(Constants.AUTISM_SPECTRUM_SCORE, Constants.CHILD_INFO_RECYCLER_TEXT_INPUT, R.drawable.ic_score));
        items.add(new InfoItem(Constants.CREATE_ACCOUNT, Constants.CHILD_INFO_RECYCLER_BUTTON, R.drawable.ic_help));

        recyclerViewChildInfo = (RecyclerView) view.findViewById(R.id.recyclerViewChildInfo);
        ChildInfoRecyclerAdapter adapter = new ChildInfoRecyclerAdapter(getContext(), items);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        ChildInfoRecyclerAdapter.OnItemClickListener onItemClickListener = this;

        recyclerViewChildInfo.setAdapter(adapter);
        adapter.setOnItemClickListener(onItemClickListener);
        recyclerViewChildInfo.setLayoutManager(layoutManager);

        auth = FirebaseAuth.getInstance();

        return view;
    }

    @Override
    public void onClick(View view, int position, RecyclerView.ViewHolder holder) {
        if(view.getId() == R.id.buttonBrowse)
        {
            ChildInfoRecyclerAdapter.PhotoInputViewHolder viewHolder = (ChildInfoRecyclerAdapter.PhotoInputViewHolder) holder;

            textViewUri = viewHolder.getTextViewUrl();
            imageViewDp = viewHolder.getImageViewPhoto();

            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent, 0);
        }

        if(view.getId() == R.id.buttonCreateAccount)
        {
            if(allFiledPersonal() && allFilledChild())
            {
                if(checkPassword())
                    new CreatingAccount().execute();
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == getActivity().RESULT_OK)
        {
            textViewUri.setText(data.getData().toString());
            Picasso.with(getContext()).load(data.getData()).into(imageViewDp);
        }
    }

    @Override
    public void onRecyclerReceived(RecyclerView recyclerView, ArrayList<InfoItem> items) {
        recyclerViewPersonalInfo = recyclerView;
        list = items;
    }

    @Override
    public void onDialogShowing(boolean showing) {
        Log.v("DIALOG_SHOWING_CHILD", "" + showing);
        done = showing;
    }

    private boolean allFiledPersonal()
    {
        ArrayList<TextInputLayout> layouts = new ArrayList<>();
        editTextsPersonal = new ArrayList<>();
        personalInfo = new HashMap<>();
        boolean allFilled = true;

        for(int i = 0; i < list.size(); i++)
        {
            if(list.get(i).getType() == Constants.CHILD_INFO_RECYCLER_TEXT_INPUT)
            {
                RecyclerView.ViewHolder viewHolder = recyclerViewPersonalInfo.findViewHolderForAdapterPosition(i);
                ChildInfoRecyclerAdapter.TextInputViewHolder holder = (ChildInfoRecyclerAdapter.TextInputViewHolder) viewHolder;
                layouts.add(holder.getTextInputLayoutTextInput());
                editTextsPersonal.add(holder.getEditTextInput());
                personalInfo.put(list.get(i).getTitle(), holder.getEditTextInput().getText().toString());
            }
        }

        for(int i = 0; i < layouts.size(); i++)
        {
            if(layouts.get(i).getHint().toString().equals(Constants.PASSWORD))
            {
                textInputLayoutPassword = layouts.get(i);
                passIndex = i;
            }
            if(layouts.get(i).getHint().toString().equals(Constants.RE_PASSWORD))
            {
                textInputLayoutRePassword = layouts.get(i);
                rePassIndex = i;
            }

            if(editTextsPersonal.get(i).getText().toString().isEmpty())
            {
                layouts.get(i).setError(getString(R.string.this_field_cannot_be_empty));
                layouts.get(i).setErrorEnabled(true);
                editTextsPersonal.get(i).requestFocus();
                allFilled = false;
            }
            else
                layouts.get(i).setErrorEnabled(false);
        }

        if(!allFilled)
            viewPagerParent.setCurrentItem(0);

        return allFilled;
    }

    private boolean allFilledChild()
    {
        ArrayList<TextInputLayout> layouts = new ArrayList<>();
        ArrayList<TextInputEditText> editTextsChild = new ArrayList<>();
        childInfo = new HashMap<>();
        int g = 2, p = 3;
        boolean allFilled = true;

        for(int i = 0; i < items.size(); i++)
        {
            if(items.get(i).getType() == Constants.CHILD_INFO_RECYCLER_TEXT_INPUT)
            {
                RecyclerView.ViewHolder viewHolder = recyclerViewChildInfo.findViewHolderForAdapterPosition(i);
                ChildInfoRecyclerAdapter.TextInputViewHolder holder = (ChildInfoRecyclerAdapter.TextInputViewHolder) viewHolder;
                layouts.add(holder.getTextInputLayoutTextInput());
                editTextsChild.add(holder.getEditTextInput());
                childInfo.put(items.get(i).getTitle(), holder.getEditTextInput().getText().toString());
            }
            else if(items.get(i).getType() == Constants.CHILD_INFO_RECYCLER_GENDER)
                g = i;
        }

        for(int i = 0; i < layouts.size(); i++)
        {
            if(editTextsChild.get(i).getText().toString().isEmpty())
            {
                layouts.get(i).setError(getString(R.string.this_field_cannot_be_empty));
                layouts.get(i).setErrorEnabled(true);
                editTextsChild.get(i).requestFocus();
                allFilled = false;
            }
            else
                layouts.get(i).setErrorEnabled(false);
        }

        RecyclerView.ViewHolder viewHolder = recyclerViewChildInfo.findViewHolderForAdapterPosition(g);
        ChildInfoRecyclerAdapter.GenderViewHolder holder = (ChildInfoRecyclerAdapter.GenderViewHolder) viewHolder;
        RadioButton radioButtonMaleChild = holder.getRadioButtonMale();
        RadioButton radioButtonFemaleChild = holder.getRadioButtonFemale();
        if(!radioButtonMaleChild.isChecked() && !radioButtonFemaleChild.isChecked())
        {
            radioButtonMaleChild.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_male_red, 0, 0, 0);
            radioButtonFemaleChild.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_female_red, 0, 0, 0);
            allFilled = false;
        }
        else
        {
            radioButtonMaleChild.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_male, 0, 0, 0);
            radioButtonFemaleChild.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_female, 0, 0, 0);

            if(radioButtonMaleChild.isChecked())
                childInfo.put(Constants.GENDER, getString(R.string.male));
            else if(radioButtonFemaleChild.isChecked())
                childInfo.put(Constants.GENDER, getString(R.string.female));
        }

        viewHolder = recyclerViewChildInfo.findViewHolderForAdapterPosition(p);
        ChildInfoRecyclerAdapter.PhotoInputViewHolder h = (ChildInfoRecyclerAdapter.PhotoInputViewHolder) viewHolder;
        if(!h.getTextViewUrl().getText().equals(Constants.CHILD_PHOTO))
            childInfo.put(Constants.CHILD_PHOTO, h.getTextViewUrl().getText().toString());

        return allFilled;
    }

    private boolean checkPassword()
    {
        boolean correct = true;
        String password = editTextsPersonal.get(passIndex).getText().toString();
        String rePassword = editTextsPersonal.get(rePassIndex).getText().toString();

        Pattern patternDigits = Pattern.compile("[0-9]+");
        Pattern patternLower = Pattern.compile("[a-z]+");
        Pattern patternUpper = Pattern.compile("[A-Z]+");

        if(password.length() > 7)
        {
            textInputLayoutPassword.setErrorEnabled(false);

            if(!patternDigits.matcher(password).find() || !patternLower.matcher(password).find()
                    || !patternUpper.matcher(password).find())
            {
                textInputLayoutPassword.setError(getString(R.string.password_content));
                textInputLayoutPassword.setErrorEnabled(true);
                editTextsPersonal.get(passIndex).requestFocus();
                correct = false;
            }
            else
            {
                textInputLayoutPassword.setErrorEnabled(false);

                if(!password.equals(rePassword))
                {
                    textInputLayoutRePassword.setError(getString(R.string.password_not_match));
                    textInputLayoutRePassword.setErrorEnabled(true);
                    editTextsPersonal.get(rePassIndex).requestFocus();
                    correct = false;
                }
                else
                    textInputLayoutRePassword.setErrorEnabled(false);
            }
        }
        else
        {
            textInputLayoutPassword.setError(getString(R.string.password_length));
            textInputLayoutPassword.setErrorEnabled(true);
            editTextsPersonal.get(passIndex).requestFocus();
            correct = false;
        }

        if(!correct)
            viewPagerParent.setCurrentItem(0);

        return correct;
    }

    @Override
    public void onExit(boolean done) {
        this.done = done;
    }

    private class CreatingAccount extends AsyncTask<String, HashMap<String, Object>, String>
    {
        private ProgressDialogFragment progressDialogFragment;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            Log.v("PRE_EXECUTE", "on pre execute");

            progressDialogFragment = ProgressDialogFragment.newProgressDialogFragment(getString(R.string.creating_account));
            progressDialogFragment.show(getFragmentManager(), "PROGRESS_DIALOG");
        }

        @Override
        protected String doInBackground(String... strings) {

            Log.v("IN_BACKGROUND", "in background");

            createAccount();

            while(!done)
            {
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            return null;
        }

        @Override
        protected void onPostExecute(String aString) {
            super.onPostExecute(aString);

            Log.v("POST_EXECUTE", "on post execute");

            progressDialogFragment.dismiss();
        }

        private void createAccount()
        {
            auth.createUserWithEmailAndPassword(personalInfo.get(Constants.EMAIL), personalInfo.get(Constants.PASSWORD))
                    .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(Task<AuthResult> task) {
                            if(task.isSuccessful())
                            {
                                DBManip.updateUserProfile(auth.getCurrentUser(), personalInfo,
                                        (AppCompatActivity)getActivity(), new OnCompleteListener() {
                                            @Override
                                            public void onComplete(Task task) {
                                                createChildProfile();
                                            }
                                        });
                            }
                            else
                            {
                                Toast.makeText(getContext(), R.string.couldn_t_create_an_account, Toast.LENGTH_SHORT).show();
                                Log.v("ACCOUNT_CREATION", task.getException().getMessage());
                            }
                        }
                    });
        }

        private void createChildProfile()
        {
            final Child child = new Child();
            child.setName(childInfo.get(Constants.NAME));
            child.setAge(Integer.valueOf(childInfo.get(Constants.AGE)));
            child.setAutismSpectrumScore(Integer.valueOf(childInfo.get(Constants.AUTISM_SPECTRUM_SCORE)));
            child.setGender(childInfo.get(Constants.GENDER));
            if(childInfo.containsKey(Constants.CHILD_PHOTO))
            {
                FirebaseStorage storage = FirebaseStorage.getInstance();
                Uri path = Uri.parse(childInfo.get(Constants.CHILD_PHOTO));

                storage.getReference().child("dp/" + path.getLastPathSegment()).putFile(path)
                        .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                child.setPhoto(taskSnapshot.getDownloadUrl().toString());
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(Exception e) {
                                Log.v("CHILD_DP_UPLOAD_ERROR", e.getMessage());
                            }
                        });
            }
            else
                child.setPhoto(Constants.NO_DP_LINK);

            DBManip.addData(AppAPI.CHILDREN, personalInfo.get(Constants.EMAIL), child, new DatabaseReference.CompletionListener() {
                @Override
                public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                    Log.v("CHILD_INFO_ADDED", "child info added");
                }
            });
        }
    }
}
