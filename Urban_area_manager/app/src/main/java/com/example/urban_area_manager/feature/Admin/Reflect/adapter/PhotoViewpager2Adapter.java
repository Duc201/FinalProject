package com.example.urban_area_manager.feature.Admin.Reflect.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.example.urban_area_manager.core.recycleview.BaseRecycleAdapter;
import com.example.urban_area_manager.core.recycleview.BaseViewHolder;
import com.example.urban_area_manager.databinding.ItemPhotoBinding;
import com.example.urban_area_manager.databinding.ItemReflectBinding;
import com.example.urban_area_manager.feature.Admin.Reflect.Model.Photo;

public class PhotoViewpager2Adapter extends BaseRecycleAdapter<Photo> {
    private OnItemClickListener onItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(Photo photo);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
    @Override
    public BaseViewHolder<?> createViewHolder(LayoutInflater inflater, ViewGroup parent, int viewType) {
        return new PhotoViewHolder(
                ItemPhotoBinding.inflate(
                        LayoutInflater.from(parent.getContext()),parent,false
                )
        );
    }
    public class PhotoViewHolder extends BaseViewHolder<ItemPhotoBinding> {
        public PhotoViewHolder(@NonNull ItemPhotoBinding viewBinding) {
            super(viewBinding);
        }

        @Override
        public void bindData(int position) {
            Photo photo = mData.get(position);
            Glide.with(itemView.getContext()).load(photo.getResourceId()).into(getViewBinding().imgPhoto);
            getViewBinding().imgPhoto.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemClickListener != null) {
                        onItemClickListener.onItemClick(photo);
                    }
                }
            });
        }
    }
}
