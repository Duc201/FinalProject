package com.example.urban_area_manager.feature.Admin.Home.Employee.Model;

import java.io.Serializable;
import java.util.Date;

public class Employee implements Serializable {
    private String id;
    private String fullName;
    private String address;
    private String nationality;
    private String indentityNumber;
    private String imageUrl;
    private String phoneNumber;
    private String email;
    private Date birth;
    private String pass;
    private int sex;
    // 0: Nam
    // 1 : Nu
    // 2: lgbt

    private int department;
    // 1 = Phòng An Ninh Giám Sát
    // 2 = Phòng Sửa Chữa Bảo Dưỡng
    // 3 = Phòng Quản lý Hành CHính
    // 4 = Admin
    private Date creationTime;
    private Date lastModificationTime;
    private String creatorUserId;
    private String lastModifierUserId;
    private String deleterUserId;
    Boolean isDelete;

    // dung cho chat
    private boolean state;


    public Employee(){
        this.id = "NULL";
        this.fullName = "NULL";
        this.address = "NULL";
        this.nationality = "VN";
        this.indentityNumber = "NULL";
        this.imageUrl = "https://t3.ftcdn.net/jpg/05/87/76/66/360_F_587766653_PkBNyGx7mQh9l1XXPtCAq1lBgOsLl6xH.jpg";
        this.phoneNumber = "NULL";
        this.email = "NULL";
        this.birth = new Date();
        this.department = 1;
        this.creationTime = new Date();
        this.lastModificationTime = new Date();
        this.creatorUserId = "NULL";
        this.lastModifierUserId = "NULL";
        this.deleterUserId = "NULL";
        this.pass = "NULL";
        this.state = true;
        this.sex = 0;
        this.isDelete = false;
    }

    public Employee(String id, String fullName, String address, String nationality, String indentityNumber, String imageUrl, String phoneNumber, String email, Date birth, String pass, int sex, int department, Date creationTime, Date lastModificationTime, String creatorUserId, String lastModifierUserId, String deleterUserId, Boolean isDelete, boolean state) {
        this.id = id;
        this.fullName = fullName;
        this.address = address;
        this.nationality = nationality;
        this.indentityNumber = indentityNumber;
        this.imageUrl = imageUrl;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.birth = birth;
        this.pass = pass;
        this.sex = sex;
        this.department = department;
        this.creationTime = creationTime;
        this.lastModificationTime = lastModificationTime;
        this.creatorUserId = creatorUserId;
        this.lastModifierUserId = lastModifierUserId;
        this.deleterUserId = deleterUserId;
        this.isDelete = isDelete;
        this.state = state;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getIndentityNumber() {
        return indentityNumber;
    }

    public void setIndentityNumber(String indentityNumber) {
        this.indentityNumber = indentityNumber;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getBirth() {
        return birth;
    }

    public void setBirth(Date birth) {
        this.birth = birth;
    }


    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public int getDepartment() {
        return department;
    }

    public void setDepartment(int department) {
        this.department = department;
    }

    public Date getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(Date creationTime) {
        this.creationTime = creationTime;
    }

    public Date getLastModificationTime() {
        return lastModificationTime;
    }

    public void setLastModificationTime(Date lastModificationTime) {
        this.lastModificationTime = lastModificationTime;
    }



    public String getCreatorUserId() {
        return creatorUserId;
    }

    public void setCreatorUserId(String creatorUserId) {
        this.creatorUserId = creatorUserId;
    }

    public String getLastModifierUserId() {
        return lastModifierUserId;
    }

    public void setLastModifierUserId(String lastModifierUserId) {
        this.lastModifierUserId = lastModifierUserId;
    }

    public String getDeleterUserId() {
        return deleterUserId;
    }

    public void setDeleterUserId(String deleterUserId) {
        this.deleterUserId = deleterUserId;
    }

    public Boolean getDelete() {
        return isDelete;
    }

    public void setDelete(Boolean delete) {
        isDelete = delete;
    }

    public boolean isState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }
}
