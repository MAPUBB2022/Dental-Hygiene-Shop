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
            try (PreparedStatement statement = connection.prepareStatement("create table X(id int identity(1,1))")) {
                //statement.setString(1, "ion");
                statement.execute();
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

//import java.sql.*;
//
//public class SQLDatabaseConnection {
//    static final String DB_URL = "jdbc:sqlserver://localhost\\SQLEXPRESS;database=Dental-Hygiene-Shop";
//    static final String USER = "guest";
//    static final String PASS = "1234";
//    static final String QUERY = "create table Employees (ID int identity(1,1))";
//
//    public static void main(String[] args) {
//        // Open a connection
//        try(Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
//            Statement stmt = conn.createStatement();
//            ResultSet rs = stmt.executeQuery(QUERY);) {
//            System.out.println(rs);
//            // Extract data from result set
////            while (rs.next()) {
////                // Retrieve by column name
//////                System.out.print("ID: " + rs.getInt("id"));
//////                System.out.print(", Age: " + rs.getInt("age"));
//////                System.out.print(", First: " + rs.getString("first"));
//////                System.out.println(", Last: " + rs.getString("last"));
////            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
//}