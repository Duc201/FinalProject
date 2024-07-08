package com.example.urban_area_manager.feature.Admin.Home.Bill.View.StepService;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.example.urban_area_manager.core.recycleview.BaseRecycleAdapter;
import com.example.urban_area_manager.core.recycleview.BaseViewHolder;
import com.example.urban_area_manager.databinding.ItemStepServiceBinding;
import com.example.urban_area_manager.feature.Admin.Home.Bill.Model.StepService;

public class StepServiceAdapter extends BaseRecycleAdapter<StepService> {

    private OnItemClickListener onItemClickListener;

    public interface OnItemClickListener {
        void onItemClickDetails(StepService service);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public BaseViewHolder<?> createViewHolder(LayoutInflater inflater, ViewGroup parent, int viewType) {
        return new StepServiceViewHoder(ItemStepServiceBinding.inflate(  LayoutInflater.from(parent.getContext()),parent,false));
    }

    public class StepServiceViewHoder extends BaseViewHolder<ItemStepServiceBinding>{
        public StepServiceViewHoder(@NonNull ItemStepServiceBinding viewBinding) {
            super(viewBinding);
        }

        @Override
        public void bindData(int position) {
            StepService stepService = mData.get(position);
            getViewBinding().tvNameService.setText(stepService.getName());
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Đơn vị: ").append(stepService.getUnit());
            getViewBinding().price.setText(stringBuilder);
            getViewBinding().itemService.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemClickListener != null) {
                        onItemClickListener.onItemClickDetails(stepService);
                    }
                }
            });
        }
    }
}
