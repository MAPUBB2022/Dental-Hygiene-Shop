package repository.databaseRepo;

import model.Address;
import model.Order;
import model.ProductOrder;
import model.User;
import repository.IOrderRepository;

import java.math.BigDecimal;
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
                " quantity from ProductOrder where orderId = ?";
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


    public static void addAddress(Address address) {

        try (Connection connection = DriverManager.getConnection(connectionUrl)) {

            String insert = "insert into Addresses (country, region, " +
                    "city, street, number, postalCode) values ( ?, ?, ?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(insert);
            //statement.setInt(1, address.getId());
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
    }


    @Override
    public void add(Order order) {

        try (Connection connection = DriverManager.getConnection(connectionUrl)) {
            System.out.println("Connected to database");
            String insert = "insert into Orders (dateTime, userId, deliveryAddressID, price)" +
                    " values (?, ?, ?, ?)";
            System.out.println("Order price: " + order.getPrice());
            BigDecimal price = BigDecimal.valueOf(order.getPrice());
            System.out.println("Order price Big Decimal: " + price);

            PreparedStatement statement = connection.prepareStatement(insert);
            //statement.setInt(1, order.getId());
            statement.setTimestamp(1, Timestamp.valueOf(order.getDateTime()));
            statement.setInt(2, order.getUserId());
            statement.setInt(3, order.getDeliveryAddress().getId());
            statement.setBigDecimal(4, price);
            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Problem inserting order");
            e.printStackTrace();
        }

    }

    @Override
    public void delete(Integer ID) {
        String query = "delete from Orders where ID = ?";

        try (Connection connection = DriverManager.getConnection(connectionUrl)) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, ID);
            statement.executeUpdate();
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
        //delete product from order; recalculate price
        String query = "delete from ProductOrder where productId = ? and orderId = ?";

        try (Connection connection = DriverManager.getConnection(connectionUrl)) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, productId);
            statement.setInt(2, orderId);
            statement.executeUpdate();

        } catch (SQLException e) {
            System.out.println("problems removing product from order");
            e.printStackTrace();
        }

        query = "update Orders set price = ? where ID = ?";

        Order newOrder = findById(orderId);
        double price = newOrder.getPrice();

        try (Connection connection = DriverManager.getConnection(connectionUrl)) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setBigDecimal(1, BigDecimal.valueOf(price));
            statement.setInt(2, orderId);
            statement.executeUpdate();

        } catch (SQLException e) {
            System.out.println("problems retrieving order");
            e.printStackTrace();
        }
    }

    @Override
    public void modifyDeliveryAddress(Integer ID, Address newDeliveryAddress) {

        addAddress(newDeliveryAddress);

        String query = "update Orders set deliveryAddressId = ? where ID = ?";
        try (Connection connection = DriverManager.getConnection(connectionUrl)) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, newDeliveryAddress.getId());
            statement.setInt(2, ID);
            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("problems modifying delivery address");
            e.printStackTrace();
        }
    }

    @Override
    public void placeOrder(User user, Order order) {
        //        insert order into orders table
        //         insert products of order in ProductOrder table

        add(order);
        Integer orderId = null;
        try (Connection connection = DriverManager.getConnection(connectionUrl)) {
            //select top 1 from orders where userId = ?
            String queryOrderId = "select top 1 ID from Orders where userId = ? " +
                    " order by dateTime desc";
            PreparedStatement statement = connection.prepareStatement(queryOrderId);
            statement.setInt(1, user.getId());
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                orderId = rs.getInt("ID");
            }
            if (orderId == null) {
                throw new SQLException("order id is null");
            }
        } catch (SQLException e) {
            System.out.println("problems retrieving order id");
            e.printStackTrace();
        }

        for (ProductOrder p : order.getProducts()) {
            String query = "insert into ProductOrder (productId, price, quantity, orderId) " +
                    "values (?,?,?,?)";

            try (Connection connection = DriverManager.getConnection(connectionUrl)) {

                PreparedStatement statement = connection.prepareStatement(query);
                statement.setInt(1, p.getProductId());
                statement.setBigDecimal(2, BigDecimal.valueOf(p.getPrice()));
                statement.setInt(3, p.getQuantity());
                statement.setInt(4, orderId);
                statement.executeUpdate();
            } catch (SQLException e) {
                System.out.println("problems placing products in productOrder");
                e.printStackTrace();
            }
        }
    }
}
