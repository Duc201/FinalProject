package com.example.urban_area_manager.feature.Admin.Home.Camera.View;

import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import com.example.urban_area_manager.R;
import com.example.urban_area_manager.core.base.BaseFragment;
import com.example.urban_area_manager.databinding.FragmentSecurityCameraBinding;
import com.example.urban_area_manager.feature.Admin.Home.Camera.Model.Camera;
import com.example.urban_area_manager.feature.Admin.Home.Camera.ViewModel.CameraViewModel;
import com.example.urban_area_manager.utils.Constant;


public class SecurityCameraFragment extends BaseFragment<FragmentSecurityCameraBinding, CameraViewModel> {
    CamerAdapter camerAdapter = new CamerAdapter();
    LinearLayoutManager linearLayoutManager ;

    @Override
    public void onCommonViewLoaded() {
        viewModel.getListCamera();
        viewBinding.rcvListcamera.setAdapter(camerAdapter);
        linearLayoutManager = new LinearLayoutManager(getContext());
        viewBinding.rcvListcamera.setLayoutManager(linearLayoutManager);
        camerAdapter.setOnItemClickListener(new CamerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Camera camera) {
                Bundle bundle = new Bundle();
                bundle.putSerializable(Constant.GO_TO_ItemCameraFragment,camera);
                openActivity(ItemCameraActivity.class,bundle);
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
            viewBinding.toolbar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    requireActivity().getSupportFragmentManager().popBackStack();
                }
            });
            viewBinding.btnAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AddCameraFragment addCameraFragment = new AddCameraFragment();
                    openFragment(addCameraFragment,R.id.frame_camera);
                }
            });
    }

    @Override
    protected FragmentSecurityCameraBinding getBinding(LayoutInflater inflater) {
        return FragmentSecurityCameraBinding.inflate(getLayoutInflater());
    }

    @Override
    protected Class<CameraViewModel> getViewModelClass() {
        return CameraViewModel.class;
    }

    @Override
    protected String getTagFragment() {
        return SecurityCameraFragment.class.getSimpleName();
    }
}