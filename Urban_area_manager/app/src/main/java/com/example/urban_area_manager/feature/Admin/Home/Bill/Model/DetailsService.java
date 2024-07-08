package com.example.urban_area_manager.feature.Admin.Home.Bill.Model;

import java.io.Serializable;
import java.util.Date;

public class DetailsService implements Serializable {
    private String id;
    private String idService;
    private String idApartment;
    private String nameService;
    private Date timeCreat;
    private Date timeDelete;
    private Boolean isPay;
    private Boolean isDelete;
    private long price;
    private double quality;
    public DetailsService() {
        this.id = "null";
        this.idService = "null";
        this.idApartment = "null";
        this.nameService = "null";
        this.timeCreat = new Date();
        this.timeDelete = null;
        this.isPay = null;
        this.isDelete = false;
        this.price = 0;
        this.quality = 0;
    }
    public DetailsService(String id, String idService, String idApartment, String nameService, Date timeCreat, Date timeDelete, Boolean isPay, Boolean isDelete, long price, float quality) {
        this.id = id;
        this.idService = idService;
        this.idApartment = idApartment;
        this.nameService = nameService;
        this.timeCreat = timeCreat;
        this.timeDelete = timeDelete;
        this.isPay = isPay;
        this.isDelete = isDelete;
        this.price = price;
        this.quality = quality;
    }

    public Date getTimeCreat() {
        return timeCreat;
    }

    public void setTimeCreat(Date timeCreat) {
        this.timeCreat = timeCreat;
    }

    public Date getTimeDelete() {
        return timeDelete;
    }

    public void setTimeDelete(Date timeDelete) {
        this.timeDelete = timeDelete;
    }

    public Boolean getPay() {
        return isPay;
    }

    public void setPay(Boolean pay) {
        isPay = pay;
    }

    public Boolean getDelete() {
        return isDelete;
    }

    public void setDelete(Boolean delete) {
        isDelete = delete;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdService() {
        return idService;
    }

    public void setIdService(String idService) {
        this.idService = idService;
    }

    public String getIdApartment() {
        return idApartment;
    }

    public void setIdApartment(String idApartment) {
        this.idApartment = idApartment;
    }


    public String getNameService() {
        return nameService;
    }

    public void setNameService(String nameService) {
        this.nameService = nameService;
    }

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    public double getQuality() {
        return quality;
    }

    public void setQuality(double quality) {
        this.quality = quality;
    }

}
