package com.napolean.nonomanews.ui.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.napolean.nonomanews.R;
import com.napolean.nonomanews.data.model.Post;
import com.napolean.nonomanews.databinding.ActivityNewsViewBinding;
import com.napolean.nonomanews.ui.utils.NonomaNewsConstants;
import com.napolean.nonomanews.ui.utils.NonomaNewsUtils;

/**
 * Created by ravi on 6/1/17.
 */

public class NewsViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityNewsViewBinding activityNewsViewBinding = DataBindingUtil.setContentView(this, R.layout.activity_news_view);

        final Post post;

        try {
            post = getIntent().getParcelableExtra(NonomaNewsConstants.BundleKeys.NEWS_DETAILS);
            activityNewsViewBinding.setNewsUrl(post);
        } catch (NullPointerException e) {
            e.printStackTrace();
            NonomaNewsUtils.showToast(getString(R.string.no_valid_post));
        }
    }
}
