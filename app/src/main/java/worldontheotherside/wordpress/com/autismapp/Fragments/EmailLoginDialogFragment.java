package worldontheotherside.wordpress.com.autismapp.Fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.DialogFragment;
import android.view.View;

import worldontheotherside.wordpress.com.autismapp.R;

/**
 * Created by زينب on 3/17/2018.
 */

public class EmailLoginDialogFragment extends DialogFragment {

    private OnButtonClickedListener onButtonClickedListener;

    public interface OnButtonClickedListener
    {
        void onLoginButtonClicked(TextInputLayout emailLayout, TextInputLayout passwordLayout, String email, String password);
        void onCancelButtonClicked();
    }

    public EmailLoginDialogFragment()
    {
        //
    }

    public static EmailLoginDialogFragment newInstance() { return new EmailLoginDialogFragment(); }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        View view = getActivity().getLayoutInflater().inflate(R.layout.fragment_email_login_dialog, null);
        final TextInputLayout textInputLayoutEmail = (TextInputLayout) view.findViewById(R.id.textInputLayoutEmail);
        final TextInputLayout textInputLayoutPassword = (TextInputLayout) view.findViewById(R.id.textInputLayoutPassword);
        final TextInputEditText textInputEditTextEmail = (TextInputEditText) view.findViewById(R.id.editTextEmail);
        final TextInputEditText inputEditTextPassword = (TextInputEditText) view.findViewById(R.id.editTextPassword);

        builder.setView(view);
        builder.setTitle(R.string.login_with_email_address);
        builder.setPositiveButton(R.string.login, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String email = textInputEditTextEmail.getText().toString();
                String password = inputEditTextPassword.getText().toString();
                onButtonClickedListener.onLoginButtonClicked(textInputLayoutEmail, textInputLayoutPassword, email, password);
            }
        });
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                onButtonClickedListener.onCancelButtonClicked();
            }
        });

        return builder.create();
    }

    public void setOnButtonClickedListener(OnButtonClickedListener onButtonClickedListener) {
        this.onButtonClickedListener = onButtonClickedListener;
    }
}
