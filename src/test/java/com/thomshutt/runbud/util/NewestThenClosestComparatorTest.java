package com.thomshutt.runbud.util;

import static com.thomshutt.runbud.util.LatitudeLongitude.PICC_CIRCUS_LAT_LON;
import static org.junit.Assert.*;

import java.util.Collections;
import java.util.List;

import org.junit.Test;

import com.google.common.collect.Lists;
import com.thomshutt.runbud.core.Run;

public class NewestThenClosestComparatorTest {

    private final long currentTime = System.currentTimeMillis();

    private final Run futureDistantRun = createRun(PICC_CIRCUS_LAT_LON.latitude, PICC_CIRCUS_LAT_LON.longitude + 1, currentTime + 10000);
    private final Run futureNearbyRun = createRun(PICC_CIRCUS_LAT_LON.latitude, PICC_CIRCUS_LAT_LON.longitude, currentTime + 10000);
    private final Run alreadyHappenedNearbyRun = createRun(PICC_CIRCUS_LAT_LON.latitude, PICC_CIRCUS_LAT_LON.longitude, currentTime - 10000);

    private final NewestThenClosestComparator underTest = new NewestThenClosestComparator(
            currentTime,
            PICC_CIRCUS_LAT_LON
    );

    @Test
    public void itSortsFirstByEventsThatHaventHappenedAndThenByDistance() throws Exception {
        final List<Run> runs = Lists.<Run>newArrayList(
                alreadyHappenedNearbyRun,
                futureNearbyRun,
                futureDistantRun
        );

        Collections.sort(runs, underTest);

        assertEquals(runs.get(0), futureNearbyRun);
        assertEquals(runs.get(1), futureDistantRun);
        assertEquals(runs.get(2), alreadyHappenedNearbyRun);
    }

    private Run createRun(double lat, double lon, long startTime) {
        return new Run(
                1,
                lat,
                lon,
                "",
                1,
                startTime,
                12,
                00,
                "",
                "",
                ""
        );
    }

}