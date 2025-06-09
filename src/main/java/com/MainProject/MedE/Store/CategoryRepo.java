package com.MainProject.MedE.Store;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoryRepo extends JpaRepository<CategoryModel,Integer> {
    Optional<CategoryModel> findByCategoryNameIgnoreCase(String categoryName);
}
