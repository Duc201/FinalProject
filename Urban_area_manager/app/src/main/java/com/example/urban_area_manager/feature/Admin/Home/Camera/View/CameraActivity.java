package com.example.urban_area_manager.feature.Admin.Home.Camera.View;

import android.os.Build;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.core.content.ContextCompat;

import com.example.urban_area_manager.R;
import com.example.urban_area_manager.core.base.BaseActivity;
import com.example.urban_area_manager.databinding.ActivityCameraBinding;
import com.example.urban_area_manager.feature.Admin.Home.Sensor.Model.SensorViewModel;

public class CameraActivity extends BaseActivity<ActivityCameraBinding, SensorViewModel> {


    @Override
    protected ActivityCameraBinding getViewBinding() {
        return ActivityCameraBinding.inflate(getLayoutInflater());
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
        SecurityCameraFragment securityCameraFragment = new SecurityCameraFragment();
        replaceFragment(securityCameraFragment,R.id.frame_camera,true,false);
    }

    @Override
    public void addViewListener() {

    }
}