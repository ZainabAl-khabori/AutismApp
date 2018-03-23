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
import com.google.gson.Gson;

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
    private CheckBox checkBoxFunctional;
    private CheckBox checkBoxSensory;
    private CheckBox checkBoxAcademic;
    private CheckBox checkBoxBehavioral;
    private EditText editTextNote;

    private boolean newNote;

    private String email;
    private String date;
    private String calendarJSON;
    private String time;
    private String noteBody;
    private boolean isImportant;
    private boolean functional;
    private boolean sensory;
    private boolean academic;
    private boolean behavioral;

    private String month;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_note);

        imageViewAction = (ImageView) findViewById(R.id.imageViewAction);
        checkBoxImportant = (CheckBox) findViewById(R.id.checkBoxImportant);
        checkBoxFunctional = (CheckBox) findViewById(R.id.checkBoxFunctionalSkills);
        checkBoxSensory = (CheckBox) findViewById(R.id.checkBoxSensorySkills);
        checkBoxAcademic = (CheckBox) findViewById(R.id.checkBoxAcademicSkills);
        checkBoxBehavioral = (CheckBox) findViewById(R.id.checkBoxBehavioralSkills);
        editTextNote = (EditText) findViewById(R.id.editTextNote);

        editTextNote.setImeOptions(EditorInfo.IME_ACTION_DONE);

        newNote = getIntent().getBooleanExtra(Constants.NEW_NOTE, false);
        email = FirebaseAuth.getInstance().getCurrentUser().getEmail();
        date = getIntent().getStringExtra(Constants.DAY_DATE);
        calendarJSON = getIntent().getStringExtra(Constants.CALENDAR);
        time = getIntent().getStringExtra(Constants.TIME_CREATED);

        Date date = new Gson().fromJson(calendarJSON, Date.class);
        month = new SimpleDateFormat("MMMM", Locale.getDefault()).format(date);

        if(newNote)
        {
            imageViewAction.setImageResource(R.drawable.ic_done);
            editTextNote.setEnabled(true);
            checkBoxImportant.setEnabled(true);
            checkBoxFunctional.setEnabled(true);
            checkBoxAcademic.setEnabled(true);
            checkBoxSensory.setEnabled(true);
            checkBoxBehavioral.setEnabled(true);
            editTextNote.requestFocus();
        }
        else
        {
            String note = getIntent().getStringExtra(Constants.NOTE_BODY);
            boolean important = getIntent().getBooleanExtra(Constants.IMPORTANT, false);

            checkBoxImportant.setChecked(important);
            checkBoxFunctional.setChecked(functional);
            checkBoxSensory.setChecked(sensory);
            checkBoxAcademic.setChecked(academic);
            checkBoxBehavioral.setChecked(behavioral);
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
            checkBoxFunctional.setEnabled(true);
            checkBoxSensory.setEnabled(true);
            checkBoxAcademic.setEnabled(true);
            checkBoxBehavioral.setEnabled(true);
            editTextNote.requestFocus();
        }
        else
        {
            imageViewAction.setImageResource(R.drawable.ic_edit);
            editTextNote.setEnabled(false);
            checkBoxImportant.setEnabled(false);
            checkBoxFunctional.setEnabled(false);
            checkBoxSensory.setEnabled(false);
            checkBoxAcademic.setEnabled(false);
            checkBoxBehavioral.setEnabled(false);
            isImportant = checkBoxImportant.isChecked();
            functional = checkBoxFunctional.isChecked();
            sensory = checkBoxSensory.isChecked();
            academic = checkBoxAcademic.isChecked();
            behavioral = checkBoxBehavioral.isChecked();
            noteBody = editTextNote.getText().toString();
            new Loading().execute();
        }
    }

    public void deleteAction(View view)
    {
        DBManip.deleteData(AppAPI.NOTES, email, month + "/" + date + "/" + time, new DatabaseReference.CompletionListener() {
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
            note.setFunctional(functional);
            note.setSensory(sensory);
            note.setAcademic(academic);
            note.setBehavioral(behavioral);
            note.setText(noteBody);

            if(newNote)
            {
                if(!noteBody.isEmpty())
                {
                    DBManip.addData(AppAPI.NOTES, email, month + "/" + date + "/" + time, note,
                            new DatabaseReference.CompletionListener() {
                        @Override
                        public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                            Log.v("NOTE_ADDED", "note added");
                        }
                    });
                }
            }
            else
            {
                DBManip.updateData(AppAPI.NOTES, email, month + "/" + date + "/" + time, note,
                        new DatabaseReference.CompletionListener() {
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
