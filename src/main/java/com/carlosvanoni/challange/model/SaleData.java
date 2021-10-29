package com.carlosvanoni.challange.model;

public class SaleData {

    private long id;
    private double price;
    private String salemanName;

    public SaleData(long id, double price, String salemanName) {
        this.id = id;
        this.price = price;
        this.salemanName = salemanName;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getSalemanName() {
        return salemanName;
    }

    public void setSalemanName(String salemanName) {
        this.salemanName = salemanName;
    }
}
