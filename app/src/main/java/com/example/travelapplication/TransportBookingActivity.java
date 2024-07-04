package com.example.travelapplication;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.travelapplication.databinding.ActivityTransportBookingBinding;
import com.example.travelapplication.fragments.AccountFragment;
import com.example.travelapplication.fragments.HomeFragment;
import com.example.travelapplication.utils.FlightTicketUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class TransportBookingActivity extends AppCompatActivity {

    ActivityTransportBookingBinding binding;
    // Declare a Calendar instance to store the selected departure date
    private final Calendar departureCalendar = Calendar.getInstance();
    private boolean isEconomyClass = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTransportBookingBinding.inflate(getLayoutInflater());
        EdgeToEdge.enable(this);
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        binding.transportBookingBottomNavigation.setSelectedItemId(R.id.booking);
        binding.transportBookingBottomNavigation.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.home) {
                replaceFragment(new HomeFragment(), false);
            }
            else if (itemId == R.id.account) {
                replaceFragment(new AccountFragment(), false);
            }
            return true;
        });

        binding.transportBookingBackButton.setOnClickListener(v -> {
            finish();
        });
        initTicketSearchButton();

        initFromToSpinners();
        initLocationsSwitchButton();

        initDepartureDateField();
        initReturnDateField();

        initClassButtons();

        initTransportButtons();
    }

    private void initTicketSearchButton() {
        binding.ticketSearchButton.setOnClickListener(v -> {
            if(TextUtils.isEmpty(binding.passengerNumEdit.getText())) {
                Toast.makeText(this, "Passenger & Luggage is not completed", Toast.LENGTH_SHORT).show();
            }
            else if(TextUtils.isEmpty(binding.babyNumEdit.getText())) {
                Toast.makeText(this, "Passenger & Luggage is not completed", Toast.LENGTH_SHORT).show();
            }
            else if(TextUtils.isEmpty(binding.petNumEdit.getText())) {
                Toast.makeText(this, "Passenger & Luggage is not completed", Toast.LENGTH_SHORT).show();
            }
            else if(TextUtils.isEmpty(binding.luggageNumEdit.getText())) {
                Toast.makeText(this, "Passenger & Luggage is not completed", Toast.LENGTH_SHORT).show();
            }
            else {
                String departureCity = binding.fromSpinner.getSelectedItem().toString();
                String arrivalCity = binding.toSpinner.getSelectedItem().toString();
                departureCity = extractAirportCode(departureCity);
                arrivalCity = extractAirportCode(arrivalCity);
                Calendar departureDateCalendar = Calendar.getInstance();
                String departureDateString = binding.departureDate.getText().toString();
                int adultsNum = Integer.parseInt(binding.passengerNumEdit.getText().toString());
                try {
                    departureDateCalendar.setTime(FlightTicketUtils.dateFormat.parse(departureDateString));
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
                Intent intent = new Intent(this, FlightsDetailsActivity.class);
                intent.putExtra(FlightTicketUtils.DEPARTURE_CITY_CODE, departureCity);
                intent.putExtra(FlightTicketUtils.ARRIVAL_CITY_CODE, arrivalCity);
                intent.putExtra(FlightTicketUtils.DEPARTURE_DATE, departureDateCalendar.getTimeInMillis());
                intent.putExtra(FlightTicketUtils.TICKET_CLASS, isEconomyClass);
                intent.putExtra(FlightTicketUtils.ADULTS_NUM, adultsNum);
                startActivity(intent);
            }
        });
    }

    private void initTransportButtons() {
        binding.boatOptionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(TransportBookingActivity.this, "Currently not available", Toast.LENGTH_SHORT).show();
            }
        });

        binding.trainOptionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(TransportBookingActivity.this, "Currently not available", Toast.LENGTH_SHORT).show();
            }
        });

        binding.busOptionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(TransportBookingActivity.this, "Currently not available", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initClassButtons() {
        binding.economyClassButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!isEconomyClass) {
                    binding.economyClassButton.setBackgroundResource(R.drawable.active_class_button);
                    binding.economyClassButton.setTextColor(ContextCompat.getColor(TransportBookingActivity.this, R.color.white));

                    binding.businessClassButton.setBackgroundResource(R.drawable.normal_class_button);
                    binding.businessClassButton.setTextColor(ContextCompat.getColor(TransportBookingActivity.this, R.color.green_500));
                    isEconomyClass = true;
                }
            }
        });

        binding.businessClassButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isEconomyClass) {
                    binding.businessClassButton.setBackgroundResource(R.drawable.active_class_button);
                    binding.businessClassButton.setTextColor(ContextCompat.getColor(TransportBookingActivity.this, R.color.white));

                    binding.economyClassButton.setBackgroundResource(R.drawable.normal_class_button);
                    binding.economyClassButton.setTextColor(ContextCompat.getColor(TransportBookingActivity.this, R.color.green_500));
                    isEconomyClass = false;
                }
            }
        });
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

    private void initFromToSpinners() {
        // Create a list of formatted strings
        List<String> formattedCities = new ArrayList<>();
        for (Map.Entry<String, String> entry : FlightTicketUtils.citiesLookup.entrySet()) {
            String formatted = entry.getValue() + " (" + entry.getKey() + ")";
            formattedCities.add(formatted);
        }
        ArrayAdapter<String> citiesAdapter = new ArrayAdapter<String>(TransportBookingActivity.this, R.layout.booking_city_spinner_item, formattedCities);
        citiesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.fromSpinner.setAdapter(citiesAdapter);
        binding.fromSpinner.setSelection(1);
        binding.toSpinner.setAdapter(citiesAdapter);
        binding.toSpinner.setSelection(0);
    }

    private void initReturnDateField() {
        // Format the date
        String defaultDate = FlightTicketUtils.dateFormat.format(departureCalendar.getTime());

        // Set the formatted date to the TextView
        binding.returnDate.setText(defaultDate);
        binding.returnDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (departureCalendar == null) {
                    // If departure date is not set, show a message to the user
                    Toast.makeText(TransportBookingActivity.this, "Please select the departure date first", Toast.LENGTH_SHORT).show();
                    return;
                }
                // the instance of our calendar.
                final Calendar c = Calendar.getInstance();

                // our day, month and year.
                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        TransportBookingActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                // Create a Calendar instance and set the date
                                Calendar returnCalendar = Calendar.getInstance();
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
                                    Toast.makeText(TransportBookingActivity.this, "Return date must be after the departure date", Toast.LENGTH_SHORT).show();
                                }
                            }
                        },
                        year, month, day);
                // display our date picker dialog.
                datePickerDialog.show();
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
                        TransportBookingActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
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

    private void replaceFragment(Fragment fragment, boolean addToBackStack) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.main_frame_layout, fragment);
        if(addToBackStack) {
            fragmentTransaction.addToBackStack(null);
        }
        fragmentTransaction.commit();
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
}