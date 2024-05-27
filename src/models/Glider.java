package models;

import java.util.function.Function;

public final class Glider extends Airline {
    public Glider() {
        super(300, 750, 15);
    }

    @Override
    public Function<Double, Double> ticketPrice() {
        return distance -> distance / 25;
    }

    public Double luggagePrice() {
        return 30.0;
    }

    public Double seatPrice(Integer seat) {
        // The price is the same for all seats
        return 5.0;
    }

    @Override
    public String toString() {
        return "Glider Airline{" +
                "max_weight = " + max_weight +
                ", max_distance = " + max_distance +
                ", seat_number = " + seat_number +
                '}';
    }
}
