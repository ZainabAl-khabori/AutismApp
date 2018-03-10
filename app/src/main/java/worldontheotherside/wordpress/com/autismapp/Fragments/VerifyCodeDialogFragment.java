package worldontheotherside.wordpress.com.autismapp.Fragments;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import worldontheotherside.wordpress.com.autismapp.R;

/**
 * Created by زينب on 3/6/2018.
 */

public class VerifyCodeDialogFragment extends DialogFragment implements View.OnClickListener {

    private EditText editTextCode;
    private Button buttonVerify;

    private OnVerifyListener onVerifyListener;

    public interface OnVerifyListener { void onVerify(EditText editTextCode); }

    public VerifyCodeDialogFragment()
    {
        //
    }

    public static VerifyCodeDialogFragment newInstance() { return new VerifyCodeDialogFragment(); }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_verification_code_dialog, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        editTextCode = (EditText) view.findViewById(R.id.editTextCode);
        buttonVerify = (Button) view.findViewById(R.id.buttonVerify);
    }

    public void setOnVerifyListener(OnVerifyListener onVerifyListener) { this.onVerifyListener = onVerifyListener; }

    @Override
    public void onClick(View view) {
        if(view.getId() == buttonVerify.getId())
        {
            if(onVerifyListener != null)
                onVerifyListener.onVerify(editTextCode);
        }
    }
}
