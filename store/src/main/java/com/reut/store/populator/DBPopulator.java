package com.reut.store.populator;

import com.reut.domain.Category;
import com.reut.domain.Product;
import com.reut.domain.categories.CategoriesENUM;
import com.reut.store.util.DBManager;
import com.reut.store.util.DBService;

import java.sql.SQLException;
import java.util.List;

public class DBPopulator implements Populator {

    public DBManager manager;

    public DBPopulator() {
        this.manager = new DBService();
    }

    public DBPopulator(DBManager dbManager) {
        this.manager = dbManager;
    }

    @Override
    public List<Category> getCategories() {
        try {
            if (manager.createTableIfDoesNotExist()) {
                fillDBByFaker();
            }
        } catch (Exception e) {
            System.out.println("Exception was thrown in getCategories(). Message: " + e.getLocalizedMessage());
        }
        return new RandomStorePopulator().getCategories();
    }

    @Override
    public List<Product> getProductsForCategory(CategoriesENUM category) {
        try {
            return manager.getProductsForCategory(category.name());
        } catch (SQLException e) {
            System.out.println("Exception was thrown in getProductsForCategory(). Message: " + e.getLocalizedMessage());
        }
        return new RandomStorePopulator().getProductsForCategory(category);
    }

    private void fillDBByFaker() {
        List<Category> categories;
        List<Product> products;
        try {
            Populator populator = new RandomStorePopulator();
            categories = populator.getCategories();

            for (Category category : categories) {
                manager.insertCategory(category.getName());

                products = populator.getProductsForCategory(CategoriesENUM.valueOf(category.getName()));
                for (Product product : products) {
                    manager.insertProduct(category, product);
                }
            }
        } catch (SQLException e) {
            System.out.println("SQL Exception Message in fillDbByFaker(): " + e.getLocalizedMessage());
        }
    }
}
