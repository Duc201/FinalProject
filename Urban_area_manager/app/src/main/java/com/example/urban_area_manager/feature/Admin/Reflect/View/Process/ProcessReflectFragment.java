package com.example.urban_area_manager.feature.Admin.Reflect.View.Process;

import static android.content.ContentValues.TAG;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.urban_area_manager.core.base.BaseFragment;
import com.example.urban_area_manager.databinding.FragmentReflectNotprocessBinding;
import com.example.urban_area_manager.feature.Admin.Reflect.Model.Reflect;
import com.example.urban_area_manager.feature.Admin.Reflect.ViewModel.ReflectViewModel;
import com.example.urban_area_manager.feature.Admin.Reflect.adapter.ReflectAdapter;

public class ProcessReflectFragment extends BaseFragment<FragmentReflectNotprocessBinding, ReflectViewModel> {

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
        super.addDataObserve();
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
                bundle.putSerializable("123", reflect);
                openActivity(DetailsProcessReflectActivity.class,bundle);
            }
        });

    }

    @Override
    protected FragmentReflectNotprocessBinding getBinding(LayoutInflater inflater) {
      return FragmentReflectNotprocessBinding.inflate(getLayoutInflater());
    }

    @Override
    protected Class<ReflectViewModel> getViewModelClass() {
        return ReflectViewModel.class;
    }

    @Override
    protected String getTagFragment() {
        return ProcessReflectFragment.class.getSimpleName();
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume: ");
        viewModel.getListAssignedProcess();
    }

}
