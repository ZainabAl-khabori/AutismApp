package worldontheotherside.wordpress.com.autismapp.Fragments;

import android.os.Bundle;
import android.support.design.widget.BottomSheetDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import worldontheotherside.wordpress.com.autismapp.R;

/**
 * Created by زينب on 3/1/2018.
 */

public class DiagnoseOptionsDialogFragment extends BottomSheetDialogFragment implements View.OnClickListener {

    public DiagnoseOptionsDialogFragment()
    {
        //
    }

    public static DiagnoseOptionsDialogFragment newInstance()
    {
        DiagnoseOptionsDialogFragment fragment = new DiagnoseOptionsDialogFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_diagnose_options_dialog, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        view.findViewById(R.id.linearLayoutWebDiagnose).setOnClickListener(this);
        view.findViewById(R.id.linearLayoutVisitHospital).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        // TODO: fill in the actions for when clicking on these two options
    }
}
