package com.example.urban_area_manager.feature.Admin.Home.Sensor.View;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.urban_area_manager.R;
import com.example.urban_area_manager.core.base.BaseFragment;
import com.example.urban_area_manager.databinding.FragmentAddDeatilsSensorBinding;
import com.example.urban_area_manager.feature.Admin.Home.Sensor.Model.Sensor;
import com.example.urban_area_manager.feature.Admin.Home.Sensor.Model.SensorViewModel;
import com.example.urban_area_manager.utils.Constant;
import com.example.urban_area_manager.utils.extensions.Extensions;
import com.example.urban_area_manager.utils.view.DialogView;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.Task;

import java.util.UUID;

public class AddDeatilsSensorFragment extends BaseFragment<FragmentAddDeatilsSensorBinding, SensorViewModel> implements OnMapReadyCallback {

    private GoogleMap mMap;
    private FusedLocationProviderClient mFusedLocationProviderClient;
    private final LatLng mDefaultLocation = new LatLng(-34, 151);
    private LatLng marker;
    private static final int DEFAULT_ZOOM = 18;
    private Location mLastKnownLocation;
    private Marker mCenterMarker;
    private Sensor sensor;

    @Override
    public void onCommonViewLoaded() {
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(requireActivity());
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map_sensor);
        mapFragment.getMapAsync(this);
        Bundle bundle = getArguments();
        if(bundle!=null){
            sensor = (Sensor) bundle.getSerializable(Constant.GO_TO_AddDeatilsSensorFragment);
        }
    }

    @Override
    public void addViewListener() {
        viewBinding.btnAccept.setOnClickListener(v -> {
            String name = viewBinding.edtName.getText().toString();
//            String location = viewBinding.edtLocation.getText().toString();
            sensor.setId(UUID.randomUUID().toString());
            sensor.setName(name);
//            sensor.setLocation(location);
            sensor.setLongitude(marker.longitude);
            sensor.setLatitude(marker.latitude);
            viewModel.addDeviceUser(sensor);
        });

        viewBinding.btnCancel.setOnClickListener(v -> getParentFragmentManager().popBackStack());
    }

    @Override
    public void addDataObserve() {
        super.addDataObserve();
        viewModel._isAdd.observe(this,isAdd->{
            Extensions.showToastShort(getActivity(),"Thêm thành công");
            viewModel.getListSensorUser();

        });
    }

    @Override
    protected FragmentAddDeatilsSensorBinding getBinding(LayoutInflater inflater) {
        return FragmentAddDeatilsSensorBinding.inflate(getLayoutInflater());
    }

    @Override
    protected Class<SensorViewModel> getViewModelClass() {
        return SensorViewModel.class;
    }

    @Override
    protected String getTagFragment() {
        return AddDeatilsSensorFragment.class.getSimpleName();
    }

    private final ActivityResultLauncher<String> requestPermissionLauncher = registerForActivityResult(
            new ActivityResultContracts.RequestPermission(), isGranted -> {
                if (isGranted) {
                    updateLocationUI();
                    getDeviceLocation();
                } else {
                    DialogView.showDialogDescriptionByHtml(requireActivity(), "Thông báo", "Vui lòng thay đổi cài đặt để cấp quyền cho GPS. Giúp tìm kiếm vị trí phản ánh tốt hơn", this::openSettingPermission);
                }
            });

    private void openSettingPermission() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", requireActivity().getPackageName(), null);
        intent.setData(uri);
        startActivity(intent);
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;
        mMap.getUiSettings().setScrollGesturesEnabled(true);
        mMap.getUiSettings().setZoomGesturesEnabled(true);
        getLocationPermission();

        LatLng centerLatLng = mMap.getCameraPosition().target;
        mCenterMarker = mMap.addMarker(new MarkerOptions().position(centerLatLng).title("Địa điểm phản ánh"));
        mMap.setOnCameraMoveListener(() -> {
            marker = mMap.getCameraPosition().target;
            if (mCenterMarker != null) {
                mCenterMarker.setPosition(marker);
            }
        });
    }

    private void getLocationPermission() {
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            updateLocationUI();
            getDeviceLocation();
        } else {
            requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION);
        }
    }

    private void getDeviceLocation() {
        try {
            Task<Location> locationResult = mFusedLocationProviderClient.getLastLocation();
            locationResult.addOnCompleteListener(requireActivity(), task -> {
                if (task.isSuccessful() && task.getResult() != null) {
                    mLastKnownLocation = task.getResult();
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(mLastKnownLocation.getLatitude(), mLastKnownLocation.getLongitude()), DEFAULT_ZOOM));
                } else {
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(mDefaultLocation, DEFAULT_ZOOM));
                    mMap.getUiSettings().setMyLocationButtonEnabled(false);
                }
            });

        } catch (SecurityException e) {
            e.printStackTrace();
        }
    }

    private void updateLocationUI() {
        if (mMap == null) {
            return;
        }
        try {
            mMap.setMyLocationEnabled(true);
            mMap.getUiSettings().setMyLocationButtonEnabled(true);
        } catch (SecurityException e) {
            e.printStackTrace();
        }
    }
}
