package com.example.final_instagram;

public class Helper {
    String password,phone;
    Helper(){
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Helper(String password, String phone) {
        this.password = password;
        this.phone = phone;
    }
}
