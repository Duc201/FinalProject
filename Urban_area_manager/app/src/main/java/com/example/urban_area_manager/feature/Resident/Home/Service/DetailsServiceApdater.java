package com.example.urban_area_manager.feature.Resident.Home.Service;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.example.urban_area_manager.core.recycleview.BaseRecycleAdapter;
import com.example.urban_area_manager.core.recycleview.BaseViewHolder;
import com.example.urban_area_manager.databinding.ItemDetailBillFixedBinding;
import com.example.urban_area_manager.databinding.ItemServiceBinding;
import com.example.urban_area_manager.feature.Admin.Home.Bill.Model.DetailsService;

public class DetailsServiceApdater extends BaseRecycleAdapter<DetailsService> {
    private OnItemClickListener onItemClickListener;

    public interface OnItemClickListener {
        void onItemClickDelete(DetailsService detailsService);
    }
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
    @Override
    public BaseViewHolder<?> createViewHolder(LayoutInflater inflater, ViewGroup parent, int viewType) {
        return new DetailsServiceViewHoder(ItemServiceBinding.inflate(
                LayoutInflater.from(parent.getContext()),parent,false
        )
        );
    }
    public class DetailsServiceViewHoder extends BaseViewHolder<ItemServiceBinding> {
        public DetailsServiceViewHoder(@NonNull ItemServiceBinding viewBinding) {
            super(viewBinding);
        }

        @Override
        public void bindData(int position) {
            DetailsService detailsService = mData.get(position);
            getViewBinding().tvNameService.setText(detailsService.getNameService());
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(detailsService.getPrice()).append(" đ/");
            getViewBinding().price.setText(stringBuilder);
            getViewBinding().delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemClickListener != null) {
                        onItemClickListener.onItemClickDelete(detailsService);
                    }
                }
            });
        }
    }
}
