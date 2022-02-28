package com.reut.store.populator;

import com.reut.domain.Category;
import com.reut.domain.Product;
import com.reut.domain.categories.CategoriesENUM;

import java.util.List;

public interface Populator {
    List<Category> getCategories();

    List<Product> getProductsForCategory(CategoriesENUM category);

    List<Product> generateRandomProduct(CategoriesENUM categories, int count);

}
