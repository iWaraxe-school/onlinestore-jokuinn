package com.reut.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
public class Category {
    private String name;

    private List<Product> productList;

    public Category(String name) {
        this.name = name;
    }

    public void addProducts(List<Product> products) {
        if (productList == null) {
            productList = new ArrayList<>();
        }
        productList.addAll(products);
    }

    public void showProducts() {
        System.out.println("Category: " + name);
        for (Product product: productList) {
            System.out.println(product.toString());
        }
    }

}
