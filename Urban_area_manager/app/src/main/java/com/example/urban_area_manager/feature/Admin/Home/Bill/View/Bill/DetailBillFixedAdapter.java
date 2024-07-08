package com.example.urban_area_manager.feature.Admin.Home.Bill.View.Bill;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.example.urban_area_manager.core.recycleview.BaseRecycleAdapter;
import com.example.urban_area_manager.core.recycleview.BaseViewHolder;
import com.example.urban_area_manager.databinding.ItemDetailBillFixedBinding;
import com.example.urban_area_manager.databinding.ItemStepServiceBillBinding;
import com.example.urban_area_manager.feature.Admin.Home.Bill.Model.DetailsBillFix;
import com.example.urban_area_manager.utils.extensions.Extensions;

public class DetailBillFixedAdapter extends BaseRecycleAdapter<DetailsBillFix> {
    private OnItemClickListener onItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(DetailsBillFix detailsBillFix);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
    @Override
    public BaseViewHolder<?> createViewHolder(LayoutInflater inflater, ViewGroup parent, int viewType) {
        return new DetailBillFixedViewHoder(ItemDetailBillFixedBinding.inflate(
                LayoutInflater.from(parent.getContext()),parent,false
            )
        );
    }

    public class DetailBillFixedViewHoder extends BaseViewHolder<ItemDetailBillFixedBinding> {
        public DetailBillFixedViewHoder(@NonNull ItemDetailBillFixedBinding viewBinding) {
            super(viewBinding);
        }

        @Override
        public void bindData(int position) {
            DetailsBillFix detailsBillFix = mData.get(position);
            switch (detailsBillFix.getType()){
                case 0:
                    getViewBinding().nameDetailBill.setText("Internet");
                    break;
                case 1:
                    getViewBinding().nameDetailBill.setText("Dịch vụ tòa nhà");
                    break;
                case 2:
                    getViewBinding().nameDetailBill.setText("Phương tiện");
                    break;
            }
            getViewBinding().tvNameService.setText(detailsBillFix.getNamService());
            getViewBinding().tvCountService.setText(String.valueOf(detailsBillFix.getCount()));
            getViewBinding().tvSumService.setText(Extensions.formatMoney(detailsBillFix.getSumDetailBill()));getViewBinding().item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemClickListener != null) {
                        onItemClickListener.onItemClick(detailsBillFix);
                    }
                }
            });
        }
    }
}
