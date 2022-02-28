package com.reut.consoleApp;


import com.reut.store.StoreHelper;
import com.reut.store.populator.Populator;
import com.reut.store.populator.RandomStorePopulator;
import com.reut.store.Store;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class StoreApp {
    public static void main(String[] args) throws Exception {

        Store store = new Store();
        StoreHelper helper = new StoreHelper(store);
        Populator populator = new RandomStorePopulator();

        helper.addProductsAndCategoriesToStore(populator);
        store.showAllProductsAndCategories();

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        boolean flag = true;
        while (flag) {
            System.out.println("Enter command sort/top5/quit: ");
            String command = reader.readLine();
            System.out.println("Your command is : " + command);
            switch (command) {
                case "sort":
                    store.showProductsList(helper.sortAllProducts());
                    break;
                case "top5":
                    store.showProductsList(helper.getTop5Products());
                    break;
                case "quit":
                    flag = false;
                    break;
            }
        }
    }
}
