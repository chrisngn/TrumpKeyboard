package com.example.chrisnguyen.customkeyboard.services;

import android.content.Context;
import android.inputmethodservice.InputMethodService;
import android.inputmethodservice.Keyboard;
import android.os.IBinder;
import android.os.SystemClock;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import android.view.inputmethod.InputMethodManager;

import com.example.chrisnguyen.customkeyboard.R;
import com.example.chrisnguyen.customkeyboard.views.TrumpKeyboardView;

import java.util.Random;

import static com.example.chrisnguyen.customkeyboard.constants.Quotes.SIGNATURES;

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
        text += SIGNATURES[new Random().nextInt(SIGNATURES.length)];
        mInputConnection.commitText(text, 1);
    }

    public void sendDownKeyEvent(int keyEventCode, int isRepeatable, int flags) {
        KeyEvent keyEvent = new KeyEvent(
                SystemClock.uptimeMillis(),
                SystemClock.uptimeMillis(),
                KeyEvent.ACTION_DOWN,
                keyEventCode,
                isRepeatable,
                flags
        );
        mInputConnection.sendKeyEvent(keyEvent);

        /*while (keyEvent.getAction() == KeyEvent.ACTION_DOWN) {

        }*/

//        if (keyEvent.getKeyCode() == KeyEvent.KEYCODE_DEL &&
//                !onKeyDown(keyEventCode, keyEvent)) {
//            sendUpKeyEvent(keyEventCode, isRepeatable, flags);
//            sendDownKeyEvent(keyEventCode, isRepeatable, flags);
//        }
    }

    public void sendUpKeyEvent(int keyEventCode, int isRepeatable, int flags) {
        KeyEvent keyEvent = new KeyEvent(
                SystemClock.uptimeMillis(),
                SystemClock.uptimeMillis(),
                KeyEvent.ACTION_UP,
                keyEventCode,
                isRepeatable,
                flags
        );
        mInputConnection.sendKeyEvent(keyEvent);
        //keyEvent.getAction();
    }


    public void sendMultipleKeyEvent(int keyEventCode, int isRepeatable, int flags) {
        KeyEvent keyEvent = new KeyEvent(
                SystemClock.uptimeMillis(),
                SystemClock.uptimeMillis(),
                KeyEvent.ACTION_MULTIPLE,
                keyEventCode,
                isRepeatable,
                flags
        );
        mInputConnection.sendKeyEvent(keyEvent);
    }

    public void sendDownAndUpKeyEvent(int keyEventCode, int isRepeatable, int flags) {
        /*if (keyEventCode == KeyEvent.KEYCODE_DEL) {
            //Keyboard keyboard = new Keyboard(this, R.xml.qwerty);
            //Keyboard.Key key = new Keyboard.Key(new Keyboard.Row(keyboard));
            //sendDownUpKeyEvents();
        } else {
        }*/
        sendDownKeyEvent(keyEventCode, isRepeatable, flags);
        sendUpKeyEvent(keyEventCode, isRepeatable, flags);
    }

    public void switchToPreviousInputMethod() {
        mPrevImm.switchToLastInputMethod(mIBinder);
    }
}