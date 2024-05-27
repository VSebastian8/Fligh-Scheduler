package models;

import java.sql.*;

public class Database {
    private static final String DATABASE_URL = System.getenv("database_url");
    private static final String DATABASE_USER = System.getenv("database_user");
    private static final String DATABASE_PASSWORD = System.getenv("database_password");

    private static final String ALL_FLIGHTS = "SELECT * FROM \"PLANES\"";


    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DATABASE_URL, DATABASE_USER, DATABASE_PASSWORD);
    }

    public static void selectall() {
        try {
            PreparedStatement preparedStatement = getConnection().prepareStatement(ALL_FLIGHTS);
            ResultSet rs = preparedStatement.executeQuery();
            rs.next();
            System.out.println(rs.getString("type"));

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
