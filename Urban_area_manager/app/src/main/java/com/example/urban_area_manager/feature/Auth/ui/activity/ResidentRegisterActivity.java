package com.example.urban_area_manager.feature.Auth.ui.activity;

import androidx.core.content.ContextCompat;

import android.os.Build;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.example.urban_area_manager.R;
import com.example.urban_area_manager.core.base.BaseActivity;
import com.example.urban_area_manager.databinding.ActivityResidentRegisterBinding;
import com.example.urban_area_manager.feature.Auth.viewmodel.AuthViewModel;
import com.example.urban_area_manager.utils.view.DialogView;

public class ResidentRegisterActivity extends BaseActivity<ActivityResidentRegisterBinding, AuthViewModel> {


    @Override
    protected ActivityResidentRegisterBinding getViewBinding() {
        return ActivityResidentRegisterBinding.inflate(getLayoutInflater());
    }

    @Override
    protected Class<AuthViewModel> getViewModelClass() {
        return AuthViewModel.class;
    }

    @Override
    public void addDataObserve() {
        super.addDataObserve();
        viewModel.isRegister.observe(this,isRegister->{
            if(isRegister == true){
                Runnable listenerPositive = () -> {
                  finish();
                };
                DialogView.showDialogDescriptionByHtml(this,"Thông báo","Vui lòng mở Email để xác nhân",listenerPositive);
            }
        });
    }

    @Override
    public void onCommonViewLoaded() {
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.blue_background));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
    }

    @Override
    public void addViewListener() {
        binding.btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = binding.edtEmail.getText().toString().trim();
                String pass = binding.edtPassword.getText().toString().trim();
                String rePass = binding.edtRePassword.getText().toString().trim();
                if (!pass.equals(rePass) ) {
                    showToastShort("Mật khẩu chưa khớp");
                }
                if (viewModel.checkValidEmail(email) && viewModel.checkValidPassword(pass)&& viewModel.checkValidPassword(rePass)) {
                    viewModel.registerAccount(email, pass, rePass);
                } else {
                    showToastShort(getString(R.string.activity_config_account_alert_config_account_label));
                }
            }
        });
    }
}