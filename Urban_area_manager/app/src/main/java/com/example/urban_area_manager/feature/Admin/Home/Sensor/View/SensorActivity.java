package com.example.urban_area_manager.feature.Admin.Home.Sensor.View;

import android.os.Build;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.core.content.ContextCompat;

import com.example.urban_area_manager.R;
import com.example.urban_area_manager.core.base.BaseActivity;
import com.example.urban_area_manager.databinding.ActivitySensorBinding;
import com.example.urban_area_manager.feature.Admin.Home.Sensor.Model.SensorViewModel;

public class SensorActivity extends BaseActivity<ActivitySensorBinding, SensorViewModel> {


    @Override
    protected ActivitySensorBinding getViewBinding() {
        return ActivitySensorBinding.inflate(getLayoutInflater());
    }

    @Override
    protected Class<SensorViewModel> getViewModelClass() {
        return SensorViewModel.class;
    }

    @Override
    public void onCommonViewLoaded() {
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.light_background));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
        SensorFragment sensorFragment = new SensorFragment();
        replaceFragment(sensorFragment,R.id.frame_sensor,true,false);
    }

    @Override
    public void addViewListener() {
    }
}