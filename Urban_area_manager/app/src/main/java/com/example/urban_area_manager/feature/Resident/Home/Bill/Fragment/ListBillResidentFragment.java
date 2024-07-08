package com.example.urban_area_manager.feature.Resident.Home.Bill.Fragment;



import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;


import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.urban_area_manager.R;
import com.example.urban_area_manager.core.base.BaseFragment;
import com.example.urban_area_manager.core.component.DataLocalManager;
import com.example.urban_area_manager.databinding.FragmentListBillBinding;
import com.example.urban_area_manager.feature.Admin.Home.Bill.Model.Bill;
import com.example.urban_area_manager.feature.Admin.Home.Bill.ViewModel.BillViewModel;
import com.example.urban_area_manager.feature.Resident.Home.Bill.Adapter.BillUserAdapter;
import com.example.urban_area_manager.utils.Constant;

public class ListBillResidentFragment extends BaseFragment<FragmentListBillBinding, BillViewModel> {

    BillUserAdapter billUserAdapter = new BillUserAdapter();
    LinearLayoutManager linearLayoutManager;

    @Override
    public void onCommonViewLoaded() {
        viewModel.getBillResident(DataLocalManager.getIdApartment());

        viewBinding.rcvListbill.setAdapter(billUserAdapter);
        linearLayoutManager = new LinearLayoutManager(getActivity());
        viewBinding.rcvListbill.setLayoutManager(linearLayoutManager);

        billUserAdapter.setOnItemClickListener(new BillUserAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Bill bill) {
                if(!bill.getPay()){
                    DetailBillUserFragment detailBillUserFragment = new DetailBillUserFragment();
                    Bundle bundle = new Bundle();
                    bundle.putSerializable(Constant.GO_TO_DetailBillUserFragment,bill);
                    openFragment(detailBillUserFragment, R.id.frame_bill_resident,bundle);
                }
                else {
                    DetailsBillPayedUserFragment detailsBillPayedUserFragment = new DetailsBillPayedUserFragment();
                    Bundle bundle = new Bundle();
                    bundle.putSerializable(Constant.GO_TO_detailsBillPayedUserFragment,bill);
                    openFragment(detailsBillPayedUserFragment, R.id.frame_bill_resident,bundle);
                }
            }
        });
    }

    @Override
    public void addDataObserve() {
        super.addDataObserve();
        viewModel.billResidentList.observe(this,listBill->{
            billUserAdapter.clearList();
            billUserAdapter.submitList(listBill);
        });
    }

    @Override
    public void addViewListener() {
        viewBinding.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               getActivity().finish();
            }
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
        return null;
    }
}