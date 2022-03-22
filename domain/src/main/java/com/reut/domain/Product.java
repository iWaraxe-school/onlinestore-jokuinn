package com.reut.domain;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Product {
    private String name;

    private double price;

    private double rate;


    @Override
    public String toString() {

        return String.format("Name: '%s', Price: %.2f, Rate: %.1f", name, price, rate);
    }
}
