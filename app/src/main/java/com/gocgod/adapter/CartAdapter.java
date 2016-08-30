package com.gocgod.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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

import com.gocgod.CartActivity;
import com.gocgod.Global;
import com.gocgod.R;
import com.gocgod.cart.Cart;
import com.gocgod.cart.CartDataSource;
import com.mikepenz.google_material_typeface_library.GoogleMaterial;
import com.mikepenz.iconics.IconicsDrawable;
import com.squareup.picasso.Picasso;

import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnFocusChange;
import butterknife.OnTouch;
import carbon.widget.CardView;

/*
 * Created by Kevin on 8/24/2016.
*/

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {
    private Context context;
    private List<Cart> cartItems;

    public CartAdapter(Context context, List<Cart> cartItems)
    {
        this.context = context;
        this.cartItems = cartItems;
    }

    public Cart getItem(int position) {
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

        final Cart cartItem = getItem(position);

        Picasso.with(holder.picture.getContext()).load(Global.imgProduct + cartItem.getCategory() + "/" + cartItem.getImage()).resize(80,80).into(holder.picture);
        holder.name.setTypeface(font);
        holder.name.setText(cartItem.getName());
        Double harga = cartItem.getPrice();
        holder.price.setText(kursIndonesia.format(harga));
        holder.quantity.setText(String.valueOf(cartItem.getQuantity()));

        int sdk = android.os.Build.VERSION.SDK_INT;
        if(sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
            holder.btnDelete.setBackgroundDrawable(new IconicsDrawable(context, GoogleMaterial.Icon.gmd_delete_forever).color(Color.GRAY));
        } else {
            holder.btnDelete.setBackground(new IconicsDrawable(context, GoogleMaterial.Icon.gmd_delete_forever).color(Color.GRAY));
        }

        ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) holder.card.getLayoutParams();
        if (position == cartItems.size()) {
            layoutParams.bottomMargin = 200;
            holder.card.setLayoutParams(layoutParams);
        }

    }

    @Override
    public int getItemCount() {
        return cartItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.picture)
        carbon.widget.ImageView picture;
        @BindView(R.id.name)
        carbon.widget.TextView name;
        @BindView(R.id.price)
        carbon.widget.TextView price;
        @BindView(R.id.quantity)
        carbon.widget.EditText quantity;
        @BindView(R.id.btnDelete)
        Button btnDelete;
        @BindView(R.id.decrease)
        carbon.widget.Button btnDecrease;
        public CardView card;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            Global.setupUI(view, (Activity)context, quantity);

            card = (CardView) view;
            card.setClickable(true);
            card.setBackgroundColor(Color.WHITE);
        }

        public void updateCart(int qty)
        {
            CartDataSource dataSource = new CartDataSource(context);

            try
            {
                dataSource.open();

                dataSource.updateQty(cartItems.get(getAdapterPosition()).getProductid(), qty);
            } catch (SQLException e) {
                Log.d("sqlError", e.getMessage());
            } finally
            {
                dataSource.close();
            }

            if(context instanceof CartActivity){
                ((CartActivity)context).updateTotalPrice();
            }
        }

        @OnFocusChange(R.id.quantity)
        public void check(View v, boolean hasFocus)
        {
            String tmp = quantity.getText().toString();
            if(!hasFocus) {
                if(tmp.equals("") || tmp.equals("0")) {
                    quantity.setText(String.valueOf(1));
                    Global.showSnackBar(v, context.getResources().getString(R.string.minimum_qty), Color.WHITE);
                }
                else
                {
                    int qty = Integer.valueOf(tmp);
                    updateCart(qty);
                }
            }
        }

        @OnTouch(R.id.quantity)
        public boolean onTouch()
        {
            Global.showCursor(quantity);
            return false;
        }

        @OnClick(R.id.btnDelete)
        public void delete(View v) {
            new AlertDialog.Builder(context)
                .setTitle(context.getResources().getString(R.string.deleteItemTitle))
                .setMessage(context.getResources().getString(R.string.deleteItemMessage))
                .setPositiveButton(context.getResources().getString(R.string.ya), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        /*List<CartItem> cartItems = Global.getCartItems(cart);
                        cart.remove(cartItems.get(getAdapterPosition()).getProduct());
                        cartItems.remove(getAdapterPosition());
                        updateCartItems(cartItems);
                        notifyDataSetChanged();*/

                        CartDataSource dataSource = new CartDataSource(context);

                        try{
                            dataSource.open();

                            dataSource.deleteCart(cartItems.get(getAdapterPosition()).getProductid());

                            cartItems.remove(getAdapterPosition());
                            notifyDataSetChanged();
                        } catch (SQLException e) {
                            Log.d("sqlError", e.getMessage());
                        }finally {
                            dataSource.close();
                        }


                        if(context instanceof CartActivity){
                            ((CartActivity)context).updateTotalPrice();
                        }
                    }
                })
                .setNegativeButton(context.getResources().getString(R.string.tidak), null)
                .show();
        }

        @OnClick(R.id.decrease)
        public void decrease()
        {
            int qty;
            String tmp = quantity.getText().toString();
            if(tmp.equals("")) {
                quantity.setText(String.valueOf(1));
                qty = 1;
            }
            else
            {
                qty = Integer.parseInt(tmp);
                if(qty <= 1) {
                    quantity.setText(String.valueOf(1));
                    qty = 1;
                }
                else {
                    qty--;
                    quantity.setText(String.valueOf(qty));
                }
            }

            updateCart(qty);
        }

        @OnClick(R.id.increase)
        public void increase()
        {
            int qty;
            String tmp = quantity.getText().toString();
            if(tmp.equals("")) {
                quantity.setText(String.valueOf(1));
                qty = 1;
            }
            else
            {
                qty = Integer.parseInt(tmp);
                qty++;
                quantity.setText(String.valueOf(qty));
            }

            updateCart(qty);
        }
    }
}
