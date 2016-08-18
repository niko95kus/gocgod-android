package com.gocgod.adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.gocgod.fragment.DescriptionProduct;
import com.gocgod.fragment.TestimonialProduct;

/**
 * Created by Aurel on 16/08/2016.
 */
public class DescriptionTestimonialAdapter extends FragmentPagerAdapter{
    private String deskripsi;
    String[] title = new String[]{
            "Deskripsi", "Ulasan"
    };

    public DescriptionTestimonialAdapter(FragmentManager fm, String description){
        super(fm);
        deskripsi = description;
    }

    @Override
    public Fragment getItem(int position){
        Fragment fragment = null;
        switch (position){
            case 0:
                fragment = new DescriptionProduct();
                Bundle bundle = new Bundle();
                bundle.putString("a", deskripsi);
                fragment.setArguments(bundle);
                break;
            case 1:
                fragment = new TestimonialProduct();
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
