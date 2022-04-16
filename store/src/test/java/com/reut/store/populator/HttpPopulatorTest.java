package com.reut.store.populator;

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

public class HttpPopulatorTest {
    HttpPopulator populator = new HttpPopulator();

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

        List<Category> categoriesList = populator.getCategories();

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

        Assert.assertNotNull(populator.getProductsForCategory(food));
        Assert.assertNotNull(populator.getProductsForCategory(beer));
        Assert.assertNotNull(populator.getProductsForCategory(book));
    }

    @Test
    public void getProductsFromCart_shouldAddProduct() throws Exception {
        Product beer = new Product("Beer", 24.5, 4.5);
        populator.addToCart(beer.getName());

        Assert.assertNotNull(populator.getProductsFromCart());
    }
}
