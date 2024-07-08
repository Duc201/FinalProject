package com.example.urban_area_manager.feature.Resident.Home.Bill;

import android.os.Build;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.core.content.ContextCompat;

import com.example.urban_area_manager.R;
import com.example.urban_area_manager.core.base.BaseActivity;
import com.example.urban_area_manager.databinding.ActivityResidentBillBinding;
import com.example.urban_area_manager.feature.Admin.Home.Bill.ViewModel.BillViewModel;
import com.example.urban_area_manager.feature.Resident.Home.Bill.Fragment.ListBillResidentFragment;

public class ResidentBillActivity extends BaseActivity<ActivityResidentBillBinding, BillViewModel> {


    @Override
    protected ActivityResidentBillBinding getViewBinding() {
        return ActivityResidentBillBinding.inflate(getLayoutInflater());
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
            ListBillResidentFragment listBillResidentFragment = new ListBillResidentFragment();
            replaceFragment(listBillResidentFragment,R.id.frame_bill_resident,true,false);
    }

    @Override
    public void addViewListener() {

    }
}