package com.MainProject.MedE.UserRegistration;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRegistrationRepo extends JpaRepository<UserRegistrationModel,Integer> {
    Optional<UserRegistrationModel> findByEmailAndPassword(String email, String password);

    Optional<UserRegistrationModel> findByEmail(String email);


    Optional<UserRegistrationModel> findByPhoneNumber(Integer phoneNumber);
}
