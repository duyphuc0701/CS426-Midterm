package com.example.travelapplication.utils;

import android.util.Pair;

import com.example.travelapplication.fragments.FlightsDetailsFragment;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public class FlightTicketUtils {
    // String keys
    public static final String DEPARTURE_CITY_CODE = "departureCity";
    public static final String ARRIVAL_CITY_CODE = "arrivalCity";
    public static final String DEPARTURE_DATE = "departureDate";
    public static final String SELECTED_FLIGHT = "selectedFlight";
    public static final String TICKET_CLASS = "ticketClass";
    public static final String SEAT_LIST = "seatCode";
    public static final String ADULTS_NUM = "adultsNum";
    public static final String MATCHING_FLIGHTS = "matchingFlights";
    public static final String DEPARTURE_TIME = "departureTime";
    public static final String ARRIVAL_TIME = "arrivalTime";
    public static final String PRICE = "price";
    public static final String DURATION = "duration";
    public static final String DEPARTURE_FILTER = "departureFilter";
    public static final String ARRIVAL_FILTER = "arrivalFilter";
    public static final String MIN_PRICE_FILTER = "minPriceFilter";
    public static final String MAX_PRICE_FILTER = "maxPriceFilter";
    public static final String SORT_CRITERION_FILTER = "sortCriterion";
    public static boolean isSelectTransportBooking = false;
    // Values for passenger info
    public static boolean isEconomy = false;
    public static int adultsNum = 1;

    // Default values for filter
    public static int departureOptionIndex = 0;
    public static int arrivalOptionIndex = 0;
    public static int priceFromValue = 0;
    public static int priceToValue = 400;
    public static String sortCriterion = "price";
    // Date format
    public static SimpleDateFormat dateFormat = new SimpleDateFormat("MMM dd, yyyy", Locale.ENGLISH);
    public static final HashMap<String, String> citiesLookup = new HashMap<>();

    static {
        // Initialize flight cities
        citiesLookup.put("NYC", "New York");
        citiesLookup.put("LDN", "London");

    }

    public static class FlightTicket implements Serializable {
        public final String fromLocationShort;
        public final String fromLocationFull;
        public final String toLocationShort;
        public final String toLocationFull;
        public final Date departureDate;
        public final String departureTime;
        public final int price;
        public final String flightNumber;
        public final String brand;

        public FlightTicket(String fromLocationShort, String fromLocationFull, String toLocationShort, String toLocationFull, Date departureDate, String departureTime, int price, String flightNumber, String brand) {
            this.fromLocationShort = fromLocationShort;
            this.fromLocationFull = fromLocationFull;
            this.toLocationShort = toLocationShort;
            this.toLocationFull = toLocationFull;
            this.departureDate = departureDate;
            this.departureTime = departureTime;
            this.price = price;
            this.flightNumber = flightNumber;
            this.brand = brand;
        }
    }
}
