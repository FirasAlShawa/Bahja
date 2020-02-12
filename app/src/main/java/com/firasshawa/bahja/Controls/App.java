package com.firasshawa.bahja.Controls;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;

public class App extends Application {
    public static final String CHANNEL_1_ID = "QuoteChannel";
    private SharedPrefs prefs;

    @Override
    public void onCreate() {
        super.onCreate();

        prefs = new SharedPrefs(this);

        if(prefs.getStartDate() == 0L){
            long startDateinMillis = prefs.setStartDate();
            prefs.modifyDays(startDateinMillis);
            System.out.println("App.java => the start date just created and days are initialized.");
        }else{
            System.out.println("start date => " + prefs.getStartDate());
            System.out.println("days => " + prefs.getDays());
            System.out.println("App.java => the start date exist.");
        }

        createNotificationChannel();

    }

    public void createNotificationChannel(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel channel = new NotificationChannel(
              CHANNEL_1_ID,
              "Quote Channel",
                    NotificationManager.IMPORTANCE_HIGH
            );

            channel.setDescription("This Notification channel is responsible for notify you with daily quote. ");

            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }
    }
}
