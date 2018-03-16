package worldontheotherside.wordpress.com.autismapp.Fragments;

import android.content.Context;
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
    private OnDialogShowingListener onDialogShowingListener;

    public interface OnVerifyListener { void onVerify(EditText editTextCode); }
    public interface OnDialogShowingListener { void onDialogShowing(boolean showing); }

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
    public void onAttach(Context context) {
        super.onAttach(context);

        try
        {
            onDialogShowingListener = (OnDialogShowingListener) context;
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        editTextCode = (EditText) view.findViewById(R.id.editTextCode);
        buttonVerify = (Button) view.findViewById(R.id.buttonVerify);
        buttonVerify.setOnClickListener(this);

        onDialogShowingListener.onDialogShowing(true);
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
