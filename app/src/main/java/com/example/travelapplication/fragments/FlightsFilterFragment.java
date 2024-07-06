package com.example.travelapplication.fragments;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.sqlite.SQLiteException;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.travelapplication.R;
import com.example.travelapplication.TravelDatabaseHelper;
import com.example.travelapplication.databinding.FragmentFlightsFilterBinding;
import com.example.travelapplication.utils.FlightTicketUtils;
import com.google.android.material.slider.RangeSlider;
import com.google.android.material.snackbar.Snackbar;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;


public class FlightsFilterFragment extends Fragment {

    FragmentFlightsFilterBinding binding;
    Float[] defaultPriceValues;
    String defaultPriceFromString = "0.00";
    String defaultPriceToString = "400.00";
    Button departureOptionCurrentButton;
    Button arrivalOptionCurrentButton;
    String sortCriterion = null;
    String departureCityCode = null;
    String arrivalCityCode = null;
    Calendar departureCalendar = Calendar.getInstance();
    String departureDateString = null;

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

        // Get info about user input
        Bundle bundle = getArguments();
        departureCityCode = bundle.getString(FlightTicketUtils.DEPARTURE_CITY_CODE);
        arrivalCityCode = bundle.getString(FlightTicketUtils.ARRIVAL_CITY_CODE);
        long departureDateMillis = bundle.getLong(FlightTicketUtils.DEPARTURE_DATE);

        // Set departure calendar object
        departureCalendar.setTimeInMillis(departureDateMillis);

        // Set departure string object (format like database)
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
        departureDateString = sdf.format(departureCalendar.getTime());

        // Init back button listener
        initBackButton();

        // Init departure field and arrival field listener
        initDepartureField();
        initArrivalField();

        // Init price field (slider and 2 editTexts)
        initPriceField();

        // Init sort field
        initSortField();

        // Init reset and done button
        initResetButton();
        initDoneButton();

