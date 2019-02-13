package com.feedbacksdk.activities;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.feedbacksdk.FeedbackRplyExecution;
import com.feedbacksdk.PutFeedbackMSGExecution;
import com.feedbacksdk.R;
import com.feedbacksdk.SDKInstance;
import com.feedbacksdk.adapters.FeedbackRplyAdapter;
import com.feedbacksdk.beans.FeedbackReplyModel;
import com.feedbacksdk.interfaces.FeedbackCallback;
import com.feedbacksdk.interfaces.PagginationCallback;
import com.feedbacksdk.utils.Constants;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;

public class FeedbackActivity extends BaseActivity implements FeedbackCallback {
    /*
     * TAG @param is used to print the Class Name
     * */
    String Tag = FeedbackActivity.this.getClass().getSimpleName();

    /*
   * Create the Object of Current Activity
   * */
    Activity mActivity = FeedbackActivity.this;

    /*
    * Declare Widgets IDs for the
    * Feedback Replies Layouts
    * */
    /*
    * RecyclerView
    * */
    RecyclerView feedbackReplyRV;

    /*
    * EditText
    * */
    EditText editMessageET;
    /*
    * ImageVIew
    * */
    ImageView imgPutMessageIV;
    /*
    * LinearLayout
    * */
    LinearLayout bottomLayoutLL;
    /*
    * ProgressBar
    * */
    ProgressBar circlePB;

    /*
    * Declare FeedbackRplyAdapter
    * */
    FeedbackRplyAdapter mFeedbackReplyAdapter;

    /*
    * Declare ArrayList to store the feedback-rplies messages
    * */
    ArrayList<FeedbackReplyModel> mMessagesArrayList = new ArrayList<>();

    /*
    * FeedbackRplyExecution Class is used to getting
    * By User.
    * */
    FeedbackRplyExecution mFeedbackRplyExecution;

    /*
    * PutFeedbackMSGExecution Class is used to put the Feedback Mesage
    *
    * */
    PutFeedbackMSGExecution mPutFeedbackMSGExecution;
    /*
    * FeedbackCallBack is the Interface which is used to
    * Return the Feedback Response & Error Data
    * */
    FeedbackCallback mFeedbackCallback;

    /*
    * Declare String
    * */
    String strDeviceID = "", strDeviceType = "", strSendMessage = "", strMessageValu = "";

    /*
    * APP ID:
    * */
    String strAppID = "";
    /*
    * Secret KEY
    * */
    String strSecretKey = "";

    /*
    * Is Paggination to Load Old
    * Feedback & Reply Messages
    * */
    boolean isPaggination = false;
    /*
    * Paggination Page Number
    * */
    int pageNum = 1;

