package com.example.urban_area_manager.feature.Admin.Home.Apartment.View;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

import com.bumptech.glide.Glide;
import com.example.urban_area_manager.core.recycleview.BaseViewHolder1;
import com.example.urban_area_manager.databinding.ItemApartmentBinding;
import com.example.urban_area_manager.feature.Admin.Home.Apartment.Model.Apartment;
import com.example.urban_area_manager.utils.Constant;

public class ApartmentAdapter1 extends ListAdapter<Apartment, ApartmentAdapter1.ApartmentViewHolder> {

    private OnItemClickListener onItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(Apartment apartment);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public ApartmentAdapter1() {
        super(new DiffUtil.ItemCallback<Apartment>() {
            @Override
            public boolean areItemsTheSame(@NonNull Apartment oldItem, @NonNull Apartment newItem) {
                return oldItem.getIdBuilding().equals(newItem.getIdBuilding());
            }

            @SuppressLint("DiffUtilEquals")
            @Override
            public boolean areContentsTheSame(@NonNull Apartment oldItem, @NonNull Apartment newItem) {
                return oldItem.equals(newItem);
            }
        });
    }



    @NonNull
    @Override
    public ApartmentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemApartmentBinding binding = ItemApartmentBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ApartmentViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ApartmentViewHolder holder, int position) {
        Apartment apartment = getItem(position);
        holder.bindData(apartment);
    }

    public class ApartmentViewHolder extends BaseViewHolder1<ItemApartmentBinding> {

        public ApartmentViewHolder(ItemApartmentBinding viewBinding) {
            super(viewBinding);
        }

        @Override
        public void bindData(Object item) {
            Apartment apartment = (Apartment) item;
            getViewBinding().name.setText(apartment.getName());
            if (apartment.getState() == 1) {
                getViewBinding().status.setText(Constant.Notbuy);
            } else {
                getViewBinding().status.setText(Constant.Buy);
            }
            Glide.with(itemView.getContext()).load(apartment.getImagePath()).into(getViewBinding().imgApartment);

            getViewBinding().itemApartment.setOnClickListener(v -> {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(apartment);
                }
            });
        }
    }
}
