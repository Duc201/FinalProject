package com.example.urban_area_manager.feature.Resident.UserInfor;

import androidx.core.content.ContextCompat;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.DatePicker;

import com.bumptech.glide.Glide;
import com.example.urban_area_manager.R;
import com.example.urban_area_manager.core.base.BaseActivity;
import com.example.urban_area_manager.core.component.DataLocalManager;
import com.example.urban_area_manager.databinding.ActivityResidentInformationBinding;
import com.example.urban_area_manager.feature.Auth.ui.activity.LoginResidentActivity;
import com.example.urban_area_manager.feature.Auth.viewmodel.AuthViewModel;
import com.example.urban_area_manager.utils.Constant;
import com.example.urban_area_manager.utils.extensions.Extensions;
import com.example.urban_area_manager.utils.view.DialogView;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;

public class ResidentInformationActivity extends BaseActivity<ActivityResidentInformationBinding, AuthViewModel> {
    private DatePickerDialog datePickerDialog;

    private Date dateUser;
    private int sexUser;
    private String phone;

    private String nameUser;
    private String passNew;

    @Override
    protected ActivityResidentInformationBinding getViewBinding() {
        return ActivityResidentInformationBinding.inflate(getLayoutInflater());
    }

    @Override
    protected Class<AuthViewModel> getViewModelClass() {
        return AuthViewModel.class;
    }

    @Override
    public void addDataObserve() {
        super.addDataObserve();
        viewModel.isUpdateSex.observe(this,isUpdatesex ->{
            if(isUpdatesex){
                if(sexUser == 0){
                    binding.txtSex.setText("Nam");
                } else if (sexUser == 1) {
                    binding.txtSex.setText("Nữ");
                }
                else
                    binding.txtSex.setText("LGBT");
            }
        });
        viewModel.isUpdatePhone.observe(this,isUpdatePhone ->{
            if(isUpdatePhone){
                binding.txtPhone.setText(phone);
            }
        });
        viewModel.isUpdateBirth.observe(this,isupdateBirth->{
            if(isupdateBirth){
                binding.birthUser.setText(Extensions.FormatDate(dateUser));
            }
        });
        viewModel.residentInfor.observe(this, resident -> {
            if(resident.getSex() == 0){
                binding.txtSex.setText("Nam");
            } else if (resident.getSex() == 1) {
                binding.txtSex.setText("Nữ");
            }
            else
                binding.txtSex.setText("LGBT");

            binding.txtPhone.setText(resident.getPhoneNumber());
            binding.birthUser.setText(Extensions.FormatDate(resident.getBirth()));
            binding.userName.setText(resident.getFullName());
            Glide.with(this).load(resident.getImageUrl()).into(binding.profileImage);

        });
        viewModel.isUpdatePass.observe(this,isUpdatePass ->{
            if(isUpdatePass == true){
                Extensions.showToastShort(this,"Cập nhật mật khẩu thành công");
                DataLocalManager.setPass(passNew);
            }
        });

        viewModel.isUpdateName.observe(this, isUpdateName->{
            if(isUpdateName == true){
                binding.userName.setText(nameUser);
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
        initDatePicker();
        viewModel.getInforUserResident(DataLocalManager.getIdUser());
        binding.birthUser.setText(getTodaysDate());
    }

    @Override
    public void addViewListener() {
        binding.datePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePickerDialog.show();
            }
        });
        binding.sexUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogView.showBottomSheetSex(ResidentInformationActivity.this, "Thay đổi giới tính", new DialogView.OnOpenSexListener() {
                    @Override
                    public void saveSex(int sex) {
                        viewModel.updateSexResident(sex , DataLocalManager.getIdUser());
                        sexUser =sex;
                    }
                });
            }
        });
        binding.phoneUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogView.showDialogChangePhone(ResidentInformationActivity.this, "Thay đổi số điện thoại", new DialogView.OnAcceptListener() {
                    @Override
                    public void onAccept(String rejectReason) {
                        viewModel.updatePhoneResident(rejectReason,DataLocalManager.getIdUser());
                        phone = rejectReason;
                    }
                });
            }
        });

        binding.replacePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogView.showDialogChangePass(ResidentInformationActivity.this, "ĐỔI MẬT KHẨU", new DialogView.OnAcceptListenerChangePass() {
                    @Override
                    public void onAccept(String oldpass, String newPass, String renewPass) {
                        passNew = newPass;
                        viewModel.updatepassResident(oldpass,newPass,renewPass,DataLocalManager.getPass());
                    }
                });
            }
        });
        binding.btnLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Runnable listenerPositive = () -> {
                    DataLocalManager.setEmail(Constant.EMPTY_STRING);
                    DataLocalManager.setPass(Constant.EMPTY_STRING);
                    DataLocalManager.setName(Constant.EMPTY_STRING);
                    DataLocalManager.setidUser(Constant.EMPTY_STRING);
                    DataLocalManager.setLoginSuccess(false);
                    DataLocalManager.setToken(Constant.EMPTY_STRING);
                    openActivity(LoginResidentActivity.class, Intent.FLAG_ACTIVITY_CLEAR_TASK,Intent.FLAG_ACTIVITY_NEW_TASK,Intent.FLAG_ACTIVITY_CLEAR_TASK);
                };
                DialogView.showDialogDescriptionByHtml(
                        ResidentInformationActivity.this,
                        getString(R.string.drawer_menu_label_logout),
                        getString(R.string.confirm_logout_title),
                        listenerPositive
                );


            }
        });
        binding.icEditUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogView.showDialogChangeName(ResidentInformationActivity.this, "THAY ĐỔI TÊN", new DialogView.OnAcceptListener() {
                    @Override
                    public void onAccept(String rejectReason) {
                        viewModel.updateNameResident(rejectReason,DataLocalManager.getIdUser());
                        nameUser = rejectReason;
                    }
                });
            }
        });
        binding.toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    private String getTodaysDate()
    {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        month = month + 1;
        int day = cal.get(Calendar.DAY_OF_MONTH);
        return String.format("%02d/%02d/%04d", day, month, year);
    }
    private void initDatePicker(){
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener()
        {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                LocalDate localDate = LocalDate.of(year, month + 1, dayOfMonth);
                Date date = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
                viewModel.updateBirthResident(date,DataLocalManager.getIdUser());
                dateUser =date;
            }
        };
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        int style = R.style.CustomDatePickerDialogTheme;
        datePickerDialog = new DatePickerDialog(this, style, dateSetListener, year, month, day);
        datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
    }
}