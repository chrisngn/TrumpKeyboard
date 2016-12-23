package com.example.chrisnguyen.customkeyboard.adapters;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;

import com.example.chrisnguyen.customkeyboard.constants.Quotes;
import com.example.chrisnguyen.customkeyboard.views.PageView;

import java.util.ArrayList;

public class TrumpPagerAdapter extends PagerAdapter {
    private ViewPager mViewPager;
    private ArrayList<View> mPages;
    private ArrayList<QuotesAdapter> mQuotesAdapters;
    private int mKeyboardHeight;

    public TrumpPagerAdapter(Context context, ViewPager viewPager, int keyboardHeight) {
        super();

        mViewPager = viewPager;
        mKeyboardHeight = keyboardHeight;
        mPages = new ArrayList<>();
        mQuotesAdapters = new ArrayList<>();

        QuotesAdapter qa1 = new QuotesAdapter(context, Quotes.arrogant);
        QuotesAdapter qa2 = new QuotesAdapter(context, Quotes.disapproving);
        QuotesAdapter qa3 = new QuotesAdapter(context, Quotes.disgusting);
        QuotesAdapter qa4 = new QuotesAdapter(context, Quotes.inspirational);
        QuotesAdapter qa5 = new QuotesAdapter(context, Quotes.sarcastic);
        QuotesAdapter qa6 = new QuotesAdapter(context, Quotes.sexist);
        QuotesAdapter qa7 = new QuotesAdapter(context, Quotes.stupid);
        QuotesAdapter qa8 = new QuotesAdapter(context, Quotes.supremacist);

        mQuotesAdapters.add(qa1);
        mQuotesAdapters.add(qa2);
        mQuotesAdapters.add(qa3);
        mQuotesAdapters.add(qa4);
        mQuotesAdapters.add(qa5);
        mQuotesAdapters.add(qa6);
        mQuotesAdapters.add(qa7);
        mQuotesAdapters.add(qa8);

        // TODO: make Recent section work
        mPages.add(new PageView(context, new RecentQuotesAdapter(context)).getView());
        mPages.add(new PageView(context, qa1).getView());
        mPages.add(new PageView(context, qa2).getView());
        mPages.add(new PageView(context, qa3).getView());
        mPages.add(new PageView(context, qa4).getView());
        mPages.add(new PageView(context, qa5).getView());
        mPages.add(new PageView(context, qa6).getView());
        mPages.add(new PageView(context, qa7).getView());
        mPages.add(new PageView(context, qa8).getView());
    }

    @Override
    public View instantiateItem(ViewGroup container, int position) {
        mViewPager.addView(mPages.get(position), position, mKeyboardHeight);
        return mPages.get(position);
    }

    @Override
    public void destroyItem (ViewGroup container, int position, Object object) {
        mViewPager.removeView(mPages.get(position));
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return Quotes.CATEGORIES[position];
    }

    @Override
    public int getCount() {
        return Quotes.CATEGORIES.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    public ArrayList<QuotesAdapter> getQuotesAdapters() {
        return mQuotesAdapters;
    }
}
