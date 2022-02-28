package com.reut.store;

import com.reut.domain.Category;
import com.reut.domain.Product;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Store {
    private List<Category> categories = new ArrayList<>();
    private List<Product> productList = new ArrayList<>();

    public void showAllProductsAndCategories() {
        for (Category category: categories) {
            category.showProducts();
        }
    }

    public void showProductsList(List<Product> products) {
        for (Product product: products) {
            System.out.println(product.toString());
        }
    }

    public List<Product> getListOfAllProducts() {

        List<Product> allProducts = new ArrayList<>();

        for (Category category : this.categories) {
            allProducts.addAll(category.getProductList());
        }

        return allProducts;
    }

}
