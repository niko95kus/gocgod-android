package com.gocgod;

import android.app.Activity;
import android.support.design.widget.Snackbar;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import com.gocgod.cart.model.Cart;
import com.gocgod.cart.model.CartItem;
import com.gocgod.cart.model.Saleable;
import com.gocgod.model.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import carbon.widget.EditText;

/**
 * Created by Kevin on 8/8/2016.
 */
public class Global {
    public static final String IP = "http://192.168.1.8/";
    public static final String imgProduct = Global.IP + "gocgod/public/assets/images/product/";

    public static List<CartItem> getCartItems(Cart cart) {
        List<CartItem> cartItems = new ArrayList<CartItem>();
        //Log.d(TAG, "Current shopping cart: " + cart);

        Map<Saleable, Integer> itemMap = cart.getItemWithQuantity();

        for (Map.Entry<Saleable, Integer> entry : itemMap.entrySet()) {
            CartItem cartItem = new CartItem();
            cartItem.setProduct((Product) entry.getKey());
            cartItem.setQuantity(entry.getValue());
            cartItems.add(cartItem);
        }

        //Log.d(TAG, "Cart item list: " + cartItems);
        return cartItems;
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
