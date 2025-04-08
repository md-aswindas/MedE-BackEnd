package com.MainProject.MedE.Store;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AdsRepo extends JpaRepository<AdsModel,Integer> {
    List<AdsModel> findAllByStoreId(Integer storeId);
}
