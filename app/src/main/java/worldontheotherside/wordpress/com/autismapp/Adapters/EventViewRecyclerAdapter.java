package worldontheotherside.wordpress.com.autismapp.Adapters;

import android.content.Context;
import android.content.res.ColorStateList;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.ImageViewCompat;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import worldontheotherside.wordpress.com.autismapp.API.Event;
import worldontheotherside.wordpress.com.autismapp.API.EventDisplayItem;
import worldontheotherside.wordpress.com.autismapp.Data.Constants;
import worldontheotherside.wordpress.com.autismapp.R;

/**
 * Created by زينب on 3/18/2018.
 */

public class EventViewRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<EventDisplayItem> items;
    private Event event;
    private Context context;
    private boolean newEvent;

    private final int FIELD = 0, BODY = 1;

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
                        fieldViewHolder.getTextViewField()
                                .setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_time_green, 0, 0, 0);
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
                    Ringtone ringtone = RingtoneManager.getRingtone(context, Uri.parse(event.getRingtone()));
                    fieldViewHolder.getTextViewBody().setText(ringtone.getTitle(context));
                    // Some coding to bring ringtone
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
}
