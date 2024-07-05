package com.example.travelapplication.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
    Button departureOptionCurrentButton;
    Button arrivalOptionCurrentButton;

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

        initDepartureField();
        initArrivalField();

        initPriceRangeSlider();
        initPriceEditText();

        initResetButton();
        initDoneButton();

        return rootView;
    }

    private void initArrivalField() {
        arrivalOptionCurrentButton = binding.filterDepartureButtonAll;
        binding.filterArrivalButtonAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(arrivalOptionCurrentButton != binding.filterArrivalButtonAll) {
                    // Change selected button
                    binding.filterArrivalButtonAll.setBackgroundResource(R.drawable.active_filter_time_button);
                    binding.filterArrivalButtonAll.setTextColor(ContextCompat.getColor(requireActivity(), R.color.white));
                    // Change old button
                    arrivalOptionCurrentButton.setBackgroundResource(R.drawable.normal_filter_time_button);
                    arrivalOptionCurrentButton.setTextColor(ContextCompat.getColor(requireActivity(), R.color.green_500));
                    // Update current button
                    arrivalOptionCurrentButton = binding.filterArrivalButtonAll;
                }
            }
        });
        binding.filterArrivalButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(arrivalOptionCurrentButton != binding.filterArrivalButton1) {
                    // Change selected button
                    binding.filterArrivalButton1.setBackgroundResource(R.drawable.active_filter_time_button);
                    binding.filterArrivalButton1.setTextColor(ContextCompat.getColor(requireActivity(), R.color.white));
                    // Change old button
                    arrivalOptionCurrentButton.setBackgroundResource(R.drawable.normal_filter_time_button);
                    arrivalOptionCurrentButton.setTextColor(ContextCompat.getColor(requireActivity(), R.color.green_500));
                    // Update current button
                    arrivalOptionCurrentButton = binding.filterArrivalButton1;
                }
            }
        });
        binding.filterArrivalButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(arrivalOptionCurrentButton != binding.filterArrivalButton2) {
                    // Change selected button
                    binding.filterArrivalButton2.setBackgroundResource(R.drawable.active_filter_time_button);
                    binding.filterArrivalButton2.setTextColor(ContextCompat.getColor(requireActivity(), R.color.white));
                    // Change old button
                    arrivalOptionCurrentButton.setBackgroundResource(R.drawable.normal_filter_time_button);
                    arrivalOptionCurrentButton.setTextColor(ContextCompat.getColor(requireActivity(), R.color.green_500));
                    // Update current button
                    arrivalOptionCurrentButton = binding.filterArrivalButton2;
                }
            }
        });
        binding.filterArrivalButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(arrivalOptionCurrentButton != binding.filterArrivalButton3) {
                    // Change selected button
                    binding.filterArrivalButton3.setBackgroundResource(R.drawable.active_filter_time_button);
                    binding.filterArrivalButton3.setTextColor(ContextCompat.getColor(requireActivity(), R.color.white));
                    // Change old button
                    arrivalOptionCurrentButton.setBackgroundResource(R.drawable.normal_filter_time_button);
                    arrivalOptionCurrentButton.setTextColor(ContextCompat.getColor(requireActivity(), R.color.green_500));
                    // Update current button
                    arrivalOptionCurrentButton = binding.filterArrivalButton3;
                }
            }
        });
        binding.filterArrivalButton4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(arrivalOptionCurrentButton != binding.filterArrivalButton4) {
                    // Change selected button
                    binding.filterArrivalButton4.setBackgroundResource(R.drawable.active_filter_time_button);
                    binding.filterArrivalButton4.setTextColor(ContextCompat.getColor(requireActivity(), R.color.white));
                    // Change old button
                    arrivalOptionCurrentButton.setBackgroundResource(R.drawable.normal_filter_time_button);
                    arrivalOptionCurrentButton.setTextColor(ContextCompat.getColor(requireActivity(), R.color.green_500));
                    // Update current button
                    arrivalOptionCurrentButton = binding.filterArrivalButton4;
                }
            }
        });
    }

    private void initDepartureField() {
        departureOptionCurrentButton = binding.filterDepartureButtonAll;
        binding.filterDepartureButtonAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(departureOptionCurrentButton != binding.filterDepartureButtonAll) {
                    // Change selected button
                    binding.filterDepartureButtonAll.setBackgroundResource(R.drawable.active_filter_time_button);
                    binding.filterDepartureButtonAll.setTextColor(ContextCompat.getColor(requireActivity(), R.color.white));
                    // Change old button
                    departureOptionCurrentButton.setBackgroundResource(R.drawable.normal_filter_time_button);
                    departureOptionCurrentButton.setTextColor(ContextCompat.getColor(requireActivity(), R.color.green_500));
                    // Update current button
                    departureOptionCurrentButton = binding.filterDepartureButtonAll;
                }
            }
        });
        binding.filterDepartureButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(departureOptionCurrentButton != binding.filterDepartureButton1) {
                    // Change selected button
                    binding.filterDepartureButton1.setBackgroundResource(R.drawable.active_filter_time_button);
                    binding.filterDepartureButton1.setTextColor(ContextCompat.getColor(requireActivity(), R.color.white));
                    // Change old button
                    departureOptionCurrentButton.setBackgroundResource(R.drawable.normal_filter_time_button);
                    departureOptionCurrentButton.setTextColor(ContextCompat.getColor(requireActivity(), R.color.green_500));
                    // Update current button
                    departureOptionCurrentButton = binding.filterDepartureButton1;
                }
            }
        });
        binding.filterDepartureButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(departureOptionCurrentButton != binding.filterDepartureButton2) {
                    // Change selected button
                    binding.filterDepartureButton2.setBackgroundResource(R.drawable.active_filter_time_button);
                    binding.filterDepartureButton2.setTextColor(ContextCompat.getColor(requireActivity(), R.color.white));
                    // Change old button
                    departureOptionCurrentButton.setBackgroundResource(R.drawable.normal_filter_time_button);
                    departureOptionCurrentButton.setTextColor(ContextCompat.getColor(requireActivity(), R.color.green_500));
                    // Update current button
                    departureOptionCurrentButton = binding.filterDepartureButton2;
                }
            }
        });
        binding.filterDepartureButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(departureOptionCurrentButton != binding.filterDepartureButton3) {
                    // Change selected button
                    binding.filterDepartureButton3.setBackgroundResource(R.drawable.active_filter_time_button);
                    binding.filterDepartureButton3.setTextColor(ContextCompat.getColor(requireActivity(), R.color.white));
                    // Change old button
                    departureOptionCurrentButton.setBackgroundResource(R.drawable.normal_filter_time_button);
                    departureOptionCurrentButton.setTextColor(ContextCompat.getColor(requireActivity(), R.color.green_500));
                    // Update current button
                    departureOptionCurrentButton = binding.filterDepartureButton3;
                }
            }
        });
        binding.filterDepartureButton4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(departureOptionCurrentButton != binding.filterDepartureButton4) {
                    // Change selected button
                    binding.filterDepartureButton4.setBackgroundResource(R.drawable.active_filter_time_button);
                    binding.filterDepartureButton4.setTextColor(ContextCompat.getColor(requireActivity(), R.color.white));
                    // Change old button
                    departureOptionCurrentButton.setBackgroundResource(R.drawable.normal_filter_time_button);
                    departureOptionCurrentButton.setTextColor(ContextCompat.getColor(requireActivity(), R.color.green_500));
                    // Update current button
                    departureOptionCurrentButton = binding.filterDepartureButton4;
                }
            }
        });
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
                binding.filterDepartureButtonAll.setBackgroundResource(R.drawable.active_filter_time_button);
                binding.filterDepartureButtonAll.setTextColor(ContextCompat.getColor(requireActivity(), R.color.white));
                departureOptionCurrentButton.setBackgroundResource(R.drawable.active_filter_time_button);
                departureOptionCurrentButton.setTextColor(ContextCompat.getColor(requireActivity(), R.color.white));
                departureOptionCurrentButton = binding.filterDepartureButtonAll;
                // Reset arrival buttons
                binding.filterArrivalButtonAll.setBackgroundResource(R.drawable.active_filter_time_button);
                binding.filterArrivalButtonAll.setTextColor(ContextCompat.getColor(requireActivity(), R.color.white));
                arrivalOptionCurrentButton.setBackgroundResource(R.drawable.active_filter_time_button);
                arrivalOptionCurrentButton.setTextColor(ContextCompat.getColor(requireActivity(), R.color.white));
                arrivalOptionCurrentButton = binding.filterArrivalButtonAll;
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