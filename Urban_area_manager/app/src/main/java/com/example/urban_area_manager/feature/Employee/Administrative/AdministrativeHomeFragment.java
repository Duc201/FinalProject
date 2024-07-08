package com.example.urban_area_manager.feature.Employee.Administrative;

import android.view.LayoutInflater;
import android.view.View;

import com.example.urban_area_manager.R;
import com.example.urban_area_manager.core.base.BaseFragment;
import com.example.urban_area_manager.core.component.DataLocalManager;
import com.example.urban_area_manager.databinding.FragmentAdministrativeHomeBinding;
import com.example.urban_area_manager.feature.Employee.NotificationEmployee.NotificationEmmployeeActivity;
import com.example.urban_area_manager.feature.Admin.Home.Apartment.View.ApartmentActivity;
import com.example.urban_area_manager.feature.Admin.Home.Bill.View.Bill.BillActivity;
import com.example.urban_area_manager.feature.Admin.Home.Bill.View.ServiceActivity;
import com.example.urban_area_manager.feature.Admin.Home.Building.View.BuildingActivity;
import com.example.urban_area_manager.feature.Admin.Home.Employee.View.EmployeeActivity;
import com.example.urban_area_manager.feature.Admin.Home.Resident.View.ResidentActivity;
import com.example.urban_area_manager.feature.Admin.Reflect.Model.Photo;
import com.example.urban_area_manager.feature.Admin.Reflect.adapter.PhotoViewpager2Adapter;
import com.example.urban_area_manager.utils.view.DialogView;

import java.util.ArrayList;
import java.util.List;


public class AdministrativeHomeFragment extends BaseFragment<FragmentAdministrativeHomeBinding, AdministrativeViewModel> {
    private List<Photo> photoList = new ArrayList<>();


    @Override
    public void onCommonViewLoaded() {

        photoList.add(new Photo(getString(R.string.img_ocen1)));
        photoList.add(new Photo(getString(R.string.img_ocen2)));
        photoList.add(new Photo(getString(R.string.img_ocen3)));

        PhotoViewpager2Adapter photoViewpager2Adapter = new PhotoViewpager2Adapter();
        photoViewpager2Adapter.submitList(photoList);
        viewBinding.imagesVp2.setAdapter(photoViewpager2Adapter);
        viewBinding.circleIndicator3.setViewPager(viewBinding.imagesVp2);
        viewBinding.name.setText(DataLocalManager.getName());

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
        viewBinding.notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity(NotificationEmmployeeActivity.class);;
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
    protected FragmentAdministrativeHomeBinding getBinding(LayoutInflater inflater) {
        return FragmentAdministrativeHomeBinding.inflate(getLayoutInflater());
    }

    @Override
    protected Class<AdministrativeViewModel> getViewModelClass() {
        return AdministrativeViewModel.class ;
    }

    @Override
    protected String getTagFragment() {
        return AdministrativeHomeFragment.class.getSimpleName();
    }
}