package com.MainProject.MedE.Admin;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "admin_table")
public class AdminModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "admin_id")
    private Integer admin_id;

    @Column(name = "adminUserName")
    private String adminUserName;

    @Column(name = "password")
    private String password;

    public Integer getAdmin_id() {
        return admin_id;
    }

    public void setAdmin_id(Integer admin_id) {
        this.admin_id = admin_id;
    }

    public String getAdminUserName() {
        return adminUserName;
    }

    public void setAdminUserName(String adminUserName) {
        this.adminUserName = adminUserName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
