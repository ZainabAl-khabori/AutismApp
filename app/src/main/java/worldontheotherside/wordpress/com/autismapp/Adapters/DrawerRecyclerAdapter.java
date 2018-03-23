package worldontheotherside.wordpress.com.autismapp.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import worldontheotherside.wordpress.com.autismapp.API.DrawerItem;
import worldontheotherside.wordpress.com.autismapp.API.User;
import worldontheotherside.wordpress.com.autismapp.Data.Constants;
import worldontheotherside.wordpress.com.autismapp.R;

/**
 * Created by زينب on 3/19/2018.
 */

public class DrawerRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<DrawerItem> items;
    private User user;
    private Context context;

    private final int HEADER = 0, ITEM = 1;

    public class HeaderViewHolder extends RecyclerView.ViewHolder
    {
        private CircularImageView imageViewDp;
        private TextView textViewUsername;

        public HeaderViewHolder(View itemView) {
            super(itemView);

            imageViewDp = (CircularImageView) itemView.findViewById(R.id.imageViewDp);
            textViewUsername = (TextView) itemView.findViewById(R.id.textViewUsername);
        }

        public CircularImageView getImageViewDp() { return imageViewDp; }

        public TextView getTextViewUsername() { return textViewUsername; }
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        private ImageView imageViewIcon;
        private TextView textViewTitle;
        private View holder;

        public ItemViewHolder(View itemView) {
            super(itemView);

            holder = itemView;
            imageViewIcon = (ImageView) itemView.findViewById(R.id.imageViewIcon);
            textViewTitle = (TextView) itemView.findViewById(R.id.textViewTitle);

            itemView.setOnClickListener(this);
        }

        public ImageView getImageViewIcon() { return imageViewIcon; }

        public TextView getTextViewTitle() { return textViewTitle; }

        public View getHolder() { return holder; }

        @Override
        public void onClick(View view) {
            AppCompatActivity activity = (AppCompatActivity) context;
            Intent intent = new Intent(context, items.get(getAdapterPosition()).getActivityClass());
            if(items.get(getAdapterPosition()).getTitle().equals(Constants.SIGN_OUT))
            {
                AuthUI.getInstance().signOut(context).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(Task<Void> task) {
                        Log.v("SIGNOUT", "signed out");
                    }
                });
            }
            activity.startActivity(intent);
            if(items.get(getAdapterPosition()).getTitle().equals(Constants.SIGN_OUT))
                activity.finish();
        }
    }

    public DrawerRecyclerAdapter(ArrayList<DrawerItem> items, User user, Context context) {
        this.items = items;
        this.user = user;
        this.context = context;
    }

    @Override
    public int getItemViewType(int position) { return items.get(position).getType(); }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        switch(viewType)
        {
            case HEADER:
                view = inflater.inflate(R.layout.drawer_header, parent, false);
                return new HeaderViewHolder(view);
            case ITEM:
                view = inflater.inflate(R.layout.drawer_item, parent, false);
                return new ItemViewHolder(view);
            default:
                return null;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch(holder.getItemViewType())
        {
            case HEADER: HeaderViewHolder headerViewHolder = (HeaderViewHolder) holder;
                Picasso.get().load(user.getDpUri()).into(headerViewHolder.getImageViewDp());
                Log.v("USERNAME", user.getUsername());
                headerViewHolder.getTextViewUsername().setText(user.getUsername());
                break;
            case ITEM: ItemViewHolder itemViewHolder = (ItemViewHolder) holder;

                if(position % 2 != 0)
                {
                    itemViewHolder.getHolder().setBackgroundColor(ContextCompat.getColor(context, R.color.colorPrimaryDark));
                    itemViewHolder.getTextViewTitle().setTextColor(ContextCompat.getColor(context, R.color.colorPrimary));
                }
                else
                {
                    itemViewHolder.getHolder().setBackgroundColor(ContextCompat.getColor(context, R.color.colorPrimary));
                    itemViewHolder.getTextViewTitle().setTextColor(ContextCompat.getColor(context, R.color.colorPrimaryDark));
                }

                itemViewHolder.getImageViewIcon().setImageResource(items.get(position).getIcon());
                itemViewHolder.getTextViewTitle().setText(items.get(position).getTitle());

                break;
        }
    }

    @Override
    public int getItemCount() { return items.size(); }
}
