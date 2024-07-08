package com.example.urban_area_manager.feature.Admin.Home.Bill.View.Bill;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.example.urban_area_manager.core.recycleview.BaseRecycleAdapter;
import com.example.urban_area_manager.core.recycleview.BaseViewHolder;
import com.example.urban_area_manager.databinding.ItemStepServiceBillBinding;
import com.example.urban_area_manager.feature.Admin.Home.Bill.Model.DetailsBillStep;
import com.example.urban_area_manager.utils.extensions.Extensions;

public class DetailBillStepAdapter extends BaseRecycleAdapter<DetailsBillStep> {
    private OnItemClickListener onItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(DetailsBillStep detailsBillStep);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
    @Override
    public BaseViewHolder<?> createViewHolder(LayoutInflater inflater, ViewGroup parent, int viewType) {
        return new DetailBillViewHoder(ItemStepServiceBillBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false));
    }

    public class DetailBillViewHoder extends BaseViewHolder<ItemStepServiceBillBinding> {
        public DetailBillViewHoder(@NonNull ItemStepServiceBillBinding viewBinding) {
            super(viewBinding);
        }

        @Override
        public void bindData(int position) {
            DetailsBillStep detailsBillStep = mData.get(position);
            getViewBinding().nameDetailBill.setText(detailsBillStep.getNamService());
            getViewBinding().edtIndexNew.setText(String.valueOf(detailsBillStep.getNewIndex()));
            getViewBinding().edtIndexOld.setText(String.valueOf(detailsBillStep.getOldIndex()));
            getViewBinding().sum.setText(Extensions.formatMoney(detailsBillStep.getSumDetailBill()));
            getViewBinding().numberIndex.setText(String.valueOf(detailsBillStep.getNewIndex()- detailsBillStep.getOldIndex()));
            getViewBinding().item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(onItemClickListener!=null){
                        onItemClickListener.onItemClick(detailsBillStep);
                    }
                }
            });
        }
    }
}
