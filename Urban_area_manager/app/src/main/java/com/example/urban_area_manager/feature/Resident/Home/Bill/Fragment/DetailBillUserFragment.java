package com.example.urban_area_manager.feature.Resident.Home.Bill.Fragment;

import static android.app.Activity.RESULT_OK;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.urban_area_manager.R;
import com.example.urban_area_manager.core.base.BaseFragment;
import com.example.urban_area_manager.databinding.FragmentDetailBillUserBinding;
import com.example.urban_area_manager.feature.Admin.Home.Bill.Model.Bill;
import com.example.urban_area_manager.feature.Admin.Home.Bill.Model.DetailsBillStep;
import com.example.urban_area_manager.feature.Admin.Home.Bill.View.Bill.DetailBillStepAdapter;
import com.example.urban_area_manager.feature.Admin.Home.Bill.View.Bill.DetailBillFixedAdapter;
import com.example.urban_area_manager.feature.Admin.Home.Bill.ViewModel.BillViewModel;

import com.example.urban_area_manager.feature.Resident.Home.Bill.ZaloPay.PayActivity;
import com.example.urban_area_manager.feature.Admin.Home.Resident.View.Authen.VisibleImageFragment;
import com.example.urban_area_manager.utils.Constant;
import com.example.urban_area_manager.utils.extensions.Extensions;
import com.example.urban_area_manager.utils.view.DialogView;


public class DetailBillUserFragment extends BaseFragment<FragmentDetailBillUserBinding, BillViewModel> {

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
            bill = (Bill) bundle.getSerializable(Constant.GO_TO_DetailBillUserFragment);
        }

        viewModel.getListStepServiceUser(bill.getIdBill());
        viewModel.getListDetailsBillFixed(bill.getIdApartment(),bill.getMonth(),bill.getYear());
//        viewModel.getListFixedServiceResident(bill.getIdBill(), bill.getIdApartment());

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
        viewBinding.btnPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putSerializable(Constant.GO_TO_PAY,bill);
                Intent intent = new Intent(getActivity(), PayActivity.class);
                intent.putExtras(bundle);
                mActivityResultLauncher.launch(intent);
            }
        });
        viewBinding.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getParentFragmentManager().popBackStack();
            }
        });
    }

    @Override
    protected FragmentDetailBillUserBinding getBinding(LayoutInflater inflater) {
        return FragmentDetailBillUserBinding.inflate(getLayoutInflater());
    }

    @Override
    protected Class<BillViewModel> getViewModelClass() {
        return BillViewModel.class;
    }

    @Override
    protected String getTagFragment() {
        return DetailBillUserFragment.class.getSimpleName();
    }

    private ActivityResultLauncher<Intent> mActivityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult o) {
            if(o.getResultCode() == RESULT_OK){
                Intent intent = o.getData();
                String strResult = intent.getStringExtra("data_result");
                getActivity().getSupportFragmentManager().popBackStack();
            }
        }
    });
}