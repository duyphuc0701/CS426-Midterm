package com.example.travelapplication.table;

import android.database.sqlite.SQLiteDatabase;

public abstract class DatabaseTable {
    public abstract void populateData(SQLiteDatabase db);
    public abstract void createTable(SQLiteDatabase db);
    String tableName;

    public DatabaseTable(String tableName) {
        this.tableName = tableName;
    }
}
