package worldontheotherside.wordpress.com.autismapp.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import worldontheotherside.wordpress.com.autismapp.API.Note;
import worldontheotherside.wordpress.com.autismapp.R;

/**
 * Created by زينب on 3/17/2018.
 */

public class DayNotesRecyclerAdapter extends RecyclerView.Adapter<DayNotesRecyclerAdapter.ViewHolder> {

    private ArrayList<Note> data;
    private OnItemClickListener onItemClickListener;

    public interface OnItemClickListener { void onItemClick(View view, int position); }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView textViewNote;
        private ImageView imageViewImportance;

        public ViewHolder(View itemView) {
            super(itemView);

            textViewNote = (TextView) itemView.findViewById(R.id.textViewNote);
            imageViewImportance = (ImageView) itemView.findViewById(R.id.imageViewImportance);

            itemView.setOnClickListener(this);
        }

        public TextView getTextViewNote() { return textViewNote; }

        public ImageView getImageViewImportance() { return imageViewImportance; }

        @Override
        public void onClick(View view) {
            if(onItemClickListener != null)
                onItemClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    public DayNotesRecyclerAdapter(ArrayList<Note> data) { this.data = data; }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_holder_day_note, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.getTextViewNote().setText(data.get(position).getText());

        if(data.get(position).isImportant())
            holder.getImageViewImportance().setImageResource(R.drawable.ic_important);
        else
            holder.getImageViewImportance().setImageResource(R.drawable.ic_ordinary);
    }

    @Override
    public int getItemCount() { return data.size(); }
}
