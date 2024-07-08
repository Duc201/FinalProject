package com.example.urban_area_manager.feature.Resident.UserInfor;

import android.view.LayoutInflater;
import android.view.View;

import com.bumptech.glide.Glide;
import com.example.urban_area_manager.core.base.BaseFragment;
import com.example.urban_area_manager.core.component.DataLocalManager;
import com.example.urban_area_manager.databinding.FragmentResidentInforBinding;
import com.example.urban_area_manager.feature.Auth.viewmodel.AuthViewModel;
import com.example.urban_area_manager.feature.Resident.UserInfor.Notification.ResidentNotificationActivity;


public class ResidentInforFragment extends BaseFragment<FragmentResidentInforBinding, AuthViewModel> {

    @Override
    public void onCommonViewLoaded() {
        viewModel.getInforUserResident(DataLocalManager.getIdUser());

    }

    @Override
    public void addDataObserve() {
        super.addDataObserve();
        viewModel.residentInfor.observe(this, employee -> {
            viewBinding.userName.setText(employee.getFullName());
            Glide.with(this).load(employee.getImageUrl()).into(viewBinding.profileImage);

        });
    }

    @Override
    public void addViewListener() {
        viewBinding.imgUserinfor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity(ResidentInformationActivity.class);
            }
        });

        viewBinding.imgNotifi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity(ResidentNotificationActivity.class);
            }
        });
    }

    @Override
    protected FragmentResidentInforBinding getBinding(LayoutInflater inflater) {
        return FragmentResidentInforBinding.inflate(getLayoutInflater());
    }

    @Override
    protected Class<AuthViewModel> getViewModelClass() {
        return AuthViewModel.class;
    }

    @Override
    protected String getTagFragment() {
        return ResidentInforFragment.class.getSimpleName();
    }
}