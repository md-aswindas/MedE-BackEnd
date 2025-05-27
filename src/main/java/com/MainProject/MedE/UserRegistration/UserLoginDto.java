package com.MainProject.MedE.UserRegistration;

import lombok.Data;
import lombok.Getter;
import org.springframework.web.bind.annotation.CrossOrigin;

@Getter
@CrossOrigin
@Data
public class UserLoginDto {

    private String email;
    private String password;
    private Integer user_id;
    private String userName;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }
}
