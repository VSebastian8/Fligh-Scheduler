import models.Jet;
import models.Cargo;
import models.Plane;
import models.Airport;
import models.Flight;
import models.Ticket;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        Plane[] planes = {new Jet(), new Cargo(), new Jet(), new Jet(), new Cargo()};
        System.out.println(Arrays.toString(planes));

        Airport ABerlin = new Airport("Germany", "Berlin", 4.5);
        Airport AMarsilla = new Airport("France", "Marsilla");

        Flight BM701 = new Flight(ABerlin, AMarsilla, 1670d, 120, "16:34", "18:05", "04:15", planes[0]);

        Ticket ticket1 = new Ticket(15.6, 4, BM701);

        System.out.println(ticket1);
    }
}