package com.reut.store.helper;

import com.reut.store.Store;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.TimerTask;

public class TimerCleaner extends TimerTask {
    Store store;

    public TimerCleaner(Store store) {
        this.store = store;
    }

    @Override
    public void run() {
        cleanPurchasedProducts();

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        System.out.println("Clean up purchased list - " + dtf.format(now));
    }

    public void cleanPurchasedProducts() {
        store.getPurchasedProducts().clear();
    }


}
