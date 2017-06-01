package com.napolean.nonomanews.ui.fragment;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.napolean.nonomanews.R;
import com.napolean.nonomanews.data.model.Comment;
import com.napolean.nonomanews.data.model.Post;
import com.napolean.nonomanews.data.remote.NonomaNewsService;
import com.napolean.nonomanews.data.remote.RetrofitHelper;
import com.napolean.nonomanews.databinding.FragmentNewsDetailBinding;
import com.napolean.nonomanews.ui.adapter.CommentsAdapter;
import com.napolean.nonomanews.ui.utils.NonomaNewsConstants;
import com.napolean.nonomanews.ui.utils.NonomaNewsUtils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Fragment containing details about a selected news.
 * Using Android data binding to set news details data to the inflated content view
 * Created by ravi on 5/25/17.
 */
public class NewsDetailFragment extends Fragment {

    public static final String TAG = NewsDetailFragment.class.getSimpleName();

    private FragmentNewsDetailBinding mFragmentNewsDetailBinding;
    private Post mSelectedPost;

    private CommentsAdapter mCommentsAdapter;
    private List<Comment> mCommentList = new ArrayList<>();
    private NonomaNewsService mHackerNewsService;

    /**
     * Counter of id's list that have already been fetched
     */
    private int mCommentIdCounter = 0;

    /**
     * Creates and returns an instance of the selected post
     *
     * @param iSelectedPost selected post whose details has to be shown
     * @return NewsDetailFragment instance
     */
    public static NewsDetailFragment newInstance(Post iSelectedPost) {
        NewsDetailFragment newsDetailFragment = new NewsDetailFragment();

        Bundle args = new Bundle();
        args.putParcelable(NonomaNewsConstants.BundleKeys.NEWS_DETAILS, iSelectedPost);
        newsDetailFragment.setArguments(args);

        return newsDetailFragment;
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mCommentsAdapter = new CommentsAdapter((FragmentActivity) activity, mCommentList);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mFragmentNewsDetailBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_news_detail, container, false);

        // Get the HackerNewsService
        mHackerNewsService = RetrofitHelper.newHackerNewsService();

        final Bundle bundle = getArguments();

        if (bundle != null) {
            mSelectedPost = bundle.getParcelable(NonomaNewsConstants.BundleKeys.NEWS_DETAILS);

            // Set adapter
            mFragmentNewsDetailBinding.newsDetailsCommentsRv.setLayoutManager(new LinearLayoutManager(getActivity()));
            mFragmentNewsDetailBinding.newsDetailsCommentsRv.setAdapter(mCommentsAdapter);

            // Set the data here
            mFragmentNewsDetailBinding.setSelectedPost(mSelectedPost);

            // Get the first comment
            if (mSelectedPost.kids != null && mSelectedPost.kids.size() > 0) {
                mHackerNewsService.getCommentItem(String.valueOf(mSelectedPost.kids.get(mCommentIdCounter++)))
                        .enqueue(mCommentsCallback);
            } else {
                NonomaNewsUtils.showToast(getString(R.string.no_comments_news));
            }
        } else {
            NonomaNewsUtils.showToast(getString(R.string.no_valid_post));
        }

        return mFragmentNewsDetailBinding.getRoot();
    }


    /**
     * Callback listing
     */
    private Callback<Comment> mCommentsCallback = new Callback<Comment>() {
        @Override
        public void onResponse(@NonNull Call<Comment> iCall, @NonNull Response<Comment> iResponse) {

            // Get the comment
            Comment comment = iResponse.body();

            if (comment != null) {
                // Add Post to the main data list backing the GridView
                mCommentList.add(comment);

                // Update UI as soon as you get the data
                mCommentsAdapter.notifyDataSetChanged();
            }

            // Get the next comment
            if (mSelectedPost.kids != null && mSelectedPost.kids.size() > mCommentIdCounter) {
                mHackerNewsService.getCommentItem(String.valueOf(mSelectedPost.kids.get(mCommentIdCounter++)))
                        .enqueue(mCommentsCallback);
            }

        }

        @Override
        public void onFailure(@NonNull Call<Comment> iCall, @NonNull Throwable iThrowable) {
            Log.e(TAG, getString(R.string.failure_fetching_comment_id));
        }
    };

}
