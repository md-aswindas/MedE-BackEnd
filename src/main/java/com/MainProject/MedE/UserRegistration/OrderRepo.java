package com.MainProject.MedE.UserRegistration;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepo extends JpaRepository<OrderModel,Long> {
    List<OrderModel> findByUserId(Long userId);

    List<OrderModel> findByStoreId(Long storeId);

    List<OrderModel> findByStatus(Long status);

    List<OrderModel> findByStoreIdAndStatus(int i, Long status);
}
