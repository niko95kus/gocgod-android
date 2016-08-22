package com.gocgod.adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

import com.gocgod.fragment.DescriptionProduct;
import com.gocgod.fragment.TestimonialProduct;
import com.gocgod.model.ProductTestimonial;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Aurel on 16/08/2016.
 */
public class DescriptionTestimonialAdapter extends FragmentPagerAdapter{
    private String deskripsi;
    private String productId;
    private ArrayList<ProductTestimonial> productTestimonials;
    String[] title = new String[]{
            "Deskripsi", "Ulasan"
    };

    public DescriptionTestimonialAdapter(FragmentManager fm, String description, ArrayList<ProductTestimonial> productTestimonials, String productId){
        super(fm);
        this.deskripsi = description;
        this.productTestimonials = productTestimonials;
        this.productId = productId;
    }

    @Override
    public Fragment getItem(int position){
        Fragment fragment = null;
        Bundle bundle = null;
        switch (position){
            case 0:
                fragment = new DescriptionProduct();
                bundle = new Bundle();
                bundle.putString("description", deskripsi);
                fragment.setArguments(bundle);
                break;
            case 1:
                fragment = new TestimonialProduct();
                bundle = new Bundle();
                bundle.putParcelableArrayList("productTestimonial", productTestimonials);
                bundle.putString("productId", productId);
                fragment.setArguments(bundle);
                break;
            default:
                fragment = null;
                break;
        }
        return fragment;
    }

    @Override
    public CharSequence getPageTitle(int position){
        return title[position];
    }

    @Override
    public int getCount(){
        return title.length;
    }


}
