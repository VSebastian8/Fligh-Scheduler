import enums.Cities;
import exceptions.CargoPlaneException;
import exceptions.LuggageWeightException;
import exceptions.PlaneSeatsException;
import exceptions.TakenSeatException;
import models.*;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        Database data = Database.getDatabase();
        data.selectAirports();
        data.selectPlanes();
        data.selectFlights();
        data.selectTickets();

        data.showDatabase();

        Ticket ticket3;
        try {
            ticket3 = new Ticket(data.flights.get(1), 1);
            System.out.println(ticket3);
        } catch (CargoPlaneException err) {
            System.out.println(err.getMessage());
        } catch (TakenSeatException err) {
            System.out.println(err.getMessage());
            try {
                ticket3 = new Ticket(data.flights.get(3));
                ticket3.changeSeat(18);
                System.out.println(ticket3.toString());
                data.addTicket(ticket3);
            } catch (Exception errr) {
                System.out.println(errr.getMessage());
            }
        }

    }
}