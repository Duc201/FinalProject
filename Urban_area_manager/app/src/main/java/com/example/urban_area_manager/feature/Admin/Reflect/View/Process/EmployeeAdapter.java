package com.example.urban_area_manager.feature.Admin.Reflect.View.Process;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.urban_area_manager.R;
import com.example.urban_area_manager.feature.Admin.Home.Employee.Model.Employee;

public class EmployeeAdapter extends ArrayAdapter<Employee> {
    public EmployeeAdapter(@NonNull Context context, int resource) {
        super(context, resource);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_selected_spinner,parent,false);
        TextView tvSelected = convertView.findViewById(R.id.item_selected);

        Employee employee = this.getItem(position);

        if(employee!=null){
            tvSelected.setText(employee.getFullName());
        }
        return convertView;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_spinner,parent,false);
        TextView textView = convertView.findViewById(R.id.item_spinner);

        Employee employee = this.getItem(position);
        if(employee!=null){
            textView.setText(employee.getFullName());
        }

        return convertView;
    }
}
