package models;

import java.util.function.Function;


public final class Jet extends Airline {
    public Jet() {
        super(1000, 2000, 50);
    }

    @Override
    public Function<Double, Double> ticketPrice() {
        return distance -> distance / 20;
    }

    public Double luggagePrice() {
        return 18.0;
    }

    public Double seatPrice(Integer seat) {
        // Reserving a window seat is more expensive, then middle, then isle
        return switch (seat % 3) {
            case 0 -> 8.0;
            case 1 -> 4.5;
            case 2 -> 4.0;
            default -> 0.0;
        };
    }

    @Override
    public String toString() {
        return "Jet Airline{" +
                "id = " + planeID +
                ", weight = " + current_weight + "/" + max_weight +
                ", number of seats = " + seat_number +
                '}';
    }
}
