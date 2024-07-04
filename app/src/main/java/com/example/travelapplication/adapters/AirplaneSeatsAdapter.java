package com.example.travelapplication.adapters;

import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.travelapplication.R;
import com.example.travelapplication.utils.AirplaneSeat;

import java.util.List;

public class AirplaneSeatsAdapter extends RecyclerView.Adapter<AirplaneSeatsAdapter.ViewHolder> {
    private final List<AirplaneSeat> seatList;

    private final AirplaneSeatsAdapter.OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(AirplaneSeat item);
    }
    public AirplaneSeatsAdapter(List<AirplaneSeat> seatList, OnItemClickListener listener) {
        this.seatList = seatList;
        this.listener = listener;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public Button seatButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            seatButton = itemView.findViewById(R.id.airplane_seat);
        }

        public void bind(final AirplaneSeat seat, final AirplaneSeatsAdapter.OnItemClickListener listener) {
            String seatText = String.valueOf(seat.getSeatRow()) + seat.getSeatColumn();
            seatButton.setText(seatText);
            seatButton.setBackgroundResource(seat.getBackgroundResource());
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(seat);
                }
            });
        }
    }
    @NonNull
    @Override
    public AirplaneSeatsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.airplane_seat_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AirplaneSeatsAdapter.ViewHolder holder, int position) {
        holder.bind(seatList.get(position), listener);
    }

    @Override
    public int getItemCount() {
        return seatList.size();
    }

    public void setSelectedSeat(AirplaneSeat chosenSeat) {
        int i;
        for(i = 0; i < seatList.size(); i++) {
            if(seatList.get(i) == chosenSeat) {
                seatList.get(i).setBackgroundResource(R.drawable.selected_seat);
                break;
            }
        }
        notifyDataSetChanged();
    }

    public void setSelectedSeat(int index) {
        seatList.get(index).setBackgroundResource(R.drawable.selected_seat);
        notifyItemChanged(index);
    }

    public AirplaneSeat getItem(int index) {
        return seatList.get(index);
    }

    public void setSeatToAvailable(int position) {
        seatList.get(position).setBackgroundResource(R.drawable.avail_seat);
        notifyItemChanged(position);
    }

    public int findAvailableSeat() {
        for(int i = 0; i < seatList.size(); i++) {
            if(seatList.get(i).getBackgroundResource() == R.drawable.avail_seat) {
                return i;
            }
        }
        return -1;
    }
}
