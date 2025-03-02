package PreparedStatementExample;

import io.github.cdimascio.dotenv.Dotenv;

import java.sql.*;

public class PreparedStatementExample {
    private static final Dotenv env = Dotenv.load();
    private static final String URL = "jdbc:postgresql://localhost:5432/ksidb";
    private static final String USER = env.get("DB_USER");
    private static final String PASSWORD = env.get("DB_PASSWORD");

    public static void main(String[] args) {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
            String insertSQL = "INSERT INTO AUTOR (NAME) VALUES (?)";

            PreparedStatement preparedStatement = connection.prepareStatement(insertSQL);

            preparedStatement.setString(1, "JULIUSZ SLOWACKI"); // bezpieczne przypisanie wartosci

            int rowsAffected = preparedStatement.executeUpdate();

            System.out.println("Added: " + rowsAffected + " record.");

            // ================
            String selectSQL = "SELECT * FROM AUTOR";
            PreparedStatement statement = connection.prepareStatement(selectSQL);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("AUTID");
                String name = resultSet.getString("Name");
                System.out.println("ID: " + id + ", Author: " + name);
            }
            // ====================
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
