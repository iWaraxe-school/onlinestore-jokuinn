package com.reut.store;

import com.reut.domain.Product;
import com.reut.store.comparator.XmlReader;
import org.junit.Assert;
import org.junit.Test;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HelperTest {
    private static final String FILE_PATH = "src/main/resources/config.xml";
    private final Store store = new Store();
    private final Helper helper = new Helper(store);
    private static final XmlReader xmlReader = new XmlReader();
    private static Map<String, String> sort;

    static {
        try {
            sort = xmlReader.getAllPropertiesToSort(FILE_PATH);
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getTop5Products_ShouldReturnTop5ProductsByPrice() {

        Map<String, String> sort = helper.setSortCriteriaByPrice();

        List<Product> products = new ArrayList<>();
        products.add(new Product("Some name", 15.0, 5.0));
        products.add(new Product("Another name", 10.0, 5.0));
        products.add(new Product("Name", 90.0, 5.0));
        products.add(new Product("Beer", 84.0, 5.0));
        products.add(new Product("Food", 75.0, 5.0));

        List<Product> expected = new ArrayList<>();
        expected.add(new Product("Name", 90.0, 5.0));
        expected.add(new Product("Beer", 84.0, 5.0));
        expected.add(new Product("Food", 75.0, 5.0));
        expected.add(new Product("Some name", 15.0, 5.0));
        expected.add(new Product("Another name", 10.0, 5.0));

        List<Product> actual = helper.getTop5Products(products, sort);

        Assert.assertEquals(expected.toString(), actual.toString());

    }

    @Test
    public void sortAllProducts_ShouldReturnSortedList() {

        List<Product> products = new ArrayList<>();
        products.add(new Product("Some name", 15.0, 5.0));
        products.add(new Product("Another name", 10.0, 5.0));
        products.add(new Product("Name", 90.0, 5.0));
        products.add(new Product("Beer", 84.0, 5.0));
        products.add(new Product("Food", 75.0, 5.0));

        List<Product> expected = new ArrayList<>();
        expected.add(new Product("Another name", 10.0, 5.0));
        expected.add(new Product("Beer", 84.0, 5.0));
        expected.add(new Product("Food", 75.0, 5.0));
        expected.add(new Product("Name", 90.0, 5.0));
        expected.add(new Product("Some name", 15.0, 5.0));

        List<Product> actual = helper.sortAllProducts(products, sort);

        Assert.assertEquals(expected.toString(), actual.toString());
    }

    @Test
    public void getOrderedProduct_ShouldReturnProductFromList() {
        Product expected = new Product("Sugar", 12.0, 3.0);

        List<Product> products = new ArrayList<>();
        products.add(new Product("Some name", 15.0, 5.0));
        products.add(new Product("Another name", 10.0, 5.0));
        products.add(new Product("Name", 90.0, 5.0));
        products.add(new Product("Sugar", 12.0, 3.0));
        products.add(new Product("Food", 75.0, 5.0));

        Product actual = helper.getOrderedProduct("Sugar", products);

        Assert.assertEquals(expected.toString(), actual.toString());
    }

}
