package models;

import enums.Cities;

public class Airport {
    private final Cities city;
    private final Double rating;

    public Airport(Cities city) {
        this.city = city;
        this.rating = -1d;
    }

    public Airport(Cities city, Double rating) {
        this.city = city;
        this.rating = rating;
    }

    public String getCity() {
        return city.toString();
    }

    public String getCountry() {
        return city.getCountry().toString();
    }

    @Override
    public String toString() {
        return "Airport{" +
                "country='" + getCountry() + '\'' +
                ", city='" + getCity() + '\'' +
                ", rating=" + (rating == -1 ? "TBD" : rating.toString()) +
                '}';
    }
}
