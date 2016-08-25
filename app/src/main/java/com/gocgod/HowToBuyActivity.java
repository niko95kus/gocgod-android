package com.gocgod;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;

import com.gocgod.adapter.HowToBuyAdapter;
import com.gocgod.adapter.HowToBuySectionAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import carbon.widget.RecyclerView;
import carbon.widget.TextView;

public class HowToBuyActivity extends BaseActivity {
    private List<String> fruits;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_how_to_buy);
        ButterKnife.bind(this);

        buildToolbar("");
        buildDrawer(savedInstanceState, toolbar);

        fruits = new ArrayList<>(Arrays.asList(
                getResources().getString(R.string.first),//1
                getResources().getString(R.string.single),//2
                getResources().getString(R.string.subscriber)//3
        ));

        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        final HowToBuyAdapter helpAdapter = new HowToBuyAdapter(fruits);
        recyclerView.setAdapter(helpAdapter);

        recyclerView.setHeader(R.layout.header_howtobuy);

        //This is the code to provide a sectioned list
        List<HowToBuySectionAdapter.Section> sections =
                new ArrayList<HowToBuySectionAdapter.Section>();

        //Sections
        sections.add(new HowToBuySectionAdapter.Section(0, getString(R.string.howtobuy)));

        //Add your adapter to the sectionAdapter
        HowToBuySectionAdapter.Section[] dummy = new HowToBuySectionAdapter.Section[sections.size()];
        HowToBuySectionAdapter mSectionedAdapter = new
                HowToBuySectionAdapter(this,R.layout.row_section,R.id.section_text,helpAdapter);
        mSectionedAdapter.setSections(sections.toArray(dummy));

        //Apply this adapter to the RecyclerView
        recyclerView.setAdapter(mSectionedAdapter);
        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(this, recyclerView, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Log.d("Help", "Position:" + position);
                Intent browserIntent = null;

                switch (position){
                    case 1:
                        browserIntent = new Intent(HowToBuyActivity.this, HowToBuyWebActivity.class);
                        browserIntent.putExtra("my_url", Global.IP + "gocgod/public/howtobuyfirst");
                        browserIntent.putExtra("my_title", getString(R.string.first));
                        break;
                    case 2:
                        browserIntent = new Intent(HowToBuyActivity.this, HowToBuyWebActivity.class);
                        browserIntent.putExtra("my_url", Global.IP + "gocgod/public/howtobuysingle");
                        browserIntent.putExtra("my_title", getString(R.string.single));
                        break;
                    case 3:
                        browserIntent = new Intent(HowToBuyActivity.this, HowToBuyWebActivity.class);
                        browserIntent.putExtra("my_url", Global.IP + "gocgod/public/howtobuysubcriber");
                        browserIntent.putExtra("my_title", getString(R.string.subscriber));
                        break;
                }

                if (browserIntent != null) {
                    startActivity(browserIntent);
                }
            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        }));
    }
}
