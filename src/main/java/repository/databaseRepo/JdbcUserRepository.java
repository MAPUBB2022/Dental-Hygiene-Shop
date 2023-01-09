package repository.databaseRepo;

import model.*;
import repository.IUserRepository;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static repository.databaseRepo.JdbcOrderRepository.addAddress;
import static repository.databaseRepo.JdbcOrderRepository.getAddressId;


public class JdbcUserRepository implements IUserRepository {
    static String connectionUrl;

    /**
     * Constructor that establishes the database connection.
     */
    public JdbcUserRepository() {
        connectionUrl =
                "jdbc:sqlserver://localhost\\SQLEXPRESS;database=Dental-Hygiene-Shop;"
                        + "user=guest;"
                        + "password=1234;"
                        + "encrypt=true;"
                        + "trustServerCertificate=true;";

        emptyAllCarts();
    }

    /**
     * This method gets an address from the Addresses table in the database based on its ID.
     *
     * @param id the ID of the address.
     * @return the address.
     */
    static Address getAddress(int id) {
        String query = "select id, country, region, city, " +
                " street, number, postalCode from Addresses where ID = ?";

        try (Connection connection = DriverManager.getConnection(connectionUrl)) {

            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                Address address = new Address(rs.getString("country"),
                        rs.getString("region"),
                        rs.getString("city"),
                        rs.getString("street"),
                        rs.getString("number"),
                        rs.getString("postalCode"));
                address.setId(rs.getInt("ID"));

                return address;
            }
        } catch (SQLException e) {
            System.out.println("problems retrieving address");
            e.printStackTrace();
        }
        return null;
    }


    @Override
    public List<User> getUserList() {
        List<User> userList = new ArrayList<>();
        final String QUERY = "select id, cartId, name, email," +
                " phoneNumber, password, addressId from Users";

        try (Connection connection = DriverManager.getConnection(connectionUrl)) {

            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(QUERY);
            while (rs.next()) {
                //Display values
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setAddress(getAddress(rs.getInt("addressId")));
                user.setName(rs.getString("name"));
                user.setEmail(rs.getString("email"));
                user.setPhoneNumber(rs.getString("phoneNumber"));
                user.setPassword(rs.getString("password"));
                user.setOrderHistory(getOrderHistoryOfUser(user));
                userList.add(user);
            }
            return userList;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Order> getOrderHistoryOfUser(User user) {
        String query = "select ID, dateTime, deliveryAddressID, price" +
                " from Orders where userId = ?";
        List<Order> orderList = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(connectionUrl)) {

            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, user.getId());
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                Address address = getAddress(rs.getInt("deliveryAddressID"));
                List<ProductOrder> productList = JdbcOrderRepository.getProductListByOrder(rs.getInt("ID"));
                Order order = new Order(rs.getTimestamp("dateTime").toLocalDateTime(), user.getId(), address,
                        productList);
                order.setId(rs.getInt("ID"));
                orderList.add(order);
            }
            return orderList;
        } catch (SQLException e) {
            System.out.println("problems retrieving user order history");
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void setUserList(List<User> userList) {
    }

    /**
     * This method adds the address of a user to the Addresses table in the database.
     *
     * @param user the user.
     */
    public void addAddressOfUser(User user) {

        Address address = user.getAddress();
        try (Connection connection = DriverManager.getConnection(connectionUrl)) {

            String insert = "insert into Addresses ( country, region, " +
                    "city, street, number, postalCode) values ( ?, ?, ?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(insert);
            statement.setString(1, address.getCountry());
            statement.setString(2, address.getRegion());
            statement.setString(3, address.getCity());
            statement.setString(4, address.getStreet());
            statement.setString(5, address.getNumber());
            statement.setString(6, address.getPostalCode());
            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Can't connect to database");
            e.printStackTrace();
        }

        try (Connection connection = DriverManager.getConnection(connectionUrl)) {
            String insert = "select ID from Addresses where country=? and region=? and " +
                    "city=? and street=? and number=? and postalCode=? ";
            PreparedStatement statement = connection.prepareStatement(insert);
            statement.setString(1, address.getCountry());
            statement.setString(2, address.getRegion());
            statement.setString(3, address.getCity());
            statement.setString(4, address.getStreet());
            statement.setString(5, address.getNumber());
            statement.setString(6, address.getPostalCode());
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                address.setId(rs.getInt("ID"));
            }
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
            String insert = "insert into Users (cartId, name, " +
                    "email, phoneNumber, password, addressId) values ( ?, ?, ?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(insert);
            //statement.setInt(1, user.getId());
            statement.setInt(1, user.getCart().getId());
            statement.setString(2, user.getName());
            statement.setString(3, user.getEmail());
            statement.setString(4, user.getPhoneNumber());
            statement.setString(5, user.getPassword());
            statement.setInt(6, user.getAddress().getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Can't connect to database");
            e.printStackTrace();
        }

    }

    @Override
    public void delete(Integer ID) {
        try (Connection connection = DriverManager.getConnection(connectionUrl)) {

            String delete = "delete from Users where id = ?";
            PreparedStatement statement = connection.prepareStatement(delete);
            statement.setInt(1, ID);
            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("error deleting user");
            e.printStackTrace();
        }

    }

    @Override
    public User findById(Integer id) {
        String query = "select id, cartId, name, email," +
                " phoneNumber, password, addressId from Users where id = ?";

        try (Connection connection = DriverManager.getConnection(connectionUrl)) {

            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();
            //Display values
            if (rs.next()) {
                User user = new User(rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("phoneNumber"),
                        rs.getString("password"),
                        getAddress(rs.getInt("addressId")), new ArrayList<>());
                user.setId(id);
                user.setOrderHistory(getOrderHistoryOfUser(user));
                return user;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String findPasswordByEmail(String email) {
        String query = "select password from Users where email = ?";

        try (Connection connection = DriverManager.getConnection(connectionUrl)) {

            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, email);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                return rs.getString("password");
            }

        } catch (SQLException e) {
            System.out.println("problems finding password by email");
            e.printStackTrace();
        }
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
            while (rs.next()) {
                User user = new User(rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("phoneNumber"),
                        rs.getString("password"),
                        getAddress(rs.getInt("addressId")), new ArrayList<>());
                user.setId(rs.getInt("id"));
                user.setOrderHistory(getOrderHistoryOfUser(user));
                return user;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void modifyName(Integer id, String newName) {

        String query = "update Users set name = ? where ID = ?";

        try (Connection connection = DriverManager.getConnection(connectionUrl)) {

            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, newName);
            statement.setInt(2, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("problems modifying user name");
            e.printStackTrace();
        }

    }

    @Override
    public void modifyEmail(Integer id, String newEmail) {
        String query = "update Users set email = ? where id = ?";

        try (Connection connection = DriverManager.getConnection(connectionUrl)) {

            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, newEmail);
            statement.setInt(2, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("problems modifying user email");
            e.printStackTrace();
        }
    }

    @Override
    public void modifyPhoneNumber(Integer id, String newPhoneNumber) {
        String query = "update Users set phoneNumber = ? where id = ?";

        try (Connection connection = DriverManager.getConnection(connectionUrl)) {

            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, newPhoneNumber);
            statement.setInt(2, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("problems modifying user phone number");
            e.printStackTrace();
        }

    }

    @Override
    public void modifyPassword(Integer id, String newPassword) {
        String query = "update Users set password = ? where id = ?";

        try (Connection connection = DriverManager.getConnection(connectionUrl)) {

            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, newPassword);
            statement.setInt(2, id);
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
            statement.executeUpdate();
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
            statement.executeUpdate();
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
            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("problems adding new product to cart");
            e.printStackTrace();
        }
    }

    /**
     * This method empties the shopping carts of all users in the database.
     */
    void emptyAllCarts() {
        String query = "delete from ProductOrder where cartId IS NOT NULL";

        try (Connection connection = DriverManager.getConnection(connectionUrl)) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("problems emptying all carts");
            e.printStackTrace();
        }
    }

    @Override
    public void emptyCart(User user) {
        String query = "delete from ProductOrder where cartId = ?";

        try (Connection connection = DriverManager.getConnection(connectionUrl)) {

            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, user.getCart().getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("problems emptying cart");
            e.printStackTrace();
        }
    }

    @Override
    public List<ProductOrder> getCartOfUser(User user) {
        String query = "select productId, price, " +
                " quantity from ProductOrder where cartId = ?";
        try (Connection connection = DriverManager.getConnection(JdbcUserRepository.connectionUrl)) {

            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, user.getCart().getId());
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
        }
        return null;
    }

    @Override
    public void modifyDeliveryAddress(User user, Address newDeliveryAddress) {

        addAddress(newDeliveryAddress);
        Integer addressId = getAddressId(newDeliveryAddress);

        String query = "update Users set addressId = ? where id = ?";
        try (Connection connection = DriverManager.getConnection(connectionUrl)) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, addressId);
            statement.setInt(2, user.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("problems modifying user delivery address");
            e.printStackTrace();
        } catch (NullPointerException e1) {
            System.out.println("address id is null");
            e1.printStackTrace();
        }
    }

}
