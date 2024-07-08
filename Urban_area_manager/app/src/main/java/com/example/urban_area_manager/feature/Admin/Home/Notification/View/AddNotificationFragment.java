package com.example.urban_area_manager.feature.Admin.Home.Notification.View;


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
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.urban_area_manager.R;
import com.example.urban_area_manager.core.base.BaseFragment;
import com.example.urban_area_manager.core.component.DataLocalManager;
import com.example.urban_area_manager.databinding.FragmentAddNotificationBinding;
import com.example.urban_area_manager.feature.Auth.ui.adapter.ApartmentAdapter;
import com.example.urban_area_manager.feature.Auth.ui.adapter.BuildingAdapter;
import com.example.urban_area_manager.feature.Admin.Home.Apartment.Model.Apartment;
import com.example.urban_area_manager.feature.Admin.Home.Building.Model.Building;
import com.example.urban_area_manager.feature.Admin.Home.Notification.DetailsResidentAdapter;
import com.example.urban_area_manager.feature.Admin.Home.Notification.Model.Notification;
import com.example.urban_area_manager.feature.Admin.Home.Resident.Model.DetailsResident;
import com.example.urban_area_manager.feature.Admin.Home.ViewModel.NotificationViewModel;
import com.example.urban_area_manager.utils.extensions.Extensions;
import com.example.urban_area_manager.utils.view.DialogView;
import com.google.firebase.Timestamp;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


public class AddNotificationFragment extends BaseFragment<FragmentAddNotificationBinding, NotificationViewModel> {
    private ArrayAdapter<CharSequence> spinnerNotificationAdapter;
    private static final int REQUEST_CAMERA_PERMISSION = 343;
    private Bitmap bitmapimage;
    private BuildingAdapter spinnerNameBuildingAdapter;
    private ApartmentAdapter spinnerNameApartmentAdapter;
    private DetailsResidentAdapter spinnerNameResidentAdapter;
    private Building selectedBuilding = new Building();
    private Apartment selectedApartment = new Apartment();
    private List<DetailsResident> detailsResidentList = new ArrayList<>();

    private String linkglobal;


    private void creatSpinnerNameBuildingAdapter() {
        spinnerNameBuildingAdapter = new BuildingAdapter(getActivity(), R.layout.item_selected_spinner);
        viewBinding.spinBulding.setAdapter(spinnerNameBuildingAdapter);

    }
    private void creatSpinnerNameApartmentAdapter() {
        spinnerNameApartmentAdapter = new ApartmentAdapter(getActivity(), R.layout.item_selected_spinner);
        viewBinding.spinApartment.setAdapter(spinnerNameApartmentAdapter);
    }

