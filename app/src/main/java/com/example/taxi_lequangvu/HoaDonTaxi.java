package com.example.taxi_lequangvu;

public class HoaDonTaxi {
    private static int autoIncrement = -1;
    private int id;
    private String plateNumber;
    private double distance;
    private int price;
    private int discountPercent;

    private double totalPrice;

    public HoaDonTaxi(String plateNumber, double distance, int price, int discountPercent) {
        this.id = ++autoIncrement;
        this.plateNumber = plateNumber;
        this.distance = distance;
        this.price = price;
        this.discountPercent = discountPercent;
        this.totalPrice = price * distance * (100 - discountPercent) / 100;
    }


    public String getPlateNumber() {
        return plateNumber;
    }

    public void setPlateNumber(String plateNumber) {
        this.plateNumber = plateNumber;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getDiscountPercent() {
        return discountPercent;
    }

    public void setDiscountPercent(int discountPercent) {
        this.discountPercent = discountPercent;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
