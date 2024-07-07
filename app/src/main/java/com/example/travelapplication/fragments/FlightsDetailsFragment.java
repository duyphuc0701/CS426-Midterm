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
import androidx.sqlite.SQLiteException;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

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
        // Get info about the user input
        String departureCityCode = bundle.getString(FlightTicketUtils.DEPARTURE_CITY_CODE);
        String arrivalCityCode = bundle.getString(FlightTicketUtils.ARRIVAL_CITY_CODE);
        long departureDateMillis = bundle.getLong(FlightTicketUtils.DEPARTURE_DATE);
        Calendar departureCalendar = Calendar.getInstance();
        departureCalendar.setTimeInMillis(departureDateMillis);

        // Check whether the flights list is empty or not
        int flightsListSize = 0;
        if (flightsList != null) {
            flightsListSize = flightsList.size();
        }
        // Set available flights text
        updateAvailableFlightsText(departureCityCode, arrivalCityCode, flightsListSize);

        // Init date tabs recyclerView
        initDateTabsRecyclerView(departureCityCode, arrivalCityCode, departureCalendar);

        // Init flights tickets recyclerView
        initFlightsTicketsRecyclerView();

        // Init back button
        initBackButton();

        // Init filter image button
        initFilterImageButton(departureCityCode, arrivalCityCode, departureDateMillis);

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

    private void initFilterImageButton(String departureCityCode, String arrivalCityCode, long departureDateMillis) {
        binding.filterImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                // Pass user input information
                bundle.putString(FlightTicketUtils.DEPARTURE_CITY_CODE, departureCityCode);
                bundle.putString(FlightTicketUtils.ARRIVAL_CITY_CODE, arrivalCityCode);
                bundle.putLong(FlightTicketUtils.DEPARTURE_DATE, departureDateMillis);

                // Set arguments for new fragment
                FlightsFilterFragment flightsFilter = new FlightsFilterFragment();
                flightsFilter.setArguments(bundle);

                // Replace with new fragment
                FragmentTransaction ft = requireActivity().getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.flightsDetailsFrameLayout, flightsFilter);
                ft.addToBackStack(null);
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
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
                        // IMPORTANT: Get new flights list from database
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

    private void initFlightsTicketsRecyclerView() {
        flightTicketsAdapter =
                new FlightTicketsAdapter(
                        flightsList,
                        new FlightTicketsAdapter.OnItemClickListener() {
                            @Override
                            public void onItemClick(FlightTicketUtils.FlightTicket item) {
                                Intent intent = new Intent(getActivity(), SelectSeatActivity.class);
                                intent.putExtra(FlightTicketUtils.SELECTED_FLIGHT, item);
                                startActivity(intent);
                            }
                        });
        binding.flightsTicketsRecyclerView.setLayoutManager(new LinearLayoutManager(
                getActivity(),
                LinearLayoutManager.VERTICAL, false));
        binding.flightsTicketsRecyclerView.setAdapter(flightTicketsAdapter);
    }

    private ArrayList<FlightTicketUtils.FlightTicket> searchForFlightsInDatabase(String departureCityCode,
                                                                                 String arrivalCityCode,
                                                                                 String departureDate,
                                                                                 Calendar departureCalendar) {
        ArrayList<FlightTicketUtils.FlightTicket> result = new ArrayList<>();
        TravelDatabaseHelper travelDatabaseHelper = new TravelDatabaseHelper(requireActivity());
        SQLiteDatabase db;
        Cursor cursor;
        try {
            db = travelDatabaseHelper.getReadableDatabase();
            cursor = db.query(TravelDatabaseHelper.TABLE_FLIGHTS,
                    new String[]{"departureTime", "price", "number", "brand"},
                    "departureCity = ? AND arrivalCity = ? AND departureDate = ?",
                    new String[]{departureCityCode, arrivalCityCode, departureDate},
                    null, null, "price ASC");
            for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
                int departureTimeMinutes = cursor.getInt(0);
                String departureTime = TravelDatabaseHelper.convertMinutesToTimeString(departureTimeMinutes);
                int price = cursor.getInt(1);
                String flightNumber = cursor.getString(2);
                String brand = cursor.getString(3);
                FlightTicketUtils.FlightTicket ticket =
                        new FlightTicketUtils.FlightTicket(
                                departureCityCode,
                                FlightTicketUtils.citiesLookup.get(departureCityCode),
                                arrivalCityCode,
                                FlightTicketUtils.citiesLookup.get(arrivalCityCode),
                                departureCalendar.getTime(),
                                departureTime,
                                price,
                                flightNumber,
                                brand);
                Log.i("departureTime", departureTime);
                Log.i("price", Integer.toString(price));
                Log.i("flightNumber", flightNumber);
                result.add(ticket);
            }
            cursor.close();
            db.close();
        } catch(SQLiteException e) {
            Toast toast = Toast.makeText(getActivity(), "Database unavailable", Toast.LENGTH_SHORT);
            toast.show();
        }
        return result;
    }
}