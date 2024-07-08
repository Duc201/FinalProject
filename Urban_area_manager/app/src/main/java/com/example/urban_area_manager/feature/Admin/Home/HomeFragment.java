package com.example.urban_area_manager.feature.Admin.Home;

import android.view.LayoutInflater;
import android.view.View;

import com.example.urban_area_manager.core.base.BaseFragment;
import com.example.urban_area_manager.databinding.FragmentHomeBinding;
import com.example.urban_area_manager.feature.Admin.Home.Apartment.View.ApartmentActivity;
import com.example.urban_area_manager.feature.Admin.Home.Bill.View.Bill.BillActivity;
import com.example.urban_area_manager.feature.Admin.Home.Bill.View.ServiceActivity;
import com.example.urban_area_manager.feature.Admin.Home.Building.View.BuildingActivity;
import com.example.urban_area_manager.feature.Admin.Home.Employee.View.EmployeeActivity;
import com.example.urban_area_manager.feature.Admin.Home.Camera.View.CameraActivity;
import com.example.urban_area_manager.feature.Admin.Home.Sensor.View.SensorActivity;
import com.example.urban_area_manager.feature.Admin.Home.Notification.View.NotificationActivity;
import com.example.urban_area_manager.feature.Admin.Home.Resident.View.ResidentActivity;
import com.example.urban_area_manager.feature.Admin.MainViewModel;
import com.example.urban_area_manager.utils.view.DialogView;

public class HomeFragment extends BaseFragment<FragmentHomeBinding,MainViewModel> {
    @Override
    public void onCommonViewLoaded() {

    }

    @Override
    public void addViewListener() {
        viewBinding.imgBuilding.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity(BuildingActivity.class);
            }
        });
        viewBinding.imgApartment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity(ApartmentActivity.class);
            }
        });
        viewBinding.resident.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity(ResidentActivity.class);
            }
        });
        viewBinding.employee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               openActivity(EmployeeActivity.class);
            }
        });
        viewBinding.IOTDevice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogView.showBottomSheetIot(getActivity(), "Danh sách thiết bị IOT", new DialogView.OnOpenListener() {
                    @Override
                    public void openCamera() {
                       openActivity(CameraActivity.class);
                    }

                    @Override
                    public void openIOT() {
                        openActivity(SensorActivity.class);
                    }
                });
            }
        });
        viewBinding.notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity(NotificationActivity.class);
            }
        });
        viewBinding.bill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogView.showBottomSheetBill(getActivity(), "Hóa đơn", new DialogView.OnOpenBillListener() {
                    @Override
                    public void openService() {
                            openActivity(ServiceActivity.class);
                    }

                    @Override
                    public void openBill() {
                            openActivity(BillActivity.class);
                    }
                });
            }
        });
    }


    @Override
    protected FragmentHomeBinding getBinding(LayoutInflater inflater) {
        return FragmentHomeBinding.inflate(getLayoutInflater());
    }

    @Override
    protected Class<MainViewModel> getViewModelClass() {
        return MainViewModel.class;
    }

    @Override
    protected String getTagFragment() {
        return HomeFragment.class.getSimpleName() ;
    }
}
