package com.MainProject.MedE.Store;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.time.LocalDate;

@CrossOrigin
@Data
@Entity
@Table(name = "store_registration_table")
public class StoreRegistrationModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "store_id")
    private Integer store_id;

    @Column(name = "storeName")
    private String storeName;

    @Column(name = "licenseNumber")
    private String licenseNumber;

    @Column(name = "phone_number")
    private String phone_number;

    @Lob
    @Column(name = "license_image")
    private byte[] license_image;

    @Column(name = "password")
    private String password;

    @Column(name = "status_id")
    private Integer status_id;

    @Column(name = "created_at")
    private LocalDate created_at;

    @Column(name = "statusUpdate_at")
    private LocalDate statusUpdate_at;

    @Column(name = "longitude")
    private Double longitude;

    @Column(name = "latitude")
    private Double latitude;

    @Column(name = "address")
    private String address;

    public StoreRegistrationModel() {
        this.status_id = 1;
    }

    public Integer getStore_id() {
        return store_id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setStore_id(Integer store_id) {
        this.store_id = store_id;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getLicenseNumber() {
        return licenseNumber;
    }

    public void setLicenseNumber(String licenseNumber) {
        this.licenseNumber = licenseNumber;
    }

    public byte[] getLicense_image() {
        return license_image;
    }

    public void setLicense_image(byte[] license_image) {
        this.license_image = license_image;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
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

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }
}
