package com.MainProject.MedE.UserRegistration;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PrescriptionRepo extends JpaRepository<PrescriptionModel,Integer> {
    List<PrescriptionModel> findByStoreId(Integer storeId);

    @Query("SELECT p FROM PrescriptionModel p WHERE p.user_id = :userId")
    List<PrescriptionModel> findByUserId(@Param("userId") Long userId);
}
