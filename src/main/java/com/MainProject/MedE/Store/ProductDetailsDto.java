package com.MainProject.MedE.Store;

import lombok.Data;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.time.LocalDate;

@CrossOrigin
@Data
public class ProductDetailsDto {
    private Integer productId;
    private String productName;
    private Double actualPrice;
    private Double discountPrice;
    private Integer offerPercentage;
    private Integer stockCount;
    private String productDescription;
    private LocalDate expiryDate;
    private byte[] productImage;

    // Additional fields from relations
    private String storeName;
    private String categoryName;

    public Double getDiscountPrice() {
        return discountPrice;
    }

    public void setDiscountPrice(Double discountPrice) {
        this.discountPrice = discountPrice;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Double getActualPrice() {
        return actualPrice;
    }

    public void setActualPrice(Double actualPrice) {
        this.actualPrice = actualPrice;
    }

    public Integer getOfferPercentage() {
        return offerPercentage;
    }

    public void setOfferPercentage(Integer offerPercentage) {
        this.offerPercentage = offerPercentage;
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

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}
