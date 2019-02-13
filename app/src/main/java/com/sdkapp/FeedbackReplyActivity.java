package com.sdkapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.feedbacksdk.AppRater;
import com.feedbacksdk.SDKInstance;
import com.feedbacksdk.activities.FeedbackActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FeedbackReplyActivity extends BaseActivity {
    /*
    * TAG @param is used to print the Class Name
    * */
    String Tag = FeedbackReplyActivity.this.getClass().getSimpleName();

    /*
    * Create the Object of Current Activity
    * */
    Activity mActivity = FeedbackReplyActivity.this;

    /*
    * Declare Widgets IDs for the
    * Feedback Replies Layouts
    * */
    @BindView(R.id.txtSendFeedbackTV)
    TextView txtSendFeedbackTV;

    @BindView(R.id.txtRateFeedbackTV)
    TextView txtRateFeedbackTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback_reply);
        ButterKnife.bind(this);
        /*
        * Firslty Define the SDK
        * @param SDK_APP_ID
        * @param SDK_APP_SECRET_KEY
        * The SDKInstance is Mendatory for both
        * App Rater & Feedback
        * */
        new SDKInstance(mActivity, BaseActivity.SDK_APP_ID, BaseActivity.SDK_APP_SECRET_KEY);

        /*Send Rating
        * Normal or Default
        * Text as it is...
        * */
        //AppRater mAppRater = new AppRater(mActivity,"com.freejobsnews.paras");
        //mAppRater.show();

        /*
        * Customizationwith Custom Text for
        * Feedback & Rating Alert Dialog
        * */
        //Feedback
        AppRater mAppRater = new AppRater(mActivity,"com.freejobsnews.paras");


        //Rating
        mAppRater.setTitle("Rate Our App")
                .setRateText("Hi! If you like this app, Then please rate & review on google play store")
                .setPositiveButtonText("Yes! Rate Now")
                .setNegativeButtonText("No, Thanks")
                .setMayBeLaterButtonText("Next Time")
                .setBeforeDaysPrompt(3)
                .setBeforeAppLaunchPrompt(5);

        //Feedback
        mAppRater.setSelectRateText("Are you happy with app, Do you want to send feedback or rating for app?")
                 .setSelectPositiveButtonText("Submit")
                 .setSelectNegativeButtonText("Cancel")
                 .show();


    }


    @OnClick({R.id.txtSendFeedbackTV})
    public void onViewClicked(View mView) {
        switch (mView.getId()) {
            case R.id.txtSendFeedbackTV:
                startActivity(new Intent(mActivity, FeedbackActivity.class));
                break;
        }
    }


}
