package repository.databaseRepo;

import model.*;
import repository.IUserRepository;

import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


/*
String query = "select country, region, city" +
                " street, number, postalCode from Addresses where ID = ?";
       
        try (Connection connection = DriverManager.getConnection(connectionUrl)) {
            
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();
        } catch (SQLException e) {
            System.out.println("problems retrieving user address");
            e.printStackTrace();
        }
        return null;
 */

/*String connectionUrl =
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
        return null;*/


public class JdbcUserRepository implements IUserRepository {
    static String connectionUrl;

    public JdbcUserRepository() {
        connectionUrl =
                "jdbc:sqlserver://localhost\\SQLEXPRESS;database=Dental-Hygiene-Shop;"
                        + "user=guest;"
                        + "password=1234;"
                        + "encrypt=true;"
                        + "trustServerCertificate=true;";
    }

    static Address getAddress(int id) {
        String query = "select id, country, region, city" +
                " street, number, postalCode from Addresses where ID = ?";

        try (Connection connection = DriverManager.getConnection(connectionUrl)) {

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
        }
        return null;
    }

    @Override
    public void placeOrder(User user, Order order) {
        //        insert order into orders table
        //        empty cart <=> delete all products from ProductOrder with that cartId

        String query = "insert into Orders (ID, dateTime, userId, deliveryAddressId, price) " +
                "values (?,?,?,?,?)";

        try (Connection connection = DriverManager.getConnection(connectionUrl)) {

            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, order.getId());
            statement.setTimestamp(2, Timestamp.valueOf(order.getDateTime()));
            statement.setInt(3, order.getUserId());
            statement.setInt(4, order.getDeliveryAddress().getId());
            statement.setBigDecimal(5, BigDecimal.valueOf(order.getPrice()));
            statement.executeQuery();
        } catch (SQLException e) {
            System.out.println("problems placing order");
            e.printStackTrace();
        }

    }

    @Override
    public List<User> getUserList() {
        List<User> userList = new ArrayList<>();
        final String QUERY = "select id, cartId, name, email," +
                " phoneNumber, password, addressId from Users";

        try (Connection connection = DriverManager.getConnection(connectionUrl)) {

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
        }
        return null;
    }

    List<Order> getOrderHistory(int userId) {
        String query = "select ID, dateTime, deliveryAddressID, price" +
                " from Orders where userId = ?";
        List<Order> orderList = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(connectionUrl)) {

            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, userId);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                Address address = getAddress(rs.getInt("deliveryAddressID"));
                List<ProductOrder> productList = JdbcOrderRepository.getProductListByOrder(rs.getInt("ID"));
                Order order = new Order((LocalDateTime) rs.getObject("dateTime"), userId, address,
                        productList);
                order.setId(rs.getInt("ID"));
                orderList.add(order);
            }
            return orderList;
        } catch (SQLException e) {
            System.out.println("problems retrieving user address");
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void setUserList(List<User> userList) {
    }

    public void addAddressOfUser(User user) {
        try (Connection connection = DriverManager.getConnection(connectionUrl)) {
            System.out.println("Connected to database");
            Address address = user.getAddress();
            String insert = "insert into Addresses (id, country, region, " +
                    "city, street, number, postalCode) values (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(insert);
            statement.setInt(1, address.getId());
            statement.setString(2, address.getCountry());
            statement.setString(3, address.getRegion());
            statement.setString(4, address.getCity());
            statement.setString(5, address.getStreet());
            statement.setString(6, address.getNumber());
            statement.setString(7, address.getPostalCode());
            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Can't connect to database");
            e.printStackTrace();
        }
    }

    @Override
    public void add(User user) {

        addAddressOfUser(user);
        try (Connection connection = DriverManager.getConnection(connectionUrl)) {
            System.out.println("Connected to database");
            String insert = "insert into Users (id, cartId, name, " +
                    "email, phoneNumber, password, addressId) values (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(insert);
            statement.setInt(1, user.getId());
            statement.setInt(2, user.getCart().getId());
            statement.setString(3, user.getName());
            statement.setString(4, user.getEmail());
            statement.setString(5, user.getPhoneNumber());
            statement.setString(6, user.getPassword());
            statement.setInt(7, user.getAddress().getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Can't connect to database");
            e.printStackTrace();
        }

    }

    @Override
    public void delete(Integer ID) {
        try (Connection connection = DriverManager.getConnection(connectionUrl)) {

            String insert = "delete from Users where ID = ?";
            PreparedStatement statement = connection.prepareStatement(insert);
            statement.setInt(1, ID);
            statement.executeUpdate(insert);
        } catch (SQLException e) {
            System.out.println("error deleting user");
        }

    }

    @Override
    public User findById(Integer id) {
        String query = "select id, cartId, name, email," +
                " phoneNumber, password, addressId from Users where ID = ?";

        try (Connection connection = DriverManager.getConnection(connectionUrl)) {

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
        }
        return null;
    }

    @Override
    public String findPasswordByEmail(String email) {

        //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        return null;
    }

    @Override
    public User findByEmail(String email) {
        String query = "select id, cartId, name, email," +
                " phoneNumber, password, addressId from Users where email = ?";

        try (Connection connection = DriverManager.getConnection(connectionUrl)) {

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
        }
        return null;
    }

    @Override
    public void modifyName(Integer ID, String newName) {

        String query = "update Users set name = ? where ID = ?";

        try (Connection connection = DriverManager.getConnection(connectionUrl)) {

            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, newName);
            statement.setInt(2, ID);
            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("problems modifying user name");
            e.printStackTrace();
        }

    }

    @Override
    public void modifyEmail(Integer ID, String newEmail) {
        String query = "update Users set email = ? where ID = ?";

        try (Connection connection = DriverManager.getConnection(connectionUrl)) {

            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, newEmail);
            statement.setInt(2, ID);
            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("problems modifying user email");
            e.printStackTrace();
        }
    }

    @Override
    public void modifyPhoneNumber(Integer ID, String newPhoneNumber) {
        String query = "update Users set phoneNumber = ? where ID = ?";

        try (Connection connection = DriverManager.getConnection(connectionUrl)) {

            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, newPhoneNumber);
            statement.setInt(2, ID);
            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("problems modifying user phone number");
            e.printStackTrace();
        }

    }

    @Override
    public void modifyPassword(Integer ID, String newPassword) {
        String query = "update Users set password = ? where ID = ?";

        try (Connection connection = DriverManager.getConnection(connectionUrl)) {

            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, newPassword);
            statement.setInt(2, ID);
            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("problems modifying user password");
            e.printStackTrace();
        }

    }

    @Override
    public ProductOrder findProductInCartById(User user, Integer productId) {
        String query = "select price, quantity from ProductOrder where productId = ? and cartId = ?";

        try (Connection connection = DriverManager.getConnection(connectionUrl)) {

            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, productId);
            statement.setInt(2, user.getCart().getId());
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return new ProductOrder(
                        productId, resultSet.getInt("quantity"),
                        resultSet.getDouble("price"));
            }
        } catch (SQLException e) {
            System.out.println("problems finding product in cart");
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void removeProductFromCart(User user, ProductOrder product) {
        String query = "delete from ProductOrder where productId = ? and cartId = ?";

        try (Connection connection = DriverManager.getConnection(connectionUrl)) {

            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, product.getProductId());
            statement.setInt(2, user.getCart().getId());
            statement.executeQuery();
        } catch (SQLException e) {
            System.out.println("problems deleting product from cart");
            e.printStackTrace();
        }
    }

    @Override
    public void setCartProductQuantity(ProductOrder product, Integer quantity, User user) {
        String query = "update ProductOrder set quantity = ? where productId = ? and cartId = ?";

        try (Connection connection = DriverManager.getConnection(connectionUrl)) {

            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, quantity);
            statement.setInt(2, product.getProductId());
            statement.setInt(3, user.getCart().getId());
            statement.executeQuery();
        } catch (SQLException e) {
            System.out.println("problems setting product quantity in cart");
            e.printStackTrace();
        }
    }

    @Override
    public void addProductToCart(User user, ProductOrder product) {
        String query = "insert into ProductOrder (productId, price, quantity, cartId) " +
                "values (?,?,?,?)";

        try (Connection connection = DriverManager.getConnection(connectionUrl)) {

            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, product.getProductId());
            statement.setBigDecimal(2, BigDecimal.valueOf(product.getPrice()));
            statement.setInt(3, product.getQuantity());
            statement.setInt(4, user.getCart().getId());
            statement.executeQuery();
        } catch (SQLException e) {
            System.out.println("problems adding new product to cart");
            e.printStackTrace();
        }
    }
}
