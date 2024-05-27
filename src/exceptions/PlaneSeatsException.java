package exceptions;

public class PlaneSeatsException extends Exception {
    public PlaneSeatsException() {
        super("Plane is full");
    }
}
