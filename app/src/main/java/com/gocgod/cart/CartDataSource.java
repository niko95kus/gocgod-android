package com.gocgod.cart;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kevin on 8/29/2016.
 */
public class CartDataSource {
    // Database fields
    private SQLiteDatabase database;
    private MySqliteHelper dbHelper;
    private String[] allColumns = {
            MySqliteHelper.IMAGE,
            MySqliteHelper.NAME,
            MySqliteHelper.CATEGORY,
            MySqliteHelper.PRICE,
            MySqliteHelper.DISCOUNTPRICE,
            MySqliteHelper.QUANTITY,
            MySqliteHelper.WEIGHT,
            MySqliteHelper.PRODUCTID,
            MySqliteHelper.SUPPLIER,
            MySqliteHelper.SIZE,
            MySqliteHelper.SIZETEXT
    };

    public CartDataSource(Context context) {
        dbHelper = new MySqliteHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public Cart createCart(int product_id, String name, String image, String category, int quantity, double weight, double price, double discount_price, int supplier, int size, String sizeText) {
        ContentValues values = new ContentValues();
        values.put(MySqliteHelper.NAME,name);
        values.put(MySqliteHelper.PRODUCTID,product_id);
        values.put(MySqliteHelper.IMAGE,image);
        values.put(MySqliteHelper.CATEGORY,category);
        values.put(MySqliteHelper.QUANTITY,quantity);
        values.put(MySqliteHelper.WEIGHT,weight);
        values.put(MySqliteHelper.PRICE,price);
        values.put(MySqliteHelper.DISCOUNTPRICE,discount_price);
        values.put(MySqliteHelper.SUPPLIER, supplier);
        values.put(MySqliteHelper.SIZE, size);
        values.put(MySqliteHelper.SIZETEXT, sizeText);

        int qty = getQtyCartById(product_id);
        if(qty == 0)
        {
            database.insert(MySqliteHelper.TABLE_CART, null, values);
            Cursor cursor = database.query(MySqliteHelper.TABLE_CART,
                    allColumns, MySqliteHelper.PRODUCTID + " = " + product_id, null,
                    null, null, null);

            cursor.moveToFirst();
            Cart newCart = cursorToCart(cursor);
            cursor.close();
            return newCart;
        }
        else
        {
            values.put(MySqliteHelper.QUANTITY, qty+quantity);
            database.update(MySqliteHelper.TABLE_CART, values, MySqliteHelper.PRODUCTID +"= ?",new String[]{String.valueOf(product_id)});
            Cursor cursor = database.query(MySqliteHelper.TABLE_CART,
                    allColumns, MySqliteHelper.PRODUCTID + " = " + product_id, null,
                    null, null, null);
            cursor.moveToFirst();
            Cart newCart = cursorToCart(cursor);
            cursor.close();
            return newCart;
        }
    }

    public void deleteCart(int productid) {

        database.delete(MySqliteHelper.TABLE_CART, MySqliteHelper.PRODUCTID + " = ?",
                new String[]{String.valueOf(productid)});
        Log.d("Delete", String.valueOf(productid));

    }

    public void deleteAllCart() {
        database.delete(MySqliteHelper.TABLE_CART, null, null);
    }

    public int getQtyCartById(int productid) {

        int qty = 0;

        Cursor cursor = database.query(
                MySqliteHelper.TABLE_CART, new String[] {
                        MySqliteHelper.QUANTITY
                }, MySqliteHelper.PRODUCTID + "=?",
                new String[] { String.valueOf(productid) }, null, null, null, null);
        if (cursor != null)
        {
            try{
                cursor.moveToFirst();

                if(cursor.getString(0)!=null)
                {
                    qty = Integer.parseInt(cursor.getString(0));
                }

                Log.d("getQtyCartById",cursor.getString(0));
                cursor.close();
            }
            catch( Exception e)
            {
                Log.d("getQtyCartByIdError",e.getLocalizedMessage());
            }
        }

        return qty;
    }

    public List<Cart> getAllCarts() {
        List<Cart> carts = new ArrayList<Cart>();

        Cursor cursor = database.query(MySqliteHelper.TABLE_CART,
                allColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Cart cart = cursorToCart(cursor);
            carts.add(cart);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return carts;
    }

    private Cart cursorToCart(Cursor cursor) {
        Cart cart = new Cart();
        cart.setImage(cursor.getString(0));
        cart.setName(cursor.getString(1));
        cart.setCategory(cursor.getString(2));
        cart.setPrice(cursor.getDouble(3));
        cart.setDiscount_price(cursor.getDouble(4));
        cart.setQuantity(cursor.getInt(5));
        cart.setWeight(cursor.getDouble(6));
        cart.setProductid(cursor.getInt(7));
        cart.setSupplier(cursor.getInt(8));
        cart.setSize(cursor.getInt(9));
        cart.setSizeText(cursor.getString(10));

        return cart;
    }

    public Double getSubtotal() {
        double subtotal = 0;
        try
        {
            Cursor cur = database.rawQuery("SELECT SUM("+ MySqliteHelper.PRICE + "*" + MySqliteHelper.QUANTITY + ") as subtotal FROM " + MySqliteHelper.TABLE_CART + "", null);
            if(cur.moveToFirst())
            {
                subtotal =  cur.getDouble(0);
            }
        }
        catch(Exception e)
        {
            Log.d("ErrorSubtotal "," "+e.getLocalizedMessage());
        }

        return subtotal;
    }

    public Double getSubtotalFree() {
        double subtotal = 0;

        try
        {
            Cursor cur = database.rawQuery("SELECT SUM(" + MySqliteHelper.PRICE + "*" + MySqliteHelper.QUANTITY + ") as subtotal FROM " + MySqliteHelper.TABLE_CART + " WHERE supplier='3' OR supplier='4'", null);
            if(cur.moveToFirst())
            {
                subtotal =  cur.getDouble(0);
            }
        }
        catch(Exception e)
        {
            Log.d("ErrorSubtotal "," "+e.getLocalizedMessage());
        }

        return subtotal;
    }

    public Double getTotalWeight() {
        double subtotal = 0;
        try
        {
            Cursor cur = database.rawQuery("SELECT SUM(" + MySqliteHelper.WEIGHT + "*" + MySqliteHelper.QUANTITY + ") as subtotal FROM " + MySqliteHelper.TABLE_CART + "", null);

            if(cur.moveToFirst())
            {
                subtotal =  cur.getDouble(0);
            }

        }
        catch(Exception e)
        {
            Log.d("ErrorSubtotal "," "+e.getLocalizedMessage());
        }

        return subtotal;
    }

    public String getOrderItem() {
        Cursor cursor = database.query(MySqliteHelper.TABLE_CART,
                allColumns, null, null, null, null, null);

        JSONArray jsonArr = new JSONArray();
        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            Cart cart = cursorToCart(cursor);
            JSONObject pnObj = new JSONObject();
            try {
                pnObj.put("id", cart.getProductid());
                pnObj.put("qty", cart.getQuantity());

                if (cart.getDiscount_price() != 0) {
                    pnObj.put("price", cart.getDiscount_price());
                } else {
                    pnObj.put("price", cart.getPrice());
                }

                JSONObject options = new JSONObject();
                options.put("size", cart.getSize());
                pnObj.put("options", options);

                jsonArr.put(pnObj);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();

        return jsonArr.toString();
    }

    public int getCountCart() {
        int count = 0;
        try
        {
            Cursor cur = database.rawQuery("SELECT SUM(" + MySqliteHelper.QUANTITY + ") as subtotal FROM " + MySqliteHelper.TABLE_CART + "", null);
            if(cur.moveToFirst())
            {
                count =  cur.getInt(0);
            }

            cur.close();
        }
        catch(Exception e)
        {
            Log.d("ErrorSubtotal "," "+e.getLocalizedMessage());
        }

        return count;
    }

    public void updateQty(int productid, int qty) {
        ContentValues values = new ContentValues();
        values.put(MySqliteHelper.QUANTITY, qty);

        database.update(MySqliteHelper.TABLE_CART, values, MySqliteHelper.PRODUCTID + "=?", new String[]{String.valueOf(productid)});
    }
}
