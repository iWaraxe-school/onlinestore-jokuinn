package com.reut.store.comparator;

import com.reut.domain.Product;
import org.junit.Assert;
import org.junit.Test;
import org.xml.sax.SAXException;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.Map;

public class ProductComparatorTest {
    private static final String FILE_PATH = "src/main/resources/config.xml";
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
    public void compare_ShouldReturnNegativeNumberWhenLeftObjectLessThanRightObject() {

        ProductComparator productComparator = new ProductComparator(sort);
        Product a = new Product("a", 2.0, 2);
        Product b = new Product("b", 3.0, 3);

        int expected = -1;
        int actual = productComparator.compare(a, b);

        Assert.assertEquals(expected, actual);

    }

    @Test
    public void compare_ShouldReturnZeroWhenLeftObjectEqualsRightObject() {
        ProductComparator productComparator = new ProductComparator(sort);
        Product a = new Product("a", 2.0, 2);
        Product b = new Product("a", 2.0, 2);

        int expected = 0;
        int actual = productComparator.compare(a, b);

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void compare_ShouldReturnPositiveNumberWhenLeftObjectGreaterRightObject()  {
        ProductComparator productComparator = new ProductComparator(sort);
        Product a = new Product("b", 3.0, 3);
        Product b = new Product("a", 2.0, 2);

        int expected = 1;
        int actual = productComparator.compare(a, b);

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void getPropertyValue_ShouldReturnCorrectName() throws Exception {
        ProductComparator productComparator = new ProductComparator(sort);
        Product a = new Product("Some name", 3.0, 3);

        String expected = "Some name";
        String actual = productComparator.getPropertyValue(a, "name");

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void getPropertyValue_ShouldReturnIncorrectName() throws Exception {
        ProductComparator productComparator = new ProductComparator(sort);
        Product a = new Product("Some name", 3.0, 3);

        String expected = "Mike";
        String actual = productComparator.getPropertyValue(a, "name");

        Assert.assertNotEquals(expected, actual);
    }

}
