import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class PostgreSQLConnection {
    private static final String URL = "jdbc:postgresql://localhost:5432/ksidb";
    private static final String USER = "postgres";
    private static final String PASSWORD = "-";

    public static void main(String[] args) {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
            System.out.println("Connected to PostgreSQL!");
        } catch (SQLException e) {
            System.err.println("Issue connecting: " + e.getMessage());
        }
    }
}
