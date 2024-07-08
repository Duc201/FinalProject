package com.example.urban_area_manager.feature.Admin.Home.Building.View;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.urban_area_manager.R;
import com.example.urban_area_manager.core.base.BaseFragment;
import com.example.urban_area_manager.feature.Admin.Home.Building.Model.Building;
import com.example.urban_area_manager.databinding.FragmentListBuildingBinding;
import com.example.urban_area_manager.feature.Admin.Home.Building.ViewModel.BuildingViewModel;
import com.example.urban_area_manager.utils.Constant;

import java.util.List;

public class BuildingFragment extends BaseFragment<FragmentListBuildingBinding, BuildingViewModel> {

  private BuildingAdapter buildingAdapter = new BuildingAdapter();
    private LinearLayoutManager linearLayoutManager;



    @Override
    public void onCommonViewLoaded() {
        viewBinding.rcvListbBuilding.setAdapter(buildingAdapter);
        linearLayoutManager = new LinearLayoutManager(getContext());
        viewBinding.rcvListbBuilding.setLayoutManager(linearLayoutManager);
        viewModel.getlistBuilding();
        buildingAdapter.setOnItemClickListener(new BuildingAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Building building) {
                DetailsBuildingFragment fragment = new DetailsBuildingFragment();
                Bundle bundle = new Bundle();
                bundle.putSerializable(Constant.GO_TO_DETAILS_BuildingFragment,building);
                openFragment(fragment,R.id.frame_building,bundle);
                viewBinding.svBuilding.setQuery("", false);
            }
        });
        viewBinding.svBuilding.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                buildingAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                buildingAdapter.getFilter().filter(newText);
                return false;
            }
        });

    }



    @Override
    public void addDataObserve() {
        super.addDataObserve();
        viewModel._listBuilding.observe(this,listBuilding->{

//            checkInitData(listBuilding);
            buildingAdapter.clearList();
            buildingAdapter.submitList(listBuilding);
        });
    }

    private void checkInitData(List<Building> listBuilding) {

            if(listBuilding.isEmpty()){
                viewBinding.nonData.setVisibility(View.VISIBLE);
                viewBinding.rcvListbBuilding.setVisibility(View.GONE);
            }
            else {
                viewBinding.nonData.setVisibility(View.GONE);
                viewBinding.rcvListbBuilding.setVisibility(View.VISIBLE);
            }
        }

    @Override
    public void addViewListener() {
        viewBinding.btnAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AddBuildingFragment addBuildingFragment = new AddBuildingFragment();
                    openFragment(addBuildingFragment,R.id.frame_building);
                }
            });
        viewBinding.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requireActivity().finish();
            }
        });

    }


    @Override
    protected FragmentListBuildingBinding getBinding(LayoutInflater inflater) {
        return FragmentListBuildingBinding.inflate(getLayoutInflater());
    }

    @Override
    protected Class<BuildingViewModel> getViewModelClass() {
        return BuildingViewModel.class;
    }

    @Override
    protected String getTagFragment() {
        return BuildingFragment.class.getSimpleName();
    }


}
