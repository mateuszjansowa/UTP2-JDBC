package SavepointExample;

import io.github.cdimascio.dotenv.Dotenv;
import java.sql.*;

public class SavepointExample {
    private static final Dotenv env = Dotenv.load();
    private static final String URL = "jdbc:postgresql://localhost:5432/ksidb";
    private static final String USER = env.get("DB_USER");
    private static final String PASSWORD = env.get("DB_PASSWORD");

    public static void main(String[] args) {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
            connection.setAutoCommit(false); // Wyłączamy auto-commit, aby kontrolować transakcję
            Savepoint savepoint = null;

            try (Statement statement = connection.createStatement()) {
                // Wstawienie autora
                statement.executeUpdate("INSERT INTO AUTOR (NAME) VALUES ('Stefan Żeromski')");
                savepoint = connection.setSavepoint("AFTER ADDING AUTHOR");

                // Wstawienie książki (błąd: AUTID 999 nie istnieje)
                statement.executeUpdate("INSERT INTO POZYCJE (ISBN, AUTID, TYTUL, WYDID, ROK, CENA) VALUES ('9784567890123', 999, 'Przedwiośnie', 1, 1924, 50.00)");

                // Zatwierdzenie całej transakcji
                connection.commit();
                System.out.println("Transakcja zakończona sukcesem.");

            } catch (SQLException e) {
                if (savepoint != null) {
                    connection.rollback(savepoint); // Cofamy tylko dodanie książki
                    System.out.println("Cofnięto operację dodania książki, ale autor pozostaje w bazie.");
                } else {
                    connection.rollback(); // Jeśli savepoint nie istnieje, cofamy całą transakcję
                    System.out.println("Cała transakcja została cofnięta.");
                }
            }
        } catch (SQLException e) {
            System.err.println("Błąd połączenia: " + e.getMessage());
        }
    }
}
