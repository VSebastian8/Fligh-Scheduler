package exceptions;

public class CargoPlaneException extends Exception {
    public CargoPlaneException(Integer planeID) {
        super("Plane " + planeID + " is not flying Passengers");
    }
}
