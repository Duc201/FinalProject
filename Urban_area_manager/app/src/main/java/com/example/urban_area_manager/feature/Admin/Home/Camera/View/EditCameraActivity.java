package com.example.urban_area_manager.feature.Admin.Home.Camera.View;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import androidx.core.content.ContextCompat;

import com.example.urban_area_manager.R;
import com.example.urban_area_manager.core.base.BaseActivity;
import com.example.urban_area_manager.databinding.FragmentEditCameraBinding;
import com.example.urban_area_manager.feature.Admin.Home.Camera.Model.Camera;
import com.example.urban_area_manager.feature.Admin.Home.Camera.ViewModel.CameraViewModel;
import com.example.urban_area_manager.utils.Constant;
import com.example.urban_area_manager.utils.extensions.Extensions;


public class EditCameraActivity extends BaseActivity<FragmentEditCameraBinding, CameraViewModel> {
    private boolean state;
    private ArrayAdapter<CharSequence> spinnerStateAdapter;
    private Camera camera;

    @Override
    public void onCommonViewLoaded() {
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.light_background));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
        Intent intent = new Intent();
        Bundle bundle = intent.getExtras();
        if(bundle!=null){
            camera = (Camera) bundle.getSerializable(Constant.GO_TO_EDIT_CAMERA);
        }
        binding.edtCameraArea.setText(camera.getArea());
        binding.edtCameraSerial.setText(camera.getSerial());
        binding.edtProducerName.setText(camera.getName());
        binding.edtCameraRTSP.setText(camera.getLink());
        setSpinnerState();
    }

    @Override
    public void addDataObserve() {
        super.addDataObserve();
        viewModel.isEditSucess.observe(this,ischeck->{
            if(ischeck){
                Extensions.showToastShort(this,"Cập nhật thành công");
                finish();
            }
        });
    }

    @Override
    public void addViewListener() {
       binding.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               finish();
            }
        });
        binding.btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                camera.setName(binding.edtProducerName.getText().toString());
                camera.setSerial(binding.edtCameraSerial.getText().toString());
                camera.setArea(binding.edtCameraArea.getText().toString());
                camera.setLink(binding.edtCameraRTSP.getText().toString().trim());
                camera.setPublic(state);
                viewModel.editCamera(camera);
            }
        });
    }
    private void setSpinnerState() {
        spinnerStateAdapter = ArrayAdapter.createFromResource(this, R.array.spinner_camera_state, R.layout.spinner_item);
        spinnerStateAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        binding.spinSate.setAdapter(spinnerStateAdapter);
        if (camera.getPublic()) {
            binding.spinSate.setSelection(0); // Public
            state = true;
        } else {
            binding.spinSate.setSelection(1); // Private
            state = false;
        }
        binding.spinSate.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position == 0){
                    state = true;
                }
                else
                    state = false;
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }


    @Override
    protected FragmentEditCameraBinding getViewBinding() {
        return FragmentEditCameraBinding.inflate(getLayoutInflater());
    }

    @Override
    protected Class<CameraViewModel> getViewModelClass() {
        return CameraViewModel.class;
    }


}