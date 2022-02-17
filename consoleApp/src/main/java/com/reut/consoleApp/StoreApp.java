package com.reut.consoleApp;

import com.reut.domain.Category;
import com.reut.domain.Product;
import com.reut.domain.categories.CategoriesENUM;
import com.reut.store.Populator;
import com.reut.store.RandomStorePopulator;
import com.reut.store.Store;

import java.util.List;

public class StoreApp {
    public static void main(String[] args) {

        Populator populator = new RandomStorePopulator();

        List<Category> categories = populator.getCategories();
        Store store = new Store();
        store.setCategories(categories);

        for (Category category: categories) {
            List<Product> products = populator.getProductsForCategory(CategoriesENUM.valueOf(category.getName()));
            category.addProducts(products);
        }

        store.showAllProductsAndCategories();
    }
}
