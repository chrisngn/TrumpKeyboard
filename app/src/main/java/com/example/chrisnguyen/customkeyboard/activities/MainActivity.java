package com.example.chrisnguyen.customkeyboard.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.chrisnguyen.customkeyboard.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //addPreferencesFromResource(R.xml.settings); // PreferencesActivity
        setContentView(R.layout.activity_main);
    }
}
