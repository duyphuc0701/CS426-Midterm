package com.example.travelapplication;

import android.content.Intent;
import android.os.Bundle;

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

public class TransportBookingActivity extends AppCompatActivity {

    ActivityTransportBookingBinding binding;
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