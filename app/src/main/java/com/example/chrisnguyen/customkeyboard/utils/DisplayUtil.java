package com.example.chrisnguyen.customkeyboard.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.util.DisplayMetrics;

/**
 * Created by chrisnguyen on 12/11/16.
 */

public class DisplayUtil {
    public static int dpToPx(Context context, int dp) {
        // Converts dp to pixels for setting widths and margins programmatically
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        int px = Math.round(dp * (displayMetrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT));
        return px;
    }

    public static int getScreenWidth(Activity activity) {
        // gets the full width of the screen
        Point point = new Point();
        activity.getWindowManager().getDefaultDisplay().getSize(point);
        return point.x;
    }

    public static int getScreenHeight(Activity activity) {
        // gets the full height of the screen
        Point point = new Point();
        activity.getWindowManager().getDefaultDisplay().getSize(point);
        return point.y;
    }
}
