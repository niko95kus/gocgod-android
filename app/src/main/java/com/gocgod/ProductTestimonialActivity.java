package com.gocgod;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.gocgod.adapter.ProductTestimonialAdapter;
import com.gocgod.model.ProductTestimonial;
import com.gocgod.model.ResponseSuccess;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductTestimonialActivity extends BaseActivity {
    @BindView(R.id.recyclerview)
    carbon.widget.RecyclerView recyclerView;

    private ArrayList<ProductTestimonial> productTestimonials = new ArrayList<ProductTestimonial>();
    private String productId;
    GridLayoutManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_testimonial);
        ButterKnife.bind(this);

        Bundle bundle = getIntent().getExtras();

        buildToolbar("Ulasan Produk");
        buildDrawer(savedInstanceState, toolbar);
        drawer.getActionBarDrawerToggle().setDrawerIndicatorEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(true);

        if(bundle != null)
        {
            productId = bundle.getString("productId");

            manager = new GridLayoutManager(this, 1);
            recyclerView.setLayoutManager(manager);
            recyclerView.addOnScrollListener(new EndlessRecyclerViewScrollListener(manager) {
                @Override
                public void onLoadMore(int page, int totalItemsCount) {
                    initCollection(page);
                }
            });

            ProductTestimonialAdapter adapter = new ProductTestimonialAdapter(this, productTestimonials, false);
            recyclerView.setAdapter(adapter);
        }

        initCollection(1);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private void initCollection(int page) {
        ApiService client = ServiceGenerator.createService(ApiService.class);
        Map<String, String> data = new HashMap<>();
        data.put("page", String.valueOf(page));

        //Ambil data testimoni produk
        Call<ResponseSuccess> callProductTestimonial = client.getProductTestimonial(productId, data);
        callProductTestimonial.enqueue(new Callback<ResponseSuccess>() {
            @Override
            public void onResponse(Call<ResponseSuccess> call, Response<ResponseSuccess> response) {
                ResponseSuccess result = response.body();

                int total = result.getSuccess().getData().getProductTestimonialPagination().getTotal();

                if (total > 0) {
                    productTestimonials.addAll(result.getSuccess().getData().getProductTestimonialPagination().getData());

                    //recyclerView.setVisibility(View.VISIBLE);

                    ProductTestimonialAdapter adapter = (ProductTestimonialAdapter) recyclerView.getAdapter();
                    int curSize = adapter.getItemCount();

                    adapter.notifyItemRangeInserted(curSize, productTestimonials.size() - 1);
                }
            }

            @Override
            public void onFailure(Call<ResponseSuccess> call, Throwable t) {

            }
        });
    }
}
