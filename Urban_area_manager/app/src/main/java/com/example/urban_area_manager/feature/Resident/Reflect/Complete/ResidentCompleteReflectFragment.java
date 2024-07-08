package com.example.urban_area_manager.feature.Resident.Reflect.Complete;

import static android.content.ContentValues.TAG;

import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;

import com.example.urban_area_manager.core.base.BaseFragment;
import com.example.urban_area_manager.databinding.FragmentResidentCompleteReflectBinding;
import com.example.urban_area_manager.feature.Admin.Reflect.Model.Reflect;
import com.example.urban_area_manager.feature.Admin.Reflect.ViewModel.ReflectViewModel;
import com.example.urban_area_manager.feature.Admin.Reflect.adapter.ReflectAdapter;
import com.example.urban_area_manager.utils.Constant;


public class ResidentCompleteReflectFragment extends BaseFragment<FragmentResidentCompleteReflectBinding, ReflectViewModel> {

    private ReflectAdapter reflectAdapter = new ReflectAdapter();
    private LinearLayoutManager linearLayoutManager;

    @Override
    public void onCommonViewLoaded() {

    }

    @Override
    public void addDataObserve() {
        super.addDataObserve();
        viewModel.listReflectComplete().observe(this,list->{
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
                bundle.putSerializable(Constant.GO_TO_ResidentDetailsCompleteReflectActivity, reflect);
                openActivity(ResidentDetailsCompleteReflectActivity.class,bundle);
            }
        });
    }

    @Override
    protected FragmentResidentCompleteReflectBinding getBinding(LayoutInflater inflater) {
        return FragmentResidentCompleteReflectBinding.inflate(getLayoutInflater());
    }

    @Override
    protected Class<ReflectViewModel> getViewModelClass() {
        return ReflectViewModel.class;
    }

    @Override
    protected String getTagFragment() {
        return ResidentCompleteReflectFragment.class.getSimpleName();
    }
    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume3: ");
        viewModel.getListCompleteReflect();

    }
}