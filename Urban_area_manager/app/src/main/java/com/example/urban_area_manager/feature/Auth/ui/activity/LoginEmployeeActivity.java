package com.example.urban_area_manager.feature.Auth.ui.activity;

import android.content.Intent;
import android.os.Build;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.core.content.ContextCompat;

import com.example.urban_area_manager.R;
import com.example.urban_area_manager.core.base.BaseActivity;
import com.example.urban_area_manager.core.component.DataLocalManager;
import com.example.urban_area_manager.databinding.ActivityLoginEmployeeBinding;
import com.example.urban_area_manager.feature.Employee.Administrative.AdministrattiveActivity;
import com.example.urban_area_manager.feature.Employee.Repair.RepairActivity;
import com.example.urban_area_manager.feature.Employee.Security.SecurityActivity;
import com.example.urban_area_manager.feature.Auth.viewmodel.AuthViewModel;


public class LoginEmployeeActivity extends BaseActivity<ActivityLoginEmployeeBinding, AuthViewModel> {
    boolean isPasswordVisible = false;


    @Override
    protected ActivityLoginEmployeeBinding getViewBinding() {
        return ActivityLoginEmployeeBinding.inflate(getLayoutInflater());
    }

    @Override
    protected Class<AuthViewModel> getViewModelClass() {
        return AuthViewModel.class;
    }

    @Override
    public void onCommonViewLoaded() {
//        if(DataLocalManager.getLoginSuccess()){
//            openActivity(MainActivity.class, Intent.FLAG_ACTIVITY_CLEAR_TASK, Intent.FLAG_ACTIVITY_CLEAR_TOP, Intent.FLAG_ACTIVITY_NEW_TASK);
//        }
//        if(DataLocalManager.getPosition() == 0){
//            openActivity(SecurityActivity.class, Intent.FLAG_ACTIVITY_CLEAR_TASK, Intent.FLAG_ACTIVITY_CLEAR_TOP, Intent.FLAG_ACTIVITY_NEW_TASK);
//        }
//        else if (DataLocalManager.getPosition() == 1){
//            openActivity(RepairActivity.class, Intent.FLAG_ACTIVITY_CLEAR_TASK, Intent.FLAG_ACTIVITY_CLEAR_TOP, Intent.FLAG_ACTIVITY_NEW_TASK);
//
//        }
//        else if (DataLocalManager.getPosition() == 2){
//            openActivity(AdministrattiveActivity.class, Intent.FLAG_ACTIVITY_CLEAR_TASK, Intent.FLAG_ACTIVITY_CLEAR_TOP, Intent.FLAG_ACTIVITY_NEW_TASK);
//
//        }
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.blue_background));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
    }

    @Override
    public void addViewListener() {
        binding.btnLogin.setOnClickListener(v -> onClickBtnLogin());
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

    @Override
    public void addDataObserve() {
        super.addDataObserve();
        viewModel.employeeResponse().observe(this,employee -> {
            String password = binding.edtPassword.getText().toString().trim();
            String email = binding.edtEmail.getText().toString().trim();
            if (password.equals(employee.getPass())) {
                showToastShort(getString(R.string.success_login));
                DataLocalManager.setEmail(email);
                DataLocalManager.setPass(password);
                DataLocalManager.setName(employee.getFullName().toString());
                DataLocalManager.setLoginSuccess(true);
                DataLocalManager.setPosition(employee.getDepartment());
                DataLocalManager.setidUser(employee.getId());
                if(employee.getDepartment() == 0){
                    openActivity(SecurityActivity.class, Intent.FLAG_ACTIVITY_CLEAR_TASK, Intent.FLAG_ACTIVITY_CLEAR_TOP, Intent.FLAG_ACTIVITY_NEW_TASK);
                }
                else if (employee.getDepartment() == 1){
                    openActivity(RepairActivity.class, Intent.FLAG_ACTIVITY_CLEAR_TASK, Intent.FLAG_ACTIVITY_CLEAR_TOP, Intent.FLAG_ACTIVITY_NEW_TASK);

                }
                else if (employee.getDepartment() == 2){
                    openActivity(AdministrattiveActivity.class, Intent.FLAG_ACTIVITY_CLEAR_TASK, Intent.FLAG_ACTIVITY_CLEAR_TOP, Intent.FLAG_ACTIVITY_NEW_TASK);

                }
            } else {
                showToastShort(getString(R.string.error_login));
            }
        });
    }

    private void onClickBtnLogin() {
        String email = binding.edtEmail.getText().toString().trim();
        String password = binding.edtPassword.getText().toString().trim();
        if (viewModel.checkValidEmail(email) && viewModel.checkValidPassword(password)) {
            viewModel.loginRequestEmployee(email, password);
        } else {
            showToastShort(getString(R.string.activity_config_account_alert_config_account_label));
        }
    }
}