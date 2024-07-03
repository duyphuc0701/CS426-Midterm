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
import com.example.travelapplication.utils.FlightTicketUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class DateTabsAdapter extends RecyclerView.Adapter<DateTabsAdapter.ViewHolder> {
    private final List<DateTab> dateTabs;
    private final DateTabsAdapter.OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(DateTab item);
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView dateInWeekTV;
        public TextView dateInMonthTV;
        public RelativeLayout dateTabLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            dateInWeekTV = itemView.findViewById(R.id.date_in_week_tab);
            dateInMonthTV = itemView.findViewById(R.id.date_in_month_tab);
            dateTabLayout = itemView.findViewById(R.id.date_tab_layout);
        }

        public void bind(final DateTab dateTab, final DateTabsAdapter.OnItemClickListener listener) {
            dateInWeekTV.setText(dateTab.getDayInWeek());
            dateInMonthTV.setText(dateTab.getDayInMonth());
            dateTabLayout.setBackgroundResource(dateTab.isActive() ? R.drawable.active_date_tab : R.drawable.normal_date_tab);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(dateTab);
                }
            });
        }
    }
    public DateTabsAdapter(List<DateTab> dateTabs, DateTabsAdapter.OnItemClickListener listener) {
        this.dateTabs = dateTabs;
        this.listener = listener;
    }
    @NonNull
    @Override
    public DateTabsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.flight_date_tab_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DateTabsAdapter.ViewHolder holder, int position) {
        holder.bind(dateTabs.get(position), listener);
    }

    @Override
    public int getItemCount() {
        return dateTabs.size();
    }

    public void setActiveDateTab(DateTab activeDateTab) {
        for (DateTab dateTab : dateTabs) {
            dateTab.setActive(dateTab == activeDateTab);
        }
        notifyDataSetChanged();
    }
}
