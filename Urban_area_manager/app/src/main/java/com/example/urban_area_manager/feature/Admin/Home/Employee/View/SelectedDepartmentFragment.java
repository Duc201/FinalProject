package com.example.urban_area_manager.feature.Admin.Home.Employee.View;

import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.example.urban_area_manager.R;
import com.example.urban_area_manager.core.base.BaseFragment;
import com.example.urban_area_manager.databinding.FragmentSelectedDepartmentBinding;
import com.example.urban_area_manager.feature.Admin.Home.Employee.ViewModel.EmployeeViewModel;
import com.example.urban_area_manager.utils.Constant;


public class SelectedDepartmentFragment extends BaseFragment<FragmentSelectedDepartmentBinding, EmployeeViewModel> {

    private ArrayAdapter<CharSequence> spinnerDepartmentAdapter;
    private int department;

    @Override
    public void onCommonViewLoaded() {
        setSpinnerDepartment();
    }

    @Override
    public void addViewListener() {
        viewBinding.apNextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Ten bo phan-> gui bundle
                Bundle bundle = new Bundle();
                bundle.putInt(Constant.GO_TO_EmployeeFragment,department);
                EmployeeFragment employeeFragment = new EmployeeFragment();
                openFragment(employeeFragment,R.id.frame_employee,bundle);
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
    protected FragmentSelectedDepartmentBinding getBinding(LayoutInflater inflater) {
        return FragmentSelectedDepartmentBinding.inflate(getLayoutInflater());
    }

    @Override
    protected Class<EmployeeViewModel> getViewModelClass() {
        return EmployeeViewModel.class;
    }

    @Override
    protected String getTagFragment() {
        return SelectedDepartmentFragment.class.getSimpleName();
    }

    private void setSpinnerDepartment(){
        spinnerDepartmentAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.spinner_department, R.layout.spinner_item);
        spinnerDepartmentAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        viewBinding.spinDepartmentSp.setAdapter(spinnerDepartmentAdapter);
        viewBinding.spinDepartmentSp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                department = position;
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
}