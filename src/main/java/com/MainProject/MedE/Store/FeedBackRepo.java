package com.MainProject.MedE.Store;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface FeedBackRepo extends JpaRepository<FeedBackModel,Integer> {

    @Query("SELECT f FROM FeedBackModel f WHERE f.user_id = :userId AND f.Store_id = :storeId")
    Optional<FeedBackModel> findByUserAndStore(@Param("userId") Integer userId, @Param("storeId") Integer storeId);

    @Query("SELECT f FROM FeedBackModel f WHERE f.Store_id = :storeId")
    List<FeedBackModel> findByStore(@Param("storeId") Integer storeId);

    @Query("SELECT AVG(f.Rating) FROM FeedBackModel f WHERE f.Store_id = :storeId")
    Double findAverageRatingByStoreId(@Param("storeId") Integer storeId);

}
