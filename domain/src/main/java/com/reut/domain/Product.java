package com.reut.domain;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Product {
    private String name;

    private double rate;

    private double price;
}
