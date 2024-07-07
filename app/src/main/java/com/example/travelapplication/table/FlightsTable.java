package com.example.travelapplication.table;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import com.example.travelapplication.TravelDatabaseHelper;

public class FlightsTable extends DatabaseTable {
    public FlightsTable(String tableName) {
        super(tableName);
    }
    public void insertFlight(SQLiteDatabase db,
                             String departureCity, String arrivalCity,
                             String departureDate, int departureTime,
                             int arrivalTime, int price,
                             String number, String brand) {
        ContentValues flightValues = new ContentValues();
        flightValues.put("departureCity", departureCity);
        flightValues.put("arrivalCity", arrivalCity);
        flightValues.put("departureDate", departureDate);
        flightValues.put("departureTime", departureTime);
        flightValues.put("arrivalTime", arrivalTime);
        flightValues.put("price", price);
        flightValues.put("number", number);
        flightValues.put("brand", brand);
        db.insert(TravelDatabaseHelper.TABLE_FLIGHTS, null, flightValues);
    }
    @Override
    public void populateData(SQLiteDatabase db) {
        // NYC - LDN flights, 10/07/2024
        insertFlight(db, "NYC", "LDN", "10/07/2024",
                420, 540, 50, "NL-10", "British Airways Flight");
        insertFlight(db, "NYC", "LDN", "10/07/2024",
                450, 570, 100, "NL-11","American Airways Flight");
        insertFlight(db, "NYC", "LDN", "10/07/2024",
                480, 600, 35, "NL-12","British Airways Flight");
        insertFlight(db, "NYC", "LDN", "10/07/2024",
                510, 630, 45, "NL-13","American Airways Flight");
        insertFlight(db, "NYC", "LDN", "10/07/2024",
                540, 660, 60, "NL-14","American Airways Flight");
        insertFlight(db, "NYC", "LDN", "10/07/2024",
                570, 690, 75, "NL-15","American Airways Flight");
        insertFlight(db, "NYC", "LDN", "10/07/2024",
                960, 1110, 300, "NL-16","British Airways Flight");
        insertFlight(db, "NYC", "LDN", "10/07/2024",
                1260, 1380, 55, "NL-17","British Airways Flight");
        insertFlight(db, "NYC", "LDN", "10/07/2024",
                780, 900, 150, "NL-18","American Airways Flight");
        // NYC - LDN flights, 11/07/2024
        insertFlight(db, "NYC", "LDN", "11/07/2024",
                420, 540, 50, "NL-10","British Airways Flight");
        insertFlight(db, "NYC", "LDN", "11/07/2024",
                450, 570, 100, "NL-11","American Airways Flight");
        insertFlight(db, "NYC", "LDN", "11/07/2024",
                480, 600, 35, "NL-12","American Airways Flight");
        insertFlight(db, "NYC", "LDN", "11/07/2024",
                510, 630, 45, "NL-13","British Airways Flight");
        insertFlight(db, "NYC", "LDN", "11/07/2024",
                540, 660, 60, "NL-14","British Airways Flight");
        // NYC - LDN flights, 12/07/2024
        insertFlight(db, "NYC", "LDN", "12/07/2024",
                420, 540, 50, "NL-10","American Airways Flight");
        insertFlight(db, "NYC", "LDN", "12/07/2024",
                450, 570, 100, "NL-11","British Airways Flight");
        insertFlight(db, "NYC", "LDN", "12/07/2024",
                480, 600, 35, "NL-12","American Airways Flight");
        insertFlight(db, "NYC", "LDN", "12/07/2024",
                510, 630, 45, "NL-13","British Airways Flight");
        insertFlight(db, "NYC", "LDN", "12/07/2024",
                540, 660, 60, "NL-14","American Airways Flight");
        insertFlight(db, "NYC", "LDN", "12/07/2024",
                570, 690, 75, "NL-15","British Airways Flight");

        // LDN - NYC flights, 10/07/2024
        insertFlight(db, "LDN", "NYC", "10/07/2024",
                420, 540, 30, "LN-10","American Airways Flight");
        insertFlight(db, "LDN", "NYC", "10/07/2024",
                450, 570, 40, "LN-11","American Airways Flight");
        insertFlight(db, "LDN", "NYC", "10/07/2024",
                480, 600, 50, "LN-12","British Airways Flight");
        insertFlight(db, "LDN", "NYC", "10/07/2024",
                1000, 1150, 55, "LN-13","British Airways Flight");
        insertFlight(db, "LDN", "NYC", "10/07/2024",
                800, 1000, 30, "LN-14","American Airways Flight");

        // LDN - NYC flights, 11/07/2024
        insertFlight(db, "LDN", "NYC", "11/07/2024",
                420, 540, 30, "LN-10","American Airways Flight");
        insertFlight(db, "LDN", "NYC", "11/07/2024",
                450, 570, 40, "LN-11","British Airways Flight");
        insertFlight(db, "LDN", "NYC", "11/07/2024",
                480, 600, 50, "LN-12","American Airways Flight");
        insertFlight(db, "LDN", "NYC", "11/07/2024",
                1000, 1150, 55, "LN-13","British Airways Flight");
        insertFlight(db, "LDN", "NYC", "11/07/2024",
        800, 1000, 30, "LN-14","British Airways Flight");
        insertFlight(db, "LDN", "NYC", "11/07/2024",
                700, 930, 30, "LN-15","British Airways Flight");
    }

    // Store departureTime and arrivalTime in minutes (count from 12 AM)
    // Store departureDate in the format "DD/MM/YYYY"
    @Override
    public void createTable(SQLiteDatabase db) {
        db.execSQL(
                "CREATE TABLE flights(" +
                        "_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "departureCity TEXT, " +
                        "arrivalCity TEXT, " +
                        "departureDate TEXT, " +
                        "departureTime INTEGER, " +
                        "arrivalTime INTEGER, " +
                        "price INTEGER, " +
                        "number TEXT, " +
                        "brand TEXT );"
        );
    }
}
