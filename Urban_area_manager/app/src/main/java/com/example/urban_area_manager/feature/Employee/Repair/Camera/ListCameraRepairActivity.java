package com.example.urban_area_manager.feature.Employee.Repair.Camera;

import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.view.View;

import com.example.urban_area_manager.core.base.BaseActivity;
import com.example.urban_area_manager.databinding.ActivityListCameraRepairBinding;
import com.example.urban_area_manager.feature.Admin.Home.Camera.Model.Camera;
import com.example.urban_area_manager.feature.Admin.Home.Camera.View.CamerAdapter;
import com.example.urban_area_manager.feature.Admin.Home.Camera.ViewModel.CameraViewModel;
import com.example.urban_area_manager.utils.Constant;

public class ListCameraRepairActivity extends BaseActivity<ActivityListCameraRepairBinding, CameraViewModel> {

    CamerAdapter camerAdapter = new CamerAdapter();
    LinearLayoutManager linearLayoutManager ;
    @Override
    protected ActivityListCameraRepairBinding getViewBinding() {
        return ActivityListCameraRepairBinding.inflate(getLayoutInflater());
    }

    @Override
    protected Class<CameraViewModel> getViewModelClass() {
        return CameraViewModel.class;
    }

    @Override
    public void onCommonViewLoaded() {
        viewModel.getListCamera();
        binding.rcvListcamera.setAdapter(camerAdapter);
        linearLayoutManager = new LinearLayoutManager(this);
        binding.rcvListcamera.setLayoutManager(linearLayoutManager);
        camerAdapter.setOnItemClickListener(new CamerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Camera camera) {
                Bundle bundle = new Bundle();
                bundle.putSerializable(Constant.GO_TO_ItemCameraRepairActivity,camera);
                openActivity(ItemCameraRepairActivity.class,bundle);
            }
        });
    }

    @Override
    public void addDataObserve() {
        super.addDataObserve();
        viewModel._listCamera.observe(this,list->{
            camerAdapter.clearList();
            camerAdapter.submitList(list);
        });
    }

    @Override
    public void addViewListener() {
        binding.toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               finish();
            }
        });
     ;
    }
}