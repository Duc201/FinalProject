package com.example.urban_area_manager.feature.Admin.Home.Building.View;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.example.urban_area_manager.R;
import com.example.urban_area_manager.core.recycleview.BaseRecycleAdapter;
import com.example.urban_area_manager.core.recycleview.BaseViewHolder;
import com.example.urban_area_manager.feature.Admin.Home.Building.Model.Building;
import com.example.urban_area_manager.databinding.ItemBuildingBinding;

import java.util.ArrayList;
import java.util.List;

public class BuildingAdapter extends BaseRecycleAdapter<Building> implements Filterable {

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
                    List<Building> list =  new ArrayList<>();
                    for(Building building : mDataold){
                        if(building.getNameBuilding().toLowerCase().contains(strSearch.toLowerCase())){
                            list.add(building);
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
                mData = (List<Building>) results.values;
                notifyDataSetChanged();
            }
        };
    }


    public interface OnItemClickListener {
        void onItemClick(Building building);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
    @Override
    public BaseViewHolder<?> createViewHolder(LayoutInflater inflater, ViewGroup parent, int viewType) {
        return new BuildingViewHodel(
                ItemBuildingBinding.inflate(
                        LayoutInflater.from(parent.getContext()),parent,false
                )
        );
    }

    public class BuildingViewHodel extends BaseViewHolder<ItemBuildingBinding> {
        public BuildingViewHodel(@NonNull ItemBuildingBinding viewBinding) {
            super(viewBinding);
        }

        @Override
        public void bindData(int position) {
            Building building = mData.get(position);
            getViewBinding().nameBuilding.setText(building.getNameBuilding());
            StringBuilder stringBuilderfloor = new StringBuilder();
            stringBuilderfloor.append(building.getFloorNumber()).append(" ").append("tầng");
            getViewBinding().floorBuilding.setText(stringBuilderfloor);
            Glide.with(itemView.getContext()).load(building.getImageUrl()).placeholder(R.drawable.ic_apartment).error(R.drawable.ic_apartment).into(getViewBinding().imgBuilding);
            getViewBinding().itemBuilding.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemClickListener != null) {
                        onItemClickListener.onItemClick(building);
                    }
                }
            });
        }
    }
}
