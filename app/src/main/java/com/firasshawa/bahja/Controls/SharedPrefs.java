package com.firasshawa.bahja.Controls;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Calendar;
import java.util.Date;

public class SharedPrefs {

    public static final String SHAREDPREF = "MySharedPrefs";
    public static final String DAYFROMSTART = "DayFromStart";
    public static final String STARTDAY = "StartDay";

    Context context ;
    SharedPreferences sharedPreferences ;
    SharedPreferences.Editor editor ;

    public SharedPrefs(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences(SHAREDPREF,Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

/**
 * This function will check if sharedprefs has the number of days
 * from the first run.
 * if the sharedprefs does not have it , it will initialize it with one
 * and save the start date.
 * else
 * it will return the value of it
 *
 * @return int number of day from the first run
 * */
    public int daysFromStart(){
        System.out.println();
        if(sharedPreferences.getInt(DAYFROMSTART,0) == 0){
            editor.putInt(DAYFROMSTART,1);
            editor.apply();
            return 1;
        }else{
            return sharedPreferences.getInt(DAYFROMSTART,0);
        }
    }
/**
 * This function will check if the sharedprefs has the start date
 * if not we will initialize it with the current date
 * else return the value
 * @return Date object which is the start date
 * */
    public Date startDate(){
        if(sharedPreferences.getLong(STARTDAY,0L) == 0){
            editor.putLong(STARTDAY,Calendar.getInstance().getTimeInMillis());
            editor.apply();

            daysFromStart();

            return Calendar.getInstance().getTime();
        }else{
            return new Date(sharedPreferences.getLong(STARTDAY,0L));
        }
    }
}
