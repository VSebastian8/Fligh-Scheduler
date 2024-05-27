package models;

public abstract class Plane {
    protected Integer max_weight;
    protected Integer max_distance;

    protected Plane(Integer max_weight, Integer max_distance) {
        this.max_weight = max_weight;
        this.max_distance = max_distance;
    }

    abstract public String toString();
}

