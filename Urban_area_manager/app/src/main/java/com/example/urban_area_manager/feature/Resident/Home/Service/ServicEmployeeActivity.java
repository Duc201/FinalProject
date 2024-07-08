package com.example.urban_area_manager.feature.Resident.Home.Service;

import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.example.urban_area_manager.R;
import com.example.urban_area_manager.core.base.BaseActivity;
import com.example.urban_area_manager.core.component.DataLocalManager;
import com.example.urban_area_manager.databinding.ActivityServicEmployeeBinding;
import com.example.urban_area_manager.databinding.DialogEditBillBinding;
import com.example.urban_area_manager.feature.Admin.Home.Bill.Model.DetailsService;
import com.example.urban_area_manager.feature.Admin.Home.Bill.Model.FixedService;
import com.example.urban_area_manager.feature.Admin.Home.Bill.ViewModel.BillViewModel;
import com.example.urban_area_manager.utils.view.DialogView;
import com.google.firebase.Timestamp;

import java.util.UUID;

public class ServicEmployeeActivity extends BaseActivity<ActivityServicEmployeeBinding, BillViewModel> {
    private ServiceFixedAdapter serviceFixedAdapter;
    private DetailsServiceApdater detailsServiceApdater;
    private LinearLayoutManager linearLayoutManager ;
    private ArrayAdapter<CharSequence> spinnerSexAdapter;

    private FixedService fixedService;

    Dialog dialogadd;
    private int field;

    @Override
    protected ActivityServicEmployeeBinding getViewBinding() {
        return ActivityServicEmployeeBinding.inflate(getLayoutInflater());
    }

    @Override
    protected Class<BillViewModel> getViewModelClass() {
        return BillViewModel.class;
    }

    @Override
    public void onCommonViewLoaded() {
        detailsServiceApdater = new DetailsServiceApdater();
        binding.rcvListservice.setAdapter(detailsServiceApdater);
        linearLayoutManager = new LinearLayoutManager(this);
        binding.rcvListservice.setLayoutManager(linearLayoutManager);
        viewModel.getlistDetailsService(DataLocalManager.getIdApartment());
        detailsServiceApdater.setOnItemClickListener(new DetailsServiceApdater.OnItemClickListener() {

            @Override
            public void onItemClickDelete(DetailsService detailsService) {
                Runnable listenPositive = () -> {
                    detailsService.setTimeDelete(Timestamp.now().toDate());
                    detailsService.setDelete(true);
                    detailsService.setPay(false);
                    viewModel.addDetailsService(detailsService);
                };
                DialogView.showDialogDescriptionByHtml(ServicEmployeeActivity.this,"Xóa dịch vụ","Bạn có chắc chắn " +
                        "muốn xóa dịch vụ này không ?", listenPositive);
            }
        });

    }

    @Override
    public void addDataObserve() {
        super.addDataObserve();
        viewModel.listDetailsService.observe(this,list->{
            detailsServiceApdater.clearList();
            detailsServiceApdater.submitList(list);
        });
        viewModel.isAddDetailsService.observe(this,list->{
            viewModel.getlistDetailsService(DataLocalManager.getIdApartment());
            dialogadd.dismiss();
        });
    }

    @Override
    public void addViewListener() {
        binding.btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialogAdd();
            }
        });
    }

    private void openDialogAdd() {
        DialogEditBillBinding binding =  DialogEditBillBinding.inflate(LayoutInflater.from(this));
        dialogadd = new Dialog(this);
        dialogadd.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogadd.setCancelable(false);
        dialogadd.setContentView(binding.getRoot());
        dialogadd.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        binding.dialogName.setText("Đăng ký dịch vụ");

        spinnerSexAdapter = ArrayAdapter.createFromResource(this, R.array.spinner_field_service, R.layout.spinner_item);
        spinnerSexAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        binding.fieldSp.setAdapter(spinnerSexAdapter);

        serviceFixedAdapter = new ServiceFixedAdapter(this, R.layout.item_selected_spinner);
        binding.listServiceSp.setAdapter(serviceFixedAdapter);

        binding.fieldSp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                field = position;
                viewModel.getlistService(field);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        viewModel._listServiceType.observe(this,listService ->{
            serviceFixedAdapter.clear();
            serviceFixedAdapter.addAll(listService);
            serviceFixedAdapter.notifyDataSetChanged();
            binding.listServiceSp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                     fixedService = (FixedService) parent.getItemAtPosition(position);
                    binding.edtServiceName.setText(fixedService.getName());
                    binding.edtServiceUnit.setText(fixedService.getUnit());
                    binding.edtServicePrice.setText(String.valueOf(fixedService.getPrice()));
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

        });
        binding.btnAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DetailsService detailsService = new DetailsService();
                detailsService.setIdService(fixedService.getId());
                detailsService.setNameService(fixedService.getName());
                detailsService.setPrice(fixedService.getPrice());
                detailsService.setQuality(30);
                detailsService.setIdApartment(DataLocalManager.getIdApartment());
                detailsService.setId(UUID.randomUUID().toString());
                viewModel.addDetailsService(detailsService);
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