package com.reut.store;

import com.reut.domain.Category;
import com.reut.domain.Product;
import com.reut.domain.categories.CategoriesENUM;
import com.reut.store.comparator.ProductComparator;
import com.reut.store.comparator.ProductSort;
import com.reut.store.comparator.XmlReader;
import com.reut.store.populator.Populator;

import javax.xml.parsers.ParserConfigurationException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Helper {
    Store store;

    public Helper(Store store) {
        this.store = store;
    }

    public void addProductsAndCategoriesToStore(Populator populator) {
        List<Category> categories = populator.getCategories();
        store.setCategories(categories);

        for (Category category: categories) {
            List<Product> products = populator.getProductsForCategory(CategoriesENUM.valueOf(category.getName()));
            category.addProducts(products);
        }
    }

    public List<Product> sortAllProducts() throws Exception {
        Map<String, String> sort;
        try {
            XmlReader xmlReader = new XmlReader();
            sort = xmlReader.getAllPropertiesToSort();
        }
        catch (ParserConfigurationException e) {
            throw new Exception("Config file exception.");
        }
        return sortAllProducts(sort);
    }

    public List<Product> sortAllProducts(Map<String, String> sort) {
        List<Product> products = this.store.getListOfAllProducts();
        products.sort(new ProductComparator(sort));

        return products;
    }

    public List<Product> getTop5Products() {
        Map<String, String> sort = new HashMap<>();
        sort.put("price", ProductSort.DESC.toString());

        List<Product> productList = sortAllProducts(sort);

        return new ArrayList<>(productList.subList(0, 5));
    }
}
