package com.example.urban_area_manager.feature.Resident.UserInfor.Notification;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.example.urban_area_manager.R;
import com.example.urban_area_manager.core.base.BaseActivity;
import com.example.urban_area_manager.databinding.ActivityResidentMainBinding;
import com.example.urban_area_manager.databinding.ActivityResidentNotificationBinding;
import com.example.urban_area_manager.feature.Admin.Home.Notification.View.listNotificationsFragment;
import com.example.urban_area_manager.feature.Admin.Home.ViewModel.NotificationViewModel;

public class ResidentNotificationActivity extends BaseActivity<ActivityResidentNotificationBinding, NotificationViewModel> {


    @Override
    protected ActivityResidentNotificationBinding getViewBinding() {
        return ActivityResidentNotificationBinding.inflate(getLayoutInflater());
    }

    @Override
    protected Class<NotificationViewModel> getViewModelClass() {
        return NotificationViewModel.class;
    }

    @Override
    public void onCommonViewLoaded() {
        ListNotificationResidentFragment listNotificationsFragment = new ListNotificationResidentFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_notifi_employee, listNotificationsFragment);
        transaction.commit();
    }

    @Override
    public void addViewListener() {

    }
}