package com.example.urban_area_manager.feature.Admin.Home.Resident.View;

import android.os.Build;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.example.urban_area_manager.R;
import com.example.urban_area_manager.core.base.BaseActivity;
import com.example.urban_area_manager.databinding.ActivityResidentBinding;
import com.example.urban_area_manager.feature.Admin.MainViewModel;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class ResidentActivity extends BaseActivity<ActivityResidentBinding, MainViewModel> {

    @Override
    protected ActivityResidentBinding getViewBinding() {
        return ActivityResidentBinding.inflate(getLayoutInflater());
    }

    @Override
    protected Class<MainViewModel> getViewModelClass() {
        return MainViewModel.class;
    }

    @Override
    public void onCommonViewLoaded() {
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.light_background));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
       ResidentViewPagerAdapter residentViewPagerAdapter = new ResidentViewPagerAdapter(this);
        binding.residentVp2.setAdapter(residentViewPagerAdapter);
        new TabLayoutMediator(binding.residentTl, binding.residentVp2, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                switch (position){
                    case 0:
                        tab.setText("Danh sách cư dân");
                        break;
                    case 1:
                        tab.setText("Xác thực cư dân");
                        break;
                }
            }
        }).attach();
    }

    @Override
    public void addViewListener() {
        binding.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getOnBackPressedDispatcher().onBackPressed();
            }
        });
    }
}