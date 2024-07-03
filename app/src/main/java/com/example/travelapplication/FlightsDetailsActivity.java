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
        Bundle bundle = new Bundle();
        bundle.putString(FlightTicketUtils.DEPARTURE_CITY, intent.getStringExtra(FlightTicketUtils.DEPARTURE_CITY));
        bundle.putString(FlightTicketUtils.ARRIVAL_CITY, intent.getStringExtra(FlightTicketUtils.ARRIVAL_CITY));
        bundle.putString(FlightTicketUtils.DEPARTURE_DATE, intent.getStringExtra(FlightTicketUtils.DEPARTURE_DATE));
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