package com.example.urban_area_manager.feature.Admin.Home.Bill.View.Service;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.example.urban_area_manager.core.recycleview.BaseRecycleAdapter;
import com.example.urban_area_manager.core.recycleview.BaseViewHolder;
import com.example.urban_area_manager.databinding.ItemServiceBinding;
import com.example.urban_area_manager.feature.Admin.Home.Bill.Model.FixedService;

public class ServiceAdapter extends BaseRecycleAdapter<FixedService> {

    private OnItemClickListener onItemClickListener;

    public interface OnItemClickListener {
        void onItemClickEdit(FixedService fixedService);
        void onItemClickDelete(FixedService fixedService);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }


    @Override
    public BaseViewHolder<?> createViewHolder(LayoutInflater inflater, ViewGroup parent, int viewType) {
        return new ServiceViewHoder(ItemServiceBinding.inflate(  LayoutInflater.from(parent.getContext()),parent,false));
    }

    public class ServiceViewHoder extends BaseViewHolder<ItemServiceBinding> {
        public ServiceViewHoder(@NonNull ItemServiceBinding viewBinding) {
            super(viewBinding);
        }

        @Override
        public void bindData(int position) {
            FixedService fixedService = mData.get(position);

            getViewBinding().tvNameService.setText(fixedService.getName());
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(fixedService.getPrice()).append(" đ/").append(fixedService.getUnit());
            getViewBinding().price.setText(stringBuilder);

           getViewBinding().edit.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   if (onItemClickListener != null) {
                       onItemClickListener.onItemClickEdit(fixedService);
                   }
               }
           });
            getViewBinding().delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemClickListener != null) {
                        onItemClickListener.onItemClickDelete(fixedService);
                    }
                }
            });
        }
    }
}
