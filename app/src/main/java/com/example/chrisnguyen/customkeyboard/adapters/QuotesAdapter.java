package com.example.chrisnguyen.customkeyboard.adapters;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.chrisnguyen.customkeyboard.services.TrumpKeyboardService;
import com.example.chrisnguyen.customkeyboard.utils.DisplayUtil;

import java.util.Arrays;
import java.util.List;

/**
 * Created by chrisnguyen on 12/12/16.
 */
public class QuotesAdapter extends BaseAdapter {

    protected TrumpKeyboardService mTrumpKeyboardService;
    protected List<String> mQuotes;

    public QuotesAdapter(Context context) {
        mTrumpKeyboardService = (TrumpKeyboardService) context;
    }

    public QuotesAdapter(Context context, String[] texts) {
        mTrumpKeyboardService = (TrumpKeyboardService) context;
        mQuotes = Arrays.asList(texts);
    }

    @Override
    public int getCount() {
        return mQuotes.size();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final TextView textView;

        if (convertView == null) {
            int scale = DisplayUtil.dpToPx(mTrumpKeyboardService, 10);
            textView = new TextView(mTrumpKeyboardService);
            textView.setPadding(scale, (int) (scale * 1.2), scale, (int) (scale * 1.2));
            textView.setGravity(Gravity.CENTER);
        } else {
            textView = (TextView) convertView;
        }

        textView.setText(mQuotes.get(position));
        textView.setTextColor(mTrumpKeyboardService.getResources().getColor(android.R.color.black));
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTrumpKeyboardService.sendText(mQuotes.get(position));
            }
        });

        return textView;
    }

    @Override
    public Object getItem(int arg0) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }
}

