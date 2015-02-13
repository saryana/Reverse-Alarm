package com.fun.saryana.reversealarm.util;

/**
 * Utility class for basic time operations
 * Created by saryana on 2/12/15.
 */
public class TimeUtil {

    /**
     * Formats the time from 24hr to 12 hour
     * @param time Time in minutes
     * @return In the format HH:MM AM/PM
     */
    public static String format24to12(int time) {
        int hours = time / 60;
        boolean isPM = hours >= 12;
        if (isPM) {
            hours -= 12;
        }
        hours = hours == 0 ? 12 : hours;
        int minutes = time % 60;

        return String.format("%d:%02d %s", hours, minutes, isPM ? "PM" : "AM");
    }

    /**
     * Gets the hours time and changes it to 24hr clock
     * @param time Time in format of HH:MM where HH is 0-12
     * @param isPM True if it is PM, false for AM
     * @return The 24 hour representation
     */
    public static int getHoursFrom12to24(String time, boolean isPM) {
        int hours = Integer.valueOf(time.split(":")[0]);

        // 12pm turns to 2400 which is am, don't want that
        if (isPM && hours != 12) {
            hours += 12;
        // 12am needs to be 0000
        } else if (!isPM && hours == 12) {
            hours = 0;
        }
        return hours;
    }

    /**
     * Gets the minutes from the time... Because modularity right?
     * @param time String in format of HH:MM
     * @return The minute value 0-59
     */
    public static int getMinutesFrom12(String time) {
        return Integer.valueOf(time.split(":")[1]);
    }
}
