package com.feedbacksdk;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.feedbacksdk.interfaces.FeedbackCallback;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by android-da on 12/27/18.
 * FeedbackRplyExecution Class
 * This class can use the volley Library
 * Methods and Getting the List of feedback and
 * its Rplies
 */

public class FeedbackRplyExecution {
    /*
    *TAG @Variable is Used to Print the Class Name
    * */
    String TAG = FeedbackRplyExecution.this.getClass().getSimpleName();
    /*
    * It provides access to things
    * */
    Context mContext;

    /*
    * Default Constuctor
    **/
    public FeedbackRplyExecution(Context mContext) {
        this.mContext = mContext;
    }


     /*
    * executeFeedbackRplyAPI()
    * This Method is use to Getting the User feedback-rplies
    * To Server End || which is Stored in Database
    * To User better Experience
    * Execute the SDK API
    * FeedbackRplies Methods
    * @parsms:
    * 1) app_id : Check Out the web when you register your app on web, after that you can get app_id from SDK web console
    * 2) security_key : Check Out the web when you register your app on web, after that you can get security_key from SDK web console
    * 3) device_id : Your Android Device Unique ID
    * 4) sendMessage : Set Default value == 0 for getting all the feedback and rply messages
    * 5) deviceTyp : android
    * 6) reply_message : Blank for getting all feedback & reply messages
    * */

    public void executeFeedbackRplyAPI(Context mContext, final FeedbackCallback mFeedbackCallback, final String app_id, final String secret_key,final String device_id,final String device_type, final String sendMessage,final int pageNum) {
        /*
        * strAPIUrl this @param is used to webservice URL
        * */
        String strAPIUrl = SDKInstance.getInstance().getReplyUrl() + ""+pageNum;
        /*
        * Volley Libray StringRequest
        * */
        StringRequest request = new StringRequest(Request.Method.POST, strAPIUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    /*Feedback Interface Reponse Method*/
                    mFeedbackCallback.notifyResponse(response, SDKInstance.GETTING_ALL_MESSAGES_TAG);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                /*Feedback Interface Error Method*/
                mFeedbackCallback.notifyError(error.toString());
            }
        }) {
            /*
            * Header of the Webservices
            * */
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type","application/x-www-form-urlencoded");
                return headers;
            }

            /*
            * @Parameters of the Api
            * */
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> params = new HashMap<>();
                params.put("app_id", app_id);
                params.put("secret_key", secret_key);
                params.put("device_id", device_id);
                params.put("sendMessage", sendMessage);
                params.put("reply_message", "");
                params.put("device_type", device_type);
                return params;
            }
        };

        /*
        *Add the Request to volley Queue
        * */
        SDKInstance.getInstance().addToRequestQueue(mContext, request);
    }


}



