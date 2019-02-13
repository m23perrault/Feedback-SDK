package com.feedbacksdk.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

/*
* SDK SharedPrefrences Class
* */
public class SDKPreference {
    /*
    * Prefrence Name
    * */
    public static final String PREF_NAME = "SDK_PREF";
    /*
    * SharedPrefrences MODE
    * */
    public static final int MODE = Context.MODE_PRIVATE;
    /*
    * @Param YES_RATE
    * @Param MAYBE_LATER_RATE
    * @Param NO_THANKS_RATE
    * @Param APP_OPEN_COUNT
    * */
    public static final String YES_RATE = "yes_rate";
    public static final String MAYBE_LATER_RATE = "maybe_later";
    public static final String NO_THANKS_RATE = "no_thanks";
    public static final String APP_OPEN_COUNT = "app_open_count";
    public static final String CURRENT_SAVED_TIME = "current_saved_time";


    /*
    * Write Boolean Prefrences
    * */
    public static void writeBoolean(Context context, String key, boolean value) {
        getEditor(context).putBoolean(key, value).commit();
    }
     /*
      * Read Boolean Prefrences
      * */
    public static boolean readBoolean(Context context, String key,
                                      boolean defValue) {
        return getPreferences(context).getBoolean(key, defValue);
    }

    /*
    * Write Integer Prefrences
    * */
    public static void writeInteger(Context context, String key, int value) {
        getEditor(context).putInt(key, value).commit();

    }

    /*
    * Read Integer Prefrences
    * */
    public static int readInteger(Context context, String key, int defValue) {
        return getPreferences(context).getInt(key, defValue);
    }

    /*
    * Write String Prefrences
    * */
    public static void writeString(Context context, String key, String value) {
        getEditor(context).putString(key, value).commit();

    }

    /*
    * Read String Prefrences
    * */
    public static String readString(Context context, String key, String defValue) {
        return getPreferences(context).getString(key, defValue);
    }

    /*
    * Write Float Prefrences
    * */
    public static void writeFloat(Context context, String key, float value) {
        getEditor(context).putFloat(key, value).commit();
    }

    /*
    * Read Float Prefrences
    * */
    public static float readFloat(Context context, String key, float defValue) {
        return getPreferences(context).getFloat(key, defValue);
    }

    /*
    * Write Long Prefrences
    * */
    public static void writeLong(Context context, String key, long value) {
        getEditor(context).putLong(key, value).commit();
    }

    /*
    * Read Long Prefrences
    * */
    public static long readLong(Context context, String key, long defValue) {
        return getPreferences(context).getLong(key, defValue);
    }

    /*
    * Get The SharedPrefrences
    * */
    public static SharedPreferences getPreferences(Context context) {
        return context.getSharedPreferences(PREF_NAME, MODE);
    }

    /*
    * Get SharedPrefrences Editor
    * */
    public static Editor getEditor(Context context) {
        return getPreferences(context).edit();
    }

}
