package com.example.urban_area_manager.feature.Admin.Home.Bill.View.Service;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;

import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.urban_area_manager.R;
import com.example.urban_area_manager.core.base.BaseFragment;
import com.example.urban_area_manager.databinding.DialogEditBillBinding;
import com.example.urban_area_manager.databinding.FragmentListServiceBinding;
import com.example.urban_area_manager.feature.Admin.Home.Bill.Model.FixedService;
import com.example.urban_area_manager.feature.Admin.Home.Bill.ViewModel.BillViewModel;
import com.example.urban_area_manager.utils.view.DialogView;

import java.util.UUID;


public class listServiceFragment extends BaseFragment<FragmentListServiceBinding, BillViewModel> {
    private ServiceAdapter serviceAdapter =  new ServiceAdapter();
    private LinearLayoutManager linearLayoutManager ;
    private ArrayAdapter<CharSequence> spinnerSexAdapter;

    Dialog dialogadd;
    Dialog dialogedit;
    private int field;

    @Override
    public void onCommonViewLoaded() {
        viewBinding.rcvListservice.setAdapter(serviceAdapter);
        linearLayoutManager = new LinearLayoutManager(getActivity());
        viewBinding.rcvListservice.setLayoutManager(linearLayoutManager);
        viewModel.getlistService();
        serviceAdapter.setOnItemClickListener(new ServiceAdapter.OnItemClickListener() {
            @Override
            public void onItemClickEdit(FixedService fixedService) {
                openDialogEdit(fixedService);
            }

            @Override
            public void onItemClickDelete(FixedService fixedService) {
                Runnable listenPositive = () -> {

                    viewModel.deleteService(fixedService);

                };
                DialogView.showDialogDescriptionByHtml(getActivity(),"Xóa dịch vụ","Bạn có chắc chắn " +
                        "muốn xóa dịch vụ này không ?", listenPositive);
            }
        });
    }

    @Override
    public void addDataObserve() {
        super.addDataObserve();
        viewModel.listService().observe(this,list->{
            serviceAdapter.clearList();
            serviceAdapter.submitList(list);
        });
        viewModel.isAddService.observe(this,isAdd->{
            if(isAdd){
                Toast.makeText(getActivity(), "Thêm thành công", Toast.LENGTH_SHORT).show();
                dialogadd.dismiss();
                viewModel.getlistService();
            }
        });
        viewModel.isUpdateService.observe(this, isUpdate->{
            if(isUpdate){
                Toast.makeText(getActivity(), "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                dialogedit.dismiss();
                viewModel.getlistService();
            }
        });
        viewModel.isDeleteService.observe(this, isDelete->{
            Toast.makeText(getActivity(), "Xóa thành công", Toast.LENGTH_SHORT).show();
            viewModel.getlistService();
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
    protected FragmentListServiceBinding getBinding(LayoutInflater inflater) {
        return FragmentListServiceBinding.inflate(getLayoutInflater());
    }

    @Override
    protected Class<BillViewModel> getViewModelClass() {
        return BillViewModel.class;
    }

    @Override
    protected String getTagFragment() {
        return listServiceFragment.class.getSimpleName();
    }
    private void openDialogAdd() {
        DialogEditBillBinding binding =  DialogEditBillBinding.inflate(LayoutInflater.from(getActivity()));
        dialogadd = new Dialog(getActivity());
        dialogadd.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogadd.setCancelable(false);
        dialogadd.setContentView(binding.getRoot());
        dialogadd.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        spinnerSexAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.spinner_field_service, R.layout.spinner_item);
        spinnerSexAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        binding.fieldSp.setAdapter(spinnerSexAdapter);
        binding.fieldSp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                field = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        binding.btnAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = UUID.randomUUID().toString();
                String name = binding.edtServiceName.getText().toString().trim();
                Long price = Long.parseLong(binding.edtServicePrice.getText().toString().trim());
                String unit = binding.edtServiceUnit.getText().toString().trim();
                FixedService fixedService = new FixedService(id,name,price,field,unit);
                viewModel.addService(fixedService);
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
    private void openDialogEdit(FixedService fixedService) {
        DialogEditBillBinding binding =  DialogEditBillBinding.inflate(LayoutInflater.from(getActivity()));
        dialogedit = new Dialog(getActivity());
        dialogedit.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogedit.setCancelable(false);
        dialogedit.setContentView(binding.getRoot());
        dialogedit.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        binding.edtServiceName.setText(fixedService.getName());
        binding.edtServicePrice.setText(String.valueOf(fixedService.getPrice()));
        binding.edtServiceUnit.setText(fixedService.getUnit());
        spinnerSexAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.spinner_field_service, R.layout.spinner_item);
        spinnerSexAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        binding.fieldSp.setAdapter(spinnerSexAdapter);
        binding.fieldSp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                field = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        binding.btnAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fixedService.setName(binding.edtServiceName.getText().toString().trim());
                fixedService.setPrice(Long.parseLong(binding.edtServicePrice.getText().toString().trim()));
                fixedService.setUnit(binding.edtServiceUnit.getText().toString().trim());
                fixedService.setType(field);
                viewModel.updateService(fixedService);
            }
        });

        binding.btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogedit.dismiss();
            }
        });

        dialogedit.show();
    }

}