package com.MainProject.MedE.Store;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "store_registration_table")
public class StoreRegistrationModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "store_id")
    private Integer store_id;

    @Column(name = "store_name")
    private String store_name;

    @Column(name = "license_number")
    private String license_number;

//    @Column(name = "license_image")
//    private String license_image;

    @Column(name = "password")
    private String password;

    @Column(name = "status_id")
    private Integer status_id;

    @Column(name = "created_at")
    private LocalDate created_at;

    @Column(name = "statusUpdate_at")
    private LocalDate statusUpdate_at;

    public StoreRegistrationModel() {
        this.status_id = 1;
    }

    public Integer getStore_id() {
        return store_id;
    }

    public void setStore_id(Integer store_id) {
        this.store_id = store_id;
    }

    public String getStore_name() {
        return store_name;
    }

    public void setStore_name(String store_name) {
        this.store_name = store_name;
    }

    public String getLicense_number() {
        return license_number;
    }

    public void setLicense_number(String license_number) {
        this.license_number = license_number;
    }

//    public String getLicense_image() {
//        return license_image;
//    }
//
//    public void setLicense_image(String license_image) {
//        this.license_image = license_image;



    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getStatus_id() {
        return status_id;
    }

    public void setStatus_id(Integer status_id) {
        this.status_id = status_id;
    }

    public LocalDate getCreated_at() {
        return created_at;
    }

    public void setCreated_at(LocalDate created_at) {
        this.created_at = created_at;
    }

    public LocalDate getStatusUpdate_at() {
        return statusUpdate_at;
    }

    public void setStatusUpdate_at(LocalDate statusUpdate_at) {
        this.statusUpdate_at = statusUpdate_at;
    }
}
