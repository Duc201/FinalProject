package com.example.urban_area_manager.feature.Admin.Reflect.Model;

public class Photo {
    private String resourceId;

    public String getResourceId() {
        return resourceId;
    }

    public void setResourceId(String resourceId) {
        this.resourceId = resourceId;
    }

    public Photo(String resourceId) {
        this.resourceId = resourceId;
    }
}
