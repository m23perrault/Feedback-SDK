package com.feedbacksdk.interfaces;

/**
 * Created by android-da on 12/27/18.
 */

/*
*Feedback & Rplies Call Interfaces
*InFeedbackCallback Interface calculate
*Feedback Response & Error Data.
* *********************************
*/
public interface FeedbackCallback {
    /*
    * In notifyResponse() Method:
    * Notify the Feedback Response Data
    */
    public void notifyResponse(String mResponse, String TAG);

    /*
   * In notifyError() Method:
   * Notify the Feedback Error Response
   */
    public void notifyError(String error);
}
