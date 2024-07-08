package com.example.urban_area_manager.feature.Employee.Repair;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.urban_area_manager.R;
import com.example.urban_area_manager.feature.Admin.Home.Building.Model.Building;

public class BuildingAdapter  extends ArrayAdapter<Building> {

    public BuildingAdapter(@NonNull Context context, int resource) {
        super(context, resource);
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_selected_spinner,parent,false);
        TextView tvSelected = convertView.findViewById(R.id.item_selected);

        Building building = this.getItem(position);

        if(building!=null){
            tvSelected.setText(building.getNameBuilding());
        }
        return convertView;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_spinner,parent,false);
        TextView textView = convertView.findViewById(R.id.item_spinner);

        Building building = this.getItem(position);
        if(building!=null){
            textView.setText(building.getNameBuilding());
        }

        return convertView;
    }
}
