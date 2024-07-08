package com.example.urban_area_manager.feature.Resident.Home.Map;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.example.urban_area_manager.R;
import com.example.urban_area_manager.core.base.BaseActivity;
import com.example.urban_area_manager.databinding.ActivityMapResidentBinding;
import com.example.urban_area_manager.feature.Resident.ResidentMainViewModel;
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

import java.util.Arrays;
import java.util.List;

public class ResidentMapActivity extends BaseActivity<ActivityMapResidentBinding, ResidentMainViewModel> implements OnMapReadyCallback {
    private GoogleMap mMap;
    private FusedLocationProviderClient mFusedLocationProviderClient;
    private final LatLng mDefaultLocation = new LatLng(21.005081, 105.843693);
    private LatLng marker;
    private static final int DEFAULT_ZOOM = 15;
    private Location mLastKnownLocation;
//    private List<LatLng> locations = Arrays.asList(
//            new LatLng(21.005081, 105.843693),
//
//            new LatLng(21.006785,  105.843321));

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_type_map, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.NORMAL) {
            mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        }
        else if (id == R.id.SATELLITE) {
            mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);

        }
        else if (id == R.id.TERRAIN) {
            mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);

        }
        else if (id == R.id.HYBRID) {
            mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);

        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    protected ActivityMapResidentBinding getViewBinding() {
        return ActivityMapResidentBinding.inflate(getLayoutInflater());
    }

    @Override
    protected Class<ResidentMainViewModel> getViewModelClass() {
        return ResidentMainViewModel.class;
    }

    @Override
    public void onCommonViewLoaded() {
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.light_background));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        setSupportActionBar(binding.toolbar);
    }

    @Override
    public void addViewListener() {
        binding.toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;
        mMap.getUiSettings().setScrollGesturesEnabled(true);
        mMap.getUiSettings().setZoomGesturesEnabled(true);
//        for (LatLng location : locations) {
//            mMap.addMarker(new MarkerOptions().position(location).title("Marker at " + location.toString()));
//        }
        getLocationPermission();
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(mDefaultLocation, DEFAULT_ZOOM));

//        LatLng centerLatLng = mMap.getCameraPosition().target;
//        mCenterMarker = mMap.addMarker(new MarkerOptions().position(centerLatLng).title("Địa điểm phản ánh"));
//        mMap.setOnCameraMoveListener(new GoogleMap.OnCameraMoveListener() {
//            @Override
//            public void onCameraMove() {
//                marker = mMap.getCameraPosition().target;
//                if (mCenterMarker != null) {
//                    mCenterMarker.setPosition(marker);
//                }
//            }
//        });
    }
    private ActivityResultLauncher<String> requestPermissionLauncher = registerForActivityResult(
            new ActivityResultContracts.RequestPermission(), isGranted -> {
                if(isGranted){
                    updateLocationUI();
                    getDeviceLocation();
                }else {
                    Runnable listenerPositive = () -> {
                        openSettingPermission();
                    };
                    DialogView.showDialogDescriptionByHtml(this,"Thông báo","Vui lòng thay " +
                            "đổi cài đặt để cấp quyền cho GPS. Giúp tìm kiếm vị trí phản ánh tốt hơn",listenerPositive);
                }
            });
    private void openSettingPermission() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", getPackageName(),null);
        intent.setData(uri);
        startActivity(intent);
    }
    private void getLocationPermission() {
        if (checkSelfPermission( Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            updateLocationUI();
            getDeviceLocation();
        } else {
            requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION);
        }
    }

    private void getDeviceLocation() {
        try {
            Task<Location> locationResult = mFusedLocationProviderClient.getLastLocation();
            locationResult.addOnCompleteListener(this, task -> {
                if (task.isSuccessful()) {
                    mLastKnownLocation = task.getResult();
                    if (mLastKnownLocation != null) {
//                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
//                                new LatLng(mLastKnownLocation.getLatitude(), mLastKnownLocation.getLongitude()), DEFAULT_ZOOM));
                    }
                } else {
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(mDefaultLocation, DEFAULT_ZOOM));
//                    mMap.getUiSettings().setMyLocationButtonEnabled(false);
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