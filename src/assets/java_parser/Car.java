package com.assets.examples.java;

class Car {

    public int price;
    private static final String BRAND = "TESLA";
    private final short year = 1960;

    private void start(Long initialSpeed) {
        return;
    }

    @NotNull
    public Car tune() {
        return this;
    }

    Car merge(Car car1, Car car2) {
        price *= 3;
        return this;
    }
}
