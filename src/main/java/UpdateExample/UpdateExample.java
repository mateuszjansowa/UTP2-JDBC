package UpdateExample;

import io.github.cdimascio.dotenv.Dotenv;

import java.sql.*;

public class UpdateExample {
    private static final Dotenv dotenv = Dotenv.load();

    private static final String URL = "jdbc:postgresql://localhost:5432/ksidb";
    private static final String USER = dotenv.get("DB_USER");
    private static final String PASSWORD = dotenv.get("DB_PASSWORD");

    public static void main(String[] args) {
        String insertSQL = "INSERT INTO AUTOR (NAME) VALUES ('Czeslaw Milosz')";
        String selectSQL = "SELECT * FROM AUTOR";

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
            Statement statement = connection.createStatement();
            int rowsAffected = statement.executeUpdate(insertSQL);
            System.out.println("Added: " + rowsAffected + " record.");

            ResultSet resultSet = statement.executeQuery(selectSQL);
            while (resultSet.next()) {
                int id = resultSet.getInt("AUTID");
                String name = resultSet.getString("Name");
                System.out.println("ID: " + id + ", Autor: " + name);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

}
