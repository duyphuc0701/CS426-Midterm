package com.example.travelapplication.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.travelapplication.R;
import com.example.travelapplication.databinding.FragmentPersonalInfoBinding;
import com.example.travelapplication.utils.UserPreferences;

import java.util.Objects;

public class PersonalInfoFragment extends Fragment {
    FragmentPersonalInfoBinding binding;
    public PersonalInfoFragment() {
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
        binding = FragmentPersonalInfoBinding.inflate(inflater, container, false);
        View rootView = binding.getRoot();

        loadUserData();

        initBackButton();

        initSaveChangesButton();

        return rootView;
    }

    private void initSaveChangesButton() {
        binding.saveChangesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentActivity activity = requireActivity();
                String firstName = binding.firstNameEdit.getText().toString();
                String lastName = binding.lastNameEdit.getText().toString();
                String phone = binding.phoneEdit.getText().toString();
                String email = binding.emailEdit.getText().toString();
                UserPreferences.saveUserData(activity, firstName, lastName, phone, email);
                activity.getSupportFragmentManager().popBackStack();
            }
        });
    }

    private void loadUserData() {
        SharedPreferences preferences = requireActivity().getSharedPreferences(UserPreferences.PREFS_NAME, Context.MODE_PRIVATE);

        binding.firstNameEdit.setText(preferences.getString(UserPreferences.KEY_FIRST_NAME, getResources().getString(R.string.victoria)));
        binding.lastNameEdit.setText(preferences.getString(UserPreferences.KEY_LAST_NAME, getResources().getString(R.string.yoker)));
        binding.phoneEdit.setText(preferences.getString(UserPreferences.KEY_PHONE, getResources().getString(R.string.samplePhoneNumber)));
        binding.emailEdit.setText(preferences.getString(UserPreferences.KEY_EMAIL, getResources().getString(R.string.sampleEmailAddress)));
    }

    private void initBackButton() {
        binding.personalInfoBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requireActivity().getSupportFragmentManager().popBackStack();
            }
        });
    }
}