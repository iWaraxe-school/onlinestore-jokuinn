package com.reut.store.util;

import com.reut.domain.Category;
import com.reut.domain.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DBService implements DBManager {
    public static Connection connection = null;

    public DBService() {
        if (connection == null) {
            connection = getConnection();
        }
    }

    private static void createCategoryTable() throws SQLException {
        String sql = "CREATE TABLE IF NOT EXISTS store.category(" +
                "id SERIAL PRIMARY KEY," +
                "name VARCHAR(128) UNIQUE NOT NULL);";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.executeUpdate();
        }
    }

    private static void createProductTable() throws SQLException {
        String sql = "CREATE TABLE IF NOT EXISTS store.product (" +
                "id SERIAL PRIMARY KEY," +
                "name VARCHAR(128) NOT NULL," +
                "category_id INT NOT NULL," +
                "price DOUBLE PRECISION ," +
                "rate DOUBLE PRECISION ," +
                "FOREIGN KEY (category_id) REFERENCES store.category (id));";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.executeUpdate();
        }
    }

    @Override
    public Connection getConnection() {
        return ConnectionManager.open();
    }

    @Override
    public boolean createTableIfDoesNotExist() throws Exception {
        String sql = "CREATE SCHEMA store;";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.executeUpdate();
        }
        createCategoryTable();
        createProductTable();
        return true;
    }

    @Override
    public void insertCategory(String categoryName) throws SQLException {
        String sql = "INSERT INTO store.category (name) VALUES(?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, categoryName);
            preparedStatement.executeUpdate();
        }
    }

    public int getCategoryIdByName(String category) {
        int id = 0;
        String sql = "SELECT id FROM store.category WHERE category.name=?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, category);

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                id = resultSet.getInt("id");
            }

        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return id;
    }

    @Override
    public void insertProduct(Category category, Product product) throws SQLException {
        String sql = "INSERT INTO store.product(name, category_id, price, rate) VALUES (?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, product.getName());
            preparedStatement.setInt(2, getCategoryIdByName(category.getName()));
            preparedStatement.setDouble(3, product.getPrice());
            preparedStatement.setDouble(4, product.getRate());
            preparedStatement.executeUpdate();
        }
    }

    @Override
    public List<Category> getAllCategories() throws SQLException {
        List<Category> categories = new ArrayList<>();

        String sql = "SELECT * FROM store.category";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                categories.add(new Category(resultSet.getString("name")));
            }
            return categories;
        }
    }

    @Override
    public List<Product> getProductsForCategory(String category) throws SQLException {
        List<Product> products = new ArrayList<>();

        String sql = "SELECT * FROM store.product WHERE category_id=?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, getCategoryIdByName(category));
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                products.add(new Product(resultSet.getString("name"), resultSet.getDouble("price"), resultSet.getDouble("rate")));
            }
            return products;
        }
    }

    public void cleanDB() {
        String sql = "DROP TABLE store.product;" +
                "DROP TABLE store.category;" +
                "DROP SCHEMA store;";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.executeUpdate();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }
}
