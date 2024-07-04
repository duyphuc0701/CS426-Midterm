package com.example.travelapplication.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.travelapplication.R;
import com.example.travelapplication.utils.DateTab;
import com.example.travelapplication.utils.TravellerTab;

import java.util.List;

public class TravellerTabsAdapter extends RecyclerView.Adapter<TravellerTabsAdapter.ViewHolder> {
    private final List<TravellerTab> travellerTabs;
    private final TravellerTabsAdapter.OnItemClickListener listener;
    private int selectedIndex;

    public interface OnItemClickListener {
        void onItemClick(TravellerTab item);
    }

    public TravellerTabsAdapter(List<TravellerTab> travellerTabs, OnItemClickListener listener, int selectedIndex) {
        this.travellerTabs = travellerTabs;
        this.listener = listener;
        this.selectedIndex = selectedIndex;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView travellerNumberTV;
        public RelativeLayout travellerTabLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            travellerNumberTV = itemView.findViewById(R.id.traveller_tab_number);
            travellerTabLayout = itemView.findViewById(R.id.traveller_tab_layout);
        }

        public void bind(final TravellerTab travellerTab, final TravellerTabsAdapter.OnItemClickListener listener) {
            travellerNumberTV.setText(Integer.toString(travellerTab.getTravellerNumber()));
            travellerTabLayout.setBackgroundResource(travellerTab.isActive() ? R.drawable.active_traveller_tab : R.drawable.normal_traveller_tab);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(travellerTab);
                }
            });
        }
    }
    @NonNull
    @Override
    public TravellerTabsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.traveller_tab_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TravellerTabsAdapter.ViewHolder holder, int position) {
        holder.bind(travellerTabs.get(position), listener);
    }

    @Override
    public int getItemCount() {
        return travellerTabs.size();
    }

    public int getSelectedIndex() {
        return selectedIndex;
    }

    public void setActiveTravellerTab(int index) {
        travellerTabs.get(selectedIndex).setActive(false);
        travellerTabs.get(index).setActive(true);
        selectedIndex = index;
        notifyDataSetChanged();
    }
}
