package com.example.urban_area_manager.feature.Admin.Home.Bill.Model;

import java.io.Serializable;

public class StepService implements Serializable {
    private String id;
    private String name;

    private String unit;
    public StepService() {
        this.id = "null";
        this.name = "null";
        this.unit = "null";
    }
    public StepService(String id, String name, String unit) {
        this.id = id;
        this.name = name;
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

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }
}
