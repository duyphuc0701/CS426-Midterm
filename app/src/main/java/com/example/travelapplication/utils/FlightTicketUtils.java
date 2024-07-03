package com.example.travelapplication.utils;

import android.util.Pair;

import com.example.travelapplication.fragments.FlightsDetailsFragment;

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
    public static final String DEPARTURE_CITY = "departureCity";
    public static final String ARRIVAL_CITY = "arrivalCity";
    public static final String DEPARTURE_DATE = "departureDate";
    public static SimpleDateFormat dateFormat = new SimpleDateFormat("MMM dd, yyyy", Locale.ENGLISH);
    public static final HashMap<String, String> citiesLookup = new HashMap<>();
    public static final HashMap<Pair<Date, Pair<String, String>>, List<FlightTicket>> allFlights = new HashMap<>();
    // An ArrayList of FlightTicket
    public static final List<FlightTicket> NYC_LDN_TICKETS = new ArrayList<>();
    public static List<FlightTicket> MATCHING_TICKETS = new ArrayList<>();
    // The ID for the index into ticket
    public static final String TICKET_ID_KEY = "item_id";
    // The number of tickets.
    private static final int NYC_LDN_COUNT = 4;

    static {
        // Initialize flight cities
        citiesLookup.put("NYC", "New York");
        citiesLookup.put("LDN", "London");
        // Fill the array with NYC_LDN tickets.
        for (int i = 0; i < NYC_LDN_COUNT; i++) {
            add_NYC_LDN_tickets(create_NYC_LDN_ticketsAtPosition(i));
        }
        // Initialize all flights

    }

    public static String extractAirportCode(String city) {
        if (city == null || city.isEmpty()) {
            return "";
        }
        int start = city.indexOf('(');
        int end = city.indexOf(')');
        if (start != -1 && end != -1 && start < end) {
            return city.substring(start + 1, end);
        }
        return "";
    }

    public static List<FlightTicket> generateDummyFlights(String fromLocationShort, String toLocationShort, Calendar departureCalendar) {
        List<FlightTicket> flights = new ArrayList<>();
        Random random = new Random();
        int num_of_flights = 6;
        int minPrice = 10;
        int maxPrice = 100;
        // Random times array (assuming 6 random times)
        String[] times = generateRandomTimes(random, num_of_flights);

        // Random prices array (ranging from 10 to 100)
        int[] prices = generateRandomPrices(random, num_of_flights, minPrice, maxPrice);

        // Random flight numbers array (assuming 6 random flight numbers)
        String[] flightNumbers = generateRandomFlightNumbers(random, num_of_flights);

        for (int i = 0; i < times.length; i++) {
            flights.add(new FlightTicket(
                    fromLocationShort,
                    citiesLookup.get(fromLocationShort),
                    toLocationShort,
                    citiesLookup.get(toLocationShort),
                    departureCalendar.getTime(),
                    times[i],
                    prices[i],
                    flightNumbers[i]
            ));
        }

        return flights;
    }

    private static String[] generateRandomTimes(Random random, int count) {
        String[] times = new String[count];
        String[] meridians = {"AM", "PM"};

        for (int i = 0; i < count; i++) {
            int hour = random.nextInt(12) + 1; // 1 to 12
            int minute = random.nextInt(60); // 0 to 59
            String meridian = meridians[random.nextInt(2)]; // AM or PM
            times[i] = String.format("%02d:%02d %s", hour, minute, meridian);
        }

        return times;
    }

    private static int[] generateRandomPrices(Random random, int count, int minPrice, int maxPrice) {
        int[] prices = new int[count];

        for (int i = 0; i < count; i++) {
            prices[i] = random.nextInt(maxPrice - minPrice + 1) + minPrice;
        }

        return prices;
    }

    private static String[] generateRandomFlightNumbers(Random random, int count) {
        String[] flightNumbers = new String[count];

        for (int i = 0; i < count; i++) {
            String sb = String.valueOf((char) (random.nextInt(26) + 'A')) + // First letter
                    (char) (random.nextInt(26) + 'A') + // Second letter
                    random.nextInt(1000); // Random number (up to 999)
            flightNumbers[i] = sb;
        }

        return flightNumbers;
    }

    public static class FlightTicket {
        public final String fromLocationShort;
        public final String fromLocationFull;
        public final String toLocationShort;
        public final String toLocationFull;
        public final Date departureDate;
        public final String departureTime;
        public final int price;
        public final String flightNumber;

        public FlightTicket(String fromLocationShort, String fromLocationFull, String toLocationShort, String toLocationFull, Date departureDate, String departureTime, int price, String flightNumber) {
            this.fromLocationShort = fromLocationShort;
            this.fromLocationFull = fromLocationFull;
            this.toLocationShort = toLocationShort;
            this.toLocationFull = toLocationFull;
            this.departureDate = departureDate;
            this.departureTime = departureTime;
            this.price = price;
            this.flightNumber = flightNumber;
        }
    }

    private static void add_NYC_LDN_tickets(FlightTicket ticketAtPosition) {
        NYC_LDN_TICKETS.add(ticketAtPosition);
    }

    private static FlightTicket create_NYC_LDN_ticketsAtPosition(int i) {
        Date newDepartureDate;
        String newDepartureTime;
        int newPrice;
        String newFlightNumber;
        String fromLocationShort = "NYC";
        String toLocationShort = "LDN";
        switch (i) {
            case 0:
                newDepartureDate = new Date(2024, 6, 2);
                newDepartureTime = "9:00 AM";
                newPrice = 50;
                newFlightNumber = "NL-41";
                break;
            case 1:
                newDepartureDate = new Date(2024, 6, 2);
                newDepartureTime = "8:00 AM";
                newPrice = 60;
                newFlightNumber = "NL-42";
                break;
            case 2:
                newDepartureDate = new Date(2024, 6, 2);
                newDepartureTime = "7:00 AM";
                newPrice = 65;
                newFlightNumber = "NL-43";
                break;
            default:
                newDepartureDate = new Date(2024, 6, 3);
                newDepartureTime = "9:00 AM";
                newPrice = 50;
                newFlightNumber = "NL-41";
                break;
        }
        return new FlightTicket(fromLocationShort, citiesLookup.get(fromLocationShort),
                toLocationShort, citiesLookup.get(toLocationShort),
                newDepartureDate, newDepartureTime, newPrice, newFlightNumber);
    }
}
