package FetchResultsExample;

import io.github.cdimascio.dotenv.Dotenv;

import java.sql.*;

public class ModifiableResultSet {
    private static final Dotenv env = Dotenv.load();
    private static final String URL = "jdbc:postgresql://localhost:5432/ksidb";
    private static final String USER = env.get("DB_USER");
    private static final String PASSWORD = env.get("DB_PASSWORD");

    public static void main(String[] args) {
        String query = "SELECT * FROM AUTOR";

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
             ResultSet resultSet = statement.executeQuery(query);
        ) {
            // przejscie do ostatniego rekordu
            resultSet.last();
            System.out.println("Ostatni autor: " + resultSet.getString("NAME"));

            // przejscie do pierwszego rekordu
            resultSet.first();
            System.out.println("Pierwszy autor: " + resultSet.getString("NAME"));

            // modyfikacja rekordu
            resultSet.absolute(1);
            resultSet.updateString("NAME", "HARLAN COBEN");
            resultSet.updateRow();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
