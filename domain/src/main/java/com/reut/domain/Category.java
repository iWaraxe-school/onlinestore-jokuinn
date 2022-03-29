package com.reut.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
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
        log.info("Category: " + name);
        for (Product product: productList) {
            log.info(product.toString());
        }
    }

}
