package models;

import exceptions.CargoPlaneException;
import exceptions.LuggageWeightException;
import exceptions.PlaneSeatsException;
import exceptions.TakenSeatException;

public class Ticket {
    private Double price;
    private Integer seat;
    private final Flight flight;
    private Luggage[] luggage;
    private final Integer id;
    static Integer counterId = 0;

    public Ticket(Double price, Integer seat, Flight flight, Integer id, Double[] luggage_weights) {
        this.price = price;
        this.seat = seat;
        this.flight = flight;
        this.id = id;
        this.luggage = new Luggage[luggage_weights.length];
        for (int w = 0; w < luggage_weights.length; w++) {
            try {
                this.luggage[w] = new Luggage(luggage_weights[w]);
            } catch (LuggageWeightException err) {
                System.out.println(err.getMessage());
                System.out.println("Wrong values from database?");
            }
        }
        Ticket.counterId = Math.max(Ticket.counterId, this.id);
    }

    public Ticket(Flight flight) throws CargoPlaneException, PlaneSeatsException {
        this.seat = flight.makeReservation();
        this.flight = flight;
        this.price = flight.ticketPrice();
        this.luggage = new Luggage[0];
        id = ++counterId;
    }

    public Ticket(Flight flight, Integer seat) throws CargoPlaneException, TakenSeatException, IndexOutOfBoundsException {
        this.seat = seat;
        this.price = flight.makeReservation(seat) + flight.ticketPrice();
        this.flight = flight;
        this.luggage = new Luggage[0];
        id = ++counterId;
    }

    public void changeSeat(Integer seat) throws CargoPlaneException, TakenSeatException, IndexOutOfBoundsException {
        this.price += flight.makeReservation(seat);
        flight.vacateSeat(this.seat);
        this.seat = seat;
    }

    public void addLuggage(Luggage suitcase) {
        // Check if there's space on the plane for the extra luggage
        if (flight.leftCapacity() < suitcase.getWeight())
            return;
        flight.addSuitcase(suitcase.getWeight());

        Luggage[] new_luggage = new Luggage[luggage.length + 1];
        System.arraycopy(luggage, 0, new_luggage, 0, luggage.length);
        new_luggage[luggage.length] = new Luggage(suitcase);
        luggage = new_luggage;
        price += flight.luggagePrice();
    }

    public void delete() {
        //Takes off all the luggage from the plane and frees up the seat
        double total_weight = 0;
        for (Luggage w : luggage) {
            total_weight += w.getWeight();
        }
        flight.takeoffSuitcase(total_weight);
        flight.vacateSeat(seat);
    }

    public Integer getID() {
        return id;
    }

    public String getFlightName() {
        return flight.getName();
    }

    public Integer getSeat() {
        return seat;
    }

    public Double getPrice() {
        return price;
    }

    public Plane getPlane() {
        return flight.getPlane();
    }

    public Double[] getLuggage() {
        Double[] weights = new Double[luggage.length];
        for (int i = 0; i < luggage.length; i++)
            weights[i] = luggage[i].getWeight();
        return weights;
    }

    public int max_seat() {
        return flight.max_seat();
    }

    @Override
    public String toString() {
        return "Ticket{" +
                "id = " + id +
                ", price = " + price +
                ", seat = " + seat +
                ", luggage_count = " + luggage.length +
                ", flight = " + flight.toString() +
                '}';
    }
}
