package com.example.urban_area_manager.feature.Admin.Home.Sensor.View;

import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;

import com.example.urban_area_manager.R;
import com.example.urban_area_manager.core.base.BaseFragment;
import com.example.urban_area_manager.databinding.FragmentItemSensorBinding;
import com.example.urban_area_manager.feature.Admin.Home.Sensor.Model.Sensor;
import com.example.urban_area_manager.feature.Admin.Home.Sensor.Model.SensorViewModel;
import com.example.urban_area_manager.utils.Constant;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;


public class ItemSensorFragment extends BaseFragment<FragmentItemSensorBinding, SensorViewModel> implements OnMapReadyCallback {

    private Sensor sensor;
    private GoogleMap mMap;
    private static final int DEFAULT_ZOOM = 17;
    @Override
    public void onCommonViewLoaded() {
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map_sensor_details);
        mapFragment.getMapAsync(this);
        Bundle bundle = getArguments();
        if(bundle!=null){
            sensor = (Sensor) bundle.getSerializable(Constant.GO_TO_ItemSensorFragment);
        }
        viewBinding.nameTitle.setText(sensor.getName());
    }

    @Override
    public void addViewListener() {
            viewModel.getSensorRealtime(sensor.getSerial());
            viewBinding.thongke.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle bundle = new Bundle();
                    bundle.putSerializable(Constant.GO_TO_ChartSensorActivity,sensor);
                    openActivity(ChartSensorActivity.class,bundle);
                }
            });
    }

    @Override
    public void addDataObserve() {
        super.addDataObserve();
        viewModel.deviceItemRT().observe(this,device->{
            viewBinding.tvTemp.setText(formatTemp(device.getTemp()));
            viewBinding.tvPercentHumidity.setText(formatHumid(device.getHumid()));
            viewBinding.tvAdditionalInfo.setText(formatGas(device.getGas()));
        });

    }

    @Override
    protected FragmentItemSensorBinding getBinding(LayoutInflater inflater) {

        return FragmentItemSensorBinding.inflate(getLayoutInflater());
    }

    @Override
    protected Class<SensorViewModel> getViewModelClass() {

        return SensorViewModel.class;
    }

    @Override
    protected String getTagFragment() {

        return ItemSensorFragment.class.getSimpleName();
    }
    private String formatHumid(float humid) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(humid).append(" ").append("%");
        return stringBuilder.toString();
    }

    private String formatTemp(float temp) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(temp).append(" ").append("°C");
        return stringBuilder.toString();
    }
    private String formatGas(double sad) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(sad).append(" ").append("ppm");
        return stringBuilder.toString();
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;
        mMap.getUiSettings().setZoomGesturesEnabled(true);
        mMap.getUiSettings().setScrollGesturesEnabled(true);
        LatLng myPos = new LatLng(sensor.getLatitude(), sensor.getLongitude());
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(myPos, DEFAULT_ZOOM));
        mMap.addMarker(new MarkerOptions().title("Vị trí đặt thiết bị").position(myPos));
    }
}