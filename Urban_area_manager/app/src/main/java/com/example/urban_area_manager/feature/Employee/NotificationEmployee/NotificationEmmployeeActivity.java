package com.example.urban_area_manager.feature.Employee.NotificationEmployee;

import androidx.fragment.app.FragmentTransaction;

import com.example.urban_area_manager.R;
import com.example.urban_area_manager.core.base.BaseActivity;
import com.example.urban_area_manager.databinding.ActivityNotificationBinding;
import com.example.urban_area_manager.databinding.ActivityNotificationEmmployeeBinding;
import com.example.urban_area_manager.feature.Admin.Home.ViewModel.NotificationViewModel;

public class NotificationEmmployeeActivity extends BaseActivity<ActivityNotificationEmmployeeBinding, NotificationViewModel> {


    @Override
    protected ActivityNotificationEmmployeeBinding getViewBinding() {
        return ActivityNotificationEmmployeeBinding.inflate(getLayoutInflater());
    }

    @Override
    protected Class<NotificationViewModel> getViewModelClass() {
        return NotificationViewModel.class;
    }

    @Override
    public void onCommonViewLoaded() {
        ListNotifiEmployeeFragment listNotifiEmployeeFragment = new ListNotifiEmployeeFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_notifi_employee, listNotifiEmployeeFragment);
        transaction.commit();
    }

    @Override
    public void addViewListener() {

    }
}