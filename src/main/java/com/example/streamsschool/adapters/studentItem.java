package com.example.streamsschool.adapters;

public class studentItem {
    String roll, name, email, phone, address, present;

    public studentItem(String roll, String name, String email, String phone, String address, String present) {
        this.roll = roll;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.present  = present;
    }

    public String getRoll() {
        return roll;
    }

    public void setRoll(String roll) {
        this.roll = roll;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPresent() {
        return present;
    }

    public void setPresent(String present) {
        this.present = present;
    }
}
