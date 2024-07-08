package com.example.urban_area_manager.feature.Admin.Home.Resident.View.Authen;

import android.os.Bundle;
import android.view.LayoutInflater;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.urban_area_manager.core.base.BaseFragment;
import com.example.urban_area_manager.feature.Admin.Home.Resident.Model.Resident;
import com.example.urban_area_manager.databinding.FragmentResidentAuthenBinding;
import com.example.urban_area_manager.feature.Admin.Home.Resident.View.ResidentAdapter;
import com.example.urban_area_manager.feature.Admin.Home.Resident.ViewModel.ResidentViewModel;
import com.example.urban_area_manager.utils.Constant;

import java.util.ArrayList;


public class ResidentAuthenFragment extends BaseFragment<FragmentResidentAuthenBinding, ResidentViewModel> {
    public ResidentAdapter residentAdapter = new ResidentAdapter();
    private LinearLayoutManager linearLayoutManager;
    private ArrayList<Resident> mlist = new ArrayList<Resident>();
    @Override
    public void onCommonViewLoaded() {


        viewBinding.rcvListresident.setAdapter(residentAdapter);
        linearLayoutManager = new LinearLayoutManager(getContext());
        viewBinding.rcvListresident.setLayoutManager(linearLayoutManager);

        residentAdapter.setOnItemClickListener(new ResidentAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Resident resident) {
                Bundle bundle = new Bundle();
                bundle.putSerializable(Constant.GO_TO_DetailsResidentAuthenFragment,resident);
                openActivity(DetailsResidentAuthenActivity.class,bundle);


            }
        });
    }

    @Override
    public void addDataObserve() {
        super.addDataObserve();
        viewModel.listResident().observe(this,listResident->{
            residentAdapter.clearList();
            residentAdapter.submitList(listResident);
        });
    }

    @Override
    public void addViewListener() {

    }

    @Override
    protected FragmentResidentAuthenBinding getBinding(LayoutInflater inflater) {
        return FragmentResidentAuthenBinding.inflate(getLayoutInflater());
    }

    @Override
    protected Class<ResidentViewModel> getViewModelClass() {
        return ResidentViewModel.class;
    }

    @Override
    protected String getTagFragment() {
        return ResidentAuthenFragment.class.getSimpleName();
    }

    @Override
    public void onResume() {
        super.onResume();
        viewModel.getListResidentAuthen();
    }
}