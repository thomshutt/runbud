package com.thomshutt.runbud.util;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TimezoneToDateConverterTest {

    @Test
    public void itCanConvertLondonCoordinates() {
        int expectedOffsetMins = 60;
        int actualOffsetMins = TimezoneToDateConverter.getOffsetMins(51.5072, 0.1275);

        assertEquals("This should only break when DST changes", expectedOffsetMins, actualOffsetMins);
    }

    @Test
    public void itCanConvertSydneyCoordinates() {
        int expectedOffsetMins = 600;
        int actualOffsetMins = TimezoneToDateConverter.getOffsetMins(-33.8650, 151.2094);

        assertEquals(expectedOffsetMins, actualOffsetMins);
    }

}