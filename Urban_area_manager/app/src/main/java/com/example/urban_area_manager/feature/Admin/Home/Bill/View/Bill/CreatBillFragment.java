package com.example.urban_area_manager.feature.Admin.Home.Bill.View.Bill;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.urban_area_manager.R;
import com.example.urban_area_manager.core.base.BaseFragment;
import com.example.urban_area_manager.core.component.DataLocalManager;
import com.example.urban_area_manager.databinding.FragmentCreatBillBinding;
import com.example.urban_area_manager.feature.Auth.ui.adapter.ApartmentAdapter;
import com.example.urban_area_manager.feature.Admin.Home.Apartment.Model.Apartment;
import com.example.urban_area_manager.feature.Admin.Home.Bill.Model.Bill;
import com.example.urban_area_manager.feature.Admin.Home.Bill.Model.DetailsBillStep;
import com.example.urban_area_manager.feature.Admin.Home.Bill.Model.DetailsBillFix;
import com.example.urban_area_manager.feature.Admin.Home.Bill.ViewModel.BillViewModel;
import com.example.urban_area_manager.utils.Constant;
import com.example.urban_area_manager.utils.extensions.Extensions;
import com.google.firebase.Timestamp;

import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class CreatBillFragment extends BaseFragment<FragmentCreatBillBinding, BillViewModel> {
    private String idBuilding;
    private int monthCreat;
    private int yearCreat;
    private String apartmentName;
    private String idApartment;
    private final DetailBillStepAdapter detailBillStepAdapter = new DetailBillStepAdapter();
    private final DetailBillFixedAdapter detailBillFixedAdapter = new DetailBillFixedAdapter();

    private LinearLayoutManager linearLayoutManager;
    private LinearLayoutManager linearLayoutManager1;
    long sum = 0;
    private Boolean check = false;
    private String idBill;

    private ApartmentAdapter spinnerNameApartmentAdapter;

    @Override
    public void onCommonViewLoaded() {
        Bundle bundle = getArguments();
        if(bundle!=null){
            idBuilding = bundle.getString(Constant.GO_TO_AddIndexFragment_BUILDING);
            monthCreat = bundle.getInt(Constant.GO_TO_AddIndexFragment_MONTH);
            yearCreat = bundle.getInt(Constant.GO_TO_AddIndexFragment_YEAR);
        }

        viewBinding.rcvListStepService.setAdapter(detailBillStepAdapter);
        linearLayoutManager = new LinearLayoutManager(getContext());
        viewBinding.rcvListStepService.setLayoutManager(linearLayoutManager);
        viewModel.getlistApartment(idBuilding);

        viewBinding.rcvDetailsBillFixed.setAdapter(detailBillFixedAdapter);
        linearLayoutManager1 = new LinearLayoutManager(getContext());
        viewBinding.rcvDetailsBillFixed.setLayoutManager(linearLayoutManager1);


        String timeBill = monthCreat + "/" + yearCreat;
        viewBinding.timeBill.setText(timeBill);
        creatSpinnerNameApartmentAdapter();
        viewBinding.spinApartment.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Apartment apartment = (Apartment) parent.getItemAtPosition(position);
                apartmentName = apartment.getName();
                idApartment = apartment.getIdApartment();
                viewModel.checkCreatBill(apartment.getIdApartment(),monthCreat,yearCreat);

//                viewModel.getCombinedFixedServiceApartments(apartment.getIdApartment(),monthCreat,yearCreat);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    public void addDataObserve() {
        super.addDataObserve();
        viewModel.listApartment().observe(this,list->{
            spinnerNameApartmentAdapter.clear();
            spinnerNameApartmentAdapter.addAll(list);
            spinnerNameApartmentAdapter.notifyDataSetChanged();
        });
        viewModel.isCreatBill.observe(this,is->{
            if(is){
                viewModel.getListStepServiceApartment(idApartment,monthCreat,yearCreat);
            }
        });
        viewModel._listStepServiceApartment.observe(this,list->{
            if(list == null){
                Toast.makeText(getActivity(),"Chưa ghi chỉ số điện - nước",Toast.LENGTH_SHORT);
                detailBillStepAdapter.clearList();
                detailBillStepAdapter.submitList(list);
                return;
            }
            calculateAndDisplaySum(list, viewModel._listServiceFixedApartment.getValue());
            creatFixedService(list);
            detailBillStepAdapter.clearList();
            detailBillStepAdapter.submitList(list);

        });
        viewModel._listServiceFixedApartment.observe(this,list->{
            calculateAndDisplaySum(viewModel._listStepServiceApartment.getValue(), list);

            detailBillFixedAdapter.clearList();
            detailBillFixedAdapter.submitList(list);
        });

        viewModel.isUpdateBill.observe(this,list->{
            Extensions.showToastShort(getActivity(), " Tạo Hóa đơn thành công");
            getParentFragmentManager().popBackStack();
        });
        viewModel.isAddDetailsBillFix.observe(this, isAdd->{
            if(isAdd == true){
                viewModel.getListDetailsBillFixed(idApartment,monthCreat,yearCreat);

            }
        });
    }

    private void creatFixedService(List<DetailsBillStep> list) {

        if(check == false){
            for(DetailsBillStep detailsBillStep : list){
                viewModel.getlistDetailsServiceForBill(detailsBillStep.getIdApartment(),detailsBillStep.getMonth(),detailsBillStep.getYear(),detailsBillStep.getIdBill(),idBuilding,apartmentName);
                check = true;
                return;
            }
        }
    }

    private void calculateAndDisplaySum(List<DetailsBillStep> stepServiceList, List<DetailsBillFix> fixedServiceList) {
        sum = 0L;
        if (stepServiceList != null) {
            for (DetailsBillStep detailsBillStep : stepServiceList) {
                sum += detailsBillStep.getSumDetailBill();
                idBill = detailsBillStep.getIdBill();
            }
        }
        if (fixedServiceList != null) {
            for (DetailsBillFix detailsBill : fixedServiceList) {
                sum += detailsBill.getSumDetailBill();
            }
        }
        updateSumUI();
    }
    private void updateSumUI() {
        viewBinding.sumBill.setText(Extensions.formatMoney(sum));
    }
    @Override
    public void addViewListener() {
        viewBinding.btnAdd.setOnClickListener(v -> {
            Bill bill = new Bill();
            bill.setIdBill(idBill);
            bill.setSumBill(sum);
            // cộng 21 ngày Date
            Date now = Timestamp.now().toDate();
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(now);
            calendar.add(Calendar.DAY_OF_YEAR, 21);
            Date endDate = calendar.getTime();
            bill.setEndBill(endDate);
            bill.setNotifi(true);
            bill.setCreatorUserId(DataLocalManager.getEmail());
            bill.setIdApartment(idApartment);
            bill.setNameBill("Hóa đơn " + monthCreat + "/" + yearCreat);
            bill.setLastModifierUserId(DataLocalManager.getEmail());
            bill.setLastModifiTime(Timestamp.now().toDate());
            bill.setIdBuilding(idBuilding);
            bill.setNameApartment(apartmentName);
            bill.setMonth(monthCreat);
            bill.setYear(yearCreat);
            viewModel.updateBill(bill);
        });
    }

    @Override
    protected FragmentCreatBillBinding getBinding(LayoutInflater inflater) {
        return FragmentCreatBillBinding.inflate(getLayoutInflater());
    }

    @Override
    protected Class<BillViewModel> getViewModelClass() {
        return BillViewModel.class;
    }

    @Override
    protected String getTagFragment() {
        return CreatBillFragment.class.getSimpleName();
    }

    private void creatSpinnerNameApartmentAdapter() {
        spinnerNameApartmentAdapter = new ApartmentAdapter(getActivity(), R.layout.spinner_item);
        viewBinding.spinApartment.setAdapter(spinnerNameApartmentAdapter);
    }
}