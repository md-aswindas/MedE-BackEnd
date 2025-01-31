package com.MainProject.MedE.Store;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "status_table")
public class StatusModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "status_id")
    private Integer status_id;

    @Column(name = "status_name")
    private String status_name;

    public Integer getStatus_id() {
        return status_id;
    }

    public void setStatus_id(Integer status_id) {
        this.status_id = status_id;
    }

    public String getStatus_name() {
        return status_name;
    }

    public void setStatus_name(String status_name) {
        this.status_name = status_name;
    }
}
