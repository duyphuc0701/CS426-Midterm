package com.example.travelapplication.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class FlightsDetailsFragment extends Fragment {

    FragmentFlightsDetailsBinding binding;
    FlightTicketsAdapter flightTicketsAdapter;
    DateTabsAdapter dateTabsAdapter;
    List<DateTab> dateTabList;
    List<FlightTicketUtils.FlightTicket> flightsList;

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

        Bundle bundle = getArguments();
        String departureCityCode = bundle.getString(FlightTicketUtils.DEPARTURE_CITY_CODE);
        String arrivalCityCode = bundle.getString(FlightTicketUtils.ARRIVAL_CITY_CODE);
        long departureDateMillis = bundle.getLong(FlightTicketUtils.DEPARTURE_DATE);
        boolean ticketClass = bundle.getBoolean(FlightTicketUtils.TICKET_CLASS);
        int adultsNum = bundle.getInt(FlightTicketUtils.ADULTS_NUM);

        Calendar departureCalendar = Calendar.getInstance();
        departureCalendar.setTimeInMillis(departureDateMillis);

        initDateTabsList(departureDateMillis);

        initDateTabsRecyclerView(departureCityCode, arrivalCityCode, departureCalendar);

        initFlightsTicketsRecyclerView(departureCityCode, arrivalCityCode, departureCalendar, ticketClass, adultsNum);

        initBackButton();

        initFilterImageButton();

        return rootView;
    }

    private void initBackButton() {
        binding.flightsDetailsBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requireActivity().finish();
            }
        });
    }

    private void initDateTabsList(long departureDateMillis) {
        Calendar tempCalendar = Calendar.getInstance();
        tempCalendar.setTimeInMillis(departureDateMillis);
        dateTabList = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            String dayOfWeek = new SimpleDateFormat("EE", Locale.ENGLISH).format(tempCalendar.getTime()).toUpperCase(Locale.ENGLISH);
            dateTabList.add(new DateTab(dayOfWeek, tempCalendar.get(Calendar.DAY_OF_MONTH), i == 0)); // Set the first date as active
            tempCalendar.add(Calendar.DAY_OF_MONTH, 1);
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

    private void initDateTabsRecyclerView(String departureCityCode, String arrivalCityCode, Calendar departureCalendar) {
        // Initialize dateTabsAdapter
        dateTabsAdapter = new DateTabsAdapter(
                dateTabList,
                new DateTabsAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(DateTab item) {
                        dateTabsAdapter.setActiveDateTab(item);
                        departureCalendar.set(Calendar.DAY_OF_MONTH, item.getDayInMonthValue());
                        // Get the flights list corresponding to the date
                        flightTicketsAdapter.updateFlightsList(FlightTicketUtils.generateDummyFlights(departureCityCode, arrivalCityCode, departureCalendar));
                    }
                });
        binding.dateTabsRecyclerView.setLayoutManager(new LinearLayoutManager(
                getActivity(),
                LinearLayoutManager.HORIZONTAL, false));
        binding.dateTabsRecyclerView.setAdapter(dateTabsAdapter);
    }

    private void initFlightsTicketsRecyclerView(String departureCityCode, String arrivalCityCode,
                                                Calendar departureCalendar, boolean ticketClass, int adultsNum) {
        // TODO: Replace with data
        flightsList = FlightTicketUtils.generateDummyFlights(departureCityCode, arrivalCityCode, departureCalendar);
        flightTicketsAdapter =
                new FlightTicketsAdapter(
                        flightsList,
                        new FlightTicketsAdapter.OnItemClickListener() {
                            @Override
                            public void onItemClick(FlightTicketUtils.FlightTicket item) {
                                Intent intent = new Intent(getActivity(), SelectSeatActivity.class);
                                intent.putExtra(FlightTicketUtils.SELECTED_FLIGHT, item);
                                intent.putExtra(FlightTicketUtils.TICKET_CLASS, ticketClass);
                                intent.putExtra(FlightTicketUtils.ADULTS_NUM, adultsNum);
                                startActivity(intent);
                            }
                        });
        binding.flightsTicketsRecyclerView.setLayoutManager(new LinearLayoutManager(
                getActivity(),
                LinearLayoutManager.VERTICAL, false));
        binding.flightsTicketsRecyclerView.setAdapter(flightTicketsAdapter);
    }

}