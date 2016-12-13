package com.example.chrisnguyen.customkeyboard.adapters;

/**
 * Created by chrisnguyen on 12/12/16.
 */

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.example.chrisnguyen.customkeyboard.sqlite.MyDataSource;
import com.example.chrisnguyen.customkeyboard.sqlite.RecentEntry;

import java.util.ArrayList;

public class RecentQuotesAdapter extends QuotesAdapter {

    private ArrayList<RecentEntry> mRecentEntries;
    private MyDataSource mDataSource;

    public RecentQuotesAdapter(Context context) {
        super(context);

        mDataSource = new MyDataSource(context);
        mDataSource.openInReadWriteMode();
        mRecentEntries = (ArrayList<RecentEntry>) mDataSource.getAllEntriesDescendingOnCount();
        setupQuotesFromList(mRecentEntries);
    }

    private void setupQuotesFromList(ArrayList<RecentEntry> recentEntries) {
        mQuotes = new ArrayList<>();
        for (RecentEntry entry : recentEntries) {
            mQuotes.add(entry.getText());
        }
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final View textView = super.getView(position, convertView, parent);

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTrumpKeyboardService.sendText(mQuotes.get(position));

                for (int i = 0; i < mRecentEntries.size(); i++) {
                    if (mRecentEntries.get(i).getText().equals(mQuotes.get(position))) {
                        mDataSource.incrementExistingEntryCountbyOne(mQuotes.get(position));
                        mRecentEntries.get(i).setCount(mRecentEntries.get(i).getCount());
                        return;
                    }
                }

                RecentEntry recentEntry = mDataSource.insertNewEntry(mQuotes.get(position));
                if (recentEntry != null) {
                    mRecentEntries.add(recentEntry);
                }
            }
        });

        textView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                mDataSource.deleteEntryWithId(mRecentEntries.get(position).getId());
                mRecentEntries.remove(position);
                RecentQuotesAdapter.this.notifyDataSetChanged();
                return true;
            }
        });

        return textView;
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        mDataSource.close();
    }
}