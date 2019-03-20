package com.sdkapp;

import android.app.Application;

import com.feedbacksdk.SDKInstance;

/**
 * Created by android-da on 3/13/19.
 */

public class SDKApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
           /*
        * Firslty Define the SDK
        * @param SDK_APP_ID
        * @param SDK_APP_SECRET_KEY
        * The SDKInstance is Mendatory for both
        * App Rater & Feedback
        * */

        new SDKInstance(getApplicationContext(), BaseActivity.SDK_APP_ID, BaseActivity.SDK_APP_SECRET_KEY);
    }
}
