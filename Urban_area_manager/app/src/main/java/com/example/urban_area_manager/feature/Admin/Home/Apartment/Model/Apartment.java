package com.example.urban_area_manager.feature.Admin.Home.Apartment.Model;

import java.io.Serializable;
import java.util.Date;

public class Apartment implements Serializable {
    String idBuilding;
    String idApartment;
    String name;
    int floor;
    Double area;
    int state; // 1 chưa mua, 0 đã mua
    String imagePath;
    String description;
    Boolean isDeleted;

    Date creationTime;
    Date lastModificationTime;
    Date deletionTime;


    String creatorUserId;
    String lastModifierUserId;
    String deleterUserId;

    public Apartment(){
        this.idBuilding = "A1";
        this.idApartment = "201";
        this.name = "Phòng 204";
        this.floor = 5;
        this.area = 120.0;
        this.state = 1;
        this.imagePath = "https://png.pngtree.com/background/20230516/original/pngtree-an-empty-apartment-with-large-windows-in-the-middle-of-the-picture-image_2610981.jpg";
        this.description = "Chưa có mô tả";
        this.isDeleted = false;
        this.creationTime = new Date();
        this.lastModificationTime = new Date();
        this.deletionTime = new Date();
        this.creatorUserId = "123";
        this.lastModifierUserId = "rrt";
        this.deleterUserId = "ded";
    }

    public Apartment(String codeBuilding, String codeApartment, String name, int floor, Double area, int status, String imagePath, String description, Boolean isDeleted, Date creationTime, Date lastModificationTime, Date deletionTime, String creatorUserId, String lastModifierUserId, String deleterUserId) {
        this.idBuilding = codeBuilding;
        this.idApartment = codeApartment;
        this.name = name;
        this.floor = floor;
        this.area = area;
        this.state = status;
        this.imagePath = imagePath;
        this.description = description;
        this.isDeleted = isDeleted;
        this.creationTime = creationTime;
        this.lastModificationTime = lastModificationTime;
        this.deletionTime = deletionTime;
        this.creatorUserId = creatorUserId;
        this.lastModifierUserId = lastModifierUserId;
        this.deleterUserId = deleterUserId;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getIdBuilding() {
        return idBuilding;
    }

    public void setIdBuilding(String idBuilding) {
        this.idBuilding = idBuilding;
    }

    public String getIdApartment() {
        return idApartment;
    }

    public void setIdApartment(String idApartment) {
        this.idApartment = idApartment;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getFloor() {
        return floor;
    }

    public void setFloor(int floor) {
        this.floor = floor;
    }


    public Double getArea() {
        return area;
    }

    public void setArea(Double area) {
        this.area = area;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getDeleted() {
        return isDeleted;
    }

    public void setDeleted(Boolean deleted) {
        isDeleted = deleted;
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

    public Date getDeletionTime() {
        return deletionTime;
    }

    public void setDeletionTime(Date deletionTime) {
        this.deletionTime = deletionTime;
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
