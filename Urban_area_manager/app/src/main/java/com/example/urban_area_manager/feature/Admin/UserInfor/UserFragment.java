package com.example.urban_area_manager.feature.Admin.UserInfor;

import android.view.LayoutInflater;
import android.view.View;

import com.bumptech.glide.Glide;
import com.example.urban_area_manager.core.base.BaseFragment;
import com.example.urban_area_manager.core.component.DataLocalManager;
import com.example.urban_area_manager.databinding.FragmentUserBinding;
import com.example.urban_area_manager.feature.Auth.viewmodel.AuthViewModel;

public class UserFragment extends BaseFragment<FragmentUserBinding, AuthViewModel> {
    @Override
    public void onCommonViewLoaded() {
        viewModel.getInforUser(DataLocalManager.getIdUser());
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
                openActivity(UserInformationActivity.class);
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
    protected FragmentUserBinding getBinding(LayoutInflater inflater) {
        return FragmentUserBinding.inflate(getLayoutInflater());
    }

    @Override
    protected Class<AuthViewModel> getViewModelClass() {
        return AuthViewModel.class;
    }

    @Override
    protected String getTagFragment() {
        return UserFragment.class.getSimpleName();
    }
}
