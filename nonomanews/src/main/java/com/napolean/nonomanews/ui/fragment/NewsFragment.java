package com.napolean.nonomanews.ui.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.napolean.nonomanews.R;
import com.napolean.nonomanews.data.model.Post;
import com.napolean.nonomanews.data.remote.NonomaNewsService;
import com.napolean.nonomanews.data.remote.RetrofitHelper;
import com.napolean.nonomanews.ui.adapter.NewsAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * This fragment provides the list of news either generic or specific to a user
 * Created by ravi on 5/23/17.
 */
public class NewsFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private static final String TAG = NewsFragment.class.getSimpleName();

    @BindView(R.id.news_gv_swipe_container)
    protected SwipeRefreshLayout mStoriesGVRefreshContainer;

    @BindView(R.id.news_gv)
    protected GridView mStoriesGridView;

    private NewsAdapter mNewsAdapter;

    /**
     * Stores the post id's
     */
    private static List<Long> POST_IDs;
    /**
     * Stores the posts
     */
    private static final List<Post> POST_LIST = new ArrayList<>();

    /**
     * {@link NonomaNewsService} client to call various api's
     */
    private NonomaNewsService mHackerNewsService = null;

    /**
     * Counter of id's list that have already been fetched
     */
    private int mPostIdCounter = 0;

    /**
     * Boolean decides if to continue loading of post
     */
    private boolean mCanLoad = false;


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mNewsAdapter = new NewsAdapter(POST_LIST, (FragmentActivity) activity);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate View
        View view = inflater.inflate(R.layout.fragment_news, container, false);

        ButterKnife.bind(this, view);

        initView();

        return view;
    }

    /**
     * Setup client to fetch the result
     */
    private void initView() {
        // Set refresh listener to the GridView
        mStoriesGVRefreshContainer.setOnRefreshListener(this);
//        mStoriesGVRefreshContainer.setProgressViewOffset(false, 200, 200);

        // clear list
        POST_LIST.clear();

        // Reset Counter
        mPostIdCounter = 0;

        // Set Adapter with default list
        mStoriesGridView.setAdapter(mNewsAdapter);

        // HackerNewsService client
        mHackerNewsService = RetrofitHelper.newHackerNewsService();

        // Get the  ids of the top stories
        mHackerNewsService.getTopStories().enqueue(mTopPostsIdsCallBack);
    }


    /**
     * Callback to for result of post api
     */
    private Callback<Post> mPostCallBack = new Callback<Post>() {
        @Override
        public void onResponse(@NonNull Call<Post> iCall, @NonNull Response<Post> iResponse) {
            // Get the post
            Post post = iResponse.body();

            if (post != null) {
                // Add Post to the main data list backing the GridView
                POST_LIST.add(post);

                // Update UI as soon as you get the data
                mNewsAdapter.notifyDataSetChanged();
            }

            // Fetch next post in group of 10 if there is any left
            if (mPostIdCounter < POST_IDs.size() && (mPostIdCounter % 10 != 0) && !mCanLoad)
                getStory(mPostIdCounter++, this);
            else
                mStoriesGVRefreshContainer.setRefreshing(false);
        }

        @Override
        public void onFailure(@NonNull Call<Post> call, @NonNull Throwable t) {
            Log.e(TAG, "Failure fetching post");
        }
    };

    /**
     * Callback to fetch List of Id's of Top stories
     */
    private Callback<List<Long>> mTopPostsIdsCallBack = new Callback<List<Long>>() {
        @Override
        public void onResponse(@NonNull Call<List<Long>> iCall, @NonNull Response<List<Long>> iResponse) {
            // Get the List of News/Stories/post Id's
            POST_IDs = iResponse.body();

            Log.e(TAG, "Current counter : " + mPostIdCounter);

            if (POST_IDs != null && POST_IDs.size() > 0) {
                // Fetching First story and hence subsequently others as well if available
                getStory(mPostIdCounter++, mPostCallBack);
            }

        }

        @Override
        public void onFailure(@NonNull Call<List<Long>> iCall, @NonNull Throwable iThrowable) {
            Log.e(TAG, "Failure fetching id's of top posts");
        }
    };


    /**
     * Method to create service for fetching Post information with a particular post Id
     *
     * @param iPositionOfId position of post id in the list
     * @param iCallback     Callback to read result
     */
    private void getStory(int iPositionOfId, Callback<Post> iCallback) {
        Log.v(TAG, "Current counter : " + iPositionOfId);

        final Call<Post> postCall = mHackerNewsService.getStoryItem(String.valueOf(POST_IDs
                .get(iPositionOfId)));
        postCall.enqueue(iCallback);

    }

    @Override
    public void onRefresh() {
        // On Refreshing using Swipe
        Log.e(TAG, "Swipe and refresh the GridView");

        getStory(mPostIdCounter++, mPostCallBack);
    }

    @Override
    public void onStart() {
        super.onStart();

        mCanLoad = false;
    }

    @Override
    public void onStop() {
        super.onStop();

        // Cannot load anymore posts as fragment is not visible
        mCanLoad = true;
    }

}

