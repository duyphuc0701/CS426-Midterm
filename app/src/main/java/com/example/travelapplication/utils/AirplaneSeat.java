package com.example.travelapplication.utils;


public class AirplaneSeat {
    private final int seatRow;
    private final char seatColumn;
    private int backgroundResource;

    public AirplaneSeat(int seatRow, char seatColumn, int backgroundResource) {
        this.seatRow = seatRow;
        this.seatColumn = seatColumn;
        this.backgroundResource = backgroundResource;
    }

    public int getBackgroundResource() {
        return backgroundResource;
    }

    public void setBackgroundResource(int newResource) {
        this.backgroundResource = newResource;
    }

    public int getSeatRow() {
        return seatRow;
    }

    public char getSeatColumn() {
        return seatColumn;
    }
}
