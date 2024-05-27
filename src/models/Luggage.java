package models;

public class Luggage {
    static Double max_weight = 20d; //per luggage
    private Double weight;

    public Luggage(Double weight) {
        if (weight > max_weight) {
            System.out.println("Handle weight exemption");
        }
        this.weight = weight;
    }

    public Double getWeight() {
        return weight;
    }
}
