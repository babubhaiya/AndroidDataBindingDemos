package com.napolean.nonomanews.ui.adapter;

import android.databinding.DataBindingUtil;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.napolean.nonomanews.R;
import com.napolean.nonomanews.data.model.Comment;
import com.napolean.nonomanews.databinding.ItemCommentBinding;

import java.util.List;

/**
 * Created by ravi on 5/31/17.
 */
public class CommentsAdapter extends RecyclerView.Adapter<CommentsAdapter.CommentsViewHolder> {

    private List<Comment> mCommentsList;
    private LayoutInflater mLayoutInflater;

    public CommentsAdapter(FragmentActivity iHostActivity, List<Comment> iCommentsList) {
        mLayoutInflater = LayoutInflater.from(iHostActivity);
        mCommentsList = iCommentsList;
    }

    @Override
    public CommentsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemCommentBinding itemCommentBinding = DataBindingUtil.
                inflate(mLayoutInflater, R.layout.item_comment, parent, false);

        // Create and initiate and instance of Comment Item View Holder
        return new CommentsViewHolder(itemCommentBinding.getRoot());
    }

    @Override
    public void onBindViewHolder(CommentsViewHolder holder, int position) {
        // Bind comment to the particular
        holder.mViewDataBinding.setComment(mCommentsList.get(position));
        holder.mViewDataBinding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return mCommentsList.size();
    }


    /**
     * Class forming all the views showing comments details
     */
    class CommentsViewHolder extends RecyclerView.ViewHolder {

        TextView mCommentText;
        TextView mCommentAuthor;
        TextView mCommentDate;

        ItemCommentBinding mViewDataBinding;

        public CommentsViewHolder(View iCommentView) {
            super(iCommentView);

            mCommentText = (TextView)iCommentView.findViewById(R.id.comment_content_txv);
            mCommentAuthor = (TextView)iCommentView.findViewById(R.id.comment_by_name_txv);
            mCommentDate = (TextView)iCommentView.findViewById(R.id.comment_date_txv);

            mViewDataBinding = DataBindingUtil.bind(iCommentView);
        }
    }

}
