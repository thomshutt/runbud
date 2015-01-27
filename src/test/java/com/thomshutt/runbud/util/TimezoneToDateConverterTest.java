package com.thomshutt.runbud.util;

import com.thomshutt.runbud.util.SimpleDate;
import com.thomshutt.runbud.util.TimezoneToDateConverter;
import org.junit.Test;

import static org.junit.Assert.*;

public class TimezoneToDateConverterTest {

    private static final String EXAMPLE_XML =
            "<?xml version=\"1.0\" encoding=\"ISO-8859-1\" ?>\n" +
            "<timezone xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:noNamespaceSchemaLocation=\"http://www.earthtools.org/timezone.xsd\">\n" +
            "  <version>1.0</version>\n" +
            "  <location>\n" +
            "    <latitude>40.71417</latitude>\n" +
            "    <longitude>-74.00639</longitude>\n" +
            "  </location>\n" +
            "  <offset>-5</offset>\n" +
            "  <suffix>R</suffix>\n" +
            "  <localtime>27 Jan 2015 06:59:10</localtime>\n" +
            "  <isotime>2015-01-27 06:59:10 -0500</isotime>\n" +
            "  <utctime>2015-01-27 11:59:10</utctime>\n" +
            "  <dst>Unknown</dst>\n" +
            "</timezone>";

    @Test
    public void itCanParseEarthToolsXml() {
        final SimpleDate simpleDate = TimezoneToDateConverter.parseEarthToolsXml(EXAMPLE_XML);
        assertEquals("Should be able to parse the current day from the XML", 27, simpleDate.day);
        assertEquals("Should be able to parse the current month from the XML", 1, simpleDate.month);
        assertEquals("Should be able to parse the current year from the XML", 2015, simpleDate.year);
    }

}