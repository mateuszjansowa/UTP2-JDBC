package ResultsMetadataExample;

import io.github.cdimascio.dotenv.Dotenv;

import java.sql.*;

public class ResultsMetadataExample {
    private static final Dotenv env = Dotenv.load();
    private static final String URL = "jdbc:postgresql://localhost:5432/ksidb";
    private static final String USER = env.get("DB_USER");
    private static final String PASSWORD = env.get("DB_PASSWORD");

    public static void main(String[] args) {
        String query = "SELECT * FROM POZYCJE";

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
             ResultSet resultSet = statement.executeQuery(query);
        ) {
            ResultSetMetaData metaData = resultSet.getMetaData();
            int columnCount = metaData.getColumnCount();

            for (int i = 1; i <= columnCount; i++) {
                System.out.println("Kolumna: " + metaData.getColumnName(i));
                System.out.println("Typ SQL: " + metaData.getColumnTypeName(i));
                System.out.println("Typ: " + metaData.getColumnClassName(i));
                System.out.println("=".repeat(10));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
