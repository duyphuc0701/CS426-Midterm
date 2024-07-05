package com.example.travelapplication.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.travelapplication.R;
import com.example.travelapplication.databinding.FragmentHomeBinding;
import com.example.travelapplication.utils.FlightTicketUtils;

public class HomeFragment extends Fragment {
    FragmentHomeBinding binding;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View rootView = binding.getRoot();

        initServicesButton();

        return rootView;
    }

    private void initServicesButton() {
        binding.tripServiceImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Currently not available", Toast.LENGTH_SHORT).show();
            }
        });
        binding.hotelServiceImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Currently not available", Toast.LENGTH_SHORT).show();
            }
        });
        binding.transportServiceImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Set the boolean so that MainActivity know that it is in TransportFragment
                FlightTicketUtils.isSelectTransportBooking = true;
                // Start TransportBookingFragment
                TransportBookingFragment transportBookingFragment = new TransportBookingFragment();
                FragmentTransaction ft = requireActivity().getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.main_frame_layout, transportBookingFragment);
                ft.addToBackStack(null);
                ft.commit();
            }
        });
        binding.eventsServiceImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Currently not available", Toast.LENGTH_SHORT).show();
            }
        });
    }
}