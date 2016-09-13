package com.gocgod.ui;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.Toast;

import com.gocgod.cart.CartDataSource;
import com.securepreferences.SecurePreferences;

import java.sql.SQLException;

import carbon.widget.EditText;

/**
 * Created by Kevin on 8/8/2016.
 */
public class Global {
    private static SharedPreferences sharedPreferences;

    public static final String IP = "http://192.168.1.14/";

    public static final String imgProduct = Global.IP + "gocgod/public/assets/images/product/";

    /*
    * cek apakah di server masih login (api token masih sama dgn secure preference)
    * atau kalau api token sudah beda (berarti usernya sudah login dgn account yang sama di device lain),
    * buang secure preference (biar otomatis logout)
    * */
    public static boolean isLoginAtServer(Context context, String type, String message)
    {
        if(type.equalsIgnoreCase("ERROR-LOG OUT API TOKEN"))
        {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.clear();
            editor.commit();

            Toast.makeText(context, message, Toast.LENGTH_LONG).show();
            return false;
        }

        return true;
    }

    //buat nentuin item2 di drawer yang bakal ditampilin
    /*public static boolean isLogin(Context context)
    {
        sharedPreferences = new SecurePreferences(context);
        Boolean is_login = sharedPreferences.getBoolean("is_login", false);

        return is_login;
    }*/

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
