package com.example.urban_area_manager.feature.Admin.Home.Bill.View.Bill;

import android.os.Build;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.core.content.ContextCompat;

import com.example.urban_area_manager.R;
import com.example.urban_area_manager.core.base.BaseActivity;
import com.example.urban_area_manager.databinding.ActivityBillBinding;
import com.example.urban_area_manager.feature.Admin.Home.Bill.ViewModel.BillViewModel;

public class BillActivity extends BaseActivity<ActivityBillBinding, BillViewModel> {



    @Override
    protected ActivityBillBinding getViewBinding() {
        return ActivityBillBinding.inflate(getLayoutInflater());
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
        SelectBuildingFragment selectBuildingFragment = new SelectBuildingFragment();
        replaceFragment(selectBuildingFragment, R.id.frame_bill_activity,true,false);
    }



    @Override
    public void addViewListener() {

    }

}