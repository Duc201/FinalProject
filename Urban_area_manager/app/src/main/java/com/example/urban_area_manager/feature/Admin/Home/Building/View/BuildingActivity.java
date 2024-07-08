package com.example.urban_area_manager.feature.Admin.Home.Building.View;

import android.os.Build;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.core.content.ContextCompat;

import com.example.urban_area_manager.R;
import com.example.urban_area_manager.core.base.BaseActivity;
import com.example.urban_area_manager.databinding.ActivityBuildingBinding;
import com.example.urban_area_manager.feature.Admin.Home.Building.ViewModel.BuildingViewModel;

public class BuildingActivity extends BaseActivity<ActivityBuildingBinding, BuildingViewModel> {


    @Override
    protected ActivityBuildingBinding getViewBinding() {
        return ActivityBuildingBinding.inflate(getLayoutInflater());
    }

    @Override
    protected Class<BuildingViewModel> getViewModelClass() {
        return BuildingViewModel.class;
    }

    @Override
    public void onCommonViewLoaded() {
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.light_background));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
        BuildingFragment buildingFragment = new BuildingFragment();
        replaceFragment(buildingFragment,R.id.frame_building,true,false);
    }

    @Override
    public void addViewListener() {

    }
}