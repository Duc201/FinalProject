package com.example.urban_area_manager.feature.Admin.Home.Building.View;

import static android.app.Activity.RESULT_OK;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.view.LayoutInflater;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.core.content.ContextCompat;

import com.example.urban_area_manager.R;
import com.example.urban_area_manager.core.base.BaseFragment;
import com.example.urban_area_manager.core.component.DataLocalManager;
import com.example.urban_area_manager.databinding.FragmentAddBuildingBinding;
import com.example.urban_area_manager.feature.Admin.Home.Building.Model.Building;
import com.example.urban_area_manager.feature.Admin.Home.Building.ViewModel.BuildingViewModel;
import com.example.urban_area_manager.utils.extensions.Extensions;
import com.example.urban_area_manager.utils.view.DialogView;

import java.io.InputStream;

public class AddBuildingFragment extends BaseFragment<FragmentAddBuildingBinding, BuildingViewModel> {
    private Bitmap bitmapimage;

    @Override
    public void onCommonViewLoaded() {
        viewBinding.toolbar.setTitle("Thêm Tòa Nhà");
    }

    @Override
    public void addViewListener() {
        viewBinding.toolbar.setNavigationOnClickListener(v -> requireActivity().getSupportFragmentManager().popBackStack());
        viewBinding.imgBuilding.setOnClickListener(v -> showImageSourceDialog());
        viewBinding.btnSend.setOnClickListener(v -> {
            if (bitmapimage == null) {
                Drawable drawable = getResources().getDrawable(R.drawable.ic_apartment, getActivity().getTheme());
                bitmapimage = Extensions.drawableToBitmap(drawable);
            }
            viewModel.uploadImage(bitmapimage);
        });
    }

    @Override
    public void addDataObserve() {
        super.addDataObserve();
        viewModel._link.observe(this, link -> {
            Building building = new Building();
            building.setNameBuilding(viewBinding.edtBuildingName.getText().toString().trim());
            building.setArea(Double.parseDouble(viewBinding.edtBuildingArea.getText().toString().trim()));
            building.setFloorNumber(Integer.parseInt(viewBinding.edtBuildingNumberfloor.getText().toString().trim()));
            building.setDescription(viewBinding.edtBuildingDescribe.getText().toString().trim());
            building.setImageUrl(link);
            building.setCreatorUserId(DataLocalManager.getEmail());
            building.setLastModifierUserId(DataLocalManager.getEmail());
            building.setDeleterUserId("null");
            viewModel.addBuilding(building);
        });
        viewModel.isAddSuccess.observe(this, state -> {
            if (state) {
                getParentFragmentManager().popBackStack();
            }
        });
    }

    @Override
    protected FragmentAddBuildingBinding getBinding(LayoutInflater inflater) {
        return FragmentAddBuildingBinding.inflate(getLayoutInflater());
    }

    @Override
    protected Class<BuildingViewModel> getViewModelClass() {
        return BuildingViewModel.class;
    }

    @Override
    protected String getTagFragment() {
        return AddBuildingFragment.class.getSimpleName();
    }

    private final ActivityResultLauncher<Intent> cameraLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK) {
                    Intent data = result.getData();
                    if (data != null) {
                        Bundle extras = data.getExtras();
                        if (extras != null) {
                            Bitmap photo = (Bitmap) extras.get("data");
                            if (photo != null) {
                                viewBinding.imgBuilding.setImageBitmap(photo);
                                bitmapimage = photo;
                            }
                        }
                    }
                }
            });

    private final ActivityResultLauncher<Intent> galleryLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK) {
                    Intent data = result.getData();
                    if (data != null) {
                        Uri selectedImageUri = data.getData();
                        if (selectedImageUri != null) {
                            try {
                                InputStream inputStream = getActivity().getContentResolver().openInputStream(selectedImageUri);
                                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                                viewBinding.imgBuilding.setImageBitmap(bitmap);
                                bitmapimage = bitmap;
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            });

    private final ActivityResultLauncher<String> requestPermissionLauncher = registerForActivityResult(
            new ActivityResultContracts.RequestPermission(),
            result -> {
                if (result) {
                    openCamera();
                } else {
                    Runnable listenerPositive = this::openSettingPermission;
                    DialogView.showDialogDescriptionByHtml(getActivity(), "Thông báo", "Vui lòng thay đổi cài đặt để cấp quyền cho camera", listenerPositive);
                }
            });

    private void showImageSourceDialog() {
        String[] options = {"Chụp ảnh", "Chọn từ thư viện"};
        DialogView.showDialogOptions(getContext(), "Chọn ảnh từ", options, (dialog, which) -> {
            if (which == 0) {
                clickRequestPermission();
            } else if (which == 1) {
                clickRequestPermission2();
            }
        });
    }
    public void clickRequestPermission2() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            openGallery();
            return;
        }
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            openGallery();
        } else {
            requestPermissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE);
        }
    }
    private void openCamera() {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraLauncher.launch(cameraIntent);
    }

    private void openGallery() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        galleryLauncher.launch(galleryIntent);
    }

    public void clickRequestPermission() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            openCamera();
            return;
        }
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            openCamera();
        } else {
            requestPermissionLauncher.launch(Manifest.permission.CAMERA);
        }
    }

    private void openSettingPermission() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", requireActivity().getPackageName(), null);
        intent.setData(uri);
        startActivity(intent);
    }
}
