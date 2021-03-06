package com.woliur.assignment;

public class User {
    String image;
    String name;
    String address;
    String email;
    String phone;
    String gender;

    public User(String image, String name, String address, String email, String phone, String gender) {
        this.image = image;
        this.name = name;
        this.address = address;
        this.email = email;
        this.phone = phone;
        this.gender = gender;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}
