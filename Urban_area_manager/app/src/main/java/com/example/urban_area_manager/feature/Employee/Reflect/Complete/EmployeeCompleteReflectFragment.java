package com.example.urban_area_manager.feature.Employee.Reflect.Complete;


import android.os.Bundle;
import android.view.LayoutInflater;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.urban_area_manager.core.base.BaseFragment;
import com.example.urban_area_manager.core.component.DataLocalManager;
import com.example.urban_area_manager.databinding.FragmentEmployeeCompleteReflect2Binding;
import com.example.urban_area_manager.feature.Admin.Reflect.Model.Reflect;
import com.example.urban_area_manager.feature.Admin.Reflect.ViewModel.ReflectViewModel;
import com.example.urban_area_manager.feature.Admin.Reflect.adapter.ReflectAdapter;
import com.example.urban_area_manager.utils.Constant;


public class EmployeeCompleteReflectFragment extends BaseFragment<FragmentEmployeeCompleteReflect2Binding, ReflectViewModel> {

    private ReflectAdapter reflectAdapter = new ReflectAdapter();
    private LinearLayoutManager linearLayoutManager;

    @Override
    public void onCommonViewLoaded() {

    }

    @Override
    public void addDataObserve() {
        super.addDataObserve();
        viewModel.listEmployeeCompleteReflect().observe(this,list->{
            reflectAdapter.clearList();
            reflectAdapter.submitList(list);
        });
    }

    @Override
    public void addViewListener() {
        viewBinding.rcvProcessed.setAdapter(reflectAdapter);
        linearLayoutManager = new LinearLayoutManager(getContext());
        viewBinding.rcvProcessed.setLayoutManager(linearLayoutManager);


        reflectAdapter.setOnItemClickListener(new ReflectAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Reflect reflect) {
                Bundle bundle = new Bundle();
                bundle.putSerializable(Constant.GO_TO_EmployeeDetailsCompleteActivity, reflect);
                openActivity(EmployeeDetailsCompleteActivity.class,bundle);
            }
        });
    }

    @Override
    protected FragmentEmployeeCompleteReflect2Binding getBinding(LayoutInflater inflater) {
        return FragmentEmployeeCompleteReflect2Binding.inflate(getLayoutInflater());
    }

    @Override
    protected Class<ReflectViewModel> getViewModelClass() {
        return ReflectViewModel.class;
    }

    @Override
    protected String getTagFragment() {
        return EmployeeCompleteReflectFragment.class.getSimpleName();
    }

    @Override
    public void onResume() {
        super.onResume();
        viewModel.getlistEmployeeCompleteReflect(DataLocalManager.getEmail());

    }
}