package com.example.urban_area_manager.feature.Admin.Home.Notification.View;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.example.urban_area_manager.core.recycleview.BaseRecycleAdapter;
import com.example.urban_area_manager.core.recycleview.BaseViewHolder;
import com.example.urban_area_manager.databinding.ItemNotificationBinding;
import com.example.urban_area_manager.feature.Admin.Home.Notification.Model.Notification;

import java.text.SimpleDateFormat;

public class NotificationAdapter extends BaseRecycleAdapter<Notification> {

    private OnItemClickListener onItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(Notification notification);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
    @Override
    public BaseViewHolder<?> createViewHolder(LayoutInflater inflater, ViewGroup parent, int viewType) {
        return new NotificationViewHoder(ItemNotificationBinding.inflate( LayoutInflater.from(parent.getContext()),parent,false));
    }

    public class NotificationViewHoder extends BaseViewHolder<ItemNotificationBinding> {
        public NotificationViewHoder(@NonNull ItemNotificationBinding viewBinding) {
            super(viewBinding);
        }

        @Override
        public void bindData(int position) {
            Notification notification = mData.get(position);
            getViewBinding().title.setText(notification.getTitle());
            getViewBinding().content.setText(notification.getContent());
            Glide.with(itemView.getContext())
                    .load(notification.getPathImage())
                    .into(getViewBinding().imgNotification);
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm");
            getViewBinding().time.setText(dateFormat.format(notification.getCreationTime()));
            getViewBinding().itemNotifi.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemClickListener != null) {
                        onItemClickListener.onItemClick(notification);
                    }
                }
            });
        }
    }
}
