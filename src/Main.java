import enums.Cities;
import exceptions.CargoPlaneException;
import exceptions.LuggageWeightException;
import exceptions.PlaneSeatsException;
import exceptions.TakenSeatException;
import models.*;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        Plane[] planes = {new Jet(), new Cargo(), new Jet(), new Glider(), new Cargo()};
        System.out.println(Arrays.toString(planes));

        Airport ABerlin = new Airport(Cities.Berlin, 4.5);
        Airport AMarseille = new Airport(Cities.Marseille);
        Airport ASeville = new Airport(Cities.Seville);

        Flight BM701 = new Flight(ABerlin, AMarseille, 1670d, 120, "16:34", "18:35", "04:15", planes[0]);
        Flight MS802 = new Flight(AMarseille, ASeville, 1800.0, 321, "12:45", "18:06", " 11:17", planes[3]);
        Flight SB900 = new Flight(ASeville, ABerlin, 2310.0, 321, "13:44", "19:05", " 10:20", planes[1]);

        Ticket ticket1 = new Ticket(15.6, 4, BM701);
        System.out.println(ticket1);

        Ticket ticket2;
        try {
            ticket2 = new Ticket(SB900);

            try {
                ticket2.addLuggage(new Luggage(14.0));
            } catch (LuggageWeightException err) {
                System.out.println(err.getMessage());
            }

            System.out.println(ticket2);
        } catch (CargoPlaneException err) {
            System.out.println(err.getMessage());
        } catch (PlaneSeatsException err) {
            System.out.println(err.getMessage());
            return;
        }

        Ticket ticket3;
        try {
            ticket3 = new Ticket(MS802, 1);
            System.out.println(ticket3);
        } catch (CargoPlaneException err) {
            System.out.println(err.getMessage());
        } catch (TakenSeatException err) {
            System.out.println(err.getMessage());
            try {
                ticket3 = new Ticket(MS802);
                ticket3.changeSeat(18);
            } catch (Exception errr) {
                System.out.println(errr.getMessage());
            }
        }
    }
}