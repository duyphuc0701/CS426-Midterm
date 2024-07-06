package com.example.travelapplication;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.travelapplication.databinding.ActivityMainBinding;
import com.example.travelapplication.fragments.AccountFragment;
import com.example.travelapplication.fragments.BookingFragment;
import com.example.travelapplication.fragments.HomeFragment;
import com.example.travelapplication.fragments.TransportBookingFragment;
import com.example.travelapplication.utils.FlightTicketUtils;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        EdgeToEdge.enable(this);
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        replaceFragment(new HomeFragment(), true, "HomeFragment");
        binding.bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.home) {
                FlightTicketUtils.isSelectTransportBooking = false;
                replaceFragment(new HomeFragment(), true, "HomeFragment");
            }
            else if (itemId == R.id.booking) {
                if(!FlightTicketUtils.isSelectTransportBooking)
                    replaceFragment(new BookingFragment(), true, "BookingFragment");
            }
            else if (itemId == R.id.notify) {
                FlightTicketUtils.isSelectTransportBooking = false;
            }
            else if (itemId == R.id.account) {
                FlightTicketUtils.isSelectTransportBooking = false;
                replaceFragment(new AccountFragment(), true, "AccountFragment");
            }
            return true;
        });
    }

    private void replaceFragment(Fragment fragment, boolean addToBackStack, String tag) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.main_frame_layout, fragment, tag);
        if(addToBackStack) {
            fragmentTransaction.addToBackStack(null);
        }
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_MATCH_ACTIVITY_OPEN);
        fragmentTransaction.commit();
    }
}