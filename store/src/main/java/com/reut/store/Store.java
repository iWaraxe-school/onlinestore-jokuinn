package com.reut.store;

import com.reut.domain.Category;
import com.reut.domain.Product;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Getter
@Setter
public class Store {
    private List<Category> categories = new ArrayList<>();
    private List<Product> productList = new ArrayList<>();
    private List<Product> purchasedProducts = new ArrayList<>();

    public void showAllProductsAndCategories() {
        for (Category category: categories) {
            category.showProducts();
        }
    }

    public void showProductsList(List<Product> products) {
        for (Product product: products) {
            log.info(product.toString());
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
