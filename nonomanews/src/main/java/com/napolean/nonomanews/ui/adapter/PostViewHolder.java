package com.napolean.nonomanews.ui.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.napolean.nonomanews.R;
import com.napolean.nonomanews.data.model.Post;


public class PostViewHolder {

    private static final String TAG = PostViewHolder.class.getSimpleName();

    View mPostContainer;

    TextView mPostTitle;

    TextView mPostAuthor;

    TextView mPostPoints;

    TextView mPostComments;

    TextView mViewPost;

    Context mContext;

    Post mPost;

    private ViewDataBinding mViewDataBinding;

    PostViewHolder(View iView) {

        mContext = iView.getContext();
        mViewDataBinding = DataBindingUtil.bind(iView);
    }

    public void onSetValues(Post story) {
        mPost = story;

        mPostTitle.setText(story.title);
        mPostAuthor.setText(Html.fromHtml(mContext.getString(R.string.story_by) + " " + "<u>" + story.by + "</u>"));
        mPostPoints.setText(story.score + " " + mContext.getString(R.string.story_points));

        if (story.postType == Post.PostType.STORY && story.kids == null) {
            mPostComments.setVisibility(View.GONE);
        } else {
            mPostComments.setVisibility(View.VISIBLE);
        }

        mViewPost.setVisibility(story.url != null && story.url.length() > 0 ? View.VISIBLE : View.GONE);
    }

//    @OnClick({R.id.text_post_author, R.id.text_view_comments,
//            R.id.text_post_points, R.id.container_post})
    public void onSetListeners(View iView) {

        switch (iView.getId()) {
            case R.id.text_post_author:
                // Start User Activity for showing user details
                break;
            case R.id.text_view_comments:
                // Start Comment Activity for showing all comments on a post
                break;
            case R.id.text_view_post:
                // Start Activity to show complete story.Usually a webview
                break;
            case R.id.container_post:
                if (mPost == null)
                    return;

                Post.PostType postType = mPost.postType;
                if (postType == Post.PostType.JOB || postType == Post.PostType.STORY) {
                    // TODO Start Activity to show complete story.Usually a webview

                } else if (postType == Post.PostType.ASK) {
                    // TODO Start Comment Activity for showing all comments on a post
                }
                break;
            default:
                Log.e(TAG, "No Match");
                break;
        }
    }

    ViewDataBinding getViewDataBinding() {
        return mViewDataBinding;
    }

}