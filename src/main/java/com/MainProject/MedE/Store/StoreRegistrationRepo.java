package com.MainProject.MedE.Store;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StoreRegistrationRepo extends JpaRepository<StoreRegistrationModel,Integer> {


    Optional<StoreRegistrationModel> findByLicenseNumberAndPassword(String licenseNumber, String password);

    List<StoreRegistrationModel> findByStoreNameContainingIgnoreCase(String storeName);
}
