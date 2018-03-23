package worldontheotherside.wordpress.com.autismapp.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import worldontheotherside.wordpress.com.autismapp.API.Comments;
import worldontheotherside.wordpress.com.autismapp.R;

/**
 * Created by OJP on 3/21/2018.
 */

public class CommentsAdapter extends BaseAdapter {

    Context context;
    ArrayList<Comments> commentsList;

    public CommentsAdapter (Context context, ArrayList<Comments> commentsArrayList) {
        this.commentsList = commentsArrayList;
        this.context = context;
    }

    @Override
    public int getCount () {
        return commentsList.size ();
    }

    @Override
    public Object getItem (int i) {
        return null;
    }

    @Override
    public long getItemId (int i) {
        return 0;
    }

    @Override
    public View getView (int i, View commentsListItem, ViewGroup viewGroup) {

        Comments comment = commentsList.get (i);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService (Context.LAYOUT_INFLATER_SERVICE);
        commentsListItem = inflater.inflate (R.layout.article_details_comment_list_item, null);

        TextView tvCommentWriter = commentsListItem.findViewById (R.id.tv_article_writer_name);
        TextView tvComment = commentsListItem.findViewById (R.id.tv_comment);
        ImageView ivCommentWriterImage = commentsListItem.findViewById (R.id.iv_article_writer_image);

        tvCommentWriter.setText (comment.getCommentWriterWriterName ());
        tvComment.setText (comment.getCommentContent ());


        if (comment.getCommentWriterImageURL () != null && !comment.getCommentWriterImageURL ().isEmpty ()) {
            Picasso.get().load (comment.getCommentWriterImageURL ()).into (ivCommentWriterImage);
        }

        return commentsListItem;
    }
}

