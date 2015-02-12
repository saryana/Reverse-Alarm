package com.fun.saryana.reversealarm;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TimePicker;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends ActionBarActivity implements TimePicker.OnTimeChangedListener {

    private static final String TAG = MainActivity.class.getSimpleName();

    // Holds the times
    private List<Integer> mTimes;
    // The adapter used by the view
    private TimeAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TimePicker timePicker = (TimePicker) findViewById(R.id.time_picker);
        timePicker.setOnTimeChangedListener(this);

        mTimes = new ArrayList<>();
        updateTimes(timePicker.getCurrentHour() * 60 + timePicker.getCurrentMinute());
        mAdapter = new TimeAdapter(this, R.layout.time_value, mTimes);

        ListView suggestedTimes = (ListView) findViewById(R.id.suggested_times);
        suggestedTimes.setAdapter(mAdapter);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

    /**
     * Listener for when the user has changed the time
     * @param view TimePicker on page
     * @param hourOfDay Hour on the current clock
     * @param minute Minute on the current clock
     */
    @Override
    public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
        Log.i(TAG, "User selected time of " + hourOfDay + ":" + minute);
        updateTimes(hourOfDay*60+minute);
        mAdapter.notifyDataSetChanged();
    }

    /**
     * Updates the times that the user should go to bed
     * @param time Time user wants to wake up
     */
    private void updateTimes(int time) {
        int minNumberOfSleepCycles = 4;
        int maxNumberOfSleepCycles = 7;
        int sleepCycleDuration = 90;
        mTimes.clear();
        for (int i = minNumberOfSleepCycles; i <= maxNumberOfSleepCycles; i++) {
            Log.i(TAG, "" + time);
            time -= sleepCycleDuration;
            mTimes.add(time);
        }
    }
}
