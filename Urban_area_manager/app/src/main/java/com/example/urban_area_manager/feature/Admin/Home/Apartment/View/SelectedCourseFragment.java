package com.example.urban_area_manager.feature.Admin.Home.Apartment.View;



import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;


import com.example.urban_area_manager.R;
import com.example.urban_area_manager.core.base.BaseFragment;
import com.example.urban_area_manager.databinding.FragmentSelectedCourseBinding;
import com.example.urban_area_manager.feature.Admin.Home.Apartment.ViewModel.ApartmentViewModel;
import com.example.urban_area_manager.utils.Constant;

import java.util.List;


public class SelectedCourseFragment extends BaseFragment<FragmentSelectedCourseBinding, ApartmentViewModel> {
    String nameBuilding;

    @Override
    public void onCommonViewLoaded() {
        viewModel.getlistNameBuilding();
    }

    @Override
    public void addDataObserve() {
        super.addDataObserve();
        viewModel.listNameBuilding().observe(this, listNameBuilding->{
            setSpinnerNameBuildingAdapter(listNameBuilding);
        });
        viewModel.idBuilding.observe(this,idBuilding->{
            if(idBuilding!= null){
                Bundle bundle = new Bundle();
                bundle.putString(Constant.GO_TO_ListApartmentFragment,idBuilding);
                ListApartmentFragment listApartmentFragment = new ListApartmentFragment();
                openFragment(listApartmentFragment, R.id.frame_apartment,bundle);
            }

        });
    }

    @Override
    public void addViewListener() {
            viewBinding.apNextBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    viewModel.getidBuilding(nameBuilding);
                }
            });
        viewBinding.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });

    }

    @Override
    protected FragmentSelectedCourseBinding getBinding(LayoutInflater inflater) {
        return FragmentSelectedCourseBinding.inflate(getLayoutInflater());
    }

    @Override
    protected Class<ApartmentViewModel> getViewModelClass() {
        return ApartmentViewModel.class;
    }

    @Override
    protected String getTagFragment() {
        return SelectedCourseFragment.class.getSimpleName();
    }
    private void setSpinnerNameBuildingAdapter(List<String> listApartment) {
        ArrayAdapter<String> spinnerNameBuildingAdapter = new ArrayAdapter<>(getActivity(), R.layout.spinner_item, listApartment);
        spinnerNameBuildingAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
         viewBinding.buildingSp.setAdapter(spinnerNameBuildingAdapter);
        viewBinding.buildingSp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                nameBuilding = spinnerNameBuildingAdapter.getItem(position);;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }
}