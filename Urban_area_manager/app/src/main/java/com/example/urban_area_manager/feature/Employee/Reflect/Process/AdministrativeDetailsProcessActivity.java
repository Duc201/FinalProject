package com.example.urban_area_manager.feature.Employee.Reflect.Process;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.example.urban_area_manager.R;
import com.example.urban_area_manager.core.base.BaseActivity;
import com.example.urban_area_manager.databinding.ActivityAdministrativeDetailsProcessBinding;
import com.example.urban_area_manager.feature.Admin.Reflect.Model.Photo;
import com.example.urban_area_manager.feature.Admin.Reflect.Model.Reflect;
import com.example.urban_area_manager.feature.Admin.Reflect.ViewModel.ReflectViewModel;
import com.example.urban_area_manager.feature.Admin.Reflect.adapter.PhotoViewpager2Adapter;
import com.example.urban_area_manager.utils.Constant;
import com.example.urban_area_manager.utils.view.DialogView;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class AdministrativeDetailsProcessActivity extends BaseActivity<ActivityAdministrativeDetailsProcessBinding, ReflectViewModel>implements OnMapReadyCallback {
    private Reflect reflect;
    private List<Photo> photoList = new ArrayList<>();
    private GoogleMap mMap;
    private Bitmap bitmapimage1;
    private Bitmap bitmapimage2;
    private int isImage = 0;
    private static final int DEFAULT_ZOOM = 18;


    @Override
    protected ActivityAdministrativeDetailsProcessBinding getViewBinding() {
        return ActivityAdministrativeDetailsProcessBinding.inflate(getLayoutInflater());
    }

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
        SupportMapFragment mapFragment = (SupportMapFragment)
                getSupportFragmentManager().findFragmentById(R.id.map_assig);
        mapFragment.getMapAsync(this);


        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            reflect = (Reflect) bundle.getSerializable(Constant.GO_TO_AdministrativeDetailsProcessActivity);
        }

        photoList.add(new Photo(reflect.getImage1()));
        photoList.add(new Photo(reflect.getImage2()));

        PhotoViewpager2Adapter photoViewpager2Adapter = new PhotoViewpager2Adapter();
        photoViewpager2Adapter.submitList(photoList);
        binding.imagesVp2.setAdapter(photoViewpager2Adapter);
        binding.circleIndicator3.setViewPager(binding.imagesVp2);


        binding.tvContent.setText(reflect.getContent());
        binding.field.setText(reflect.getField());
        binding.tvLoaction.setText(reflect.getLoaction());
        binding.tvSpecificLocation.setText(reflect.getSpecificLocation());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.getDefault());
        String dateString = simpleDateFormat.format(reflect.getTimeCreator());
        binding.timeCreator.setText(dateString);
        binding.nameEmployee.setText(reflect.getNameHandler());
        binding.toolbar.setTitle(reflect.getContent());

    }

    @Override
    public void addDataObserve() {
        super.addDataObserve();
        viewModel.isUpdateReflectEmployee.observe(this,isUpdate->{
            if(isUpdate){
                Toast.makeText(this,"Đã xử lý thành công",Toast.LENGTH_SHORT).show();
                finish();
            }
        });
        viewModel.updateEmployeeProcessReflectNo.observe(this,isUpdate->{
            if(isUpdate){
                Toast.makeText(this,"Đã từ chối",Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }

    @Override
    public void addViewListener() {
        binding.btnNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogView.showDialogReject(AdministrativeDetailsProcessActivity.this, "Lý do từ chối", new DialogView.OnAcceptListener() {
                    @Override
                    public void onAccept(String rejectReason) {
                        viewModel.updateEmployeeProcessReflectNo(rejectReason,2,reflect.getId());
                    }
                });
            }
        });
        binding.btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               String result = binding.edtResult.getText().toString();
               String idReflect = reflect.getId();
               viewModel.updateEmployeeProcess(result,idReflect);
               viewModel.updateListImageRespon(bitmapimage1,bitmapimage2);
            }
        });

        binding.respondimg1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isImage = 1;
                showImageSourceDialog();
            }
        });
        binding.respondimg2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isImage = 2;
                showImageSourceDialog();
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
                                binding.respondimg1.setImageBitmap(bitmap);
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
                                binding.respondimg2.setImageBitmap(bitmap);
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
                               binding.respondimg1.setImageBitmap(photo);
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
                                binding.respondimg2.setImageBitmap(photo);
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
    private void openSettingPermission() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", getPackageName(),null);
        intent.setData(uri);
        startActivity(intent);
    }
    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;
        LatLng myPos = new LatLng(reflect.getLatitude(), reflect.getLongitude());
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(myPos, DEFAULT_ZOOM));
        mMap.addMarker(new MarkerOptions().title("Vị trí phản ánh").position(myPos));
    }
}