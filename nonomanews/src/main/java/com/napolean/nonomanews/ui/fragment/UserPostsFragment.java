package com.napolean.nonomanews.ui.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.napolean.nonomanews.R;
import com.napolean.nonomanews.data.model.User;
import com.napolean.nonomanews.data.remote.NonomaNewsService;
import com.napolean.nonomanews.data.remote.RetrofitHelper;
import com.napolean.nonomanews.databinding.FragmentUserPostsBinding;
import com.napolean.nonomanews.ui.utils.NonomaNewsConstants;
import com.napolean.nonomanews.ui.utils.NonomaNewsUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ravi on 5/29/17.
 */
public class UserPostsFragment extends Fragment {

    public static final String TAG = UserPostsFragment.class.getSimpleName();
    private String mUserName;
    private FragmentUserPostsBinding mFragmentUserPostsBinding;
    private NonomaNewsService mHackerNewsService;


    public static UserPostsFragment newInstance(String iUserName) {
        Bundle arg = new Bundle();
        arg.putString(NonomaNewsConstants.BundleKeys.USER_NAME, iUserName);

        UserPostsFragment userFragment = new UserPostsFragment();
        userFragment.setArguments(arg);
        return userFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mFragmentUserPostsBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_user_posts, container, false);

        Bundle args = getArguments();
        if (args != null) {
            mUserName = args.getString(NonomaNewsConstants.BundleKeys.USER_NAME);

            // Init HackerNewsService
            mHackerNewsService = RetrofitHelper.newHackerNewsService();

            // Set name
            mFragmentUserPostsBinding.setName(mUserName);

            // Launch the service
            mHackerNewsService.getUser(mUserName).enqueue(mUserDetailsCallback);
        }

        return mFragmentUserPostsBinding.getRoot();
    }

    /**
     * This callback has to run only once
     */
    private Callback<User> mUserDetailsCallback = new Callback<User>() {
        @Override
        public void onResponse(@NonNull Call<User> iCall, @NonNull Response<User> iResponse) {
            User user = iResponse.body();

            if (user != null) {
                mFragmentUserPostsBinding.setUser(user);
            } else {
                NonomaNewsUtils.showToast(getString(R.string.no_valid_user_details));
            }
        }

        @Override
        public void onFailure(@NonNull Call<User> iCall, @NonNull Throwable iThrowable) {
            Log.e(TAG, getString(R.string.failure_fetching_user_details));
        }
    };


}
