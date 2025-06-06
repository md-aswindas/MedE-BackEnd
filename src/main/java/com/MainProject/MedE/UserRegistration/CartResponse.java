package com.MainProject.MedE.UserRegistration;

import java.util.List;

public class CartResponse {
    private List<CartProductDTO> items;
    private double totalPrice;
    private double totalDiscount;
    private String storeName;

    public List<CartProductDTO> getItems() {
        return items;
    }

    public void setItems(List<CartProductDTO> items) {
        this.items = items;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public double getTotalDiscount() {
        return totalDiscount;
    }

    public void setTotalDiscount(double totalDiscount) {
        this.totalDiscount = totalDiscount;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }
}
