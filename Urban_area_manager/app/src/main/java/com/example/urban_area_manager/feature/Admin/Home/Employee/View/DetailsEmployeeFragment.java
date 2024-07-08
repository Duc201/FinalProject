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
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.example.urban_area_manager.R;
import com.example.urban_area_manager.core.base.BaseFragment;
import com.example.urban_area_manager.core.component.DataLocalManager;
import com.example.urban_area_manager.databinding.FragmentDetailsEmployeeBinding;
import com.example.urban_area_manager.feature.Admin.Home.Employee.Model.Employee;
import com.example.urban_area_manager.feature.Admin.Home.Employee.ViewModel.EmployeeViewModel;
import com.example.urban_area_manager.utils.Constant;
import com.example.urban_area_manager.utils.view.DialogView;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DetailsEmployeeFragment extends BaseFragment<FragmentDetailsEmployeeBinding, EmployeeViewModel> {
    private Employee employee;
    private int sex;
    int department;
    private Date date;

    private ArrayAdapter<CharSequence> spinnerSexAdapter;
    private ArrayAdapter<CharSequence> spinnerDepartmentAdapter;
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
            viewModel.uploadImage(bitmapimage);
            return true;
        } else if (id == R.id.delete) {
            Runnable listenerPositive = () -> {
                employee.setDelete(true);
                employee.setDeleterUserId(DataLocalManager.getEmail());
                viewModel.deleteImageAndEmployee(employee.getImageUrl(),employee);
            };
            DialogView.showDialogDescriptionByHtml(getActivity(),"Xác nhận","Bạn có muốn xóa nhân viên này không",listenerPositive);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onCommonViewLoaded() {
        AppCompatActivity activity = (AppCompatActivity) requireActivity();
        activity.setSupportActionBar(viewBinding.toolbar);

        Bundle bundle = getArguments();
        if (bundle != null) {
            employee = (Employee) bundle.getSerializable(Constant.GO_TO_detailsEmployeeFragment);
        }

        viewBinding.toolbar.setTitle(employee.getFullName());
        setSpinnerSex();
        setSpinnerDepartment();
        viewBinding.spinNationality.setCountryForNameCode(employee.getNationality());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/YYYY");
        viewBinding.edtEmployeeBirth.setText(simpleDateFormat.format(employee.getBirth()));
        viewBinding.edtResidentCccd.setText(employee.getIndentityNumber());
        viewBinding.edtEmployeeEmail.setText(employee.getEmail());
        viewBinding.edtEmployeePhone.setText(employee.getPhoneNumber());
        viewBinding.edtEmployeeAddress.setText(employee.getAddress());
        viewBinding.edtEmployeeName.setText(employee.getFullName());
        viewBinding.spinSex.setSelection(employee.getSex());
        viewBinding.spinDepartment.setSelection(employee.getDepartment());
        String imageUrl = employee.getImageUrl();
        if (imageUrl != null && !imageUrl.isEmpty()) {
            Glide.with(requireContext())
                    .load(imageUrl)
                    .placeholder(R.drawable.icons_user_blue)
                    .error(R.drawable.icons_user_blue)
                    .into(viewBinding.imgResident);
        } else {
            viewBinding.imgResident.setImageResource(R.drawable.icons_user_blue);
        }

    }

    @Override
    public void addDataObserve() {
        super.addDataObserve();
        viewModel._link.observe(this,link->{
            employee.setFullName(viewBinding.edtEmployeeName.getText().toString().trim());
            if(date != null){
                employee.setBirth(date);
            }
            employee.setSex(sex);
            employee.setNationality(viewBinding.spinNationality.getSelectedCountryNameCode());
            employee.setIndentityNumber(viewBinding.edtResidentCccd.getText().toString().trim());
            employee.setAddress(viewBinding.edtEmployeeAddress.getText().toString().trim());
            employee.setPhoneNumber(viewBinding.edtEmployeePhone.getText().toString().trim());
            employee.setDepartment(department);
            employee.setEmail(viewBinding.edtEmployeeEmail.getText().toString().trim());
            if(!link.equals("null")){
                employee.setImageUrl(link);
            }
            employee.setDeleterUserId("null");
            employee.setLastModifierUserId(DataLocalManager.getEmail());
            viewModel.updateEmployee(employee);
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
        viewBinding.edtEmployeeBirth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickDate();
            }
        });
        viewBinding.imgResident.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showImageSourceDialog();
            }
        });
    }

    @Override
    protected FragmentDetailsEmployeeBinding getBinding(LayoutInflater inflater) {
        return FragmentDetailsEmployeeBinding.inflate(getLayoutInflater());
    }

    @Override
    protected Class<EmployeeViewModel> getViewModelClass() {
        return EmployeeViewModel.class;
    }

    @Override
    protected String getTagFragment() {
        return DetailsEmployeeFragment.class.getSimpleName();
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
                viewBinding.edtEmployeeBirth.setText(simpleDateFormat.format(calendar.getTime()));
                date = calendar.getTime();
            }
        },day,month,year);
        datePickerDialog.show();
    }

    private void setSpinnerDepartment(){
        spinnerDepartmentAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.spinner_department, R.layout.spinner_item);
        spinnerDepartmentAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        viewBinding.spinDepartment.setAdapter(spinnerDepartmentAdapter);
        viewBinding.spinDepartment.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                department = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
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
    private final ActivityResultLauncher<Intent> cameraLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK) {
                    Intent data = result.getData();
                    if (data != null) {
                        // Xử lý kết quả từ Intent ở đây
                        Bundle extras = data.getExtras();
                        if (extras != null) {
                            Bitmap photo = (Bitmap) extras.get("data");
                            if (photo != null) {
                                viewBinding.imgResident.setImageBitmap(photo);
                                bitmapimage = photo;
                            }
                        }
                    }
                }
            });
    public void clickRequestPermission() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return;
        }

        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            // Đã cấp quyền
            Intent camera_intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            cameraLauncher.launch(camera_intent); // Sử dụng ActivityResultLauncher để khởi chạy hoạt động
        } else {
            requestPermissionLauncherCamera.launch(Manifest.permission.CAMERA);
        }
    }

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
    private final ActivityResultLauncher<String> requestPermissionLauncherStorage = registerForActivityResult(
            new ActivityResultContracts.RequestPermission(),
            result -> {
                if (result) {
                    openGallery();
                } else {
                    Runnable listenerPositive = () -> {
                        openSettingPermission();
                    };
                    DialogView.showDialogDescriptionByHtml(getActivity(),"Thông báo","Vui lòng thay đổi cài đặt để cấp quyền cho ứng dụng để có thể chọn ảnh từ thư viện",listenerPositive);
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
                                viewBinding.imgResident.setImageBitmap(bitmap);
                                bitmapimage = bitmap;
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
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
            requestPermissionLauncherStorage.launch(Manifest.permission.READ_EXTERNAL_STORAGE);
        }
    }
    private void openGallery() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        galleryLauncher.launch(galleryIntent);
    }
    private void openSettingPermission() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", requireActivity().getPackageName(),null);
        intent.setData(uri);
        startActivity(intent);
    }

}