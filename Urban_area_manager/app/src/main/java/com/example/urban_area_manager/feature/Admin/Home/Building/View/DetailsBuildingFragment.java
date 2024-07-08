package com.example.urban_area_manager.feature.Admin.Home.Building.View;

import static android.app.Activity.RESULT_OK;

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
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.example.urban_area_manager.R;
import com.example.urban_area_manager.core.base.BaseFragment;
import com.example.urban_area_manager.core.component.DataLocalManager;
import com.example.urban_area_manager.databinding.FragmentDetailsBuildingBinding;
import com.example.urban_area_manager.feature.Admin.Home.Building.Model.Building;
import com.example.urban_area_manager.feature.Admin.Home.Building.ViewModel.BuildingViewModel;
import com.example.urban_area_manager.utils.Constant;
import com.example.urban_area_manager.utils.view.DialogView;
import com.google.firebase.Timestamp;

import java.io.InputStream;

public class DetailsBuildingFragment extends BaseFragment<FragmentDetailsBuildingBinding, BuildingViewModel> {

    private Building building;

    private Bitmap bitmapimage;

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_toolbar, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.save) {
            if(bitmapimage != null){
                viewModel.uploadImage(bitmapimage);
            }
            else {
                building.setNameBuilding(viewBinding.edtBuildingName.getText().toString().trim());
                building.setArea(Double.parseDouble(viewBinding.edtBuildingArea.getText().toString().trim()));
                building.setFloorNumber(Integer.parseInt(viewBinding.edtBuildingNumberfloor.getText().toString().trim()));
                building.setDescription(viewBinding.edtBuildingDescribe.getText().toString().trim());
                building.setDeleterUserId("null");
                building.setLastModifierUserId(DataLocalManager.getEmail());
                building.setLastModificationTime(Timestamp.now().toDate());
                viewModel.updateBuilding(building);
            }
            return true;
        } else if (id == R.id.delete) {
            Runnable listenerPositive = () -> {
                building.setDeleted(true);
                building.setDeleterUserId(DataLocalManager.getEmail());
                viewModel.deleteImageAndBuilding(building.getImageUrl(),building);
            };
            DialogView.showDialogDescriptionByHtml(getActivity(),"Xác nhận","Bạn có muốn xóa toa nha này không",listenerPositive);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onCommonViewLoaded() {
        AppCompatActivity activity = (AppCompatActivity) requireActivity();
        activity.setSupportActionBar(viewBinding.toolbar);

        Bundle bundle = getArguments();
        if(bundle != null){
           building = (Building) bundle.getSerializable(Constant.GO_TO_DETAILS_BuildingFragment);
        }
        viewBinding.toolbar.setTitle(building.getNameBuilding());

        viewBinding.edtBuildingName.setText(building.getNameBuilding());
        viewBinding.edtBuildingArea.setText(String.valueOf(building.getArea()));
        viewBinding.edtBuildingNumberfloor.setText(String.valueOf(building.getFloorNumber()));
        viewBinding.edtBuildingDescribe.setText(building.getDescription());
        Glide.with(activity).load(building.getImageUrl()).placeholder(R.drawable.ic_apartment).error(R.drawable.ic_apartment).into(viewBinding.imgBuilding);

    }

    @Override
    public void addDataObserve() {
        super.addDataObserve();
        viewModel._link.observe(this,link->{
            building.setNameBuilding(viewBinding.edtBuildingName.getText().toString().trim());
            building.setArea(Double.parseDouble(viewBinding.edtBuildingArea.getText().toString().trim()));
            building.setFloorNumber(Integer.parseInt(viewBinding.edtBuildingNumberfloor.getText().toString().trim()));
            building.setDescription(viewBinding.edtBuildingDescribe.getText().toString().trim());
            building.setImageUrl(link);
            building.setDeleterUserId("null");
            building.setLastModifierUserId(DataLocalManager.getEmail());
            building.setLastModificationTime(Timestamp.now().toDate());
            viewModel.updateBuilding(building);
        });

        viewModel.isUpdateSuccess.observe(this,state->{
            if(state == true){
                getParentFragmentManager().popBackStack();
            }
        });

        viewModel.deleteResultLiveData.observe(this,sate->{
            if (sate) {
                Toast.makeText(getActivity(), "Xóa thành công", Toast.LENGTH_SHORT).show();
                getActivity().getSupportFragmentManager().popBackStack();
            } else {
                Toast.makeText(getActivity(), "Xóa không thành công", Toast.LENGTH_SHORT).show();
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
        viewBinding.imgBuilding.setOnClickListener(v -> showImageSourceDialog());

    }

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

    private void clickRequestPermission2() {
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

    private void openGallery() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        galleryLauncher.launch(galleryIntent);
    }
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
    @Override
    protected FragmentDetailsBuildingBinding getBinding(LayoutInflater inflater) {
        return FragmentDetailsBuildingBinding.inflate(getLayoutInflater());
    }

    @Override
    protected Class<BuildingViewModel> getViewModelClass() {
        return BuildingViewModel.class;
    }

    @Override
    protected String getTagFragment() {
        return DetailsBuildingFragment.class.getSimpleName();
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

    private void openCamera() {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraLauncher.launch(cameraIntent);
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
    private final ActivityResultLauncher<String> requestPermissionLauncher = registerForActivityResult(
            new ActivityResultContracts.RequestPermission(),
            result -> {
                if (result) {
                    Intent camera_intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    cameraLauncher.launch(camera_intent);
                } else {
                    Runnable listenerPositive = () -> {
                        openSettingPermission();
                    };
                    DialogView.showDialogDescriptionByHtml(getActivity(),"Thông báo","Vui lòng thay " +
                            "đổi cài đặt để cấp quyền cho camera",listenerPositive);
                }

            });
  private void openSettingPermission() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", requireActivity().getPackageName(),null);
        intent.setData(uri);
        startActivity(intent);
    }

}
