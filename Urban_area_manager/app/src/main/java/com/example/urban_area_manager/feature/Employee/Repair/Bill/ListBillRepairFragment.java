package com.example.urban_area_manager.feature.Employee.Repair.Bill;

import android.os.Bundle;
import android.view.LayoutInflater;

import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.urban_area_manager.R;
import com.example.urban_area_manager.core.base.BaseFragment;
import com.example.urban_area_manager.databinding.FragmentListBillRepairBinding;
import com.example.urban_area_manager.feature.Admin.Home.Bill.Model.DetailsBillStep;
import com.example.urban_area_manager.feature.Admin.Home.Bill.ViewModel.BillViewModel;
import com.example.urban_area_manager.feature.Admin.Home.Resident.View.Authen.VisibleImageFragment;
import com.example.urban_area_manager.utils.Constant;
import com.example.urban_area_manager.utils.view.DialogView;


public class ListBillRepairFragment extends BaseFragment<FragmentListBillRepairBinding, BillViewModel> {

    int month;
    int year;
    String idBuilding;
    DetailsBillAdapter detailsBillAdapter = new DetailsBillAdapter();
    private DetailsBillStep electricbill = new DetailsBillStep();
    private DetailsBillStep waterbill = new DetailsBillStep();
    private LinearLayoutManager linearLayoutManager;

    @Override
    public void onCommonViewLoaded() {
        Bundle bundle = getArguments();
        if(bundle!=null){
            idBuilding = bundle.getString(Constant.GO_TO_BILL_BUILDING);
            month = bundle.getInt(Constant.GO_TO_BILL_MONTH);
            year = bundle.getInt(Constant.GO_TO_BILL_YEAR);
        }
        viewModel.getUniqueApartments(month,year,idBuilding);
        viewBinding.rcvListindextbill.setAdapter(detailsBillAdapter);
        linearLayoutManager = new LinearLayoutManager(getActivity());
        viewBinding.rcvListindextbill.setLayoutManager(linearLayoutManager);
        detailsBillAdapter.setOnItemClickListener(new DetailsBillAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(DetailsBillStep detailsBillStep) {
                viewModel.getListStepServiceApartment(detailsBillStep.getIdApartment(),month,year);
            }
        });


    }

    @Override
    public void addDataObserve() {
     viewModel.DetailsBillApartments.observe(this,list->{
         detailsBillAdapter.clearList();
         detailsBillAdapter.submitList(list);
     });
     viewModel._listStepServiceApartment.observe(this,list->{
         if(list.isEmpty()){
             return;
         }
         for(DetailsBillStep detailsBillStep : list){
                if(detailsBillStep.getIdService().equals(Constant.ID_ELECTRIC)){
                    electricbill = detailsBillStep;
                }
                else if(detailsBillStep.getIdService().equals(Constant.ID_WATER)){
                    waterbill = detailsBillStep;
                }
         }
         DialogView.showBottomSheetDetails(getActivity(), electricbill, waterbill, new DialogView.OnOpenDetailsBillListener() {
             @Override
             public void openEdit() {
                 EditIndexFragment editIndexFragment = new EditIndexFragment();
                 Bundle bundle = new Bundle();

                 bundle.putSerializable(Constant.GO_TO_ELECTRICBILL,electricbill);
                 bundle.putSerializable(Constant.GO_TO_WATERBBILL,waterbill);
                 openFragment(editIndexFragment,R.id.frame_repair_bill_em,bundle);
             }

             @Override
             public void delete() {
                 Runnable listenerPositive = () -> {
//                         viewModel.deleteImageAndBuilding(building.getImageUrl(),building);
                 };
                 DialogView.showDialogDescriptionByHtml(getActivity(),"Xóa dịch vụ","Bạn có chắc chắn muốn xóa không",listenerPositive);
             }

             @Override
             public void openImage(String path) {
                 Bundle bundle = new Bundle();
                 bundle.putString(Constant.GO_TO_VisibleImageFragment,path);
                 // Tạo Fragment và đặt Bundle vào
                 VisibleImageFragment visibleImageFragment = new VisibleImageFragment();
                 visibleImageFragment.setArguments(bundle);

                 // Hiển thị Fragment
                 FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                 transaction.replace(R.id.frame_repair_bill_em, visibleImageFragment).addToBackStack(null);
                 transaction.commit();
             }


         });
     });
    }

    @Override
    public void addViewListener() {
            viewBinding.btnAdd.setOnClickListener(v->{
                AddIndexFragment addIndexFragment = new AddIndexFragment();
                Bundle bundle = new Bundle();
                bundle.putString(Constant.GO_TO_AddIndexFragment_BUILDING,idBuilding);
                bundle.putInt(Constant.GO_TO_AddIndexFragment_MONTH,month);
                bundle.putInt(Constant.GO_TO_AddIndexFragment_YEAR,year);
                openFragment(addIndexFragment, R.id.frame_repair_bill_em,bundle);

            });
    }

    @Override
    protected FragmentListBillRepairBinding getBinding(LayoutInflater inflater) {
        return FragmentListBillRepairBinding.inflate(getLayoutInflater());
    }

    @Override
    protected Class<BillViewModel> getViewModelClass() {
        return BillViewModel.class;
    }

    @Override
    protected String getTagFragment() {
        return ListBillRepairFragment.class.getSimpleName();
    }
}