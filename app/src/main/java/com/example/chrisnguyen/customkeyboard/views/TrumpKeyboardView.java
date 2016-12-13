package com.example.chrisnguyen.customkeyboard.views;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.astuetz.PagerSlidingTabStrip;
import com.example.chrisnguyen.customkeyboard.R;
import com.example.chrisnguyen.customkeyboard.adapters.TrumpPagerAdapter;
import com.example.chrisnguyen.customkeyboard.services.TrumpKeyboardService;

/**
 * Created by chrisnguyen on 12/12/16.
 */

public class TrumpKeyboardView extends View {
    private static final String LOG = TrumpKeyboardView.class.getSimpleName();

    private TrumpKeyboardService mTrumpKeyboardService;

    private LinearLayout mLayout;
    private PagerSlidingTabStrip mTabs;
    private ViewPager mViewPager;

    private TrumpPagerAdapter mTrumpPagerAdapter;

    private int mWidth;
    private int mHeight;

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        mWidth = View.MeasureSpec.getSize(widthMeasureSpec);
        mHeight = View.MeasureSpec.getSize(heightMeasureSpec);
        setMeasuredDimension(mWidth, mHeight);

        Log.d(LOG, "(width, height): (" + mWidth + ", " + mHeight + ")");
    }

    public TrumpKeyboardView(Context context) {
        super(context);
        initialize(context);
    }

    public TrumpKeyboardView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize(context);
    }

    public TrumpKeyboardView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialize(context);
    }

    private void initialize(Context context) {
        mTrumpKeyboardService = (TrumpKeyboardService) context;

        LayoutInflater inflater = (LayoutInflater)
                getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        mLayout = (LinearLayout) inflater.inflate(R.layout.keyboard_main, null);

        mViewPager = (ViewPager) mLayout.findViewById(R.id.view_pager);
        mTrumpPagerAdapter = new TrumpPagerAdapter(context, mViewPager, mHeight);
        mViewPager.setAdapter(mTrumpPagerAdapter);
        //mViewPager.setCurrentItem(1);

        mTabs = (PagerSlidingTabStrip) mLayout.findViewById(R.id.tabs);
        mTabs.setTextSize(getResources().getDimensionPixelSize(R.dimen.button_text_size));
        mTabs.setAllCaps(false);
        mTabs.setIndicatorHeight(mTabs.getIndicatorHeight() / 2);
        mTabs.setIndicatorColor(getResources().getColor(R.color.pager_color));
        mTabs.setViewPager(mViewPager);

        setupButtons();
    }

    private void setupButtons() {
        Button delete = (Button) mLayout.findViewById(R.id.delete_button);
        Button globe = (Button) mLayout.findViewById(R.id.globe_button);
        Button random = (Button) mLayout.findViewById(R.id.random_button);
        Button send = (Button) mLayout.findViewById(R.id.send_button);

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: make it repeatable
                mTrumpKeyboardService.sendDownKeyEvent(KeyEvent.KEYCODE_DEL, 1, KeyEvent.FLAG_LONG_PRESS);
            }
        });

        globe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTrumpKeyboardService.switchToPreviousInputMethod();
            }
        });


        random.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTrumpKeyboardService.sendDownAndUpKeyEvent(KeyEvent.KEYCODE_ENTER, 0, 0);
            }
        });
    }

    public View getView() {
        return mLayout;
    }
}
