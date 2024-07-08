package com.example.urban_area_manager.feature.Employee.Repair.Bill;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.example.urban_area_manager.core.recycleview.BaseRecycleAdapter;
import com.example.urban_area_manager.core.recycleview.BaseViewHolder;
import com.example.urban_area_manager.databinding.ItemDetailsBillBinding;
import com.example.urban_area_manager.feature.Admin.Home.Bill.Model.DetailsBillStep;

import java.text.SimpleDateFormat;

public class DetailsBillAdapter extends BaseRecycleAdapter<DetailsBillStep> {

    private OnItemClickListener onItemClickListener;
    public interface OnItemClickListener {
        void onItemClick(DetailsBillStep detailsBillStep);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
    @Override
    public BaseViewHolder<?> createViewHolder(LayoutInflater inflater, ViewGroup parent, int viewType) {
        return new  DetailsBillViewHolder(
                ItemDetailsBillBinding.inflate(
                        LayoutInflater.from(parent.getContext()),parent,false
                )
        );
    }
    public class DetailsBillViewHolder extends BaseViewHolder<ItemDetailsBillBinding> {
        public DetailsBillViewHolder(@NonNull ItemDetailsBillBinding viewBinding) {
            super(viewBinding);
        }

        @Override
        public void bindData(int position) {
            DetailsBillStep detailsBillStep = mData.get(position);
            getViewBinding().tvNameApartment.setText(detailsBillStep.getNameApartment());
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm");
            String time = simpleDateFormat.format(detailsBillStep.getLastModifiTime());
            getViewBinding().time.setText(time);
            getViewBinding().itemReflect.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemClickListener != null) {
                        onItemClickListener.onItemClick(detailsBillStep);
                    }
                }
            });
        }
    }
}
