package com.firasshawa.bahja.Receives;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.firasshawa.bahja.Controls.TimeOptions;

public class Day_reciver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        Toast.makeText(context,"new Day , New Quote",Toast.LENGTH_SHORT).show();
        TimeOptions timeOptions = new TimeOptions(context);
        timeOptions.modifyDays();
    }
}
