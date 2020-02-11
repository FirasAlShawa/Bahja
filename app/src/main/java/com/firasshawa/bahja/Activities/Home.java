package com.firasshawa.bahja.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.FileProvider;
import androidx.viewpager.widget.ViewPager;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
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
import com.firasshawa.bahja.Controls.TimeOptions;
import com.firasshawa.bahja.Models.Quote;
import com.firasshawa.bahja.R;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.File;
import java.io.FileOutputStream;
import java.net.URI;
import java.util.ArrayList;
import java.util.Queue;

public class Home extends AppCompatActivity {

    ListView motivationList;
    ViewPager quotesViewPager;
    TextView graduationDayCount,MonthValue,DayValue,WeekValue;
    FloatingActionButton shareFAB;
    TimeOptions timeOptions;
    Data data;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_layout);

        Constructor();

        DateCountersSetup();
        ViewPagerSetup();

        shareFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"FAB",Toast.LENGTH_SHORT).show();
                Screenshot screenshot = new Screenshot(Home.this);
                screenshot.TakeScreenShot();
            }
        });

    }

    public void Constructor(){
        ToolbarSetup();
        ViewsSetup();
        timeOptions = new TimeOptions(getApplicationContext());
        data = new Data(getApplicationContext());

    }

    public void ToolbarSetup(){
        Toolbar toolbar = findViewById(R.id.mytoolbar);
        setSupportActionBar(toolbar);
    }

    public void ViewsSetup(){
        quotesViewPager = findViewById(R.id.quotesViewPager);

        graduationDayCount = findViewById(R.id.graduationDayCount);

        MonthValue = findViewById(R.id.MonthValue);
        WeekValue = findViewById(R.id.WeekValue);
        DayValue = findViewById(R.id.DayValue);

        shareFAB = findViewById(R.id.shareFAB);

    }

    public void DateCountersSetup(){
        graduationDayCount.setText(timeOptions.daysToGraduation() +" "+ getString(R.string.Day_Eng) );

        MonthValue.setText(timeOptions.monthsToGraduation()+"");
        WeekValue.setText(timeOptions.weeksToGraduation()+"");
        DayValue.setText(timeOptions.daysToGraduation()+"");
    }

    public void ViewPagerSetup(){
        QuotesViewPagerAdapter adapter = new QuotesViewPagerAdapter(getApplicationContext(),data.getQuotes());

        quotesViewPager.setPadding(130,0,130,0);

        quotesViewPager.setAdapter(adapter);

        quotesViewPager.setCurrentItem(data.getQuotesSize());
    }
}
