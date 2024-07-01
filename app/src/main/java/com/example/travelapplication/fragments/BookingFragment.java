package com.example.travelapplication.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
        binding.transportBookingImageView.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), TransportBookingActivity.class);
            startActivity(intent);
        });

        return rootView;
    }
}