package com.gocgod.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gocgod.R;
import com.gocgod.adapter.HowToBuyAdapter;
import com.gocgod.adapter.HowToBuySectionAdapter;

import java.util.List;

import carbon.widget.RecyclerView;
import carbon.widget.TextView;

/**
 * Created by Aurel on 24/08/2016.
 */
public class HowToBuyAdapter extends RecyclerView.Adapter<HowToBuyAdapter.ViewHolder, String> {
    private List<String> help;

    public HowToBuyAdapter(List<String> help) {
        this.help = help;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.row_drawer, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        super.onBindViewHolder(holder, position);
        holder.tv.setText(help.get(position));
    }

    @Override
    public String getItem(int position) {
        return help.get(position);
    }

    @Override
    public int getItemCount() {
        return help.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView tv;

        public ViewHolder(View itemView) {
            super(itemView);
            tv = (TextView) itemView.findViewById(R.id.text);
        }

    }
}
