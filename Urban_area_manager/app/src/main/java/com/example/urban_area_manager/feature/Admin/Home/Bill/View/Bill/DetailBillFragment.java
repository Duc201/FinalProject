package com.example.urban_area_manager.feature.Admin.Home.Bill.View.Bill;

import android.os.Bundle;

import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;

import com.example.urban_area_manager.R;
import com.example.urban_area_manager.core.base.BaseFragment;
import com.example.urban_area_manager.databinding.FragmentDetailBillBinding;
import com.example.urban_area_manager.feature.Admin.Home.Bill.Model.Bill;
import com.example.urban_area_manager.feature.Admin.Home.Bill.Model.DetailsBillStep;
import com.example.urban_area_manager.feature.Admin.Home.Bill.ViewModel.BillViewModel;
import com.example.urban_area_manager.feature.Admin.Home.Resident.View.Authen.VisibleImageFragment;
import com.example.urban_area_manager.utils.Constant;
import com.example.urban_area_manager.utils.extensions.Extensions;
import com.example.urban_area_manager.utils.view.DialogView;


public class DetailBillFragment extends BaseFragment<FragmentDetailBillBinding, BillViewModel> {
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
            bill = (Bill) bundle.getSerializable(Constant.GO_TO_DetailBillFragment);
        }
        viewBinding.toolbar.setTitle("Chi tiết hóa đơn "+bill.getNameApartment());
        viewModel.getListStepServiceUser(bill.getIdBill());
        viewModel.getListDetailsBillFixed(bill.getIdApartment(),bill.getMonth(),bill.getYear());
        viewBinding.rcvListStepService.setAdapter(detailBillStepAdapter);
        linearLayoutManager = new LinearLayoutManager(getContext());
        viewBinding.rcvListStepService.setLayoutManager(linearLayoutManager);
        detailBillStepAdapter.setOnItemClickListener(new DetailBillStepAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(DetailsBillStep detailsBillStep) {
                DialogView.showBottomSheetDetailsResident(getActivity(), detailsBillStep, new DialogView.OnOpenDetailsBillListener1() {
                    @Override
                    public void openImage(String path) {
                        Bundle bundle = new Bundle();
                        bundle.putString(Constant.GO_TO_VisibleImageFragment,path);
                        VisibleImageFragment visibleImageFragment = new VisibleImageFragment();
                        visibleImageFragment.setArguments(bundle);
                        FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                        transaction.replace(R.id.frame_bill_resident, visibleImageFragment).addToBackStack(null);
                        transaction.commit();
                    }
                });
            }
        });




        viewBinding.rcvDetailsBillFixed.setAdapter(detailBillFixedAdapter);
        linearLayoutManager1 = new LinearLayoutManager(getContext());
        viewBinding.rcvDetailsBillFixed.setLayoutManager(linearLayoutManager1);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(bill.getSumBill()).append(" VNĐ");
        viewBinding.sumBill.setText(stringBuilder);
        viewBinding.dateEndPayBill.setText(Extensions.FormatDate(bill.getEndBill()));
    }

    @Override
    public void addDataObserve() {
        super.addDataObserve();
        viewModel._listStepServiceUse.observe(this, list -> {
            detailBillStepAdapter.clearList();
            detailBillStepAdapter.submitList(list);
        });
        viewModel._listServiceFixedApartment.observe(this, list -> {
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
    protected FragmentDetailBillBinding getBinding(LayoutInflater inflater) {
        return FragmentDetailBillBinding.inflate(getLayoutInflater());
    }

    @Override
    protected Class<BillViewModel> getViewModelClass() {
        return BillViewModel.class;
    }

    @Override
    protected String getTagFragment() {
        return DetailBillFragment.class.getSimpleName();
    }
}