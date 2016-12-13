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
    private int mKeyboardHeight;

    public TrumpPagerAdapter(Context context, ViewPager viewPager, int keyboardHeight) {
        super();

        mViewPager = viewPager;
        mKeyboardHeight = keyboardHeight;
        mPages = new ArrayList<>();

        // TODO: make Recent section work
        mPages.add(new PageView(context, new RecentQuotesAdapter(context)).getView());
        mPages.add(new PageView(context, new QuotesAdapter(context, Quotes.arrogant)).getView());
        mPages.add(new PageView(context, new QuotesAdapter(context, Quotes.disapproving)).getView());
        mPages.add(new PageView(context, new QuotesAdapter(context, Quotes.disgusting)).getView());
        mPages.add(new PageView(context, new QuotesAdapter(context, Quotes.inspirational)).getView());
        mPages.add(new PageView(context, new QuotesAdapter(context, Quotes.sarcastic)).getView());
        mPages.add(new PageView(context, new QuotesAdapter(context, Quotes.sexist)).getView());
        mPages.add(new PageView(context, new QuotesAdapter(context, Quotes.stupid)).getView());
        mPages.add(new PageView(context, new QuotesAdapter(context, Quotes.supremacist)).getView());
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
}
