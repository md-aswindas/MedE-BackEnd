package com.MainProject.MedE.UserRegistration;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartItemRepo extends JpaRepository<CartItem,Long> {

    List<CartItem> findByProductId(long l);
}
