package worldontheotherside.wordpress.com.autismapp.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import worldontheotherside.wordpress.com.autismapp.R;

public class ArticleDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.article_details_activity);

        TextView tvToolbarTitle = findViewById (R.id.tv_search_title);

    }
}
