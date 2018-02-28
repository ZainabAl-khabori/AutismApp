package worldontheotherside.wordpress.com.autismapp.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;

import worldontheotherside.wordpress.com.autismapp.R;

/**
 * Created by زينب on 2/25/2018.
 */

public class PersonalInfoFragment extends Fragment implements View.OnClickListener {

    public static final String TAB = "tab";

    private RadioButton radioButtonMale;
    private RadioButton radioButtonFemale;
    private Button buttonNext;

    private int pg;

    public static PersonalInfoFragment newPersonalInfoFragment(int page)
    {
        PersonalInfoFragment personalInfoFragment = new PersonalInfoFragment();
        Bundle args = new Bundle();

        args.putInt(TAB, page);
        personalInfoFragment.setArguments(args);

        return personalInfoFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        pg = getArguments().getInt(TAB, 0);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_personal_info, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        radioButtonMale = view.findViewById(R.id.radioButtonMale);
        radioButtonFemale = view.findViewById(R.id.radioButtonFemale);
        buttonNext = view.findViewById(R.id.buttonNext);

        buttonNext.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        // buttonNext onClick implementation
    }

    public boolean isMale() { return radioButtonMale.isChecked(); }

    public boolean isFemale() { return radioButtonFemale.isChecked(); }

    // set up methods to interact with anything that needs interacting with
    // Like radioButtons


}
