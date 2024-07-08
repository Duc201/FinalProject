package com.example.urban_area_manager.feature.Employee.Reflect.Process;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.urban_area_manager.core.base.BaseFragment;
import com.example.urban_area_manager.core.component.DataLocalManager;
import com.example.urban_area_manager.databinding.FragmentAdministrativeProcessBinding;
import com.example.urban_area_manager.feature.Admin.Reflect.Model.Reflect;
import com.example.urban_area_manager.feature.Admin.Reflect.ViewModel.ReflectViewModel;
import com.example.urban_area_manager.feature.Admin.Reflect.adapter.ReflectAdapter;
import com.example.urban_area_manager.utils.Constant;

import java.util.ArrayList;


public class AdministrativeProcessFragment extends BaseFragment<FragmentAdministrativeProcessBinding, ReflectViewModel> {

    private ReflectAdapter reflectAdapter = new ReflectAdapter();
    private LinearLayoutManager linearLayoutManager;
    private ArrayList<Reflect> mlist = new ArrayList<Reflect>();
    @Override
    public void onCommonViewLoaded() {

        viewBinding.rcvAssigned.setAdapter(reflectAdapter);
        linearLayoutManager = new LinearLayoutManager(getContext());
        viewBinding.rcvAssigned.setLayoutManager(linearLayoutManager);
        Log.d("aaa",DataLocalManager.getEmail());


        reflectAdapter.setOnItemClickListener(new ReflectAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Reflect reflect) {
                Bundle bundle = new Bundle();
                bundle.putSerializable(Constant.GO_TO_AdministrativeDetailsProcessActivity, reflect);
                openActivity(AdministrativeDetailsProcessActivity.class,bundle);
            }
        });
    }

    @Override
    public void addViewListener() {
            viewModel.listProcesEmployeeReflect().observe(this, list->{
                reflectAdapter.clearList();
                reflectAdapter.submitList(list);
            });
    }

    @Override
    protected FragmentAdministrativeProcessBinding getBinding(LayoutInflater inflater) {
        return FragmentAdministrativeProcessBinding.inflate(getLayoutInflater());
    }

    @Override
    protected Class<ReflectViewModel> getViewModelClass() {
        return ReflectViewModel.class;
    }

    @Override
    protected String getTagFragment() {
        return AdministrativeProcessFragment.class.getSimpleName();
    }

    @Override
    public void onResume() {
        super.onResume();
        viewModel.getListProcesEmployeeReflect(DataLocalManager.getEmail());
    }
}