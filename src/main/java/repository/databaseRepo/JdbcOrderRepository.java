package repository.databaseRepo;

import model.Address;
import model.Order;
import model.ProductOrder;
import repository.IOrderRepository;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static repository.databaseRepo.JdbcUserRepository.getAddress;

public class JdbcOrderRepository implements IOrderRepository {
    static String connectionUrl;

    public JdbcOrderRepository() {
        connectionUrl =
                "jdbc:sqlserver://localhost\\SQLEXPRESS;database=Dental-Hygiene-Shop;"
                        + "user=guest;"
                        + "password=1234;"
                        + "encrypt=true;"
                        + "trustServerCertificate=true;";
    }

    static List<ProductOrder> getProductListByOrder(int orderId) {
        String query = "select productId, price, " +
                " quantity, from ProductOrder where order_id = ?";
        try (Connection connection = DriverManager.getConnection(JdbcUserRepository.connectionUrl)) {

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
        }
        return null;
    }

    @Override
    public List<Order> getOrderList() {
        String query = "select ID, dateTime, userId, deliveryAddressID, price" +
                " from Orders";
        List<Order> orderList = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(connectionUrl)) {

            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(query);

            while (rs.next()) {
                Address address = getAddress(rs.getInt("deliveryAddressID"));
                List<ProductOrder> productList = JdbcOrderRepository.getProductListByOrder(rs.getInt("ID"));
                Order order = new Order((LocalDateTime) rs.getObject("dateTime"),
                        rs.getInt("userId"), address, productList);
                order.setId(rs.getInt("ID"));
                orderList.add(order);
            }
            return orderList;
        } catch (SQLException e) {
            System.out.println("problems retrieving order list");
            e.printStackTrace();
        }
        return null;
    }



    @Override
    public void add(Order order) {

//        addAddressOfUser(user);
//        try (Connection connection = DriverManager.getConnection(connectionUrl)) {
//            System.out.println("Connected to database");
//            String insert = "insert into Users (id, cartId, name, " +
//                    "email, phoneNumber, password, addressId) values (?, ?, ?, ?, ?, ?, ?)";
//            PreparedStatement statement = connection.prepareStatement(insert);
//            statement.setInt(1, user.getId());
//            statement.setInt(2, user.getCart().getId());
//            statement.setString(3, user.getName());
//            statement.setString(4, user.getEmail());
//            statement.setString(5, user.getPhoneNumber());
//            statement.setString(6, user.getPassword());
//            statement.setInt(7, user.getAddress().getId());
//            statement.executeUpdate();
//        } catch (SQLException e) {
//            System.out.println("Can't connect to database");
//            e.printStackTrace();
//        }

    }

    @Override
    public void delete(Integer ID) {
        String query = "delete from Orders where ID = ?";

        try (Connection connection = DriverManager.getConnection(connectionUrl)) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, ID);
            statement.executeQuery();
        } catch (SQLException e) {
            System.out.println("problems deleting order");
            e.printStackTrace();
        }

    }

    @Override
    public Order findById(Integer ID) {

        String query = "select ID, userId, dateTime, deliveryAddressID, price" +
                " from Orders where ID = ?";

        try (Connection connection = DriverManager.getConnection(connectionUrl)) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, ID);
            ResultSet rs = statement.executeQuery();
            Address address = getAddress(rs.getInt("deliveryAddressID"));
            List<ProductOrder> productList = getProductListByOrder(rs.getInt("ID"));
            Order order = new Order((LocalDateTime) rs.getObject("dateTime"),
                    rs.getInt("userId"), address,
                    productList);
            order.setId(rs.getInt("ID"));
            return order;
        } catch (SQLException e) {
            System.out.println("problems retrieving order");
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void modifyProducts(Integer orderId, Integer productId) {

    }

    @Override
    public void modifyDeliveryAddress(Integer ID, Address newDeliveryAddress) {

    }
}
