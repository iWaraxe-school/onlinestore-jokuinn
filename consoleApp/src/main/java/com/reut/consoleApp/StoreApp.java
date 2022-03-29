package com.reut.consoleApp;

import com.reut.domain.Product;
import com.reut.store.Helper;
import com.reut.store.TimerCleaner;
import com.reut.store.populator.Populator;
import com.reut.store.populator.RandomStorePopulator;
import com.reut.store.Store;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Timer;

@Slf4j
public class StoreApp {
    public static void main(String[] args) throws Exception {

        Store store = new Store();
        Helper helper = new Helper(store);
        Populator populator = new RandomStorePopulator();

        helper.addProductsAndCategoriesToStore(populator);
        List<Product> products = store.getListOfAllProducts();
        store.showAllProductsAndCategories();

        Scanner scanner = new Scanner(System.in);
        Timer timer = new Timer();
        timer.schedule(new TimerCleaner(store), 0,120000);

        boolean flag = true;
        while (flag) {
            log.info("Choose sort/to5/order/quit");
            String command = scanner.nextLine();
            log.info("Your command is : " + command);
            switch (command) {
                case "sort":
                    store.showProductsList(helper.sortAllProducts(products));
                    break;
                case "top5":
                    Map<String, String> sort = helper.setSortCriteriaByPrice();
                    store.showProductsList(helper.getTop5Products(products ,sort));
                    break;
                case "order":
                    log.info("Enter name of product to order: ");
                    String productName = scanner.nextLine();
                    helper.createOrder(productName, products);
                    break;
                case "quit":
                    flag = false;
                    break;

            }
        }
    }
}
