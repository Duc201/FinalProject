package com.example.urban_area_manager.feature.Resident.UserInfor.Notification;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.example.urban_area_manager.R;
import com.example.urban_area_manager.core.base.BaseFragment;
import com.example.urban_area_manager.databinding.FragmentDetailsNotificationResidentBinding;
import com.example.urban_area_manager.feature.Admin.Home.Notification.Model.Notification;
import com.example.urban_area_manager.feature.Admin.Home.ViewModel.NotificationViewModel;
import com.example.urban_area_manager.utils.Constant;

import java.text.SimpleDateFormat;


public class DetailsNotificationResidentFragment extends BaseFragment<FragmentDetailsNotificationResidentBinding, NotificationViewModel> {
    Notification notification;

    @Override
    public void onCommonViewLoaded() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            notification = (Notification) bundle.getSerializable(Constant.GO_TO_DetailsNotificationFragment);
        }
        AppCompatActivity activity = (AppCompatActivity) requireActivity();
        activity.setSupportActionBar(viewBinding.toolbar);
        viewBinding.title.setText(notification.getTitle());
        viewBinding.content.setText(notification.getContent());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        viewBinding.tvTime.setText(simpleDateFormat.format(notification.getCreationTime()));
        viewBinding.tvSender.setText(notification.getNameSender());
        Glide.with(this).load(notification.getPathImage()).into(viewBinding.imgNotifidetail);
    }

    @Override
    public void addViewListener() {
        viewBinding.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getParentFragmentManager().popBackStack();
            }
        });
    }

    @Override
    protected FragmentDetailsNotificationResidentBinding getBinding(LayoutInflater inflater) {
        return FragmentDetailsNotificationResidentBinding.inflate(getLayoutInflater());
    }

    @Override
    protected Class<NotificationViewModel> getViewModelClass() {
        return NotificationViewModel.class;
    }

    @Override
    protected String getTagFragment() {
        return DetailsNotificationResidentFragment.class.getSimpleName();
    }
}