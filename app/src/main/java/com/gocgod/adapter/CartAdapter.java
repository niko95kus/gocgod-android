package com.gocgod.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.gocgod.Global;
import com.gocgod.R;
import com.gocgod.cart.model.Cart;
import com.gocgod.cart.model.CartItem;
import com.gocgod.cart.util.CartHelper;
import com.mikepenz.google_material_typeface_library.GoogleMaterial;
import com.mikepenz.iconics.IconicsDrawable;
import com.squareup.picasso.Picasso;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.List;

import carbon.widget.CardView;

/**
 * Created by Kevin on 8/24/2016.
 */
public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {
    private Context context;
    private List<CartItem> cartItems;

    public CartAdapter(Context context)
    {
        this.context = context;
    }

    public void updateCartItems(List<CartItem> cartItems) {
        this.cartItems = cartItems;
        notifyDataSetChanged();
    }

    public CartItem getItem(int position) {
        return cartItems.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_cart, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        //untuk format rupiah
        DecimalFormat kursIndonesia = (DecimalFormat) DecimalFormat.getCurrencyInstance();
        DecimalFormatSymbols formatRp = new DecimalFormatSymbols();
        formatRp.setCurrencySymbol("Rp ");
        formatRp.setMonetaryDecimalSeparator(',');
        formatRp.setGroupingSeparator('.');
        kursIndonesia.setDecimalFormatSymbols(formatRp);

        //untuk ubah tulisan
        Typeface font = Typeface.createFromAsset(context.getAssets(), "fonts/nexablack.ttf");

        final Cart cart = CartHelper.getCart();
        final CartItem cartItem = getItem(position);

        Picasso.with(holder.picture.getContext()).load(Global.imgProduct + cartItem.getProduct().getCategory() + "/" + cartItem.getProduct().getPicture()).resize(80,80).into(holder.picture);
        holder.name.setTypeface(font);
        holder.name.setText(cartItem.getProduct().getName());
        BigDecimal harga = cartItem.getProduct().getPrice();
        holder.price.setText(kursIndonesia.format(harga));
        holder.quantity.setText(String.valueOf(cartItem.getQuantity()));

        int sdk = android.os.Build.VERSION.SDK_INT;
        if(sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
            holder.btnDelete.setBackgroundDrawable(new IconicsDrawable(context, GoogleMaterial.Icon.gmd_delete).color(Color.BLACK));
        } else {
            holder.btnDelete.setBackground(new IconicsDrawable(context, GoogleMaterial.Icon.gmd_delete).color(Color.BLACK));
        }

        ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) holder.card.getLayoutParams();
        if (position != cartItems.size() - 1) {
            layoutParams.bottomMargin = 0;
            holder.card.setLayoutParams(layoutParams);
        }
    }

    @Override
    public int getItemCount() {
        return cartItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        carbon.widget.ImageView picture;
        carbon.widget.TextView name;
        carbon.widget.TextView price;
        carbon.widget.TextView quantity;
        Button btnDelete;
        public CardView card;

        public ViewHolder(View view) {
            super(view);

            picture = (carbon.widget.ImageView) view.findViewById(R.id.picture);
            name = (carbon.widget.TextView) view.findViewById(R.id.name);
            price = (carbon.widget.TextView) view.findViewById(R.id.price);
            quantity = (carbon.widget.TextView) view.findViewById(R.id.quantity);
            btnDelete = (Button) view.findViewById(R.id.btnDelete);
            card = (CardView) view;
            card.setClickable(true);
            card.setBackgroundColor(Color.WHITE);
        }
    }
}
