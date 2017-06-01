package com.napolean.nonomanews.ui.adapter;

import android.databinding.ViewDataBinding;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.napolean.nonomanews.BR;
import com.napolean.nonomanews.R;
import com.napolean.nonomanews.data.model.Post;

import java.util.List;

/**
 * Created by ravi on 5/23/17.
 */
public class NewsAdapter extends BaseAdapter {

    private List<Post> mNewsStories;
    private LayoutInflater mLayoutInflater;
    private FragmentActivity mHostActivity;

    public NewsAdapter(List<Post> iNewsStories, FragmentActivity iContext) {
        mNewsStories = iNewsStories;
        mLayoutInflater = LayoutInflater.from(iContext);
        mHostActivity = iContext;
    }

    @Override
    public int getCount() {
        return mNewsStories.size();
    }

    @Override
    public Object getItem(int iPosition) {
        return mNewsStories.get(iPosition);
    }

    @Override
    public long getItemId(int iPosition) {
        return iPosition;
    }

    @Override
    public View getView(int iPosition, View convertView, ViewGroup viewGroup) {

        PostViewHolder storiesHolder;

        if (convertView == null) {
            convertView = mLayoutInflater.inflate(R.layout.item_story, viewGroup, false);
            storiesHolder = new PostViewHolder(convertView);
            convertView.setTag(storiesHolder);
        } else {
            storiesHolder = (PostViewHolder) convertView.getTag();
        }

        // Databinding for the grid
        ViewDataBinding dataBinding =storiesHolder.getViewDataBinding();

        final Post post = mNewsStories.get(iPosition);
        final PostsClickHandler postsClickHandler = new PostsClickHandler(post, mHostActivity);

        // Following provides the data binding to the news grid view
        dataBinding.setVariable(BR.post, post);
        dataBinding.setVariable(BR.postHandlers, postsClickHandler);

        dataBinding.executePendingBindings();

        return convertView;
    }


}
