package com.example.travelapplication.utils;

public class DateTab {
    private final String dayInWeek;
    private final int dayInMonth;
    private boolean isActive;

    public DateTab(String dayInWeek, int dayInMonth, boolean isActive) {
        this.dayInWeek = dayInWeek;
        this.dayInMonth = dayInMonth;
        this.isActive = isActive;
    }

    public String getDayInWeek() {
        return dayInWeek;
    }

    public String getDayInMonth() {
        return String.valueOf(dayInMonth);
    }

    public  int getDayInMonthValue() {
        return dayInMonth;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}