        return rootView;
    }
    private void initBackButton() {
        binding.filterBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requireActivity().getSupportFragmentManager().popBackStack();
            }
        });
    }
    private void initDepartureField() {
        // Set default value
        setDepartureOptionButtonBasedOnIndex();
        // Set on click listener
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

    private void setDepartureOptionButtonBasedOnIndex() {
        if(FlightTicketUtils.departureOptionIndex == 0) {
            departureOptionCurrentButton = binding.filterDepartureButtonAll;
        }
        else if(FlightTicketUtils.departureOptionIndex == 1) {
            departureOptionCurrentButton = binding.filterDepartureButton1;
        }
        else if(FlightTicketUtils.departureOptionIndex == 2) {
            departureOptionCurrentButton = binding.filterDepartureButton2;
        }
        else if(FlightTicketUtils.departureOptionIndex == 3) {
            departureOptionCurrentButton = binding.filterDepartureButton3;
        }
        else if(FlightTicketUtils.departureOptionIndex == 4) {
            departureOptionCurrentButton = binding.filterDepartureButton4;
        }
        // Set button appearance
        departureOptionCurrentButton.setBackgroundResource(R.drawable.active_filter_time_button);
        departureOptionCurrentButton.setTextColor(ContextCompat.getColor(requireActivity(), R.color.white));
    }

    private void initArrivalField() {
        // Set default value
        setArrivalOptionButtonBasedOnIndex();
        // Set on click listeners
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

    private void setArrivalOptionButtonBasedOnIndex() {
        if(FlightTicketUtils.arrivalOptionIndex == 0) {
            arrivalOptionCurrentButton = binding.filterArrivalButtonAll;
        }
        else if(FlightTicketUtils.arrivalOptionIndex == 1) {
            arrivalOptionCurrentButton = binding.filterArrivalButton1;
        }
        else if(FlightTicketUtils.arrivalOptionIndex == 2) {
            arrivalOptionCurrentButton = binding.filterArrivalButton2;
        }
        else if(FlightTicketUtils.arrivalOptionIndex == 3) {
            arrivalOptionCurrentButton = binding.filterArrivalButton3;
        }
        else if(FlightTicketUtils.arrivalOptionIndex == 4) {
            arrivalOptionCurrentButton = binding.filterArrivalButton4;
        }
        arrivalOptionCurrentButton.setBackgroundResource(R.drawable.active_filter_time_button);
        arrivalOptionCurrentButton.setTextColor(ContextCompat.getColor(requireActivity(), R.color.white));
    }
    private void initPriceField() {
        initPriceEditText();
        initPriceRangeSlider();
    }

    private void setPriceRadioGroupBasedOnSortCriterion() {
        if(FlightTicketUtils.sortCriterion.equals(FlightTicketUtils.ARRIVAL_TIME)) {
            binding.filterSortRadioGroup.check(R.id.filter_arrivalTime_radioButton);
        }
        else if(FlightTicketUtils.sortCriterion.equals(FlightTicketUtils.PRICE)) {
            binding.filterSortRadioGroup.check(R.id.filter_price_radioButton);
        }
        else if(FlightTicketUtils.sortCriterion.equals(FlightTicketUtils.DEPARTURE_TIME)) {
            binding.filterSortRadioGroup.check(R.id.filter_departureTime_radioButton);
        }
        else if(FlightTicketUtils.sortCriterion.equals(FlightTicketUtils.DURATION)) {
            binding.filterSortRadioGroup.check(R.id.filter_duration_radioButton);
        }
    }

    private void initPriceEditText() {
        // Set default value
        binding.filterPriceFromValue.setText(String.format(Locale.ENGLISH,"%.2f", FlightTicketUtils.priceFromFloat));
        // Set text changed listener
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
        // Set default value
        binding.filterPriceToValue.setText(String.format(Locale.ENGLISH,"%.2f", FlightTicketUtils.priceToFloat));
        // Set text changed listener
        binding.filterPriceToValue.addTextChangedListener(new TextWatcher() {
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
        // Set default value
        binding.filterPriceSlider.setValues(FlightTicketUtils.priceFromFloat, FlightTicketUtils.priceToFloat);
        // Set on change listener
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

    private void initSortField() {
        // Set the default value
        sortCriterion = FlightTicketUtils.sortCriterion;
        setPriceRadioGroupBasedOnSortCriterion();
        // Set on check listener
        binding.filterSortRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId == R.id.filter_arrivalTime_radioButton) {
                    sortCriterion = FlightTicketUtils.ARRIVAL_TIME;
                }
                else if(checkedId == R.id.filter_departureTime_radioButton) {
                    sortCriterion = FlightTicketUtils.DEPARTURE_TIME;
                }
                else if(checkedId == R.id.filter_price_radioButton) {
                    sortCriterion = FlightTicketUtils.PRICE;
                }
                else if(checkedId == R.id.filter_duration_radioButton) {
                    sortCriterion = FlightTicketUtils.DURATION;
                }
            }
        });
    }

    private void initResetButton() {
        binding.filterResetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Reset departure buttons
                if(departureOptionCurrentButton != binding.filterDepartureButtonAll) {
                    binding.filterDepartureButtonAll.setBackgroundResource(R.drawable.active_filter_time_button);
                    binding.filterDepartureButtonAll.setTextColor(ContextCompat.getColor(requireActivity(), R.color.white));

                    departureOptionCurrentButton.setBackgroundResource(R.drawable.normal_filter_time_button);
                    departureOptionCurrentButton.setTextColor(ContextCompat.getColor(requireActivity(), R.color.green_500));
                    departureOptionCurrentButton = binding.filterDepartureButtonAll;
                }
                // Reset arrival buttons
                if(arrivalOptionCurrentButton != binding.filterArrivalButtonAll) {
                    binding.filterArrivalButtonAll.setBackgroundResource(R.drawable.active_filter_time_button);
                    binding.filterArrivalButtonAll.setTextColor(ContextCompat.getColor(requireActivity(), R.color.white));
                    arrivalOptionCurrentButton.setBackgroundResource(R.drawable.normal_filter_time_button);
                    arrivalOptionCurrentButton.setTextColor(ContextCompat.getColor(requireActivity(), R.color.green_500));
                    arrivalOptionCurrentButton = binding.filterArrivalButtonAll;
                }
                // Reset price RangeSlider and EditText
                binding.filterPriceSlider.setValues(defaultPriceValues);
                binding.filterPriceFromValue.setText(defaultPriceFromString);
                binding.filterPriceToValue.setText(defaultPriceToString);
                // Reset Sort RadioGroup
                binding.filterSortRadioGroup.check(R.id.filter_price_radioButton);
                // Display message for user
                Toast.makeText(getActivity(), "Reset filter successfully!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initDoneButton() {
        binding.filterDoneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Set up arguments to pass to new fragment
                int minDepartureMinutes = getMinDepartureMinutes();
                int maxDepartureMinutes = getMaxDepartureMinutes();
                int minArrivalMinutes = getMinArrivalMinutes();
                int maxArrivalMinutes = getMaxArrivalMinutes();
                List<Float> priceValues = binding.filterPriceSlider.getValues();
                int fromPriceValue = priceValues.get(0).intValue();
                int toPriceValue = priceValues.get(1).intValue();
                // MOST IMPORTANT: Get filtered flights list from database
                List<FlightTicketUtils.FlightTicket> filteredFlightsList =
                        getFilteredFlightsList(minDepartureMinutes, maxDepartureMinutes,
                                minArrivalMinutes, maxArrivalMinutes,
                                fromPriceValue, toPriceValue, sortCriterion);
                // Pass user input back to FlightsDetailsFragment
                Bundle bundle = new Bundle();
                bundle.putString(FlightTicketUtils.DEPARTURE_CITY_CODE, departureCityCode);
                bundle.putString(FlightTicketUtils.ARRIVAL_CITY_CODE, arrivalCityCode);
                bundle.putLong(FlightTicketUtils.DEPARTURE_DATE, departureCalendar.getTimeInMillis());
                // Pass the new flights list to the fragment
                bundle.putSerializable(FlightTicketUtils.MATCHING_FLIGHTS, (Serializable) filteredFlightsList);
                // Pass filter conditions to the fragment as well
                bundle.putInt(FlightTicketUtils.DEPARTURE_FILTER, getDepartureOptionIndex());
                bundle.putInt(FlightTicketUtils.ARRIVAL_FILTER, getArrivalOptionIndex());
                bundle.putFloat(FlightTicketUtils.MIN_PRICE_FILTER, priceValues.get(0).floatValue());
                bundle.putFloat(FlightTicketUtils.MAX_PRICE_FILTER, priceValues.get(1).floatValue());
                bundle.putString(FlightTicketUtils.SORT_CRITERION_FILTER, sortCriterion);
                // Save the variables based on current filter
                FlightTicketUtils.departureOptionIndex = getDepartureOptionIndex();
                FlightTicketUtils.arrivalOptionIndex = getArrivalOptionIndex();
                FlightTicketUtils.priceFromFloat = priceValues.get(0);
                FlightTicketUtils.priceToFloat = priceValues.get(1);
                FlightTicketUtils.sortCriterion = sortCriterion;
                // Display message for user
                Toast.makeText(getActivity(), "Filter successfully!", Toast.LENGTH_SHORT).show();
                // Replace with new FlightsDetailsFragment and set arguments
                FlightsDetailsFragment frag = new FlightsDetailsFragment();
                frag.setArguments(bundle);
                replaceFragment(frag, true);
            }
        });
    }

    private int getArrivalOptionIndex() {
        int result = -1;
        if(arrivalOptionCurrentButton == binding.filterArrivalButtonAll) {
            result = 0;
        }
        else if(arrivalOptionCurrentButton == binding.filterArrivalButton1) {
            result = 1;
        }
        else if(arrivalOptionCurrentButton == binding.filterArrivalButton2) {
            result = 2;
        }
        else if(arrivalOptionCurrentButton == binding.filterArrivalButton3) {
            result = 3;
        }
        else if(arrivalOptionCurrentButton == binding.filterArrivalButton4) {
            result = 4;
        }
        return result;
    }

    private int getDepartureOptionIndex() {
        int result = -1;
        if(departureOptionCurrentButton == binding.filterDepartureButtonAll) {
            result = 0;
        }
        else if(departureOptionCurrentButton == binding.filterDepartureButton1) {
            result = 1;
        }
        else if(departureOptionCurrentButton == binding.filterDepartureButton2) {
            result = 2;
        }
        else if(departureOptionCurrentButton == binding.filterDepartureButton3) {
            result = 3;
        }
        else if(departureOptionCurrentButton == binding.filterDepartureButton4) {
            result = 4;
        }
        return result;
    }

    private int getMaxArrivalMinutes() {
        int result = -1;
        if(arrivalOptionCurrentButton == binding.filterArrivalButtonAll) {
            result = 1439;
        }
        else if(arrivalOptionCurrentButton == binding.filterArrivalButton1) {
            result = 360;
        }
        else if(arrivalOptionCurrentButton == binding.filterArrivalButton2) {
            result = 720;
        }
        else if(arrivalOptionCurrentButton == binding.filterArrivalButton3) {
            result = 1080;
        }
        else if(arrivalOptionCurrentButton == binding.filterArrivalButton4) {
            result = 1439;
        }
        return result;
    }

    private int getMinArrivalMinutes() {
        int result = -1;
        if(arrivalOptionCurrentButton == binding.filterArrivalButtonAll) {
            result = 0;
        }
        else if(arrivalOptionCurrentButton == binding.filterArrivalButton1) {
            result = 0;
        }
        else if(arrivalOptionCurrentButton == binding.filterArrivalButton2) {
            result = 360;
        }
        else if(arrivalOptionCurrentButton == binding.filterArrivalButton3) {
            result = 720;
        }
        else if(arrivalOptionCurrentButton == binding.filterArrivalButton4) {
            result = 1080;
        }
        return result;
    }

    private int getMaxDepartureMinutes() {
        int result = -1;
        if(departureOptionCurrentButton == binding.filterDepartureButtonAll) {
            result = 1439;
        }
        else if(departureOptionCurrentButton == binding.filterDepartureButton1) {
            result = 360;
        }
        else if(departureOptionCurrentButton == binding.filterDepartureButton2) {
            result = 720;
        }
        else if(departureOptionCurrentButton == binding.filterDepartureButton3) {
            result = 1080;
        }
        else if(departureOptionCurrentButton == binding.filterDepartureButton4) {
            result = 1439;
        }
        return result;
    }

    private int getMinDepartureMinutes() {
        int result = -1;
        if(departureOptionCurrentButton == binding.filterDepartureButtonAll) {
            result = 0;
        }
        else if(departureOptionCurrentButton == binding.filterDepartureButton1) {
            result = 0;
        }
        else if(departureOptionCurrentButton == binding.filterDepartureButton2) {
            result = 360;
        }
        else if(departureOptionCurrentButton == binding.filterDepartureButton3) {
            result = 720;
        }
        else if(departureOptionCurrentButton == binding.filterDepartureButton4) {
            result = 1080;
        }
        return result;
    }

    private void replaceFragment(Fragment fragment, boolean addToBackStack) {
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.flightsDetailsFrameLayout, fragment);
        if(addToBackStack) {
            fragmentTransaction.addToBackStack(null);
        }
        fragmentTransaction.commit();
    }

    private ArrayList<FlightTicketUtils.FlightTicket> getFilteredFlightsList(int minDepartureMinutes, int maxDepartureMinutes,
                                                                             int minArrivalMinutes, int maxArrivalMinutes,
                                                                             int fromPriceValue, int toPriceValue, String sortCriterion) {
        ArrayList<FlightTicketUtils.FlightTicket> result = new ArrayList<>();
        TravelDatabaseHelper travelDatabaseHelper = new TravelDatabaseHelper(requireActivity());
        SQLiteDatabase db;
        Cursor cursor;
        try {
            db = travelDatabaseHelper.getReadableDatabase();
            String selectCondition = "departureCity = ? AND arrivalCity = ? AND departureDate = ? " +
                    "AND departureTime >= ? " +
                    "AND departureTime <= ? " +
                    "AND arrivalTime >= ? " +
                    "AND arrivalTime <= ? " +
                    "AND price >= ? " +
                    "AND price <= ? ";
            String[] selectArgs = new String[]{
                    departureCityCode,
                    arrivalCityCode,
                    departureDateString,
                    Integer.toString(minDepartureMinutes),
                    Integer.toString(maxDepartureMinutes),
                    Integer.toString(minArrivalMinutes),
                    Integer.toString(maxArrivalMinutes),
                    Integer.toString(fromPriceValue),
                    Integer.toString(toPriceValue)};
            String orderByCondition = sortCriterion + " ASC";
            if(sortCriterion.equals("duration")) {
                String sql = "SELECT departureTime, price, number, " +
                        "(arrivalTime - departureTime) AS duration " +
                        "FROM " + TravelDatabaseHelper.TABLE_FLIGHTS +
                        " WHERE " + selectCondition +
                        "ORDER BY duration ASC";
                cursor = db.rawQuery(sql, selectArgs);
            }
            else {
                cursor = db.query(TravelDatabaseHelper.TABLE_FLIGHTS,
                        new String[]{"departureTime", "price", "number"},
                        selectCondition,
                        selectArgs,
                        null, null, orderByCondition);
            }
            // Transfer data from cursor to ArrayList of flight tickets
            for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
                int departureTimeMinutes = cursor.getInt(0);
                String departureTime = TravelDatabaseHelper.convertMinutesToTimeString(departureTimeMinutes);
                int price = cursor.getInt(1);
                String flightNumber = cursor.getString(2);
                FlightTicketUtils.FlightTicket ticket =
                        new FlightTicketUtils.FlightTicket(
                                departureCityCode,
                                FlightTicketUtils.citiesLookup.get(departureCityCode),
                                arrivalCityCode,
                                FlightTicketUtils.citiesLookup.get(arrivalCityCode),
                                departureCalendar.getTime(),
                                departureTime,
                                price,
                                flightNumber);
                result.add(ticket);
            }
            db.close();
            cursor.close();
        } catch(SQLiteException e) {
            Toast toast = Toast.makeText(getActivity(), "Database unavailable", Toast.LENGTH_SHORT);
            toast.show();
        }
        return result;
    }
}