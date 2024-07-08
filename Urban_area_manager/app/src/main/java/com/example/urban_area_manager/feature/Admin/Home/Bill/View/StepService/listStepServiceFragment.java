package com.example.urban_area_manager.feature.Admin.Home.Bill.View.StepService;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;

import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

import com.example.urban_area_manager.core.base.BaseFragment;
import com.example.urban_area_manager.databinding.DialogAddServiceStepBinding;
import com.example.urban_area_manager.databinding.DialogEditStepServiceBinding;
import com.example.urban_area_manager.databinding.DialogVisibleStepServiceBinding;
import com.example.urban_area_manager.databinding.FragmentListStepServiceBinding;
import com.example.urban_area_manager.feature.Admin.Home.Bill.Model.StepService;
import com.example.urban_area_manager.feature.Admin.Home.Bill.ViewModel.BillViewModel;

import java.util.UUID;


public class listStepServiceFragment extends BaseFragment<FragmentListStepServiceBinding, BillViewModel> {
    private StepServiceAdapter stepServiceAdapter =  new StepServiceAdapter();
    private LinearLayoutManager linearLayoutManager ;
    Dialog dialogadd;

    @Override
    public void onResume() {
        super.onResume();
        viewModel.getlistStepService();
    }

    @Override
    public void onCommonViewLoaded() {
        viewBinding.rcvListservice.setAdapter(stepServiceAdapter);
        linearLayoutManager = new LinearLayoutManager(getActivity());
        viewBinding.rcvListservice.setLayoutManager(linearLayoutManager);
        viewModel.getlistStepService();
        stepServiceAdapter.setOnItemClickListener(new StepServiceAdapter.OnItemClickListener() {
            @Override
            public void onItemClickDetails(StepService stepService) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("Duc123",stepService);
                openActivity(DetailsStepService.class,bundle);
            }
        });
    }

    @Override
    public void addDataObserve() {
        super.addDataObserve();
        viewModel.listStepService().observe(this,list->{
            stepServiceAdapter.clearList();
            stepServiceAdapter.submitList(list);
        });
        viewModel.isAddStepService.observe(this,isAdd->{
            if(isAdd){
                Toast.makeText(getActivity(), "Thêm thành công", Toast.LENGTH_SHORT).show();
                dialogadd.dismiss();
                viewModel.getlistStepService();
            }
        });
    }

    @Override
    public void addViewListener() {
        viewBinding.btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialogAdd();
            }
        });
    }

    @Override
    protected FragmentListStepServiceBinding getBinding(LayoutInflater inflater) {
        return FragmentListStepServiceBinding.inflate(getLayoutInflater());
    }

    @Override
    protected Class<BillViewModel> getViewModelClass() {
        return BillViewModel.class;
    }

    @Override
    protected String getTagFragment() {
        return listStepServiceFragment.class.getSimpleName();
    }
    private void openDialogAdd() {
        DialogAddServiceStepBinding binding =  DialogAddServiceStepBinding.inflate(LayoutInflater.from(getActivity()));
        dialogadd = new Dialog(getActivity());
        dialogadd.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogadd.setCancelable(false);
        dialogadd.setContentView(binding.getRoot());
        dialogadd.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        binding.dialogName.setText("Thêm Dịch Vụ");
        binding.btnAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StepService stepService = new StepService(UUID.randomUUID().toString(),binding.edtServiceName.getText().toString().trim(),
                        binding.edtServiceUnit.getText().toString().trim());
                viewModel.addStepService(stepService);
            }
        });

        binding.btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogadd.dismiss();
            }
        });
        dialogadd.show();
    }

}