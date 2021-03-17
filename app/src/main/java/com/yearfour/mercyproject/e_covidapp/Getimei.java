package com.yearfour.mercyproject.e_covidapp;

import android.content.Context;
import android.provider.Settings;
import android.util.Log;

/**
 * Created by  Mercy Chebet
 * on 18/10/2020 12:31 2020
 */
public class Getimei {
    public static String getIMEI(Context context) {

        String unique_id = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
        Log.e("unique_id", "-->" + unique_id);
        return unique_id;
    }
}
