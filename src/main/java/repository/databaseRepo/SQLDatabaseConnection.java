package repository.databaseRepo;

import org.jetbrains.annotations.Nullable;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SQLDatabaseConnection {
    // Connect to your database.
    // Replace server name, username, and password with your credentials

    public static @Nullable Connection getConnection() {
        String connectionUrl =
                "jdbc:sqlserver://localhost\\SQLEXPRESS;database=Dental-Hygiene-Shop;"
                        + "user=guest;"
                        + "password=1234;"
                        + "encrypt=true;"
                        + "trustServerCertificate=true;";

        try (Connection connection = DriverManager.getConnection(connectionUrl)) {
            System.out.println("Connected to database");

            return connection;
        } catch (SQLException e) {
            System.out.println("Can't connect to database");
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        getConnection();
    }
}
// Handle any errors that may have occurred
