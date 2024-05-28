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
        data.selectDatabase();
        //data.showDatabase();

        System.out.println("Available flights:\n");
        System.out.println(data.querySelect(data.AVAILABLE_FLIGHTS));

        System.out.println("Flights ordered by distance:\n");
        System.out.println(data.querySelect(data.ORDERED_FLIGHTS));

        System.out.println("Flights that have a stopover:\n");
        System.out.println(data.querySelect(data.STOPOVER_FLIGHTS));
    }
}