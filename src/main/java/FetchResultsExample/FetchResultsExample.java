package FetchResultsExample;

import io.github.cdimascio.dotenv.Dotenv;

import java.math.BigDecimal;
import java.sql.*;
import java.text.NumberFormat;

public class FetchResultsExample {
    private static final Dotenv env = Dotenv.load();
    private static final String URL = "jdbc:postgresql://localhost:5432/ksidb";
    private static final String USER = env.get("DB_USER");
    private static final String PASSWORD = env.get("DB_PASSWORD");

    public static void main(String[] args) {
        String query = "SELECT ISBN, TYTUL, CENA, ROK FROM pozycje WHERE CENA > 10";

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query);
        ) {
            while (resultSet.next()) {
                String isbn = resultSet.getString("ISBN");
                String tytul = resultSet.getString("TYTUL");
                BigDecimal cena = resultSet.getBigDecimal("CENA");
                int rok = resultSet.getInt("ROK");

                System.out.printf("\nISBN %s\nTytul: %s\nCena: %s\nRok wydania: %d\n--------", isbn, tytul, NumberFormat.getCurrencyInstance().format(cena), rok);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
