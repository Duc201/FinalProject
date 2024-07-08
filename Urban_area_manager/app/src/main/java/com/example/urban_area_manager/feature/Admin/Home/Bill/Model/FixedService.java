package com.example.urban_area_manager.feature.Admin.Home.Bill.Model;

public class FixedService {
    private String id;
    private String name;
    private long price;
    private int type;
    //0 : Internet
    //1: Dịch vụ tòa nhà
    private String unit;
    public FixedService() {
        this.id = id;
        this.name = name;
        this.price = price;
        this.type = type;
        this.unit = unit;
    }
    public FixedService(String id, String name, long price, int type, String unit) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.type = type;
        this.unit = unit;
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

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }
}
