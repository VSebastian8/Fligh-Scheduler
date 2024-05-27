package models;

public class Airport {
    private final String country;
    private final String city;
    private Double rating;

    public Airport(String country, String city) {
        this.country = country;
        this.city = city;
        this.rating = -1d;
    }

    public Airport(String country, String city, Double rating) {
        this.country = country;
        this.city = city;
        this.rating = rating;
    }

    public String getCity() {
        return city;
    }

    public String getCountry() {
        return country;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    @Override
    public String toString() {
        return "Airport{" +
                "country='" + country + '\'' +
                ", city='" + city + '\'' +
                ", rating=" + (rating == -1 ? "TBD" : rating.toString()) +
                '}';
    }
}
