package com.example.urban_area_manager.feature.Employee.Security;

import android.view.LayoutInflater;
import android.view.View;

import com.example.urban_area_manager.R;
import com.example.urban_area_manager.core.base.BaseFragment;
import com.example.urban_area_manager.databinding.FragmentSecurityHomeBinding;
import com.example.urban_area_manager.feature.Employee.NotificationEmployee.NotificationEmmployeeActivity;
import com.example.urban_area_manager.feature.Admin.Home.Camera.View.CameraActivity;
import com.example.urban_area_manager.feature.Admin.Reflect.Model.Photo;
import com.example.urban_area_manager.feature.Admin.Reflect.adapter.PhotoViewpager2Adapter;

import java.util.ArrayList;
import java.util.List;


public class SecurityHomeFragment extends BaseFragment<FragmentSecurityHomeBinding, SecurityViewModel> {

    private List<Photo> photoList = new ArrayList<>();

    @Override
    public void onCommonViewLoaded() {
        photoList.add(new Photo(getString(R.string.img_ocen1)));
        photoList.add(new Photo(getString(R.string.img_ocen2)));
        photoList.add(new Photo(getString(R.string.img_ocen3)));
        PhotoViewpager2Adapter photoViewpager2Adapter = new PhotoViewpager2Adapter();
        photoViewpager2Adapter.submitList(photoList);
        viewBinding.imagesVp2.setAdapter(photoViewpager2Adapter);
        viewBinding.circleIndicator3.setViewPager(viewBinding.imagesVp2);
    }

    @Override
    public void addViewListener() {
        viewBinding.bill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewBinding.camera.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        openActivity(CameraActivity.class);
                    }
                });
            }
        });
        viewBinding.imgNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity(NotificationEmmployeeActivity.class);;
            }
        });
    }

    @Override
    protected FragmentSecurityHomeBinding getBinding(LayoutInflater inflater) {
        return FragmentSecurityHomeBinding.inflate(getLayoutInflater());
    }

    @Override
    protected Class<SecurityViewModel> getViewModelClass() {
        return SecurityViewModel.class;
    }

    @Override
    protected String getTagFragment() {
        return SecurityHomeFragment.class.getSimpleName();
    }
}