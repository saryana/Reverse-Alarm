package com.fun.saryana.reversealarm;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.fun.saryana.reversealarm.util.TimeUtil;

import java.util.List;

/**
 * TimeAdapter that is used to add values to list view.
 *
 * Created by saryana on 2/12/15.
 */
public class TimeAdapter extends ArrayAdapter<Integer> {

    private static final String TAG = TimeAdapter.class.getSimpleName();

    // Resource to use
    private int mResource;

    /**
     * Creates a new TimeAdapter of the times that the user should wake up
     * @param context Context being created from
     * @param resource Resource file to use
     * @param times Times to display
     */
    public TimeAdapter(Context context, int resource, List<Integer> times) {
        super(context, resource, times);
        mResource = resource;
    }

    /**
     * Handles each individual view that will be in the list view
     * @param position Position of the view
     * @param contextView View we are handling
     * @param parent Parent view
     * @return Updated view
     */
    @Override
    public View getView(int position, View contextView, ViewGroup parent) {
        Integer time = getItem(position);

        // Is this the first time we are creating the view
        if (contextView == null) {
            contextView = LayoutInflater.from(getContext()).inflate(mResource, parent, false);
        }
        // Get and set the time
        TextView timeDisplay = (TextView) contextView.findViewById(R.id.time_value);
        TextView numSleepCycles = (TextView) contextView.findViewById(R.id.number_of_sleep_cycles);
        numSleepCycles.setText(String.valueOf(position + MainActivity.MIN_CYCLES));
        timeDisplay.setText(TimeUtil.format24to12(time));

        return contextView;
    }
}
