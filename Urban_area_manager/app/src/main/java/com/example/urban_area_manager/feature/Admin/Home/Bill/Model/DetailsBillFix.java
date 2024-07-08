package com.example.urban_area_manager.feature.Admin.Home.Bill.Model;

import java.util.Date;

public class DetailsBillFix {
    private String id;
    private String idBill;
    private String idService;
    private int month;
    private int year;
    private double sumDetailBill;

    private Boolean isDeleted;
    private Date creationTime;
    private Date lastModifiTime;
    private Date deletionTime;

    private String creatorUserId;
    private String lastModifierUserId;
    private String deleterUserId;

    //thêm
    private String idBuilding;
    private String idApartment;
    private String nameApartment;
    private String namService;
    private Double count;
    private long price;
    private int type;
    public DetailsBillFix() {
        this.id = null;
        this.idBill = null;
        this.idService = null;
        this.month = 0;
        this.year = 0;
        this.sumDetailBill = 0;
        this.isDeleted = false;
        this.creationTime = null;
        this.lastModifiTime = null;
        this.deletionTime = null;
        this.creatorUserId = null;
        this.lastModifierUserId = null;
        this.deleterUserId = null;
        this.idBuilding = null;
        this.idApartment = null;
        this.nameApartment = null;
        this.namService = null;
        this.count = 0.0;
        this.price = 0;
        this.type = 0;
    }

    public DetailsBillFix(String id, String idBill, String idService, int month, int year, boolean isMonthly, double sumDetailBill, Boolean isDeleted, Date creationTime, Date lastModifiTime, Date deletionTime, String creatorUserId, String lastModifierUserId, String deleterUserId, String idBuilding, String idApartment, String nameApartment, String namService, Double count, long price, int type) {
        this.id = id;
        this.idBill = idBill;
        this.idService = idService;
        this.month = month;
        this.year = year;
        this.sumDetailBill = sumDetailBill;
        this.isDeleted = isDeleted;
        this.creationTime = creationTime;
        this.lastModifiTime = lastModifiTime;
        this.deletionTime = deletionTime;
        this.creatorUserId = creatorUserId;
        this.lastModifierUserId = lastModifierUserId;
        this.deleterUserId = deleterUserId;
        this.idBuilding = idBuilding;
        this.idApartment = idApartment;
        this.nameApartment = nameApartment;
        this.namService = namService;
        this.count = count;
        this.price = price;
        this.type = type;
    }

    public Double getCount() {
        return count;
    }

    public void setCount(Double count) {
        this.count = count;
    }

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdBill() {
        return idBill;
    }

    public void setIdBill(String idBill) {
        this.idBill = idBill;
    }

    public String getIdService() {
        return idService;
    }

    public void setIdService(String idService) {
        this.idService = idService;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }



    public double getSumDetailBill() {
        return sumDetailBill;
    }

    public void setSumDetailBill(double sumDetailBill) {
        this.sumDetailBill = sumDetailBill;
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

    public String getNameApartment() {
        return nameApartment;
    }

    public void setNameApartment(String nameApartment) {
        this.nameApartment = nameApartment;
    }

    public String getNamService() {
        return namService;
    }

    public void setNamService(String namService) {
        this.namService = namService;
    }



    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
