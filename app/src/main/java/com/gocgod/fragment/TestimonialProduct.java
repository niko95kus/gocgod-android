package com.gocgod.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gocgod.ApiService;
import com.gocgod.ui.product.ProductTestimonialActivity;
import com.gocgod.R;
import com.gocgod.ServiceGenerator;
import com.gocgod.adapter.ProductTestimonialAdapter;
import com.gocgod.model.ProductTestimonial;
import com.gocgod.model.ResponseSuccess;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import carbon.widget.TextView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class TestimonialProduct extends Fragment implements View.OnClickListener {
    @BindView(R.id.recyclerview)
    RecyclerView recyclerView;
    @BindView(R.id.emptyTestimoni)
    TextView emptyTestimoni;
    @BindView(R.id.btnTestimoni)
    carbon.widget.Button btnTestimoni;

    private ArrayList<ProductTestimonial> productTestimonials;
    private String productId;
    GridLayoutManager manager;

    public TestimonialProduct() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_testimonial_product, container, false);
        ButterKnife.bind(this, view);

        productTestimonials = getArguments().getParcelableArrayList("productTestimonial");
        productId = getArguments().getString("productId");

        if(!productTestimonials.isEmpty())
        {
            manager = new GridLayoutManager(getActivity(), 1);
            recyclerView.setLayoutManager(manager);
            /*recyclerView.addOnScrollListener(new EndlessRecyclerViewScrollListener(manager) {
                @Override
                public void onLoadMore(int page, int totalItemsCount) {
                    initCollection(page);
                }
            });*/

            ProductTestimonialAdapter adapter = new ProductTestimonialAdapter(getActivity(), productTestimonials, true);
            recyclerView.setAdapter(adapter);

            btnTestimoni.setOnClickListener(this);

            emptyTestimoni.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
            btnTestimoni.setVisibility(View.VISIBLE);
        }
        else
        {
            emptyTestimoni.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
            btnTestimoni.setVisibility(View.GONE);
        }

        return view;
    }

    public void initCollection(int page)
    {
        ApiService client = ServiceGenerator.createService(ApiService.class);
        Map<String, String> data = new HashMap<>();
        data.put("page", String.valueOf(1));

        //Ambil data testimoni produk
        Call<ResponseSuccess> callProductTestimonial = client.getProductTestimonial(productId, data);
        callProductTestimonial.enqueue(new Callback<ResponseSuccess>() {
            @Override
            public void onResponse(Call<ResponseSuccess> call, Response<ResponseSuccess> response) {
                ResponseSuccess result = response.body();

                int total = result.getSuccess().getData().getProductTestimonialPagination().getTotal();

                if (total > 0) {
                    productTestimonials.addAll(result.getSuccess().getData().getProductTestimonialPagination()
                            .getData());

                    recyclerView.setVisibility(View.VISIBLE);

                    ProductTestimonialAdapter adapter = (ProductTestimonialAdapter) recyclerView.getAdapter();
                    int curSize = adapter.getItemCount();

                    adapter.notifyItemRangeInserted(curSize, productTestimonials.size() - 1);
                    recyclerView.invalidate();
                }
            }

            @Override
            public void onFailure(Call<ResponseSuccess> call, Throwable t) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(getActivity(), ProductTestimonialActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("productId", productId);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}
