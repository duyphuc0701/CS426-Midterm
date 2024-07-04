package com.example.travelapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.travelapplication.adapters.FlightTicketsAdapter;
import com.example.travelapplication.databinding.ActivityFlightsDetailsBinding;
import com.example.travelapplication.fragments.FlightsDetailsFragment;
import com.example.travelapplication.utils.FlightTicketUtils;

public class FlightsDetailsActivity extends AppCompatActivity {

    ActivityFlightsDetailsBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFlightsDetailsBinding.inflate(getLayoutInflater());
        EdgeToEdge.enable(this);
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Intent intent = getIntent();
        String departureCity = intent.getStringExtra(FlightTicketUtils.DEPARTURE_CITY_CODE);
        String arrivalCity = intent.getStringExtra(FlightTicketUtils.ARRIVAL_CITY_CODE);
        long departureDateMillis = intent.getLongExtra(FlightTicketUtils.DEPARTURE_DATE, -1);
        boolean ticketClass = intent.getBooleanExtra(FlightTicketUtils.TICKET_CLASS, false);
        int adultsNum = intent.getIntExtra(FlightTicketUtils.ADULTS_NUM, -1);
        Bundle bundle = new Bundle();
        bundle.putString(FlightTicketUtils.DEPARTURE_CITY_CODE, departureCity);
        bundle.putString(FlightTicketUtils.ARRIVAL_CITY_CODE, arrivalCity);
        bundle.putLong(FlightTicketUtils.DEPARTURE_DATE, departureDateMillis);
        bundle.putBoolean(FlightTicketUtils.TICKET_CLASS, ticketClass);
        bundle.putInt(FlightTicketUtils.ADULTS_NUM, adultsNum);
        FlightsDetailsFragment frag = new FlightsDetailsFragment();
        frag.setArguments(bundle);
        replaceFragment(frag, true);
    }

    private void replaceFragment(Fragment fragment, boolean addToBackStack) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.flightsDetailsFrameLayout, fragment);
        if(addToBackStack) {
            fragmentTransaction.addToBackStack(null);
        }
        fragmentTransaction.commit();
    }
}