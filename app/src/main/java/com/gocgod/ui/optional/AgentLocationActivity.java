package com.gocgod.ui.optional;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;

import com.gocgod.ApiService;
import com.gocgod.EndlessRecyclerViewScrollListener;
import com.gocgod.R;
import com.gocgod.ServiceGenerator;
import com.gocgod.adapter.AgentLocationAdapter;
import com.gocgod.model.AgentLocation;
import com.gocgod.model.ResponseSuccess;
import com.gocgod.ui.BaseActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AgentLocationActivity extends BaseActivity {
    @BindView(R.id.recyclerview)
    carbon.widget.RecyclerView recyclerView;

    private ArrayList<AgentLocation> agentLocation = new ArrayList<AgentLocation>();
    GridLayoutManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agent_location);

        ButterKnife.bind(this);

        buildToolbar("Lokasi Agen");
        buildDrawer(savedInstanceState, toolbar);

        manager = new GridLayoutManager(this, 1);
        recyclerView.setLayoutManager(manager);
        recyclerView.addOnScrollListener(new EndlessRecyclerViewScrollListener(manager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                initCollection(page);
            }
        });

        AgentLocationAdapter adapter = new AgentLocationAdapter(this, agentLocation);
        recyclerView.setAdapter(adapter);

        initCollection(1);
    }

    private void initCollection(int page) {
        ApiService client = ServiceGenerator.createService(ApiService.class);
        Map<String, String> data = new HashMap<>();
        data.put("page", String.valueOf(page));

        //Ambil data testimoni produk
        Call<ResponseSuccess> callAgentLocation = client.getAgentLocation(data);
        callAgentLocation.enqueue(new Callback<ResponseSuccess>() {
            @Override
            public void onResponse(Call<ResponseSuccess> call, Response<ResponseSuccess> response) {
                ResponseSuccess result = response.body();

                int total = result.getSuccess().getData().getAgent().getTotal();

                if (total > 0) {
                    agentLocation.addAll(result.getSuccess().getData().getAgent().getData());

                    //recyclerView.setVisibility(View.VISIBLE);

                    AgentLocationAdapter adapter = (AgentLocationAdapter) recyclerView.getAdapter();
                    int curSize = adapter.getItemCount();

                    adapter.notifyItemRangeInserted(curSize, agentLocation.size() - 1);
                }
            }

            @Override
            public void onFailure(Call<ResponseSuccess> call, Throwable t) {

            }
        });
    }
}
