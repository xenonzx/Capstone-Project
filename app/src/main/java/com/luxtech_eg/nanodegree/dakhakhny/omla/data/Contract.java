package com.luxtech_eg.nanodegree.dakhakhny.omla.data;


import android.net.Uri;
import android.provider.BaseColumns;

public final class Contract {

    static final String AUTHORITY = "com.luxtech_eg.nanodegree.dakhakhny.omla";
    static final String PATH_BANK = "bank";
    static final String PATH_BANK_WITH_SYMBOL = "bank/*";
    private static final Uri BASE_URI = Uri.parse("content://" + AUTHORITY);

    private Contract() {
    }

    public static final class Bank implements BaseColumns {

        public static final Uri URI = BASE_URI.buildUpon().appendPath(PATH_BANK).build();
        public static final String COLUMN_BANK_SYMBOL = "bank_symbol";
        public static final String COLUMN_BANK_TITLE = "bank_title";
        public static final String COLUMN_BANK_URL = "bank_ref";
        public static final String COLUMN_USD_BUY_PRICE = "usd_buy";
        public static final String COLUMN_USD_SELL_PRICE = "usd_sell";
        public static final String COLUMN_EUR_BUY_PRICE = "eur_buy";
        public static final String COLUMN_EUR_SELL_PRICE = "eur_sell";
        public static final String COLUMN_SAR_BUY_PRICE = "sar_buy";
        public static final String COLUMN_SAR_SELL_PRICE = "sar_sell";
        public static final String COLUMN_GBP_BUY_PRICE = "gbp_buy";
        public static final String COLUMN_GBP_SELL_PRICE = "gbp_sell";


        public static final int POSITION_ID = 0;
        public static final int POSITION_BANK_SYMBOL = 1;
        public static final int POSITION_BANK_TITLE = 2;
        public static final int POSITION_BANK_URL = 3;
        public static final int POSITION_USD_BUY_PRICE = 4;
        public static final int POSITION_USD_SELL_PRICE = 5;
        public static final int POSITION_EUR_BUY_PRICE = 6;
        public static final int POSITION_EUR_SELL_PRICE = 7;
        public static final int POSITION_SAR_BUY_PRICE = 8;
        public static final int POSITION_SAR_SELL_PRICE = 9;
        public static final int POSITION_GBP_BUY_PRICE = 10;
        public static final int POSITION_GBP_SELL_PRICE = 11;

        public static final String[] BANK_COLUMNS = {
                _ID,
                COLUMN_BANK_SYMBOL,
                COLUMN_BANK_TITLE,
                COLUMN_BANK_URL,
                COLUMN_USD_BUY_PRICE,
                COLUMN_USD_SELL_PRICE,
                COLUMN_EUR_BUY_PRICE,
                COLUMN_EUR_SELL_PRICE,
                COLUMN_SAR_BUY_PRICE,
                COLUMN_SAR_SELL_PRICE,
                COLUMN_GBP_BUY_PRICE,
                COLUMN_GBP_SELL_PRICE

        };
        static final String TABLE_NAME = "banks";

        public static Uri makeUriForBank(String symbol) {
            return URI.buildUpon().appendPath(symbol).build();
        }

        public static String getBankFromUri(Uri queryUri) {
            return queryUri.getLastPathSegment();
        }


    }

}
