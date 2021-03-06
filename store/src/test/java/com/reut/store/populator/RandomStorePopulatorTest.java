package com.reut.store.populator;

import com.reut.domain.Category;
import com.reut.domain.categories.BeerCategory;
import com.reut.domain.categories.BookCategory;
import com.reut.domain.categories.CategoriesENUM;
import com.reut.domain.categories.FoodCategory;
import org.junit.Assert;
import org.junit.Test;
import java.util.ArrayList;
import java.util.List;

public class RandomStorePopulatorTest {
    RandomStorePopulator populator = new RandomStorePopulator();
    @Test
    public void getCategories_ShouldReturnAllCategories() {
        List<Category> categories = new ArrayList<>();

        categories.add(new BeerCategory());
        categories.add(new FoodCategory());
        categories.add(new BookCategory());

        List<String> expected = new ArrayList<>();
        List<String> actual = new ArrayList<>();

        List<Category> categoriesList = populator.getCategories();

        for (Category category: categories) {
            expected.add(category.getName());
        }

        for (Category category: categoriesList) {
            actual.add(category.getName());
        }

        Assert.assertEquals(expected, actual);



    }

    @Test
    public void generateRandomPrice_ShouldReturnRandomDoublePrice() {

        Double price = populator.generateRandomPrice();
        String expected = "java.lang.Double";
        String actual = price.getClass().getName();

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void generateRandomRate_ShouldReturnDoubleRate() {

        Double price = populator.generateRandomRate();
        String expected = "java.lang.Double";
        String actual = price.getClass().getName();

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


}