    /*
    * LoadMore Feedback Messages & Replies
    *
    * */
    PagginationCallback mPagginationCallback = new PagginationCallback() {
        @Override
        public void getNextPageMessages() {
            if (isPaggination) {
                circlePB.setVisibility(View.VISIBLE);
                ++pageNum;
                executeFeedbackRplyAPI();
            } else {
                if (mMessagesArrayList.size() > 10)
                    showToast(mActivity, "No More feedback!");
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        setWidgetIDs();
        setClickListner();
        /*
        * if you have get All the msges
        * then send the "SendMessage"
        * @param = 0
        * if You hav put the rply again
        * then send the "SendMessage"
        * @param = 1
        * *
        * */
        strDeviceType = "android";
        strAppID = SDKInstance.getInstance().getAppID();
        strSecretKey = SDKInstance.getInstance().getSecretKey();
        strDeviceID = getDeviceUniqueID();
        mFeedbackCallback = (FeedbackCallback) FeedbackActivity.this;
        mFeedbackRplyExecution = new FeedbackRplyExecution(mActivity);
        mPutFeedbackMSGExecution = new PutFeedbackMSGExecution(mActivity);
        //Execute Api Getting Latest Messages
        mFeedbackRplyApi();
    }

    public void setWidgetIDs() {
        feedbackReplyRV = (RecyclerView) findViewById(R.id.feedbackReplyRV);
        editMessageET = (EditText) findViewById(R.id.editMessageET);
        imgPutMessageIV = (ImageView) findViewById(R.id.imgPutMessageIV);
        bottomLayoutLL = (LinearLayout) findViewById(R.id.bottomLayoutLL);
        circlePB = (ProgressBar) findViewById(R.id.circlePB);

    }

    public void setClickListner() {
        imgPutMessageIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                strSendMessage = "1";
                isPaggination = false;
                putFeedbackMsg();
            }
        });


    }

    private void putFeedbackMsg() {
        if (isNetworkAvailable(mActivity)) {
            /*
             * Execute Feedback Rplies Apis
             *
             * */
            if (editMessageET.getText().toString().trim().equals("")) {
                showToast(mActivity, "Please enter feedback");
            } else {
                strMessageValu = editMessageET.getText().toString().trim();
                showProgressDialog(mActivity);
                pageNum = 1;
                isPaggination = false;
                executeFeedbackMsgAPI();
            }

        } else {
            showToast(mActivity, getString(R.string.internetconnection));
        }
    }

    private void executeFeedbackMsgAPI() {
             /*
                * Execute the SDK API
                * Submit Feedback Methods
                *
                * @parsms:
                * 1) app_id : Check Out the web when you register your app on web, after that you can get app_id from SDK web console
                * 2) security_key : Check Out the web when you register your app on web, after that you can get security_key from SDK web console
                * 3) device_type : Please set the device type static "android || ios"
                * 4) device_id : Your Android Device Unique ID
                * 5) message : Your Feedback Message text
                * 6) strSendMessage : set this value 1
                * 7) reply_message : add the feedback message here.
                * */

        mPutFeedbackMSGExecution.executePutFeedbackMSGAPI(mActivity,
                mFeedbackCallback,
                strAppID,
                strSecretKey,
                strDeviceID,
                strDeviceType,
                strSendMessage,
                strMessageValu,
                pageNum
        );
    }

    private void mFeedbackRplyApi() {
        if (isNetworkAvailable(mActivity)) {
            showProgressDialog(mActivity);
            /*
             * Execute Feedback Rplies Apis
             *
             * */
            executeFeedbackRplyAPI();
        } else {
            showToast(mActivity, getString(R.string.internetconnection));
        }

    }

    private void executeFeedbackRplyAPI() {
        strSendMessage = "0";
       /*
                * Execute the SDK API
                * Submit Feedback Methods
                * @parsms:
                * 1) app_id : Check Out the web when you register your app on web, after that you can get app_id from SDK web console
                * 2) security_key : Check Out the web when you register your app on web, after that you can get security_key from SDK web console
                * 3) device_type : Please set the device type static "android || ios"
                * 4) device_id : Your Android Device Unique ID
                * 5) title : Your Feedback Title text
                * 6) message : Your Feedback Message text
                * */

        mFeedbackRplyExecution.executeFeedbackRplyAPI(mActivity,
                mFeedbackCallback,
                strAppID,
                strSecretKey,
                strDeviceID,
                strDeviceType,
                strSendMessage,
                pageNum);
    }

    /*
  * Override the Feedback Call Backs
  * Feedback Call Interfaces
  * InFeedbackCallback Interface calculate
  * Feedback Response & Error Data.
  * In notifyResponse() Method:
  * Notify the Feedback Response Data
  */
    @Override
    public void notifyResponse(String mResponse, String TAG) {
        hideProgressDialog();
        try {
            JSONObject mJsonObject = new JSONObject(mResponse);
            editMessageET.setText("");
            /*
            * if status Code == 200
            * It means your api request is working properly
            * And getting response properly
            * */
            if (circlePB.getVisibility() == View.VISIBLE) {
                circlePB.setVisibility(View.GONE);
            }
            if (mJsonObject.getString("status").equals("200")) {
                /*
                * JSON Parsing
                * */
                if (TAG.equals(SDKInstance.GETTING_ALL_MESSAGES_TAG)) {
                    ArrayList<FeedbackReplyModel> mTempAL = new ArrayList<>();
                    JSONArray mJsonArray = mJsonObject.getJSONArray("data");
                    for (int i = 0; i < mJsonArray.length(); i++) {
                        FeedbackReplyModel mModel = new FeedbackReplyModel();
                        JSONObject mDataObj = mJsonArray.getJSONObject(i);
                        if (mDataObj.getString("sender_user").equals(getDeviceUniqueID())) {
                            mModel.setIntChatType(Constants.VIEW_TYPE_FEEDBACK_MESSAGE);
                        } else {
                            mModel.setIntChatType(Constants.VIEW_TYPE_REPLY_MESSAGE);
                        }
                        if (!mDataObj.isNull("created_on"))
                            mModel.setCreated_on(mDataObj.getString("created_on"));
                        if (!mDataObj.isNull("sender_user"))
                            mModel.setSender_user(mDataObj.getString("sender_user"));
                        if (!mDataObj.isNull("message"))
                            mModel.setMessage(mDataObj.getString("message"));

                        mTempAL.add(mModel);
                    }

                    if (mTempAL.size() == 10) {
                        isPaggination = true;
                    } else if (mTempAL.size() < 10) {
                        isPaggination = false;
                    }
                    Collections.reverse(mMessagesArrayList);

                    mMessagesArrayList.addAll(mTempAL);

                    mTempAL.clear();

                    setAdapter();

                } else if (TAG.equals(SDKInstance.PUT_MESSAGES_TAG)) {
                    mMessagesArrayList.clear();
                    Log.e(Tag, "=====Put Message Reponse=====" + mResponse);
                    JSONArray mJsonArray = mJsonObject.getJSONArray("data");
                    for (int i = 0; i < mJsonArray.length(); i++) {
                        FeedbackReplyModel mModel = new FeedbackReplyModel();
                        JSONObject mDataObj = mJsonArray.getJSONObject(i);
                        if (mDataObj.getString("sender_user").equals(getDeviceUniqueID())) {
                            mModel.setIntChatType(Constants.VIEW_TYPE_FEEDBACK_MESSAGE);
                        } else {
                            mModel.setIntChatType(Constants.VIEW_TYPE_REPLY_MESSAGE);
                        }
                        if (!mDataObj.isNull("created_on"))
                            mModel.setCreated_on(mDataObj.getString("created_on"));
                        if (!mDataObj.isNull("sender_user"))
                            mModel.setSender_user(mDataObj.getString("sender_user"));
                        if (!mDataObj.isNull("message"))
                            mModel.setMessage(mDataObj.getString("message"));

                        mMessagesArrayList.add(mModel);
                    }

                    if (mMessagesArrayList.size() == 10) {
                        isPaggination = true;
                    } else if (mMessagesArrayList.size() < 10) {
                        isPaggination = false;
                    }

                    setAdapter();
                }


            }


              /*
            * if status Code == 401
            * It means your api is request is working properly
            * but getting some error
            * And : "message": "An error occurred. please try again later"
            * For this error please check the SDK web Console
            * Please check your @parameters which you have pass in SDK FeedbackReplyMethods
            * */
            else if (mJsonObject.getString("status").equals("400")) {
                String msg = mJsonObject.getString("message");
                showToast(mActivity, msg);
            }


            /*
            * if status Code == 401
            * It means your api is request is working properly
            * but getting some error
            * And : "message": "Unauthorized app"
            * For this error please check the SDK web Console
            * It means your App is not Registered on SDK web Console or
            * If your app is registered then please check your console for
            * "App ID" & "Secret Key" it means app_id or secret_key is wrong.
            * */
            else if (mJsonObject.getString("status").equals("401")) {
                String msg = mJsonObject.getString("message");
                showToast(mActivity, msg);
            }


           /*
            * if status Code == 403
            * It means your api is request is working properly
            * but getting some error
            * And : "message": "This app is disabled"
            * For this error please check the SDK web Console
            * and Enable the APP Settings.
            * */
            else if (mJsonObject.getString("status").equals("403")) {
                String msg = mJsonObject.getString("message");
                showToast(mActivity, msg);

            }

           /*
            * if status Code == 404
            * It means your api is request is working properly
            * but getting some error
            * And : "message": "No Feedback Found"
            * For this error please send and feedback first then you
            * will see the feedback - rplies messages here.
            * */
            else if (mJsonObject.getString("status").equals("404")) {
                String msg = mJsonObject.getString("message");
                showToast(mActivity, msg);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /*
   * In notifyError() Method:
   * Notify the Feedback Error Response
   */
    @Override
    public void notifyError(String error) {
        hideProgressDialog();
        showToast(mActivity, error);
    }

    /*
    * Setup the Feedback-Rply Messages
    * Adapter;
    * */
    private void setAdapter() {
        Collections.reverse(mMessagesArrayList);
        mFeedbackReplyAdapter = new FeedbackRplyAdapter(mActivity, mMessagesArrayList, mPagginationCallback);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(mActivity);
        feedbackReplyRV.setLayoutManager(mLayoutManager);
        feedbackReplyRV.setAdapter(mFeedbackReplyAdapter);
        feedbackReplyRV.scrollToPosition(mMessagesArrayList.size() - 1);
        feedbackReplyRV.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                feedbackReplyRV.scrollToPosition(mMessagesArrayList.size() - 1);
            }
        });
    }

}
