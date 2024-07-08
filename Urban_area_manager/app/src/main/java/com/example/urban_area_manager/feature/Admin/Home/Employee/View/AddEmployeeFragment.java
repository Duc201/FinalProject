package com.example.urban_area_manager.feature.Admin.Home.Employee.View;


import static android.app.Activity.RESULT_OK;

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
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.core.content.ContextCompat;

import com.example.urban_area_manager.R;
import com.example.urban_area_manager.core.base.BaseFragment;
import com.example.urban_area_manager.core.component.DataLocalManager;
import com.example.urban_area_manager.databinding.FragmentAddEmployeeBinding;
import com.example.urban_area_manager.feature.Admin.Home.Employee.Model.Employee;
import com.example.urban_area_manager.feature.Admin.Home.Employee.ViewModel.EmployeeViewModel;
import com.example.urban_area_manager.utils.Constant;
import com.example.urban_area_manager.utils.view.DialogView;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class AddEmployeeFragment extends BaseFragment<FragmentAddEmployeeBinding, EmployeeViewModel> {

    private int department;
    private int sex;
    private Date date;
    private Bitmap bitmapimage;


    private ArrayAdapter<CharSequence> spinnerSexAdapter;
    @Override
    public void onCommonViewLoaded() {
        Bundle bundle = getArguments();
        department = bundle.getInt(Constant.GO_TO_AddEmployeeFragment);
        setSpinnerSex();

    }

    @Override
    public void addDataObserve() {
        super.addDataObserve();
        viewModel._link.observe(this,link->{
            Employee employee = new Employee();
            employee.setFullName(viewBinding.edtEmployeeName.getText().toString().trim());
            employee.setBirth(date);
            employee.setSex(sex);
            employee.setNationality(viewBinding.spinNationality.getSelectedCountryNameCode());
            employee.setIndentityNumber(viewBinding.edtResidentCccd.getText().toString().trim());
            employee.setAddress(viewBinding.edtEmployeeAddress.getText().toString().trim());
            employee.setPhoneNumber(viewBinding.edtEmployeePhone.getText().toString().trim());
            employee.setDepartment(department);
            employee.setEmail(viewBinding.edtEmployeeEmail.getText().toString().trim());
            if(link != null){
                employee.setImageUrl(link);
            }
            employee.setState(true);
            employee.setCreatorUserId(DataLocalManager.getEmail());
            employee.setDeleterUserId("null");
            employee.setLastModifierUserId(DataLocalManager.getEmail());
            employee.setPass(viewBinding.edtResidentCccd.getText().toString().trim() + "@bka");
            viewModel.addEmployee(employee);
        });
        viewModel.isAddSuccess.observe(this,state->{
            if(state == true){
                getParentFragmentManager().popBackStack();
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
        viewBinding.imgResident.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showImageSourceDialog();
            }
        });
        viewBinding.btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.uploadImage(bitmapimage);
            }
        });
        viewBinding.edtBirth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickDate();
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

    private void clickRequestPermission2() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            openGallery();
            return;
        }
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            openGallery();
        } else {
            requestPermissionLauncherCamera.launch(Manifest.permission.READ_EXTERNAL_STORAGE);
        }
    }

    private void openGallery() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        galleryLauncher.launch(galleryIntent);
    }

    public void clickRequestPermission() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return;
        }

        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            // Đã cấp quyền
            Intent camera_intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            cameraLauncher.launch(camera_intent); // Sử dụng ActivityResultLauncher để khởi chạy hoạt động
        } else {
//            ActivityCompat.requestPermissions(requireActivity(), new String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA_PERMISSION);
            requestPermissionLauncherCamera.launch(Manifest.permission.CAMERA);
        }
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
                                viewBinding.imgResident.setImageBitmap(bitmap);
                                bitmapimage = bitmap;
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            });
    private final ActivityResultLauncher<Intent> cameraLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK) {
                    Intent data = result.getData();
                    if (data != null) {
                        Bundle extras = data.getExtras();
                        if (extras != null) {
                            Bitmap photo = (Bitmap) extras.get("data");
                            bitmapimage = photo;
                            if (photo != null) {
                                viewBinding.imgResident.setImageBitmap(photo);
                                // Lưu đường dẫn của hình ảnh vào filePath (nếu cần)
//                                filePath = getImageUri(requireContext(), photo);
                            }
                        }
                    }
                }
            });
    // Hàm này để chuyển đổi Bitmap thành Uri
//    private Uri getImageUri(Context context, Bitmap bitmap) {
//        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
//        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
//        String path = MediaStore.Images.Media.insertImage(context.getContentResolver(), bitmap, "Title", null);
//        return Uri.parse(path);
//    }
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
                    DialogView.showDialogDescriptionByHtml(getActivity(),"Thông báo","Vui lòng thay " +
                            "đổi cài đặt để cấp quyền cho camera để có thể chụp ảnh minh chứng",listenerPositive);
                }

            });


    private void openSettingPermission() {
            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
            Uri uri = Uri.fromParts("package", requireActivity().getPackageName(),null);
            intent.setData(uri);
            startActivity(intent);
        }



    @Override
    protected FragmentAddEmployeeBinding getBinding(LayoutInflater inflater) {
        return FragmentAddEmployeeBinding.inflate(getLayoutInflater());
    }

    @Override
    protected Class<EmployeeViewModel> getViewModelClass() {
        return EmployeeViewModel.class;
    }

    @Override
    protected String getTagFragment() {
        return AddEmployeeFragment.class.getSimpleName();
    }
    private void clickDate() {
        final Calendar calendar = Calendar.getInstance();
        int day = calendar.get(calendar.DAY_OF_MONTH);
        int month = calendar.get(calendar.MONTH);
        int year = calendar.get(calendar.YEAR);
        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(year,month,dayOfMonth);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/YYYY");
                viewBinding.edtBirth.setText(simpleDateFormat.format(calendar.getTime()));
                date = calendar.getTime();
            }
        },day,month,year);
        datePickerDialog.show();
    }
    private void setSpinnerSex() {
        spinnerSexAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.spinner_sex, R.layout.spinner_item);
        spinnerSexAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        viewBinding.spinSex.setAdapter(spinnerSexAdapter);
        viewBinding.spinSex.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                sex = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
}