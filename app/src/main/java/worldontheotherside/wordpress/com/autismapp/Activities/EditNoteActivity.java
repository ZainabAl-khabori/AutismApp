package worldontheotherside.wordpress.com.autismapp.Activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import worldontheotherside.wordpress.com.autismapp.API.Note;
import worldontheotherside.wordpress.com.autismapp.Data.Constants;
import worldontheotherside.wordpress.com.autismapp.Database.AppAPI;
import worldontheotherside.wordpress.com.autismapp.Database.DBManip;
import worldontheotherside.wordpress.com.autismapp.Fragments.ProgressDialogFragment;
import worldontheotherside.wordpress.com.autismapp.R;

public class EditNoteActivity extends AppCompatActivity {

    private ImageView imageViewAction;
    private CheckBox checkBoxImportant;
    private EditText editTextNote;

    private boolean newNote;

    private String email;
    private String date;
    private String time;
    private String noteBody;
    private boolean isImportant;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_note);

        imageViewAction = (ImageView) findViewById(R.id.imageViewAction);
        checkBoxImportant = (CheckBox) findViewById(R.id.checkBoxImportant);
        editTextNote = (EditText) findViewById(R.id.editTextNote);

        editTextNote.setImeOptions(EditorInfo.IME_ACTION_DONE);

        newNote = getIntent().getBooleanExtra(Constants.NEW_NOTE, false);
        email = FirebaseAuth.getInstance().getCurrentUser().getEmail();
        date = getIntent().getStringExtra(Constants.DAY_DATE);
        time = getIntent().getStringExtra(Constants.TIME_CREATED);

        if(newNote)
        {
            imageViewAction.setImageResource(R.drawable.ic_done);
            editTextNote.setEnabled(true);
            checkBoxImportant.setEnabled(true);
            editTextNote.requestFocus();
        }
        else
        {
            String note = getIntent().getStringExtra(Constants.NOTE_BODY);
            boolean important = getIntent().getBooleanExtra(Constants.IMPORTANT, false);

            checkBoxImportant.setChecked(important);
            editTextNote.setText(note);
        }
    }

    public void editSaveAction(View view)
    {
        if(!editTextNote.isEnabled())
        {
            imageViewAction.setImageResource(R.drawable.ic_done);
            editTextNote.setEnabled(true);
            checkBoxImportant.setEnabled(true);
            editTextNote.requestFocus();
        }
        else
        {
            imageViewAction.setImageResource(R.drawable.ic_edit);
            editTextNote.setEnabled(false);
            checkBoxImportant.setEnabled(false);
            isImportant = checkBoxImportant.isChecked();
            noteBody = editTextNote.getText().toString();
            new Loading().execute();
        }
    }

    public void deleteAction(View view)
    {
        DBManip.deleteData(AppAPI.NOTES, email, date + "/" + time, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                Log.v("NOTE_DELETION", "position: " + time + " successful");
                setResult(RESULT_OK, new Intent());
                finish();
            }
        });
    }

    public class Loading extends AsyncTask<Void, Void, Void>
    {
        private ProgressDialogFragment progressDialogFragment;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialogFragment = ProgressDialogFragment.newProgressDialogFragment(getString(R.string.please_wait_a_moment));
            progressDialogFragment.show(getSupportFragmentManager(), "PROGRESS_DIALOG");
            progressDialogFragment.setCancelable(false);
        }

        @Override
        protected Void doInBackground(Void... voids) {

            Note note = new Note();
            note.setDate(date);
            note.setTimeCreated(time);
            note.setImportant(isImportant);
            note.setText(noteBody);

            if(newNote)
            {
                if(!noteBody.isEmpty())
                {
                    DBManip.addData(AppAPI.NOTES, email, date + "/" + time, note, new DatabaseReference.CompletionListener() {
                        @Override
                        public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                            Log.v("NOTE_ADDED", "note added");
                        }
                    });
                }
            }
            else
            {
                DBManip.updateData(AppAPI.NOTES, email, date + "/" + time, note, new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                        Log.v("NOTE_ADDED", "note updated");
                    }
                });
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            progressDialogFragment.dismiss();
        }
    }
}
