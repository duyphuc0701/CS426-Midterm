package com.example.travelapplication.utils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class FlightTicketUtils {
    // An ArrayList of FlightTicket
    public static final List<FlightTicket> NYC_LDN_TICKETS = new ArrayList<>();
    // The ID for the index into ticket
    public static final String TICKET_ID_KEY = "item_id";
    // The number of tickets.
    private static final int NYC_LDN_COUNT = 3;
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

    static {
        // Fill the array with NYC_LDN tickets.
        for (int i = 0; i < NYC_LDN_COUNT; i++) {
            add_NYC_LDN_tickets(create_NYC_LDN_ticketsAtPosition(i));
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
        switch (i) {
            case 0:
                newDepartureDate = new Date(2022, 6, 2);
                newDepartureTime = "9:00 AM";
                newPrice = 50;
                newFlightNumber = "NL-41";
                break;
            case 1:
                newDepartureDate = new Date(2022, 6, 2);
                newDepartureTime = "8:00 AM";
                newPrice = 60;
                newFlightNumber = "NL-42";
                break;
            default:
                newDepartureDate = new Date(2022, 6, 2);
                newDepartureTime = "7:00 AM";
                newPrice = 65;
                newFlightNumber = "NL-43";
                break;
        }
        return new FlightTicket("NYC", "New York",
                "LDN", "London",
                newDepartureDate, newDepartureTime, newPrice, newFlightNumber);
    }
}
