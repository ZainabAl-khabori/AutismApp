package worldontheotherside.wordpress.com.autismapp.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import worldontheotherside.wordpress.com.autismapp.R;

public class ArticleMainActivity extends AppCompatActivity {

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.artical_main_design);

        FrameLayout flToolBar = (FrameLayout) findViewById (R.id.toolbar);
        final TextView tvToolBarTitle = (TextView) findViewById (R.id.tv_search_title);
        final EditText etToolBarSearchKey = (EditText) findViewById (R.id.search_bar);
        ImageView imgvwSearchIcon = (ImageView) findViewById (R.id.imgvw_search_icon);

        flToolBar.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick (View v) {

                if (etToolBarSearchKey.getVisibility () == View.GONE) {
                    tvToolBarTitle.setVisibility (View.GONE);
                    etToolBarSearchKey.setVisibility (View.VISIBLE);
                }
            }
        });


        imgvwSearchIcon.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick (View v) {

                if (etToolBarSearchKey.getVisibility () == View.GONE) {
                    tvToolBarTitle.setVisibility (View.GONE);
                    etToolBarSearchKey.setVisibility (View.VISIBLE);
                }
                else {
                  //  Intent go = new Intent (getApplicationContext (),SearchBooksActivity.class);
                }
            }
        });



        FloatingActionButton fab = (FloatingActionButton) findViewById (R.id.fab);
        fab.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick (View view) {
                Snackbar.make (view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction ("Action", null).show ();
            }
        });
    }

}
