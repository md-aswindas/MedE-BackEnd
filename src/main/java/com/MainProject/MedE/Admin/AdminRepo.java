package com.MainProject.MedE.Admin;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AdminRepo extends JpaRepository<AdminModel,Integer> {



    Optional<AdminModel> findByAdminUserNameAndPassword(String adminUserName, String password);
}
