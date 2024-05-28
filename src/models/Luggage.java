package models;

import exceptions.LuggageWeightException;

public class Luggage {
    static Double max_weight = 20d; //per luggage
    private final Double weight;

    public Luggage(Double weight) throws LuggageWeightException {
        if (weight > max_weight) {
            throw new LuggageWeightException(weight - max_weight);
        }
        this.weight = weight;
    }

    public Luggage(Luggage suitcase) {
        this.weight = suitcase.weight;
    }

    public Double getWeight() {
        return weight;
    }
}
