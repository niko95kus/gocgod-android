package com.gocgod.adapter;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gocgod.R;
import com.gocgod.model.ProductTestimonial;

import java.util.ArrayList;

import carbon.drawable.ripple.RippleDrawable;
import carbon.drawable.ripple.RippleDrawableFroyo;
import carbon.widget.CardView;
import carbon.widget.TextView;

/**
 * Created by Kevin on 8/19/2016.
 */
public class ProductTestimonialAdapter extends RecyclerView.Adapter<ProductTestimonialAdapter.ViewHolder> {
    private Context context;
    private ArrayList<ProductTestimonial> productTestimonials;
    private boolean previewTestimonial;

    public ProductTestimonialAdapter(Context context, ArrayList<ProductTestimonial> productTestimonials, boolean previewTestimonial)
    {
        this.context = context;
        this.productTestimonials = productTestimonials;
        this.previewTestimonial = previewTestimonial;
    }

    @Override
    public ProductTestimonialAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_product_testimonial, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ProductTestimonialAdapter.ViewHolder holder, int i) {
        if(!productTestimonials.isEmpty())
        {
            ProductTestimonial testimonial = productTestimonials.get(i);

            holder.name.setText(testimonial.getName());
            holder.testimoni.setText(testimonial.getTestimonial());
            holder.date.setText(testimonial.getCreatedAt());
        }
    }

    @Override
    public int getItemCount() {
        if(previewTestimonial)
        {
            if(productTestimonials.size() < 4) return productTestimonials.size();
            else return 4;
        }
        else return productTestimonials.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView testimoni;
        TextView name;
        TextView date;
        public CardView card;

        public ViewHolder(View view) {
            super(view);

            name = (TextView) view.findViewById(R.id.name);
            testimoni = (TextView) view.findViewById(R.id.productTestimonial);
            date = (TextView) view.findViewById(R.id.date);

            card = (CardView) view;
            card.setClickable(true);
            card.setBackgroundColor(Color.WHITE);

            RippleDrawable rippleDrawable = new RippleDrawableFroyo(ColorStateList.valueOf(Color.rgb(0, 204, 255)), null, RippleDrawable.Style.Over);
            rippleDrawable.setCallback(card);
            rippleDrawable.setHotspotEnabled(true);
            card.setRippleDrawable(rippleDrawable);
        }
    }
}
