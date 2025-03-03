package Transactions;

import io.github.cdimascio.dotenv.Dotenv;

import java.sql.*;

public class TransactionExample {
    private static final Dotenv env = Dotenv.load();
    private static final String URL = "jdbc:postgresql://localhost:5432/ksidb";
    private static final String USER = env.get("DB_USER");
    private static final String PASSWORD = env.get("DB_PASSWORD");

    public static void main(String[] args) {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
            connection.setAutoCommit(false);

            try (
                    PreparedStatement insertAuthor = connection.prepareStatement("INSERT INTO AUTOR (NAME) VALUES (?) RETURNING AUTID");
                    PreparedStatement insertBook = connection.prepareStatement("INSERT INTO POZYCJE (ISBN, AUTID, TYTUL, WYDID, ROK, CENA) VALUES (?, ?, ?, ? , ?, ?)")
            ) {
                insertAuthor.setString(1, "BOLESLAW PRUS");
                ResultSet resultSet = insertAuthor.executeQuery();
                resultSet.next();
                int autorId = resultSet.getInt(1); // get author id

                // insert book for new author
                insertBook.setString(1, "91291202103");
                insertBook.setInt(2, autorId);
                insertBook.setString(3, "Lalka");
                insertBook.setInt(4, 1);
                insertBook.setInt(5, 1890);
                insertBook.setBigDecimal(6, new java.math.BigDecimal("45.00"));
                insertBook.executeUpdate();

                connection.commit();
                System.out.println("Transaction finished");
            } catch (SQLException e) {
                connection.rollback(); // rollback if something is wrong
                System.out.println("ERROR: rollingback " + e.getMessage());
            }
            ;
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
