package com.example.travelapplication.fragments;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.Toast;

import com.example.travelapplication.FlightsDetailsActivity;
import com.example.travelapplication.R;
import com.example.travelapplication.databinding.FragmentTransportBookingBinding;
import com.example.travelapplication.utils.FlightTicketUtils;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class TransportBookingFragment extends Fragment {
    FragmentTransportBookingBinding binding;
    private Calendar departureCalendar = Calendar.getInstance();
    private Calendar returnCalendar = Calendar.getInstance();
    private boolean isEconomyClass = false;
    public TransportBookingFragment() {
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
        binding = FragmentTransportBookingBinding.inflate(inflater, container, false);
        View rootView = binding.getRoot();

        FragmentActivity activity = requireActivity();

        binding.transportBookingBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FlightTicketUtils.isSelectTransportBooking = false;
                activity.getSupportFragmentManager().popBackStack();
            }
        });

        BottomNavigationView bottomNavigationView = (BottomNavigationView) activity.findViewById(R.id.bottomNavigationView);

        bottomNavigationView.setSelectedItemId(R.id.booking);

        initTicketSearchButton();

        initFromToSpinners();
        initLocationsSwitchButton();

        initDepartureDateField();
        initReturnDateField();

        initClassButtons();

        initTransportButtons();

        return rootView;
    }
    private static String extractAirportCode(String city) {
        if (city == null || city.isEmpty()) {
            return "";
        }
        int start = city.indexOf('(');
        int end = city.indexOf(')');
        if (start != -1 && end != -1 && start < end) {
            return city.substring(start + 1, end);
        }
        return "";
    }
    private void initTicketSearchButton() {
        binding.ticketSearchButton.setOnClickListener(v -> {
            if(TextUtils.isEmpty(binding.passengerNumEdit.getText())) {
                Toast.makeText(getActivity(), "Passenger & Luggage is not completed", Toast.LENGTH_SHORT).show();
            }
            else if(TextUtils.isEmpty(binding.babyNumEdit.getText())) {
                Toast.makeText(getActivity(), "Passenger & Luggage is not completed", Toast.LENGTH_SHORT).show();
            }
            else if(TextUtils.isEmpty(binding.petNumEdit.getText())) {
                Toast.makeText(getActivity(), "Passenger & Luggage is not completed", Toast.LENGTH_SHORT).show();
            }
            else if(TextUtils.isEmpty(binding.luggageNumEdit.getText())) {
                Toast.makeText(getActivity(), "Passenger & Luggage is not completed", Toast.LENGTH_SHORT).show();
            }
            else {
                // Get selected city from user
                String departureCity = binding.fromSpinner.getSelectedItem().toString();
                String arrivalCity = binding.toSpinner.getSelectedItem().toString();
                // Extract city code only
                departureCity = extractAirportCode(departureCity);
                arrivalCity = extractAirportCode(arrivalCity);
                // Store number of adults
                FlightTicketUtils.adultsNum = Integer.parseInt(binding.passengerNumEdit.getText().toString());
                // Store ticket class boolean (isEconomy)
                FlightTicketUtils.isEconomy = isEconomyClass;
                // Move to FlightsDetailsActivity
                Intent intent = new Intent(getActivity(), FlightsDetailsActivity.class);
                intent.putExtra(FlightTicketUtils.DEPARTURE_CITY_CODE, departureCity);
                intent.putExtra(FlightTicketUtils.ARRIVAL_CITY_CODE, arrivalCity);
                intent.putExtra(FlightTicketUtils.DEPARTURE_DATE, departureCalendar.getTimeInMillis());
                startActivity(intent);
            }
        });
    }

    private void initFromToSpinners() {
        // Create a list of formatted strings
        List<String> formattedCities = new ArrayList<>();
        for (Map.Entry<String, String> entry : FlightTicketUtils.citiesLookup.entrySet()) {
            String formatted = entry.getValue() + " (" + entry.getKey() + ")";
            formattedCities.add(formatted);
        }
        ArrayAdapter<String> citiesAdapter = new ArrayAdapter<String>(requireActivity(), R.layout.booking_city_spinner_item, formattedCities);
        citiesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.fromSpinner.setAdapter(citiesAdapter);
        binding.fromSpinner.setSelection(1);
        binding.toSpinner.setAdapter(citiesAdapter);
        binding.toSpinner.setSelection(0);
    }

    private void initLocationsSwitchButton() {
        binding.locationsSwitchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int fromPosition = binding.fromSpinner.getSelectedItemPosition();
                int toPosition = binding.toSpinner.getSelectedItemPosition();

                binding.fromSpinner.setSelection(toPosition);
                binding.toSpinner.setSelection(fromPosition);
            }
        });
    }

    private void initDepartureDateField() {
        // Format the date
        String defaultDate = FlightTicketUtils.dateFormat.format(departureCalendar.getTime());

        // Set the formatted date to the TextView
        binding.departureDate.setText(defaultDate);
        binding.departureDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // the instance of our calendar.
                final Calendar c = Calendar.getInstance();

                // our day, month and year.
                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);

                // on below line we are creating a variable for date picker dialog.
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        // on below line we are passing context.
                        requireActivity(),
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                departureCalendar.clear();
                                departureCalendar.set(year, month, dayOfMonth); // month is zero-based in Calendar

                                // Format the date
                                String formattedDate = FlightTicketUtils.dateFormat.format(departureCalendar.getTime());

                                // Set the formatted date to the TextView
                                binding.departureDate.setText(formattedDate);
                            }
                        },
                        // on below line we are passing year,
                        // month and day for selected date in our date picker.
                        year, month, day);
                // at last we are calling show to
                // display our date picker dialog.
                datePickerDialog.show();
            }
        });
    }

    private void initReturnDateField() {
        // Format the date
        String defaultDate = FlightTicketUtils.dateFormat.format(departureCalendar.getTime());

        // Set the formatted date to the TextView
        binding.returnDate.setText(defaultDate);
        binding.returnDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // the instance of our calendar.
                final Calendar c = Calendar.getInstance();

                // our day, month and year.
                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        requireActivity(),
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                // Create a Calendar instance and set the date
                                returnCalendar.clear();
                                returnCalendar.set(year, month, dayOfMonth); // month is zero-based in Calendar

                                // Check if the return date is after the departure date
                                if (returnCalendar.after(departureCalendar)) {
                                    // Create a SimpleDateFormat instance with the desired format
                                    SimpleDateFormat dateFormat = new SimpleDateFormat("MMM dd, yyyy", Locale.ENGLISH);

                                    // Format the date
                                    String formattedDate = dateFormat.format(returnCalendar.getTime());

                                    // Set the formatted date to the TextView
                                    binding.returnDate.setText(formattedDate);
                                } else {
                                    // Show a message that the return date must be after the departure date
                                    Toast.makeText(getActivity(), "Return date must be after the departure date", Toast.LENGTH_SHORT).show();
                                }
                            }
                        },
                        year, month, day);
                // display our date picker dialog.
                datePickerDialog.show();
            }
        });
    }

    private void initClassButtons() {
        binding.economyClassButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!isEconomyClass) {
                    binding.economyClassButton.setBackgroundResource(R.drawable.active_class_button);
                    binding.economyClassButton.setTextColor(ContextCompat.getColor(requireActivity(), R.color.white));

                    binding.businessClassButton.setBackgroundResource(R.drawable.normal_class_button);
                    binding.businessClassButton.setTextColor(ContextCompat.getColor(requireActivity(), R.color.green_500));
                    isEconomyClass = true;
                }
            }
        });

        binding.businessClassButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isEconomyClass) {
                    binding.businessClassButton.setBackgroundResource(R.drawable.active_class_button);
                    binding.businessClassButton.setTextColor(ContextCompat.getColor(requireActivity(), R.color.white));

                    binding.economyClassButton.setBackgroundResource(R.drawable.normal_class_button);
                    binding.economyClassButton.setTextColor(ContextCompat.getColor(requireActivity(), R.color.green_500));
                    isEconomyClass = false;
                }
            }
        });
    }

    private void initTransportButtons() {
        binding.boatOptionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Currently not available", Toast.LENGTH_SHORT).show();
            }
        });

        binding.trainOptionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Currently not available", Toast.LENGTH_SHORT).show();
            }
        });

        binding.busOptionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Currently not available", Toast.LENGTH_SHORT).show();
            }
        });
    }
}