package com.MainProject.MedE.UserRegistration;

import com.MainProject.MedE.Store.ProductModel;
import lombok.Data;

@Data
public class CartItemWithProductDTO {
    private Long itemId;
    private int quantity;
    private Long productId;
    private String productName;
    private double actualPrice;
    private Integer offerPercentage;
    private double discountPrice;

    public CartItemWithProductDTO(CartItem item, ProductModel product) {
        this.itemId = item.getItemId();
        this.quantity = item.getQuantity();
        this.productId = item.getProductId();
        if (product != null) {
            this.productName = product.getProductName();
            this.actualPrice = product.getActualPrice();
            this.offerPercentage = product.getOfferPercentage();
            this.discountPrice = product.getDiscountPrice();
        }
    }

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public double getActualPrice() {
        return actualPrice;
    }

    public void setActualPrice(double actualPrice) {
        this.actualPrice = actualPrice;
    }

    public Integer getOfferPercentage() {
        return offerPercentage;
    }

    public void setOfferPercentage(Integer offerPercentage) {
        this.offerPercentage = offerPercentage;
    }

    public double getDiscountPrice() {
        return discountPrice;
    }

    public void setDiscountPrice(double discountPrice) {
        this.discountPrice = discountPrice;
    }
}
