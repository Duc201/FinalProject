package com.example.urban_area_manager.feature.Employee.Repair.Bill;

import com.example.urban_area_manager.R;
import com.example.urban_area_manager.core.base.BaseActivity;
import com.example.urban_area_manager.databinding.ActivityBillRepairBinding;
import com.example.urban_area_manager.feature.Admin.Home.Bill.ViewModel.BillViewModel;

public class BillRepairActivity extends BaseActivity<ActivityBillRepairBinding, BillViewModel> {


    @Override
    protected ActivityBillRepairBinding getViewBinding() {
        return ActivityBillRepairBinding.inflate(getLayoutInflater());
    }

    @Override
    protected Class<BillViewModel> getViewModelClass() {
        return BillViewModel.class;
    }

    @Override
    public void onCommonViewLoaded() {
            SelectedBuildingFragment selectedBuildingFragment = new SelectedBuildingFragment();
            replaceFragment(selectedBuildingFragment, R.id.frame_repair_bill_em,true,false);
    }

    @Override
    public void addViewListener() {

    }
}