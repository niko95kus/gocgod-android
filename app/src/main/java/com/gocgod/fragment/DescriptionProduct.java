package com.gocgod.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gocgod.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class DescriptionProduct extends Fragment {
    @BindView(R.id.description)
    TextView description;
    private String deskripsi;

    public DescriptionProduct() {
        // Required empty public constructor
    }

//    @Override
//    public void onCreate(Bundle savedIntanceState){
//        super.onCreate(savedIntanceState);
//        Bundle bundle = getActivity().getIntent().getExtras();
//        deskripsi = bundle.getString("a");
//        deskripsi = getActivity().getIntent().getStringExtra("a");
////        Log.d("des", bundle.getString("a"));
//        //Intent intent = getActivity().getIntent();
////        deskripsi = intent.getStringExtra("a");
//    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
//        String productDescription = getArguments().getString("deskripsiFragment");
//        description.setText(productDescription);
//        Bundle bundle = getActivity().getIntent().getExtras();
        View view = inflater.inflate(R.layout.fragment_description_product, container, false);
        ButterKnife.bind(this, view);
        deskripsi = getArguments().getString("description");
        description.setText(deskripsi);
        return view;
    }

}
