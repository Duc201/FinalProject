package com.example.urban_area_manager.feature.Employee.EmployeeInfor;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.view.View;
import android.widget.DatePicker;

import com.bumptech.glide.Glide;
import com.example.urban_area_manager.R;
import com.example.urban_area_manager.core.base.BaseActivity;
import com.example.urban_area_manager.core.component.DataLocalManager;
import com.example.urban_area_manager.databinding.ActivityEmployeeInformationBinding;
import com.example.urban_area_manager.feature.Auth.ui.activity.LoginEmployeeActivity;
import com.example.urban_area_manager.feature.Auth.viewmodel.AuthViewModel;
import com.example.urban_area_manager.utils.Constant;
import com.example.urban_area_manager.utils.extensions.Extensions;
import com.example.urban_area_manager.utils.view.DialogView;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;

public class EmployeeInformationActivity extends BaseActivity<ActivityEmployeeInformationBinding, AuthViewModel> {
    private DatePickerDialog datePickerDialog;

    private Date dateUser;
    private int sexUser;
    private String phone;

    private String nameUser;
    private String passNew;

    @Override
    protected ActivityEmployeeInformationBinding getViewBinding() {
        return ActivityEmployeeInformationBinding.inflate(getLayoutInflater());
    }

    @Override
    protected Class<AuthViewModel> getViewModelClass() {
        return AuthViewModel.class;
    }

    @Override
    public void onCommonViewLoaded() {
        initDatePicker();
        viewModel.getInforUserEmployee(DataLocalManager.getIdUser());
        binding.birthUser.setText(getTodaysDate());
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
        viewModel.adminInfor.observe(this, employee -> {
            if(employee.getSex() == 0){
                binding.txtSex.setText("Nam");
            } else if (employee.getSex() == 1) {
                binding.txtSex.setText("Nữ");
            }
            else
                binding.txtSex.setText("LGBT");

            binding.txtPhone.setText(employee.getPhoneNumber());
            binding.birthUser.setText(Extensions.FormatDate(employee.getBirth()));
            binding.userName.setText(employee.getFullName());
            Glide.with(this).load(employee.getImageUrl()).into(binding.profileImage);

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
                DialogView.showBottomSheetSex(EmployeeInformationActivity.this, "Thay đổi giới tính", new DialogView.OnOpenSexListener() {
                    @Override
                    public void saveSex(int sex) {
                        viewModel.updateSexEmployee(sex , DataLocalManager.getIdUser());
                        sexUser =sex;
                    }
                });
            }
        });
        binding.phoneUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogView.showDialogChangePhone(EmployeeInformationActivity.this, "Thay đổi số điện thoại", new DialogView.OnAcceptListener() {
                    @Override
                    public void onAccept(String rejectReason) {
                        viewModel.updatePhoneEmployee(rejectReason,DataLocalManager.getIdUser());
                        phone = rejectReason;
                    }
                });
            }
        });

        binding.replacePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogView.showDialogChangePass(EmployeeInformationActivity.this, "ĐỔI MẬT KHẨU", new DialogView.OnAcceptListenerChangePass() {
                    @Override
                    public void onAccept(String oldpass, String newPass, String renewPass) {
                        passNew = newPass;
                        viewModel.updatepassEmployee(oldpass,newPass,renewPass,DataLocalManager.getPass(),DataLocalManager.getIdUser());
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
                    DataLocalManager.setPosition(9);
                    openActivity(LoginEmployeeActivity.class, Intent.FLAG_ACTIVITY_CLEAR_TASK,Intent.FLAG_ACTIVITY_NEW_TASK,Intent.FLAG_ACTIVITY_CLEAR_TASK);
                };
                DialogView.showDialogDescriptionByHtml(
                        EmployeeInformationActivity.this,
                        getString(R.string.drawer_menu_label_logout),
                        getString(R.string.confirm_logout_title),
                        listenerPositive
                );


            }
        });
        binding.icEditUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogView.showDialogChangeName(EmployeeInformationActivity.this, "THAY ĐỔI TÊN", new DialogView.OnAcceptListener() {
                    @Override
                    public void onAccept(String rejectReason) {
                        viewModel.updateNameEmployee(rejectReason,DataLocalManager.getIdUser());
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
                viewModel.updateBirthEmployee(date,DataLocalManager.getIdUser());
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