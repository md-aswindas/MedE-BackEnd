package com.MainProject.MedE.Store;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductRepo extends JpaRepository<ProductModel,Integer> {

    List<ProductModel> findAllByStoreId(Integer storeId);

    List<ProductModel> findByProductNameContainingIgnoreCase(String productName);


    Optional<byte[]> findImageByProductId(Integer productId);

    Optional<ProductModel> findByProductName(String productName);

    List<ProductModel> findByStoreIdAndProductNameContainingIgnoreCase(Integer storeId, String productName);
}
