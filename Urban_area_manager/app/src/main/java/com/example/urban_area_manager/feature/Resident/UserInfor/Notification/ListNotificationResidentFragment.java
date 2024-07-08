package com.example.urban_area_manager.feature.Resident.UserInfor.Notification;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.urban_area_manager.R;
import com.example.urban_area_manager.core.base.BaseFragment;
import com.example.urban_area_manager.core.component.DataLocalManager;
import com.example.urban_area_manager.databinding.FragmentListNotificationResidentBinding;
import com.example.urban_area_manager.feature.Admin.Home.Notification.Model.Notification;
import com.example.urban_area_manager.feature.Admin.Home.Notification.View.DetailsNotificationFragment;
import com.example.urban_area_manager.feature.Admin.Home.Notification.View.NotificationAdapter;
import com.example.urban_area_manager.feature.Admin.Home.ViewModel.NotificationViewModel;
import com.example.urban_area_manager.utils.Constant;


public class ListNotificationResidentFragment extends BaseFragment<FragmentListNotificationResidentBinding, NotificationViewModel> {
    private NotificationAdapter notificationAdapter = new NotificationAdapter();
    private LinearLayoutManager linearLayoutManager ;
    @Override
    public void onCommonViewLoaded() {
        viewBinding.rcvListNotification.setAdapter(notificationAdapter);
        linearLayoutManager = new LinearLayoutManager(getContext());
        viewBinding.rcvListNotification.setLayoutManager(linearLayoutManager);
        viewModel.getAllNotificationResident(DataLocalManager.getIdUser());



        notificationAdapter.setOnItemClickListener(new NotificationAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Notification notification) {
                Bundle bundle = new Bundle();
                bundle.putSerializable(Constant.GO_TO_DetailsNotificationFragment,notification);
                DetailsNotificationResidentFragment detailsNotificationFragment = new DetailsNotificationResidentFragment();
                openFragment(detailsNotificationFragment,R.id.frame_notifi_employee,bundle);
            }
        });

    }

    @Override
    public void addViewListener() {
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
    protected FragmentListNotificationResidentBinding getBinding(LayoutInflater inflater) {
        return FragmentListNotificationResidentBinding.inflate(getLayoutInflater());
    }

    @Override
    protected Class<NotificationViewModel> getViewModelClass() {
        return NotificationViewModel.class;
    }

    @Override
    protected String getTagFragment() {
        return ListNotificationResidentFragment.class.getSimpleName();
    }
}