package worldontheotherside.wordpress.com.autismapp.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import worldontheotherside.wordpress.com.autismapp.API.InfoItem;
import worldontheotherside.wordpress.com.autismapp.Adapters.ChildInfoRecyclerAdapter;
import worldontheotherside.wordpress.com.autismapp.Data.Constants;
import worldontheotherside.wordpress.com.autismapp.R;

/**
 * Created by زينب on 2/25/2018.
 */

public class PersonalInfoFragment extends Fragment implements ChildInfoRecyclerAdapter.OnItemClickListener {

    public static final String ID = "pager id";

    private ArrayList<InfoItem> items;
    private ViewPager viewPagerParent;

    private int viewPagerId;

    public static PersonalInfoFragment newPersonalInfoFragment(int id)
    {
        PersonalInfoFragment personalInfoFragment = new PersonalInfoFragment();
        Bundle args = new Bundle();

        args.putInt(ID, id);
        personalInfoFragment.setArguments(args);

        return personalInfoFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewPagerId = getArguments().getInt(ID, 0);
        items = new ArrayList<>();

        items.add(new InfoItem(Constants.EMAIL, Constants.CHILD_INFO_RECYCLER_TEXT_INPUT, R.drawable.ic_email));
        items.add(new InfoItem(Constants.PASSWORD, Constants.CHILD_INFO_RECYCLER_TEXT_INPUT, R.drawable.ic_password));
        items.add(new InfoItem(Constants.RE_PASSWORD, Constants.CHILD_INFO_RECYCLER_TEXT_INPUT, R.drawable.ic_password));
        items.add(new InfoItem(Constants.USERNAME, Constants.CHILD_INFO_RECYCLER_TEXT_INPUT, R.drawable.ic_username));
        items.add(new InfoItem(Constants.PHONE, Constants.CHILD_INFO_RECYCLER_TEXT_INPUT, R.drawable.ic_phone));
        items.add(new InfoItem(Constants.GENDER, Constants.CHILD_INFO_RECYCLER_GENDER, R.drawable.ic_gender));
        items.add(new InfoItem(Constants.FILL_CHILD_INFO, Constants.CHILD_INFO_RECYCLER_BUTTON, R.drawable.ic_help));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_personal_info, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewPagerParent = (ViewPager) getActivity().findViewById(viewPagerId);

        RecyclerView recyclerViewPersonalInfo = (RecyclerView) view.findViewById(R.id.recyclerViewPersonalInfo);
        ChildInfoRecyclerAdapter adapter = new ChildInfoRecyclerAdapter(getContext(), items);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        ChildInfoRecyclerAdapter.OnItemClickListener onItemClickListener = this;

        recyclerViewPersonalInfo.setAdapter(adapter);
        adapter.setOnItemClickListener(onItemClickListener);
        recyclerViewPersonalInfo.setLayoutManager(layoutManager);
    }

    @Override
    public void onClick(View view, int position, RecyclerView.ViewHolder holder) {
        if(view.getId() == R.id.buttonCreateAccount)
            viewPagerParent.setCurrentItem(1);
    }
}
