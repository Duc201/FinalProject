package com.example.urban_area_manager.feature.Admin.Home.Apartment.View;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.example.urban_area_manager.core.recycleview.BaseRecycleAdapter;
import com.example.urban_area_manager.core.recycleview.BaseViewHolder;
import com.example.urban_area_manager.databinding.ItemResidentBinding;
import com.example.urban_area_manager.databinding.ItemResidentInApartmentBinding;
import com.example.urban_area_manager.feature.Admin.Home.Resident.Model.DetailsResident;

public class ResidentInApartmentAdapter  extends BaseRecycleAdapter<DetailsResident> {
    @Override
    public BaseViewHolder<?> createViewHolder(LayoutInflater inflater, ViewGroup parent, int viewType) {
        return new ResidentInApartmentViewHoder(
                ItemResidentInApartmentBinding.inflate(
                        LayoutInflater.from(parent.getContext()),parent,false
                ));
    }
    public class ResidentInApartmentViewHoder extends BaseViewHolder<ItemResidentInApartmentBinding> {
        public ResidentInApartmentViewHoder(@NonNull ItemResidentInApartmentBinding viewBinding) {
            super(viewBinding);
        }

        @Override
        public void bindData(int position) {
            DetailsResident detailsResident = mData.get(position);
            getViewBinding().name.setText(detailsResident.getNameUser());

            switch (detailsResident.getRelationShip()){
                case 0:
                    getViewBinding().relative.setText("Chủ Hộ");
                    break;
                case 1:
                    getViewBinding().relative.setText("Chồng");
                    break;
                case 2:
                    getViewBinding().relative.setText("Vợ");
                    break;
                case 3:
                    getViewBinding().relative.setText("Con trai");
                    break;
                case 4:
                    getViewBinding().relative.setText("Con gái");
                    break;
                case 5:
                    getViewBinding().relative.setText("Bố Vợ");
                    break;
                case 6:
                    getViewBinding().relative.setText("Mẹ Vợ");
                    break;
                case 7:
                    getViewBinding().relative.setText("Anh");
                    break;
                case 8:
                    getViewBinding().relative.setText("Em");
                    break;
                case 9:
                    getViewBinding().relative.setText("Chị");
                    break;
            }
        }
    }
}
