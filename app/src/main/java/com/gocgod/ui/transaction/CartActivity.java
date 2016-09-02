package com.gocgod.ui.transaction;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.util.Log;
import android.view.View;

import com.gocgod.ui.BaseActivity;
import com.gocgod.ui.Global;
import com.gocgod.R;
import com.gocgod.adapter.CartAdapter;

import com.gocgod.cart.Cart;
import com.gocgod.cart.CartDataSource;

import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

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
    private List<Cart> cart = new ArrayList<Cart>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        ButterKnife.bind(this);
        Global.setupUI(findViewById(R.id.layout), CartActivity.this, null);

        buildToolbar("Shopping Cart");
        buildDrawer(savedInstanceState, toolbar);

        drawer.getActionBarDrawerToggle().setDrawerIndicatorEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        CartDataSource dataSource = new CartDataSource(this);

        try{
            dataSource.open();

            cart = dataSource.getAllCarts();
        } catch (SQLException e) {
            Log.d("sqlError", e.getMessage());
        } finally {
            dataSource.close();
        }

        final CartAdapter cartAdapter = new CartAdapter(this, cart);

        updateTotalPrice();

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

    public void updateTotalPrice()
    {
        int totalQuantity = 0;
        double totalPrice = 0;
        double hargaOngkosKirim;

        try {
            totalQuantity = Global.getCartCount(this);
            totalPrice = Global.getCartTotalPrice(this);
        } catch (SQLException e) {
            Log.d("sqlError", e.getMessage());
        }

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

        total.setText(kursIndonesia.format(totalPrice + hargaOngkosKirim));
    }
}
