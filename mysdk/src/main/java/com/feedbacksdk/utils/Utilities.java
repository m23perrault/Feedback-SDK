package com.feedbacksdk.utils;

import android.util.Log;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by android-da on 1/31/19.
 */

public class Utilities {

    /*
    * Getting the Current Time
    * */
    public static String getCurrentTime(){
        String localTime = "";
        try{
            Calendar cal = Calendar.getInstance();
            Date currentLocalTime = cal.getTime();
            DateFormat date2 = new SimpleDateFormat("HH:mm a");
            localTime = date2.format(currentLocalTime);
        }catch (Exception e){
            e.printStackTrace();
        }

        return localTime;
    }



    /*
    * Getting Two Time Diffrence in Minutes
    * */
    public static int getDiffrenceBetweenTwoTimes(String str1, String str2){
        //3 Days == 72 Hours = 4320 Minutes
            int hours;
            int min = 0;
            int days;
            long difference;
            try {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
                Date date1 = simpleDateFormat.parse(str1);
                Date date2 = simpleDateFormat.parse(str2);

                difference = date2.getTime() - date1.getTime();
                days = (int) (difference / (1000  * 60  * 60 * 24));
                hours = (int) ((difference - (1000 * 60 * 60 * 24 * days)) / (1000 * 60 * 60));
                min = (int) (difference - (1000 * 60 * 60 * 24 * days) - (1000 * 60 * 60 * hours))
                    / (1000 * 60);
                hours = (hours < 0 ? -hours : hours);
                Log.e("======= Hours", " :: " + hours);
                return min;
            } catch (ParseException e) {
                e.printStackTrace();
            }

            return min;
    }


    public static int convertDaysToMinutes(int mDays){
        int mFinalMinute = 0;

        mFinalMinute = mDays * 24 * 60;

        return mFinalMinute;
    }
}
