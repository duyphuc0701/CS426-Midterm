package com.example.travelapplication.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.travelapplication.R;
import com.example.travelapplication.databinding.FragmentFlightsFilterBinding;
import com.google.android.material.slider.RangeSlider;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class FlightsFilterFragment extends Fragment {

    FragmentFlightsFilterBinding binding;
    Float[] defaultPriceValues;
    String defaultPriceFromValue = "50";
    String defaultPriceToValue = "250";

    public FlightsFilterFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        defaultPriceValues = new Float[2];
        defaultPriceValues[0] = 50.0f;
        defaultPriceValues[1] = 250.0f;
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentFlightsFilterBinding.inflate(inflater, container, false);
        View rootView = binding.getRoot();

        initBackButton();

        initPriceRangeSlider();
        initPriceEditText();

        initResetButton();
        initDoneButton();

        return rootView;
    }

    private void initPriceEditText() {
        binding.filterPriceFromValue.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                updatePriceRangeSlider();
            }
        });
        binding.filterPriceToValue.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (binding.filterPriceToValue.length() == 0) {
                    binding.filterPriceToValue.setText("$");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                updatePriceRangeSlider();
            }
        });
    }

    private void updatePriceRangeSlider() {
        try {
            float minValue = Float.parseFloat(binding.filterPriceFromValue.getText().toString());
            float maxValue = Float.parseFloat(binding.filterPriceToValue.getText().toString());

            if (minValue <= maxValue && minValue >= binding.filterPriceSlider.getValueFrom() && maxValue <= binding.filterPriceSlider.getValueTo()) {
                binding.filterPriceSlider.setValues(minValue, maxValue);
            }
        } catch (NumberFormatException e) {
            Toast.makeText(getActivity(), "Exception update range slider", Toast.LENGTH_SHORT).show();
        }
    }

    private void initPriceRangeSlider() {
        binding.filterPriceSlider.addOnChangeListener(new RangeSlider.OnChangeListener() {
            @Override
            public void onValueChange(@NonNull RangeSlider rangeSlider, float v, boolean b) {
                List<Float> sliderValues = binding.filterPriceSlider.getValues();
                // Update the EditTexts with the formatted values
                binding.filterPriceFromValue.setText(String.format(Locale.ENGLISH,"%.2f", sliderValues.get(0)));
                binding.filterPriceToValue.setText(String.format(Locale.ENGLISH,"%.2f", sliderValues.get(1)));
            }
        });
    }

    private void initResetButton() {
        binding.filterResetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Reset departure buttons
                binding.filterDepartureButton1.setChecked(false);
                binding.filterDepartureButton2.setChecked(true);
                binding.filterDepartureButton3.setChecked(false);
                binding.filterDepartureButton4.setChecked(false);
                // Reset arrival buttons
                binding.filterArrivalButton1.setChecked(true);
                binding.filterArrivalButton2.setChecked(false);
                binding.filterArrivalButton3.setChecked(false);
                binding.filterArrivalButton4.setChecked(false);
                // Reset price RangeSlider and EditText
                binding.filterPriceSlider.setValues(defaultPriceValues);
                binding.filterPriceFromValue.setText(defaultPriceFromValue);
                binding.filterPriceToValue.setText(defaultPriceToValue);
                // Reset Sort RadioGroup
                binding.filterSortRadioGroup.check(R.id.filter_price_radioButton);
            }
        });
    }

    private void initBackButton() {
        binding.filterBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requireActivity().getSupportFragmentManager().popBackStack();
            }
        });
    }

    private void initDoneButton() {
        binding.filterDoneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: Change this code to make it filter
                requireActivity().getSupportFragmentManager().popBackStack();
            }
        });
    }
}