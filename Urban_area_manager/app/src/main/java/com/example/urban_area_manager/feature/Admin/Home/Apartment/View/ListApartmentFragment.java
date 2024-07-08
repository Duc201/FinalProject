package com.example.urban_area_manager.feature.Admin.Home.Apartment.View;

import android.os.Bundle;

import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import com.example.urban_area_manager.R;
import com.example.urban_area_manager.core.base.BaseFragment;
import com.example.urban_area_manager.databinding.FragmentListApartmentBinding;
import com.example.urban_area_manager.feature.Admin.Home.Apartment.Model.Apartment;
import com.example.urban_area_manager.feature.Admin.Home.Apartment.ViewModel.ApartmentViewModel;
import com.example.urban_area_manager.utils.Constant;

import java.util.ArrayList;
import java.util.List;


public class ListApartmentFragment extends BaseFragment<FragmentListApartmentBinding, ApartmentViewModel> {

    String idBuilding;
    ApartmentAdapter apartmentAdapter = new ApartmentAdapter();
    private LinearLayoutManager linearLayoutManager;
    private ArrayList<Apartment> mlist = new ArrayList<Apartment>();
    @Override
    public void onCommonViewLoaded() {
        Bundle bundle = getArguments();
        idBuilding = bundle.getString(Constant.GO_TO_ListApartmentFragment);
        viewModel.getlistApartment(idBuilding);

        viewBinding.rcvListApartment.setAdapter(apartmentAdapter);
        linearLayoutManager = new LinearLayoutManager(getActivity());
        viewBinding.rcvListApartment.setLayoutManager(linearLayoutManager);
        apartmentAdapter.setOnItemClickListener(new ApartmentAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Apartment apartment) {
                DetailsApartmentFragment detailsApartmentFragment = new DetailsApartmentFragment();
                Bundle bundle = new Bundle();
                bundle.putSerializable(Constant.GO_TO_DetailsApartmentFragment,apartment);
                openFragment(detailsApartmentFragment,R.id.frame_apartment,bundle);
            }
        });
        viewBinding.sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                apartmentAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                apartmentAdapter.getFilter().filter(newText);
                return false;
            }
        });
    }

    @Override
    public void addDataObserve() {
        super.addDataObserve();
        viewModel._listApartment.observe(getViewLifecycleOwner(),listApartment->{
//            checkInitData(listApartment);
            Log.d("haha",listApartment.toString());
            apartmentAdapter.clearList();
            apartmentAdapter.submitList(listApartment);
        });

    }

    @Override
    public void addViewListener() {
        viewBinding.btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString(Constant.AddApartmentFragment,idBuilding);
                AddApartmentFragment addApartmentFragment = new AddApartmentFragment();
                openFragment(addApartmentFragment,R.id.frame_apartment,bundle);
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
    protected FragmentListApartmentBinding getBinding(LayoutInflater inflater) {
        return FragmentListApartmentBinding.inflate(getLayoutInflater());
    }

    @Override
    protected Class<ApartmentViewModel> getViewModelClass() {
        return ApartmentViewModel.class;
    }

    @Override
    protected String getTagFragment() {
        return ListApartmentFragment.class.getSimpleName();
    }
    private void checkInitData(List<Apartment> listApartment) {

        if(listApartment.isEmpty()){
            viewBinding.nonData.setVisibility(View.VISIBLE);
            viewBinding.rcvListApartment.setVisibility(View.GONE);
        }
        else {
            viewBinding.rcvListApartment.setVisibility(View.VISIBLE);
            viewBinding.nonData.setVisibility(View.GONE);
        }
    }


}