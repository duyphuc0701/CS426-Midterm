package com.example.travelapplication.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.travelapplication.TransportBookingActivity;
import com.example.travelapplication.databinding.FragmentBookingBinding;


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
        binding.transportBookingImageView.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), TransportBookingActivity.class);
            startActivity(intent);
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