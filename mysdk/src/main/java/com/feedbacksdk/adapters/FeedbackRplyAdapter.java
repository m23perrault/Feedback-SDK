package com.feedbacksdk.adapters;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.feedbacksdk.R;
import com.feedbacksdk.beans.FeedbackReplyModel;
import com.feedbacksdk.holders.ItemFeedbackViewHolder;
import com.feedbacksdk.holders.ItemRplyViewHolder;
import com.feedbacksdk.interfaces.PagginationCallback;
import com.feedbacksdk.utils.Constants;

import java.util.ArrayList;

/**
 * Created by android-da on 6/7/18.
 */


/*
* Feedback Messages Adapter
* */
public class FeedbackRplyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    /*
    * Activity
    * */
    Activity mActivity;
    /*
    * ArrayList
    * */
    ArrayList<FeedbackReplyModel> mArrayList;
    /*
    * Paggination Interface
    * */
    PagginationCallback mPagginationCallback;

    /*
    * Default Adapter Constructor
    * */
    public FeedbackRplyAdapter(Activity mActivity, ArrayList<FeedbackReplyModel> mArrayList,PagginationCallback mPagginationCallback) {
        this.mActivity = mActivity;
        this.mArrayList = mArrayList;
        this.mPagginationCallback = mPagginationCallback;
    }


    /*
    *
    * Implemented Method onCreateViewHolder
    * where set the recyclerview item layout
    * */
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == Constants.VIEW_TYPE_FEEDBACK_MESSAGE) {
            View view = LayoutInflater.from(mActivity).inflate(R.layout.item_chat_right, parent, false);
            return new ItemFeedbackViewHolder(view);
        } else if (viewType == Constants.VIEW_TYPE_REPLY_MESSAGE) {
            View view = LayoutInflater.from(mActivity).inflate(R.layout.item_chat_left, parent, false);
            return new ItemRplyViewHolder(view);
        }
        return null;
    }


    /*
    * Implemented Method onBindViewHolder
    * where set up the data for recyclerview item
    * */
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder.getItemViewType() == Constants.VIEW_TYPE_FEEDBACK_MESSAGE) {
            ((ItemFeedbackViewHolder) holder).bindData(mActivity, (FeedbackReplyModel) mArrayList.get(position));
        } else if (holder.getItemViewType() == Constants.VIEW_TYPE_REPLY_MESSAGE) {
            ((ItemRplyViewHolder) holder).bindData(mActivity, (FeedbackReplyModel) mArrayList.get(position));
        }

        if (holder.getLayoutPosition()  == 0){
            Log.e("TAG","======Adapter Position====");
            mPagginationCallback.getNextPageMessages();
        }
    }


    /*
    * Implemented Method
    * Getting the RecyclerView Item Position
    * */
    @Override
    public int getItemViewType(int position) {
        if (mArrayList.get(position).getIntChatType() == 1)
            return Constants.VIEW_TYPE_FEEDBACK_MESSAGE;
        else if (mArrayList.get(position).getIntChatType() == 0)
            return Constants.VIEW_TYPE_REPLY_MESSAGE;
        return 0;
    }

    /*
    * Implemented Method
    * Getting RecyclerView List/Items Counts
    * */
    @Override
    public int getItemCount() {
        if (mArrayList == null)
            return 0;
        return mArrayList.size();
    }
}
