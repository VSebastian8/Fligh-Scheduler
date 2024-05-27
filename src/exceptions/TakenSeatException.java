package exceptions;

public class TakenSeatException extends Exception {
    public TakenSeatException(Integer seatNumber) {
        super("The seat number " + seatNumber + " is taken.");
    }
}
