package com.example.urban_area_manager.feature.Admin.Home.Bill.View.Bill;

import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;

import com.example.urban_area_manager.R;
import com.example.urban_area_manager.core.base.BaseFragment;
import com.example.urban_area_manager.databinding.FragmentListBillBinding;
import com.example.urban_area_manager.feature.Admin.Home.Bill.Model.Bill;
import com.example.urban_area_manager.feature.Admin.Home.Bill.ViewModel.BillViewModel;
import com.example.urban_area_manager.utils.Constant;


public class ListBillFragment extends BaseFragment<FragmentListBillBinding, BillViewModel> {
    int month;
    int year;
    String idBuilding;
    private BillAdapter billAdapter = new BillAdapter();
    private LinearLayoutManager linearLayoutManager;

    @Override
    public void onCommonViewLoaded() {
        Bundle bundle = getArguments();
        if(bundle!=null){
            idBuilding = bundle.getString(Constant.GO_TO_BILL_BUILDING);
            month = bundle.getInt(Constant.GO_TO_BILL_MONTH);
            year = bundle.getInt(Constant.GO_TO_BILL_YEAR);
        }
        viewModel.getListBill(idBuilding,month,year);
        viewBinding.rcvListbill.setAdapter(billAdapter);
        linearLayoutManager = new LinearLayoutManager(getContext());
        viewBinding.rcvListbill.setLayoutManager(linearLayoutManager);
        billAdapter.setOnItemClickListener(new BillAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Bill bill) {
                DetailBillFragment detailBillFragment = new DetailBillFragment();
                Bundle bundle = new Bundle();
                bundle.putSerializable(Constant.GO_TO_DetailBillFragment,bill);
                openFragment(detailBillFragment,  R.id.frame_bill_activity,bundle);
            }
        });



    }

    @Override
    public void addViewListener() {
        viewBinding.btnAdd.setOnClickListener(v->{
            CreatBillFragment creatBillFragment = new CreatBillFragment();
            Bundle bundle = new Bundle();
            bundle.putString(Constant.GO_TO_AddIndexFragment_BUILDING,idBuilding);
            bundle.putInt(Constant.GO_TO_AddIndexFragment_MONTH,month);
            bundle.putInt(Constant.GO_TO_AddIndexFragment_YEAR,year);
            openFragment(creatBillFragment, R.id.frame_bill_activity,bundle);
        });

    }

    @Override
    public void addDataObserve() {
        super.addDataObserve();
        viewModel.billList.observe(this,list->{
            billAdapter.clearList();
            billAdapter.submitList(list);
        });
    }

    @Override
    protected FragmentListBillBinding getBinding(LayoutInflater inflater) {
        return FragmentListBillBinding.inflate(getLayoutInflater());
    }

    @Override
    protected Class<BillViewModel> getViewModelClass() {
        return BillViewModel.class;
    }

    @Override
    protected String getTagFragment() {
        return ListBillFragment.class.getSimpleName();
    }
}