package com.example.urban_area_manager.feature.Employee.EmployeeInfor;

import android.view.LayoutInflater;
import android.view.View;

import com.bumptech.glide.Glide;
import com.example.urban_area_manager.core.base.BaseFragment;
import com.example.urban_area_manager.core.component.DataLocalManager;
import com.example.urban_area_manager.databinding.FragmentEmployeeInforBinding;
import com.example.urban_area_manager.feature.Auth.viewmodel.AuthViewModel;
import com.example.urban_area_manager.feature.Admin.UserInfor.NotificationsUserActivity;


public class EmployeeInforFragment extends BaseFragment<FragmentEmployeeInforBinding, AuthViewModel> {


    @Override
    public void onCommonViewLoaded() {
        viewModel.getInforUserEmployee(DataLocalManager.getIdUser());

    }

    @Override
    public void addDataObserve() {
        super.addDataObserve();
        viewModel.adminInfor.observe(this, employee -> {
            viewBinding.userName.setText(employee.getFullName());
            Glide.with(this).load(employee.getImageUrl()).into(viewBinding.profileImage);

        });
    }

    @Override
    public void addViewListener() {
        viewBinding.imgUserinfor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity(EmployeeInformationActivity.class);
            }
        });

        viewBinding.imgNotifi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity(NotificationsUserActivity.class);
            }
        });
    }

    @Override
    protected FragmentEmployeeInforBinding getBinding(LayoutInflater inflater) {
        return FragmentEmployeeInforBinding.inflate(getLayoutInflater());
    }

    @Override
    protected Class<AuthViewModel> getViewModelClass() {
        return AuthViewModel.class;
    }

    @Override
    protected String getTagFragment() {
        return EmployeeInforFragment.class.getSimpleName();
    }
}