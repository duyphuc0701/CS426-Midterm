package com.example.travelapplication;

import android.content.Intent;
import android.os.Bundle;

import android.view.View;

import android.widget.Toast;

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
import java.util.Random;

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

        // Get intent
        Intent intent = getIntent();
        // Get selected ticket
        FlightTicketUtils.FlightTicket selectedTicket =
                (FlightTicketUtils.FlightTicket) intent.getSerializableExtra(FlightTicketUtils.SELECTED_FLIGHT);
        // Get seats of passengers
        String[] seatCodeList = intent.getStringArrayExtra(FlightTicketUtils.SEAT_LIST);
        if(selectedTicket != null) {
            // Set text of the flight
            binding.airwaysFlightNumber.setText("British Airways Flight " + selectedTicket.flightNumber);
            // Set text of cities
            binding.boardingFromLocationShort.setText(selectedTicket.fromLocationShort);
            binding.boardingFromLocationFull.setText(selectedTicket.fromLocationFull);
            binding.boardingToLocationShort.setText(selectedTicket.toLocationShort);
            binding.boardingToLocationFull.setText(selectedTicket.toLocationFull);

            // Set text of departure date
            SimpleDateFormat boardingDateFormat = new SimpleDateFormat("dd MMM", Locale.US);
            String departureDateFormatted = boardingDateFormat.format(selectedTicket.departureDate);
            binding.boardingDateValue.setText(departureDateFormatted);

            // Set text of class
            String ticketClassText = FlightTicketUtils.isEconomy ? "Economy" : "Business";
            binding.passengerClassValue.setText(ticketClassText);

            // Set text of number of adults
            String adultString = FlightTicketUtils.adultsNum + " " +
                    (FlightTicketUtils.adultsNum == 1 ? "Adult" : "Adults");
            binding.passengerNumberValue.setText(adultString);

            // Set text of boarding pass number
            char firstLetter = selectedTicket.fromLocationShort.charAt(0);
            char secondLetter = selectedTicket.toLocationShort.charAt(0);
            Random random = new Random();
            int twoDigitNumber = 10 + random.nextInt(90);
            int singleDigitNumber = random.nextInt(10);
            String boardingPassNumber =
                    String.format(Locale.ENGLISH,"%c%c%d-%d",
                            firstLetter, secondLetter,
                            twoDigitNumber, singleDigitNumber);
            binding.ticketCodeValue.setText(boardingPassNumber);

            // Use StringBuilder for efficient concatenation, display list of seats
            String passengerSeatsString = getSeatsString(seatCodeList);
            binding.passengerSeatValue.setText(passengerSeatsString);
        }
        // Init download ticket button
        initDownloadTicketButton();
        // Init back button
        initBackButton();
        // Init home button
        initHomeButton();
    }

    private void initHomeButton() {
        binding.boardingHomeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BoardingPassActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            }
        });
    }

    private void initDownloadTicketButton() {
        binding.boardingDownloadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(BoardingPassActivity.this,
                        "Download ticket successfully!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initBackButton() {
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