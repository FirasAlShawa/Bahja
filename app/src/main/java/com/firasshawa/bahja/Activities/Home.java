package com.firasshawa.bahja.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.viewpager.widget.ViewPager;

import android.Manifest;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.firasshawa.bahja.Adapters.QuotesViewPagerAdapter;
import com.firasshawa.bahja.Controls.Data;
import com.firasshawa.bahja.Controls.Screenshot;
import com.firasshawa.bahja.Controls.SharedPrefs;
import com.firasshawa.bahja.Controls.TimeOptions;
import com.firasshawa.bahja.Models.Quote;
import com.firasshawa.bahja.R;
import com.firasshawa.bahja.Receives.Day_reciver;
import com.firasshawa.bahja.Receives.Notification_reciver;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.File;
import java.io.FileOutputStream;
import java.net.URI;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Queue;

public class Home extends AppCompatActivity {

    private  int Req_code = 5597;

    ListView motivationList;
    ViewPager quotesViewPager;
    TextView graduationDayCount,MonthValue,DayValue,WeekValue;
    FloatingActionButton shareFAB;
    TimeOptions timeOptions;
    Data data;
    Boolean PermissionStats;


    @Override
    protected void onStart() {
        super.onStart();
        timeOptions.modifyDays();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_layout);

        Constructor();

        DateCountersSetup();
        ViewPagerSetup();

        quotesDailyAlarm();
        NotificationDailyAlarm();

        shareFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q){

                    if(Environment.isExternalStorageLegacy()){

                        if (ActivityCompat.checkSelfPermission(Home.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(Home.this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {

                            requestStoragePermission();

                            TakeScreenShot();

                        }else{
                            requestStoragePermission();
                        }
                    }
                }else{
                    if (ActivityCompat.checkSelfPermission(Home.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(Home.this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                        requestStoragePermission();

                        Toast.makeText(getApplicationContext(),"Taking Screenshot...",Toast.LENGTH_SHORT).show();
                        Screenshot screenshot = new Screenshot(Home.this);
                        screenshot.TakeScreenShot();
                        Toast.makeText(getApplicationContext(),"Share it now...",Toast.LENGTH_SHORT).show();
                    }else{
                        requestStoragePermission();
                    }
                }
            }
        });

    }

    public void Constructor(){
        ToolbarSetup();
        ViewsSetup();
        timeOptions = new TimeOptions(getApplicationContext());
        data = new Data(getApplicationContext());
        PermissionStats = false;
    }

    public void ToolbarSetup(){
        Toolbar toolbar = findViewById(R.id.mytoolbar);
        setSupportActionBar(toolbar);
    }

    public void ViewsSetup(){
        quotesViewPager = findViewById(R.id.quotesViewPager);

        graduationDayCount = findViewById(R.id.graduationDayCount);

//        MonthValue = findViewById(R.id.MonthValue);
//        WeekValue = findViewById(R.id.WeekValue);
//        DayValue = findViewById(R.id.DayValue);

        shareFAB = findViewById(R.id.shareFAB);

    }

    public void DateCountersSetup(){
        graduationDayCount.setText(timeOptions.daysToGraduation() +" "+ getString(R.string.Day_Eng) );

//        MonthValue.setText(timeOptions.monthsToGraduation()+"");
//        WeekValue.setText(timeOptions.weeksToGraduation()+"");
//        DayValue.setText(timeOptions.daysToGraduation()+"");
    }

    public void ViewPagerSetup(){
        QuotesViewPagerAdapter adapter = new QuotesViewPagerAdapter(getApplicationContext(),data.getQuotes());

        quotesViewPager.setPadding(130,0,130,0);

        quotesViewPager.setAdapter(adapter);

        quotesViewPager.setCurrentItem(data.getQuotesSize());
    }

    public void TakeScreenShot(){
        Toast.makeText(getApplicationContext(),"Taking Screenshot...",Toast.LENGTH_SHORT).show();
        Screenshot screenshot = new Screenshot(Home.this);
        screenshot.TakeScreenShot();
        Toast.makeText(getApplicationContext(),"Share it now...",Toast.LENGTH_SHORT).show();
    }

    public void quotesDailyAlarm() {
        Intent intent = new Intent(getApplicationContext(), Notification_reciver.class);

        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);


        boolean flag = timeOptions.getBroadcast(SharedPrefs.DAILYBRODCAST);

        if(!flag) {


        this.timeOptions.setBroadcast(SharedPrefs.DAILYBRODCAST, true);

        System.out.println("Home.java : new Alarm setup");

        Calendar AlarmCalender = timeOptions.AlarmCalender();

        System.out.println(AlarmCalender.getTime());

        intent.putExtra("quoteOfTheDay", data.quoteOftheDay());

        PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 666, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, AlarmCalender.getTimeInMillis(), AlarmManager.INTERVAL_HOUR, pendingIntent);

        System.out.println("Home.java : Alarm is now on");

            Toast.makeText(getApplicationContext(),"Daily On",Toast.LENGTH_SHORT).show();

        }

    }
    public void NotificationDailyAlarm(){
        Intent intent = new Intent(getApplicationContext(), Day_reciver.class);

        AlarmManager alarmManager =(AlarmManager) getSystemService(ALARM_SERVICE);

//        new SharedPrefs(getApplicationContext()).removeBrodcast(SharedPrefs.NOTIFICATIONBROADCAST);

        boolean flag = timeOptions.getBroadcast(SharedPrefs.NOTIFICATIONBROADCAST);

        if(!flag) {

            this.timeOptions.setBroadcast(SharedPrefs.NOTIFICATIONBROADCAST, true);

            System.out.println("Home.java : new Daily Alarm setup");

            Calendar AlarmCalender = timeOptions.AlarmCalender();

            PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 667, intent, PendingIntent.FLAG_UPDATE_CURRENT);

            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, AlarmCalender.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);

            System.out.println("Home.java : Alarm Daily is now on");

            Toast.makeText(getApplicationContext(),"Notify On",Toast.LENGTH_SHORT).show();
        }


    }

    public void requestStoragePermission(){
        if(ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.READ_EXTERNAL_STORAGE) && ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.WRITE_EXTERNAL_STORAGE)){
            new AlertDialog.Builder(this)
                    .setTitle("Permission Needed !")
                    .setMessage("these permissions are needed to take a screenshot and share it !")
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            ActivityCompat.requestPermissions(Home.this,new String[]{
                                    Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE
                            },Req_code);
                        }
                    })
                    .setNegativeButton("canel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    })
                    .create().show();
        }else{
            ActivityCompat.requestPermissions(Home.this,new String[]{
                    Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE
            },Req_code);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == Req_code){
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED){
                Toast.makeText(this, "Permission GRANTED", Toast.LENGTH_SHORT).show();
                this.PermissionStats = true;
            }else{
                Toast.makeText(this, "Permission DENIED", Toast.LENGTH_SHORT).show();
                this.PermissionStats = false;

            }
        }

    }

    public void removeBroadcast(){
        new SharedPrefs(getApplicationContext()).removeBrodcast(SharedPrefs.DAILYBRODCAST);
        new SharedPrefs(getApplicationContext()).removeBrodcast(SharedPrefs.NOTIFICATIONBROADCAST);

    }
}
