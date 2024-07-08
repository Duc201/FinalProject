package com.example.urban_area_manager.feature.Admin.UserInfor;

import com.example.urban_area_manager.core.base.BaseActivity;
import com.example.urban_area_manager.databinding.ActivityUserNotificationsBinding;

public class NotificationsUserActivity extends BaseActivity<ActivityUserNotificationsBinding,UserViewModel> {


    @Override
    protected ActivityUserNotificationsBinding getViewBinding() {
        return ActivityUserNotificationsBinding.inflate(getLayoutInflater());
    }

    @Override
    protected Class<UserViewModel> getViewModelClass() {
        return UserViewModel.class;
    }

    @Override
    public void onCommonViewLoaded() {

    }

    @Override
    public void addViewListener() {

    }
}