    private void creatSpinnerNameResidentAdapter() {
        spinnerNameResidentAdapter = new DetailsResidentAdapter(getActivity(), R.layout.item_selected_spinner);
        viewBinding.spinResident.setAdapter(spinnerNameResidentAdapter);
    }
    @Override
    public void onCommonViewLoaded() {
        setSpinnerDepartment();
        creatSpinnerNameApartmentAdapter();
        creatSpinnerNameBuildingAdapter();
        creatSpinnerNameResidentAdapter();
        viewModel.getlistBuilding();
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
                if(bitmapimage == null){
                    Drawable drawable = getResources().getDrawable(R.drawable.ic_apartment, getActivity().getTheme());
                    bitmapimage = Extensions.drawableToBitmap(drawable);
                }
                viewModel.uploadImage(bitmapimage);
            }
        });
        viewBinding.spinBulding.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedBuilding = (Building) parent.getItemAtPosition(position);
                viewModel.getlistApartment(selectedBuilding.getIdBuilding());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        viewBinding.spinApartment.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                 selectedApartment = (Apartment) parent.getItemAtPosition(position);
                 viewModel.getListDetailResidentApartment(selectedApartment.getIdApartment());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

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

    @Override
    public void addDataObserve() {
        super.addDataObserve();
        viewModel._link.observe(this,link->{
            linkglobal = link;
                    Building building = (Building) viewBinding.spinBulding.getSelectedItem();
                    if(building.getNameBuilding().equals("Tất cả")){
                        viewModel.getListDetailsResiden();
                    }
                    else {
                        Apartment apartment = (Apartment) viewBinding.spinApartment.getSelectedItem();
                        if(apartment.getName().equals("Tất cả")){
                            viewModel.getListDetailsResidenInBuilding(building.getIdBuilding());
                        }
                        else{
                            DetailsResident detailsResident = (DetailsResident) viewBinding.spinResident.getSelectedItem();
                            if(detailsResident.getNameUser().equals("Tất cả")){
                                List<Notification> notificationList = new ArrayList<>();
                                for(DetailsResident detailsResident1 : detailsResidentList){
                                    Notification notification = new Notification();
                                    notification.setId(UUID.randomUUID().toString());
                                    notification.setTitle(viewBinding.edtTitle.getText().toString());
                                    notification.setContent(viewBinding.edtContent.getText().toString());
                                    notification.setType(viewBinding.spinNotification.getSelectedItemPosition());
                                    notification.setIdreceiver(detailsResident1.getIdUser());
                                    notification.setIdSender(DataLocalManager.getEmail());
                                    notification.setNameSender(DataLocalManager.getName());
                                    notification.setCreationTime(Timestamp.now().toDate());
                                    notification.setPathImage(link);
                                    notificationList.add(notification);
                                }
                                viewModel.addListNotifi(notificationList);

                            }
                            else {
                                // Tạo một notification thôi chính người đó
                                Notification notification = new Notification();
                                notification.setId(UUID.randomUUID().toString());
                                notification.setTitle(viewBinding.edtTitle.getText().toString());
                                notification.setContent(viewBinding.edtContent.getText().toString());
                                notification.setType(viewBinding.spinNotification.getSelectedItemPosition());
                                notification.setIdreceiver(detailsResident.getIdUser());
                                notification.setIdSender(DataLocalManager.getEmail());
                                notification.setNameSender(DataLocalManager.getName());
                                notification.setCreationTime(Timestamp.now().toDate());
                                notification.setPathImage(link);
                                viewModel.addNotifi(notification);
                            }
                        }
                    }

        });
        viewModel.listbuilding().observe(this, listBuilding -> {
            spinnerNameBuildingAdapter.addAll(listBuilding);
            Building building = new Building();
            building.setNameBuilding("Tất cả");
            spinnerNameBuildingAdapter.add(building);
            spinnerNameBuildingAdapter.notifyDataSetChanged();
        });

        viewModel.listApartment().observe(this, listApartment -> {
            if (!listApartment.isEmpty()) {
                spinnerNameApartmentAdapter.clear();
                spinnerNameApartmentAdapter.addAll(listApartment);
                Apartment apartment = new Apartment();
                apartment.setName("Tất cả");
                spinnerNameApartmentAdapter.add(apartment);
                spinnerNameApartmentAdapter.notifyDataSetChanged();

                // Khi spinnerApartment có dữ liệu, cập nhật spinnerResident
                if (selectedApartment != null) {
                    viewModel.getListDetailResidentApartment(selectedApartment.getIdApartment());
                }
            } else {
                spinnerNameApartmentAdapter.clear();
                spinnerNameApartmentAdapter.notifyDataSetChanged();

                // Làm rỗng spinnerResident khi spinnerApartment trống
                spinnerNameResidentAdapter.clear();
                spinnerNameResidentAdapter.notifyDataSetChanged();
            }
        });

        viewModel.listResidented().observe(this, listResident -> {
            if (!listResident.isEmpty()) {
                spinnerNameResidentAdapter.clear();
                spinnerNameResidentAdapter.addAll(listResident);
                DetailsResident detailsResident = new DetailsResident();
                detailsResident.setNameUser("Tất cả");
                spinnerNameResidentAdapter.add(detailsResident);
                spinnerNameResidentAdapter.notifyDataSetChanged();
            } else {
                spinnerNameResidentAdapter.clear();
                spinnerNameResidentAdapter.notifyDataSetChanged();
            }
        });
        viewModel.ListDetails.observe(this, listDetaiResidentinBuilding->{
            List<Notification> notificationList = new ArrayList<>();
            for(DetailsResident detailsResident : listDetaiResidentinBuilding){
                Notification notification = new Notification();
                notification.setId(UUID.randomUUID().toString());
                notification.setTitle(viewBinding.edtTitle.getText().toString());
                notification.setContent(viewBinding.edtContent.getText().toString());
                notification.setType(viewBinding.spinNotification.getSelectedItemPosition());
                notification.setIdreceiver(detailsResident.getIdUser());
                notification.setIdSender(DataLocalManager.getEmail());
                notification.setNameSender(DataLocalManager.getName());
                notification.setCreationTime(Timestamp.now().toDate());
                notification.setPathImage(linkglobal);
                notificationList.add(notification);
            }
            viewModel.addListNotifi(notificationList);
        });

        viewModel.ListDetailsAll.observe(this, listDetaiResidentin->{
            List<Notification> notificationList = new ArrayList<>();
            for(DetailsResident detailsResident : listDetaiResidentin){
                Notification notification = new Notification();
                notification.setId(UUID.randomUUID().toString());
                notification.setTitle(viewBinding.edtTitle.getText().toString());
                notification.setContent(viewBinding.edtContent.getText().toString());
                notification.setType(viewBinding.spinNotification.getSelectedItemPosition());
                notification.setIdreceiver(detailsResident.getIdUser());
                notification.setIdSender(DataLocalManager.getEmail());
                notification.setNameSender(DataLocalManager.getName());
                notification.setCreationTime(Timestamp.now().toDate());
                notification.setPathImage(linkglobal);
                notificationList.add(notification);
            }
            viewModel.addListNotifi(notificationList);
        });
        viewModel.isAddNoti.observe(this,isAdd->{
            Extensions.showToastShort(getActivity(),"Tạo thành công");
            getParentFragmentManager().popBackStack();
        });
        viewModel.isAddListNoti.observe(this, isAddlist->{
            Extensions.showToastShort(getActivity(),"Tạo thành công");
            getParentFragmentManager().popBackStack();
        });
    }

    @Override
    protected FragmentAddNotificationBinding getBinding(LayoutInflater inflater) {
        return FragmentAddNotificationBinding.inflate(getLayoutInflater());
    }

    @Override
    protected Class<NotificationViewModel> getViewModelClass() {
        return NotificationViewModel.class;
    }

    @Override
    protected String getTagFragment() {
        return AddNotificationFragment.class.getSimpleName();
    }

    private void setSpinnerDepartment(){
        spinnerNotificationAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.spinner_notification, R.layout.spinner_item);
        spinnerNotificationAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        viewBinding.spinNotification.setAdapter(spinnerNotificationAdapter);

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
            ActivityCompat.requestPermissions(requireActivity(), new String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA_PERMISSION);
        }
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
                                viewBinding.imgResident.setImageBitmap(photo);
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
                                viewBinding.imgResident.setImageBitmap(bitmap);
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