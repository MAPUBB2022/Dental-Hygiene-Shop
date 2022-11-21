import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SQLDatabaseConnection {
    // Connect to your database.
    // Replace server name, username, and password with your credentials
    public static void main(String[] args) {
        String connectionUrl =
                "jdbc:sqlserver://localhost\\SQLEXPRESS;database=Dental-Hygiene-Shop;"
                        + "user=guest;"
                        + "password=1234;"
                        + "encrypt=true;"
                        + "trustServerCertificate=true;";

        try (Connection connection = DriverManager.getConnection(connectionUrl)) {
            try (PreparedStatement statement = connection.prepareStatement("insert into TEST(Name) values (?)")) {
                statement.setString(1, "ion");
                statement.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        // Handle any errors that may have occurred.
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

