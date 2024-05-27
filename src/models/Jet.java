package models;

import java.util.Arrays;

public final class Jet extends Airline {
    public Jet() {
        super(1000, 2000, 50);
    }

    @Override
    public String toString() {
        return "Jet Airline{" +
                "max_weight = " + max_weight +
                ", max_distance = " + max_distance +
                ", seat_number = " + seat_number +
                '}';
    }
}
