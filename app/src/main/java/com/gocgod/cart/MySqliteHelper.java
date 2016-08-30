package com.gocgod.cart;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Kevin on 8/29/2016.
 */
public class MySqliteHelper extends SQLiteOpenHelper {

    public static final String PRODUCTID = "product_id";
    public static final String QUANTITY = "qty";
    public static final String NAME = "name";
    public static final String IMAGE = "image";
    public static final String CATEGORY = "category";
    public static final String PRICE = "price";
    public static final String DISCOUNTPRICE = "discount_price";
    public static final String WEIGHT = "weight";
    public static final String SUPPLIER = "supplier";
    public static final String SIZE = "size";
    public static final String SIZETEXT = "sizeText";

    public static final String TABLE_CART = "cart";
    private static final String DATABASE_NAME = "cart.db";
    private static final int DATABASE_VERSION = 2;

    private static final String DATABASE_CREATE = "create table cart (product_id INTEGER PRIMARY KEY, qty INTEGER, name TEXT, image TEXT, category TEXT, price REAL, discount_price REAL, weight REAL, supplier INTEGER, size INTEGER, sizeText TEXT);";

    public MySqliteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d(MySqliteHelper.class.getName(), "Upgrading database from version " + oldVersion + " to " + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CART);
        onCreate(db);
    }

}