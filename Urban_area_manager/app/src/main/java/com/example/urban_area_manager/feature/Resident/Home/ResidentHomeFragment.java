package com.example.urban_area_manager.feature.Resident.Home;

import android.view.LayoutInflater;
import android.view.View;

import com.example.urban_area_manager.R;
import com.example.urban_area_manager.core.base.BaseFragment;
import com.example.urban_area_manager.core.component.DataLocalManager;
import com.example.urban_area_manager.databinding.FragmentResidentHomeBinding;
import com.example.urban_area_manager.feature.Resident.Home.Camera.ResidentCameraActivity;
import com.example.urban_area_manager.feature.Resident.Home.Service.ServicEmployeeActivity;
import com.example.urban_area_manager.feature.Admin.Home.Sensor.View.SensorActivity;
import com.example.urban_area_manager.feature.Admin.Reflect.Model.Photo;
import com.example.urban_area_manager.feature.Admin.Reflect.adapter.PhotoViewpager2Adapter;
import com.example.urban_area_manager.feature.Resident.Home.Bill.ResidentBillActivity;
import com.example.urban_area_manager.feature.Resident.Home.Map.ResidentMapActivity;
import com.example.urban_area_manager.feature.Resident.ResidentMainViewModel;

import java.util.ArrayList;
import java.util.List;


public class ResidentHomeFragment extends BaseFragment<FragmentResidentHomeBinding, ResidentMainViewModel> {
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
            viewBinding.map.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    openActivity(ResidentMapActivity.class);
                }
            });
            viewBinding.bill.setOnClickListener(v->{
                openActivity(ResidentBillActivity.class);
            });
            viewBinding.camera.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    openActivity(ResidentCameraActivity.class);
                }
            });
            viewBinding.sensor.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    openActivity(SensorActivity.class);
                }
            });
            viewBinding.service.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    openActivity(ServicEmployeeActivity.class);
                }
            });
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Xin Chào: ").append(DataLocalManager.getName());
            viewBinding.name.setText(stringBuilder);

    }

    @Override
    protected FragmentResidentHomeBinding getBinding(LayoutInflater inflater) {
        return FragmentResidentHomeBinding.inflate(getLayoutInflater());
    }

    @Override
    protected Class<ResidentMainViewModel> getViewModelClass() {
        return ResidentMainViewModel.class;
    }

    @Override
    protected String getTagFragment() {
        return ResidentHomeFragment.class.getSimpleName();
    }
}