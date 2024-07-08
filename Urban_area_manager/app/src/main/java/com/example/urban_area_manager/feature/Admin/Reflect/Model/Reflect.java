package com.example.urban_area_manager.feature.Admin.Reflect.Model;

import java.io.Serializable;
import java.util.Date;

public class Reflect implements Serializable {
    private String id;
    private String loaction;
    private String specificLocation;
    private String field;
    private int department;
    private Date timeCreator;
    private Date timeHandle;
    private String content;
    private String feelback;
    private String result;
    private double longitude;
    private double latitude;
    private String idCreator;
    private String idHandler;
    private String nameHandler;
    private int state; //0: đang xử lý , satate 1: đã phân công , sate 2: hoàn thành
    private  String image1;
    private String image2;
    private String respondimg1;
    private String respondimg2;
    private Boolean isDeleted;


    public Reflect() {
        this.id = id;
        this.loaction = "NULL";
        this.specificLocation = "NULL";
        this.field = "NULL";
        this.department = 1;
        this.timeCreator = new Date();
        this.timeHandle = new Date();
        this.content = "NULL";
        this.longitude = 105.79908217742233;
        this.latitude = 20.9806986867882947;
        this.idCreator = "NULL";
        this.idHandler = "NULL";
        this.state = 10;
        this.image1 = "NULL";
        this.image2 = "NULL";
        this.respondimg1 = "NULL";
        this.respondimg2 = "NULL";
        this.feelback = "NULL";
        this.result = "NULL";
        this.isDeleted = false;
        this.nameHandler = "NULL";
        this.department = 1;
    }

    public Reflect(String id, String loaction, String specificLocation, String field, int department, Date timeCreator, Date timeHandle, String content, String feelback, String result, double longitude, double latitude, String idCreator, String idHandler, String nameHandler, int state, String image1, String image2, String respondimg1, String respondimg2, Boolean isDeleted) {
        this.id = id;
        this.loaction = loaction;
        this.specificLocation = specificLocation;
        this.field = field;
        this.department = department;
        this.timeCreator = timeCreator;
        this.timeHandle = timeHandle;
        this.content = content;
        this.feelback = feelback;
        this.result = result;
        this.longitude = longitude;
        this.latitude = latitude;
        this.idCreator = idCreator;
        this.idHandler = idHandler;
        this.nameHandler = nameHandler;
        this.state = state;
        this.image1 = image1;
        this.image2 = image2;
        this.respondimg1 = respondimg1;
        this.respondimg2 = respondimg2;
        this.isDeleted = isDeleted;
    }

    public String getNameHandler() {
        return nameHandler;
    }

    public void setNameHandler(String nameHandler) {
        this.nameHandler = nameHandler;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLoaction() {
        return loaction;
    }

    public void setLoaction(String loaction) {
        this.loaction = loaction;
    }

    public String getSpecificLocation() {
        return specificLocation;
    }

    public void setSpecificLocation(String specificLocation) {
        this.specificLocation = specificLocation;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public int getDepartment() {
        return department;
    }

    public void setDepartment(int department) {
        this.department = department;
    }

    public Date getTimeCreator() {
        return timeCreator;
    }

    public void setTimeCreator(Date timeCreator) {
        this.timeCreator = timeCreator;
    }

    public Date getTimeHandle() {
        return timeHandle;
    }

    public void setTimeHandle(Date timeHandle) {
        this.timeHandle = timeHandle;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getFeelback() {
        return feelback;
    }

    public void setFeelback(String feelback) {
        this.feelback = feelback;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public String getIdCreator() {
        return idCreator;
    }

    public void setIdCreator(String idCreator) {
        this.idCreator = idCreator;
    }

    public String getIdHandler() {
        return idHandler;
    }

    public void setIdHandler(String idHandler) {
        this.idHandler = idHandler;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getImage1() {
        return image1;
    }

    public void setImage1(String image1) {
        this.image1 = image1;
    }

    public String getImage2() {
        return image2;
    }

    public void setImage2(String image2) {
        this.image2 = image2;
    }

    public String getRespondimg1() {
        return respondimg1;
    }

    public void setRespondimg1(String respondimg1) {
        this.respondimg1 = respondimg1;
    }

    public String getRespondimg2() {
        return respondimg2;
    }

    public void setRespondimg2(String respondimg2) {
        this.respondimg2 = respondimg2;
    }

    public Boolean getDeleted() {
        return isDeleted;
    }

    public void setDeleted(Boolean deleted) {
        isDeleted = deleted;
    }
}
