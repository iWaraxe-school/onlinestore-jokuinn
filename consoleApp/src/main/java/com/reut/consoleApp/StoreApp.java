package com.reut.consoleApp;


import com.reut.store.Helper;
import com.reut.store.populator.Populator;
import com.reut.store.populator.RandomStorePopulator;
import com.reut.store.Store;

import java.util.Scanner;

public class StoreApp {
    public static void main(String[] args) throws Exception {

        Store store = new Store();
        Helper helper = new Helper(store);
        Populator populator = new RandomStorePopulator();

        helper.addProductsAndCategoriesToStore(populator);
        store.showAllProductsAndCategories();

        Scanner scanner = new Scanner(System.in);

        boolean flag = true;
        while (flag) {
            System.out.println("Enter command sort/top5/quit: ");
            String command = scanner.nextLine();
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
