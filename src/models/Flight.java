package models;

import exceptions.CargoPlaneException;
import exceptions.PlaneSeatsException;
import exceptions.TakenSeatException;


public class Flight {
    private final Airport source;
    private final Airport destination;
    private final Double distance; // kilometers
    private final Integer duration; // minutes
    private final String takeoff_time; // "hh:mm"
    private final String landing_time;
    private final String day; // "mm:dd"
    private final Plane plane;
    private Airport stopover = null; // null for direct flights

    public Flight(Airport source, Airport destination, Double distance, Integer duration, String takeoff_time, String landing_time, String day, Plane plane) {
        this.source = source;
        this.destination = destination;
        this.distance = distance;
        this.duration = duration;
        this.takeoff_time = takeoff_time;
        this.landing_time = landing_time;
        this.day = day;
        this.plane = plane;
    }

    public void addStop(Airport stopover) {
        this.stopover = stopover;
    }

    public Double leftCapacity() {
        return plane.getMax_weight() - plane.getCurrent_weight();
    }

    public Integer makeReservation() throws CargoPlaneException, PlaneSeatsException {
        // Try to reserve a random seat
        if (plane instanceof Airline) {
            return ((Airline) plane).reserveSeat();
        } else {
            throw new CargoPlaneException(plane.getPlaneID());
        }
    }

    public Double makeReservation(Integer seat) throws CargoPlaneException, TakenSeatException, IndexOutOfBoundsException {
        // Try to reserve a specific seat, returns extra cost
        if (plane instanceof Airline) {
            return ((Airline) plane).reserveSeat(seat);
        } else {
            throw new CargoPlaneException(plane.getPlaneID());
        }
    }

    public Double luggagePrice() {
        return ((Airline) plane).luggagePrice();
    }

    public Double ticketPrice() {
        return ((Airline) plane).ticketPrice().apply(distance);
    }

    @Override
    public String toString() {
        return "Flight{" +
                "source=" + source +
                ", destination = " + destination +
                ", distance = " + distance +
                ", duration = " + duration +
                ", takeoff_time = '" + takeoff_time +
                ", landing_time = '" + landing_time +
                ", day = '" + day +
                ", plane = " + plane +
                (stopover == null ? "" : ", stopover=" + stopover) +
                '}';
    }
}
