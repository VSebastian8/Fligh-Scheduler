package models;

import exceptions.CargoPlaneException;
import exceptions.PlaneSeatsException;
import exceptions.TakenSeatException;


public class Flight {
    private final String name;
    private final Airport source;
    private final Airport destination;
    private final Plane plane;
    private final Double distance; // kilometers
    private final Integer duration; // minutes
    private final String takeoff_time; // "hh:mm:ss"
    private final String landing_time;
    private final String day; // "yyyy-mm-dd"
    private Airport stopover = null; // null for direct flights

    public Flight(String name, Airport source, Airport destination, Plane plane, Double distance, Integer duration, String takeoff_time, String landing_time, String day) {
        this.name = name;
        this.source = source;
        this.destination = destination;
        this.plane = plane;
        this.distance = distance;
        this.duration = duration;
        this.takeoff_time = takeoff_time;
        this.landing_time = landing_time;
        this.day = day;
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

    public void vacateSeat(Integer seat) {
        if (plane instanceof Airline)
            ((Airline) plane).vacateSeat(seat);
    }

    public void addSuitcase(Double weight) {
        plane.increaseWeight(weight);
    }

    public void takeoffSuitcase(Double weight) {
        plane.decreaseWeight(weight);
    }

    public Double luggagePrice() {
        return ((Airline) plane).luggagePrice();
    }

    public Double ticketPrice() {
        return ((Airline) plane).ticketPrice().apply(distance);
    }

    public String getName() {
        return name;
    }

    public int max_seat() {
        if (plane instanceof Airline) {
            return ((Airline) plane).getSeatNumber();
        }
        return -1;
    }

    public Plane getPlane() {
        return plane;
    }

    public Integer getDuration() {
        return duration;
    }

    public boolean connectsTo(Airport air) {
        if (air == null)
            return false;
        return air.equals(source) || air.equals(destination) || air.equals(stopover);
    }

    @Override
    public String toString() {
        return "Flight{" +
                "name = " + name +
                ", " + source.getCity() + " -> " + (stopover == null ? "" : "(" + stopover.getCity() + ") -> ") + destination.getCity() +
                ", distance = " + distance + " km" +
                ", duration = " + duration + " min" +
                ", takeoff_time = '" + takeoff_time +
                ", landing_time = '" + landing_time +
                ", day = '" + day +
                ", plane = " + plane +

                '}';
    }
}
