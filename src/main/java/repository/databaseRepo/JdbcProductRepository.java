package repository.databaseRepo;

import model.Product;
import model.ProductOrder;
import model.ProductType;
import model.ProductUse;
import repository.IProductRepository;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JdbcProductRepository implements IProductRepository {
    @Override
    public List<Product> getProductList() {
        String query = "select ID, name, basePrice, size, stock, type, [use] from Products";

        try (Connection connection = DriverManager.getConnection(JdbcUserRepository.connectionUrl)) {

            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(query);
            List<Product> productList = new ArrayList<>();
            while (rs.next()) {
                Product product = new Product (rs.getString("name"), rs.getString("size"),
                        ProductType.valueOf(rs.getString("type")), rs.getDouble("basePrice"),
                        ProductUse.valueOf(rs.getString("use")), rs.getInt("stock"));
                product.setId(rs.getInt("ID"));
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
    public void setProductList(List<Product> productList) {

    }

    @Override
    public void add(Product product) {
        String query = "insert into Products(ID, name, basePrice, size, stock, type, [use])" +
                " values  (?,?,?,?,?,?,?)";

        try (Connection connection = DriverManager.getConnection(JdbcUserRepository.connectionUrl)) {

            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, product.getId());
            statement.setString(2, product.getName());
            statement.setBigDecimal(3, BigDecimal.valueOf(product.getBasePrice()));
            statement.setString(4, product.getSize());
            statement.setInt(5, product.getStock());
            statement.setString(6, product.getType().name());
            statement.setString(7, product.getUse().name());
            statement.executeUpdate();

        } catch (SQLException e) {
            System.out.println("problems adding product to repository");
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Integer ID) {
        String query = "delete from Products where ID = ?";

        try (Connection connection = DriverManager.getConnection(JdbcUserRepository.connectionUrl)) {

            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, ID);
            statement.executeQuery();

        } catch (SQLException e) {
            System.out.println("problems deleting product from repository");
            e.printStackTrace();
        }
    }

    @Override
    public Product findById(Integer ID) {
        String query = "select ID, name, basePrice, size, stock, type, [use] from Products" +
                " where ID = ?";

        try (Connection connection = DriverManager.getConnection(JdbcUserRepository.connectionUrl)) {

            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                Product product = new Product (rs.getString("name"), rs.getString("size"),
                        ProductType.valueOf(rs.getString("type")), rs.getDouble("basePrice"),
                        ProductUse.valueOf(rs.getString("use")), rs.getInt("stock"));
                product.setId(rs.getInt("ID"));

                return product;
            }

        } catch (SQLException e) {
            System.out.println("problems finding product by ID ");
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void modify(Integer id, Product newProduct) {

        String query = "update Products set name = ?, basePrice = ?, size = ?, stock = ?, type = ?, [use]  = ?" +
                " where ID = ?";

        try (Connection connection = DriverManager.getConnection(JdbcUserRepository.connectionUrl)) {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, newProduct.getName());
            statement.setBigDecimal(2, BigDecimal.valueOf(newProduct.getBasePrice()));
            statement.setString(3, newProduct.getSize());
            statement.setInt(4, newProduct.getStock());
            statement.setString(5, newProduct.getType().name());
            statement.setString(6, newProduct.getUse().name());
            statement.setInt(7, newProduct.getId());
            statement.executeQuery();

        } catch (SQLException e) {
            System.out.println("Repo: problems modifying product");
            e.printStackTrace();
        }

    }

}
