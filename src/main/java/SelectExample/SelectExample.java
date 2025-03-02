package SelectExample;

import io.github.cdimascio.dotenv.Dotenv;

import java.sql.*;

public class SelectExample {
    private static final Dotenv dotenv = Dotenv.load();
    private static final String URL = "jdbc:postgresql://localhost:5432/ksidb";
    private static final String USER = dotenv.get("DB_USER");
    private static final String PASSWORD  = dotenv.get("DB_PASSWORD");

    public static void main(String[] args) {
        String query = "SELECT * FROM AUTOR";

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            while(resultSet.next()) {
                int id = resultSet.getInt("AUTID");
                String name = resultSet.getString("Name");
                System.out.println("ID: " + id + ", Author: " + name);
            }

        } catch(SQLException e) {
            System.out.println("SQL Error: " + e.getMessage());
        }
    }
}
