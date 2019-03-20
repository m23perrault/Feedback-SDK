package com.feedbacksdk;

import android.content.Context;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.feedbacksdk.utils.SDKPreference;
import com.feedbacksdk.utils.Utilities;

import java.util.HashMap;
import java.util.Map;

/**********************************
 * Created by android-da on 12/27/18.
 * SDKInstance Class
 * This Class is Used for Taking all common values
 * And Here we will declare the Volley Methods
 ************************************/

public class SDKInstance {
    /*
    *TAG @Variable is Used to Print the Class Name
    **/
    public static final String TAG = SDKInstance.class.getSimpleName();
    /*CONNECTION_TIMEOUT @Variable is Used to define the Volley
    * Library Request Time
    */
    public static final int CONNECTION_TIMEOUT = 100000 * 1000;

    /*
    * SET the Tags Values for Interfaces
    * */
    /*
    * GET all feedback & reply messages
    * */
    public static final String GETTING_ALL_MESSAGES_TAG = "reply";
    /*
    * Put the feedback message
    * */
    public static final String PUT_MESSAGES_TAG = "pu_msg";
    /*
    * GOOGLE PLAY STORE LINK
    * */
    public static String GOOGLE_PLAY_STORE_LINK = "https://play.google.com/store/apps/details?id=";
    /*
    * APP ID:
    * */
    public static String strAppID = "";
    /*
    * Secret KEY
    * */
    public static String strSecretKey = "";
    /*
    * SDKInstance, This  @Variable is the instance of class
    * */
    private static SDKInstance mSDKInstance;
    /*
    * SERVER_URL
    * This is the SDK SERVER URL
    * */
    private String SERVER_URL = "http://www.apptenium.com/index.php?r=api/v1/";
    private String FEEDBACK_API = SERVER_URL + "feedback/create";
    private String REPLY_API = SERVER_URL + "reply/create" + "&per-page=10" + "&page=";//&page=2&per-page=10
    private String APP_DETAILS_API = SERVER_URL + "feedback/install";
    /*
    * This is Request Class of Volley Library
    * */
    private RequestQueue mRequestQueue;

    /*
    * SDKInstance()
    * This method is Defualt Constuctor
    * of the Class
    * */
    public SDKInstance() {
        mSDKInstance = this;
    }

    /*
    * Constructor with Pass the @params
    * APP_Id & Secret_Key
    * */
    public SDKInstance(Context mContext, String appID, String secretKey) {
        strAppID = appID;
        strSecretKey = secretKey;
        /*
        * Calculate the App Open Count
        * */
        calculaterAppOpenCount(mContext);

        /*
        * Submit App Details
        * */
        mAppDetail(mContext);
    }

    /*
    * getInstance()
    * This method is used to get the Intance of the class
    * If there this class instance is null
    * then this method can get the Intance class
    * */
    public static SDKInstance getInstance() {
        //When Implement
        //if there is no instance available... create new one
        if (mSDKInstance == null) {
            mSDKInstance = new SDKInstance();
        }
        return mSDKInstance;
    }



    /*
    * Calculate the App Open Count
    * */
    private void calculaterAppOpenCount(Context mContext) {
        int mCount = SDKPreference.readInteger(mContext, SDKPreference.APP_OPEN_COUNT, 0);
        mCount++;
        SDKPreference.writeInteger(mContext, SDKPreference.APP_OPEN_COUNT, mCount);
        if (SDKPreference.readString(mContext, SDKPreference.CURRENT_SAVED_TIME, "").equals("") && SDKPreference.readString(mContext, SDKPreference.CURRENT_SAVED_TIME, "").length() == 0)
            SDKPreference.writeString(mContext, SDKPreference.CURRENT_SAVED_TIME, Utilities.getCurrentTime());

        Log.e(TAG, "==App Open Count==" + mCount);
    }

    /*
    * Get AppID
    * */
    public String getAppID() {
        return strAppID;
    }

    /*
    * Get Secret Key
    * */
    public String getSecretKey() {
        return strSecretKey;
    }

    /*
    * getFeedbackUrl()
    * This method is used to Get the
    * Feedback Url of Server where we push
    * the feedback
    * */
    public String getFeedbackUrl() {
        return FEEDBACK_API;
    }

    /*
    * getReplyUrl()
    * This method is used to Get the
    * Feedback- Replies Url of Server where we push
    * the feedback
    * */
    public String getReplyUrl() {
        return REPLY_API;
    }

    /***************
     *getRequestQueue()
     *This Method is used for Request Queue
     **************/
    public RequestQueue getRequestQueue(Context mContext) {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(mContext);
        }
        return mRequestQueue;
    }

    /***************
     *addToRequestQueue()
     *Volley Request Method
     *This Method is used for Request
     *with any TAG Value
     **************/
    public <T> void addToRequestQueue(Context mContext, Request<T> req, String tag) {
        // set the default tag if tag is empty
        req.setRetryPolicy(new DefaultRetryPolicy(
                CONNECTION_TIMEOUT,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        getRequestQueue(mContext).add(req);
    }

    /***************
     *addToRequestQueue()
     *Volley Request Method
     *This Method is used for Request
     *without any TAG Value
     **************/
    public <T> void addToRequestQueue(Context mContext, Request<T> req) {
        req.setRetryPolicy(new DefaultRetryPolicy(
                CONNECTION_TIMEOUT,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        req.setTag(TAG);
        getRequestQueue(mContext).add(req);
    }



    /*
*
* Execute App Details Api
*
* */
    public void mAppDetail(final Context mContext) {
        /*
        * Volley Libray StringRequest
        * */
        StringRequest request = new StringRequest(Request.Method.POST, APP_DETAILS_API, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    /*Feedback Interface Reponse Method*/
                    Log.e(TAG, "**Response**" + response);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                /*Feedback Interface Error Method*/
                Log.e(TAG, "**Error**" + error.toString());
            }
        }) {
            /*
            * Header of the Webservices
            * */
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                return headers;
            }

            /*
            * @Parameters of the Api
            * */
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> params = new HashMap<>();
                params.put("app_id", strAppID);
                params.put("secret_key", strSecretKey);
                params.put("device_token", getDeviceUniqueID(mContext));
                params.put("device_type", "android");//device_type
                return params;
            }
        };

        /*
        *Add the Request to volley Queue
        * */
        addToRequestQueue(mContext, request);
    }



    /*
  * Getting the Android Device Unique ID
  *
  * */
    public String getDeviceUniqueID(Context mContext) {
        String strDeviceID = Settings.Secure.getString(mContext.getContentResolver(), Settings.Secure.ANDROID_ID);
        return strDeviceID;
    }

}













