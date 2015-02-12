package com.fun.saryana.reversealarm;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by saryana on 2/12/15.
 */
public class TimeAdapter extends ArrayAdapter<Integer> {

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

    @Override
    public View getView(int position, View contextView, ViewGroup parent) {
        Integer time = getPosition(position);

        // Is this the first time we are creating the view
        if (contextView == null) {
            contextView = LayoutInflater.from(getContext()).inflate(mResource, parent, false);
        }

        // Get and set the time
        TextView timeDisplay = (TextView) contextView.findViewById(R.id.time_value);
        timeDisplay.setText(Integer.toString(time));

        return contextView;
    }


}
