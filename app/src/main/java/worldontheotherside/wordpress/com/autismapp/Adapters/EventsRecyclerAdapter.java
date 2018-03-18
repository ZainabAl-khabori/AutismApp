package worldontheotherside.wordpress.com.autismapp.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import worldontheotherside.wordpress.com.autismapp.API.Event;
import worldontheotherside.wordpress.com.autismapp.R;

/**
 * Created by زينب on 3/17/2018.
 */

public class EventsRecyclerAdapter extends RecyclerView.Adapter<EventsRecyclerAdapter.ViewHolder> {

    private ArrayList<Event> data;
    private OnItemClickListener onItemClickListener;

    public interface OnItemClickListener { void onItemClick(View view, int position); }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView textViewTime;
        private TextView textViewDescription;

        public ViewHolder(View itemView) {
            super(itemView);

            textViewTime = (TextView) itemView.findViewById(R.id.textViewTime);
            textViewDescription = (TextView) itemView.findViewById(R.id.textViewDescription);

            itemView.setOnClickListener(this);
        }

        public TextView getTextViewTime() { return textViewTime; }

        public TextView getTextViewDescription() { return textViewDescription; }

        @Override
        public void onClick(View view) {
            if(onItemClickListener != null)
                onItemClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public EventsRecyclerAdapter(ArrayList<Event> data) { this.data = data; }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_holder_event, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String time = holder.getTextViewTime().getText().toString() + data.get(position).getTime();
        holder.getTextViewTime().setText(time);
        holder.getTextViewDescription().setText(data.get(position).getTitle());
    }

    @Override
    public int getItemCount() { return data.size(); }
}
