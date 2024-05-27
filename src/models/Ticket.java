package models;

public class Ticket {
    private Double price;
    private Integer seat;
    private Luggage[] luggage;
    private final Flight flight;

    public Ticket(Double price, Integer seat, Flight flight) {
        this.price = price;
        this.seat = seat;
        this.luggage = new Luggage[0];
        this.flight = flight;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public void setSeat(Integer seat) {
        this.seat = seat;
    }

    public void addLuggage(Luggage suitcase) {
        Luggage[] new_luggage = new Luggage[luggage.length + 1];
        System.arraycopy(luggage, 0, new_luggage, 0, luggage.length);
        new_luggage[luggage.length] = suitcase;
        luggage = new_luggage;
    }

    @Override
    public String toString() {
        return "Ticket{" +
                "price = " + price +
                ", seat = " + seat +
                ", luggage_count = " + luggage.length +
                ", flight = " + flight.toString() +
                '}';
    }
}
