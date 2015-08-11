package com.thomshutt.runbud.util;

import org.joda.time.DateTime;

import java.util.TimeZone;

public class TimezoneToDateConverter {

    public static int getOffsetMins(double lat, double lon) {
        final TimeZone timeZone = TimeZone.getTimeZone(
                TimezoneMapper.latLngToTimezoneString(lat, lon)
        );

        int offsetMillis = timeZone.getOffset(DateTime.now().getMillis());

        return offsetMillis / 1000 / 60;
    }

    public static void main(String[] args) {
        System.out.println(getOffsetMins(51.5072, 0.1275));
    }

}
