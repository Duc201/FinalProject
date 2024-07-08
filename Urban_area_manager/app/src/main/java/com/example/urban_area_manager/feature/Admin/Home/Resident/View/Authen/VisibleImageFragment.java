package com.example.urban_area_manager.feature.Admin.Home.Resident.View.Authen;

import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;

import com.bumptech.glide.Glide;
import com.example.urban_area_manager.core.base.BaseFragment;
import com.example.urban_area_manager.databinding.FragmentVisibleImageBinding;
import com.example.urban_area_manager.feature.Admin.Home.Resident.ViewModel.ResidentViewModel;
import com.example.urban_area_manager.utils.Constant;


public class VisibleImageFragment extends BaseFragment<FragmentVisibleImageBinding, ResidentViewModel> {


    @Override
    public void onCommonViewLoaded() {
        Bundle bundle = getArguments();
        if(bundle !=null){
            String image = bundle.getString(Constant.GO_TO_VisibleImageFragment);
            Glide.with(getActivity()).load(image).into(viewBinding.imgCccd);
        }
    }

    @Override
    public void addViewListener() {
        viewBinding.toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requireActivity().getSupportFragmentManager().popBackStack();
            }
        });
    }

    @Override
    protected FragmentVisibleImageBinding getBinding(LayoutInflater inflater) {
        return FragmentVisibleImageBinding.inflate(getLayoutInflater());
    }

    @Override
    protected Class<ResidentViewModel> getViewModelClass() {
        return ResidentViewModel.class;
    }

    @Override
    protected String getTagFragment() {
        return VisibleImageFragment.class.getSimpleName();
    }
}