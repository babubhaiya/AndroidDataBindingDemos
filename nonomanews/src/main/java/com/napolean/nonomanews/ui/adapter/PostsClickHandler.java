package com.napolean.nonomanews.ui.adapter;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.customtabs.CustomTabsIntent;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;

import com.napolean.nonomanews.R;
import com.napolean.nonomanews.data.model.Post;
import com.napolean.nonomanews.ui.activity.NonomaNewsActivity;
import com.napolean.nonomanews.ui.activity.NewsViewActivity;
import com.napolean.nonomanews.ui.fragment.NewsDetailFragment;
import com.napolean.nonomanews.ui.fragment.UserPostsFragment;
import com.napolean.nonomanews.ui.utils.NonomaNewsConstants;

/**
 * Created by ravi on 5/25/17.
 */
public class PostsClickHandler {

    private static final String CHROME_PACKAGE = "com.android.chrome";
    private Post mPost;
    private FragmentActivity mActivity;

    PostsClickHandler(Post iPost, FragmentActivity iActivity) {
        mPost = iPost;
        mActivity = iActivity;
    }

    /**
     * Method to be called when username is selected
     *
     * @param iView TextView with username
     */
    @SuppressWarnings("unused")
    public void onViewClick(View iView) {
        // Launch  fragment
        NonomaNewsActivity apnaHackerNewsActivity = (NonomaNewsActivity) mActivity;

        switch (iView.getId()) {
            case R.id.text_post_points:

                apnaHackerNewsActivity.getSupportFragmentManager().beginTransaction().replace(R.id.story_container_fl,
                        NewsDetailFragment.newInstance(mPost), NewsDetailFragment.TAG).addToBackStack(NewsDetailFragment.TAG)
                        .commit();
                break;
            case R.id.text_post_author:
                apnaHackerNewsActivity.getSupportFragmentManager().beginTransaction().replace(R.id.story_container_fl,
                        UserPostsFragment.newInstance(mPost.by), UserPostsFragment.TAG).addToBackStack(UserPostsFragment.TAG)
                        .commit();
                break;
            default:
                Log.v("TAG", "Default click");
                break;
        }
    }

    /**
     * Method to be called when post is clicked
     *
     * @param iView layout for post
     */
    @SuppressWarnings("unused")
    public void onPostClick(View iView) {
        if (mPost != null && mPost.url != null) {
            Context context = iView.getContext();

            CustomTabsIntent customTabsIntent = new CustomTabsIntent.Builder().setShowTitle(true)
                    .setToolbarColor(context.getResources().getColor(R.color.colorPrimaryDark))
                    .setStartAnimations(context, 0, android.R.anim.slide_in_left)
                    .setExitAnimations(context, android.R.anim.slide_in_left, android.R.anim.slide_out_right).build();

            // Following line is used to avoid chooser for browser when chrome tabs are launched
            customTabsIntent.intent.setPackage(CHROME_PACKAGE);

            try {
                customTabsIntent.launchUrl(context, Uri.parse(mPost.url));
            } catch (ActivityNotFoundException e) {
                // Open in WebView if activity is not found that can handle this
                Intent intent = new Intent(mActivity, NewsViewActivity.class);
                intent.putExtra(NonomaNewsConstants.BundleKeys.NEWS_DETAILS, mPost);
                mActivity.startActivity(intent);
            }

        }

    }

}
