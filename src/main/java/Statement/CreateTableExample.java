package Statement;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class CreateTableExample {
    private static final String URL = "jdbc:postgresql://localhost:5432/ksidb";
    private static final String USER = "postgres";
    private static final String PASSWORD = "admin";

    public static void main(String[] args) {
        String createTableSQL = """
                CREATE TABLE IF NOT EXISTS WYDAWCA (
                    WYDID SERIAL PRIMARY KEY,
                    NAME VARCHAR(120) NOT NULL
                );
                """;

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
            Statement statement = connection.createStatement();
            statement.executeUpdate(createTableSQL);
            System.out.println("Table WYDAWCA has been created!");
        } catch (SQLException e) {
            System.out.println("SQL error: " + e.getMessage());
        }
    }
}
