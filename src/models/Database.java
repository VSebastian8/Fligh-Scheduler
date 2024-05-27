package models;

import enums.Cities;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Database implements DatabaseInterface {
    private static final String DATABASE_URL = System.getenv("database_url");
    private static final String DATABASE_USER = System.getenv("database_user");
    private static final String DATABASE_PASSWORD = System.getenv("database_password");

    public List<Airport> airports;
    public List<Plane> planes;

    public Database() {
        airports = new ArrayList<>();
        planes = new ArrayList<>();
    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DATABASE_URL, DATABASE_USER, DATABASE_PASSWORD);
    }

    @Override
    public void selectAirports() {
        String ALL_FLIGHTS = "SELECT * FROM \"AIRPORTS\"";
        try (PreparedStatement preparedStatement = getConnection().prepareStatement(ALL_FLIGHTS);
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
        String ALL_PLANES = "SELECT * FROM \"PLANES\"";
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
}
