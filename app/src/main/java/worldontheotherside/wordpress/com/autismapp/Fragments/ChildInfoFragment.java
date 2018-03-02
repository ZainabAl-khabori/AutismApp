package worldontheotherside.wordpress.com.autismapp.Fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mikhaellopez.circularimageview.CircularImageView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import worldontheotherside.wordpress.com.autismapp.Adapters.ChildInfoRecyclerAdapter;
import worldontheotherside.wordpress.com.autismapp.Data.Constants;
import worldontheotherside.wordpress.com.autismapp.R;

public class ChildInfoFragment extends Fragment implements ChildInfoRecyclerAdapter.OnItemClickListener {

    private static final String TAB = "tab";

    private int pg;
    ArrayList<ChildInfoItem> items;

    private CircularImageView imageViewDp;
    private TextView textViewUri;

    public ChildInfoFragment()
    {
        // Required empty public constructor
    }

    public static ChildInfoFragment newChildInfoFragment(int page) {
        ChildInfoFragment fragment = new ChildInfoFragment();
        Bundle args = new Bundle();
        args.putInt(TAB, page);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null)
            pg = getArguments().getInt(TAB, 0);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_child_info, container, false);
        items = new ArrayList<>();

        items.add(new ChildInfoItem(Constants.NAME, Constants.CHILD_INFO_RECYCLER_TEXT_INPUT, R.drawable.ic_username));
        items.add(new ChildInfoItem(Constants.AGE, Constants.CHILD_INFO_RECYCLER_TEXT_INPUT, R.drawable.ic_age));
        items.add(new ChildInfoItem(Constants.GENDER, Constants.CHILD_INFO_RECYCLER_GENDER, R.drawable.ic_gender));
        items.add(new ChildInfoItem(Constants.CHILD_PHOTO, Constants.CHILD_INFO_RECYCLER_PHOTO_INPUT, R.drawable.ic_dp));
        items.add(new ChildInfoItem(Constants.AUTISM_SPECTRUM_SCORE, Constants.CHILD_INFO_RECYCLER_TEXT_INPUT, R.drawable.ic_score));
        items.add(new ChildInfoItem(Constants.CREATE_ACCOUNT, Constants.CHILD_INFO_RECYCLER_BUTTON, R.drawable.ic_help));

        RecyclerView recyclerViewChildInfo = (RecyclerView) view.findViewById(R.id.recyclerViewChildInfo);
        ChildInfoRecyclerAdapter adapter = new ChildInfoRecyclerAdapter(getContext(), items);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        ChildInfoRecyclerAdapter.OnItemClickListener onItemClickListener = this;

        recyclerViewChildInfo.setAdapter(adapter);
        adapter.setOnItemClickListener(onItemClickListener);
        recyclerViewChildInfo.setLayoutManager(layoutManager);

        return view;
    }

    @Override
    public void onClick(View view, int position, RecyclerView.ViewHolder holder) {
        if(view.getId() == R.id.buttonBrowse)
        {
            ChildInfoRecyclerAdapter.PhotoInputViewHolder viewHolder = (ChildInfoRecyclerAdapter.PhotoInputViewHolder) holder;

            textViewUri = viewHolder.getTextViewUrl();
            imageViewDp = viewHolder.getImageViewPhoto();

            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent, 0);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == getActivity().RESULT_OK)
        {
            textViewUri.setText(data.getData().toString());
            Picasso.with(getContext()).load(data.getData()).into(imageViewDp);
        }
    }

    public class ChildInfoItem
    {
        private String title;
        private int type;
        private int icon;

        public ChildInfoItem(String title, int type, int icon) {
            this.title = title;
            this.type = type;
            this.icon = icon;
        }

        public String getTitle() { return title; }

        public int getType() { return type; }

        public int getIcon() { return icon; }
    }
}
