package com.gocgod;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.util.Log;
import android.view.View;

import com.gocgod.adapter.CartAdapter;
import com.gocgod.cart.model.Cart;
import com.gocgod.cart.model.CartItem;
import com.gocgod.cart.model.Saleable;
import com.gocgod.cart.util.CartHelper;
import com.gocgod.model.Product;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CartActivity extends BaseActivity {
    @BindView(R.id.recyclerview)
    carbon.widget.RecyclerView recyclerView;
    @BindView(R.id.ongkosKirim)
    carbon.widget.TextView ongkosKirim;
    @BindView(R.id.total)
    carbon.widget.TextView total;
    @BindView(R.id.layoutOngkosKirim)
    carbon.widget.LinearLayout layoutOngkosKirim;


    GridLayoutManager manager;
    private double hargaOngkosKirim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        ButterKnife.bind(this);

        buildToolbar("Shopping Cart");
        buildDrawer(savedInstanceState, toolbar);

        drawer.getActionBarDrawerToggle().setDrawerIndicatorEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final Cart cart = CartHelper.getCart();
        final CartAdapter cartAdapter = new CartAdapter(this);
        cartAdapter.updateCartItems(getCartItems(cart));

        int totalQuantity = cart.getTotalQuantity();

        //format rupiah
        DecimalFormat kursIndonesia = (DecimalFormat) DecimalFormat.getCurrencyInstance();
        DecimalFormatSymbols formatRp = new DecimalFormatSymbols();
        formatRp.setCurrencySymbol("Rp ");
        formatRp.setMonetaryDecimalSeparator(',');
        formatRp.setGroupingSeparator('.');
        kursIndonesia.setDecimalFormatSymbols(formatRp);

        if(totalQuantity < 5)
        {
            hargaOngkosKirim = 10000;
            layoutOngkosKirim.setVisibility(View.VISIBLE);
            ongkosKirim.setText(kursIndonesia.format(hargaOngkosKirim));
        }
        else
        {
            hargaOngkosKirim = 0;
            layoutOngkosKirim.setVisibility(View.GONE);
        }

        total.setText(kursIndonesia.format(cart.getTotalPrice().add(BigDecimal.valueOf(hargaOngkosKirim))));

        recyclerView.setHasFixedSize(true);
        manager = new GridLayoutManager(this, 1);
        recyclerView.setLayoutManager(manager);
        /*recyclerView.addOnScrollListener(new EndlessRecyclerViewScrollListener(manager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                initCollection(page);
            }
        });*/

        recyclerView.setAdapter(cartAdapter);
    }

    private List<CartItem> getCartItems(Cart cart) {
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
}
