package com.example.travelapplication.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.travelapplication.R;
import com.example.travelapplication.SelectSeatActivity;
import com.example.travelapplication.adapters.DateTabsAdapter;
import com.example.travelapplication.adapters.FlightTicketsAdapter;
import com.example.travelapplication.databinding.FragmentFlightsDetailsBinding;
import com.example.travelapplication.utils.DateTab;
import com.example.travelapplication.utils.FlightTicketUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class FlightsDetailsFragment extends Fragment {

    FragmentFlightsDetailsBinding binding;
    FlightTicketsAdapter flightTicketsAdapter;
    DateTabsAdapter dateTabsAdapter;
    List<DateTab> dateTabList;
    List<FlightTicketUtils.FlightTicket> flightsList;
    Calendar departureCalendar = Calendar.getInstance();

    public FlightsDetailsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentFlightsDetailsBinding.inflate(inflater, container, false);
        View rootView = binding.getRoot();

        String departureCity = getArguments().getString(FlightTicketUtils.DEPARTURE_CITY);
        String arrivalCity = getArguments().getString(FlightTicketUtils.ARRIVAL_CITY);
        String departureDate = getArguments().getString(FlightTicketUtils.DEPARTURE_DATE);

        String fromLocationShort = FlightTicketUtils.extractAirportCode(departureCity);
        String toLocationShort = FlightTicketUtils.extractAirportCode(arrivalCity);

        initDepartureCalendar(departureDate);

        initDateTabsRecyclerView(fromLocationShort, toLocationShort);

        initFlightsTicketsRecyclerView(fromLocationShort, toLocationShort, departureCalendar);

        binding.flightsDetailsBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requireActivity().finish();
            }
        });

        initFilterImageButton();

        return rootView;
    }

    private void initDepartureCalendar(String departureDate) {
        dateTabList = new ArrayList<>();
        Date departureDateObject;
        try {
            departureDateObject = FlightTicketUtils.dateFormat.parse(departureDate);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(departureDateObject);

            for (int i = 0; i < 7; i++) {
                String dayOfWeek = new SimpleDateFormat("EE", Locale.ENGLISH).format(calendar.getTime()).toUpperCase(Locale.ENGLISH);
                dateTabList.add(new DateTab(dayOfWeek, calendar.get(Calendar.DAY_OF_MONTH), i == 0)); // Set the first date as active
                calendar.add(Calendar.DAY_OF_MONTH, 1);
            }
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    private void initFilterImageButton() {
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
    }

    private void initDateTabsRecyclerView(String fromLocationShort, String toLocationShort) {
        // Initialize dateTabsAdapter
        dateTabsAdapter = new DateTabsAdapter(
                dateTabList,
                new DateTabsAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(DateTab item) {
                        dateTabsAdapter.setActiveDateTab(item);
                        departureCalendar.set(Calendar.DAY_OF_MONTH, item.getDayInMonthValue());
                        flightTicketsAdapter.updateFlightsList(FlightTicketUtils.generateDummyFlights(fromLocationShort, toLocationShort, departureCalendar));
                    }
                });
        binding.dateTabsRecyclerView.setLayoutManager(new LinearLayoutManager(
                getActivity(),
                LinearLayoutManager.HORIZONTAL, false));
        binding.dateTabsRecyclerView.setAdapter(dateTabsAdapter);
    }

    private void initFlightsTicketsRecyclerView(String fromLocationShort, String toLocationShort, Calendar departureCalendar) {
        flightsList = FlightTicketUtils.generateDummyFlights(fromLocationShort, toLocationShort, departureCalendar);
        flightTicketsAdapter =
                new FlightTicketsAdapter(
                        flightsList,
                        new FlightTicketsAdapter.OnItemClickListener() {
                            @Override
                            public void onItemClick(FlightTicketUtils.FlightTicket item) {
                                Intent intent = new Intent(getActivity(), SelectSeatActivity.class);
                                startActivity(intent);
                            }
                        });
        binding.flightsTicketsRecyclerView.setLayoutManager(new LinearLayoutManager(
                getActivity(),
                LinearLayoutManager.VERTICAL, false));
        binding.flightsTicketsRecyclerView.setAdapter(flightTicketsAdapter);
    }

}