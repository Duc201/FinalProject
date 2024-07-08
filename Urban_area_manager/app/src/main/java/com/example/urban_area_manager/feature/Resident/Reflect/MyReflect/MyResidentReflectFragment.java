package com.example.urban_area_manager.feature.Resident.Reflect.MyReflect;

import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;

import com.example.urban_area_manager.core.base.BaseFragment;
import com.example.urban_area_manager.core.component.DataLocalManager;
import com.example.urban_area_manager.databinding.FragmentMyResidentReflectBinding;
import com.example.urban_area_manager.feature.Admin.Reflect.Model.Reflect;
import com.example.urban_area_manager.feature.Admin.Reflect.View.Assigned.DetailsAssignedReflectActivity;
import com.example.urban_area_manager.feature.Admin.Reflect.ViewModel.ReflectViewModel;
import com.example.urban_area_manager.feature.Admin.Reflect.adapter.ReflectAdapter;
import com.example.urban_area_manager.feature.Resident.Reflect.Complete.ResidentDetailsCompleteReflectActivity;
import com.example.urban_area_manager.feature.Resident.Reflect.Process.ResidentDetailsProcessReflectActivity;
import com.example.urban_area_manager.utils.Constant;


public class MyResidentReflectFragment extends BaseFragment<FragmentMyResidentReflectBinding, ReflectViewModel> {

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
        viewModel.ListMyReflect().observe(this,list->{
            reflectAdapter.clearList();
            reflectAdapter.submitList(list);
        });
    }

    @Override
    public void addViewListener() {
        reflectAdapter.setOnItemClickListener(new ReflectAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Reflect reflect) {
                if(reflect.getState()== 0){
                    Bundle bundle = new Bundle();
                    //Viết lại
                    bundle.putSerializable(Constant.GO_TO_ResidentDetailsProcessReflectActivity, reflect);
                    openActivity(ResidentDetailsProcessReflectActivity.class,bundle);
                }
                if(reflect.getState() == 1){
                    Bundle bundle = new Bundle();
                    bundle.putSerializable(Constant.GO_TO_DetailsAssignedReflectActivity, reflect);
                    openActivity(DetailsAssignedReflectActivity.class,bundle);
                }
                if(reflect.getState() == 2){
                    Bundle bundle = new Bundle();
                    bundle.putSerializable(Constant.GO_TO_ResidentDetailsCompleteReflectActivity, reflect);
                    openActivity(ResidentDetailsCompleteReflectActivity.class,bundle);
                }
            }
        });
    }

    @Override
    protected FragmentMyResidentReflectBinding getBinding(LayoutInflater inflater) {
        return FragmentMyResidentReflectBinding.inflate(getLayoutInflater());
    }

    @Override
    protected Class<ReflectViewModel> getViewModelClass() {
        return ReflectViewModel.class;
    }

    @Override
    protected String getTagFragment() {
        return MyResidentReflectFragment.class.getSimpleName();
    }

    @Override
    public void onResume() {
        super.onResume();
        viewModel.getListMyReflect(DataLocalManager.getEmail());

    }
}