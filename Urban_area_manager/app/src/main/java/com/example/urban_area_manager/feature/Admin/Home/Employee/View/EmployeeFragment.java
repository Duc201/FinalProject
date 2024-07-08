package com.example.urban_area_manager.feature.Admin.Home.Employee.View;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.urban_area_manager.R;
import com.example.urban_area_manager.core.base.BaseFragment;
import com.example.urban_area_manager.databinding.FragmentEmployeeBinding;
import com.example.urban_area_manager.feature.Admin.Home.Employee.Model.Employee;

import com.example.urban_area_manager.feature.Admin.Home.Employee.ViewModel.EmployeeViewModel;
import com.example.urban_area_manager.utils.Constant;

import java.util.List;


public class EmployeeFragment extends BaseFragment<FragmentEmployeeBinding, EmployeeViewModel> {

    private EmployeeAdapter employeeAdapter = new EmployeeAdapter()  ;
    private LinearLayoutManager linearLayoutManager ;
    int department;

    @Override
    public void onCommonViewLoaded() {

        viewBinding.rcvListEmployee.setAdapter(employeeAdapter);
        linearLayoutManager = new LinearLayoutManager(getContext());
        viewBinding.rcvListEmployee.setLayoutManager(linearLayoutManager);

        Bundle bundle = getArguments();
        department = bundle.getInt(Constant.GO_TO_EmployeeFragment);
        viewModel.getlistEmployee(department);


        employeeAdapter.setOnItemClickListener(new EmployeeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Employee employee) {
                        DetailsEmployeeFragment detailsEmployeeFragment = new DetailsEmployeeFragment();
                        Bundle bundle = new Bundle();
                        bundle.putSerializable(Constant.GO_TO_detailsEmployeeFragment,employee );
                        openFragment(detailsEmployeeFragment, R.id.frame_employee,bundle);
            }
        });
    }

    @Override
    public void addDataObserve() {
        super.addDataObserve();
        viewModel.listEmployee().observe(this,listEmployee->{
            checkInitData(listEmployee);
            Log.d("haha",listEmployee.toString());
            employeeAdapter.clearList();
            employeeAdapter.submitList(listEmployee);
        });


    }

    private void checkInitData(List<Employee> listEmployee) {
        if(listEmployee.isEmpty()){
            viewBinding.nonData.setVisibility(View.VISIBLE);
            viewBinding.rcvListEmployee.setVisibility(View.GONE);
        }
        else {
            viewBinding.nonData.setVisibility(View.GONE);
            viewBinding.rcvListEmployee.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void addViewListener() {

        viewBinding.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requireActivity().getSupportFragmentManager().popBackStack();
            }
        });
        viewBinding.btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putInt(Constant.GO_TO_AddEmployeeFragment,department);
                AddEmployeeFragment addEmployeeFragment = new AddEmployeeFragment();
                openFragment(addEmployeeFragment,R.id.frame_employee,bundle);
            }
        });
    }

    @Override
    protected FragmentEmployeeBinding getBinding(LayoutInflater inflater) {
        return FragmentEmployeeBinding.inflate(getLayoutInflater());
    }

    @Override
    protected Class<EmployeeViewModel> getViewModelClass() {
        return EmployeeViewModel.class;
    }

    @Override
    protected String getTagFragment() {
        return EmployeeFragment.class.getSimpleName();
    }
}