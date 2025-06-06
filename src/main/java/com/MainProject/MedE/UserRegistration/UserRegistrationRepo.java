package com.MainProject.MedE.UserRegistration;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRegistrationRepo extends JpaRepository<UserRegistrationModel,Integer> {
    Optional<UserRegistrationModel> findByEmailAndPassword(String email, String password);

    Optional<UserRegistrationModel> findByEmail(String email);


    Optional<UserRegistrationModel> findByPhoneNumber(Integer phoneNumber);

    @Query("SELECT p FROM UserRegistrationModel p WHERE p.user_id = :userId")
    Optional<UserRegistrationModel> findByUserId(@Param("userId")Integer userId);


}
