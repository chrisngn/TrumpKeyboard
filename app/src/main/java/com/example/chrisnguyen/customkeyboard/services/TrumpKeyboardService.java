package com.example.chrisnguyen.customkeyboard.services;

import android.content.Context;
import android.inputmethodservice.InputMethodService;
import android.os.IBinder;
import android.os.SystemClock;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import android.view.inputmethod.InputMethodManager;

import com.example.chrisnguyen.customkeyboard.R;
import com.example.chrisnguyen.customkeyboard.views.TrumpKeyboardView;

public class TrumpKeyboardService extends InputMethodService {
    private TrumpKeyboardView mKeyboardView;
    private InputConnection mInputConnection;
    private InputMethodManager mPrevImm;
    private IBinder mIBinder;

    @Override
    public View onCreateInputView() {
        mKeyboardView = (TrumpKeyboardView) getLayoutInflater().inflate(R.layout.keyboard, null);
        mPrevImm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        mIBinder = getWindow().getWindow().getAttributes().token;
        return mKeyboardView.getView();
    }

    @Override
    public void onStartInput(EditorInfo attribute, boolean restarting) {
        super.onStartInputView(attribute, restarting);
        mInputConnection = getCurrentInputConnection();
    }

    public void sendText(String text) {
        mInputConnection.commitText(text, 1);
    }

    public void sendDownKeyEvent(int keyEventCode, int isRepeatable, int flags) {
        mInputConnection.sendKeyEvent(
                new KeyEvent(
                        SystemClock.uptimeMillis(),
                        SystemClock.uptimeMillis(),
                        KeyEvent.ACTION_DOWN,
                        keyEventCode,
                        isRepeatable,
                        flags
                )
        );
    }

    public void sendUpKeyEvent(int keyEventCode, int isRepeatable, int flags) {
        mInputConnection.sendKeyEvent(
                new KeyEvent(
                        SystemClock.uptimeMillis(),
                        SystemClock.uptimeMillis(),
                        KeyEvent.ACTION_UP,
                        keyEventCode,
                        isRepeatable,
                        flags
                )
        );
    }

    public void sendDownAndUpKeyEvent(int keyEventCode, int isRepeatable, int flags){
        sendDownKeyEvent(keyEventCode, isRepeatable, flags);
        sendUpKeyEvent(keyEventCode, isRepeatable, flags);
    }

    public void switchToPreviousInputMethod() {
        mPrevImm.switchToLastInputMethod(mIBinder);
    }
}