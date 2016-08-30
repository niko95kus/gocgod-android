package com.gocgod.adapter;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gocgod.R;
import com.gocgod.model.AgentLocation;
import com.gocgod.model.ProductTestimonial;

import org.w3c.dom.Text;

import java.text.DecimalFormat;
import java.util.ArrayList;

import carbon.drawable.ripple.RippleDrawable;
import carbon.drawable.ripple.RippleDrawableFroyo;
import carbon.widget.CardView;
import carbon.widget.TextView;

/**
 * Created by Aurel on 29/08/2016.
 */
public class AgentLocationAdapter extends RecyclerView.Adapter<AgentLocationAdapter.ViewHolder> {
    private Context context;
    private ArrayList<AgentLocation> agentLocation;
//    private boolean previewTestimonial;

    public AgentLocationAdapter(Context context, ArrayList<AgentLocation> agentLocation)
    {
        this.context = context;
        this.agentLocation = agentLocation;
//        this.previewTestimonial = previewTestimonial;
    }

    @Override
    public AgentLocationAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_agent_location, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AgentLocationAdapter.ViewHolder holder, int i) {
        if(!agentLocation.isEmpty()) {
            DecimalFormat formatter = new DecimalFormat("#.##");

            AgentLocation location = agentLocation.get(i);
//            String rate = "Peringkat: " + ) +"/5";

            if (location.getRate() == null) {
                holder.rating.setText("Belum ada rating");
            } else {
                holder.rating.setText("Rating: " + Double.parseDouble(location.getRate()) + "/5.0");
            }

            holder.name.setText(location.getName());
            holder.address.setText(location.getAddress());
            holder.city.setText(location.getCityName());
            holder.phone.setText(location.getPhone());
            holder.email.setText(location.getEmail());
        }
    }

    @Override
    public int getItemCount() {
        return agentLocation.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView name;
        TextView rating;
        TextView address;
        TextView city;
        TextView phone;
        TextView email;
        public CardView card;

        public ViewHolder(View view) {
            super(view);

            name = (TextView) view.findViewById(R.id.name);
            rating = (TextView) view.findViewById(R.id.rating);
            address = (TextView) view.findViewById(R.id.address);
            city = (TextView) view.findViewById(R.id.city);
            phone = (TextView) view.findViewById(R.id.phone);
            email = (TextView) view.findViewById(R.id.email);

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
