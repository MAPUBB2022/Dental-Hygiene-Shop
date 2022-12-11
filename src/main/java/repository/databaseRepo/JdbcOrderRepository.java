package repository.databaseRepo;

import model.Address;
import model.Order;
import model.ProductOrder;
import repository.IOrderRepository;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


public class JdbcOrderRepository implements IOrderRepository {
    Connection connection;
    public JdbcOrderRepository() {
        connection = SQLDatabaseConnection.getConnection();
    }

    @Override
    public List<Order> getOrderList() {
        return null;
    }


    @Override
    public void add(Order order) {

    }

    @Override
    public void delete(Integer ID) {

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

    @Override
    public Order findById(Integer ID) {
        String query = "select ID, userId, dateTime, deliveryAddressID, price" +
                " from Orders where ID = ?";

        try {
            assert connection != null;
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
            System.out.println("problems retrieving user address");
            e.printStackTrace();
        } catch (AssertionError a) {
            System.out.println("no connection to database");
        }
        return null;
    }

    @Override
    public void modifyProducts(Integer ID, List<ProductOrder> products) {

    }

    @Override
    public void modifyDeliveryAddress(Integer ID, Address newDeliveryAddress) {

    }
}
