package com.example.urban_area_manager.feature.Admin.Home.Notification;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.urban_area_manager.R;
import com.example.urban_area_manager.feature.Admin.Home.Resident.Model.DetailsResident;

public class ResidentAdapter extends ArrayAdapter<DetailsResident> {
    public ResidentAdapter(@NonNull Context context, int resource) {

        super(context, resource);
    }
    // Hiển thị khi để im( 1 cái )
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_selected_spinner,parent,false);
        TextView tvSelected = convertView.findViewById(R.id.item_selected);

        DetailsResident resident = this.getItem(position);

        if(resident!=null){
            tvSelected.setText(resident.getNameUser());
        }
        return convertView;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_spinner,parent,false);
        TextView textView = convertView.findViewById(R.id.item_spinner);

        DetailsResident resident = this.getItem(position);
        if(resident!=null){
            textView.setText(resident.getNameUser());
        }

        return convertView;    }
}
