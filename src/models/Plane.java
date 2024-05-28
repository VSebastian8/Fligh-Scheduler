package models;

public abstract class Plane {
    static public Integer counterID = 0;
    protected Integer max_weight;
    protected Integer max_distance;
    protected Double current_weight;
    protected Integer planeID;

    protected Plane(Integer max_weight, Integer max_distance) {
        this.max_weight = max_weight;
        this.max_distance = max_distance;
        this.current_weight = 0.0;
        planeID = ++counterID;
    }

    public Integer getMax_weight() {
        return max_weight;
    }

    public void increaseWeight(Double weight) {
        current_weight += weight;
    }

    public void decreaseWeight(Double weight) {
        current_weight -= weight;
    }

    public Double getCurrent_weight() {
        return current_weight;
    }

    public void setPlaneID(Integer planeID) {
        this.planeID = planeID;
    }

    public void setCurrent_weight(Double current_weight) {
        this.current_weight = current_weight;
    }

    public Integer getPlaneID() {
        return planeID;
    }

    abstract public Integer[] getReservedSeats();

    abstract public String toString();
}

