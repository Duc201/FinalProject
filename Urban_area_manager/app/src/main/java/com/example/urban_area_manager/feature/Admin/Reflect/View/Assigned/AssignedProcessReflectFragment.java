package com.example.urban_area_manager.feature.Admin.Reflect.View.Assigned;

import static android.content.ContentValues.TAG;

import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;

import com.example.urban_area_manager.core.base.BaseFragment;
import com.example.urban_area_manager.databinding.FragmentAssignedProcessReflectBinding;
import com.example.urban_area_manager.feature.Admin.Reflect.Model.Reflect;
import com.example.urban_area_manager.feature.Admin.Reflect.ViewModel.ReflectViewModel;
import com.example.urban_area_manager.feature.Admin.Reflect.adapter.ReflectAdapter;
import com.example.urban_area_manager.utils.Constant;


public class AssignedProcessReflectFragment extends BaseFragment<FragmentAssignedProcessReflectBinding, ReflectViewModel> {
    private ReflectAdapter reflectAdapter = new ReflectAdapter();
    private LinearLayoutManager linearLayoutManager;

    @Override
    public void onCommonViewLoaded() {

        viewBinding.rcvAssigned.setAdapter(reflectAdapter);
        linearLayoutManager = new LinearLayoutManager(getContext());
        viewBinding.rcvAssigned.setLayoutManager(linearLayoutManager);

        reflectAdapter.setOnItemClickListener(new ReflectAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Reflect reflect) {
                Bundle bundle = new Bundle();
                bundle.putSerializable(Constant.GO_TO_DetailsAssignedReflectActivity, reflect);
                openActivity(DetailsAssignedReflectActivity.class,bundle);
            }
        });
    }

    @Override
    public void addDataObserve() {
        super.addDataObserve();
        viewModel.listReflectAssigned().observe(this,list->{
            reflectAdapter.clearList();
            reflectAdapter.submitList(list);
        });
    }

    @Override
    public void addViewListener() {

    }

    @Override
    protected FragmentAssignedProcessReflectBinding getBinding(LayoutInflater inflater) {
        return FragmentAssignedProcessReflectBinding.inflate(getLayoutInflater());
    }

    @Override
    protected Class<ReflectViewModel> getViewModelClass() {
        return ReflectViewModel.class;
    }

    @Override
    protected String getTagFragment() {
        return AssignedProcessReflectFragment.class.getSimpleName();
    }


    @Override
    public void onResume() {
        viewModel.getListAssignedReflect();
        super.onResume();
        Log.d(TAG, "onResume2: ");

    }


}