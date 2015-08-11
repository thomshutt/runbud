package com.thomshutt.runbud.util;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;

public class TimezoneToDateConverterTest {

    public static final double LAT_LONDON = 51.5072;
    public static final double LON_LONDON = 0.1275;
    public static final double LAT_SYDNEY = -33.8650;
    public static final double LON_SYDNEY = 151.2094;

    @Test
    public void itCanConvertLondonCoordinates() {
        int expectedOffsetMins = 60;
        int actualOffsetMins = TimezoneToDateConverter.getOffsetMins(LAT_LONDON, LON_LONDON);

        assertEquals("This should only break when DST changes", expectedOffsetMins, actualOffsetMins);
    }

    @Test
    public void itCanConvertSydneyCoordinates() {
        int expectedOffsetMins = 600;
        int actualOffsetMins = TimezoneToDateConverter.getOffsetMins(LAT_SYDNEY, LON_SYDNEY);

        assertEquals(expectedOffsetMins, actualOffsetMins);
    }

    @Test
    public void itCanCalculateOffsetForCurrentDayLondon() throws Exception {
        long expectedMillis = DateTime.now(DateTimeZone.forOffsetHours(0))
                .withHourOfDay(22)
                .withMinuteOfHour(59)
                .withSecondOfMinute(0)
                .withMillisOfSecond(0)
                .getMillis();

        long actualMillis = TimezoneToDateConverter.getUtcForCurrentDay(LAT_LONDON, LON_LONDON, 23, 59);

        assertEquals("This should only break when DST changes", expectedMillis, actualMillis);
    }

}