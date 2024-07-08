package com.example.urban_area_manager.feature.Admin.Home.Sensor.View;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.DatePicker;

import com.example.urban_area_manager.R;
import com.example.urban_area_manager.core.base.BaseActivity;
import com.example.urban_area_manager.databinding.ActivityChartSensorBinding;
import com.example.urban_area_manager.feature.Admin.Home.Sensor.Model.Sensor;
import com.example.urban_area_manager.feature.Admin.Home.Sensor.Model.SensorViewModel;
import com.example.urban_area_manager.utils.Constant;
import com.example.urban_area_manager.utils.extensions.Extensions;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ChartSensorActivity extends BaseActivity<ActivityChartSensorBinding, SensorViewModel> {

   private Sensor sensor;
    private DatePickerDialog datePickerDialog;
    private Date date;

    @Override
    protected ActivityChartSensorBinding getViewBinding() {
        return ActivityChartSensorBinding.inflate(getLayoutInflater());
    }

    @Override
    protected Class<SensorViewModel> getViewModelClass() {
        return SensorViewModel.class;
    }

    @Override
    public void onCommonViewLoaded() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if(bundle!=null){
            sensor = (Sensor) bundle.getSerializable(Constant.GO_TO_ChartSensorActivity);
        }
        initDatePicker();
        viewModel.getListHistory(getCurrentDate(),sensor.getSerial());
        binding.edtBirth.setText(getTodaysDate());
    }


    @Override
    public void addViewListener() {
        binding.edtBirth.setOnClickListener(v -> {
            datePickerDialog.show();
        });
    }

    @Override
    public void addDataObserve() {
        viewModel.listSensorHistory().observe(this, list -> {
            List<Entry> temperatureEntries = new ArrayList<>();
            List<Entry> humidityEntries = new ArrayList<>();
            List<Entry> gasEntries = new ArrayList<>();
            if (list.isEmpty()) {
                binding.lineChart.clear();
                return;
            }
            for (Sensor sensor1 : list) {
                temperatureEntries.add(new Entry(sensor1.getTime().getTime(), sensor1.getTemp()));
                humidityEntries.add(new Entry(sensor1.getTime().getTime(), sensor1.getHumid()));
                gasEntries.add(new Entry(sensor1.getTime().getTime(), (float) sensor1.getGas()));
            }

            // Tạo các LineDataSet cho từng loại dữ liệu
            LineDataSet temperatureDataSet = new LineDataSet(temperatureEntries, "Temperature");
            temperatureDataSet.setColor(Color.RED);
            temperatureDataSet.setValueTextColor(Color.RED);

            LineDataSet humidityDataSet = new LineDataSet(humidityEntries, "Humidity");
            humidityDataSet.setColor(Color.BLUE);
            humidityDataSet.setValueTextColor(Color.BLUE);

            LineDataSet gasDataSet = new LineDataSet(gasEntries, "Air quality");
            gasDataSet.setColor(Color.GREEN);
            gasDataSet.setValueTextColor(Color.GREEN);

            // Tạo LineData và đính kèm vào LineChart
            LineData lineData = new LineData(temperatureDataSet, humidityDataSet, gasDataSet);
            binding.lineChart.setData(lineData);

            // Cấu hình biểu đồ
            binding.lineChart.getDescription().setEnabled(false);
            binding.lineChart.setTouchEnabled(true);
            binding.lineChart.setDragEnabled(true);
            binding.lineChart.setScaleEnabled(true);
            binding.lineChart.setPinchZoom(true);

            XAxis xAxis = binding.lineChart.getXAxis();
            xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
            xAxis.setDrawGridLines(false);
            xAxis.setValueFormatter(new DateValueFormatter());

            YAxis leftAxis = binding.lineChart.getAxisLeft();
            leftAxis.setDrawGridLines(false);

            YAxis rightAxis = binding.lineChart.getAxisRight();
            rightAxis.setEnabled(false);

            Legend legend = binding.lineChart.getLegend();
            legend.setForm(Legend.LegendForm.LINE);

            // Làm mới biểu đồ
            binding.lineChart.invalidate();
        });

    }
    private void initDatePicker() {

        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener()
        {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                LocalDate localDate = LocalDate.of(year, month + 1, dayOfMonth);
                date = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
                binding.edtBirth.setText(Extensions.FormatDate(date));
                viewModel.getListHistory(date, sensor.getSerial());
            }
        };
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        int style = R.style.CustomDatePickerDialogTheme;
        datePickerDialog = new DatePickerDialog(this, style, dateSetListener, year, month, day);
        datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());

    }
    public Date getCurrentDate() {
        // Lấy ngày hiện tại từ Calendar
        Calendar calendar = Calendar.getInstance();
        Date currentDate = calendar.getTime();
        return currentDate;
    }
    private String getTodaysDate() {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        month = month + 1;
        int day = cal.get(Calendar.DAY_OF_MONTH);
        return String.format("%02d/%02d/%04d", day, month, year);
    }
}