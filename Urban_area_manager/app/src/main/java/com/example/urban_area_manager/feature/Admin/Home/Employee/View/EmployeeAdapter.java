package com.example.urban_area_manager.feature.Admin.Home.Employee.View;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.example.urban_area_manager.R;
import com.example.urban_area_manager.core.recycleview.BaseRecycleAdapter;
import com.example.urban_area_manager.core.recycleview.BaseViewHolder;
import com.example.urban_area_manager.databinding.ItemEmployeeBinding;
import com.example.urban_area_manager.feature.Admin.Home.Employee.Model.Employee;
import com.example.urban_area_manager.utils.Constant;

public class EmployeeAdapter extends BaseRecycleAdapter<Employee> {
    private OnItemClickListener onItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(Employee employee);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
    @Override
    public BaseViewHolder<?> createViewHolder(LayoutInflater inflater, ViewGroup parent, int viewType) {
        return new EmployeeViewHoder(ItemEmployeeBinding.inflate(  LayoutInflater.from(parent.getContext()),parent,false));
    }

    public class EmployeeViewHoder extends BaseViewHolder<ItemEmployeeBinding> {


        public EmployeeViewHoder(@NonNull ItemEmployeeBinding viewBinding) {
            super(viewBinding);
        }

        @Override
        public void bindData(int position) {
            Employee employee = mData.get(position);

            getViewBinding().employeeName.setText(employee.getFullName());

            if(employee.getDepartment() == 0){
                getViewBinding().departmentName.setText(Constant.SECURITY_DEPARTMENT);
            } else if (employee.getDepartment() == 1 ) {
                getViewBinding().departmentName.setText(Constant.REPAIR_DEPARTMENT);
            }
            else {
                getViewBinding().departmentName.setText(Constant.ADMINSTRAYION_DEPARTMENT);
            }
            Glide.with(itemView.getContext()).load(employee.getImageUrl()).placeholder(R.drawable.icons_user_blue).error(R.drawable.icons_user_blue).into(getViewBinding().imgResident);
            getViewBinding().codeEmployee.setText(employee.getEmail());

            getViewBinding().itemEmployee.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemClickListener != null) {
                        onItemClickListener.onItemClick(employee);
                    }
                }
            });

        }
    }

}
