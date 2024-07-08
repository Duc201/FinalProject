package com.example.urban_area_manager.feature.Employee.Repair.Bill;

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
import android.view.View;
import android.widget.AdapterView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.core.content.ContextCompat;

import com.example.urban_area_manager.R;
import com.example.urban_area_manager.core.base.BaseFragment;
import com.example.urban_area_manager.core.component.DataLocalManager;
import com.example.urban_area_manager.databinding.FragmentAddIndexBinding;
import com.example.urban_area_manager.feature.Auth.ui.adapter.ApartmentAdapter;
import com.example.urban_area_manager.feature.Admin.Home.Apartment.Model.Apartment;
import com.example.urban_area_manager.feature.Admin.Home.Bill.Model.Bill;
import com.example.urban_area_manager.feature.Admin.Home.Bill.Model.DetailsBillStep;
import com.example.urban_area_manager.feature.Admin.Home.Bill.Model.FixedService;
import com.example.urban_area_manager.feature.Admin.Home.Bill.Model.Step;
import com.example.urban_area_manager.feature.Admin.Home.Bill.ViewModel.BillViewModel;
import com.example.urban_area_manager.utils.Constant;
import com.example.urban_area_manager.utils.extensions.Extensions;
import com.example.urban_area_manager.utils.view.DialogView;
import com.google.firebase.Timestamp;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


public class AddIndexFragment extends BaseFragment<FragmentAddIndexBinding, BillViewModel> {

    private String idBuilding;
    private int monthCreat;
    private int yearCreat;
    private ApartmentAdapter spinnerNameApartmentAdapter;
    private Bitmap bitmapimage1;
    private Bitmap bitmapimage2;
    private int isImage = 0;
    private String pathWaterOld = "null";
    private String pathElecOld = "null";
//    StepService electricService = new StepService();
//    StepService waterService = new StepService();

    List<Step> electricListStep = new ArrayList<>();
    List<Step> waterListStep = new ArrayList<>();

    FixedService urbanManagerService = new FixedService() ;

    @Override
    public void onCommonViewLoaded() {
        Bundle bundle = getArguments();
        if(bundle!=null){
            idBuilding = bundle.getString(Constant.GO_TO_AddIndexFragment_BUILDING);
            monthCreat = bundle.getInt(Constant.GO_TO_AddIndexFragment_MONTH);
            yearCreat = bundle.getInt(Constant.GO_TO_AddIndexFragment_YEAR);
        }
        viewBinding.tvTimeCreat.setText(monthCreat +"/"+yearCreat);
        viewModel.getlistApartment(idBuilding);
//        viewModel.getStepService(Constant.ID_ELECTRIC);
//        viewModel.getStepService(Constant.ID_WATER);
        viewModel.getFixedService(Constant.ID_Urban_Manager);
        creatSpinnerNameApartmentAdapter();
        viewBinding.edtIndexElectricOld.setText("0");
        viewBinding.edtIndexWaterOld.setText("0");
        viewModel.getStep(Constant.ID_ELECTRIC);
        viewModel.getStep(Constant.ID_WATER);

    }

    @Override
    public void addDataObserve() {
        super.addDataObserve();
        viewModel.listApartment().observe(this,list->{
            spinnerNameApartmentAdapter.clear();
            spinnerNameApartmentAdapter.addAll(list);
            spinnerNameApartmentAdapter.notifyDataSetChanged();
        });
        viewModel.DetailElectricLast().observe(this, detailsBill->{
            if(detailsBill != null){
                viewBinding.edtIndexElectricOld.setText(String.valueOf(detailsBill.getNewIndex()));
                pathElecOld = detailsBill.getPathNewImage();
            }
        });
        viewModel.DetailWaterLast().observe(this, detailsBill -> {
            if(detailsBill != null){
                viewBinding.edtIndexWaterOld.setText(String.valueOf(detailsBill.getNewIndex()));
                pathWaterOld = detailsBill.getPathNewImage();
            }
        });
//        viewModel.StepService().observe(this, step->{
//            if(step.getId().equals(Constant.ID_ELECTRIC)){
//                electricService = step;
//            }
//            else if (step.getId().equals(Constant.ID_WATER)){
//                waterService = step;
//            }
//        });

        viewModel.listStep.observe(this,listStep->{
            for(Step step : listStep){
                if(step.getIdServiceStep().equals(Constant.ID_ELECTRIC)){
                    electricListStep = listStep;
                    return;
                }
                if(step.getIdServiceStep().equals(Constant.ID_WATER)){
                    waterListStep = listStep;
                    return;
                }
            }


        });
        viewModel._FixedService.observe(this, fixedService -> {
            urbanManagerService = fixedService;
        } );
        viewModel.isAddDetails.observe(this,isCheck->{
            if (isCheck){
                Extensions.showToastShort(getActivity(),"Tạo thành công");
                getParentFragmentManager().popBackStack();
            }
        });
    }

