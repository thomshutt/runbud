package com.thomshutt.runbud.util;

public class SimpleDate {

    public final int day;
    public final int month;
    public final int year;

    public SimpleDate(int day, int month, int year) {
        this.day = day;
        this.month = month;
        this.year = year;
    }

    public String toDbFormat() {
        return year + "-" + month + "-" + day;
    }

}
