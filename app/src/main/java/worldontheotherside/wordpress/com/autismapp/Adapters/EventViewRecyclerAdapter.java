package worldontheotherside.wordpress.com.autismapp.Adapters;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.ImageViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import worldontheotherside.wordpress.com.autismapp.API.Event;
import worldontheotherside.wordpress.com.autismapp.API.EventDisplayItem;
import worldontheotherside.wordpress.com.autismapp.Activities.EditEventActivity;
import worldontheotherside.wordpress.com.autismapp.Data.Constants;
import worldontheotherside.wordpress.com.autismapp.Database.AppAPI;
import worldontheotherside.wordpress.com.autismapp.Database.DBManip;
import worldontheotherside.wordpress.com.autismapp.Fragments.ProgressDialogFragment;
import worldontheotherside.wordpress.com.autismapp.Fragments.TimePickerDialogFragment;
import worldontheotherside.wordpress.com.autismapp.R;

/**
 * Created by زينب on 3/18/2018.
 */

public class EventViewRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>
        implements EditEventActivity.OnTimeSelectedListener, EditEventActivity.OnRingtoneSelectedListener {

    private ArrayList<EventDisplayItem> items;
    private Event event;
    private Context context;
    private boolean newEvent;

    private final int FIELD = 0, BODY = 1;
    private RecyclerView recyclerViewParent;
    private String email;
    private int ringtoneIndex;
    private int timeIndex;

    private Ringtone ringtone;

    @Override
    public void onTimeSelected(TimePicker view, int hr, int min) {

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, hr);
        calendar.set(Calendar.MINUTE, min);

        String time = new SimpleDateFormat("hh:mm a", Locale.getDefault()).format(calendar.getTime());

        FieldViewHolder holder = (FieldViewHolder) recyclerViewParent.findViewHolderForAdapterPosition(timeIndex);
        holder.getTextViewBody().setText(time);
        event.setTime(time);
    }

    @Override
    public void onRingtoneSelected(Ringtone ringtone, Uri uri) {
        this.ringtone = ringtone;
        FieldViewHolder holder = (FieldViewHolder) recyclerViewParent.findViewHolderForAdapterPosition(ringtoneIndex);
        holder.getTextViewBody().setText(ringtone.getTitle(context));
        event.setRingtone(uri.toString());
    }

    public class FieldViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        private TextView textViewField;
        private TextView textViewBody;
        private ImageView imageViewAction;
        private ImageView imageViewEdit;

        public FieldViewHolder(View itemView) {
            super(itemView);

            textViewField = (TextView) itemView.findViewById(R.id.textViewField);
            textViewBody = (TextView) itemView.findViewById(R.id.textViewBody);
            imageViewAction = (ImageView) itemView.findViewById(R.id.imageViewAction);
            imageViewEdit = (ImageView) itemView.findViewById(R.id.imageViewEdit);

            imageViewAction.setOnClickListener(this);
            imageViewEdit.setOnClickListener(this);
        }

        public TextView getTextViewField() { return textViewField; }

        public TextView getTextViewBody() { return textViewBody; }

        public ImageView getImageViewAction() { return imageViewAction; }

        public ImageView getImageViewEdit() { return imageViewEdit; }

        @Override
        public void onClick(View view) {
            if(view.getId() == imageViewEdit.getId())
            {
                BodyViewHolder holder;
                if(items.get(getAdapterPosition()).getTitle().equals(Constants.EVENT))
                {
                    holder = (BodyViewHolder) recyclerViewParent.findViewHolderForAdapterPosition(getAdapterPosition() + 1);
                    if(!holder.getEditTextInfo().isEnabled())
                    {
                        imageViewEdit.setImageResource(R.drawable.ic_done);
                        holder.getEditTextInfo().setEnabled(true);
                        holder.getEditTextInfo().requestFocus();
                    }
                    else
                    {
                        imageViewEdit.setImageResource(R.drawable.ic_edit);
                        holder.getEditTextInfo().setEnabled(false);
                        event.setTitle(holder.getEditTextInfo().getText().toString());
                    }
                }

                if(items.get(getAdapterPosition()).getTitle().equals(Constants.TIME))
                {
                    timeIndex = getAdapterPosition();

                    AppCompatActivity activity = (AppCompatActivity)context;
                    TimePickerDialogFragment timeDialog = new TimePickerDialogFragment();
                    timeDialog.show(activity.getSupportFragmentManager(), "TIME_PICKER");
                }

                if(items.get(getAdapterPosition()).getTitle().equals(Constants.EVENT_DESCRIPTION))
                {
                    holder = (BodyViewHolder) recyclerViewParent.findViewHolderForAdapterPosition(getAdapterPosition() + 1);
                    if(!holder.getEditTextInfo().isEnabled())
                    {
                        imageViewEdit.setImageResource(R.drawable.ic_done);
                        holder.getEditTextInfo().setEnabled(true);
                        holder.getEditTextInfo().requestFocus();
                    }
                    else
                    {
                        imageViewEdit.setImageResource(R.drawable.ic_edit);
                        holder.getEditTextInfo().setEnabled(false);
                        event.setDescription(holder.getEditTextInfo().getText().toString());
                    }
                }

                if(items.get(getAdapterPosition()).getTitle().equals(Constants.RINGTONE))
                {
                    if(ringtone != null && ringtone.isPlaying())
                        ringtone.stop();

                    ringtoneIndex = getAdapterPosition();

                    AppCompatActivity activity = (AppCompatActivity) context;
                    Intent intent = new Intent(RingtoneManager.ACTION_RINGTONE_PICKER);
                    activity.startActivityForResult(intent, Constants.RQS_RINGTONE_PICKER);
                }
            }

            if(view.getId() == imageViewAction.getId())
            {
                if(items.get(getAdapterPosition()).getTitle().equals(Constants.EVENT))
                {
                    final AppCompatActivity activity = (AppCompatActivity) context;
                    DBManip.deleteData(AppAPI.EVENTS, email, event.getDate() + "/" + event.getTime(), new DatabaseReference.CompletionListener() {
                        @Override
                        public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                            Log.v("NOTE_DELETION", "position: " + event.getTime() + " successful");
                            activity.setResult(activity.RESULT_OK, new Intent());
                            activity.finish();
                        }
                    });
                }

                if(items.get(getAdapterPosition()).getTitle().equals(Constants.RINGTONE))
                {
                    if(ringtone != null && !ringtone.isPlaying())
                    {
                        imageViewAction.setImageResource(R.drawable.ic_pause);
                        ImageViewCompat.setImageTintList(imageViewAction,
                                ColorStateList.valueOf(ContextCompat.getColor(context, android.R.color.holo_red_light)));
                        ringtone.play();
                    }
                    else
                    {
                        imageViewAction.setImageResource(R.drawable.ic_play);
                        ImageViewCompat.setImageTintList(imageViewAction,
                                ColorStateList.valueOf(ContextCompat.getColor(context, R.color.colorPrimaryDark)));
                        ringtone.stop();
                    }
                }
            }

            if(imageViewEdit.getDrawable() == ContextCompat.getDrawable(context, R.drawable.ic_done))
            {
                FieldViewHolder timeHolder = (FieldViewHolder) recyclerViewParent.findViewHolderForAdapterPosition(timeIndex);
                timeHolder.getTextViewField().setTextColor(Color.RED);
            }

            //new Saving().execute();
        }
    }

    public class BodyViewHolder extends RecyclerView.ViewHolder
    {
        private EditText editTextInfo;

        public BodyViewHolder(View itemView) {
            super(itemView);
            editTextInfo = (EditText) itemView.findViewById(R.id.editTextInfo);
        }

        public EditText getEditTextInfo() { return editTextInfo; }
    }

    public EventViewRecyclerAdapter(ArrayList<EventDisplayItem> items, Event event, Context context, boolean newEvent) {
        this.items = items;
        this.event = event;
        this.context = context;
        this.newEvent = newEvent;
    }

    @Override
    public int getItemViewType(int position) { return items.get(position).getType(); }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        recyclerViewParent = (RecyclerView) parent;
        email = FirebaseAuth.getInstance().getCurrentUser().getEmail();

        switch(viewType)
        {
            case FIELD:
                view = inflater.inflate(R.layout.view_holder_event_field, parent, false);
                return new FieldViewHolder(view);
            case BODY:
                view = inflater.inflate(R.layout.view_holder_event_edit_text, parent, false);
                return new BodyViewHolder(view);
            default:
                return null;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch(holder.getItemViewType())
        {
            case FIELD: FieldViewHolder fieldViewHolder = (FieldViewHolder) holder;

                fieldViewHolder.getTextViewField().setText(items.get(position).getTitle());

                if(items.get(position).getTitle().equals(Constants.EVENT))
                    fieldViewHolder.getTextViewField()
                            .setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_event_green, 0, 0, 0);

                if(items.get(position).getTitle().equals(Constants.TIME) || items.get(position).getTitle().equals(Constants.DATE))
                {
                    if(items.get(position).getTitle().equals(Constants.TIME))
                    {
                        timeIndex = position;

                        fieldViewHolder.getTextViewField()
                                .setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_time_green, 0, 0, 0);
                        if(event.getTime() != null)
                            fieldViewHolder.getTextViewBody().setText(event.getTime());
                    }
                    else if(items.get(position).getTitle().equals(Constants.DATE))
                    {
                        fieldViewHolder.getTextViewField()
                                .setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_date_green, 0, 0, 0);
                        fieldViewHolder.getImageViewEdit().setVisibility(View.GONE);
                        fieldViewHolder.getTextViewBody().setText(event.getDate());
                    }

                    fieldViewHolder.getImageViewAction().setVisibility(View.GONE);
                }

                if(items.get(position).getTitle().equals(Constants.EVENT_DESCRIPTION))
                {
                    fieldViewHolder.getTextViewField()
                            .setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_event_note_green, 0, 0, 0);
                    fieldViewHolder.getImageViewAction().setVisibility(View.GONE);
                }

                if(items.get(position).getTitle().equals(Constants.RINGTONE))
                {
                    fieldViewHolder.getTextViewField()
                            .setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_ringtone_green, 0, 0, 0);
                    fieldViewHolder.getImageViewAction().setImageResource(R.drawable.ic_play);
                    ImageViewCompat.setImageTintList(fieldViewHolder.getImageViewAction(),
                            ColorStateList.valueOf(ContextCompat.getColor(context, R.color.colorPrimaryDark)));
                    if(event.getRingtone() != null)
                    {
                        ringtone = RingtoneManager.getRingtone(context, Uri.parse(event.getRingtone()));
                        fieldViewHolder.getTextViewBody().setText(ringtone.getTitle(context));
                    }
                }

                break;
            case BODY: BodyViewHolder bodyViewHolder = (BodyViewHolder) holder;

                if(items.get(position).getTitle().equals(Constants.EVENT_INFO))
                {
                    bodyViewHolder.getEditTextInfo().setEllipsize(null);
                    bodyViewHolder.getEditTextInfo().setHeight(500);
                    bodyViewHolder.getEditTextInfo().setMaxHeight(500);
                    if(!newEvent)
                        bodyViewHolder.getEditTextInfo().setEnabled(false);
                    bodyViewHolder.getEditTextInfo().setInputType(InputType.TYPE_CLASS_TEXT|InputType.TYPE_TEXT_FLAG_MULTI_LINE);
                }

                break;
        }
    }

    @Override
    public int getItemCount() { return items.size(); }

    public class Saving extends AsyncTask<Void, Void, Void>
    {
        private ProgressDialogFragment progressDialogFragment;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            AppCompatActivity activity = (AppCompatActivity) context;

            progressDialogFragment = ProgressDialogFragment.newProgressDialogFragment(Constants.WAIT_MESSAGE);
            progressDialogFragment.show(activity.getSupportFragmentManager(), "PROGRESS_DIALOG");
            progressDialogFragment.setCancelable(false);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            progressDialogFragment.dismiss();
        }
    }
}
