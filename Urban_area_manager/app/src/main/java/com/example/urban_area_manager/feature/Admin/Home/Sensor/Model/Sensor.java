package com.example.urban_area_manager.feature.Admin.Home.Sensor.Model;

import java.io.Serializable;
import java.util.Date;

public class Sensor implements Serializable {
    String id;
    String name;
    String serial;
    float humid;
    float temp;
    double gas;
//    String  location;
    private double longitude;
    private double latitude;
    String  employeeid;
    Date time;

    public Sensor(String id, String name, String serial, float humid, float temp, double gas, double longitude, double latitude, String employeeid, Date time) {
        this.id = id;
        this.name = name;
        this.serial = serial;
        this.humid = humid;
        this.temp = temp;
        this.gas = gas;
        this.longitude = longitude;
        this.latitude = latitude;
        this.employeeid = employeeid;
        this.time = time;
    }

    public Sensor() {
        this.id = "null";
        this.name = "null";
        this.serial = "null";
        this.humid = 34;
        this.temp = 32;
        this.gas = 102;
        this.longitude = 105.79908217742233;
        this.latitude = 20.9806986867882947;
        this.employeeid = "áds";
        this.time = new Date();
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

    public float getHumid() {
        return humid;
    }

    public void setHumid(float humid) {
        this.humid = humid;
    }

    public float getTemp() {
        return temp;
    }

    public void setTemp(float temp) {
        this.temp = temp;
    }

    public double getGas() {
        return gas;
    }

    public void setGas(double gas) {
        this.gas = gas;
    }

//    public String getLocation() {
//        return location;
//    }
//
//    public void setLocation(String location) {
//        this.location = location;
//    }

    public String getEmployeeid() {
        return employeeid;
    }

    public void setEmployeeid(String employeeid) {
        this.employeeid = employeeid;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }
}
