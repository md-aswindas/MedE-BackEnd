package com.MainProject.MedE.UserRegistration;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PrescriptionRepo extends JpaRepository<PrescriptionModel,Integer> {
    List<PrescriptionModel> findByStoreId(Integer storeId);
}
