package com.example.urban_area_manager.feature.Resident.Home.Sensor;

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
//            Sensor sensor = mData.get(position);
//
//            getViewBinding().AQIValue.setText(String.valueOf((int) sensor.getAqi_value()));
//
//
//            if(sensor.getAqi_value()<=50){
//                getViewBinding().AQIName.setText(Constant.VERY_GOOD);
//                getViewBinding().colorItem.setCardBackgroundColor(itemView.getContext().getColor(R.color.aqi_verygood));
//            }
//            else if(50 <sensor.getAqi_value() && sensor.getAqi_value() <100){
//                getViewBinding().AQIName.setText(itemView.getContext().getColor(R.color.aqi_normal));
//                getViewBinding().colorItem.setCardBackgroundColor(itemView.getContext().getColor(R.color.aqi_normal));
//            }
//            else if(100 <= sensor.getAqi_value() && sensor.getAqi_value() <150){
//                getViewBinding().AQIName.setText(itemView.getContext().getColor(R.color.aqi_bad));
//                getViewBinding().colorItem.setCardBackgroundColor(itemView.getContext().getColor(R.color.aqi_bad));
//            }
//            else if( sensor.getAqi_value() >= 150){
//                getViewBinding().AQIName.setText(Constant.VERY_BAD);
//                getViewBinding().colorItem.setCardBackgroundColor(itemView.getContext().getColor(itemView.getContext().getColor(R.color.aqi_verybad)));
//            }
//
//
//            getViewBinding().areaName.setText(sensor.getName());
//            getViewBinding().humid.setText(formatHumid(sensor.getHumid()));
//            getViewBinding().temp.setText(formatTemp(sensor.getTemp()));
//            getViewBinding().wind.setText(formatWind(sensor.getWind()));
//            getViewBinding().sad.setText(formatSad(sensor.getSad()));
//            getViewBinding().timeUpdateSensor.setText(formatDate(sensor.getTime()));
//            getViewBinding().areaName.setText(sensor.getName());
//        getViewBinding().colorItem.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (onItemClickListener != null) {
//                    onItemClickListener.onItemClick(sensor);
//                }
//            }
//        });
            Sensor sensor = mData.get(position);

            getViewBinding().tvHumidity.setText(formatHumid(sensor.getHumid()));
            getViewBinding().tvTemperature.setText(formatTemp(sensor.getTemp()));
            getViewBinding().imageDevice.setImageResource(R.drawable.sensor);
            getViewBinding().tvNameDevice.setText(sensor.getName());
//            getViewBinding().tvGas.setText(formatGas(sensor.getHumid()));

            if(sensor.getHumid() >40){
                getViewBinding().background.setCardBackgroundColor(itemView.getContext().getColor(R.color.aqi_verybad));
                getViewBinding().imageDevice.setVisibility(View.INVISIBLE);
            }
            else if (30<sensor.getHumid() && sensor.getHumid()<=40){
                getViewBinding().background.setCardBackgroundColor(itemView.getContext().getColor(R.color.aqi_bad));
                getViewBinding().imageDevice.setVisibility(View.VISIBLE);
            }

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
    private String formatHumid(double humid) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(humid).append(" ").append("%");
        return stringBuilder.toString();
    }

    private String formatTemp(double temp) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(temp).append(" ").append("°C");
        return stringBuilder.toString();
    }
    private String formatSad(double sad) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("PM 2.5 :").append(" ").append(sad);
        return stringBuilder.toString();
    }
    private String formatWind(double wind) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(wind).append(" ").append("m/s");
        return stringBuilder.toString();
    }
    private String formatDate(Date date) {
        StringBuilder stringBuilder = new StringBuilder();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm dd/MM/yyyy4", Locale.getDefault());
        String dateString = sdf.format(date);
        stringBuilder.append("Cập nhật :").append(" ").append(dateString);
        return stringBuilder.toString();
    }
    private String formatGas(double sad) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("GAS :").append(" ").append(sad);
        return stringBuilder.toString();
    }
}
