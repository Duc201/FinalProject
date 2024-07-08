package com.example.urban_area_manager.feature.Admin.Home.Apartment.View;

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
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.core.content.ContextCompat;

import com.example.urban_area_manager.R;
import com.example.urban_area_manager.core.base.BaseFragment;
import com.example.urban_area_manager.core.component.DataLocalManager;
import com.example.urban_area_manager.databinding.FragmentAddApartmentBinding;
import com.example.urban_area_manager.feature.Admin.Home.Apartment.Model.Apartment;
import com.example.urban_area_manager.feature.Admin.Home.Apartment.ViewModel.ApartmentViewModel;
import com.example.urban_area_manager.utils.Constant;
import com.example.urban_area_manager.utils.extensions.Extensions;
import com.example.urban_area_manager.utils.view.DialogView;

import java.io.InputStream;


public class AddApartmentFragment extends BaseFragment<FragmentAddApartmentBinding, ApartmentViewModel> {

    private String idBuilding;
    private int state;
    private Bitmap bitmapimage;

    private ArrayAdapter<CharSequence> spinnerStateAdapter;

    @Override
    public void onCommonViewLoaded() {
        Bundle bundle = getArguments();
        idBuilding = bundle.getString(Constant.AddApartmentFragment);
        setSpinnerState();
    }

    @Override
    public void addDataObserve() {
        super.addDataObserve();
        viewModel._link.observe(this,link->{
            Apartment apartment = new Apartment();
            apartment.setName(viewBinding.edtApartmentName.getText().toString().trim());
            apartment.setFloor(Integer.parseInt(viewBinding.edtBuildingNumberfloore.getText().toString()));
            apartment.setIdBuilding(idBuilding);
            apartment.setArea(Double.parseDouble(viewBinding.edtApartmentArea.getText().toString()));
            apartment.setDescription(viewBinding.edtApartmentDescribe.getText().toString());
            apartment.setState(state);
            apartment.setImagePath(link);
            apartment.setCreatorUserId(DataLocalManager.getEmail());
            apartment.setDeleterUserId("null");
            apartment.setLastModifierUserId(DataLocalManager.getEmail());

            viewModel.addApartment(apartment);
        });
        viewModel.isAddSuccess.observe(this,state->{
            if(state == true){
                getParentFragmentManager().popBackStack();
            }
        });
    }

    @Override
    public void addViewListener() {
        viewBinding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requireActivity().getSupportFragmentManager().popBackStack();
            }
        });
        viewBinding.nameToolbar.setText("Tạo Căn hộ");
        viewBinding.imgApartment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showImageSourceDialog();
            }


        });
        viewBinding.btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(bitmapimage == null){
                    Drawable drawable = getResources().getDrawable(R.drawable.ic_home_apartment);
                    bitmapimage = Extensions.drawableToBitmap(drawable);
                }
                if (isValidForm()) {
                    viewModel.uploadImage(bitmapimage);
                } else {
                    Toast.makeText(getActivity(), "Yêu cầu điền đầy đủ các trường", Toast.LENGTH_SHORT).show();
                }
            }
        });


        viewBinding.edtApartmentName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                String apartmentName = s.toString().trim();
                if (apartmentName.isEmpty()) {
                    // Show error message if the field is empty
                    viewBinding.edtApartmentName.setError("Tên căn hộ không được để trống");
                }
                else {
                    // Clear error message if the field is valid
                    viewBinding.edtApartmentName.setError(null);
                }
            }
        });
        viewBinding.edtBuildingNumberfloore.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().trim().isEmpty()) {
                    viewBinding.edtBuildingNumberfloore.setError("Số tầng không được để trống");
                }
                else {
                    viewBinding.edtBuildingNumberfloore.setError(null);
                }
            }
        });
        viewBinding.edtApartmentArea.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().trim().isEmpty()) {
                    viewBinding.edtApartmentArea.setError("Diện tích căn hộ không được để trống");
                }
                else {
                    viewBinding.edtApartmentArea.setError(null);
                }
            }
        });
        viewBinding.edtApartmentDescribe.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().trim().isEmpty()) {
                    viewBinding.edtApartmentDescribe.setError("Mô tả không được để trống");
                }
                else {
                    viewBinding.edtApartmentDescribe.setError(null);
                }
            }
        });
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

    private void openCamera() {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraLauncher.launch(cameraIntent);
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
    @Override
    protected FragmentAddApartmentBinding getBinding(LayoutInflater inflater) {
        return FragmentAddApartmentBinding.inflate(getLayoutInflater());
    }

    @Override
    protected Class<ApartmentViewModel> getViewModelClass() {
        return ApartmentViewModel.class;
    }

    @Override
    protected String getTagFragment() {
        return AddApartmentFragment.class.getSimpleName();
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
                                viewBinding.imgApartment.setImageBitmap(photo);
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
                                viewBinding.imgApartment.setImageBitmap(bitmap);
                                bitmapimage = bitmap;
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            });



    private void openSettingPermission() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", requireActivity().getPackageName(),null);
        intent.setData(uri);
        startActivity(intent);
    }
    private void setSpinnerState() {
        spinnerStateAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.spinner_status, R.layout.spinner_item);
        spinnerStateAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        viewBinding.spinSate.setAdapter(spinnerStateAdapter);
        viewBinding.spinSate.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                state = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
    private boolean isValidForm() {
        boolean isValid = true;

        if (viewBinding.edtApartmentName.getText().toString().trim().isEmpty()) {
            viewBinding.edtApartmentName.setError("Tên căn hộ không được để trống");
            isValid = false;
        }

        if (viewBinding.edtBuildingNumberfloore.getText().toString().trim().isEmpty()) {
            viewBinding.edtBuildingNumberfloore.setError("Tên căn hộ không được để trống");
            isValid = false;
        }
        if (viewBinding.edtApartmentArea.getText().toString().trim().isEmpty()) {
            viewBinding.edtApartmentArea.setError("Diện tích không được để trống");
            isValid = false;
        }
        if (viewBinding.edtApartmentDescribe.getText().toString().trim().isEmpty()) {
            viewBinding.edtApartmentDescribe.setError("Mô tả căn hộ không được để trống");
            isValid = false;
        }

        return isValid;
    }
}