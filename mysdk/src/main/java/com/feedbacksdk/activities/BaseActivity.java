package com.feedbacksdk.activities;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.Window;
import android.widget.Toast;

import com.feedbacksdk.R;

public class BaseActivity extends AppCompatActivity {
    /*
    * Progress Dialog
    * */
    public Dialog progressDialog;

    /*
    * Check the all internet
    * Connections of your device
    * Is Internet is working or not
    * */
    public static boolean isNetworkAvailable(Context mContext) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    /*
    * Activity Create Method
    * */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
    }



    /*
    * Show the
    * Progress Dialog
    * */
    public void showProgressDialog(Activity mActivity) {
        progressDialog = new Dialog(mActivity);
        progressDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        progressDialog.setContentView(R.layout.dialog_progress);
        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setCancelable(false);
        progressDialog.show();
    }


    /*
    * Hide thie
    * Progress Dialog
    * */
    public void hideProgressDialog() {
        if (progressDialog != null) {
            if (progressDialog.isShowing())
                progressDialog.dismiss();
        }

    }


    /*
  * Getting the Android Device Unique ID
  *
  * */
    public String getDeviceUniqueID() {
        String strDeviceID = Settings.Secure.getString(getApplicationContext().getContentResolver(), Settings.Secure.ANDROID_ID);
        return strDeviceID;
    }


    /*
    * Show Custom Toast Message With gravity Center
    * */
    public void showToast(Activity mActivity,String strMsg){
        Toast mToast = Toast.makeText(mActivity,strMsg,Toast.LENGTH_SHORT);
        mToast.setGravity(Gravity.CENTER,0,0);
        mToast.show();
    }
}
