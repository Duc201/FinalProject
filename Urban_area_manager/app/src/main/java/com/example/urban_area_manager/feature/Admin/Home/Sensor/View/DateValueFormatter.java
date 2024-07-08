package com.example.urban_area_manager.feature.Admin.Home.Sensor.View;

import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.formatter.ValueFormatter;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateValueFormatter extends ValueFormatter {

    private SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");

    @Override
    public String getAxisLabel(float value, AxisBase axis) {
        // Convert float value to Date
        Date date = new Date((long) value);
        // Format date using SimpleDateFormat
        return dateFormat.format(date);
    }
}
