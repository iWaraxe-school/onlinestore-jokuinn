package com.reut.store.db;

import com.reut.domain.Category;
import com.reut.domain.Product;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface DBManager {
    Connection getConnection();

    boolean createTableIfDoesNotExist() throws Exception;

    void insertCategory(String categoryName) throws SQLException;

    void insertProduct(Category category, Product product) throws SQLException;

    List<Category> getAllCategories() throws SQLException;

    List<Product> getProductsForCategory(String category) throws SQLException;
}
