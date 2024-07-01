package com.example.travelapplication.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.travelapplication.R;
import com.example.travelapplication.utils.FlightTicketUtils;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class FlightTicketsAdapter extends
        RecyclerView.Adapter<FlightTicketsAdapter.ViewHolder>{
    private final List<FlightTicketUtils.FlightTicket> flightTickets;
    private final OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(FlightTicketUtils.FlightTicket item);
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView fromLocationShortTV, fromLocationFullTV, toLocationShortTV, toLocationFullTV,
                departureDateTV, departureTimeTV, priceTV, flightNumberTV;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            fromLocationShortTV = itemView.findViewById(R.id.from_location_shorthand);
            fromLocationFullTV = itemView.findViewById(R.id.from_location_full);
            toLocationShortTV = itemView.findViewById(R.id.to_location_shorthand);
            toLocationFullTV = itemView.findViewById(R.id.to_location_full);
            departureDateTV = itemView.findViewById(R.id.ticket_date_value);
            departureTimeTV = itemView.findViewById(R.id.ticket_time_value);
            priceTV = itemView.findViewById(R.id.ticket_price_value);
            flightNumberTV = itemView.findViewById(R.id.ticket_flightNumber_value);
        }

        public void bind(final FlightTicketUtils.FlightTicket ticket, final OnItemClickListener listener) {
            fromLocationShortTV.setText(ticket.fromLocationShort);
            fromLocationFullTV.setText(ticket.fromLocationFull);
            toLocationShortTV.setText(ticket.toLocationShort);
            toLocationFullTV.setText(ticket.toLocationFull);
            departureDateTV.setText(new SimpleDateFormat("MMM dd", Locale.ENGLISH).format(ticket.departureDate));
            departureTimeTV.setText(ticket.departureTime);
            priceTV.setText(String.format("$%d", ticket.price));
            flightNumberTV.setText(ticket.flightNumber);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    listener.onItemClick(ticket);
                }
            });
        }
    }

    public FlightTicketsAdapter(List<FlightTicketUtils.FlightTicket> flightTickets, OnItemClickListener listener) {
        this.flightTickets = flightTickets;
        this.listener = listener;
    }

    @NonNull
    @Override
    public FlightTicketsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.flight_ticket_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FlightTicketsAdapter.ViewHolder holder, int position) {
        holder.bind(flightTickets.get(position), listener);
    }

    @Override
    public int getItemCount() {
        return flightTickets.size();
    }
}
