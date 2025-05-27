package com.MainProject.MedE.Admin;

import lombok.Data;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.time.LocalDate;

@Data
@CrossOrigin
public class AdminViewProductDTO {

    private Integer storeId;
    private String storeName;
    private Integer productId;
    private String productName;
    private String productDescription;
    private Integer stockCount;
    private double actualPrice;
    private Integer offerPercentage;
    private double finalDiscountPrice;
    private Integer categoryId;
    private String categoryName;
    private LocalDate expiryDate;
    private byte[] productImage;

    public Integer getStoreId() {
        return storeId;
    }

    public void setStoreId(Integer storeId) {
        this.storeId = storeId;
    }

    public double getFinalDiscountPrice() {
        return finalDiscountPrice;
    }

    public void setFinalDiscountPrice(double finalDiscountPrice) {
        this.finalDiscountPrice = finalDiscountPrice;
    }

    public Integer getOfferPercentage() {
        return offerPercentage;
    }

    public void setOfferPercentage(Integer offerPercentage) {
        this.offerPercentage = offerPercentage;
    }

    public double getActualPrice() {
        return actualPrice;
    }

    public void setActualPrice(double actualPrice) {
        this.actualPrice = actualPrice;
    }

    public Integer getStockCount() {
        return stockCount;
    }

    public void setStockCount(Integer stockCount) {
        this.stockCount = stockCount;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public LocalDate getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(LocalDate expiryDate) {
        this.expiryDate = expiryDate;
    }

    public byte[] getProductImage() {
        return productImage;
    }

    public void setProductImage(byte[] productImage) {
        this.productImage = productImage;
    }
}
