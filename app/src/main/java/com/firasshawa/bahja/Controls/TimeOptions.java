package com.firasshawa.bahja.Controls;

import android.content.Context;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class TimeOptions {

    public Calendar calendar;
    public SimpleDateFormat simpleDateFormat;
    public final String graduationDate;
    public Context context;
   SharedPrefs prefs;


    public TimeOptions(Context context) {
        this.calendar = Calendar.getInstance();
        this.simpleDateFormat = new SimpleDateFormat("dd MM yyyy");
        this.graduationDate = "01 07 2020";
        this.context = context;
        this.prefs = new SharedPrefs(context);
    }

    public Date getDate(){
        return this.calendar.getTime();
    }

    private long millisecondsToGraduation(){
        long Milliseconds ;

        try {
            Date today = this.calendar.getTime();

            Date graduation = simpleDateFormat.parse(this.graduationDate);

            Milliseconds = graduation.getTime() - today.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
            Milliseconds = 0;
        }

        return Milliseconds;
    }

    public int daysToGraduation(){
        int days = (int) (millisecondsToGraduation() / (1000*60*60*24));
        return days;
    }

    public int weeksToGraduation(){
        int weeks = (int) (millisecondsToGraduation() / (1000*60*60*24*7));
        return weeks;
    }

    public int monthsToGraduation(){
        int weeks = weeksToGraduation();
        int months = weeks / 4;
        return months;
    }


/**
 * This function will return the value of
 * subtraction of the start date of the app
 * till today
 * @return int number of days from the first run
 * */
    public int daysFromStart(){
//        long daysInMillisecond = 0L;
//
//        Date startDate = prefs.startDate();
//        Date current = this.calendar.getTime();
//
//        System.out.println(startDate.getTime());
//        System.out.println( current.getTime());
//
//        daysInMillisecond = current.getTime() - startDate.getTime();
//        int days = (int) daysInMillisecond / (1000*60*60*24) ;
//
//        if(days == 0){
//            return prefs.daysFromStart();
//        }
//
//        System.out.println("days " + days);
//
//        return days;
        return 0 ;
    }

    public void checkDays(){
//        int currentDays = daysFromStart();
//        int sharedPrefDays = prefs.daysFromStart();
//        if(currentDays > sharedPrefDays){
//            System.out.println("current : " + currentDays + "\nsharedPrefsDays : "+sharedPrefDays);
//        }else{
//            System.out.println("current : " + currentDays + "\nsharedPrefsDays : "+sharedPrefDays);
//        }
    }

    public long getDays(){
        return this.prefs.getDays();
    }

    public long modifyDays(){
        return this.prefs.modifyDays();
    }



    public Date currentDate(){
        return this.calendar.getTime();
    }

    public long currentLong(){
        return this.calendar.getTimeInMillis();
    }

}
