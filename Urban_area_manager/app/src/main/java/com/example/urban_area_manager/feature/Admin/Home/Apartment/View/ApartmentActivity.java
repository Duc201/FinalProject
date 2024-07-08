package com.example.urban_area_manager.feature.Admin.Home.Apartment.View;

import android.os.Build;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.core.content.ContextCompat;

import com.example.urban_area_manager.R;
import com.example.urban_area_manager.core.base.BaseActivity;
import com.example.urban_area_manager.databinding.ActivityApartmentBinding;
import com.example.urban_area_manager.feature.Admin.Home.Apartment.ViewModel.ApartmentViewModel;

public class ApartmentActivity extends BaseActivity<ActivityApartmentBinding, ApartmentViewModel> {


    @Override
    protected ActivityApartmentBinding getViewBinding() {
        return ActivityApartmentBinding.inflate(getLayoutInflater());
    }

    @Override
    protected Class<ApartmentViewModel> getViewModelClass() {
        return ApartmentViewModel.class;
    }

    @Override
    public void onCommonViewLoaded() {
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.light_background));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
        SelectedCourseFragment selectedCourseFragment = new SelectedCourseFragment();
        replaceFragment(selectedCourseFragment,R.id.frame_apartment,true,false);
    }

    @Override
    public void addViewListener() {}
}