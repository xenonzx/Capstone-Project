package com.luxtech_eg.nanodegree.dakhakhny.omla.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


class DbHelper extends SQLiteOpenHelper {


    static final String NAME = "Omla.db";
    private static final int VERSION = 1;


    DbHelper(Context context) {
        super(context, NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String builder = "CREATE TABLE " + Contract.Bank.TABLE_NAME + " ("
                + Contract.Bank._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + Contract.Bank.COLUMN_BANK_SYMBOL + " TEXT NOT NULL, "
                + Contract.Bank.COLUMN_BANK_TITLE + " TEXT NOT NULL, "
                + Contract.Bank.COLUMN_BANK_URL + " TEXT NOT NULL, "
                + Contract.Bank.COLUMN_USD_BUY_PRICE + " REAL NOT NULL, "
                + Contract.Bank.COLUMN_USD_SELL_PRICE + " REAL NOT NULL, "
                + Contract.Bank.COLUMN_EUR_BUY_PRICE + " REAL NOT NULL, "
                + Contract.Bank.COLUMN_EUR_SELL_PRICE + " REAL NOT NULL, "
                + Contract.Bank.COLUMN_SAR_BUY_PRICE + " REAL NOT NULL, "
                + Contract.Bank.COLUMN_SAR_SELL_PRICE + " REAL NOT NULL, "
                + Contract.Bank.COLUMN_GBP_BUY_PRICE + " REAL NOT NULL, "
                + Contract.Bank.COLUMN_GBP_SELL_PRICE + " REAL NOT NULL, "
                + "UNIQUE (" + Contract.Bank.COLUMN_BANK_SYMBOL + ") ON CONFLICT REPLACE);";

        db.execSQL(builder);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL(" DROP TABLE IF EXISTS " + Contract.Bank.TABLE_NAME);

        onCreate(db);
    }
}
