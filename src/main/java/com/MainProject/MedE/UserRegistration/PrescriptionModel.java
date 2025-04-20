package com.MainProject.MedE.UserRegistration;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin
@Entity
@Data
@Table(name = "prescriptionTable")
public class PrescriptionModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "prescriptionId")
    private Integer prescriptionId;

    @Column(name = "user_id")
    private Integer user_id;

    @Column(name = "storeId")
    private Integer storeId;

    @Lob
    @Column(name = "prescriptionImage")
    private byte[] prescriptionImage;

    @Column(name = "status")  // Status like "Pending", "Accepted", "Rejected"
    private String status;

    @Column(name = "rejectionReason")  // Reason for rejection if any
    private String rejectionReason;


    public Integer getPrescriptionId() {
        return prescriptionId;
    }

    public void setPrescriptionId(Integer prescriptionId) {
        this.prescriptionId = prescriptionId;
    }

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public byte[] getPrescriptionImage() {
        return prescriptionImage;
    }

    public void setPrescriptionImage(byte[] prescriptionImage) {
        this.prescriptionImage = prescriptionImage;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRejectionReason() {
        return rejectionReason;
    }

    public void setRejectionReason(String rejectionReason) {
        this.rejectionReason = rejectionReason;
    }

    public Integer getStoreId() {
        return storeId;
    }

    public void setStoreId(Integer storeId) {
        this.storeId = storeId;
    }
}
