package com.MainProject.MedE.UserRegistration;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartRepo extends JpaRepository<CartModel, Long> {
    Optional<CartModel> findByUserId(Long userId);

}
