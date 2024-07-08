package com.example.urban_area_manager.feature.Admin.Home.Resident.Model;

import java.io.Serializable;
import java.util.Date;

public class Resident implements Serializable {
    private String id;
    private String indentifyNumber;
    private String fullName;
    private String address;
    private String nationality;
    private String imageUrl;
    private String imgIdentityCardFront;
    private String imgIndentityCardBehind;
    private String phoneNumber;
    private String email;
    private Date birth;
    private int state;
    // state = 0 chưa xác minh
    // state = 1 xác mỉnh e
    private int status;
    private int sex;
    //0: nam
    //1: nu
    //2: lgbt

    private Date creationTime;
    private Date lastModificationTime;
    private Date deleteTime;
    Boolean isDelete;

    private String creatorUserId;
    private String lastModifierUserId;
    private String deleterUserId;


    public Resident(String id, String indentityNumber, String fullName, String address, String nationality, String imageUrl, String imgIdentityCardFront, String imgIndentityCardBehind, String phoneNumber, String email, Date birth, int state, int status, int sex, Date creationTime, Date lastModificationTime, Date deleteTime, Boolean isDelete, String creatorUserId, String lastModifierUserId, String deleterUserId) {
        this.id = id;
        this.indentifyNumber = indentityNumber;
        this.fullName = fullName;
        this.address = address;
        this.nationality = nationality;
        this.imageUrl = imageUrl;
        this.imgIdentityCardFront = imgIdentityCardFront;
        this.imgIndentityCardBehind = imgIndentityCardBehind;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.birth = birth;
        this.state = state;
        this.status = status;
        this.sex = sex;
        this.creationTime = creationTime;
        this.lastModificationTime = lastModificationTime;
        this.deleteTime = deleteTime;
        this.isDelete = false;
        this.creatorUserId = creatorUserId;
        this.lastModifierUserId = lastModifierUserId;
        this.deleterUserId = deleterUserId;
    }

    public Resident() {
        this.id = "null";
        this.indentifyNumber = "null";
        this.fullName = "null";
        this.address = "null";
        this.nationality = "VN";
        this.imgIdentityCardFront = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSUGF8qp5w_zjiT0jJ_d67xGy_GHuvF26wh-A&s";
        this.imgIndentityCardBehind = "https://lh5.googleusercontent.com/proxy/Dd31f1Ah5DIv9EtTVQXGizIHoyAO4SGPa1KuANScO_QJM-Ghkq3fdL-fqbkgHKK3fghoZa6haKOZbI3SDUAz372sutPGKvyG84J997gNnPVemJ9OWzLwrtP_G_g_";
        this.phoneNumber = "null";
        this.email = "null";
        this.birth = new Date();
        this.state = 0;
        this.status = 0;
        this.sex = 0;

        this.isDelete = false;
        this.creationTime = new Date();
        this.lastModificationTime = new Date();
        this.deleteTime = new Date();
        this.creatorUserId = "null";
        this.lastModifierUserId = "null";
        this.deleterUserId = "null";
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIndentityNumber() {
        return indentifyNumber;
    }

    public void setIndentityNumber(String indentityNumber) {
        this.indentifyNumber = indentityNumber;
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

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getImgIdentityCardFront() {
        return imgIdentityCardFront;
    }

    public void setImgIdentityCardFront(String imgIdentityCardFront) {
        this.imgIdentityCardFront = imgIdentityCardFront;
    }

    public String getImgIndentityCardBehind() {
        return imgIndentityCardBehind;
    }

    public void setImgIndentityCardBehind(String imgIndentityCardBehind) {
        this.imgIndentityCardBehind = imgIndentityCardBehind;
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

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
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

    public Date getDeleteTime() {
        return deleteTime;
    }

    public void setDeleteTime(Date deleteTime) {
        this.deleteTime = deleteTime;
    }

    public Boolean getDelete() {
        return isDelete;
    }

    public void setDelete(Boolean delete) {
        isDelete = delete;
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
}
