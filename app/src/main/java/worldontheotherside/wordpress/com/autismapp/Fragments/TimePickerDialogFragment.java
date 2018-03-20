package worldontheotherside.wordpress.com.autismapp.Fragments;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

import java.util.Calendar;

/**
 * Created by زينب on 3/19/2018.
 */

public class TimePickerDialogFragment extends DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Calendar calendar = Calendar.getInstance();
        int hr = calendar.get(Calendar.HOUR_OF_DAY);
        int min = calendar.get(Calendar.MINUTE);

        TimePickerDialog.OnTimeSetListener listener = (TimePickerDialog.OnTimeSetListener) getActivity();

        return new TimePickerDialog(getActivity(), listener, hr, min, false);
    }
}
