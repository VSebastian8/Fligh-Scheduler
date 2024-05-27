package models;

import java.util.Arrays;

public final class Glider extends Airline {
    public Glider() {
        super(300, 750, 25);
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
