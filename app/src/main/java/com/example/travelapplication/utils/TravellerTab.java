package com.example.travelapplication.utils;

public class TravellerTab {
    private final int travellerNumber;
    private boolean isActive;
    public TravellerTab(int travellerNumber, boolean isActive) {
        this.travellerNumber = travellerNumber;
        this.isActive = isActive;
    }

    public int getTravellerNumber() {
        return travellerNumber;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}
