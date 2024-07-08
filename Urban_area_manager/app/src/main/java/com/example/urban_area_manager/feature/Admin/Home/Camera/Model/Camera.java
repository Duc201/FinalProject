package com.example.urban_area_manager.feature.Admin.Home.Camera.Model;

import java.io.Serializable;

public class Camera implements Serializable {
    String id;
    String name;
    String serial;
    String area;
    String link;
    Boolean isPublic;
    public Camera() {
        this.id = "null";
        this.name = "null";
        this.serial = "null";
        this.area = "null";
        this.link = "null";
        this.isPublic = false;
    }
    public Camera(String id, String name, String serial, String area, String link, Boolean isPublic) {
        this.id = id;
        this.name = name;
        this.serial = serial;
        this.area = area;
        this.link = link;
        this.isPublic = isPublic;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public Boolean getPublic() {
        return isPublic;
    }

    public void setPublic(Boolean aPublic) {
        isPublic = aPublic;
    }
}
