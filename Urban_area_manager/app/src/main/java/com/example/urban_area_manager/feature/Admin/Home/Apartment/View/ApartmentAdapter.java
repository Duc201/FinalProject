package com.example.urban_area_manager.feature.Admin.Home.Apartment.View;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.example.urban_area_manager.core.recycleview.BaseRecycleAdapter;
import com.example.urban_area_manager.core.recycleview.BaseViewHolder;
import com.example.urban_area_manager.feature.Admin.Home.Apartment.Model.Apartment;
import com.example.urban_area_manager.databinding.ItemApartmentBinding;
import com.example.urban_area_manager.feature.Admin.Home.Building.Model.Building;
import com.example.urban_area_manager.utils.Constant;

import java.util.ArrayList;
import java.util.List;

public class ApartmentAdapter extends BaseRecycleAdapter<Apartment> implements Filterable {

    private OnItemClickListener onItemClickListener;

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String strSearch = constraint.toString();
                if(strSearch.isEmpty()){
                    if(mData.isEmpty()){
                        mData = mDataold;
                    }
                }
                else {
                    List<Apartment> list =  new ArrayList<>();
                    for(Apartment apartment : mDataold){
                        if(apartment.getName().toLowerCase().contains(strSearch.toLowerCase())){
                            list.add(apartment);
                        }
                    }
                    mData =list;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = mData;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                mData = (List<Apartment>) results.values;
                notifyDataSetChanged();
            }
        };
    }

    public interface OnItemClickListener {
        void onItemClick(Apartment apartment);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
    @Override
    public BaseViewHolder<?> createViewHolder(LayoutInflater inflater, ViewGroup parent, int viewType) {
        return new ApartmentViewHoder(
                ItemApartmentBinding.inflate(
                        LayoutInflater.from(parent.getContext()),parent,false
                )
        );
    }

    public class ApartmentViewHoder extends BaseViewHolder<ItemApartmentBinding> {
        public ApartmentViewHoder(@NonNull ItemApartmentBinding viewBinding) {
            super(viewBinding);
        }

        @SuppressLint("SuspiciousIndentation")
        @Override
        public void bindData(int position) {
            Apartment apartment = mData.get(position);
            getViewBinding().name.setText(apartment.getName());
            if (apartment.getState() == 1) {
                getViewBinding().status.setText(Constant.Notbuy);
            } else
                getViewBinding().status.setText(Constant.Buy);
            Glide.with(itemView.getContext()).load(apartment.getImagePath()).into(getViewBinding().imgApartment);
            getViewBinding().itemApartment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemClickListener != null) {
                        onItemClickListener.onItemClick(apartment);
                    }
                }

            });
        }

    }
    }


