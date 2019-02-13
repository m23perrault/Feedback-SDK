package com.feedbacksdk;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.feedbacksdk.activities.FeedbackActivity;
import com.feedbacksdk.utils.SDKPreference;
import com.feedbacksdk.utils.Utilities;

/**
 * Created by android-da on 1/28/19.
 */

public class AppRater {

    private final String TAG = AppRater.class.getSimpleName();
    private final Context context;

    /*
    * Dialog for Select Local Feedback or Google PlayStore Review & Rating
    * Click on "NO" for>>* Select Local Feedback about
    * Click on "YES" for>>* Review & Rating on Google Play Store
    * */
    private final String DEFAULT_SELECT_RATE_TEXT = "Do you like iPhemeris?";
    private final String DEFAULT_SEELCT_YES = "Yes";
    private final String DEFAULT_SEELCT_NO = "No";


    /*
    * Rater Dialog Defaults Texts
    * */
    private final String DEFAULT_TITLE = "Rate Me";
    private final String DEFAULT_RATE_TEXT = "Hi! If you like this App, can you please take a few minutes to rate it? It help so much when you do , thanks!";
    private final String DEFAULT_YES_RATE = "Yes";
    private final String DEFAULT_MAY_BE_LATER = "Maybe Later";
    private final String DEFAULT_NO_THANKS = "No, Thanks";
    private final int DEFAULT_DAYS_BEFORE_PROMPT = 3;
    private final int DEFAULT_LAUNCH_BEFORE_PROMPT = 7;

    private String mPackageName;
    private String selectrateText;
    private String selectYesButton;
    private String selectNoButton;
    private String title;
    private String rateText;
    private String yesButton;
    private String noThanksButton;
    private String mayBeLaterButton;
    private int daysPrompt;
    private int appLaunchPrompt;


    /*
    * Default Constructor
    * */
    public AppRater(Context context, String mPackageName) {
        this.context = context;
        this.mPackageName = mPackageName;
    }

    /*
    * Intent to set the Rating on App in GooglePlayStore
    * */
    private static void sendRating(Context mContext, String strUrl) {
        Intent i = new Intent(android.content.Intent.ACTION_VIEW);
        i.setData(Uri.parse(strUrl));
        mContext.startActivity(i);
    }

    /*
    *Methods for App FeedbackRating Dialog.
    * */
    /*
    * Set Rate Text
    * */
    public AppRater setSelectRateText(String selectrateText) {
        this.selectrateText = selectrateText;
        return this;
    }

    /*
    * Set Positive Button Yes/Submit/DONE Text etc...
    * */
    public AppRater setSelectPositiveButtonText(String btnYesText) {
        this.selectYesButton = btnYesText;
        return this;
    }

    /*
    * Set Negative Button No/Cancel Text etc...
    * */
    public AppRater setSelectNegativeButtonText(String btnNoText) {
        this.selectNoButton = btnNoText;
        return this;
    }

    /*
    * Show the Feedback/Rating Dialog
    * */
    public void show() {
        sendRateFeedback(context, mPackageName);
    }

