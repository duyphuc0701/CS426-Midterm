package com.example.travelapplication.fragments;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
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
import com.example.travelapplication.TravelDatabaseHelper;
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
        // Get list of matching tickets
        flightsList = (List<FlightTicketUtils.FlightTicket>)
                bundle.getSerializable(FlightTicketUtils.MATCHING_FLIGHTS);
        // Get other passenger info
        boolean ticketClass = bundle.getBoolean(FlightTicketUtils.TICKET_CLASS);
        int adultsNum = bundle.getInt(FlightTicketUtils.ADULTS_NUM);

        // Get necessary info
        String departureCityCode = null;
        String arrivalCityCode = null;
        Calendar departureCalendar = Calendar.getInstance();
        int flightsListSize = 0;
        if (flightsList != null) {
            departureCityCode = flightsList.get(0).fromLocationShort;
            arrivalCityCode = flightsList.get(0).toLocationShort;
            departureCalendar.setTime(flightsList.get(0).departureDate);
            flightsListSize = flightsList.size();
        }
        // Set available flights text
        updateAvailableFlightsText(departureCityCode, arrivalCityCode, flightsListSize);

        // Init date tabs recyclerView
        initDateTabsRecyclerView(departureCityCode, arrivalCityCode, departureCalendar);

        // Init flights tickets recyclerView
        initFlightsTicketsRecyclerView(departureCityCode, arrivalCityCode,
                departureCalendar, ticketClass, adultsNum);

        initBackButton();

        initFilterImageButton();

        return rootView;
    }

    private void updateAvailableFlightsText(String departureCityCode, String arrivalCityCode, int flightsListSize) {
        String departureCityName = FlightTicketUtils.citiesLookup.get(departureCityCode);
        String arrivalCityName = FlightTicketUtils.citiesLookup.get(arrivalCityCode);
        String availableText =
                Integer.toString(flightsListSize) +
                        " flights available " +
                departureCityName +
                " to " +
                arrivalCityName;
        binding.flightsAvailableText.setText(availableText);
    }

    private void initBackButton() {
        binding.flightsDetailsBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requireActivity().finish();
            }
        });
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

    private void initDateTabsRecyclerView(String departureCityCode,
                                          String arrivalCityCode,
                                          Calendar departureCalendar) {
        // Init dateTabList
        dateTabList = new ArrayList<>();
        Calendar c = Calendar.getInstance();
        c.setTime(departureCalendar.getTime());
        for (int i = 0; i < 7; i++) {
            String dayOfWeek = new SimpleDateFormat("EE", Locale.ENGLISH).format(c.getTime()).toUpperCase(Locale.ENGLISH);
            dateTabList.add(new DateTab(dayOfWeek, c.get(Calendar.DAY_OF_MONTH), i == 0)); // Set the first date as active
            c.add(Calendar.DAY_OF_MONTH, 1);
        }
        // Initialize dateTabsAdapter
        dateTabsAdapter = new DateTabsAdapter(
                dateTabList,
                new DateTabsAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(DateTab item) {
                        dateTabsAdapter.setActiveDateTab(item);
                        departureCalendar.set(Calendar.DAY_OF_MONTH, item.getDayInMonthValue());
                        // Get the flights list corresponding to the date
                        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
                        String departureDateString = sdf.format(departureCalendar.getTime());
                        List<FlightTicketUtils.FlightTicket> newFlightsList = searchForFlightsInDatabase(departureCityCode,
                                arrivalCityCode, departureDateString, departureCalendar);
                        // Update available flights text
                        flightTicketsAdapter.updateFlightsList(newFlightsList);
                        updateAvailableFlightsText(departureCityCode, arrivalCityCode, newFlightsList.size());
                    }
                });
        binding.dateTabsRecyclerView.setLayoutManager(new LinearLayoutManager(
                getActivity(),
                LinearLayoutManager.HORIZONTAL, false));
        binding.dateTabsRecyclerView.setAdapter(dateTabsAdapter);
    }

    private void initFlightsTicketsRecyclerView(String departureCityCode, String arrivalCityCode,
                                                Calendar departureCalendar, boolean ticketClass, int adultsNum) {
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

    private ArrayList<FlightTicketUtils.FlightTicket> searchForFlightsInDatabase(String departureCity,
                                                                                 String arrivalCity,
                                                                                 String departureDate,
                                                                                 Calendar departureCalendar) {
        TravelDatabaseHelper travelDatabaseHelper = new TravelDatabaseHelper(getActivity());
        SQLiteDatabase db = travelDatabaseHelper.getReadableDatabase();
        Cursor cursor = db.query(TravelDatabaseHelper.TABLE_FLIGHTS,
                new String[]{"departureTime", "price", "number"},
                "departureCity = ? AND arrivalCity = ? AND departureDate = ?",
                new String[]{departureCity, arrivalCity, departureDate},
                null, null, "price ASC");
        ArrayList<FlightTicketUtils.FlightTicket> result = new ArrayList<>();
        if(cursor != null) {
            while(cursor.moveToNext()) {
                int departureTimeMinutes = cursor.getInt(0);
                String departureTime = TravelDatabaseHelper.convertMinutesToTimeString(departureTimeMinutes);
                int price = cursor.getInt(1);
                String flightNumber = cursor.getString(2);
                FlightTicketUtils.FlightTicket ticket =
                        new FlightTicketUtils.FlightTicket(
                                departureCity,
                                FlightTicketUtils.citiesLookup.get(departureCity),
                                arrivalCity,
                                FlightTicketUtils.citiesLookup.get(arrivalCity),
                                departureCalendar.getTime(),
                                departureTime,
                                price,
                                flightNumber);
                result.add(ticket);
            }
        }
        db.close();
        cursor.close();
        return result;
    }
}