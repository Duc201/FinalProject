package com.example.urban_area_manager.feature.Admin.Home.Resident.View;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.example.urban_area_manager.R;
import com.example.urban_area_manager.core.recycleview.BaseRecycleAdapter;
import com.example.urban_area_manager.core.recycleview.BaseViewHolder;
import com.example.urban_area_manager.feature.Admin.Home.Resident.Model.Resident;
import com.example.urban_area_manager.databinding.ItemResidentBinding;

public class ResidentAdapter extends BaseRecycleAdapter<Resident> {
    private OnItemClickListener onItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(Resident resident);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
    @Override
    public BaseViewHolder<?> createViewHolder(LayoutInflater inflater, ViewGroup parent, int viewType) {
        return new ResidentViewHolder(
                ItemResidentBinding.inflate(
                        LayoutInflater.from(parent.getContext()),parent,false
                ));
    }

    public class ResidentViewHolder extends BaseViewHolder<ItemResidentBinding> {
        public ResidentViewHolder(@NonNull ItemResidentBinding viewBinding) {
            super(viewBinding);
        }

        @Override
        public void bindData(int position) {
                Resident resident = mData.get(position);
                getViewBinding().residentName.setText(resident.getFullName());
                getViewBinding().indentityNumber.setText(resident.getIndentityNumber());
                getViewBinding().apartmentEmail.setText(resident.getEmail());
                if(resident.getState() == 0){
                    getViewBinding().state.setText("Chưa xác thực");
                    getViewBinding().state.setBackgroundColor(itemView.getContext().getColor(R.color.process));
                }
                else {
                    getViewBinding().state.setText("Đã xác thực");
                    getViewBinding().state.setBackgroundColor(itemView.getContext().getColor(R.color.complete));
                }
            Glide.with(itemView.getContext()).load(resident.getImageUrl()).into(getViewBinding().imgResident);

            getViewBinding().itemResident.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (onItemClickListener != null) {
                            onItemClickListener.onItemClick(resident);                    }
                    }
                });
        }
    }
}
