package com.example.urban_area_manager.feature.Admin.Home.Resident.Model;

import java.io.Serializable;
import java.util.Date;

public class DetailsResident implements Serializable {

    private String id;
    private String apartmentCode;
    private String buildingCode;
    private String apartmentName;
    private String buildingName;
    private int relationShip;
    private int state;
    private String imageContract;

    private Date creationTime;
    private Date lastModificationTime;
    private Date deleteTime;
    Boolean isDelete;

    private String creatorUserId;
    private String lastModifierUserId;
    private String deleterUserId;


    /*
     * 0 - Chủ hộ
     * 1 - Chồng
     * 2- Vợ
     * 3 - Con trai
     * 4- Con gái
     * 5 - Bố Vợ
     * 6 - Mẹ Vợ
     * 7 - Anh
     * 8 - Em
     * 9 - Chị
     * */
    private String idUser;
    private String nameUser;
    public DetailsResident(){
        this.id = "null";
        this.apartmentCode = "null";
        this.buildingCode = "null";
        this.apartmentName = "null";
        this.buildingName = "null";
        this.relationShip = 9;
        this.idUser = "null";
        this.nameUser = "null";
        this.imageContract = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcT5snNfIynotQPTtPh3EK88dbf0ZWfgnjI1uA&s";
        this.isDelete = false;
        this.creationTime = new Date();
        this.lastModificationTime = new Date();
        this.deleteTime = null;
        this.creatorUserId = "null";
        this.lastModifierUserId = "null";
        this.deleterUserId = "null";
        this.state = 0;
    }

    public DetailsResident(String id, String apartmentCode, String buildingCode, String apartmentName, String buildingName, int relationShip, int state, String imageContract, Date creationTime, Date lastModificationTime, Date deleteTime, Boolean isDelete, String creatorUserId, String lastModifierUserId, String deleterUserId, String idUser, String nameUser) {
        this.id = id;
        this.apartmentCode = apartmentCode;
        this.buildingCode = buildingCode;
        this.apartmentName = apartmentName;
        this.buildingName = buildingName;
        this.relationShip = relationShip;
        this.state = state;
        this.imageContract = imageContract;
        this.creationTime = creationTime;
        this.lastModificationTime = lastModificationTime;
        this.deleteTime = deleteTime;
        this.isDelete = isDelete;
        this.creatorUserId = creatorUserId;
        this.lastModifierUserId = lastModifierUserId;
        this.deleterUserId = deleterUserId;
        this.idUser = idUser;
        this.nameUser = nameUser;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getApartmentCode() {
        return apartmentCode;
    }

    public void setApartmentCode(String apartmentCode) {
        this.apartmentCode = apartmentCode;
    }

    public String getBuildingCode() {
        return buildingCode;
    }

    public void setBuildingCode(String buildingCode) {
        this.buildingCode = buildingCode;
    }

    public String getApartmentName() {
        return apartmentName;
    }

    public void setApartmentName(String apartmentName) {
        this.apartmentName = apartmentName;
    }

    public String getBuildingName() {
        return buildingName;
    }

    public void setBuildingName(String buildingName) {
        this.buildingName = buildingName;
    }

    public int getRelationShip() {
        return relationShip;
    }

    public void setRelationShip(int relationShip) {
        this.relationShip = relationShip;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getImageContract() {
        return imageContract;
    }

    public void setImageContract(String imageContract) {
        this.imageContract = imageContract;
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

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public String getNameUser() {
        return nameUser;
    }

    public void setNameUser(String nameUser) {
        this.nameUser = nameUser;
    }
}
