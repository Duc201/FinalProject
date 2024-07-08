package com.example.urban_area_manager.feature.Admin.Home.Notification.Model;

import java.io.Serializable;
import java.util.Date;

public class Notification implements Serializable {
    String id;
    String title;
    String content;
    String pathImage;
    int type;
    //
    /*
        0// Sửa chữa
        1// An ninh
        2// hành chính
        3// hành chính
    *
    * */
    String idSender;
    String idreceiver;

    String nameSender;
    String nameRevier;
    Boolean isDeleted;

    Date creationTime;
    Date deletionTime;

//    String lastModifierUserId;
    String deleterUserId;
    public Notification() {
        this.id = "null";
        this.title = "null";
        this.content = "null";
        this.pathImage = "null";
        this.type = 1;
        this.idSender = "null";
        this.idreceiver = "null";
        this.nameSender = "null";
        this.nameRevier = "null";
        this.isDeleted = false;
        this.creationTime = new Date();
        this.deletionTime = null;
        this.deleterUserId = null;
    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPathImage() {
        return pathImage;
    }

    public void setPathImage(String pathImage) {
        this.pathImage = pathImage;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getIdSender() {
        return idSender;
    }

    public void setIdSender(String idSender) {
        this.idSender = idSender;
    }

    public String getIdreceiver() {
        return idreceiver;
    }

    public void setIdreceiver(String idreceiver) {
        this.idreceiver = idreceiver;
    }

    public String getNameSender() {
        return nameSender;
    }

    public void setNameSender(String nameSender) {
        this.nameSender = nameSender;
    }

    public String getNameRevier() {
        return nameRevier;
    }

    public void setNameRevier(String nameRevier) {
        this.nameRevier = nameRevier;
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


    public Date getDeletionTime() {
        return deletionTime;
    }

    public void setDeletionTime(Date deletionTime) {
        this.deletionTime = deletionTime;
    }


//    public String getLastModifierUserId() {
//        return lastModifierUserId;
//    }
//
//    public void setLastModifierUserId(String lastModifierUserId) {
//        this.lastModifierUserId = lastModifierUserId;
//    }

    public String getDeleterUserId() {
        return deleterUserId;
    }

    public void setDeleterUserId(String deleterUserId) {
        this.deleterUserId = deleterUserId;
    }

    public Notification(String id, String title, String content, String pathImage, int type, String idSender, String idreceiver, String nameSender, String nameRevier, Boolean isDeleted, Date creationTime, Date deletionTime, String creatorUserId, String lastModifierUserId, String deleterUserId) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.pathImage = pathImage;
        this.type = type;
        this.idSender = idSender;
        this.idreceiver = idreceiver;
        this.nameSender = nameSender;
        this.nameRevier = nameRevier;
        this.isDeleted = isDeleted;
        this.creationTime = creationTime;
        this.deletionTime = deletionTime;
//        this.lastModifierUserId = lastModifierUserId;
        this.deleterUserId = deleterUserId;
    }
}
