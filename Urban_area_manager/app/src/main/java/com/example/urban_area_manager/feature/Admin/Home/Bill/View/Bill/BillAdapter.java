package com.example.urban_area_manager.feature.Admin.Home.Bill.View.Bill;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.example.urban_area_manager.R;
import com.example.urban_area_manager.core.recycleview.BaseRecycleAdapter;
import com.example.urban_area_manager.core.recycleview.BaseViewHolder;
import com.example.urban_area_manager.databinding.ItemBillBinding;
import com.example.urban_area_manager.feature.Admin.Home.Bill.Model.Bill;

import java.text.SimpleDateFormat;

public class BillAdapter extends BaseRecycleAdapter<Bill> {
    private OnItemClickListener onItemClickListener;

    public interface OnItemClickListener{
        void onItemClick(Bill bill);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
    @Override
    public BaseViewHolder<?> createViewHolder(LayoutInflater inflater, ViewGroup parent, int viewType) {
        return new BillViewHoder(ItemBillBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false));
    }

    public class BillViewHoder extends BaseViewHolder<ItemBillBinding> {


        public BillViewHoder(@NonNull ItemBillBinding viewBinding) {
            super(viewBinding);
        }

        @Override
        public void bindData(int position) {
            Bill bill = mData.get(position);
            getViewBinding().tvNameApartment.setText(bill.getNameApartment());
            StringBuilder sumBill = new StringBuilder();
            sumBill.append(bill.getSumBill()).append(" ").append("VNĐ");
            getViewBinding().tvSumBill.setText(sumBill);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
            String endDate  = simpleDateFormat.format(bill.getEndBill());
            getViewBinding().endDate.setText(endDate);
            if(bill.getPay()){
                getViewBinding().statePay.setText("Đã thanh toán");
                getViewBinding().statePay.setBackgroundColor(itemView.getContext().getColor(R.color.complete));
            }
            else{
                getViewBinding().statePay.setText("Chưa thanh toán");
                getViewBinding().statePay.setBackgroundColor(itemView.getContext().getColor(R.color.process));
            }
            getViewBinding().itemReflect.setOnClickListener(v->{
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(bill);
                }
            });
        }
    }
}
