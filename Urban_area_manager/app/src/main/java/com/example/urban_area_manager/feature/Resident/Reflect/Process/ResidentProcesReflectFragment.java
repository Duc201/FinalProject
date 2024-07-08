package com.example.urban_area_manager.feature.Resident.Reflect.Process;


import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.view.LayoutInflater;

import com.example.urban_area_manager.core.base.BaseFragment;
import com.example.urban_area_manager.databinding.FragmentResidentProcesReflectBinding;
import com.example.urban_area_manager.feature.Admin.Reflect.Model.Reflect;
import com.example.urban_area_manager.feature.Admin.Reflect.ViewModel.ReflectViewModel;
import com.example.urban_area_manager.feature.Admin.Reflect.adapter.ReflectAdapter;
import com.example.urban_area_manager.utils.Constant;


public class ResidentProcesReflectFragment extends BaseFragment<FragmentResidentProcesReflectBinding, ReflectViewModel> {

    private ReflectAdapter reflectAdapter = new ReflectAdapter();
    private LinearLayoutManager linearLayoutManager;
    @Override
    public void onCommonViewLoaded() {

        viewBinding.rcvNotprocess.setAdapter(reflectAdapter);
        linearLayoutManager = new LinearLayoutManager(getContext());
        viewBinding.rcvNotprocess.setLayoutManager(linearLayoutManager);
    }

    @Override
    public void addDataObserve() {
        viewModel.listReflectProces().observe(this,list->{
            reflectAdapter.clearList();
            reflectAdapter.submitList(list);
        });
    }

    @Override
    public void addViewListener() {
        reflectAdapter.setOnItemClickListener(new ReflectAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Reflect reflect) {
                Bundle bundle = new Bundle();
                bundle.putSerializable(Constant.GO_TO_ResidentDetailsProcessReflectActivity, reflect);
                openActivity(ResidentDetailsProcessReflectActivity.class,bundle);
            }
        });
    }

    @Override
    protected FragmentResidentProcesReflectBinding getBinding(LayoutInflater inflater) {
        return FragmentResidentProcesReflectBinding.inflate(getLayoutInflater());
    }

    @Override
    protected Class<ReflectViewModel> getViewModelClass() {
        return ReflectViewModel.class;
    }

    @Override
    protected String getTagFragment() {
        return ResidentProcesReflectFragment.class.getSimpleName();
    }

    @Override
    public void onResume() {
        super.onResume();
        viewModel.getListAssignedProcess();
    }
}