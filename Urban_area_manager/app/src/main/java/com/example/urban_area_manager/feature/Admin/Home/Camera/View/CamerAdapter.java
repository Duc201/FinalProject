package com.example.urban_area_manager.feature.Admin.Home.Camera.View;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.example.urban_area_manager.core.recycleview.BaseRecycleAdapter;
import com.example.urban_area_manager.core.recycleview.BaseViewHolder;
import com.example.urban_area_manager.databinding.ItemCameraBinding;
import com.example.urban_area_manager.feature.Admin.Home.Camera.Model.Camera;

public class CamerAdapter extends BaseRecycleAdapter<Camera> {

    private OnItemClickListener onItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(Camera camera);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
    @Override
    public BaseViewHolder<?> createViewHolder(LayoutInflater inflater, ViewGroup parent, int viewType) {
        return new CameraViewHoder(ItemCameraBinding.inflate( LayoutInflater.from(parent.getContext()),parent,false));
    }
    public class CameraViewHoder extends BaseViewHolder<ItemCameraBinding> {
        public CameraViewHoder(@NonNull ItemCameraBinding viewBinding) {
            super(viewBinding);
        }

        @Override
        public void bindData(int position) {
            Camera camera = mData.get(position);
            getViewBinding().nameCamera.setText(camera.getArea());
            getViewBinding().nameArea.setText(camera.getName());
            getViewBinding().itemCamera.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemClickListener != null) {
                        onItemClickListener.onItemClick(camera);
                    }
                }
            });
        }
    }
}
