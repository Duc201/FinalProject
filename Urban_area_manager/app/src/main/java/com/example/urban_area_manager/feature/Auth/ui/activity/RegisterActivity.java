package com.example.urban_area_manager.feature.Auth.ui.activity;

import android.Manifest;
import android.app.DatePickerDialog;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.core.content.ContextCompat;

import com.example.urban_area_manager.R;
import com.example.urban_area_manager.core.base.BaseActivity;
import com.example.urban_area_manager.core.component.DataLocalManager;
import com.example.urban_area_manager.databinding.ActivityRegisterBinding;
import com.example.urban_area_manager.feature.Auth.ui.adapter.ApartmentAdapter;
import com.example.urban_area_manager.feature.Auth.ui.adapter.BuildingAdapter;
import com.example.urban_area_manager.feature.Auth.viewmodel.AuthViewModel;
import com.example.urban_area_manager.feature.Admin.Home.Apartment.Model.Apartment;
import com.example.urban_area_manager.feature.Admin.Home.Building.Model.Building;
import com.example.urban_area_manager.feature.Admin.Home.Resident.Model.DetailsResident;
import com.example.urban_area_manager.feature.Admin.Home.Resident.Model.Resident;
import com.example.urban_area_manager.utils.extensions.Extensions;
import com.example.urban_area_manager.utils.view.DialogView;

import java.io.InputStream;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

public class RegisterActivity extends BaseActivity<ActivityRegisterBinding, AuthViewModel> {

    private ArrayAdapter<CharSequence> spinnerSexAdapter;
    private ArrayAdapter<CharSequence> spinnerRelativeAdapter;
    private BuildingAdapter spinnerNameBuildingAdapter;
    private ApartmentAdapter spinnerNameApartmentAdapter;

    private int sex;
    private int relative;
    private int isImage = 0;
    private Building selectedBuilding = new Building();
    private String idBuilding;
    private String idApartment;
    private String nameBuilding;
    private String nameApartment;
    private Bitmap bitmapcccd1;
    private Bitmap bitmapcccd2;
    private Bitmap bitmapuser;
    private Bitmap bitmapContract;
    private DatePickerDialog datePickerDialog;

    private static final int pic_id = 123;
    public static final int REQUEST_PERMISSION_CODE = 321;
    private Date date;

    @Override
    protected ActivityRegisterBinding getViewBinding() {
        return ActivityRegisterBinding.inflate(getLayoutInflater());
    }

    @Override
    protected Class<AuthViewModel> getViewModelClass() {
        return AuthViewModel.class;
    }

    @Override
    public void onCommonViewLoaded() {

            binding.edtBirth.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    datePickerDialog.show();
                }
            });
            initDatePicker();
            binding.edtBirth.setText(getTodaysDate());
            setSpinnerSex();
            setSpinnerRelative();
            creatSpinnerNameApartmentAdapter();
            creatSpinnerNameBuildingAdapter();
            viewModel.getlistBuilding();
