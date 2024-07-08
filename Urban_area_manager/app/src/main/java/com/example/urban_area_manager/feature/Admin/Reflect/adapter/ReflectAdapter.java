package com.example.urban_area_manager.feature.Admin.Reflect.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.example.urban_area_manager.R;
import com.example.urban_area_manager.core.recycleview.BaseRecycleAdapter;
import com.example.urban_area_manager.core.recycleview.BaseViewHolder;
import com.example.urban_area_manager.databinding.ItemReflectBinding;

import com.example.urban_area_manager.feature.Admin.Reflect.Model.Reflect;
import com.example.urban_area_manager.utils.Constant;

import java.text.SimpleDateFormat;

public class ReflectAdapter extends BaseRecycleAdapter<Reflect> {
    private OnItemClickListener onItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(Reflect reflect);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
    @Override
    public BaseViewHolder<?> createViewHolder(LayoutInflater inflater, ViewGroup parent, int viewType) {
        return new RefelctViewHoder(
                ItemReflectBinding.inflate(
                        LayoutInflater.from(parent.getContext()),parent,false
                )
        );
    }
    public class RefelctViewHoder extends BaseViewHolder<ItemReflectBinding> {
        public RefelctViewHoder(@NonNull ItemReflectBinding viewBinding) {
            super(viewBinding);
        }

        @Override
        public void bindData(int position) {
            Reflect pention = mData.get(position);
            getViewBinding().location.setText(pention.getLoaction());
            getViewBinding().content.setText(pention.getContent());
            Glide.with(itemView.getContext())
                    .load(pention.getImage1())
                    .into(getViewBinding().imgRefect);
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm");
            getViewBinding().time.setText(dateFormat.format(pention.getTimeCreator()));

            switch (pention.getState()){
                case 0:
                    getViewBinding().state.setText(Constant.PROCESS);
                    getViewBinding().state.setBackgroundColor(itemView.getContext().getColor(R.color.process));
                    break;
                case 1:
                    getViewBinding().state.setText(Constant.ASSIGNED);
                    getViewBinding().state.setBackgroundColor(itemView.getContext().getColor(R.color.assigned));
                    break;
                case 2:
                    getViewBinding().state.setText(Constant.COMPLETE);
                    getViewBinding().state.setBackgroundColor(itemView.getContext().getColor(R.color.complete));
                    break;

            }
            getViewBinding().itemReflect.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemClickListener != null) {
                        onItemClickListener.onItemClick(pention);
                    }
                }
            });
        }
    }
}
