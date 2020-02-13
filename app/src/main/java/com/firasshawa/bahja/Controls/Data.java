package com.firasshawa.bahja.Controls;

import android.content.Context;

import com.firasshawa.bahja.Models.Quote;
import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Arrays;

public class Data {

    Context context;
    private int quotesSize;
    private TimeOptions timeOptions;
    private int CurrentDay;
    ArrayList<Quote> quoteArrayList;
    public Data(Context context) {
        this.context = context;
        this.quotesSize = 0;
        this.timeOptions = new TimeOptions(context);
        this.CurrentDay = 0 ;
    }

    public int getQuotesSize() {
        return quotesSize;
    }

    public  ArrayList<Quote> getQuotes(){

        try {
            InputStream stream = context.getAssets().open("quotes.json");
            int size = stream.available();
            byte[] buffer = new byte[size];
            stream.read(buffer);
            stream.close();

            String JsonString = new String(buffer,"UTF-8");

            quoteArrayList =  new ArrayList<>(Arrays.asList( new Gson().fromJson(JsonString,Quote[].class)));

            // timeOptions.checkDays();
            long days = timeOptions.getDays();
            this.CurrentDay = (int)days;
            //TODO this is how to subset the list (last index is not included!)
            quoteArrayList = new ArrayList<>(quoteArrayList.subList(0,(int)days));

//            System.out.println("from start " +new SharedPrefs(context).daysFromStart());
            this.quotesSize = quoteArrayList.size();
            return quoteArrayList;

        } catch (IOException e) {
            e.printStackTrace();
        }

        return new ArrayList<>();
    }

    public String quoteOftheDay(){
        if(!this.quoteArrayList.isEmpty())
            return quoteArrayList.get(this.CurrentDay).getText();
        else{
            getQuotes();
            return quoteArrayList.get(this.CurrentDay).getText();
        }
    }

}
