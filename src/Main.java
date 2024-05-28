import enums.Cities;
import models.*;
import exceptions.*;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    static int gatherInt(Scanner scanner, String message) {
        while (true) {
            try {
                int a = scanner.nextInt();
                scanner.nextLine();
                return a;
            } catch (InputMismatchException e) {
                System.out.println(message);
                scanner.nextLine();
            }
        }
    }

    static Double gatherDouble(Scanner scanner, String message) {
        while (true) {
            try {
                double a = scanner.nextDouble();
                scanner.nextLine();
                return a;
            } catch (InputMismatchException e) {
                System.out.println(message);
                scanner.nextLine();
            }
        }
    }

    static boolean getConfirmation(Scanner scanner, String message) {
        System.out.println(message);
        while (true) {
            String c = scanner.nextLine();
            switch (c) {
                case "y":
                    return true;
                case "n":
                    return false;
                default:
                    System.out.println("Please type one of the options");
            }
        }
    }

    static void createTicketDialog(Scanner scanner, Database data) {
        System.out.println("Please enter the flight you want to book:");
        String selected_flight = scanner.nextLine().toUpperCase();
        Flight the_flight = data.find_flight(selected_flight);
        if (the_flight == null) {
            System.out.println("We couldn't find the flight " + selected_flight + ". Please try using the show options");
            return;
        }

        System.out.println("We found the flight:");
        System.out.println(the_flight);

        Ticket new_ticket;
        while (true) {
            if (getConfirmation(scanner, "Would you like to reserve a particular seat? (y/n)")) {
                System.out.println("Please pick your seat:");
                int seat = gatherInt(scanner, "Pick a number");

                try {
                    new_ticket = new Ticket(the_flight, seat);
                    break;
                } catch (CargoPlaneException err) {
                    System.out.println(err.getMessage());
                    return;
                } catch (TakenSeatException | IndexOutOfBoundsException err) {
                    System.out.println(err.getMessage());
                    if (!getConfirmation(scanner, "Would you like to try again? (y/n)"))
                        return;
                }
            } else {
                try {
                    new_ticket = new Ticket(the_flight);
                    break;
                } catch (CargoPlaneException | PlaneSeatsException err) {
                    System.out.println(err.getMessage());
                    return;
                }
            }
        }


        if (getConfirmation(scanner, "Do you have any luggage to add? (y/n)")) {
            System.out.println("Please specify the suitcase's weight");
            Double w = gatherDouble(scanner, "Please insert a real number");
            try {
                Luggage lug = new Luggage(w);
                new_ticket.addLuggage(lug);
            } catch (LuggageWeightException err) {
                System.out.println(err.getMessage());
            }
            while (getConfirmation(scanner, "Add more? (y/n)")) {
                System.out.println("Please specify the suitcase's weight");
                Double ww = gatherDouble(scanner, "Please insert a real number");
                try {
                    Luggage lug = new Luggage(ww);
                    new_ticket.addLuggage(lug);
                } catch (LuggageWeightException err) {
                    System.out.println(err.getMessage());
                }
            }
        }

        data.tickets.add(new_ticket);
        data.addTicket(new_ticket);
        data.updatePlane(new_ticket.getPlane());
        System.out.println("You have successfully made the following reservation:");
        System.out.println(new_ticket);
    }

    static void deleteTicketDialog(Scanner scanner, Database data) {
        System.out.println("Please enter the id of the ticket you want to delete:");
        int delete_ticket_id = gatherInt(scanner, "Please enter a number:");

        Ticket delete_ticket = data.find_ticket(delete_ticket_id);
        if (delete_ticket == null) {
            System.out.println("Sorry, we couldn't find your ticket\n");
            return;
        }

        System.out.println("You have the ticket:");
        System.out.println(delete_ticket);

        boolean proceed = getConfirmation(scanner, "Are you sure you want to delete this ticket? (y/n)");
        if (!proceed)
            return;

        delete_ticket.delete();
        data.deleteTicket(delete_ticket);
        data.updatePlane(delete_ticket.getPlane());
        data.tickets.remove(delete_ticket);
        System.out.println("Ticket deleted successfully\n");
    }

    static void changeTicketDialog(Scanner scanner, Database data) {
        System.out.println("Please enter your ticket's id:");
        int change_ticket_id = gatherInt(scanner, "Please enter a number:");

        Ticket change_ticket = data.find_ticket(change_ticket_id);
        if (change_ticket == null) {
            System.out.println("Sorry, we couldn't find your ticket\n");
            return;
        }

        System.out.println("You have the ticket:");
        System.out.println(change_ticket);

        while (true) {
            if (!getConfirmation(scanner, "Continue with changing your seat? (y/n)"))
                return;

            System.out.println("Please enter the new seat:");
            int new_seat = gatherInt(scanner, "Please enter a number between 0 and " + change_ticket.max_seat());
            try {
                change_ticket.changeSeat(new_seat);
                data.updateTicket(change_ticket);
                data.updatePlane(change_ticket.getPlane());
                break;
            } catch (CargoPlaneException err) {
                System.out.println(err.getMessage());
                break;
            } catch (TakenSeatException | IndexOutOfBoundsException err) {
                System.out.println(err.getMessage());
                System.out.println("You can try again");
            }
        }
        System.out.println("You have successfully changed your ticket");
        System.out.println(change_ticket);
    }

    public static void main(String[] args) {
        Database data = Database.getDatabase();
        data.selectDatabase();
        //data.showDatabase();

        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.print("\u001B[35m"); // Color purple
            System.out.println("Please pick an option from the list below:");
            System.out.print("\u001B[34m"); // Color blue
            System.out.println("1. show available flights");
            System.out.println("2. show flights ordered by distance");
            System.out.println("3. show flights that have a stopover");
            System.out.println("4. look up flights by airport");
            System.out.println("5. make a new reservation");
            System.out.println("6. delete a ticket");
            System.out.println("7. change your seat (costs extra)");
            System.out.println("8. exit");
            System.out.println("\u001B[0m"); // Color reset

            int o = gatherInt(scanner, "Please enter a number between 1 and 4");
            switch (o) {
                case 1:
                    System.out.println("Available flights:\n");
                    System.out.println(data.querySelect(Database.AVAILABLE_FLIGHTS));
                    break;
                case 2:
                    System.out.println("Flights ordered by distance:\n");
                    System.out.println(data.querySelect(Database.ORDERED_FLIGHTS));
                    break;
                case 3:
                    System.out.println("Flights that have a stopover:\n");
                    System.out.println(data.querySelect(Database.STOPOVER_FLIGHTS));
                    break;
                case 4:
                    System.out.println(Database.getDatabase().flights);
                    break;
                case 5:
                    createTicketDialog(scanner, data);
                    break;
                case 6:
                    deleteTicketDialog(scanner, data);
                    break;
                case 7:
                    changeTicketDialog(scanner, data);
                    break;
                case 8:
                    System.out.println("Thank you for your time!");
                    System.exit(0);
            }
        }
    }
}