package com.firasshawa.bahja.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.firasshawa.bahja.Adapters.QuotesViewPagerAdapter;
import com.firasshawa.bahja.Controls.Data;
import com.firasshawa.bahja.Controls.TimeOptions;
import com.firasshawa.bahja.Models.Quote;
import com.firasshawa.bahja.R;
import com.google.android.material.appbar.CollapsingToolbarLayout;

import java.util.ArrayList;
import java.util.Queue;

public class Home extends AppCompatActivity {

    ListView motivationList;
    ViewPager quotesViewPager;
    TextView graduationDayCount,MonthValue,DayValue,WeekValue;

    TimeOptions timeOptions;
    Data data;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_layout);

        Constructor();

        DateCountersSetup();
        ViewPagerSetup();

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

    }

    public void DateCountersSetup(){
        graduationDayCount.setText(timeOptions.daysToGraduation() +" "+ getString(R.string.Day_Eng) );

        MonthValue.setText(timeOptions.monthsToGraduation()+"");
        WeekValue.setText(timeOptions.weeksToGraduation()+"");
        DayValue.setText(timeOptions.daysToGraduation()+"");
    }

    public void ViewPagerSetup(){
        QuotesViewPagerAdapter adapter = new QuotesViewPagerAdapter(getApplicationContext(),data.getQuotes());

        quotesViewPager.setPadding(200,0,50,0);

        quotesViewPager.setAdapter(adapter);

        quotesViewPager.setCurrentItem(data.getQuotesSize());
    }
}
