package models;

import java.sql.Connection;
import java.sql.SQLException;

public interface DatabaseInterface {
    Connection getConnection() throws SQLException;

    void selectAirports();

    void selectPlanes();

    void showAirports();
}
