package models;

import exceptions.PlaneSeatsException;
import exceptions.TakenSeatException;

import java.util.function.Function;

public abstract class Airline extends Plane {
    protected Integer seat_number;
    protected Boolean[] reserved_seats;

    protected Airline(Integer max_weight, Integer max_distance, Integer seat_number) {
        super(max_weight, max_distance);
        this.seat_number = seat_number;
        this.reserved_seats = new Boolean[seat_number];
        for (int i = 0; i < seat_number; i++) {
            this.reserved_seats[i] = false;
        }
    }

    public Integer reserveSeat() throws PlaneSeatsException {
        for (int i = 0; i < seat_number; i++) {
            if (!reserved_seats[i]) {
                reserved_seats[i] = true;
                return i;
            }
        }
        throw new PlaneSeatsException();
    }

    public Double reserveSeat(Integer seat) throws IndexOutOfBoundsException, TakenSeatException {
        if (seat < 0 || seat > seat_number) {
            throw new IndexOutOfBoundsException();
        }
        if (reserved_seats[seat]) {
            throw new TakenSeatException(seat);
        }
        reserved_seats[seat] = true;
        return seatPrice(seat);
    }

    public void setReservedSeats(Integer[] res) {
        for (int i : res) {
            if (i > 0 && i < reserved_seats.length) {
                reserved_seats[i] = true;
            }
        }
    }

    public Integer getSeatNumber() {
        return seat_number;
    }

    abstract public Function<Double, Double> ticketPrice();

    abstract public Double luggagePrice();

    abstract public Double seatPrice(Integer seat);

    abstract public String toString();
}
