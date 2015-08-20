package com.thomshutt.runbud.util;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

public class TimezoneToDateConverter {

    public static int getOffsetMins(double lat, double lon) {
        final TimeZone timeZone = TimeZone.getTimeZone(
                TimezoneMapper.latLngToTimezoneString(lat, lon)
        );

        int offsetMillis = timeZone.getOffset(DateTime.now().getMillis());

        return offsetMillis / 1000 / 60;
    }

    public static long getUtcForCurrentDay(double lat, double lon, int hours, int mins) {
        final int offsetMins = getOffsetMins(lat, lon);
        final int offsetHours = (int) TimeUnit.MINUTES.toHours(offsetMins);

        return DateTime.now(DateTimeZone.forOffsetHours(offsetHours))
                .withTimeAtStartOfDay()
                .plusHours(hours)
                .plusMinutes(mins)
                .getMillis();
    }

    public static long getStartOfCurrentDayUtc(double lat, double lon) {
        final int offsetMins = getOffsetMins(lat, lon);
        final int offsetHours = (int) TimeUnit.MINUTES.toHours(offsetMins);

        return DateTime.now(DateTimeZone.forOffsetHours(offsetHours))
                .withTimeAtStartOfDay()
                .getMillis();
    }

    public static long getCurrentTimeUtc() {
        return DateTime.now(DateTimeZone.forOffsetHours(0)).getMillis();
    }

}
