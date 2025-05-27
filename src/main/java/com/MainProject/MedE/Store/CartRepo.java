package com.MainProject.MedE.Store;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface CartRepo extends JpaRepository<CartModel, Long> {
    Optional<CartModel> findByUserId(Long userId);

}
