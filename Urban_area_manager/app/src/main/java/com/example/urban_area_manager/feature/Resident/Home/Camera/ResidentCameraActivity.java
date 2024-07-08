package com.example.urban_area_manager.feature.Resident.Home.Camera;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.example.urban_area_manager.R;
import com.example.urban_area_manager.core.base.BaseActivity;
import com.example.urban_area_manager.databinding.ActivityResidentCameraBinding;
import com.example.urban_area_manager.feature.Employee.Repair.Camera.ItemCameraRepairActivity;
import com.example.urban_area_manager.feature.Admin.Home.Camera.Model.Camera;
import com.example.urban_area_manager.feature.Admin.Home.Camera.View.CamerAdapter;
import com.example.urban_area_manager.feature.Admin.Home.Camera.ViewModel.CameraViewModel;
import com.example.urban_area_manager.utils.Constant;

public class ResidentCameraActivity extends BaseActivity<ActivityResidentCameraBinding, CameraViewModel> {
    CamerAdapter camerAdapter = new CamerAdapter();
    LinearLayoutManager linearLayoutManager ;

    @Override
    protected ActivityResidentCameraBinding getViewBinding() {
        return ActivityResidentCameraBinding.inflate(getLayoutInflater());
    }

    @Override
    protected Class<CameraViewModel> getViewModelClass() {
        return CameraViewModel.class;
    }

    @Override
    public void onCommonViewLoaded() {
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.light_background));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
        viewModel.getListCameraPublic();
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
        viewModel._listCameraPublic.observe(this,list->{
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
    }
}