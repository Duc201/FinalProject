package com.example.urban_area_manager.feature.Auth.ui.activity;

import android.os.Build;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.core.content.ContextCompat;

import com.example.urban_area_manager.R;
import com.example.urban_area_manager.core.base.BaseActivity;
import com.example.urban_area_manager.databinding.ActivityForgetPassBinding;
import com.example.urban_area_manager.feature.Auth.viewmodel.AuthViewModel;
import com.example.urban_area_manager.utils.extensions.Extensions;

public class ForgetPassActivity extends BaseActivity<ActivityForgetPassBinding, AuthViewModel> {
    @Override
    protected ActivityForgetPassBinding getViewBinding() {
        return ActivityForgetPassBinding.inflate(getLayoutInflater());
    }

    @Override
    protected Class<AuthViewModel> getViewModelClass() {
        return AuthViewModel.class;
    }

    @Override
    public void addDataObserve() {
        super.addDataObserve();
        viewModel.isForgotpass.observe(this,isForgot ->{
            if(isForgot){
                openActivity(OpenEmailActivity.class);
            }
        });
    }

    @Override
    public void onCommonViewLoaded() {
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.light_background));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
    }

    @Override
    public void addViewListener() {
            binding.btnNext.setOnClickListener(v->{
                String email = binding.edtUsername.getText().toString().trim();
                if(viewModel.checkValidEmail(email)){
                    viewModel.ResetPassword(email);
                }
                else {
                    Extensions.showToastShort(this,"Địa chỉ Email không đúng định dạng");
                }

            });
    }
}
