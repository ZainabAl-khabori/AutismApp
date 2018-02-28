package worldontheotherside.wordpress.com.autismapp.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import worldontheotherside.wordpress.com.autismapp.R;

public class ChildInfoFragment extends Fragment {

    private static final String TAB = "tab";

    private int pg;

    private OnFragmentInteractionListener mListener;

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
        return inflater.inflate(R.layout.fragment_child_info, container, false);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
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
