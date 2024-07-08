package com.example.urban_area_manager.feature.Auth.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.urban_area_manager.R;
import com.example.urban_area_manager.data.model.Object_Spinner;

import java.util.List;

public class SpinnerAdapter  extends ArrayAdapter<Object_Spinner> {
    public SpinnerAdapter(@NonNull Context context, int resource, @NonNull List<Object_Spinner> objects) {
        super(context, resource, objects);
    }


  // Trường hợp hiển thị 1 phần tử
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_selected_spinner,parent,false);
        TextView tvSelected = convertView.findViewById(R.id.item_selected);

        Object_Spinner object_spinner = this.getItem(position);

        if(object_spinner!=null){
            tvSelected.setText(object_spinner.getName());
        }
        return convertView;

    }


    // Trường hợp hiển thị cả bbảng
    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
       convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_spinner,parent,false);
       TextView textView = convertView.findViewById(R.id.item_spinner);

       Object_Spinner object_spinner = this.getItem(position);

       if(object_spinner!=null){
           textView.setText(object_spinner.getName());
       }

        return convertView;
    }
}
