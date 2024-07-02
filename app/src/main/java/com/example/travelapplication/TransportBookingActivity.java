package com.example.travelapplication;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class TransportBookingActivity extends AppCompatActivity {

    ActivityTransportBookingBinding binding;
    // Declare a Calendar instance to store the selected departure date
    private Calendar departureCalendar = Calendar.getInstance();
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
        binding.ticketSearchButton.setOnClickListener(v -> {
            Intent intent = new Intent(this, FlightsDetailsActivity.class);
            startActivity(intent);
        });

        // Create a list of formatted strings
        List<String> formattedCities = new ArrayList<>();
        for (Map.Entry<String, String> entry : FlightTicketUtils.flightCities.entrySet()) {
            String formatted = entry.getValue() + " (" + entry.getKey() + ")";
            formattedCities.add(formatted);
        }
        ArrayAdapter<String> citiesAdapter = new ArrayAdapter<String>(TransportBookingActivity.this, R.layout.booking_city_spinner_item, formattedCities);
        citiesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.fromSpinner.setAdapter(citiesAdapter);
        binding.fromSpinner.setSelection(1);
        binding.toSpinner.setAdapter(citiesAdapter);
        binding.toSpinner.setSelection(0);

        binding.locationsSwitchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int fromPosition = binding.fromSpinner.getSelectedItemPosition();
                int toPosition = binding.toSpinner.getSelectedItemPosition();

                binding.fromSpinner.setSelection(toPosition);
                binding.toSpinner.setSelection(fromPosition);
            }
        });

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

                                // Create a SimpleDateFormat instance with the desired format
                                SimpleDateFormat dateFormat = new SimpleDateFormat("MMM dd, yyyy", Locale.ENGLISH);

                                // Format the date
                                String formattedDate = dateFormat.format(departureCalendar.getTime());

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

    private void replaceFragment(Fragment fragment, boolean addToBackStack) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.main_frame_layout, fragment);
        if(addToBackStack) {
            fragmentTransaction.addToBackStack(null);
        }
        fragmentTransaction.commit();
    }
}