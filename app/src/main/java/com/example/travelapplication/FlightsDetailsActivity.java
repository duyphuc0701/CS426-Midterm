package com.example.travelapplication;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.sqlite.SQLiteException;

import com.example.travelapplication.databinding.ActivityFlightsDetailsBinding;
import com.example.travelapplication.fragments.FlightsDetailsFragment;
import com.example.travelapplication.utils.FlightTicketUtils;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class FlightsDetailsActivity extends AppCompatActivity {

    ActivityFlightsDetailsBinding binding;
    List<FlightTicketUtils.FlightTicket> matchingFlights;
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
        // Get intent
        Intent intent = getIntent();
        String departureCityCode = intent.getStringExtra(FlightTicketUtils.DEPARTURE_CITY_CODE);
        String arrivalCityCode = intent.getStringExtra(FlightTicketUtils.ARRIVAL_CITY_CODE);
        long departureDateMillis = intent.getLongExtra(FlightTicketUtils.DEPARTURE_DATE, -1);

        // Convert departureDate to format "DD/MM/YYYY"
        Calendar departureCalendar = Calendar.getInstance();
        departureCalendar.setTimeInMillis(departureDateMillis);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
        String departureDateString = sdf.format(departureCalendar.getTime());
        // Get matching tickets
        matchingFlights = searchForFlightsInDatabase(departureCityCode, arrivalCityCode,
                departureDateString, departureCalendar);

        // Pass matching tickets list and user input to fragment
        Bundle bundle = new Bundle();
        bundle.putString(FlightTicketUtils.DEPARTURE_CITY_CODE, departureCityCode);
        bundle.putString(FlightTicketUtils.ARRIVAL_CITY_CODE, arrivalCityCode);
        bundle.putLong(FlightTicketUtils.DEPARTURE_DATE, departureDateMillis);
        bundle.putSerializable(FlightTicketUtils.MATCHING_FLIGHTS, (Serializable) matchingFlights);
        FlightsDetailsFragment frag = new FlightsDetailsFragment();
        frag.setArguments(bundle);
        replaceFragment(frag, true);
    }

    private ArrayList<FlightTicketUtils.FlightTicket> searchForFlightsInDatabase(String departureCityCode,
                                                                                 String arrivalCityCode,
                                                                                 String departureDate,
                                                                                 Calendar departureCalendar) {
        ArrayList<FlightTicketUtils.FlightTicket> result = new ArrayList<>();
        TravelDatabaseHelper travelDatabaseHelper = new TravelDatabaseHelper(this);
        SQLiteDatabase db;
        Cursor cursor;
        try {
            db = travelDatabaseHelper.getReadableDatabase();
            cursor = db.query(TravelDatabaseHelper.TABLE_FLIGHTS,
                    new String[]{"departureTime", "price", "number"},
                    "departureCity = ? AND arrivalCity = ? AND departureDate = ?",
                    new String[]{departureCityCode, arrivalCityCode, departureDate},
                    null, null, "price ASC");
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
                Log.i("departureTime", departureTime);
                Log.i("price", Integer.toString(price));
                Log.i("flightNumber", flightNumber);
                result.add(ticket);
            }
            db.close();
            cursor.close();
        } catch(SQLiteException e) {
            Toast toast = Toast.makeText(this, "Database unavailable", Toast.LENGTH_SHORT);
            toast.show();
        }
        return result;
    }

    private void replaceFragment(Fragment fragment, boolean addToBackStack) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.flightsDetailsFrameLayout, fragment);
        if(addToBackStack) {
            fragmentTransaction.addToBackStack(null);
        }
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_MATCH_ACTIVITY_OPEN);
        fragmentTransaction.commit();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}