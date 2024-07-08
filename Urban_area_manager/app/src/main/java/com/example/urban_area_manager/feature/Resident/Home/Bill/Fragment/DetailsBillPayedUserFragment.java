package com.example.urban_area_manager.feature.Resident.Home.Bill.Fragment;

import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;

import com.example.urban_area_manager.core.base.BaseFragment;
import com.example.urban_area_manager.databinding.FragmentDetailsBillPayedUserBinding;
import com.example.urban_area_manager.feature.Admin.Home.Bill.Model.Bill;
import com.example.urban_area_manager.feature.Admin.Home.Bill.View.Bill.DetailBillStepAdapter;
import com.example.urban_area_manager.feature.Admin.Home.Bill.View.Bill.DetailBillFixedAdapter;
import com.example.urban_area_manager.feature.Admin.Home.Bill.ViewModel.BillViewModel;
import com.example.urban_area_manager.utils.Constant;
import com.example.urban_area_manager.utils.extensions.Extensions;


public class DetailsBillPayedUserFragment extends BaseFragment<FragmentDetailsBillPayedUserBinding, BillViewModel> {
    private Bill bill;
    private DetailBillStepAdapter detailBillStepAdapter = new DetailBillStepAdapter();
    private DetailBillFixedAdapter detailBillFixedAdapter = new DetailBillFixedAdapter();
    private LinearLayoutManager linearLayoutManager;
    private LinearLayoutManager linearLayoutManager1;
    long sum = 0;
    @Override
    public void onCommonViewLoaded() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            bill = (Bill) bundle.getSerializable(Constant.GO_TO_detailsBillPayedUserFragment);
        }

        viewModel.getListStepServiceUser(bill.getIdBill());
//        viewModel.getListFixedServiceResident(bill.getIdBill(), bill.getIdApartment());

        viewBinding.rcvListStepService.setAdapter(detailBillStepAdapter);
        linearLayoutManager = new LinearLayoutManager(getContext());
        viewBinding.rcvListStepService.setLayoutManager(linearLayoutManager);

        viewBinding.rcvDetailsBillFixed.setAdapter(detailBillFixedAdapter);
        linearLayoutManager1 = new LinearLayoutManager(getContext());
        viewBinding.rcvDetailsBillFixed.setLayoutManager(linearLayoutManager1);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(bill.getSumBill()).append(" VNĐ");
        viewBinding.sumBill.setText(stringBuilder);
        viewBinding.datePayBill.setText(Extensions.FormatDate(bill.getPayBillTime()));
    }

    @Override
    public void addDataObserve() {
        super.addDataObserve();
        viewModel._listStepServiceUse.observe(this, list -> {
            detailBillStepAdapter.clearList();
            detailBillStepAdapter.submitList(list);
        });
        viewModel.listServiceFixedResident.observe(this, list -> {
            detailBillFixedAdapter.clearList();
            detailBillFixedAdapter.submitList(list);
        });
    }

    @Override
    public void addViewListener() {
        viewBinding.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getParentFragmentManager().popBackStack();
            }
        });
    }

    @Override
    protected FragmentDetailsBillPayedUserBinding getBinding(LayoutInflater inflater) {
        return FragmentDetailsBillPayedUserBinding.inflate(getLayoutInflater());
    }

    @Override
    protected Class<BillViewModel> getViewModelClass() {
        return BillViewModel.class;
    }

    @Override
    protected String getTagFragment() {
        return DetailsBillPayedUserFragment.class.getSimpleName();
    }
}