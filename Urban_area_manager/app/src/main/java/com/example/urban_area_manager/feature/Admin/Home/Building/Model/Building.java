package com.example.urban_area_manager.feature.Admin.Home.Building.Model;

import java.io.Serializable;
import java.util.Date;

public class Building implements Serializable {
    String idBuilding;
    String nameBuilding;
    double area;
    int floorNumber;
    String description;
    String imageUrl;
    Boolean isDeleted;

    Date creationTime;
    Date lastModificationTime;
    Date deletionTime;

    String creatorUserId;
    String lastModifierUserId;
    String deleterUserId;


    public Building() {
        this.idBuilding = "Electric";
        this.nameBuilding = "Tòa A";
        this.area = 180;
        this.floorNumber = 67;
        this.description = "Tòa nhà chỉ dành cho hộ thu nhập thấp";
        this.imageUrl = "https://media-cdn-v2.laodong.vn/Storage/NewsPortal/2019/12/23/774000/DSC06822.jpg";
        this.isDeleted = false;
        this.creationTime = new Date();
        this.lastModificationTime = new Date();
        this.deletionTime = null;
        this.creatorUserId = "ID_1";
        this.lastModifierUserId = "ID_1";
        this.deleterUserId = "ID_1";
    }

    public Building(String idBuilding, String nameBuilding, double area, int floorNumber, String description, String imageUrl, Boolean isDeleted, Date creationTime, Date lastModificationTime, Date deletionTime, String creatorUserId, String lastModifierUserId, String deleterUserId) {
        this.idBuilding = idBuilding;
        this.nameBuilding = nameBuilding;
        this.area = area;
        this.floorNumber = floorNumber;
        this.description = description;
        this.imageUrl = imageUrl;
        this.isDeleted = isDeleted;
        this.creationTime = creationTime;
        this.lastModificationTime = lastModificationTime;
        this.deletionTime = deletionTime;
        this.creatorUserId = creatorUserId;
        this.lastModifierUserId = lastModifierUserId;
        this.deleterUserId = deleterUserId;
    }

    public String getIdBuilding() {
        return idBuilding;
    }

    public void setIdBuilding(String idBuilding) {
        this.idBuilding = idBuilding;
    }

    public String getNameBuilding() {
        return nameBuilding;
    }

    public void setNameBuilding(String nameBuilding) {
        this.nameBuilding = nameBuilding;
    }

    public double getArea() {
        return area;
    }

    public void setArea(double area) {
        this.area = area;
    }

    public int getFloorNumber() {
        return floorNumber;
    }

    public void setFloorNumber(int floorNumber) {
        this.floorNumber = floorNumber;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
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
