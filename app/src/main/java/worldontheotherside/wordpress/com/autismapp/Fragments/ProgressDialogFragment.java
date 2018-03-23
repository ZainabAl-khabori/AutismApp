package worldontheotherside.wordpress.com.autismapp.Fragments;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import worldontheotherside.wordpress.com.autismapp.Data.Keys;
import worldontheotherside.wordpress.com.autismapp.R;

/**
 * Created by زينب on 3/14/2018.
 */

public class ProgressDialogFragment extends DialogFragment {

    private TextView textViewMessage;

    public ProgressDialogFragment()
    {
        //
    }

    public static ProgressDialogFragment newProgressDialogFragment(String message)
    {
        ProgressDialogFragment dialog = new ProgressDialogFragment();
        Bundle args = new Bundle();
        args.putString(Keys.PROGRESS_DIALOG_MESSAGE, message);
        dialog.setArguments(args);
        return dialog;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getDialog().setCanceledOnTouchOutside(false);
        return inflater.inflate(R.layout.fragment_progress_dialog, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        textViewMessage = (TextView) view.findViewById(R.id.textViewMessage);
        textViewMessage.setText(getArguments().getString(Keys.PROGRESS_DIALOG_MESSAGE));
    }

    @Override
    public void show(FragmentManager manager, String tag) {
        try
        {
            FragmentTransaction fragmentTransaction = manager.beginTransaction();
            fragmentTransaction.add(this, tag);
            fragmentTransaction.commit();
        }
        catch (IllegalStateException e)
        {
            Log.v("Exception", e.getMessage());
        }
    }
}
