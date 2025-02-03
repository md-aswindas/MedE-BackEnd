package com.MainProject.MedE.Store;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "product_table")
public class ProductModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column(name = "productId")
    private Integer productId;

    @Column(name = "storeId")
    private Integer storeId;

    @Column(name = "productName")
    private String productName;

    @Column(name = "productDesc")
    private String productDesc;
    
    @Column(name = "stock")
    private Integer stock;

    @Column(name = "actualPrice")
    private Integer actualPrice;

    @Column(name = "offerPrice")
    private Integer offerPrice;

    @Lob
    @Column(name = "productImage")
    private byte[] productImage;


}
