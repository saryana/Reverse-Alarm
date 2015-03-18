package com.fun.saryana.reversealarm;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.provider.AlarmClock;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.ToggleButton;

import com.fun.saryana.reversealarm.util.TimeUtil;

import java.util.ArrayList;
import java.util.List;


public class MainActivity
                    extends ActionBarActivity implements
                            TimePicker.OnTimeChangedListener,
                            AdapterView.OnItemClickListener,
                            CompoundButton.OnCheckedChangeListener {

    private static final String TAG = MainActivity.class.getSimpleName();

    /* These need to get moved to a preference later */
    // Minimum number of sleep cycles to see
    public static int MIN_CYCLES;
    // Max number of sleep cycles to see
    public static int MAX_CYCLES;
    // Length of user's sleep cycle (in minutes);
    public static int SLEEP_CYCLE_DURATION;


    // Holds the times
    private List<Integer> mTimes;
    // The adapter used by the view
    private TimeAdapter mAdapter;
    // Time picker being shown
    private TimePicker mTimePicker;

    /**
     * Set up the initial list and attach the on change listeners
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Handle the TimePicker
        mTimePicker = (TimePicker) findViewById(R.id.time_picker);
        mTimePicker.setOnTimeChangedListener(this);

        MIN_CYCLES = 3;
        MAX_CYCLES = 6;
        SLEEP_CYCLE_DURATION = 90;

        mTimes = new ArrayList<>();

        updateSleepAtText();
        updateTimes();

        mAdapter = new TimeAdapter(this, R.layout.time_value, mTimes);

        // Handle the ListView
        ListView suggestedTimes = (ListView) findViewById(R.id.suggested_times);
        suggestedTimes.setAdapter(mAdapter);
        suggestedTimes.setOnItemClickListener(this);

        // Handle the toggle button
        ToggleButton toggleButton = (ToggleButton) findViewById(R.id.wake_up_switch);
        toggleButton.setOnCheckedChangeListener(this);
    }

    /**
     * Updates the text being shown to the user to wake up at or go to bed at
     */
    private void updateSleepAtText() {
        Resources res = getResources();
        TextView welcomeText = (TextView) findViewById(R.id.wake_sleep_text);

        // Get the time from the time picker
        TimePicker timePicker = (TimePicker) findViewById(R.id.time_picker);
        int time = timePicker.getCurrentHour() * 60 + timePicker.getCurrentMinute();

        boolean wakeUpTimes = !((ToggleButton) findViewById(R.id.wake_up_switch)).isChecked();

        int resource = wakeUpTimes ? R.string.wake_up_at_text : R.string.go_to_bed_at_text ;

        welcomeText.setText(String.format(res.getString(resource), TimeUtil.format24to12(time)));
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
        updateSleepAtText();
        updateTimes();
        mAdapter.notifyDataSetChanged();
    }

    /**
     * Updates the times that the user should go to bed
     *
     * @param time Time user wants to wake up
     */
    private void updateTimes() {
        int time = getTime();
        boolean wakeUpTimes = !((ToggleButton) findViewById(R.id.wake_up_switch)).isChecked();
        // Since we have a changing list length let's make sure there aren't any unecssary valeus
        mTimes.clear();
        for (int i = MIN_CYCLES; i <= MAX_CYCLES; i++) {
            int sleepTime;
            if (!wakeUpTimes) {
                sleepTime = time - (i * SLEEP_CYCLE_DURATION);
                if (sleepTime < 0) {
                    sleepTime = (24 * 60) + sleepTime;
                }
            } else {
                sleepTime = time + (i * SLEEP_CYCLE_DURATION);
                int hours = sleepTime / 60;
                int minutes = sleepTime - (hours * 60);
                if (minutes >= 60) {
                    hours += minutes / 60;
                    minutes %= 60;
                }
                if (hours >= 24) {
                    hours %= 24;
                }
                sleepTime = hours * 60 + minutes;
            }
            mTimes.add(sleepTime);
        }
    }

    /**
     * @return Current time on clock in minutes
     */
    public int getTime() {
        return mTimePicker.getCurrentHour() * 60 + mTimePicker.getCurrentMinute();
    }

    /**
     * Set up on click listener for when the
     * user clicks on a time it get added to their alarms
     */
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        ToggleButton button = (ToggleButton) findViewById(R.id.wake_up_switch);
        // Don't allow user to set alarm if it is showing wake up times
        if (button.isChecked()) {
            Log.i(TAG, "Can't set alarm for sleep times");
            return;
        }

        TextView timeValueView = (TextView) view.findViewById(R.id.time_value);
        String timeString = timeValueView.getText().toString();

        // Grab the hours and minutes from the string
        String[] timeSplit = timeString.split(" ");
        boolean isPM = timeSplit[1].equals("PM");

        int hours = TimeUtil.getHoursFrom12to24(timeSplit[0], isPM);
        int minutes = TimeUtil.getMinutesFrom12(timeSplit[0]);

        Log.i(TAG, "Sending intent with value " + hours + " " + minutes);
        Intent intent = new Intent(AlarmClock.ACTION_SET_ALARM);
        intent.putExtra(AlarmClock.EXTRA_HOUR, hours);
        intent.putExtra(AlarmClock.EXTRA_MINUTES, minutes);
        startActivity(intent);
    }

    /**
     * On a toggle switch we get the appropriate times
     */
    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        updateSleepAtText();
        updateTimes();
        mAdapter.notifyDataSetChanged();
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
            startActivity(new Intent(this, SettingsActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
