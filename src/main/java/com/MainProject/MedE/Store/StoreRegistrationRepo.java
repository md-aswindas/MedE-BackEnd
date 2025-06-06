package com.MainProject.MedE.Store;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface StoreRegistrationRepo extends JpaRepository<StoreRegistrationModel,Integer> {


    Optional<StoreRegistrationModel> findByLicenseNumberAndPassword(String licenseNumber, String password);

    List<StoreRegistrationModel> findByStoreNameContainingIgnoreCase(String storeName);

    @Query("SELECT s FROM StoreRegistrationModel s WHERE s.status_id = :statusId")
    List<StoreRegistrationModel> findByStatusId(@Param("statusId") Integer statusId);
}
