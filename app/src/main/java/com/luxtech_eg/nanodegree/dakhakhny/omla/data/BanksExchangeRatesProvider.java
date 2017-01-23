package com.luxtech_eg.nanodegree.dakhakhny.omla.data;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;


public class BanksExchangeRatesProvider extends ContentProvider {

    static final int BANK = 100;
    static final int BANK_FOR_SYMBOL = 101;

    static UriMatcher uriMatcher = buildUriMatcher();

    private DbHelper dbHelper;

    static UriMatcher buildUriMatcher() {
        UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        matcher.addURI(Contract.AUTHORITY, Contract.PATH_BANK, BANK);
        matcher.addURI(Contract.AUTHORITY, Contract.PATH_BANK_WITH_SYMBOL, BANK_FOR_SYMBOL);
        return matcher;
    }


    @Override
    public boolean onCreate() {
        dbHelper = new DbHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        Cursor returnCursor;
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        switch (uriMatcher.match(uri)) {
            case BANK:
                returnCursor = db.query(
                        Contract.Bank.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;

            case BANK_FOR_SYMBOL:
                returnCursor = db.query(
                        Contract.Bank.TABLE_NAME,
                        projection,
                        Contract.Bank.COLUMN_BANK_SYMBOL + " = ?",
                        new String[]{Contract.Bank.getBankFromUri(uri)},
                        null,
                        null,
                        sortOrder
                );

                break;
            default:
                throw new UnsupportedOperationException("Unknown URI:" + uri);
        }

        returnCursor.setNotificationUri(getContext().getContentResolver(), uri);

//        if (db.isOpen()) {
//            db.close();
//        }

        return returnCursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, ContentValues values) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Uri returnUri;

        switch (uriMatcher.match(uri)) {
            case BANK:
                db.insert(
                        Contract.Bank.TABLE_NAME,
                        null,
                        values
                );
                returnUri = Contract.Bank.URI;
                break;
            default:
                throw new UnsupportedOperationException("Unknown URI:" + uri);
        }

        getContext().getContentResolver().notifyChange(uri, null);


        return returnUri;
    }

    @Override
    public int delete(@NonNull Uri uri, String selection, String[] selectionArgs) {
        final SQLiteDatabase db = dbHelper.getWritableDatabase();
        int rowsDeleted;

        if (null == selection) {
            selection = "1";
        }
        switch (uriMatcher.match(uri)) {
            case BANK:
                rowsDeleted = db.delete(
                        Contract.Bank.TABLE_NAME,
                        selection,
                        selectionArgs
                );

                break;

            case BANK_FOR_SYMBOL:
                String symbol = Contract.Bank.getBankFromUri(uri);
                rowsDeleted = db.delete(
                        Contract.Bank.TABLE_NAME,
                        '"' + symbol + '"' + " =" + Contract.Bank.COLUMN_BANK_SYMBOL,
                        selectionArgs
                );
                break;
            default:
                throw new UnsupportedOperationException("Unknown URI:" + uri);
        }

        if (rowsDeleted != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsDeleted;
    }

    @Override
    public int update(@NonNull Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }

    @Override
    public int bulkInsert(@NonNull Uri uri, @NonNull ContentValues[] values) {

        final SQLiteDatabase db = dbHelper.getWritableDatabase();

        switch (uriMatcher.match(uri)) {
            case BANK:
                db.beginTransaction();
                int returnCount = 0;
                try {
                    for (ContentValues value : values) {
                        db.insert(
                                Contract.Bank.TABLE_NAME,
                                null,
                                value
                        );
                    }
                    db.setTransactionSuccessful();
                } finally {
                    db.endTransaction();
                }
                getContext().getContentResolver().notifyChange(uri, null);
                return returnCount;
            default:
                return super.bulkInsert(uri, values);
        }


    }
}
