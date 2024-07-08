package com.example.urban_area_manager.feature.Admin.Home.Bill.Model;

public class Step {
    private String idStep;
    private int nameStep;
    private int startValue;
    private int endValue;
    private long price;
    private String idServiceStep;

    public Step() {
        this.idStep = "null";
        this.nameStep = 0;
        this.startValue = 0;
        this.endValue = 0;
        this.price = 0;
        this.idServiceStep = "null";
    }
    public Step(String idStep, int nameStep, int startValue, int endValue, long price, String idServiceStep) {
        this.idStep = idStep;
        this.nameStep = nameStep;
        this.startValue = startValue;
        this.endValue = endValue;
        this.price = price;
        this.idServiceStep = idServiceStep;
    }

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    public String getIdStep() {
        return idStep;
    }

    public void setIdStep(String idStep) {
        this.idStep = idStep;
    }

    public int getNameStep() {
        return nameStep;
    }

    public void setNameStep(int nameStep) {
        this.nameStep = nameStep;
    }

    public int getStartValue() {
        return startValue;
    }

    public void setStartValue(int startValue) {
        this.startValue = startValue;
    }

    public int getEndValue() {
        return endValue;
    }

    public void setEndValue(int endValue) {
        this.endValue = endValue;
    }

    public String getIdServiceStep() {
        return idServiceStep;
    }

    public void setIdServiceStep(String idServiceStep) {
        this.idServiceStep = idServiceStep;
    }
}
