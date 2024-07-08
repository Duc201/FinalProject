package com.example.urban_area_manager.feature.Admin.Home.Notification.View;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.urban_area_manager.R;
import com.example.urban_area_manager.core.base.BaseFragment;
import com.example.urban_area_manager.core.component.DataLocalManager;
import com.example.urban_area_manager.databinding.FragmentDetailsNotificationBinding;
import com.example.urban_area_manager.feature.Admin.Home.Notification.Model.Notification;
import com.example.urban_area_manager.feature.Admin.Home.ViewModel.NotificationViewModel;
import com.example.urban_area_manager.utils.Constant;
import com.example.urban_area_manager.utils.extensions.Extensions;
import com.example.urban_area_manager.utils.view.DialogView;
import com.google.firebase.Timestamp;

import java.text.SimpleDateFormat;


public class DetailsNotificationFragment extends BaseFragment<FragmentDetailsNotificationBinding, NotificationViewModel> {

    Notification notification;
    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_toolbar_delete, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
           if (id == R.id.delete) {
                    Runnable listenerPositive = () -> {
                        notification.setDeleted(true);
                        notification.setDeleterUserId(DataLocalManager.getIdUser());
                        notification.setDeletionTime(Timestamp.now().toDate());
                        viewModel.deleteNotification(notification);
                    };
                    DialogView.showDialogDescriptionByHtml(getActivity(),"Xác nhận","Bạn có muốn xóa thông báo này không? ",listenerPositive);
                    return true;
                }
                return super.onOptionsItemSelected(item);
    }
    @Override
    public void onCommonViewLoaded() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            notification = (Notification) bundle.getSerializable(Constant.GO_TO_DetailsNotificationFragment);
        }
        AppCompatActivity activity = (AppCompatActivity) requireActivity();
        activity.setSupportActionBar(viewBinding.toolbar);
        viewBinding.title.setText(notification.getTitle());
        viewBinding.content.setText(notification.getContent());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        viewBinding.tvTime.setText(simpleDateFormat.format(notification.getCreationTime()));
        viewBinding.tvSender.setText(notification.getNameSender());
        Glide.with(this).load(notification.getPathImage()).into(viewBinding.imgNotifidetail);
        viewBinding.toolbar.setTitle(notification.getTitle());
    }

    @Override
    public void addViewListener() {
        viewBinding.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getParentFragmentManager().popBackStack();
            }
        });

    }

    @Override
    public void addDataObserve() {
        super.addDataObserve();
        viewModel.deleteNotification.observe(this,isDelete->{
            Extensions.showToastShort(getContext(),"Xóa thành Công");
            getParentFragmentManager().popBackStack();
        });
    }

    @Override
    protected FragmentDetailsNotificationBinding getBinding(LayoutInflater inflater) {
        return FragmentDetailsNotificationBinding.inflate(getLayoutInflater());
    }

    @Override
    protected Class<NotificationViewModel> getViewModelClass() {
        return NotificationViewModel.class;
    }

    @Override
    protected String getTagFragment() {
        return DetailsNotificationFragment.class.getSimpleName();
    }
}