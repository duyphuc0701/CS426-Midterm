package com.example.travelapplication;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.travelapplication.table.FlightsTable;
import com.example.travelapplication.utils.FlightTicketUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class TravelDatabaseHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "travelBooking.db"; // the name of our database
    private static final int DB_VERSION = 1; // the version of the database

    private final FlightsTable flightsTable;
    public static final String TABLE_FLIGHTS = "flights";
    public TravelDatabaseHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        flightsTable = new FlightsTable(TABLE_FLIGHTS);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        flightsTable.createTable(db);
        flightsTable.populateData(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // drop table
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FLIGHTS);
        onCreate(db);
    }
    public static String convertMinutesToTimeString(int minutes) {
        // Calculate hours and minutes
        int hours = minutes / 60;
        int remainingMinutes = minutes % 60;

        // Determine AM/PM
        String period = (hours >= 12) ? "PM" : "AM";

        // Adjust hours to 12-hour format
        if (hours > 12) {
            hours -= 12;
        } else if (hours == 0) {
            hours = 12; // Midnight case
        }

        // Format the time string
        return String.format(Locale.ENGLISH,"%d:%02d %s", hours, remainingMinutes, period);
    }
}
