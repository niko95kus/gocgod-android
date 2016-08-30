package com.gocgod;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.AsyncTask;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.gocgod.adapter.ProductAdapter;
import com.gocgod.model.ProductData;
import com.gocgod.model.ResponseSuccess;

import com.gocgod.ui.BadgeDrawable;
import com.mikepenz.google_material_typeface_library.GoogleMaterial;
import com.mikepenz.iconics.IconicsDrawable;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//import carbon.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends BaseActivity {
    @BindView(R.id.recyclerview) RecyclerView recyclerView;

    List<ProductData> productData = new ArrayList<ProductData>();
    GridLayoutManager manager;

    private int cartCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        /*Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);*/


        /*PrimaryDrawerItem product = new PrimaryDrawerItem().withName(R.string.product)
                .withIdentifier(1)
                .withIcon(GoogleMaterial.Icon.gmd_free_breakfast);
        PrimaryDrawerItem howtobuy = new PrimaryDrawerItem().withName(R.string.howToBuy)
                .withIdentifier(2)
                .withSelectable(false)
                .withIcon(GoogleMaterial.Icon.gmd_info);
        PrimaryDrawerItem faq = new PrimaryDrawerItem().withName(R.string.faq)
                .withIdentifier(3)
                .withIcon(GoogleMaterial.Icon.gmd_help);


        //menu header
        AccountHeader header = new AccountHeaderBuilder()
                .withActivity(this)
                .withSavedInstance(savedInstanceState)
                .withHeaderBackground(R.color.colorPrimary)
                .withCompactStyle(true)
                .addProfiles(
                        new ProfileDrawerItem().withIcon(R.drawable.logo_1).withName("Smiley")
                                .withEmail("smiley@smiley.com")
                )

                .withSelectionListEnabledForSingleProfile(false)
                .build();

        //create the drawer and remember the `Drawer` result object
        Drawer result = new DrawerBuilder()
                .withSelectedItem(-1)
                .withActivity(this)
                .withToolbar(toolbar)
                .withAccountHeader(header)
                .addDrawerItems(
                        product,
                        howtobuy,
                        faq
                )
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        // do something with the clicked item :D
                        if(drawerItem != null)
                        {
                            Intent intent = null;

                            if(drawerItem.getIdentifier() == 1){
                                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                            }
                            else if(drawerItem.getIdentifier() == 3){
                                startActivity(new Intent(getApplicationContext(), com.gocgod.FaqActivity.class));
                            }
                        }
                        return false;
                    }
                })
                .build();*/

        buildToolbar(null);
        buildDrawer(savedInstanceState, toolbar);

        /*getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        drawer.getActionBarDrawerToggle().setDrawerIndicatorEnabled(true);*/

        recyclerView.setHasFixedSize(true);
        /*RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addOnScrollListener(new EndlessRecyclerViewScrollListener(layoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                initCollection(page);
            }
        });*/

        manager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(manager);
        recyclerView.addOnScrollListener(new EndlessRecyclerViewScrollListener(manager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                initCollection(page);
            }
        });

        ProductAdapter adapter = new ProductAdapter(getApplicationContext(), productData);
        recyclerView.setAdapter(adapter);

        initCollection(1);

        new FetchCartCountTask().execute();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        MenuItem item = menu.findItem(R.id.action_cart);
        LayerDrawable icon = (LayerDrawable) item.getIcon();

        try {
            cartCount = Global.getCartCount(this);
        } catch (SQLException e) {
            Log.d("sqlError", e.getMessage());
        }

        BadgeDrawable badge;

        // Reuse drawable if possible
        Drawable reuse = icon.findDrawableByLayerId(R.id.ic_badge);
        if (reuse != null && reuse instanceof BadgeDrawable) {
            badge = (BadgeDrawable) reuse;
        } else {
            badge = new BadgeDrawable(this);
        }

        badge.setCount(cartCount);
        icon.mutate();
        icon.setDrawableByLayerId(R.id.ic_badge, badge);

        IconicsDrawable cartIcon = new IconicsDrawable(this, GoogleMaterial.Icon.gmd_shopping_cart);
        cartIcon = cartIcon.color(Color.WHITE);
        icon.setDrawableByLayerId(R.id.ic_shoppingcart, cartIcon.actionBar());

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menu) {
        int id = menu.getItemId();

        switch(id){
            case R.id.action_cart:
                startActivity(new Intent(this, CartActivity.class));
                return true;
        }
        return super.onOptionsItemSelected(menu);
    }

    @Override
    protected void onResume() {
        super.onResume();
        new FetchCartCountTask().execute();
    }

    class FetchCartCountTask extends AsyncTask<Void, Void, Integer> {

        @Override
        protected Integer doInBackground(Void... params) {
            int cartCount = 0;
            try {
                cartCount = Global.getCartCount(MainActivity.this);
            } catch (SQLException e) {
                e.printStackTrace();
            }

            return cartCount;
        }

        @Override
        public void onPostExecute(Integer count) {
            cartCount = count;
            invalidateOptionsMenu();
        }
    }

    public void initCollection(int page)
    {
        ApiService client = ServiceGenerator.createService(ApiService.class);
        Map<String, String> data = new HashMap<>();
        data.put("page", String.valueOf(page));

        Call<ResponseSuccess> call = client.getProduct(data);
        call.enqueue(new Callback<ResponseSuccess>() {
            @Override
            public void onResponse(Call<ResponseSuccess> call, Response<ResponseSuccess> response) {
                ResponseSuccess result = response.body();

                int total = result.getSuccess().getData().getProductPagination().getTotal();

                if (total > 0) {
                    productData.addAll(result.getSuccess().getData().getProductPagination().getData());

                    recyclerView.setVisibility(View.VISIBLE);

                    ProductAdapter adapter = (ProductAdapter) recyclerView.getAdapter();
                    int curSize = adapter.getItemCount();
                    //Log.d("SHOP COLLECTION", "Notify :" + curSize + " Item Size: " + productData.size());
                    adapter.notifyItemRangeInserted(curSize, productData.size() - 1);
                    recyclerView.invalidate();
                } else {
                    //emptyView.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<ResponseSuccess> call, Throwable t) {

            }
        });
    }
}
