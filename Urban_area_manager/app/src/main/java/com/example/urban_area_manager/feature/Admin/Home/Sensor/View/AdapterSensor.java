package com.example.urban_area_manager.feature.Admin.Home.Sensor.View;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.example.urban_area_manager.R;
import com.example.urban_area_manager.core.recycleview.BaseRecycleAdapter;
import com.example.urban_area_manager.core.recycleview.BaseViewHolder;
import com.example.urban_area_manager.databinding.ItemSensorBinding;
import com.example.urban_area_manager.feature.Admin.Home.Sensor.Model.Sensor;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class AdapterSensor extends BaseRecycleAdapter<Sensor> {
    private OnItemClickListener onItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(Sensor sensor);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
    @Override
    public BaseViewHolder<?> createViewHolder(LayoutInflater inflater, ViewGroup parent, int viewType) {
        return new SensorViewHoder(
                ItemSensorBinding.inflate(
                        LayoutInflater.from(parent.getContext()),parent,false
                )
        );
    }

    public class SensorViewHoder extends BaseViewHolder<ItemSensorBinding>{

        public SensorViewHoder(@NonNull ItemSensorBinding viewBinding) {
            super(viewBinding);
        }

        @SuppressLint({"ResourceAsColor", "ResourceType"})
        @Override
        public void bindData(int position) {
            Sensor sensor = mData.get(position);

            getViewBinding().tvHumidity.setText(formatHumid(sensor.getHumid()));
            getViewBinding().tvTemperature.setText(formatTemp(sensor.getTemp()));
            getViewBinding().imageDevice.setImageResource(R.drawable.sensor);
//            getViewBinding().tvLocation.setText(sensor.getLocation());
            getViewBinding().tvGas.setText(formatGas(sensor.getGas()));
            getViewBinding().tvNameDevice.setText(formatDate(sensor.getTime()));


        getViewBinding().background.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(sensor);
                }
            }
        });
        }

    }
    private String formatHumid(float humid) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(humid).append(" ").append("%");
        return stringBuilder.toString();
    }

    private String formatTemp(float temp) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(temp).append(" ").append("°C");
        return stringBuilder.toString();
    }
    private String formatGas(double sad) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(" ").append(sad);
        return stringBuilder.toString();
    }

    private String formatDate(Date date) {
        StringBuilder stringBuilder = new StringBuilder();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm dd/MM/yyyy", Locale.getDefault());
        String dateString = sdf.format(date);
        stringBuilder.append("Cập nhật :").append(" ").append(dateString);
        return stringBuilder.toString();
    }
    public void notifyItemsChanged(Sensor sensor) {
        for (int index = 0; index < mData.size(); index++) {
            Sensor item = mData.get(index);
            if (item.getSerial().equals(sensor.getSerial())) {
                mData.get(index).setHumid(sensor.getHumid());
                mData.get(index).setTemp(sensor.getTemp());
                mData.get(index).setGas(sensor.getGas());
                mData.get(index).setTime(sensor.getTime());
                notifyItemChanged(index);
                return;
            }
        }
    }
}
