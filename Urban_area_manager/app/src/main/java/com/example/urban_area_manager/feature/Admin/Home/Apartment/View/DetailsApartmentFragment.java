package com.example.urban_area_manager.feature.Admin.Home.Apartment.View;

import static android.app.Activity.RESULT_OK;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.urban_area_manager.R;
import com.example.urban_area_manager.core.base.BaseFragment;
import com.example.urban_area_manager.core.component.DataLocalManager;
import com.example.urban_area_manager.databinding.FragmentDetailsApartmentBinding;
import com.example.urban_area_manager.feature.Admin.Home.Apartment.Model.Apartment;
import com.example.urban_area_manager.feature.Admin.Home.Apartment.ViewModel.ApartmentViewModel;
import com.example.urban_area_manager.utils.Constant;
import com.example.urban_area_manager.utils.view.DialogView;
import com.google.firebase.Timestamp;

import java.io.InputStream;
import java.util.ArrayList;


public class DetailsApartmentFragment extends BaseFragment<FragmentDetailsApartmentBinding, ApartmentViewModel> {


    private int state;
    private ArrayAdapter<CharSequence> spinnerStateAdapter;

    private ResidentInApartmentAdapter residentInApartmentAdapter = new ResidentInApartmentAdapter();
    private LinearLayoutManager linearLayoutManager;
    private Apartment apartment;

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
                apartment.setName(viewBinding.edtApartmentName.getText().toString().trim());
                apartment.setFloor(Integer.parseInt(viewBinding.edtBuildingNumberfloor.getText().toString()));
                apartment.setArea(Double.parseDouble(viewBinding.edtApartmentArea.getText().toString()));
                apartment.setDescription(viewBinding.edtApartmentDescribe.getText().toString());
                apartment.setState(state);
                apartment.setDeleterUserId("null");
                apartment.setLastModifierUserId(DataLocalManager.getEmail());
                apartment.setLastModificationTime(Timestamp.now().toDate());
                viewModel.updateApartment(apartment);
            }
            return true;
        } else if (id == R.id.delete) {
            Runnable listenerPositive = () -> {
                apartment.setDeleted(true);
                apartment.setDeleterUserId(DataLocalManager.getEmail());
                apartment.setLastModificationTime(Timestamp.now().toDate());
                viewModel.deleteImageAndApartment(apartment.getImagePath(),apartment);
            };
            DialogView.showDialogDescriptionByHtml(getActivity(),"Xác nhận","Bạn có muốn xóa căn hộ này không",listenerPositive);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCommonViewLoaded() {
        AppCompatActivity activity = (AppCompatActivity) requireActivity();
        activity.setSupportActionBar(viewBinding.toolbar);
        Bundle bundle = getArguments();
        if(bundle!=null){
            apartment = (Apartment) bundle.getSerializable(Constant.GO_TO_DetailsApartmentFragment);
        }
        setSpinnerSate();
        Glide.with(activity).load(apartment.getImagePath()).placeholder(R.drawable.ic_apartment).error(R.drawable.ic_apartment).into(viewBinding.imgApartment);
        viewBinding.edtApartmentName.setText(apartment.getName());
        viewBinding.edtApartmentArea.setText(String.valueOf(apartment.getArea()));
        viewBinding.edtBuildingNumberfloor.setText(String.valueOf(apartment.getFloor()));
        viewBinding.edtApartmentDescribe.setText(apartment.getDescription());
        viewBinding.spinSate.setSelection(apartment.getState());

        viewModel.getResidentInApartment(apartment.getIdApartment());
        linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        viewBinding.rcvListResident.setLayoutManager(linearLayoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(viewBinding.rcvListResident.getContext(),
                linearLayoutManager.getOrientation());
        viewBinding.rcvListResident.addItemDecoration(dividerItemDecoration);
        viewBinding.rcvListResident.setAdapter(residentInApartmentAdapter);
        residentInApartmentAdapter.submitList(new ArrayList<>());
    }

    @Override
    public void addDataObserve() {
        super.addDataObserve();
        viewModel._link.observe(this,link->{
            apartment.setName(viewBinding.edtApartmentName.getText().toString().trim());
            apartment.setFloor(Integer.parseInt(viewBinding.edtBuildingNumberfloor.getText().toString()));
            apartment.setArea(Double.parseDouble(viewBinding.edtApartmentArea.getText().toString()));
            apartment.setDescription(viewBinding.edtApartmentDescribe.getText().toString());
            apartment.setState(state);
            apartment.setImagePath(link);
            apartment.setDeleterUserId("null");
            apartment.setLastModifierUserId(DataLocalManager.getEmail());
            apartment.setLastModificationTime(Timestamp.now().toDate());
            viewModel.updateApartment(apartment);
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
        viewModel.listResdient().observe(this,listResident->{
            if(listResident != null){
                residentInApartmentAdapter.submitList(listResident);
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
        viewBinding.imgApartment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showImageSourceDialog();
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
                                viewBinding.imgApartment.setImageBitmap(bitmap);
                                bitmapimage = bitmap;
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            });
    @Override
    protected FragmentDetailsApartmentBinding getBinding(LayoutInflater inflater) {
        return FragmentDetailsApartmentBinding.inflate(getLayoutInflater());
    }

    @Override
    protected Class<ApartmentViewModel> getViewModelClass() {
        return ApartmentViewModel.class;
    }

    @Override
    protected String getTagFragment() {
        return DetailsApartmentFragment.class.getSimpleName();
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
    private void openSettingPermission() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", requireActivity().getPackageName(),null);
        intent.setData(uri);
        startActivity(intent);
    }
    private void setSpinnerSate() {
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
}