package com.example.urban_area_manager.feature.Admin.Home.Employee.View;

import android.os.Build;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.core.content.ContextCompat;

import com.example.urban_area_manager.R;
import com.example.urban_area_manager.core.base.BaseActivity;
import com.example.urban_area_manager.databinding.ActivityEmployeeBinding;
import com.example.urban_area_manager.feature.Admin.Home.Employee.ViewModel.EmployeeViewModel;

public class EmployeeActivity extends BaseActivity<ActivityEmployeeBinding, EmployeeViewModel> {

    @Override
    protected ActivityEmployeeBinding getViewBinding() {
        return ActivityEmployeeBinding.inflate(getLayoutInflater());
    }

    @Override
    protected Class getViewModelClass() {
        return EmployeeViewModel.class;
    }

    @Override
    public void onCommonViewLoaded() {
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.light_background));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
        SelectedDepartmentFragment selectedDepartmentFragment = new SelectedDepartmentFragment();
        replaceFragment(selectedDepartmentFragment,R.id.frame_employee,true,false);
    }

    @Override
    public void addViewListener() {

    }
}