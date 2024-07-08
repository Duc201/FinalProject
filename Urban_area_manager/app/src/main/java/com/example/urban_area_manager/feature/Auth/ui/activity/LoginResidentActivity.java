package com.example.urban_area_manager.feature.Auth.ui.activity;

import android.os.Build;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.core.content.ContextCompat;

import com.example.urban_area_manager.R;
import com.example.urban_area_manager.core.base.BaseActivity;
import com.example.urban_area_manager.core.component.DataLocalManager;
import com.example.urban_area_manager.databinding.ActivityLoginResidentBinding;
import com.example.urban_area_manager.feature.Auth.viewmodel.AuthViewModel;
import com.example.urban_area_manager.feature.Resident.ResidentMainActivity;
import com.example.urban_area_manager.utils.view.DialogView;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class LoginResidentActivity extends BaseActivity<ActivityLoginResidentBinding, AuthViewModel> {
    boolean isPasswordVisible = false;
    @Override
    protected ActivityLoginResidentBinding getViewBinding() {
        return ActivityLoginResidentBinding.inflate(getLayoutInflater());
    }

    @Override
    protected Class<AuthViewModel> getViewModelClass() {
        return AuthViewModel.class;
    }
    String password;
    @Override
    public void onCommonViewLoaded() {
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.blue_background));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
        binding.tvForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity(ForgetPassActivity.class);
            }
        });
        binding.tvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity(ResidentRegisterActivity.class);
            }
        });
        binding.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickBtnLogin();
            }
        });
        binding.eyeImage.setOnClickListener(v -> {
            isPasswordVisible = !isPasswordVisible;
            if (isPasswordVisible) {
                binding.edtPassword.setTransformationMethod(null);
                binding.eyeImage.setSelected(true);
            } else {
                binding.edtPassword.setTransformationMethod(new PasswordTransformationMethod());
                binding.eyeImage.setSelected(false);
            }
            binding.edtPassword.setSelection(binding.edtPassword.length());
        });


    }

    private void onClickBtnLogin() {
        String email = binding.edtEmail.getText().toString().trim();
        password = binding.edtPassword.getText().toString().trim();
        if (viewModel.checkValidEmail(email) && viewModel.checkValidPassword(password)) {
            viewModel.loginResident(email, password);
        } else {
            showToastShort(getString(R.string.activity_config_account_alert_config_account_label));
        }
    }



    @Override
    public void addViewListener() {
        binding.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickBtnLogin();
            }
        });
    }

    @Override
    public void addDataObserve() {
        super.addDataObserve();
        viewModel.isLogin.observe(this,isLogin->{
            if(isLogin){
                String email = binding.edtEmail.getText().toString().trim();
                viewModel.checkAccontResident(email);
            }
        });
        viewModel.ischeckAccontResident.observe(this,resident->{
            if(resident == null){
                DataLocalManager.setEmail(binding.edtEmail.getText().toString().trim());
                openActivity(RegisterActivity.class);
            }
            else {
                if(resident.getState() == 0){
                    Runnable listenPositive = () -> {
                    };
                    DialogView.showDialogDescriptionByHtml(this,"Thông báo","Tài khoản của cư dân đang trong giai đoạn xét duyệt. Xin cám ơn",
                            listenPositive);
                }
                else if (resident.getState() == 1) {
                    openActivity(ResidentMainActivity.class);
                    DataLocalManager.setidUser(resident.getId());
                    DataLocalManager.setEmail(binding.edtEmail.getText().toString().trim());
                    DataLocalManager.setPass(password);
                    DataLocalManager.setName(resident.getFullName().toString());
                    DataLocalManager.setLoginSuccess(true);

                            Map<String,Object> tokenData = new HashMap<>();
                            tokenData.put("token",DataLocalManager.getToken());
                            FirebaseFirestore firestore = FirebaseFirestore.getInstance();
                            firestore.collection("Resident").document(DataLocalManager.getIdUser()).update(tokenData);

                }
            }

        });
    }
}
