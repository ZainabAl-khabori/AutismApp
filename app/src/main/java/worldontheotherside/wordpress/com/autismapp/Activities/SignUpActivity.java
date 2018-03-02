package worldontheotherside.wordpress.com.autismapp.Activities;

import android.content.Intent;
import android.provider.MediaStore;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.mikhaellopez.circularimageview.CircularImageView;
import com.squareup.picasso.Picasso;

import worldontheotherside.wordpress.com.autismapp.Adapters.TabsPagerAdapter;
import worldontheotherside.wordpress.com.autismapp.Fragments.ChildInfoFragment;
import worldontheotherside.wordpress.com.autismapp.R;

public class SignUpActivity extends AppCompatActivity {

    private TextView textViewLogin;

    private CircularImageView imageViewDp;
    private TextView textViewUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        TabLayout tabLayoutTabs = (TabLayout) findViewById(R.id.tabLayoutTabs);
        ViewPager viewPagerTabs = (ViewPager) findViewById(R.id.viewPagerTabs);

        viewPagerTabs.setAdapter(new TabsPagerAdapter(getSupportFragmentManager()));
        tabLayoutTabs.setupWithViewPager(viewPagerTabs);
    }
}
