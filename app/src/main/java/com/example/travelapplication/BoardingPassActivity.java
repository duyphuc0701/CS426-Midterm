package com.example.travelapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.travelapplication.databinding.ActivityBoardingPassBinding;
import com.example.travelapplication.utils.FlightTicketUtils;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class BoardingPassActivity extends AppCompatActivity {

    ActivityBoardingPassBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityBoardingPassBinding.inflate(getLayoutInflater());
        EdgeToEdge.enable(this);
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Intent intent = getIntent();
        FlightTicketUtils.FlightTicket selectedTicket =
                (FlightTicketUtils.FlightTicket) intent.getSerializableExtra(FlightTicketUtils.SELECTED_FLIGHT);
        String[] seatCodeList = intent.getStringArrayExtra(FlightTicketUtils.SEAT_LIST);
        if(selectedTicket != null) {
            binding.airwaysFlightNumber.setText("British Airways Flight " + selectedTicket.flightNumber);
            binding.boardingFromLocationShort.setText(selectedTicket.fromLocationShort);
            binding.boardingFromLocationFull.setText(selectedTicket.fromLocationFull);
            binding.boardingToLocationShort.setText(selectedTicket.toLocationShort);
            binding.boardingToLocationFull.setText(selectedTicket.toLocationFull);

            SimpleDateFormat boardingDateFormat = new SimpleDateFormat("dd MMM", Locale.US);
            String departureDateFormatted = boardingDateFormat.format(selectedTicket.departureDate);
            binding.boardingDateValue.setText(departureDateFormatted);

            String ticketClassText = FlightTicketUtils.isEconomy ? "Economy" : "Business";
            binding.passengerClassValue.setText(ticketClassText);

            String adultString = FlightTicketUtils.adultsNum + " " +
                    (FlightTicketUtils.adultsNum == 1 ? "Adult" : "Adults");
            binding.passengerNumberValue.setText(adultString);

            // Use StringBuilder for efficient concatenation
            String passengerSeatsString = getSeatsString(seatCodeList);
            binding.passengerSeatValue.setText(passengerSeatsString);
        }

        binding.boardingBackButton.setOnClickListener(v -> finish());
    }

    @NonNull
    private static String getSeatsString(String[] seatCodeList) {
        StringBuilder stringBuilder = new StringBuilder();
        // Iterate through the array and append each string to the StringBuilder
        if (seatCodeList != null) {
            for (int i = 0; i < seatCodeList.length; i++) {
                stringBuilder.append(seatCodeList[i]);
                if (i < seatCodeList.length - 1) {
                    stringBuilder.append(","); // Add space between words
                }
            }
        }
        // Convert StringBuilder to String
        return stringBuilder.toString();
    }
}