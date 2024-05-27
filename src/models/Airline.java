package models;

import java.util.Arrays;

public abstract class Airline extends Plane {
    protected Integer seat_number;
    protected Boolean[] reserved_seats;

    protected Airline(Integer max_weight, Integer max_distance, Integer seat_number) {
        super(max_weight, max_distance);
        this.seat_number = seat_number;
        this.reserved_seats = new Boolean[seat_number];
    }

    abstract public String toString();
}
