package com.fun.saryana.reversealarm;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;


public class SettingActivityTemp extends ActionBarActivity {
    // Min Number of sleep cycles field
    private EditText mMinSleepCycles;
    // Max number of sleep cycles field
    private EditText mMaxSleepCycles;
    // Sleep cycle duration field
    private EditText mSleepCycleDuration;
    // Shared prefrences
    private SharedPreferences mSharedPrefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_activity_temp);

        mMinSleepCycles = (EditText) findViewById(R.id.min_sleep_cycle_field);
        mMaxSleepCycles = (EditText) findViewById(R.id.max_sleep_cycles_field);
        mSleepCycleDuration = (EditText) findViewById(R.id.sleep_cycles_duration_field);

        mSharedPrefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        mMinSleepCycles.setText(String.valueOf(mSharedPrefs.getInt(MainActivity.MIN_CYCLES_TEXT, 3)));
        mMaxSleepCycles.setText(String.valueOf(mSharedPrefs.getInt(MainActivity.MAX_CYCLES_TEXT, 7)));
        mSleepCycleDuration.setText(String.valueOf(mSharedPrefs.getInt(MainActivity.SLEEP_CYCLE_DURATION_TEXT, 90)));

    }

    @Override
    protected void onPause() {
        super.onPause();
        mSharedPrefs.edit()
                .putInt(MainActivity.MIN_CYCLES_TEXT, Integer.valueOf(mMinSleepCycles.getText().toString()))
                .putInt(MainActivity.MAX_CYCLES_TEXT, Integer.valueOf(mMaxSleepCycles.getText().toString()))
                .putInt(MainActivity.SLEEP_CYCLE_DURATION_TEXT, Integer.valueOf(mSleepCycleDuration.getText().toString()))
                .apply();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_setting_activity_temp, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
