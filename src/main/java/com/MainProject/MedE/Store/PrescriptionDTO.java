package com.MainProject.MedE.Store;

import lombok.Data;
import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin
@Data
public class PrescriptionDTO {
    private String userName;
    private Integer userId;
    private String email;
    private String phoneNumber;
    private Integer prescriptionId;
    private byte[] prescriptionImage;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getUserId() {
        return userId;
    }

    public Integer getPrescriptionId() {
        return prescriptionId;
    }

    public void setPrescriptionId(Integer prescriptionId) {
        this.prescriptionId = prescriptionId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public byte[] getPrescriptionImage() {
        return prescriptionImage;
    }

    public void setPrescriptionImage(byte[] prescriptionImage) {
        this.prescriptionImage = prescriptionImage;
    }
}
