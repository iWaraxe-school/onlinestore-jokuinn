package com.reut.store;

import com.reut.domain.Category;
import com.reut.domain.Product;

import java.util.ArrayList;
import java.util.List;

public class Store {
    public List<Category> categories = new ArrayList<>();
    public List<Product> productList = new ArrayList<>();

    public void showAllProductsAndCategories() {
        for (Category category: categories) {
            category.showProducts();
        }
    }

    public void showProductsList() {
        for (Product product: productList) {
            System.out.println(product.toString());
        }
    }

}
