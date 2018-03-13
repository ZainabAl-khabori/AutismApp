package worldontheotherside.wordpress.com.autismapp.Activities;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import worldontheotherside.wordpress.com.autismapp.R;

public class InfoDetailsActivity extends AppCompatActivity {

    ListView lvInfoListView;

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.info_details_activity);

        lvInfoListView = findViewById (R.id.lv_info_list);

        ArrayList<Info> infoList = (ArrayList<Info>) getIntent ().getExtras ().getSerializable ("infoList");

        lvInfoListView.setAdapter (new MyAdapter (infoList, InfoDetailsActivity.this));


    }

    private class MyAdapter extends BaseAdapter {
        private ArrayList<Info> infoList;
        private Context context;

        public MyAdapter (ArrayList<Info> newInfoList, Context newContext) {
            infoList = newInfoList;
            context = newContext;
        }

        @Override
        public int getCount () {

            return infoList.size ();
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
        public View getView (final int index, View itemView, ViewGroup viewGroup) {

            final Info info = infoList.get (index);
            LayoutInflater layoutInflater = (LayoutInflater) getSystemService (LAYOUT_INFLATER_SERVICE);
            itemView = layoutInflater.inflate (R.layout.info_list_item, null);

            TextView tvInfoTitle = itemView.findViewById (R.id.tv_info_title);
            final TextView tvInfoDescription = itemView.findViewById (R.id.tv_info_description);
            final ImageView imgvwInfoImage = itemView.findViewById (R.id.imgvw_info_image);
            final ImageView imgvwInfoExtend = itemView.findViewById (R.id.imgvw_info_extend);


            tvInfoTitle.setText (info.getInfoTitle ());

            itemView.setOnClickListener (new View.OnClickListener () {
                @Override
                public void onClick (View view) {
                    if (tvInfoDescription.getVisibility () == View.GONE) {
                        imgvwInfoImage.setVisibility (View.VISIBLE);
                        tvInfoDescription.setVisibility (View.VISIBLE);
                    } else {
                        imgvwInfoImage.setVisibility (View.GONE);
                        tvInfoDescription.setVisibility (View.GONE);
                    }
                }
            });


            imgvwInfoExtend.setOnClickListener (new View.OnClickListener () {
                @Override
                public void onClick (View view) {

                    String url = info.getInfoPageURL ();
                    Intent implicit = new Intent (Intent.ACTION_VIEW, Uri.parse (url));
                    startActivity (implicit);
                }
            });


            tvInfoDescription.setText (info.getInfoDescription ());

            return itemView;
        }
    }
}
