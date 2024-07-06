package com.example.travelapplication.table;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import com.example.travelapplication.TravelDatabaseHelper;

public class FlightsTable extends DatabaseTable {
    public FlightsTable(String tableName) {
        super(tableName);
    }
    public void insertFlight(SQLiteDatabase db, String departureCity, String arrivalCity,
                             String departureDate, int departureTime,
                             int arrivalTime, int price, String number) {
        ContentValues flightValues = new ContentValues();
        flightValues.put("departureCity", departureCity);
        flightValues.put("arrivalCity", arrivalCity);
        flightValues.put("departureDate", departureDate);
        flightValues.put("departureTime", departureTime);
        flightValues.put("arrivalTime", arrivalTime);
        flightValues.put("price", price);
        flightValues.put("number", number);
        db.insert(TravelDatabaseHelper.TABLE_FLIGHTS, null, flightValues);
    }
    @Override
    public void populateData(SQLiteDatabase db) {
        // NYC - LDN flights, 10/07/2024
        insertFlight(db, "NYC", "LDN", "10/07/2024",
                420, 540, 50, "NL-10");
        insertFlight(db, "NYC", "LDN", "10/07/2024",
                450, 570, 100, "NL-11");
        insertFlight(db, "NYC", "LDN", "10/07/2024",
                480, 600, 35, "NL-12");
        insertFlight(db, "NYC", "LDN", "10/07/2024",
                510, 630, 45, "NL-13");
        insertFlight(db, "NYC", "LDN", "10/07/2024",
                540, 660, 60, "NL-14");
        insertFlight(db, "NYC", "LDN", "10/07/2024",
                570, 690, 75, "NL-15");
        insertFlight(db, "NYC", "LDN", "10/07/2024",
                960, 1110, 300, "NL-16");
        insertFlight(db, "NYC", "LDN", "10/07/2024",
                1260, 1380, 55, "NL-17");
        insertFlight(db, "NYC", "LDN", "10/07/2024",
                780, 900, 150, "NL-18");
        // NYC - LDN flights, 11/07/2024
        insertFlight(db, "NYC", "LDN", "11/07/2024",
                420, 540, 50, "NL-10");
        insertFlight(db, "NYC", "LDN", "11/07/2024",
                450, 570, 100, "NL-11");
        insertFlight(db, "NYC", "LDN", "11/07/2024",
                480, 600, 35, "NL-12");
        insertFlight(db, "NYC", "LDN", "11/07/2024",
                510, 630, 45, "NL-13");
        insertFlight(db, "NYC", "LDN", "11/07/2024",
                540, 660, 60, "NL-14");
        // NYC - LDN flights, 12/07/2024
        insertFlight(db, "NYC", "LDN", "12/07/2024",
                420, 540, 50, "NL-10");
        insertFlight(db, "NYC", "LDN", "12/07/2024",
                450, 570, 100, "NL-11");
        insertFlight(db, "NYC", "LDN", "12/07/2024",
                480, 600, 35, "NL-12");
        insertFlight(db, "NYC", "LDN", "12/07/2024",
                510, 630, 45, "NL-13");
        insertFlight(db, "NYC", "LDN", "12/07/2024",
                540, 660, 60, "NL-14");
        insertFlight(db, "NYC", "LDN", "12/07/2024",
                570, 690, 75, "NL-15");

        // LDN - NYC flights, 10/07/2024
        insertFlight(db, "LDN", "NYC", "10/07/2024",
                420, 540, 30, "LN-10");
        insertFlight(db, "LDN", "NYC", "10/07/2024",
                450, 570, 40, "LN-11");
        insertFlight(db, "LDN", "NYC", "10/07/2024",
                480, 600, 50, "LN-12");
        insertFlight(db, "LDN", "NYC", "10/07/2024",
                1000, 1150, 55, "LN-13");
        insertFlight(db, "LDN", "NYC", "10/07/2024",
                800, 1000, 30, "LN-14");

        // LDN - NYC flights, 11/07/2024
        insertFlight(db, "LDN", "NYC", "11/07/2024",
                420, 540, 30, "LN-10");
        insertFlight(db, "LDN", "NYC", "11/07/2024",
                450, 570, 40, "LN-11");
        insertFlight(db, "LDN", "NYC", "11/07/2024",
                480, 600, 50, "LN-12");
        insertFlight(db, "LDN", "NYC", "11/07/2024",
                1000, 1150, 55, "LN-13");
        insertFlight(db, "LDN", "NYC", "11/07/2024",
        800, 1000, 30, "LN-14");
        insertFlight(db, "LDN", "NYC", "11/07/2024",
                700, 930, 30, "LN-15");
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
                        "number TEXT );"
        );
    }
}
