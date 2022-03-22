package com.reut.store.populator;

import com.reut.domain.Category;
import com.reut.domain.categories.BeerCategory;
import com.reut.domain.categories.BookCategory;
import com.reut.domain.categories.FoodCategory;
import org.junit.Assert;
import org.junit.Test;
import java.util.ArrayList;
import java.util.List;

public class RandomStorePopulatorTest {
    @Test
    public void getCategories_ShouldReturnAllCategories() {
        Populator populator = new RandomStorePopulator();
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
        RandomStorePopulator populator = new RandomStorePopulator();

        Double price = populator.generateRandomPrice();
        String expected = "java.lang.Double";
        String actual = price.getClass().getName();

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void generateRandomRate_ShouldReturnDoubleRate() {
        RandomStorePopulator populator = new RandomStorePopulator();

        Double price = populator.generateRandomRate();
        String expected = "java.lang.Double";
        String actual = price.getClass().getName();

        Assert.assertEquals(expected, actual);
    }


}
