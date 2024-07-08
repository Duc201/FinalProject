package com.example.urban_area_manager.feature.Admin.Home.Bill.View;

import android.os.Build;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.core.content.ContextCompat;

import com.example.urban_area_manager.R;
import com.example.urban_area_manager.core.base.BaseActivity;
import com.example.urban_area_manager.databinding.ActivityServiceBinding;
import com.example.urban_area_manager.feature.Admin.Home.Bill.View.Service.listServiceFragment;
import com.example.urban_area_manager.feature.Admin.Home.Bill.View.StepService.listStepServiceFragment;
import com.example.urban_area_manager.feature.Admin.Home.Bill.ViewModel.BillViewModel;

public class ServiceActivity extends BaseActivity<ActivityServiceBinding, BillViewModel> {



    @Override
    protected ActivityServiceBinding getViewBinding() {
        return ActivityServiceBinding.inflate(getLayoutInflater());
    }

    @Override
    protected Class<BillViewModel> getViewModelClass() {
        return BillViewModel.class;
    }

    @Override
    public void onCommonViewLoaded() {
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.light_background));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
    }

    @Override
    public void addDataObserve() {
        super.addDataObserve();

    }

    @Override
    public void addViewListener() {
        binding.imgStepService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listStepServiceFragment listStepServiceFragment = new listStepServiceFragment();
                replaceFragment(listStepServiceFragment, R.id.frame_service,true,true);
            }
        });
        binding.imgService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listServiceFragment listServiceFragment = new listServiceFragment();
                replaceFragment(listServiceFragment, R.id.frame_service,true,true);
            }
        });
    }

}