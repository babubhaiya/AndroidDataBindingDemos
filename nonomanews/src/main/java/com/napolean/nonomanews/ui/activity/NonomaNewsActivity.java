package com.napolean.nonomanews.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.napolean.nonomanews.R;
import com.napolean.nonomanews.ui.fragment.NewsFragment;

public class NonomaNewsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nonoma_news);

        if (savedInstanceState == null) {
            // Inflate Stories
            getSupportFragmentManager().beginTransaction().add(R.id.story_container_fl,
                    new NewsFragment(), NewsFragment.class.getSimpleName())
                    .commit();
        }

    }
}
