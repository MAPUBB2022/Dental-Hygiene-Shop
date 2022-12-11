package repository.databaseRepo;

import model.*;
import repository.IUserRepository;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


/*
String query = "select country, region, city" +
                " street, number, postalCode from Addresses where ID = ?";
       
        try {
            assert connection != null;
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();
        } catch (SQLException e) {
            System.out.println("problems retrieving user address");
            e.printStackTrace();
        }
        catch (AssertionError a){
            System.out.println("no connection to database");
        }
        return null;
 */


public class JdbcUserRepository implements IUserRepository {

    Connection connection;

    public JdbcUserRepository() {
        connection = SQLDatabaseConnection.getConnection();
    }

    Address getAddress(int id) {
        String query = "select id, country, region, city" +
                " street, number, postalCode from Addresses where ID = ?";

        try {
            assert connection != null;
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();
            Address address = new Address(rs.getString("country"),
                    rs.getString("region"),
                    rs.getString("city"),
                    rs.getString("street"),
                    rs.getString("number"),
                    rs.getString("postalCode"));
            address.setId(rs.getInt("ID"));
        } catch (SQLException e) {
            System.out.println("problems retrieving address");
            e.printStackTrace();
        } catch (AssertionError a) {
            System.out.println("no connection to database");
        }
        return null;
    }

    @Override
    public List<User> getUserList() {
        List<User> userList = new ArrayList<>();
        final String QUERY = "select id, cartId, name, email," +
                " phoneNumber, password, addressId from Users";

        try {
            assert connection != null;
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(QUERY);
            User user = new User();
            while (rs.next()) {
                //Display values
                user.setId(rs.getInt("id"));
                user.setAddress(getAddress(rs.getInt("addressId")));
                rs.getInt("cartId");
                rs.getString("name");
                rs.getString("email");
                rs.getString("phoneNumber");
                rs.getString("password");
                userList.add(user);
            }
            return userList;
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (AssertionError a) {
            System.out.println("no connection to database");
        }
        return null;
    }

    List<ProductOrder> getProductListByOrder(int orderId) {
        String query = "select productId, price, " +
                " quantity, from ProductOrder where order_id = ?";
        try {
            assert connection != null;
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, orderId);
            ResultSet rs = statement.executeQuery();
            List<ProductOrder> productList = new ArrayList<>();
            while (rs.next()) {
                ProductOrder product = new ProductOrder(rs.getInt("productId"),
                        rs.getInt("quantity"), rs.getDouble("price"));
                productList.add(product);
            }
            return productList;

        } catch (SQLException e) {
            System.out.println("problems retrieving product list");
            e.printStackTrace();
        } catch (AssertionError a) {
            System.out.println("no connection to database");
        }
        return null;
    }

    List<Order> getOrderHistory(int userId) {
        String query = "select ID, dateTime, deliveryAddressID, price" +
                " from Orders where userId = ?";
        List<Order> orderList = new ArrayList<>();
        try {
            assert connection != null;
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, userId);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                Address address = getAddress(rs.getInt("deliveryAddressID"));
                List<ProductOrder> productList = getProductListByOrder(rs.getInt("ID"));
                Order order = new Order((LocalDateTime) rs.getObject("dateTime"), userId, address,
                        productList);
                order.setId(rs.getInt("ID"));
                orderList.add(order);
            }
            return orderList;
        } catch (SQLException e) {
            System.out.println("problems retrieving user address");
            e.printStackTrace();
        } catch (AssertionError a) {
            System.out.println("no connection to database");
        }
        return null;
    }

    @Override
    public void setUserList(List<User> userList) {
    }

    @Override
    public void add(User user) {
        try {
            assert(!connection.isClosed());
            String insert = "insert into Users (id, cartId, name, " +
                    "email, phoneNumber, password, addressId) values (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(insert);
            statement.setInt(1, user.getId());
            statement.setInt(2, user.getCart().getId());
            statement.setString(3, "'" + user.getName() + "'");
            statement.setString(4, user.getEmail());
            statement.setString(5, user.getPhoneNumber());
            statement.setString(6, user.getPassword());
            statement.setInt(7, user.getAddress().getId());
            statement.executeUpdate(insert);
        } catch (SQLException e) {
            System.out.println("error adding user");
        }
        catch (AssertionError a) {
            System.out.println("No connection to database");
        }
    }

    @Override
    public void delete(Integer ID) {
        try {
            assert connection!=null;
            String insert = "delete from Users where ID = ?";
            PreparedStatement statement = connection.prepareStatement(insert);
            statement.setInt(1, ID);
            statement.executeUpdate(insert);
        } catch (SQLException e) {
            System.out.println("error deleting user");
        }
        catch (AssertionError a) {
            System.out.println("No connection to database");
        }
    }

    @Override
    public User findById(Integer id) {
        String query = "select id, cartId, name, email," +
                " phoneNumber, password, addressId from Users where ID = ?";

        try {
            assert connection != null;
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();
            //Display values
            User user = new User(rs.getString("name"),
                    rs.getString("email"),
                    rs.getString("phoneNumber"),
                    rs.getString("password"),
                    getAddress(rs.getInt("addressId")), new ArrayList<>());
            user.setId(id);
            user.setOrderHistory(getOrderHistory(id));
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (AssertionError a) {
            System.out.println("no connection to database");
        }
        return null;
    }

    @Override
    public String findPasswordByEmail(String email) {
        return null;
    }

    @Override
    public User findByEmail(String email) {
        String query = "select id, cartId, name, email," +
                " phoneNumber, password, addressId from Users where email = ?";

        try {
            assert connection != null;
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, email);
            ResultSet rs = statement.executeQuery();
            //Display values
            User user = new User(rs.getString("name"),
                    rs.getString("email"),
                    rs.getString("phoneNumber"),
                    rs.getString("password"),
                    getAddress(rs.getInt("addressId")), new ArrayList<>());
            user.setId(rs.getInt("id"));
            user.setOrderHistory(getOrderHistory(rs.getInt("id")));
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (AssertionError a) {
            System.out.println("no connection to database");
        }
        return null;
    }


    @Override
    public void modifyName(Integer ID, String newName) {

    }

    @Override
    public void modifyEmail(Integer ID, String newEmail) {

    }

    @Override
    public void modifyPhoneNumber(Integer ID, String newPhoneNumber) {

    }

    @Override
    public void modifyPassword(Integer ID, String newPassword) {

    }

}
