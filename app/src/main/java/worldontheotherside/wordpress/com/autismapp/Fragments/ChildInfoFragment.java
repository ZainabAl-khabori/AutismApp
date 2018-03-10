package worldontheotherside.wordpress.com.autismapp.Fragments;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Pattern;

import worldontheotherside.wordpress.com.autismapp.API.InfoItem;
import worldontheotherside.wordpress.com.autismapp.Adapters.ChildInfoRecyclerAdapter;
import worldontheotherside.wordpress.com.autismapp.Data.Constants;
import worldontheotherside.wordpress.com.autismapp.R;

public class ChildInfoFragment extends Fragment implements ChildInfoRecyclerAdapter.OnItemClickListener,
        PersonalInfoFragment.OnRecyclerReceivedListener {

    private static final String ID = "pager id";

    private int id;
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

    public ChildInfoFragment()
    {
        // Required empty public constructor
    }

    public static ChildInfoFragment newChildInfoFragment(int pagerId) {
        ChildInfoFragment fragment = new ChildInfoFragment();
        Bundle args = new Bundle();
        args.putInt(ID, pagerId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null)
            id = getArguments().getInt(ID, 0);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_child_info, container, false);
        viewPagerParent = (ViewPager) getActivity().findViewById(id);
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
                {
                    //
                }
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
            radioButtonMaleChild.setBackgroundColor(Color.RED);
            radioButtonFemaleChild.setBackgroundColor(Color.RED);
            allFilled = false;
        }
        else
        {
            radioButtonMaleChild.setBackgroundColor(Color.WHITE);
            radioButtonFemaleChild.setBackgroundColor(Color.WHITE);

            if(radioButtonMaleChild.isChecked())
                childInfo.put(Constants.GENDER, getString(R.string.male));
            else if(radioButtonFemaleChild.isChecked())
                childInfo.put(Constants.GENDER, getString(R.string.female));
        }

        viewHolder = recyclerViewChildInfo.findViewHolderForAdapterPosition(p);
        ChildInfoRecyclerAdapter.PhotoInputViewHolder h = (ChildInfoRecyclerAdapter.PhotoInputViewHolder) viewHolder;
        if(h.getTextViewUrl().getText().equals(Constants.CHILD_PHOTO))
            childInfo.put(Constants.CHILD_PHOTO, "");
        else
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
                correct = false;
            }
            else
            {
                textInputLayoutPassword.setErrorEnabled(false);

                if(!password.equals(rePassword))
                {
                    textInputLayoutRePassword.setError(getString(R.string.password_not_match));
                    textInputLayoutRePassword.setErrorEnabled(true);
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
            correct = false;
        }

        if(!correct)
            viewPagerParent.setCurrentItem(0);

        return correct;
    }

    private void createAccount()
    {
        auth.createUserWithEmailAndPassword(personalInfo.get(Constants.EMAIL), personalInfo.get(Constants.PASSWORD))
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(Task<AuthResult> task) {
                        if(task.isSuccessful())
                        {

                        }
                        else
                        {
                            Toast.makeText(getContext(), "Couldn't create an account", Toast.LENGTH_SHORT).show();
                            Log.v("ACCOUNT_CREATION", task.getException().getMessage());
                        }
                    }
                });
    }

    @Override
    public void onRecyclerReceived(RecyclerView recyclerView, ArrayList<InfoItem> items) {
        recyclerViewPersonalInfo = recyclerView;
        list = items;
    }
}
