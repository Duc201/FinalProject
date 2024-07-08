package com.example.urban_area_manager.feature.Admin.Reflect.View;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;

import android.provider.MediaStore;
import android.provider.Settings;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.urban_area_manager.R;
import com.example.urban_area_manager.core.base.BaseActivity;
import com.example.urban_area_manager.core.component.DataLocalManager;
import com.example.urban_area_manager.databinding.FragmentAddReflectBinding;
import com.example.urban_area_manager.feature.Admin.Reflect.Model.Reflect;
import com.example.urban_area_manager.feature.Admin.Reflect.ViewModel.ReflectViewModel;
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
import com.google.firebase.Timestamp;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.core.content.ContextCompat;

import java.io.InputStream;
import java.util.UUID;

public class AddReflectFragment extends BaseActivity<FragmentAddReflectBinding, ReflectViewModel> implements OnMapReadyCallback {

    private GoogleMap mMap;
    private FusedLocationProviderClient mFusedLocationProviderClient;
    private final LatLng mDefaultLocation = new LatLng(-34, 151);
    private LatLng marker;
    private static final int DEFAULT_ZOOM = 15;
    private Location mLastKnownLocation;
    private Marker mCenterMarker;
    private Bitmap bitmapimage1;
    private Bitmap bitmapimage2;
    private int isImage = 0;
    private String nameLocation;
    private ArrayAdapter<CharSequence> spinnerSexAdapter;
    private String field;
    private ArrayAdapter<String> spinnerNameBuildingAdapter;





    @Override
    protected Class<ReflectViewModel> getViewModelClass() {
        return ReflectViewModel.class;
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
        setSpinnerSex();
        setSpinnerNameBuildingAdapter();
        viewModel.getlistNameBuilding();
    }

    @Override
    public void addDataObserve() {
        super.addDataObserve();
        viewModel.listNameBuilding().observe(this, listNameBuilding->{
            listNameBuilding.add("Cổng 1");
            listNameBuilding.add("Cổng 2");
            listNameBuilding.add("Khu vực đường nội khu");
            listNameBuilding.add("Khu vực tiện ích dịch vụ");
            listNameBuilding.add("Khu vực sân bóng đá");
            spinnerNameBuildingAdapter.clear();
            spinnerNameBuildingAdapter.addAll(listNameBuilding);
            spinnerNameBuildingAdapter.notifyDataSetChanged();
        });
        viewModel.isAddSuccess.observe(this,isadd->{
            if(isadd){
                Toast.makeText(this, "Tạo thành công", Toast.LENGTH_SHORT).show();
                viewModel.clear();
                finish();
            }
        });
    }

