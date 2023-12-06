package com.example.appstudentmanagement;

import java.util.Map;

public class Student {
    private String photoUrl;
    private String code;
    private String name;
    private String studentClass;
    private String dateOfBirth;
    private String address;
    private String phone;
    private String detail;

    public Student() {
        // Default constructor required for calls to DataSnapshot.getValue(Student.class)
    }

    public Student(String photoUrl, String code, String name, String studentClass,String Birth, String address, String phone, String detail) {
        this.photoUrl = photoUrl;
        this.code = code;
        this.name = name;
        this.studentClass = studentClass;
        this.dateOfBirth = Birth;
        this.address = address;
        this.phone = phone;
        this.detail = detail;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStudentClass() {
        return studentClass;
    }

    public void setStudentClass(String studentClass) {
        this.studentClass = studentClass;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }
}
