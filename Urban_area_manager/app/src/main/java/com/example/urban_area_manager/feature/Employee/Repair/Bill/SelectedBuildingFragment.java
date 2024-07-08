package com.example.urban_area_manager.feature.Employee.Repair.Bill;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;

import com.example.urban_area_manager.R;
import com.example.urban_area_manager.core.base.BaseFragment;
import com.example.urban_area_manager.databinding.FragmentSelectedBuildingBinding;
import com.example.urban_area_manager.feature.Auth.ui.adapter.BuildingAdapter;
import com.example.urban_area_manager.feature.Admin.Home.Bill.ViewModel.BillViewModel;
import com.example.urban_area_manager.feature.Admin.Home.Building.Model.Building;
import com.example.urban_area_manager.utils.Constant;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class SelectedBuildingFragment extends BaseFragment<FragmentSelectedBuildingBinding, BillViewModel> {


    private BuildingAdapter buildingAdapter;


    @Override
    public void onCommonViewLoaded() {
        // Lấy tháng và năm hiện tại
        Calendar calendar = Calendar.getInstance();
        int currentYear = calendar.get(Calendar.YEAR);
        int currentMonth = calendar.get(Calendar.MONTH) + 1; // Month is zero-based, so add 1

        // Thiết lập Adapter cho Spinner năm
        List<Integer> years = new ArrayList<>();
        for (int i = 2000; i <= currentYear + 10; i++) {
            years.add(i);
        }
        ArrayAdapter<Integer> yearAdapter = new ArrayAdapter<>(requireActivity(), android.R.layout.simple_spinner_item, years);
        yearAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        viewBinding.spYear.setAdapter(yearAdapter);
        viewBinding.spYear.setSelection(years.indexOf(currentYear)); // Chọn năm hiện tại

        // Thiết lập Adapter cho Spinner tháng
        List<Integer> months = new ArrayList<>();
        for (int i = 1; i <= 12; i++) {
            months.add(i);
        }
        ArrayAdapter<Integer> monthAdapter = new ArrayAdapter<>(requireActivity(), android.R.layout.simple_spinner_item, months);
        monthAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        viewBinding.spMonth.setAdapter(monthAdapter);
        viewBinding.spMonth.setSelection(currentMonth - 1); // Chọn tháng hiện tại (trừ đi 1 vì month là zero-based)
        viewModel.getlistBuilding();
    }

    @Override
    public void addDataObserve() {
        super.addDataObserve();
        viewModel.listBuilding().observe(this,list->{
            setSpinnerBuildingAdapter(list);
        });
    }

    private void setSpinnerBuildingAdapter(List<Building> c) {

        buildingAdapter = new BuildingAdapter(getActivity(), R.layout.item_selected_spinner);
        viewBinding.buildingSp.setAdapter(buildingAdapter);
        buildingAdapter.clear();
        buildingAdapter.addAll(c);
        buildingAdapter.notifyDataSetChanged();
    }

    @Override
    public void addViewListener() {
        viewBinding.apNextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Building building = (Building) viewBinding.buildingSp.getSelectedItem();
                int year = (int) viewBinding.spYear.getSelectedItem();
                int month = (int) viewBinding.spMonth.getSelectedItem();
                Bundle bundle = new Bundle();
                bundle.putString(Constant.GO_TO_BILL_BUILDING,building.getIdBuilding());
                bundle.putInt(Constant.GO_TO_BILL_MONTH,month);
                bundle.putInt(Constant.GO_TO_BILL_YEAR,year);
                ListBillRepairFragment listBillRepairFragment = new ListBillRepairFragment();
                openFragment(listBillRepairFragment,R.id.frame_repair_bill_em,bundle);
            }
        });


    }
    @Override
    protected FragmentSelectedBuildingBinding getBinding(LayoutInflater inflater) {
        return FragmentSelectedBuildingBinding.inflate(getLayoutInflater());
    }

    @Override
    protected Class<BillViewModel> getViewModelClass() {
        return BillViewModel.class;
    }

    @Override
    protected String getTagFragment() {
        return SelectedBuildingFragment.class.getSimpleName();
    }
}