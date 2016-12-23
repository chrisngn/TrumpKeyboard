package com.example.chrisnguyen.customkeyboard.views;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.astuetz.PagerSlidingTabStrip;
import com.example.chrisnguyen.customkeyboard.R;
import com.example.chrisnguyen.customkeyboard.adapters.QuotesAdapter;
import com.example.chrisnguyen.customkeyboard.adapters.TrumpPagerAdapter;
import com.example.chrisnguyen.customkeyboard.constants.Quotes;
import com.example.chrisnguyen.customkeyboard.services.TrumpKeyboardService;

import java.util.List;
import java.util.Random;

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

        // Skip the "Recent" section. Set the default to the second page
        mViewPager.setCurrentItem(1);
    }

    private void setupButtons() {
        Button delete = (Button) mLayout.findViewById(R.id.delete_button);
        Button globe = (Button) mLayout.findViewById(R.id.globe_button);
        Button random = (Button) mLayout.findViewById(R.id.random_button);
        final Button send = (Button) mLayout.findViewById(R.id.send_button);

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: make it repeatable
                mTrumpKeyboardService.sendMultipleKeyEvent(KeyEvent.KEYCODE_DEL, 1, 0);
            }
        });

//        delete.setOnTouchListener(new OnTouchListener() {
//            int i = 1;
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                while (event.getAction() == MotionEvent.ACTION_DOWN) {
//                    Log.d("onTouch", String.valueOf(i++));
//                    mTrumpKeyboardService.sendDownAndUpKeyEvent(KeyEvent.KEYCODE_DEL, 1, KeyEvent.FLAG_LONG_PRESS);
//                }
//                return true;
//            }
//        });

        globe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTrumpKeyboardService.switchToPreviousInputMethod();
            }
        });


        random.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                List<QuotesAdapter> quotesAdapters = mTrumpPagerAdapter.getQuotesAdapters();
                Random rand = new Random();

                // Exclude "Recent" category
                QuotesAdapter qa = quotesAdapters.get(rand.nextInt(Quotes.CATEGORIES.length - 1));
                mTrumpKeyboardService.sendText(qa.getItem(rand.nextInt(qa.getCount())));

                send.callOnClick();
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
