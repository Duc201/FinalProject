package com.example.urban_area_manager.feature.Employee.NotificationEmployee;

import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;

import com.example.urban_area_manager.R;
import com.example.urban_area_manager.core.base.BaseFragment;
import com.example.urban_area_manager.core.component.DataLocalManager;
import com.example.urban_area_manager.databinding.FragmentListNotifiEmployeeBinding;
import com.example.urban_area_manager.feature.Admin.Home.Notification.Model.Notification;
import com.example.urban_area_manager.feature.Admin.Home.Notification.View.DetailsNotificationFragment;
import com.example.urban_area_manager.feature.Admin.Home.Notification.View.NotificationAdapter;
import com.example.urban_area_manager.feature.Admin.Home.ViewModel.NotificationViewModel;
import com.example.urban_area_manager.utils.Constant;


public class ListNotifiEmployeeFragment extends BaseFragment<FragmentListNotifiEmployeeBinding, NotificationViewModel> {
    private NotificationAdapter notificationAdapter = new NotificationAdapter();
    private LinearLayoutManager linearLayoutManager ;

    @Override
    public void onCommonViewLoaded() {
        viewBinding.rcvListNotification.setAdapter(notificationAdapter);
        linearLayoutManager = new LinearLayoutManager(getContext());
        viewBinding.rcvListNotification.setLayoutManager(linearLayoutManager);
        // 2 hành chình
        // 1 sửa chữa
        // 0 ann ninh
        if(DataLocalManager.getPosition() == 0){
            viewModel.getAllNotificationEmploy(0);
        }
        else if (DataLocalManager.getPosition() == 1){
            viewModel.getAllNotificationEmploy(1);
        }
        else
            viewModel.getAllNotificationEmploy1(2,3);



        notificationAdapter.setOnItemClickListener(new NotificationAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Notification notification) {
                Bundle bundle = new Bundle();
                bundle.putSerializable(Constant.GO_TO_DetailsNotificationFragment,notification);
                DetailsNotificationFragment detailsNotificationFragment = new DetailsNotificationFragment();
                openFragment(detailsNotificationFragment,R.id.frame_notifi_employee,bundle);
            }
        });
    }

    @Override
    public void addViewListener() {
        viewBinding.btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(DataLocalManager.getPosition() == 0 || DataLocalManager.getPosition() == 1){
                    AddNotificationEmployeeFragment addEmployeeFragment = new AddNotificationEmployeeFragment();
                    openFragment(addEmployeeFragment,R.id.frame_notifi_employee);
                } else if (DataLocalManager.getPosition() == 2) {
                    AddNotificationEmployee2Fragment addEmployeeFragment = new AddNotificationEmployee2Fragment();
                    openFragment(addEmployeeFragment,R.id.frame_notifi_employee);
                }


            }
        });
        viewBinding.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requireActivity().getSupportFragmentManager().popBackStack();
            }
        });
    }

    @Override
    public void addDataObserve() {
        super.addDataObserve();
        viewModel.listNotification.observe(this,list->{
            notificationAdapter.clearList();
            notificationAdapter.submitList(list);
        });
    }

    @Override
    protected FragmentListNotifiEmployeeBinding getBinding(LayoutInflater inflater) {
        return FragmentListNotifiEmployeeBinding.inflate(getLayoutInflater());
    }

    @Override
    protected Class<NotificationViewModel> getViewModelClass() {
        return NotificationViewModel.class;
    }

    @Override
    protected String getTagFragment() {
        return ListNotifiEmployeeFragment.class.getSimpleName();
    }
}