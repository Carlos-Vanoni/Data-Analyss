package com.carlosvanoni.challange.model;

public class Saleman {

    private long cpf;
    private String name;
    private double salary;
    private double priceOfSales;

    public Saleman(long cpf, String name, double salary) {
        this.cpf = cpf;
        this.name = name;
        this.salary = salary;
        this.priceOfSales = 0;
    }

    public long getCpf() {
        return cpf;
    }

    public void setCpf(long cpf) {
        this.cpf = cpf;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public double getPriceOfSales() {
        return priceOfSales;
    }

    public void setPriceOfSales(double priceOfSales) {
        this.priceOfSales = priceOfSales;
    }

    public void addPriceOfSale(double priceOfSales) {
        this.priceOfSales += priceOfSales;
    }
}
