package models;

public class Cargo extends Plane {
    public Cargo() {
        super(4000, 2000);
    }

    @Override
    public String toString() {
        return "Cargo Plane{" +
                "max_weight = " + max_weight + " kg" +
                ", max_distance = " + max_distance +
                "}";
    }
}
