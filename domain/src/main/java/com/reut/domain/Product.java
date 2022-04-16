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
        return "Name: " + name + ", Price: " + price + ", Rate: " + rate;
    }
}
