import java.sql.*;
import java.util.Enumeration;

public class PostgreSQLConnection {
    private static final String URL = "jdbc:postgresql://localhost:5432/ksidb";
    private static final String USER = "postgres";
    private static final String PASSWORD = "admin";

    public static void main(String[] args) {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
            System.out.println("Connected to PostgreSQL!");

            Enumeration<Driver> drivers = DriverManager.getDrivers();
            while(drivers.hasMoreElements()) {
                System.out.println("Driver registered: " + drivers.nextElement().getClass().getName()); // org.postgresql.Driver
            }

            DatabaseMetaData metaData = conn.getMetaData();
            System.out.println("Database name: " + metaData.getDatabaseProductName()); // PostgreSQL
            System.out.println("Version: " + metaData.getDatabaseProductVersion()); // 17.3
            System.out.println("Driver: " + metaData.getDriverName()); // PostgreSQL JDBC Driver
        } catch (SQLException e) {
            System.err.println("Issue connecting: " + e.getMessage());
        }
    }
}
