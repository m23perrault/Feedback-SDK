package com.feedbacksdk.holders;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.feedbacksdk.R;
import com.feedbacksdk.beans.FeedbackReplyModel;

/**
 * Created by android-da on 6/7/18.
 */

/*
* ItemRplyViewHolder
* */
public class ItemRplyViewHolder extends RecyclerView.ViewHolder {
    public TextView txtMessageTV,txtDateTV;

    public ItemRplyViewHolder(View itemView) {
        super(itemView);
        txtDateTV = (TextView) itemView.findViewById(R.id.txtDateTV);
        txtMessageTV = (TextView) itemView.findViewById(R.id.txtMessageTV);
    }

    public void bindData(final Context context, final FeedbackReplyModel tempValue) {
        txtDateTV.setText(tempValue.getCreated_on());
        txtMessageTV.setText(tempValue.getMessage());
    }
}
