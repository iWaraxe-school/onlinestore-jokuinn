package com.reut.store.helper;

import com.reut.domain.Product;
import com.reut.store.Store;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;

public class TimerCleanerTest {
    Store store = new Store();
    Timer timer = new Timer();
    TimerCleaner cleaner = new TimerCleaner(store);
    Helper helper = new Helper(store);

    @Test
    public void run_ShouldCleanedPurchasedProductList() {
        Product product = new Product("Sugar", 12.0, 3.0);

        List<Product> products = new ArrayList<>();
        products.add(new Product("Some name", 15.0, 5.0));
        products.add(new Product("Another name", 10.0, 5.0));
        products.add(new Product("Name", 90.0, 5.0));
        products.add(new Product("Sugar", 12.0, 3.0));
        products.add(new Product("Food", 75.0, 5.0));

        helper.createOrder(product.getName(), products);
        timer.schedule(new TimerCleaner(store), 0, 1);
        cleaner.run();
        cleaner.cleanPurchasedProducts();

        Assert.assertTrue(store.getPurchasedProducts().isEmpty());
    }
}
