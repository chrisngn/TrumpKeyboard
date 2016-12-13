package com.example.chrisnguyen.customkeyboard.sqlite;

/**
 * Created by chrisnguyen on 12/12/16.
 */

public class RecentEntry {
    private long mId;
    private String mText;
    private long mCount;

    public void incrementUsageCountByOne() {
        mCount = mCount + 1;
    }

    public RecentEntry(long id, String text, long count) {
        mId = id;
        mText = text;
        mCount = count;
    }

    public RecentEntry(String text, long count) {
        mText = text;
        mCount = count;
    }

    public long getId() {
        return mId;
    }

    public long getCount() {
        return mCount;
    }

    public String getText() {
        return mText;
    }

    public void setId(long id) {
        mId = id;
    }

    public void setCount(long count) {
        mCount = count;
    }

    public void setText(String text) {
        mText = text;
    }
}