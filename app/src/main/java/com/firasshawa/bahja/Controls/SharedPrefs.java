package com.firasshawa.bahja.Controls;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class SharedPrefs {

    public static final String SHAREDPREF = "MySharedPrefs";
    public static final String DAYFROMSTART = "DayFromStart";
    public static final String STARTDATE = "StartDate";
    public static final String DAILYBRODCAST = "Daily";
    public static final String NOTIFICATIONBROADCAST = "NotificationBroadcast";

    Context context ;
    SharedPreferences sharedPreferences ;
    SharedPreferences.Editor editor ;
    // TimeOptions timeOptions;

    public SharedPrefs(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences(SHAREDPREF,Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        // timeOptions = new TimeOptions(context);
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
//            editor.putInt(DAYFROMSTART,1);
//            editor.apply();
            return 0;
        }else{
            return sharedPreferences.getInt(DAYFROMSTART,0);
        }
    }

    public void initStartDateAndDays(){
        editor.putLong(STARTDATE,Calendar.getInstance().getTimeInMillis());
        editor.putInt(DAYFROMSTART,1);
        editor.apply();
     }

    public void isExist(String Key,Long defaultValue){
        if(sharedPreferences.getLong(Key,defaultValue) != 0L){
            System.out.println("Long is Exist! =>"+sharedPreferences.getLong(Key,defaultValue));
        }else{
            System.out.println("Long is Not Exist! =>"+sharedPreferences.getLong(Key,defaultValue));
        }
    }

    public void isExist(String Key,int defaultValue){
        if(sharedPreferences.getInt(Key,defaultValue) != defaultValue){
            System.out.println("int is Exist! =>" + sharedPreferences.getInt(Key,defaultValue) );
        }else{
            System.out.println("int is Not Exist! => " + sharedPreferences.getInt(Key,defaultValue));
        }
    }


//    public void increaseDays(int day){
//        editor.putLong(DAYFROMSTART,day);
//        editor.apply();
//    }

///**
// * This function will check if the sharedprefs has the start date
// * if not we will initialize it with the current date
// * else return the value
// * @return Date object which is the start date
// * */
//    public Date startDate(){
//        if(sharedPreferences.getLong(STARTDATE,0L) == 0){
//            System.out.println("StartDate Called!");
//            editor.putLong(STARTDATE,Calendar.getInstance().getTimeInMillis());
//            editor.apply();
//            daysFromStart();
//            return Calendar.getInstance().getTime();
//        }else{
//            System.out.println("startDate => " + new Date(sharedPreferences.getLong(STARTDATE,0L)).getTime());
//            return new Date(sharedPreferences.getLong(STARTDATE,0L));
//        }
//    }


    //SetStartDate
    public long setStartDate(){
        long currentDate = currentLong();
        editor.putLong(STARTDATE,currentDate);
        editor.apply();
        return currentDate;
    }

    //getStartDate
    public long getStartDate(){
        return sharedPreferences.getLong(STARTDATE,0L);
    }

    //setDays
    public void setDays(long value){
        editor.putLong(DAYFROMSTART,value);
        editor.apply();
    }
    //getDays
    public long getDays(){
        return sharedPreferences.getLong(DAYFROMSTART,0L);
    }

    //modifyDays
    public long modifyDays(){
        Date current = setupDate(currentLong());
        Date start = setupDate(getStartDate());

        System.out.println("current " + current);
        System.out.println("start " + start);

        long days  = calculateDays(current,start);

        setDays(days);

        System.out.println("days " + days);

        return days;
    }

    public long modifyDays(long dateInMillis){
        Date current = setupDate(currentLong());
        Date start = setupDate(dateInMillis);

        long days  = calculateDays(current,start);

        setDays(days);

        return days;
    }


    //Fix Each Date
    public Date setupDate(long timeInMillis){
        Calendar c = Calendar.getInstance();
        c.setTime(new Date(timeInMillis));
        c.set(Calendar.HOUR,1);
        c.set(Calendar.MINUTE,0);
        c.set(Calendar.SECOND,0);
        return c.getTime();
    }

    //String getValueOf(String format) -> MMM = Month , E = day of week
    public String FormatDate(String key,Date date){
        return new SimpleDateFormat(key).format(date.getTime());
    }

    //Calculate Days from the start
    public long calculateDays(Date current,Date start){
        long days = current.getTime() - start.getTime();
        return days / (1000*60*60*24) + 1;
    }


    public Calendar setMyDate(int day,int month,int year){
        Calendar c = Calendar.getInstance();
        c.set(Calendar.MONTH,month);
        c.set(Calendar.YEAR,year);
        c.set(Calendar.DAY_OF_MONTH,day);
        c.set(Calendar.HOUR,1);
        c.set(Calendar.MINUTE,0);
        c.set(Calendar.SECOND,0);
        return c;
    }

    public Calendar AlarmCalender(){
        long startDate = getStartDate();
        Calendar alarmCalender = Calendar.getInstance();
        alarmCalender.setTime(new Date(startDate));
        alarmCalender.set(Calendar.HOUR,8);
        alarmCalender.set(Calendar.MINUTE,10);
        alarmCalender.set(Calendar.SECOND,0);
        return alarmCalender;
    }


    public Date currentDate(){
        return Calendar.getInstance().getTime();
    }

    public long currentLong(){
        return Calendar.getInstance().getTimeInMillis();
    }

    public void setUpBroadcast(String key ,boolean state){
        editor.putBoolean(key,state);
        editor.apply();
    }

    public boolean getBroadcast(String key){
        return sharedPreferences.getBoolean(key,false);
    }


    public void removeBrodcast(String key){
        editor.remove(key);
        editor.apply();
    }



}
