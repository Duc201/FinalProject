package com.example.urban_area_manager.feature.Employee.Repair.Bill;

import static android.app.Activity.RESULT_OK;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.view.LayoutInflater;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.example.urban_area_manager.core.base.BaseFragment;
import com.example.urban_area_manager.core.component.DataLocalManager;
import com.example.urban_area_manager.databinding.FragmentEditIndexBinding;
import com.example.urban_area_manager.feature.Admin.Home.Bill.Model.DetailsBillStep;
import com.example.urban_area_manager.feature.Admin.Home.Bill.Model.Step;
import com.example.urban_area_manager.feature.Admin.Home.Bill.ViewModel.BillViewModel;
import com.example.urban_area_manager.utils.Constant;
import com.example.urban_area_manager.utils.extensions.Extensions;
import com.example.urban_area_manager.utils.view.DialogView;
import com.google.firebase.Timestamp;

import java.util.ArrayList;
import java.util.List;


public class EditIndexFragment extends BaseFragment<FragmentEditIndexBinding, BillViewModel> {

    private DetailsBillStep electriBill;
    private DetailsBillStep waterBill;
    private Bitmap bitmapimage1;
    private Bitmap bitmapimage2;
//    StepService electricService = new StepService();
//    StepService waterService = new StepService();
    private List<Step> electricListStep = new ArrayList<>();
    private List<Step> waterListStep = new ArrayList<>();
    private int isImage = 0;

    @Override
    public void onCommonViewLoaded() {
        Bundle bundle = getArguments();
        if(bundle!=null){
            electriBill = (DetailsBillStep) bundle.getSerializable(Constant.GO_TO_ELECTRICBILL);
            waterBill = (DetailsBillStep) bundle.getSerializable(Constant.GO_TO_WATERBBILL);
        }
//        viewModel.getStepService(Constant.ID_ELECTRIC);
//        viewModel.getStepService(Constant.ID_WATER);
        viewBinding.edtIndexElectricNew.setText(String.valueOf(electriBill.getNewIndex()));
        viewBinding.edtIndexElectricOld.setText(String.valueOf(electriBill.getOldIndex()));
        viewBinding.edtIndexWaterNew.setText(String.valueOf(waterBill.getNewIndex()));
        viewBinding.edtIndexWaterOld.setText(String.valueOf(waterBill.getOldIndex()));
        Glide.with(getActivity()).load(electriBill.getPathNewImage()).into(viewBinding.imageIndexElectric);
        Glide.with(getActivity()).load(waterBill.getPathNewImage()).into(viewBinding.imgIndexWater);
        viewModel.getStep(Constant.ID_ELECTRIC);
        viewModel.getStep(Constant.ID_WATER);
    }

    @Override
    public void addDataObserve() {
        super.addDataObserve();
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
        viewModel.isAddDetailsEdit.observe(this,ischeck->{
            Extensions.showToastShort(getActivity(),"Cập nhật thành công");
            getParentFragmentManager().popBackStack();
        });
    }

    @Override
    public void addViewListener() {
        viewBinding.btnAdd.setOnClickListener(v->{

            int sumIndex = Integer.parseInt(viewBinding.edtIndexElectricNew.getText().toString().trim()) - Integer.parseInt(viewBinding.edtIndexElectricOld.getText().toString().trim());
            long sum = Extensions.calculateElectricBill(electricListStep,sumIndex);
            electriBill.setNewIndex(Integer.parseInt(viewBinding.edtIndexElectricNew.getText().toString().trim()));
            electriBill.setOldIndex(Integer.parseInt(viewBinding.edtIndexElectricOld.getText().toString().trim()));
            electriBill.setSumDetailBill(sum);
            electriBill.setLastModifiTime(Timestamp.now().toDate());
            electriBill.setLastModifierUserId(DataLocalManager.getEmail());
            viewModel.addDeatilsElecEdit(electriBill);


            int sumIndexWater = Integer.parseInt(viewBinding.edtIndexWaterNew.getText().toString().trim()) - Integer.parseInt(viewBinding.edtIndexWaterOld.getText().toString().trim());
            long sumWater = Extensions.calculateElectricBill(waterListStep,sumIndexWater);

            waterBill.setNewIndex(Integer.parseInt(viewBinding.edtIndexWaterNew.getText().toString().trim()));
            waterBill.setOldIndex(Integer.parseInt(viewBinding.edtIndexWaterOld.getText().toString().trim()));
            waterBill.setSumDetailBill(sumWater);
            waterBill.setLastModifiTime(Timestamp.now().toDate());
            waterBill.setLastModifierUserId(DataLocalManager.getEmail());

            viewModel.addDetailsWaterEdit(waterBill);
            viewModel.updateListImageEdit(bitmapimage1,bitmapimage2);
        });
        viewBinding.imageIndexElectric.setOnClickListener(v->{
            isImage = 1;
            clickRequestPermission();
        });
        viewBinding.imgIndexWater.setOnClickListener(v->{
            isImage = 2;
            clickRequestPermission();
        });
    }

    @Override
    protected FragmentEditIndexBinding getBinding(LayoutInflater inflater) {
        return FragmentEditIndexBinding.inflate(getLayoutInflater());
    }

    @Override
    protected Class<BillViewModel> getViewModelClass() {
        return BillViewModel.class;
    }

    @Override
    protected String getTagFragment() {
        return EditIndexFragment.class.getSimpleName();
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