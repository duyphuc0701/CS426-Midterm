package com.example.travelapplication.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.travelapplication.R;
import com.example.travelapplication.SelectSeatActivity;
import com.example.travelapplication.adapters.FlightTicketsAdapter;
import com.example.travelapplication.databinding.FragmentFlightsDetailsBinding;
import com.example.travelapplication.utils.FlightTicketUtils;

public class FlightsDetailsFragment extends Fragment {

    FragmentFlightsDetailsBinding binding;

    public FlightsDetailsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentFlightsDetailsBinding.inflate(inflater, container, false);
        View rootView = binding.getRoot();
        binding.flightsTicketsRecyclerView.setLayoutManager(new LinearLayoutManager(
                getActivity(),
                LinearLayoutManager.VERTICAL, false));
        binding.flightsTicketsRecyclerView.setAdapter(
                new FlightTicketsAdapter(FlightTicketUtils.NYC_LDN_TICKETS, new FlightTicketsAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(FlightTicketUtils.FlightTicket item) {
                        Intent intent = new Intent(getActivity(), SelectSeatActivity.class);
                        startActivity(intent);
                    }
                })
        );

        binding.flightsDetailsBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentActivity activity = requireActivity();
                activity.getSupportFragmentManager().popBackStack();
                activity.finish();
            }
        });

        binding.filterImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FlightsFilterFragment flightsFilter = new FlightsFilterFragment();
                FragmentTransaction ft = requireActivity().getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.flightsDetailsFrameLayout, flightsFilter);
                ft.addToBackStack(null);
                ft.commit();
            }
        });

        return rootView;
    }
}