    @Override
    public void addViewListener() {
        viewBinding.btnAdd.setOnClickListener(v -> {
            DetailsBillStep detailsBillStep = new DetailsBillStep();

            Apartment apartment = (Apartment) viewBinding.spinApartment.getSelectedItem();
            detailsBillStep.setIdApartment(apartment.getIdApartment());
            detailsBillStep.setNameApartment(apartment.getName());
            detailsBillStep.setIdBuilding(apartment.getIdBuilding());

        });
        viewBinding.spinApartment.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Apartment apartment = (Apartment) parent.getItemAtPosition(position);
                if(monthCreat == 1){
                    viewModel.getDetailElectricLast(apartment.getIdApartment(),Constant.ID_ELECTRIC,12,yearCreat-1);
                    viewModel.getDetuailWaterLast(apartment.getIdApartment(),Constant.ID_WATER,12,yearCreat-1);
                }
                else {
                    viewModel.getDetailElectricLast(apartment.getIdApartment(),Constant.ID_ELECTRIC,monthCreat-1,yearCreat);
                    viewModel.getDetuailWaterLast(apartment.getIdApartment(),Constant.ID_WATER,monthCreat-1,yearCreat);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        viewBinding.imageIndexElectric.setOnClickListener(v->{
            isImage = 1;
            clickRequestPermission();
        });
        viewBinding.imgIndexWater.setOnClickListener(v->{
            isImage = 2;
            clickRequestPermission();
        });
        viewBinding.btnAdd.setOnClickListener(v -> {

            Apartment apartment = (Apartment) viewBinding.spinApartment.getSelectedItem();
            String idBill = UUID.randomUUID().toString();
            Bill bill = new Bill();
            bill.setIdBill(idBill);
            bill.setIdApartment(apartment.getIdApartment());
            bill.setNameBill("Hóa đơn" + monthCreat + "/" +yearCreat);
            bill.setCreationTime(Timestamp.now().toDate());
            bill.setCreatorUserId(DataLocalManager.getEmail());
            viewModel.addBill(bill);

            // dùng viewModel gọi ra


            int sumIndex = Integer.parseInt(viewBinding.edtIndexElectricNew.getText().toString().trim()) - Integer.parseInt(viewBinding.edtIndexElectricOld.getText().toString().trim());
            long sum = Extensions.calculateElectricBill(electricListStep,sumIndex);
            DetailsBillStep detailsBillStepElec = new DetailsBillStep();
            detailsBillStepElec.setId(UUID.randomUUID().toString());
            detailsBillStepElec.setIdBill(idBill);
            detailsBillStepElec.setNamService("Điện");
            detailsBillStepElec.setIdService(Constant.ID_ELECTRIC);
            detailsBillStepElec.setMonth(monthCreat);
            detailsBillStepElec.setYear(yearCreat);
            detailsBillStepElec.setNewIndex(Integer.parseInt(viewBinding.edtIndexElectricNew.getText().toString().trim()));
            detailsBillStepElec.setOldIndex(Integer.parseInt(viewBinding.edtIndexElectricOld.getText().toString().trim()));
            detailsBillStepElec.setSumDetailBill(sum);
            detailsBillStepElec.setPathOldImage(pathElecOld);
            detailsBillStepElec.setIdBuilding(idBuilding);
            detailsBillStepElec.setIdApartment(apartment.getIdApartment());
            detailsBillStepElec.setNameApartment(apartment.getName());
            detailsBillStepElec.setCreationTime(Timestamp.now().toDate());
            detailsBillStepElec.setCreatorUserId(DataLocalManager.getEmail());
            viewModel.addDeatilsElec(detailsBillStepElec);


            int sumIndexWater = Integer.parseInt(viewBinding.edtIndexWaterNew.getText().toString().trim()) - Integer.parseInt(viewBinding.edtIndexWaterOld.getText().toString().trim());
            long sumWater = Extensions.calculateElectricBill(waterListStep,sumIndexWater);
            DetailsBillStep detailsBillStepWater = new DetailsBillStep();
            detailsBillStepWater.setId(UUID.randomUUID().toString());
            detailsBillStepWater.setIdBill(idBill);
            detailsBillStepWater.setIdService(Constant.ID_WATER);
            detailsBillStepWater.setMonth(monthCreat);
            detailsBillStepWater.setYear(yearCreat);
            detailsBillStepWater.setNamService("Nước");
            detailsBillStepWater.setNewIndex(Integer.parseInt(viewBinding.edtIndexWaterNew.getText().toString().trim()));
            detailsBillStepWater.setOldIndex(Integer.parseInt(viewBinding.edtIndexWaterOld.getText().toString().trim()));
            detailsBillStepWater.setSumDetailBill(sumWater);
            detailsBillStepElec.setPathOldImage(pathWaterOld);
            detailsBillStepWater.setIdBuilding(idBuilding);
            detailsBillStepWater.setIdApartment(apartment.getIdApartment());
            detailsBillStepWater.setNameApartment(apartment.getName());
            detailsBillStepWater.setCreationTime(Timestamp.now().toDate());
            detailsBillStepWater.setCreatorUserId(DataLocalManager.getEmail());
            viewModel.addDetailsWater(detailsBillStepWater);
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_none_image);


            if(bitmapimage1 == null ){
                bitmapimage1 = bitmap;
            }
            if(bitmapimage2 ==null){
                bitmapimage2 =  bitmap;

            }
            viewModel.updateListImage(bitmapimage1,bitmapimage2);
        });
    }

    @Override
    protected FragmentAddIndexBinding getBinding(LayoutInflater inflater) {
        return FragmentAddIndexBinding.inflate(getLayoutInflater());
    }

    @Override
    protected Class<BillViewModel> getViewModelClass() {
        return BillViewModel.class;
    }

    @Override
    protected String getTagFragment() {
        return AddIndexFragment.class.getSimpleName();
    }
    private void creatSpinnerNameApartmentAdapter() {
        spinnerNameApartmentAdapter = new ApartmentAdapter(getActivity(), R.layout.spinner_item);
        viewBinding.spinApartment.setAdapter(spinnerNameApartmentAdapter);
    }
    private void clickRequestPermission() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return;
        }
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
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
                                viewBinding.imageIndexElectric.setImageBitmap(photo);
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
                                viewBinding.imgIndexWater.setImageBitmap(photo);
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
                    Runnable listenerPositive = () -> openSettingPermission();
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
}