package models;

import exceptions.CargoPlaneException;
import exceptions.PlaneSeatsException;
import exceptions.TakenSeatException;

public class Ticket {
    private Double price;
    private Integer seat;
    private Luggage[] luggage;
    private final Flight flight;

    public Ticket(Double price, Integer seat, Flight flight) {
        this.price = price;
        this.seat = seat;
        this.luggage = new Luggage[0];
        this.flight = flight;
    }

    public Ticket(Flight flight) throws CargoPlaneException, PlaneSeatsException {
        this.seat = flight.makeReservation();
        this.flight = flight;
        this.price = flight.ticketPrice();
    }

    public Ticket(Flight flight, Integer seat) throws CargoPlaneException, TakenSeatException, IndexOutOfBoundsException {
        this.seat = seat;
        this.price = flight.makeReservation(seat) + flight.ticketPrice();
        this.flight = flight;
    }

    public void changeSeat(Integer seat) throws CargoPlaneException, TakenSeatException, IndexOutOfBoundsException {
        this.price += flight.makeReservation(seat);
        this.seat = seat;
    }

    public void addLuggage(Luggage suitcase) {
        // Check if there's space on the plane for the extra luggage
        if (flight.leftCapacity() < suitcase.getWeight())
            return;

        Luggage[] new_luggage = new Luggage[luggage.length + 1];
        System.arraycopy(luggage, 0, new_luggage, 0, luggage.length);
        new_luggage[luggage.length] = new Luggage(suitcase);
        luggage = new_luggage;
        price += flight.luggagePrice();
    }

    @Override
    public String toString() {
        return "Ticket{" +
                "price = " + price +
                ", seat = " + seat +
                ", luggage_count = " + luggage.length +
                ", flight = " + flight.toString() +
                '}';
    }
}
