package models;

import enums.Cities;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Database implements DatabaseInterface {
    private static Database instance = null;

    private static final String DATABASE_URL = System.getenv("database_url");
    private static final String DATABASE_USER = System.getenv("database_user");
    private static final String DATABASE_PASSWORD = System.getenv("database_password");

    public List<Airport> airports;
    public List<Plane> planes;
    public List<Flight> flights;
    public List<Ticket> tickets;

    private static final String ALL_AIRPORTS = "SELECT * FROM \"AIRPORTS\"";
    private static final String ALL_PLANES = "SELECT * FROM \"PLANES\"";
    private static final String ALL_FLIGHTS = "SELECT * FROM \"FLIGHTS\"";
    private static final String ALL_TICKETS = "SELECT * FROM \"TICKETS\"";

    public static final String AVAILABLE_FLIGHTS = "SELECT name, source, destination, type, distance, duration, day FROM \"FLIGHTS\" JOIN \"PLANES\" ON plane_id = id WHERE (type = 'Jet' OR type = 'Glider') AND current_date < day";
    public static final String ORDERED_FLIGHTS = "SELECT name, source, destination, distance, day FROM \"FLIGHTS\" ORDER BY distance ASC";
    public static final String STOPOVER_FLIGHTS = "SELECT * FROM \"FLIGHTS\" WHERE stopover is not null";

    private Database() {
        airports = new ArrayList<>();
        planes = new ArrayList<>();
        flights = new ArrayList<>();
        tickets = new ArrayList<>();
    }

    public static Database getDatabase() {
        if (instance == null) {
            instance = new Database();
        }
        return instance;
    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DATABASE_URL, DATABASE_USER, DATABASE_PASSWORD);
    }

    @Override
    public void selectAirports() {
        try (PreparedStatement preparedStatement = getConnection().prepareStatement(ALL_AIRPORTS);
             ResultSet rs = preparedStatement.executeQuery()) {
            while (rs.next()) {
                Cities city = Cities.valueOf(rs.getString("city"));
                double rating = rs.getDouble("rating");
                if (rating == 0.0) {
                    airports.add(new Airport(city));
                } else {
                    airports.add(new Airport(city, rating));
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void selectPlanes() {
        try (PreparedStatement preparedStatement = getConnection().prepareStatement(ALL_PLANES);
             ResultSet rs = preparedStatement.executeQuery()) {
            int max_id = 0;
            while (rs.next()) {
                Plane p;
                String type = rs.getString("type");
                switch (type) {
                    case "Jet":
                        p = new Jet();
                        break;
                    case "Glider":
                        p = new Glider();
                        break;
                    case "Cargo":
                        p = new Cargo();
                        break;
                    default:
                        continue;
                }
                int id = rs.getInt("id");
                max_id = Math.max(max_id, id);
                p.setPlaneID(id);

                double weight = rs.getDouble("current_weight");
                p.setCurrent_weight(weight);

                java.sql.Array res = rs.getArray("reserved_seats");
                if (res != null && p instanceof Airline) {
                    ((Airline) p).setReservedSeats((Integer[]) res.getArray());
                }

                planes.add(p);
            }
            Plane.counterID = max_id;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private Airport find_airport(String city) {
        for (Airport airport : airports) {
            if (airport.getCity().equals(city)) {
                return airport;
            }
        }
        return null;
    }

    public Plane find_plane(Integer plane_id) {
        for (Plane plane : planes) {
            if (plane.getPlaneID().equals(plane_id)) {
                return plane;
            }
        }
        return null;
    }

    public void selectFlights() {
        try (PreparedStatement preparedStatement = getConnection().prepareStatement(ALL_FLIGHTS);
             ResultSet rs = preparedStatement.executeQuery()) {
            while (rs.next()) {
                flights.add(new Flight(rs.getString("name"), find_airport(rs.getString("source")),
                        find_airport(rs.getString("destination")), find_plane(rs.getInt("plane_id")),
                        rs.getDouble("distance"), rs.getInt("duration"),
                        rs.getTime("takeoff").toString(), rs.getTime("landing").toString(),
                        rs.getDate("day").toString()));
                String stop = rs.getString("stopover");
                if (stop != null)
                    flights.getLast().addStop(find_airport(stop));
            }
        } catch (SQLException err) {
            System.out.println(err.getMessage());
        }
    }

    public Flight find_flight(String name) {
        for (Flight flight : flights) {
            if (flight.getName().equals(name)) {
                return flight;
            }
        }
        return null;
    }

    public void selectTickets() {
        try (PreparedStatement preparedStatement = getConnection().prepareStatement(ALL_TICKETS);
             ResultSet rs = preparedStatement.executeQuery()) {
            while (rs.next()) {
                java.sql.Array luggage = rs.getArray("luggage");
                tickets.add(new Ticket(rs.getDouble("price"), rs.getInt("seat"),
                        find_flight(rs.getString("flight")), rs.getInt("id"),
                        (luggage != null ? (Double[]) luggage.getArray() : new Double[0])));
            }
        } catch (SQLException err) {
            System.out.println(err.getMessage());
        }
    }

    public void showAirports() {
        for (Airport a : airports) {
            System.out.println(a);
        }
    }

    public void showPlanes() {
        for (Plane p : planes) {
            System.out.println(p);
        }
    }

    public void showFlights() {
        for (Flight f : flights)
            System.out.println(f);
    }

    public void showTickets() {
        for (Ticket t : tickets)
            System.out.println(t);
    }

    public void addTicket(Ticket t) {
        String INSERT_TICKET = "INSERT INTO \"TICKETS\" VALUES(?, ?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = getConnection().prepareStatement(INSERT_TICKET)) {
            preparedStatement.setInt(1, t.getID());
            preparedStatement.setString(2, t.getFlightName());
            preparedStatement.setInt(3, t.getSeat());
            preparedStatement.setDouble(4, t.getPrice());

            java.sql.Array weights = getConnection().createArrayOf("Double", t.getLuggage());
            preparedStatement.setArray(5, weights);

            preparedStatement.execute();
        } catch (SQLException err) {
            System.out.println(err.getMessage());
        }
    }

    public void deleteTicket(Ticket t) {
        String DELETE_TICKET = "DELETE FROM \"TICKETS\" WHERE id = ?";
        try (PreparedStatement preparedStatement = getConnection().prepareStatement(DELETE_TICKET)) {
            preparedStatement.setInt(1, t.getID());

            preparedStatement.execute();
        } catch (SQLException err) {
            System.out.println(err.getMessage());
        }
    }

    public void updateTicket(Ticket t) {
        String UPDATE_TICKET = "UPDATE \"TICKETS\" SET flight = ?, seat = ?, price = ?, luggage = ? WHERE id = ?";
        try (PreparedStatement preparedStatement = getConnection().prepareStatement(UPDATE_TICKET)) {
            preparedStatement.setString(1, t.getFlightName());
            preparedStatement.setInt(2, t.getSeat());
            preparedStatement.setDouble(3, t.getPrice());

            java.sql.Array weights = getConnection().createArrayOf("Double", t.getLuggage());
            preparedStatement.setArray(4, weights);

            preparedStatement.setInt(5, t.getID());
            preparedStatement.execute();
        } catch (SQLException err) {
            System.out.println(err.getMessage());
        }
    }

    public String querySelect(String query) {
        try (PreparedStatement preparedStatement = getConnection().prepareStatement(query)) {
            ResultSet res = preparedStatement.executeQuery();
            StringBuilder str = new StringBuilder();
            int columnCount = res.getMetaData().getColumnCount();
            if (columnCount == 0)
                return "No results found";

            for (int i = 1; i <= columnCount; i++) {
                str.append(res.getMetaData().getColumnName(i).toUpperCase());
                str.append(" | ");
            }
            str.append("\n");
            while (res.next()) {
                for (int i = 1; i <= columnCount; i++) {
                    str.append(res.getString(i));
                    str.append(", ");
                    if (i == columnCount)
                        str.append("\n");
                }
            }
            return str.toString();
        } catch (SQLException err) {
            System.out.println(err.getMessage());
            return "Encountered an error while executing query: " + query;
        }
    }
}
