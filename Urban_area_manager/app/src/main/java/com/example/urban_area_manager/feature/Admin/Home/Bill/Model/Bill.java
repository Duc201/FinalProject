package com.example.urban_area_manager.feature.Admin.Home.Bill.Model;

import java.io.Serializable;
import java.util.Date;

public class Bill implements Serializable {
    private String idBill;
    private String nameBill;
    private long sumBill;
    private Date endBill;
    private Boolean isPay;
    private Boolean isNotifi;
    private String idApartment;
    private String idPayResident;

    private Boolean isDeleted;

    private Date creationTime;
    private Date lastModifiTime;
    private Date payBillTime;
    private Date deletionTime;

    private String creatorUserId;
    private String lastModifierUserId;
    private String deleterUserId;
    //
    private String idBuilding;
    private String nameApartment;
    private int year;
    private int month;



    public Bill(){
        this.idBill = "null";
        this.nameBill = "null";
        this.sumBill = 0;
        this.endBill = new Date();
        this.isPay = false;
        this.isNotifi = false;
        this.idApartment = "null";
        this.isDeleted = false;
        this.creationTime = new Date();
        this.lastModifiTime = new Date();
        this.payBillTime = null;
        this.deletionTime = null;
        this.creatorUserId = "null";
        this.lastModifierUserId = "null";
        this.deleterUserId = "null";
        this.idBuilding = "null";
        this.nameApartment = "null";
        this.year = 0;
        this.month = 0;
        this.idPayResident = "null";
    }


    public Bill(String idBill, String nameBill, long sumBill, Date endBill, Boolean isPay, Boolean isNotifi, String idApartment, String idPayResident, Boolean isDeleted, Date creationTime, Date lastModifiTime, Date payBillTime, Date deletionTime, String creatorUserId, String lastModifierUserId, String deleterUserId, String idBuilding, String nameApartment, int year, int month) {
        this.idBill = idBill;
        this.nameBill = nameBill;
        this.sumBill = sumBill;
        this.endBill = endBill;
        this.isPay = isPay;
        this.isNotifi = isNotifi;
        this.idApartment = idApartment;
        this.idPayResident = idPayResident;
        this.isDeleted = isDeleted;
        this.creationTime = creationTime;
        this.lastModifiTime = lastModifiTime;
        this.payBillTime = payBillTime;
        this.deletionTime = deletionTime;
        this.creatorUserId = creatorUserId;
        this.lastModifierUserId = lastModifierUserId;
        this.deleterUserId = deleterUserId;
        this.idBuilding = idBuilding;
        this.nameApartment = nameApartment;
        this.year = year;
        this.month = month;
    }

    public String getIdPayResident() {
        return idPayResident;
    }

    public void setIdPayResident(String idPayResident) {
        this.idPayResident = idPayResident;
    }

    public Date getPayBillTime() {
        return payBillTime;
    }

    public void setPayBillTime(Date payBillTime) {
        this.payBillTime = payBillTime;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public String getNameApartment() {
        return nameApartment;
    }

    public void setNameApartment(String nameApartment) {
        this.nameApartment = nameApartment;
    }

    public String getIdBuilding() {
        return idBuilding;
    }

    public void setIdBuilding(String idBuilding) {
        this.idBuilding = idBuilding;
    }

    public String getIdBill() {
        return idBill;
    }

    public void setIdBill(String idBill) {
        this.idBill = idBill;
    }

    public String getNameBill() {
        return nameBill;
    }

    public void setNameBill(String nameBill) {
        this.nameBill = nameBill;
    }

    public long getSumBill() {
        return sumBill;
    }

    public void setSumBill(long sumBill) {
        this.sumBill = sumBill;
    }

    public Date getEndBill() {
        return endBill;
    }

    public void setEndBill(Date endBill) {
        this.endBill = endBill;
    }

    public Boolean getPay() {
        return isPay;
    }

    public void setPay(Boolean pay) {
        isPay = pay;
    }

    public Boolean getNotifi() {
        return isNotifi;
    }

    public void setNotifi(Boolean notifi) {
        isNotifi = notifi;
    }

    public String getIdApartment() {
        return idApartment;
    }

    public void setIdApartment(String idApartment) {
        this.idApartment = idApartment;
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

    public Date getLastModifiTime() {
        return lastModifiTime;
    }

    public void setLastModifiTime(Date lastModifiTime) {
        this.lastModifiTime = lastModifiTime;
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