    @Override
    public void addViewListener() {
        binding.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        binding.img1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isImage = 1;
                showImageSourceDialog();
            }
        });
        binding.img2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isImage = 2;
                showImageSourceDialog();
            }
        });
        binding.btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Reflect reflect = new Reflect();
                reflect.setId(UUID.randomUUID().toString());
                reflect.setLoaction(nameLocation);
                reflect.setContent(binding.edtContent.getText().toString());
                reflect.setLongitude(marker.longitude);
                reflect.setLatitude(marker.latitude);
                reflect.setSpecificLocation(binding.edtSpecificLocation.getText().toString());
                reflect.setField(field);
                reflect.setState(0);
                reflect.setIdCreator(DataLocalManager.getEmail());
                reflect.setTimeCreator(Timestamp.now().toDate());
                reflect.setDeleted(false);
                viewModel.addReflectNotImage(reflect);
                viewModel.updateListImage(bitmapimage1,bitmapimage2);
            }
        });
    }

    private void showImageSourceDialog() {
        String[] options = {"Chụp ảnh", "Chọn từ thư viện"};
        DialogView.showDialogOptions(this, "Chọn ảnh từ", options, (dialog, which) -> {
            if (which == 0) {
                clickRequestPermission();
            } else if (which == 1) {
                clickRequestPermission2();
            }
        });
    }

    private void clickRequestPermission2() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            openGallery();
            return;
        }
        if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            openGallery();
        } else {
            requestPermissionLauncherCamera.launch(Manifest.permission.READ_EXTERNAL_STORAGE);
        }
    }

    private void openGallery() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        galleryLauncher.launch(galleryIntent);
    }
    private final ActivityResultLauncher<Intent> galleryLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK && isImage == 1) {
                    Intent data = result.getData();
                    if (data != null) {
                        Uri selectedImageUri = data.getData();
                        if (selectedImageUri != null) {
                            try {
                                InputStream inputStream = getContentResolver().openInputStream(selectedImageUri);
                                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                                binding.img1.setImageBitmap(bitmap);
                                bitmapimage1 = bitmap;
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
                if (result.getResultCode() == RESULT_OK && isImage == 2) {
                    Intent data = result.getData();
                    if (data != null) {
                        Uri selectedImageUri = data.getData();
                        if (selectedImageUri != null) {
                            try {
                                InputStream inputStream = getContentResolver().openInputStream(selectedImageUri);
                                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                                binding.img2.setImageBitmap(bitmap);
                                bitmapimage2 = bitmap;
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            });
    private void clickRequestPermission() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return;
        }
        if (checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            Intent camera_intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            cameraLauncher.launch(camera_intent);
        } else {
            requestPermissionLauncherCamera.launch(Manifest.permission.CAMERA);
        }
    }
    private final ActivityResultLauncher<Intent> cameraLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK && isImage ==1) {
                    Intent data = result.getData();
                    if (data != null) {
                        // Xử lý kết quả từ Intent ở đây
                        Bundle extras = data.getExtras();
                        if (extras != null) {
                            Bitmap photo = (Bitmap) extras.get("data");
                            bitmapimage1 = photo;
                            if (photo != null) {
                                binding.img1.setImageBitmap(photo);
                            }
                        }
                    }
                }
                if (result.getResultCode() == RESULT_OK && isImage ==2) {
                    Intent data = result.getData();
                    if (data != null) {
                        // Xử lý kết quả từ Intent ở đây
                        Bundle extras = data.getExtras();
                        if (extras != null) {
                            Bitmap photo = (Bitmap) extras.get("data");
                            bitmapimage2 = photo;
                            if (photo != null) {
                                binding.img2.setImageBitmap(photo);
                            }
                        }
                    }
                }
            });
    private final ActivityResultLauncher<String> requestPermissionLauncherCamera = registerForActivityResult(
            new ActivityResultContracts.RequestPermission(),
            result -> {
                if (result) {
                    Intent camera_intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    cameraLauncher.launch(camera_intent);
                } else {
                    Runnable listenerPositive = () -> {
                        openSettingPermission();
                    };
                    DialogView.showDialogDescriptionByHtml(this,"Thông báo","Vui lòng thay " +
                            "đổi cài đặt để cấp quyền cho camera để có thể chụp ảnh minh chứng",listenerPositive);
                }

            });
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


    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;
        mMap.getUiSettings().setScrollGesturesEnabled(true);
        mMap.getUiSettings().setZoomGesturesEnabled(true);
        getLocationPermission();

        LatLng centerLatLng = mMap.getCameraPosition().target;
        mCenterMarker = mMap.addMarker(new MarkerOptions().position(centerLatLng).title("Địa điểm phản ánh"));
        mMap.setOnCameraMoveListener(new GoogleMap.OnCameraMoveListener() {
            @Override
            public void onCameraMove() {
                 marker = mMap.getCameraPosition().target;
                if (mCenterMarker != null) {
                    mCenterMarker.setPosition(marker);
                }
            }
        });
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
                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                                    new LatLng(mLastKnownLocation.getLatitude(), mLastKnownLocation.getLongitude()), DEFAULT_ZOOM));
                        }
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
    private void setSpinnerNameBuildingAdapter() {
        spinnerNameBuildingAdapter = new ArrayAdapter<>(this, R.layout.spinner_item);
        spinnerNameBuildingAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        binding.locationReflectSp.setAdapter(spinnerNameBuildingAdapter);
        binding.locationReflectSp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                nameLocation = spinnerNameBuildingAdapter.getItem(position);;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }
    private void setSpinnerSex() {
        spinnerSexAdapter = ArrayAdapter.createFromResource(this, R.array.spinner_field, R.layout.spinner_item);
        spinnerSexAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        binding.spFeild.setAdapter(spinnerSexAdapter);
        binding.spFeild.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                field = (String) spinnerSexAdapter.getItem(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }




    @Override
    protected FragmentAddReflectBinding getViewBinding() {
        return FragmentAddReflectBinding.inflate(getLayoutInflater());
    }



}
