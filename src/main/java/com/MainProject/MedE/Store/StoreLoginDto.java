package com.MainProject.MedE.Store;

import lombok.Data;
import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin
@Data
public class StoreLoginDto {
    private String licenseNumber;
    private String password;

    public String getLicenseNumber() {
        return licenseNumber;
    }

    public void setLicenseNumber(String licenseNumber) {
        this.licenseNumber = licenseNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
