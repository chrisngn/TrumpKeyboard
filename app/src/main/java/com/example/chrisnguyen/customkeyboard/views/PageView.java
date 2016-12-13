package com.example.chrisnguyen.customkeyboard.views;

import android.content.Context;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.GridView;

import com.example.chrisnguyen.customkeyboard.utils.DisplayUtil;

public class PageView {
    private Context mContext;
    private BaseAdapter mAdapter;

    public PageView(Context context, BaseAdapter adapter) {
        mContext = context;
        mAdapter = adapter;
    }

    public View getView() {
        final GridView grid = new GridView(mContext);
        grid.setColumnWidth(DisplayUtil.dpToPx(mContext, 50));
        grid.setNumColumns(1);
        grid.setAdapter(mAdapter);
        return grid;
    }
}