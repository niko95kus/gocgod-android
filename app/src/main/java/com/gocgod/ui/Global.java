package com.gocgod.ui;

import android.app.Activity;
import android.content.Context;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import com.gocgod.cart.CartDataSource;

import java.sql.SQLException;

import carbon.widget.EditText;

/**
 * Created by Kevin on 8/8/2016.
 */
public class Global {
    public static final String IP = "http://192.168.1.15/";
    public static final String imgProduct = Global.IP + "gocgod/public/assets/images/product/";

    public static double getCartTotalPrice(Context context) throws SQLException {
        CartDataSource dataSource = new CartDataSource(context);
        double totalPrice = 0;

        try {
            dataSource.open();

            totalPrice = dataSource.getSubtotal();
        } catch (Exception e) {
            Log.d("sqlError", e.getMessage());
        }finally {
            dataSource.close();
        }

        dataSource.close();

        return totalPrice;
    }

    public static int getCartCount(Context context) throws SQLException {
        CartDataSource dataSource = new CartDataSource(context);
        int cart = 0;

        try {
            dataSource.open();

            cart = dataSource.getCountCart();
        } catch (Exception e) {
            Log.d("sqlError", e.getMessage());
        }finally {
            dataSource.close();
        }

        dataSource.close();

        return cart;
    }

    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager =
                (InputMethodManager) activity.getSystemService(
                        Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(
                activity.getCurrentFocus().getWindowToken(), 0);
    }

    public static void setupUI(View view, final Activity activity,final carbon.widget.EditText qty) {
        // Set up touch listener for non-text box views to hide keyboard.
        if (!(view instanceof EditText)) {
            view.setOnTouchListener(new View.OnTouchListener() {
                public boolean onTouch(View v, MotionEvent event) {
                    Global.hideSoftKeyboard(activity);
                    if(qty != null) qty.setFocusable(false);
                    return false;
                }
            });
        }

        //If a layout container, iterate over children and seed recursion.
        if (view instanceof ViewGroup) {
            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
                View innerView = ((ViewGroup) view).getChildAt(i);
                setupUI(innerView, activity, qty);
            }
        }
    }

    public static void showCursor(carbon.widget.EditText qty)
    {
        qty.setFocusableInTouchMode(true);
    }

    public static void showSnackBar(View layout, String message, int messageColor)
    {
        Snackbar snackbar = Snackbar
                .make(layout, message, Snackbar.LENGTH_LONG);

        // Changing action button text color
        View sbView = snackbar.getView();
        TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(messageColor);

        snackbar.show();
    }
}
