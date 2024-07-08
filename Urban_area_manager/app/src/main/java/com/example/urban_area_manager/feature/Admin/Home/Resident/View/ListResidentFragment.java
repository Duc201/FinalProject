package com.example.urban_area_manager.feature.Admin.Home.Resident.View;

import android.os.Bundle;
import android.view.LayoutInflater;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.urban_area_manager.core.base.BaseFragment;
import com.example.urban_area_manager.feature.Admin.Home.Resident.Model.Resident;
import com.example.urban_area_manager.databinding.FragmentListResidentBinding;
import com.example.urban_area_manager.feature.Admin.Home.Resident.ViewModel.ResidentViewModel;
import com.example.urban_area_manager.utils.Constant;


public class ListResidentFragment extends BaseFragment<FragmentListResidentBinding, ResidentViewModel> {

    public ResidentAdapter residentAdapter = new ResidentAdapter();
    private LinearLayoutManager linearLayoutManager;
    @Override
    public void onCommonViewLoaded() {

        viewBinding.rcvListbBuilding.setAdapter(residentAdapter);
        linearLayoutManager = new LinearLayoutManager(getContext());
        viewBinding.rcvListbBuilding.setLayoutManager(linearLayoutManager);


    }


    @Override
    public void addDataObserve() {
        super.addDataObserve();
        viewModel._listResidented.observe(this,list->{
            residentAdapter.clearList();
            residentAdapter.submitList(list);
        });
    }

    @Override
    public void addViewListener() {
        residentAdapter.setOnItemClickListener(new ResidentAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Resident resident) {
                Bundle bundle = new Bundle();
                bundle.putSerializable(Constant.GO_TO_DetailsResidentFragment,resident);
                openActivity(DetailsResidentActivity.class,bundle);
            }
        });
    }

    @Override
    protected FragmentListResidentBinding getBinding(LayoutInflater inflater) {
        return FragmentListResidentBinding.inflate(getLayoutInflater());
    }

    @Override
    protected Class<ResidentViewModel> getViewModelClass() {
        return ResidentViewModel.class;
    }

    @Override
    protected String getTagFragment() {
        return ListResidentFragment.class.getSimpleName();
    }

    @Override
    public void onResume() {
        super.onResume();
        viewModel.getListResidentAuthened();
    }
}