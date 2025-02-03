package com.MainProject.MedE.Store;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepo extends JpaRepository<ProductModel,Integer> {
}