    public void sendRateFeedback(final Context mContext, final String mPackageName) {
        /*
        * Check the Dynamically Value of
        * Before Days Prompt Value
        * Before App Launch Counts
        * */
        int intDaysPrompt = (daysPrompt == 0) ? DEFAULT_DAYS_BEFORE_PROMPT : daysPrompt;
        int intLaunchPrompt = (appLaunchPrompt == 0) ? DEFAULT_LAUNCH_BEFORE_PROMPT : appLaunchPrompt;

        Log.e(TAG,"===Days==="+intDaysPrompt);
        Log.e(TAG,"===App Launch==="+intLaunchPrompt);

        if (SDKPreference.readInteger(mContext, SDKPreference.APP_OPEN_COUNT, 0) == intLaunchPrompt) {

            SharedPreferences preferences = SDKPreference.getPreferences(mContext);
            SharedPreferences.Editor editor = preferences.edit();
            editor.remove(SDKPreference.APP_OPEN_COUNT);
            editor.commit();

            if (SDKPreference.readBoolean(mContext, SDKPreference.NO_THANKS_RATE, false) == false && SDKPreference.readBoolean(mContext, SDKPreference.YES_RATE, false) == false) {
                showRateFeedbackDialog(mContext, mPackageName);
            }
        } else {
            /*
            * Calculate two Time Diffrence
            * */
             //convert Day into minutes
            /**/
            int mFinalminutes = Utilities.convertDaysToMinutes(intDaysPrompt);

            int mDiffInMinute = Utilities.getDiffrenceBetweenTwoTimes(SDKPreference.readString(mContext, SDKPreference.CURRENT_SAVED_TIME, ""), Utilities.getCurrentTime());

            if (mDiffInMinute >= mFinalminutes) {

                SharedPreferences preferences = SDKPreference.getPreferences(mContext);
                SharedPreferences.Editor editor = preferences.edit();
                editor.remove(SDKPreference.CURRENT_SAVED_TIME);
                editor.commit();

                if (SDKPreference.readBoolean(mContext, SDKPreference.NO_THANKS_RATE, false) == false && SDKPreference.readBoolean(mContext, SDKPreference.YES_RATE, false) == false) {
                    showRateFeedbackDialog(mContext, mPackageName);
                }
            }
        }
    }

