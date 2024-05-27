package exceptions;

public class CargoPlaneException extends Exception {
    public CargoPlaneException(Integer planeID) {
        super("Plan " + planeID + " is not flying Passengers");
    }
}