//            openCamera();
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.light_background));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }

    }

    private String getTodaysDate() {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        month = month + 1;
        int day = cal.get(Calendar.DAY_OF_MONTH);
        return String.format("%02d/%02d/%04d", day, month, year);
    }

    private void initDatePicker() {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener()
        {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                LocalDate localDate = LocalDate.of(year, month + 1, dayOfMonth);
                Date date1 = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
                binding.edtBirth.setText(Extensions.FormatDate(date1));
                date =date1;
            }
        };
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        int style = R.style.CustomDatePickerDialogTheme;
        datePickerDialog = new DatePickerDialog(this, style, dateSetListener, year, month, day);
        datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
    }

    @Override
    public void addDataObserve() {
        super.addDataObserve();
        viewModel.listbuilding().observe(this,listBuilding->{
            spinnerNameBuildingAdapter.addAll(listBuilding);
            spinnerNameBuildingAdapter.notifyDataSetChanged();
        });
        viewModel.listApartment().observe(this,listApartment->{
            spinnerNameApartmentAdapter.clear();
            spinnerNameApartmentAdapter.addAll(listApartment);
            spinnerNameApartmentAdapter.notifyDataSetChanged();
        });

        viewModel.isAddSuccess.observe(this,isSuccess->{
            if(isSuccess == true){
                Runnable listenPositive = () -> {
                    finish();
                };
                DialogView.showDialogDescriptionByHtml(this,"Thông báo","Tài khoản của cư dân đang chờ" +
                                " xác thực. Quý cư dân vui lòng chú ý email để nhận thông báo. Xin cám ơn",
                        listenPositive);
            }
            viewModel.clear();
        });
    }

    @Override
    public void addViewListener() {
        binding.spinBuilding.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                 selectedBuilding = (Building) parent.getItemAtPosition(position);
                 nameBuilding =selectedBuilding.getNameBuilding();
                 idBuilding = selectedBuilding.getIdBuilding();
                 viewModel.getlistApartment(selectedBuilding.getIdBuilding());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        binding.spinApartment.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Apartment selectedApartment = (Apartment) parent.getItemAtPosition(position);
                nameApartment = selectedApartment.getName();
                idApartment = selectedApartment.getIdApartment();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        binding.btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Resident resident = new Resident();
                DetailsResident detailsResident = new DetailsResident();

                String id = UUID.randomUUID().toString();
                resident.setId(id);
                resident.setFullName(binding.edtResidentName.getText().toString().trim());
                resident.setIndentityNumber(binding.edtResidentCccd.getText().toString().trim());
                resident.setAddress(binding.edtResidentAddress.getText().toString().trim());
                resident.setNationality(binding.spinNationality.getSelectedCountryNameCode());
                resident.setPhoneNumber(binding.edtResidentPhone.getText().toString());
                resident.setEmail(DataLocalManager.getEmail());
                if(date == null){
                    date = new Date();
                }
                resident.setBirth(date);
                resident.setState(0);
                resident.setSex(sex);
                resident.setCreatorUserId(DataLocalManager.getEmail());
                resident.setDeleterUserId("null");
                resident.setLastModifierUserId(DataLocalManager.getEmail());

                detailsResident.setId(UUID.randomUUID().toString());
                detailsResident.setIdUser(id);
                detailsResident.setRelationShip(relative);
                detailsResident.setBuildingCode(idBuilding);
                detailsResident.setApartmentCode(idApartment);
                detailsResident.setApartmentName(nameApartment);
                detailsResident.setBuildingName(nameBuilding);
                detailsResident.setRelationShip(relative);
                detailsResident.setState(0);
                detailsResident.setNameUser(binding.edtResidentName.getText().toString());
                detailsResident.setCreatorUserId(DataLocalManager.getEmail());
                detailsResident.setDeleterUserId("null");
                detailsResident.setLastModifierUserId(DataLocalManager.getEmail());

                viewModel.addResidentViewModel(resident,detailsResident);
                viewModel.updateListImage(bitmapcccd1,bitmapcccd2,bitmapuser,bitmapContract);

            }
        });
        binding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.back.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                });
            }
        });
        binding.nameToolbar.setText("Đăng ký thông tin");
        binding.imgUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isImage = 1;
                showImageSourceDialog();
            }
        });
        binding.imgCccdtruoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isImage = 2;
                showImageSourceDialog();
            }
        });
        binding.imgCccdsau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isImage = 3;
                showImageSourceDialog();
            }
        });

        binding.contract.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isImage = 4;
                showImageSourceDialog();
            }
        });
    }




    private final ActivityResultLauncher<String> requestPermissionLauncher = registerForActivityResult(
            new ActivityResultContracts.RequestPermission(),
            result -> {
                if (result) {
                    openCamera();
                } else {
                    Runnable listenerPositive = this::openSettingPermission;
                    DialogView.showDialogDescriptionByHtml(this, "Thông báo", "Vui lòng thay đổi cài đặt để cấp quyền cho camera", listenerPositive);
                }
            });
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
    public void clickRequestPermission2() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            openGallery();
            return;
        }
        if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            openGallery();
        } else {
            requestPermissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE);
        }
    }
    private void clickRequestPermission() {
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.M){
            openCamera();
            return;
        }
        if(checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED){
            openCamera();
        }
        else {
            String[] permissions = {Manifest.permission.CAMERA};
//            requestPermissions(permissions,REQUEST_PERMISSION_CODE);
            requestPermissionLauncher.launch(Manifest.permission.CAMERA);
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


    private final ActivityResultLauncher<Intent> cameraLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK && isImage == 1) {
                    Intent data = result.getData();
                    if (data != null) {
                        Bundle extras = data.getExtras();
                        if (extras != null) {
                            Bitmap photo = (Bitmap) extras.get("data");
                            if (photo != null) {
                                binding.imgUser.setImageBitmap(photo);
                                bitmapuser = photo;
                            }
                        }
                    }
                }
                if (result.getResultCode() == RESULT_OK && isImage == 2) {
                    Intent data = result.getData();
                    if (data != null) {
                        Bundle extras = data.getExtras();
                        if (extras != null) {
                            Bitmap photo = (Bitmap) extras.get("data");
                            if (photo != null) {
                                binding.imgCccdtruoc.setImageBitmap(photo);
                                bitmapcccd1 = photo;
                            }
                        }
                    }
                }
                if (result.getResultCode() == RESULT_OK && isImage == 3) {
                    Intent data = result.getData();
                    if (data != null) {
                        Bundle extras = data.getExtras();
                        if (extras != null) {
                            Bitmap photo = (Bitmap) extras.get("data");
                            if (photo != null) {
                                binding.imgCccdsau.setImageBitmap(photo);
                                bitmapcccd2 = photo;
                            }
                        }
                    }
                }
                if (result.getResultCode() == RESULT_OK && isImage == 4) {
                    Intent data = result.getData();
                    if (data != null) {
                        Bundle extras = data.getExtras();
                        if (extras != null) {
                            Bitmap photo = (Bitmap) extras.get("data");
                            if (photo != null) {
                                binding.contract.setImageBitmap(photo);
                                bitmapContract = photo;
                            }
                        }
                    }
                }
            });

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
                                binding.imgUser.setImageBitmap(bitmap);
                                bitmapuser = bitmap;
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
                                binding.imgCccdtruoc.setImageBitmap(bitmap);
                                bitmapcccd1 = bitmap;
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
                if (result.getResultCode() == RESULT_OK && isImage == 3) {
                    Intent data = result.getData();
                    if (data != null) {
                        Uri selectedImageUri = data.getData();
                        if (selectedImageUri != null) {
                            try {
                                InputStream inputStream = getContentResolver().openInputStream(selectedImageUri);
                                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                                binding.imgCccdsau.setImageBitmap(bitmap);
                                bitmapcccd2 = bitmap;
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
                if (result.getResultCode() == RESULT_OK && isImage == 4) {
                    Intent data = result.getData();
                    if (data != null) {
                        Uri selectedImageUri = data.getData();
                        if (selectedImageUri != null) {
                            try {
                                InputStream inputStream = getContentResolver().openInputStream(selectedImageUri);
                                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                                binding.contract.setImageBitmap(bitmap);
                                bitmapContract = bitmap;
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            });


    private void openSettingPermission() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", getPackageName(),null);
        intent.setData(uri);
        startActivity(intent);
    }



    private void setSpinnerSex() {
        spinnerSexAdapter = ArrayAdapter.createFromResource(this, R.array.spinner_sex, R.layout.spinner_item);
        spinnerSexAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        binding.spinSex.setAdapter(spinnerSexAdapter);
        binding.spinSex.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                sex = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
    private void setSpinnerRelative() {
        spinnerRelativeAdapter = ArrayAdapter.createFromResource(this, R.array.spinner_relative, R.layout.spinner_item);
        spinnerRelativeAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        binding.spinRelative.setAdapter(spinnerRelativeAdapter);
        binding.spinRelative.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                relative = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void creatSpinnerNameBuildingAdapter() {
        spinnerNameBuildingAdapter = new BuildingAdapter(this, R.layout.item_selected_spinner);
        binding.spinBuilding.setAdapter(spinnerNameBuildingAdapter);

    }
    private void creatSpinnerNameApartmentAdapter() {
       spinnerNameApartmentAdapter = new ApartmentAdapter(this, R.layout.spinner_item);
        binding.spinApartment.setAdapter(spinnerNameApartmentAdapter);
    }
}