    /*****
     *Show the Rate/ Feedback Dialog
     * @Param mContext
     * @Param mPackageName
     */
    public void showRateFeedbackDialog(final Context mContext, final String mPackageName) {
        final Dialog alertDialog = new Dialog(mContext);
        alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        alertDialog.setContentView(R.layout.dialog_rate_feedback);
        alertDialog.setCanceledOnTouchOutside(true);
        alertDialog.setCancelable(true);
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        /*
        * Set Up the Dialog Widget IDS:
        * @WidgetID : txtNoTV>> To Open the Local Feedback SDK to put the Feedback
        * @WidgetID : txtYesTV>> To Open the Google PlayStore Rationg Dialog to Open the PlayStore App
        * & Rate the App
        * */
        TextView txtRateTextTV = (TextView) alertDialog.findViewById(R.id.txtRateTextTV);
        TextView txtYesTV = (TextView) alertDialog.findViewById(R.id.txtYesTV);
        TextView txtNoTV = (TextView) alertDialog.findViewById(R.id.txtNoTV);

        /*
        * Set Dynamically Text To Dialog
        * */
        String strRateText = (selectrateText == null) ? DEFAULT_SELECT_RATE_TEXT : selectrateText;
        String strYesText = (selectYesButton == null) ? DEFAULT_SEELCT_YES : selectYesButton;
        String strNoText = (selectNoButton == null) ? DEFAULT_SEELCT_NO : selectNoButton;
        txtRateTextTV.setText(strRateText);
        txtYesTV.setText(strYesText);
        txtNoTV.setText(strNoText);

        /*
        * Perform Action When click on NO
        * Then open the Local FEEDBACK SDK..
        * */
        txtNoTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
                mContext.startActivity(new Intent(mContext, FeedbackActivity.class));
            }
        });
        /*
        * Perform Action When click on YES
        * Then open the Dialog To Open the Google Play Store ..
        * */
        txtYesTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
                showRateDialog(mContext, mPackageName);
            }
        });

        alertDialog.show();
    }

    /*
    * Customization Methods for Rating...Alert Dialog
    *
    * */
    /*
    * Set Title Text
    * */
    public AppRater setTitle(String title) {
        this.title = title;
        return this;
    }

    /*
    * Set Rate Text
    * */
    public AppRater setRateText(String rateText) {
        this.rateText = rateText;
        return this;
    }

    /*
    * Set Positive Button Text
    * */
    public AppRater setPositiveButtonText(String yesButton) {
        this.yesButton = yesButton;
        return this;
    }

    /*
    * Set Negative/Never Button Text
    * */
    public AppRater setNegativeButtonText(String noThanksButton) {
        this.noThanksButton = noThanksButton;
        return this;
    }

    /*
    * Set MayBeLater Button Text
    * */
    public AppRater setMayBeLaterButtonText(String mayBeLaterButton) {
        this.mayBeLaterButton = mayBeLaterButton;
        return this;
    }

    /*
    * Set the BeforeDays Prompt Value
    * */
    public AppRater setBeforeDaysPrompt(int daysPrompt) {
        this.daysPrompt = daysPrompt;
        return this;
    }

    /*
    * Set the App Launches Prompt Value
    */
    public AppRater setBeforeAppLaunchPrompt(int appLaunchPrompt) {
        this.appLaunchPrompt = appLaunchPrompt;
        return this;
    }

    /*****
     *Show the Rate Dialog
     * @Param mContext
     * @Param mPackageName
     */
    public void showRateDialog(final Context mContext, String mPackageName) {
        final String strAppRateLink = SDKInstance.GOOGLE_PLAY_STORE_LINK + mPackageName;

        final Dialog alertDialog = new Dialog(mContext);
        alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        alertDialog.setContentView(R.layout.dialog_rate_us);
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.setCancelable(false);
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        /*
        * Set Up the Dialog Widget IDS:
        * @WidgetID : txtYesTV>> To Open the Google Play Store App to Rate the App
        * @WidgetID : txtMaybeLaterTV>> To Dismiss the Dialog Rate the app later
        * @WidgetID : txtNoThanksTV>> Dismiss the Dialog No Rate
        * */
        TextView txtTitleTV = (TextView) alertDialog.findViewById(R.id.txtTitleTV);
        TextView txtAboutRateTV = (TextView) alertDialog.findViewById(R.id.txtAboutRateTV);
        TextView txtYesTV = (TextView) alertDialog.findViewById(R.id.txtYesTV);
        TextView txtMaybeLaterTV = (TextView) alertDialog.findViewById(R.id.txtMaybeLaterTV);
        TextView txtNoThanksTV = (TextView) alertDialog.findViewById(R.id.txtNoThanksTV);
        /*
        * Getting the Dynamically
        * Values for Rating Alert Dialog
        * */
        String strTitle = (title == null) ? DEFAULT_TITLE : title;
        String strRateText = (rateText == null) ? DEFAULT_RATE_TEXT : rateText;
        String strYesText = (yesButton == null) ? DEFAULT_YES_RATE : yesButton;
        String strNoThanksText = (noThanksButton == null) ? DEFAULT_NO_THANKS : noThanksButton;
        String strMaybeLaterText = (mayBeLaterButton == null) ? DEFAULT_MAY_BE_LATER : mayBeLaterButton;
        int intDaysPrompt = (daysPrompt == 0) ? DEFAULT_DAYS_BEFORE_PROMPT : daysPrompt;
        int intLaunchPrompt = (appLaunchPrompt == 0) ? DEFAULT_LAUNCH_BEFORE_PROMPT : appLaunchPrompt;
        /*
        * SetUp Dynamically Text & Values for
        * Rating Alert Dialog
        * */
        txtTitleTV.setText(strTitle);
        txtAboutRateTV.setText(strRateText);
        txtYesTV.setText(strYesText);
        txtNoThanksTV.setText(strNoThanksText);
        txtMaybeLaterTV.setText(strMaybeLaterText);

        /*
        * Perform the YES Button click Action
        * */
        txtYesTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
                SDKPreference.writeBoolean(mContext, SDKPreference.YES_RATE, true);
                sendRating(mContext, strAppRateLink);
            }
        });

        /*
        * Preform the MayBeLater Button click Action
        * */
        txtMaybeLaterTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SDKPreference.writeBoolean(mContext, SDKPreference.MAYBE_LATER_RATE, true);
                alertDialog.dismiss();
            }
        });

        /*
        * Perfrom the NoThanks button Click Action
        * */
        txtNoThanksTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SDKPreference.writeBoolean(mContext, SDKPreference.NO_THANKS_RATE, true);
                alertDialog.dismiss();
            }
        });


        alertDialog.show();
    }


}
