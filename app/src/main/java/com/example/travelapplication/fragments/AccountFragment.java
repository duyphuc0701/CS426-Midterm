package com.example.travelapplication.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.travelapplication.R;
import com.example.travelapplication.databinding.FragmentAccountBinding;
import com.example.travelapplication.utils.UserPreferences;
import com.google.android.material.snackbar.Snackbar;

public class AccountFragment extends Fragment {
    FragmentAccountBinding binding;
    public AccountFragment() {
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
        binding = FragmentAccountBinding.inflate(inflater, container, false);
        View rootView = binding.getRoot();

        loadUserData();

        initPersonalInfoSection();

        initPaymentAndCardsSection();

        initSavedSection();

        initBookingHistorySection();

        initSettingsSection();

        return rootView;
    }

    private void initSettingsSection() {
        binding.settingsField.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "This function will be developed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initBookingHistorySection() {
        binding.bookingHistoryField.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "This function will be developed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initSavedSection() {
        binding.savedField.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "This function will be developed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initPaymentAndCardsSection() {
        binding.paymentField.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "This function will be developed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initPersonalInfoSection() {
        binding.personalInfoField.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PersonalInfoFragment personalInfoFragment = new PersonalInfoFragment();
                FragmentTransaction ft = requireActivity().getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.main_frame_layout, personalInfoFragment);
                ft.addToBackStack(null);
                ft.commit();
            }
        });
    }

    private void loadUserData() {
        SharedPreferences preferences = requireActivity().getSharedPreferences(UserPreferences.PREFS_NAME, Context.MODE_PRIVATE);

        String firstName = preferences.getString(UserPreferences.KEY_FIRST_NAME, getResources().getString(R.string.victoria));
        String lastName = preferences.getString(UserPreferences.KEY_LAST_NAME, getResources().getString(R.string.yoker));
        binding.accountName.setText(String.format("%s %s", firstName, lastName));
    }
}