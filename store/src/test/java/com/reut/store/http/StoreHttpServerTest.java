package com.reut.store.http;

import com.reut.domain.Category;
import com.reut.domain.Product;
import com.reut.domain.categories.BeerCategory;
import com.reut.domain.categories.BookCategory;
import com.reut.domain.categories.CategoriesENUM;
import com.reut.domain.categories.FoodCategory;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class StoreHttpServerTest {
    StoreHttpServer server = new StoreHttpServer();

    @Test
    public void getCategories_shouldReturnCategories() {

        List<Category> categories = new ArrayList<>();
        categories.add(new BeerCategory());
        categories.add(new FoodCategory());
        categories.add(new BookCategory());

        List<String> expected = new ArrayList<>();
        for (Category category : categories) {
            expected.add(category.getName());
        }

        List<Category> categoriesList = server.getCategories();

        List<String> actual = new ArrayList<>();
        for (Category category : categoriesList) {
            actual.add(category.getName());
        }

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void getProductsForCategory_ShouldReturnNotNullList() {

        CategoriesENUM food = CategoriesENUM.FOOD;
        CategoriesENUM beer = CategoriesENUM.BEER;
        CategoriesENUM book = CategoriesENUM.BOOK;

        Assert.assertNotNull(server.getProductsForCategory(food, server.getCategories()));
        Assert.assertNotNull(server.getProductsForCategory(beer, server.getCategories()));
        Assert.assertNotNull(server.getProductsForCategory(book, server.getCategories()));

    }

    @Test
    public void addProductToCart_ShouldReturnProduct() {
        Product beer = new Product("Beer", 24.5, 4.5);
        Product expected = new Product("Milk", 12.0, 5.0);

        List<Product> allProducts = new ArrayList<>();
        allProducts.add(beer);
        allProducts.add(expected);

        List<Product> cartProducts = new ArrayList<>();

        Assert.assertEquals(expected, server.addProductToCart(expected.getName(), cartProducts, allProducts));

    }
}
