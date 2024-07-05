package com.example.travelapplication.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.travelapplication.R;
import com.example.travelapplication.databinding.FragmentBookingBinding;
import com.example.travelapplication.utils.FlightTicketUtils;


public class BookingFragment extends Fragment {
    FragmentBookingBinding binding;

    public BookingFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentBookingBinding.inflate(inflater, container, false);
        View rootView = binding.getRoot();

        initBookingImageViews();

        return rootView;
    }

    private void initBookingImageViews() {
        binding.hotelBookingImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Currently not available", Toast.LENGTH_SHORT).show();
            }
        });
        binding.transportBookingImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FlightTicketUtils.isSelectTransportBooking = true;
                TransportBookingFragment transportBookingFragment = new TransportBookingFragment();
                FragmentTransaction ft = requireActivity().getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.main_frame_layout, transportBookingFragment);
                ft.addToBackStack(null);
                ft.commit();
            }
        });
        binding.tripsBookingImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Currently not available", Toast.LENGTH_SHORT).show();
            }
        });
        binding.eventsBookingImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Currently not available", Toast.LENGTH_SHORT).show();
            }
        });
    }
}