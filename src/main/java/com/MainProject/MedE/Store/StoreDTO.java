package com.MainProject.MedE.Store;


import lombok.Data;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.time.LocalDate;

@CrossOrigin
@Data
public class StoreDTO {

    private Integer storeId;

    private String storeName;

    private String licenseNumber;

//    private String licenseImage;

    private String storePassword;

    private Integer statusId;

    private String statusName;

    private LocalDate registrationDate;

    private LocalDate statusUpdateDate;

    private String phoneNumber;

    private String address;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getStorePassword() {
        return storePassword;
    }



    public Integer getStoreId() {
        return storeId;
    }

    public void setStoreId(Integer storeId) {
        this.storeId = storeId;
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

//    public String getLicenseImage() {
//        return licenseImage;
//    }
//
//    public void setLicenseImage(String licenseImage) {
//        this.licenseImage = licenseImage;
//    }

    public Integer getStatusId() {
        return statusId;
    }

    public void setStatusId(Integer statusId) {
        this.statusId = statusId;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public LocalDate getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(LocalDate registrationDate) {
        this.registrationDate = registrationDate;
    }

    public LocalDate getStatusUpdateDate() {
        return statusUpdateDate;
    }

    public void setStatusUpdateDate(LocalDate statusUpdateDate) {
        this.statusUpdateDate = statusUpdateDate;
    }

    public String getStorePassword(String password) {
        return storePassword;
    }

    public void setStorePassword(String storePassword) {
        this.storePassword = storePassword;
    }
}
