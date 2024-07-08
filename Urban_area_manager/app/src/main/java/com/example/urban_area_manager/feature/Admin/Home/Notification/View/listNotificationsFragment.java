package com.example.urban_area_manager.feature.Admin.Home.Notification.View;

import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;

import com.example.urban_area_manager.R;
import com.example.urban_area_manager.core.base.BaseFragment;
import com.example.urban_area_manager.databinding.FragmentListNotificationsBinding;
import com.example.urban_area_manager.feature.Admin.Home.Notification.Model.Notification;
import com.example.urban_area_manager.feature.Admin.Home.ViewModel.NotificationViewModel;
import com.example.urban_area_manager.utils.Constant;


public class listNotificationsFragment extends BaseFragment<FragmentListNotificationsBinding, NotificationViewModel> {

private NotificationAdapter notificationAdapter = new NotificationAdapter();
private LinearLayoutManager linearLayoutManager ;

    @Override
    public void onCommonViewLoaded() {

        viewBinding.rcvListNotification.setAdapter(notificationAdapter);
        linearLayoutManager = new LinearLayoutManager(getContext());
        viewBinding.rcvListNotification.setLayoutManager(linearLayoutManager);
        viewModel.getAllNotification();



        notificationAdapter.setOnItemClickListener(new NotificationAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Notification notification) {
                Bundle bundle = new Bundle();
                bundle.putSerializable(Constant.GO_TO_DetailsNotificationFragment,notification);
                DetailsNotificationFragment detailsNotificationFragment = new DetailsNotificationFragment();
                openFragment(detailsNotificationFragment,R.id.frame_notifi,bundle);
            }
        });

    }

    @Override
    public void addViewListener() {
        viewBinding.btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               AddNotificationFragment addEmployeeFragment = new AddNotificationFragment();
               openFragment(addEmployeeFragment,R.id.frame_notifi);
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
    protected FragmentListNotificationsBinding getBinding(LayoutInflater inflater) {
        return FragmentListNotificationsBinding.inflate(getLayoutInflater());
    }

    @Override
    protected Class<NotificationViewModel> getViewModelClass() {
        return NotificationViewModel.class;
    }

    @Override
    protected String getTagFragment() {
        return listNotificationsFragment.class.getSimpleName();
    }
}