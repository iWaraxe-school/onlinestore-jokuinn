package com.reut.store;

import com.github.javafaker.Faker;
import com.reut.domain.Category;
import com.reut.domain.Product;
import com.reut.domain.categories.CategoriesENUM;
import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class RandomStorePopulator implements Populator {
    Faker faker = new Faker();

    @Override
    public List<Category> getCategories() {
        List<Category> categories = new ArrayList<>();

        Reflections reflections = new Reflections("com.reut.domain.categories", new SubTypesScanner());

        Set<Class<? extends Category>> subTypes = reflections.getSubTypesOf(Category.class);

        for (Class<? extends Category> category: subTypes) {
            try {
                categories.add(category.getConstructor().newInstance());
            } catch (InvocationTargetException | NoSuchMethodException | IllegalAccessException | InstantiationException e) {
                System.out.println(e.getLocalizedMessage());
            }
        }

        return categories;
    }

    @Override
    public List<Product> getProductsForCategory(CategoriesENUM category) {
        Random random = new Random();
        int count = random.nextInt(10);

        return new ArrayList<>(generateRandomProduct(category, count));
    }

    @Override
    public List<Product> generateRandomProduct(CategoriesENUM categories, int count) {
        List<Product> products = new ArrayList<>();

        for (int i = 0; i < count; i++) {
            products.add(new Product(generateRandomProductName(categories), generateRandomPrice(), generateRandomRate()));
        }

        return products;
    }

    private String generateRandomProductName(CategoriesENUM category) {
        switch (category) {
            case BIKE:
                return faker.name().name();
            case MILK:
                return faker.food().ingredient();
            case PHONE:
                return faker.phoneNumber().cellPhone();
            default:
                return null;
        }
    }

    private double generateRandomRate() {
        return faker.number().randomDouble(1, 0, 5);
    }

    private double generateRandomPrice() {
        return faker.number().randomDouble(1, 1, 100);
    }
}
