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
    private final static String FILE_PATH = "store/src/main/resources/config.xml";
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

    public List<Product> sortAllProducts(List<Product> products) throws Exception {
        Map<String, String> sort;
        try {
            XmlReader xmlReader = new XmlReader();
            sort = xmlReader.getAllPropertiesToSort(FILE_PATH);
        }
        catch (ParserConfigurationException e) {
            throw new Exception("Config file exception.");
        }
        return sortAllProducts(products, sort);
    }

    public List<Product> sortAllProducts(List<Product> products, Map<String, String> sort) {
        products.sort(new ProductComparator(sort));
        return products;
    }

    public Map<String, String> setSortCriteriaByPrice() {
        Map<String, String> sort = new HashMap<>();
        sort.put("price", ProductSort.DESC.toString());

        return sort;
    }

    public List<Product> getTop5Products(List<Product> products, Map<String, String> sort) {
        List<Product> productList = sortAllProducts(products, sort);
        return new ArrayList<>(productList.subList(0, 5));
    }
}
