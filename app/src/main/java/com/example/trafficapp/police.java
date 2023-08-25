package com.example.trafficapp;

public class police {
    String email,name,city,phone,department,district,stationId;

    public police() {
    }

    public police(String email, String name, String city, String phone, String department, String district, String stationId) {
        this.email = email;
        this.name = name;
        this.city = city;
        this.phone = phone;
        this.department = department;
        this.district = district;
        this.stationId = stationId;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public void setStationId(String stationId) {
        this.stationId = stationId;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public String getCity() {
        return city;
    }

    public String getPhone() {
        return phone;
    }

    public String getDepartment() {
        return department;
    }

    public String getDistrict() {
        return district;
    }

    public String getStationId() {
        return stationId;
    }

}
