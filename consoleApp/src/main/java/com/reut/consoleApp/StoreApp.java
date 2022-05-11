package com.reut.consoleApp;

import com.reut.domain.Product;
import com.reut.store.Store;
import com.reut.store.db.DBService;
import com.reut.store.helper.Helper;
import com.reut.store.helper.TimerCleaner;
import com.reut.store.populator.*;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Timer;

@Slf4j
public class StoreApp {
    public static void main(String[] args) {
        init();
    }

    public static void init() {
        Populator populator = null;
        try {
            Store store = new Store();
            Helper helper = new Helper(store);


            PopulatorFactory factory = PopulatorFactory.HttpPopulator;

            switch (factory) {
                case DBPopulator:
                    populator = new DBPopulator();
                    break;
                case RandomStorePopulator:
                    populator = new RandomStorePopulator();
                    break;
                case HttpPopulator:
                    populator = new HttpPopulator();
                    break;
            }

            helper.addProductsAndCategoriesToStore(populator);
            List<Product> products = store.getListOfAllProducts();

            store.showAllProductsAndCategories();

            Scanner scanner = new Scanner(System.in);
            Timer timer = new Timer();
            timer.schedule(new TimerCleaner(store), 0, 120000);

            boolean flag = true;
            while (flag) {
                log.info("Choose sort/top5/order/addToCart/quit");
                String command = scanner.nextLine();
                log.info("Your command is : " + command);
                switch (command) {
                    case "sort":
                        store.showProductsList(helper.sortAllProducts(products));
                        break;
                    case "top5":
                        Map<String, String> sort = helper.setSortCriteriaByPrice();
                        store.showProductsList(helper.getTop5Products(products, sort));
                        break;
                    case "order":
                        log.info("Enter name of product to order: ");
                        String productName = scanner.nextLine();
                        helper.createOrder(productName, products);
                        break;
                    case "addToCart":
                        System.out.println("Enter name of product to add to cart:");
                        String product = scanner.nextLine();

                        ((HttpPopulator) populator).addToCart(product);

                        log.info("Products in the cart:");
                        store.showProductsList(((HttpPopulator) populator).getProductsFromCart());
                        break;
                    case "quit":
                        DBService service = new DBService();
                        service.cleanDB();

                        timer.cancel();
                        helper.shutdownThreads();
                        flag = false;
                        break;
                }
            }
        } catch (Exception e) {
            log.error("Error: the exception was thrown with message: " + e.getLocalizedMessage());
        }

    }
}
