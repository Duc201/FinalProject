package com.example.urban_area_manager.feature.Resident.Home.Bill.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.example.urban_area_manager.R;
import com.example.urban_area_manager.core.recycleview.BaseRecycleAdapter;
import com.example.urban_area_manager.core.recycleview.BaseViewHolder;
import com.example.urban_area_manager.databinding.ItemBillUserBinding;
import com.example.urban_area_manager.feature.Admin.Home.Bill.Model.Bill;
import com.example.urban_area_manager.utils.extensions.Extensions;

import java.text.SimpleDateFormat;

public class BillUserAdapter extends BaseRecycleAdapter<Bill> {
    private OnItemClickListener onItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(Bill bill);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
    @Override
    public BaseViewHolder<?> createViewHolder(LayoutInflater inflater, ViewGroup parent, int viewType) {
        return new BillUserViewHoder(ItemBillUserBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false));
    }
    public class BillUserViewHoder extends BaseViewHolder<ItemBillUserBinding>{

        public BillUserViewHoder(@NonNull ItemBillUserBinding viewBinding) {
            super(viewBinding);
        }

        @Override
        public void bindData(int position) {
            Bill bill = mData.get(position);
            getViewBinding().nameBill.setText(bill.getNameBill());
            if(bill.getPay()){
                getViewBinding().stateBill.setText("Đã thanh toán");
                getViewBinding().stateBill.setBackgroundColor(itemView.getContext().getColor(R.color.complete));
            }
            else {
                getViewBinding().stateBill.setText("Chưa thanh toán");
                getViewBinding().stateBill.setBackgroundColor(itemView.getContext().getColor(R.color.process));
            }
            getViewBinding().tvSumBill.setText(Extensions.formatMoney(bill.getSumBill()));
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
            String dateEnd = simpleDateFormat.format(bill.getEndBill());
            getViewBinding().endDate.setText(dateEnd);
            getViewBinding().itemBuilding.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemClickListener != null) {
                        onItemClickListener.onItemClick(bill);
                    }
                }
            });
        }

    }
}
