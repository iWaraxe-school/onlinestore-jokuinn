package com.reut.domain;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Product {
    private String name;

    private double rate;

    private double price;

    @Override
    public String toString() {

        return String.format("Name: '%s', Price: %.2f, Rate: %.1f", name, price, rate);
    }
}
