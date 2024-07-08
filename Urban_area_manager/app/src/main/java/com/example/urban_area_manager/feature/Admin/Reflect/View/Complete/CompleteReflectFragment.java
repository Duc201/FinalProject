package com.example.urban_area_manager.feature.Admin.Reflect.View.Complete;

import static android.content.ContentValues.TAG;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.urban_area_manager.core.base.BaseFragment;
import com.example.urban_area_manager.databinding.FragmentReflectProcessedBinding;
import com.example.urban_area_manager.feature.Admin.Reflect.Model.Reflect;
import com.example.urban_area_manager.feature.Admin.Reflect.ViewModel.ReflectViewModel;
import com.example.urban_area_manager.feature.Admin.Reflect.adapter.ReflectAdapter;
import com.example.urban_area_manager.utils.Constant;

public class CompleteReflectFragment extends BaseFragment<FragmentReflectProcessedBinding, ReflectViewModel> {
    private ReflectAdapter reflectAdapter = new ReflectAdapter();
    private LinearLayoutManager linearLayoutManager;

    @Override
    public void onCommonViewLoaded() {
//        viewModel.getListCompleteReflect();
    }

    @Override
    public void addDataObserve() {
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
                bundle.putSerializable(Constant.GO_TO_DetailsCompleteReflect, reflect);
                openActivity(DetailsCompleteReflectActivity.class,bundle);
            }
        });

    }

    @Override
    protected FragmentReflectProcessedBinding getBinding(LayoutInflater inflater) {
        return FragmentReflectProcessedBinding.inflate(getLayoutInflater());
    }

    @Override
    protected Class<ReflectViewModel> getViewModelClass() {
        return ReflectViewModel.class;
    }

    @Override
    protected String getTagFragment() {
        return CompleteReflectFragment.class.getSimpleName();
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume3: ");
        viewModel.getListCompleteReflect();
    }

}
