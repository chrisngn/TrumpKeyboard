package com.example.chrisnguyen.customkeyboard.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chrisnguyen on 12/12/16.
 */

public class MyDataSource {
    private static final int NUM_RECENT_ENTRIES = 20;

    // Database fields
    private SQLiteDatabase mDatabase;
    private MySqliteHelper mDatabaseHelper;
    private String[] mColumns = {
            MySqliteHelper.COLUMN_ID,
            MySqliteHelper.COLUMN_TEXT,
            MySqliteHelper.COLUMN_COUNT
    };

    public MyDataSource(Context context) {
        mDatabaseHelper = new MySqliteHelper(context);
    }

    public void openInReadWriteMode() throws SQLException {
        mDatabase = mDatabaseHelper.getWritableDatabase();
    }

    public void openInReadMode() throws SQLException {
        mDatabase = mDatabaseHelper.getReadableDatabase();
    }

    public void close() {
        mDatabaseHelper.close();
    }

    private ContentValues getFilledContentValuesObject(String text, long count) {
        ContentValues values = new ContentValues();
        values.put(MySqliteHelper.COLUMN_TEXT, text);
        values.put(MySqliteHelper.COLUMN_COUNT, count);
        return values;
    }

    private ContentValues getFilledContentValuesObject(RecentEntry recentEntry) {
        return getFilledContentValuesObject(recentEntry.getText(), recentEntry.getCount());
    }

    public RecentEntry insertNewEntry(String text) {
        int initialCount = 0;
        ContentValues values = getFilledContentValuesObject(text, initialCount);
        long insertId = mDatabase.insert(MySqliteHelper.TABLE_RECENTS, null, values);

        if (insertId == -1) {
            return null;
        } else {
            return new RecentEntry(insertId, text, initialCount);
        }
    }

    public void incrementExistingEntryCountbyOne(String text) {
        Cursor cursor = mDatabase.query(
                MySqliteHelper.TABLE_RECENTS,
                mColumns,
                MySqliteHelper.COLUMN_TEXT + " = " + text,
                null,
                null,
                null,
                null
        );
        cursor.moveToFirst();
        RecentEntry newRecentEntry = cursorToRecent(cursor);
        cursor.close();
        newRecentEntry.incrementUsageCountByOne();
        ContentValues values = getFilledContentValuesObject(newRecentEntry);
        mDatabase.update(
                MySqliteHelper.TABLE_RECENTS,
                values,
                MySqliteHelper.COLUMN_ID + "=" + newRecentEntry.getId(),
                null
        );
    }

    public boolean deleteEntryWithId(long id) {
        int rowsDeleted = mDatabase.delete(
                MySqliteHelper.TABLE_RECENTS,
                MySqliteHelper.COLUMN_ID + " = " + id,
                null);

        if (rowsDeleted == 0) {
            return false;
        } else {
            return true;
        }
    }

    public List<RecentEntry> getAllEntriesDescendingOnCount() {
        List<RecentEntry> recentEntries = new ArrayList<>();

        Cursor cursor = mDatabase.query(
                MySqliteHelper.TABLE_RECENTS,
                mColumns,
                null,
                null,
                null,
                null,
                MySqliteHelper.COLUMN_COUNT + " * 1 DESC"
        );
        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            if (cursor.getPosition() >= NUM_RECENT_ENTRIES) {
                deleteEntryWithId(cursor.getLong(0));
            } else {
                RecentEntry recentEntry = cursorToRecent(cursor);
                recentEntries.add(recentEntry);
            }

            cursor.moveToNext();
        }

        cursor.close();
        return recentEntries;
    }

    private RecentEntry cursorToRecent(Cursor cursor) {
        return new RecentEntry(cursor.getLong(0), cursor.getString(1), cursor.getLong(2));
    }
}