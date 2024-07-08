package com.example.urban_area_manager.feature.Admin.Home.Resident.View;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import androidx.core.content.ContextCompat;

import com.example.urban_area_manager.R;
import com.example.urban_area_manager.core.base.BaseActivity;
import com.example.urban_area_manager.core.component.DataLocalManager;
import com.example.urban_area_manager.databinding.FragmentEditDetailsResidentBinding;
import com.example.urban_area_manager.feature.Auth.ui.adapter.ApartmentAdapter;
import com.example.urban_area_manager.feature.Auth.ui.adapter.BuildingAdapter;
import com.example.urban_area_manager.feature.Admin.Home.Apartment.Model.Apartment;
import com.example.urban_area_manager.feature.Admin.Home.Building.Model.Building;
import com.example.urban_area_manager.feature.Admin.Home.Resident.Model.DetailsResident;
import com.example.urban_area_manager.feature.Admin.Home.Resident.ViewModel.ResidentViewModel;
import com.example.urban_area_manager.utils.Constant;
import com.example.urban_area_manager.utils.extensions.Extensions;
import com.google.firebase.Timestamp;


public class EditDetailsResidentActivity extends BaseActivity<FragmentEditDetailsResidentBinding, ResidentViewModel> {

    private BuildingAdapter buildingAdapter;
    private ApartmentAdapter apartmentAdapter;
    private ArrayAdapter<CharSequence> spinnerRelativeAdapter;

    private DetailsResident detailsResident;



    @Override
    public void onCommonViewLoaded() {
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.light_background));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        detailsResident = (DetailsResident) getIntent().getSerializableExtra(Constant.GO_TO_DetailsResidentFragment);
            viewModel.getlistBuilding();
            buildingAdapter = new BuildingAdapter(this,R.layout.item_selected_spinner);
            binding.spinBuilding.setAdapter(buildingAdapter);

            apartmentAdapter = new ApartmentAdapter(this,R.layout.item_selected_spinner);
            binding.spinApartment.setAdapter(apartmentAdapter);

            spinnerRelativeAdapter = ArrayAdapter.createFromResource(this, R.array.spinner_relative, R.layout.spinner_item);
            spinnerRelativeAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
            binding.spinRelative.setAdapter(spinnerRelativeAdapter);

            binding.edtResidentName.setText(detailsResident.getNameUser());
            binding.spinRelative.setSelection(detailsResident.getRelationShip());
    }

    @Override
    public void addViewListener() {
            binding.spinBuilding.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    Building building = (Building) parent.getItemAtPosition(position);
                    viewModel.getListApartment(building.getIdBuilding());
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
            binding.btnUpdate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Building building = (Building) binding.spinBuilding.getSelectedItem();
                    Apartment apartment = (Apartment) binding.spinApartment.getSelectedItem();
                    int idRelative = binding.spinRelative.getSelectedItemPosition();
                    detailsResident.setRelationShip(idRelative);
                    detailsResident.setBuildingCode(building.getIdBuilding());
                    detailsResident.setBuildingName(building.getNameBuilding());
                    detailsResident.setApartmentName(apartment.getName());
                    detailsResident.setApartmentCode(apartment.getIdApartment());
                    detailsResident.setLastModificationTime(Timestamp.now().toDate());
                    detailsResident.setLastModifierUserId(DataLocalManager.getEmail());
                    viewModel.updateDetailsResident(detailsResident);
                }
            });
    }

    @Override
    public void addDataObserve() {
        super.addDataObserve();
        viewModel._listApartment.observe(this,listApartment->{
            apartmentAdapter.clear();
            apartmentAdapter.addAll(listApartment);
            apartmentAdapter.notifyDataSetChanged();
            if (detailsResident.getApartmentCode() != null) {
                for (int i = 0; i < listApartment.size(); i++) {
                    if (listApartment.get(i).getIdApartment().equals(detailsResident.getApartmentCode())) {
                        binding.spinApartment.setSelection(i);
                        break;
                    }
                }
            }
        });
        viewModel._listbuilding.observe(this,listBuilding->{
            buildingAdapter.clear();
            buildingAdapter.addAll(listBuilding);
            buildingAdapter.notifyDataSetChanged();
            if (detailsResident.getBuildingCode() != null) {
                for (int i = 0; i < listBuilding.size(); i++) {
                    if (listBuilding.get(i).getIdBuilding().equals(detailsResident.getBuildingCode())) {
                        binding.spinBuilding.setSelection(i);
                        break;
                    }
                }
            }
        });
        viewModel.isUpdateDetailsResident.observe(this,is->{
            if(is){
                Extensions.showToastShort(this,"Cập nhật thành công");
                Intent resultIntent = new Intent();
//                String
//                resultIntent.putExtra(Constant.GO_TO_DetailsResidentFragment, updatedDetailsResident);
                setResult(Activity.RESULT_OK, resultIntent);
             finish();
            }

        });

    }



    @Override
    protected FragmentEditDetailsResidentBinding getViewBinding() {
        return FragmentEditDetailsResidentBinding.inflate(getLayoutInflater());
    }

    @Override
    protected Class<ResidentViewModel> getViewModelClass() {
        return ResidentViewModel.class;
    }

}