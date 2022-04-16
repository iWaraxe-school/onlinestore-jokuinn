package com.reut.store.helper;

import com.reut.domain.Category;
import com.reut.domain.Product;
import com.reut.domain.categories.CategoriesENUM;
import com.reut.store.Store;
import com.reut.store.comparator.ProductComparator;
import com.reut.store.comparator.ProductSort;
import com.reut.store.comparator.XmlReader;
import com.reut.store.populator.Populator;
import lombok.extern.slf4j.Slf4j;

import javax.xml.parsers.ParserConfigurationException;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
public class Helper {
    private final static String FILE_PATH = "store/src/main/resources/config.xml";
    public ExecutorService executorService = Executors.newFixedThreadPool(3);

    Store store;

    public Helper(Store store) {
        this.store = store;
    }

    public void addProductsAndCategoriesToStore(Populator populator) {
        List<Category> categories = populator.getCategories();
        store.setCategories(categories);

        for (Category category : categories) {
            List<Product> products = populator.getProductsForCategory(CategoriesENUM.valueOf(category.getName()));
            category.addProducts(products);
        }
    }

    public List<Product> sortAllProducts(List<Product> products) throws Exception {
        Map<String, String> sort;
        try {
            XmlReader xmlReader = new XmlReader();
            sort = xmlReader.getAllPropertiesToSort(FILE_PATH);
        } catch (ParserConfigurationException e) {
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

    public void createOrder(String productName, List<Product> products) {
        log.info("createOrder() is started " + Thread.currentThread().getName());

        Product orderedProduct = getOrderedProduct(productName, products);
        int threadTime = new Random().nextInt(30);

        executorService.execute(() -> {
            try {
                log.info("Starting order thread " + Thread.currentThread().getName());
                store.getPurchasedProducts().add(orderedProduct);

                log.info("Purchased products are ");
                store.showProductsList(store.getPurchasedProducts());

                log.info("Time to sleep for " + threadTime);
                Thread.sleep(threadTime * 1000);

                log.info("Finishing order thread " + Thread.currentThread().getName());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        log.info("createOrder() is finished " + Thread.currentThread().getName());
    }

    public void shutdownThreads() {
        executorService.shutdown();
    }

    public Product getOrderedProduct(String productName, List<Product> products) {
        Optional<Product> orderedProduct = products.stream()
                .filter(x -> x.getName().equals(productName))
                .findFirst();

        return orderedProduct.orElse(null);
    }
}
