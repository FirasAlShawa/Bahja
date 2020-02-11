package com.firasshawa.bahja.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.firasshawa.bahja.Models.Quote;
import com.firasshawa.bahja.R;

import java.util.ArrayList;

public class QuotesViewPagerAdapter extends PagerAdapter {

    private Context context;
    private ArrayList<Quote> quoteArrayList;

    public QuotesViewPagerAdapter(Context context, ArrayList<Quote> quoteArrayList) {
        this.context = context;
        this.quoteArrayList = quoteArrayList;
    }

    @Override
    public int getCount() {
        return quoteArrayList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view.equals(object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view = LayoutInflater.from(context).inflate(R.layout.qutoe_item_layout,container,false);

        TextView QuoteText = view.findViewById(R.id.QuoteText);
        QuoteText.setText(quoteArrayList.get(position).getText());

        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View)object);
    }
}
