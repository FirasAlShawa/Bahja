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
        prefs.isExist(SharedPrefs.STARTDATE,0L);
        prefs.isExist(SharedPrefs.DAYFROMSTART,0);
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
