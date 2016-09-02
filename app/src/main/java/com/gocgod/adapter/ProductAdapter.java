package com.gocgod.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.gocgod.ui.Global;
import com.gocgod.ui.product.ProductDetailActivity;
import com.gocgod.R;
import com.gocgod.model.ProductData;
import com.squareup.picasso.Picasso;

import java.util.List;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

import carbon.drawable.ripple.RippleDrawable;
import carbon.drawable.ripple.RippleDrawableFroyo;
import carbon.widget.CardView;

/**
 * Created by Kevin on 7/27/2016.
 */
public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {
    private Context context;
    private List<ProductData> productData;

    public ProductAdapter(Context context, List<ProductData> productData) {
        this.context = context;
        this.productData = productData;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_product, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int i) {
        ProductData data = productData.get(i);

        //untuk format rupiah
        DecimalFormat kursIndonesia = (DecimalFormat) DecimalFormat.getCurrencyInstance();
        DecimalFormatSymbols formatRp = new DecimalFormatSymbols();
        formatRp.setCurrencySymbol("Rp ");
        formatRp.setMonetaryDecimalSeparator(',');
        formatRp.setGroupingSeparator('.');
        kursIndonesia.setDecimalFormatSymbols(formatRp);

        //untuk ubah tulisan
        Typeface font = Typeface.createFromAsset(context.getAssets(), "fonts/nexablack.ttf");

        Picasso.with(holder.image.getContext()).load(Global.imgProduct + data.getCategoryName() + "/" + data.getPicture()).resize(600,650).into(holder.image);
        holder.name.setTypeface(font);
        holder.name.setText(data.getVarianName());
        double harga = Double.parseDouble(data.getPrice());
        holder.price.setText(kursIndonesia.format(harga));

        /*ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) holder.card.getLayoutParams();
        if (position != items.size() - 1) {
            layoutParams.bottomMargin = 0;
            holder.card.setLayoutParams(layoutParams);
        }*/

        /*ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) holder.card.getLayoutParams();
        if (position != 10 - 1) {
            layoutParams.bottomMargin = 0;
            holder.card.setLayoutParams(layoutParams);
        }*/
    }

    @Override
    public int getItemCount() {
        return productData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        ImageView image;
        TextView name;
        TextView price;
        public CardView card;

        public ViewHolder(View view) {
            super(view);

            image = (ImageView) view.findViewById(R.id.image);
            name = (TextView) view.findViewById(R.id.name);
            price = (TextView) view.findViewById(R.id.price);
            card = (CardView) view;
            card.setClickable(true);
            card.setBackgroundColor(Color.WHITE);

            RippleDrawable rippleDrawable = new RippleDrawableFroyo(ColorStateList.valueOf(Color.rgb(0, 204, 255)), null, RippleDrawable.Style.Over);
            rippleDrawable.setCallback(card);
            rippleDrawable.setHotspotEnabled(true);
            card.setRippleDrawable(rippleDrawable);
            card.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            //Log.d("position", Integer.toString(productData.get(position).getVarianId()));
            Intent intent = new Intent(context, ProductDetailActivity.class);
            Bundle bundle = new Bundle();
            bundle.putInt("productId", productData.get(position).getVarianId());
            intent.putExtras(bundle);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        }
    }
}
