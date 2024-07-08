package com.example.urban_area_manager.feature.Employee.Repair;

import android.view.LayoutInflater;
import android.view.View;

import com.example.urban_area_manager.R;
import com.example.urban_area_manager.core.base.BaseFragment;
import com.example.urban_area_manager.databinding.FragmentRepairHomeBinding;
import com.example.urban_area_manager.feature.Employee.NotificationEmployee.NotificationEmmployeeActivity;
import com.example.urban_area_manager.feature.Employee.Repair.Bill.BillRepairActivity;
import com.example.urban_area_manager.feature.Admin.Home.Camera.View.CameraActivity;
import com.example.urban_area_manager.feature.Admin.Home.Sensor.View.SensorActivity;
import com.example.urban_area_manager.feature.Admin.Reflect.Model.Photo;
import com.example.urban_area_manager.feature.Admin.Reflect.adapter.PhotoViewpager2Adapter;

import java.util.ArrayList;
import java.util.List;


public class RepairHomeFragment extends BaseFragment<FragmentRepairHomeBinding, RepairViewModel> {
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
        viewBinding.camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity(CameraActivity.class);
            }
        });

        viewBinding.sensor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity(SensorActivity.class);
            }
        });
        viewBinding.notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity(NotificationEmmployeeActivity.class);
            }
        });
        viewBinding.bill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity(BillRepairActivity.class);
            }
        });

    }

    @Override
    protected FragmentRepairHomeBinding getBinding(LayoutInflater inflater) {
        return FragmentRepairHomeBinding.inflate(getLayoutInflater());
    }

    @Override
    protected Class<RepairViewModel> getViewModelClass() {
        return RepairViewModel.class;
    }

    @Override
    protected String getTagFragment() {
        return RepairHomeFragment.class.getSimpleName();
    }
}