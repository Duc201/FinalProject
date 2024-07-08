package com.example.urban_area_manager.feature.Admin.Home.Resident.View;


import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.example.urban_area_manager.R;
import com.example.urban_area_manager.core.base.BaseActivity;
import com.example.urban_area_manager.core.component.DataLocalManager;
import com.example.urban_area_manager.databinding.FragmentDetailsResidentBinding;
import com.example.urban_area_manager.feature.Admin.Home.Resident.Model.DetailsResident;
import com.example.urban_area_manager.feature.Admin.Home.Resident.Model.Resident;
import com.example.urban_area_manager.feature.Admin.Home.Resident.ViewModel.ResidentViewModel;
import com.example.urban_area_manager.utils.Constant;
import com.example.urban_area_manager.utils.extensions.Extensions;
import com.example.urban_area_manager.utils.view.DialogView;
import com.google.firebase.Timestamp;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class DetailsResidentActivity extends BaseActivity<FragmentDetailsResidentBinding, ResidentViewModel> {
    private Resident resident;
    private List<DetailsResident> listdetailsResident;
    private int sex;
    private int country_name;
    int relative;
    private TableRow tableRow;
    private ArrayAdapter<CharSequence> spinnerSexAdapter;
    private ArrayAdapter<CharSequence> spinnerRelativeAdapter;
    private DatePickerDialog datePickerDialog;
    private Date date;
    private ActivityResultLauncher<Intent> editDetailsActivityResultLauncher;


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_toolbar, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.save) {
            resident.setFullName(binding.edtResidentName.getText().toString().trim());
            resident.setIndentityNumber(binding.edtResidentCccd.getText().toString().trim());
            resident.setAddress(binding.edtResidentAddress.getText().toString().trim());
            resident.setNationality(binding.spinNationality.getSelectedCountryNameCode());
            resident.setPhoneNumber(binding.edtResidentPhone.getText().toString());
            if(date != null){
                resident.setBirth(date);
            }
            resident.setSex(sex);
            if(DataLocalManager.getEmail() != null){
                resident.setLastModifierUserId(DataLocalManager.getEmail());

            }
            resident.setLastModificationTime(Timestamp.now().toDate());
            for(DetailsResident detailsResident : listdetailsResident){
                detailsResident.setNameUser(binding.edtResidentName.getText().toString().trim());
            }
            viewModel.updateResidentall(resident,listdetailsResident);

            Toast.makeText(this, "Save", Toast.LENGTH_SHORT).show();
            finish();

            return true;
        } else if (id == R.id.delete) {
            viewModel.deleteResidentall(resident.getId());
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCommonViewLoaded() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            resident = (Resident) bundle.getSerializable(Constant.GO_TO_DetailsResidentFragment);
        }

        viewModel.getListDetailsResident(resident.getId(),1);
        initDatePicker();


        setSupportActionBar(binding.toolbar);

        binding.toolbar.setTitle(resident.getFullName());
        setSpinnerSex();
        binding.spinNationality.setCountryForNameCode(resident.getNationality());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/YYYY");
        binding.edtResidentBirth.setText(simpleDateFormat.format(resident.getBirth()));
        binding.edtResidentCccd.setText(resident.getIndentityNumber());
        binding.edtResidentEmail.setText(resident.getEmail());
        binding.edtResidentPhone.setText(resident.getPhoneNumber());
        binding.edtResidentAddress.setText(resident.getAddress());
        binding.edtResidentName.setText(resident.getFullName());
        binding.spinSex.setSelection(resident.getSex());
        Glide.with(this).load(resident.getImageUrl()).into(binding.imgResident);
        editDetailsActivityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK) {
                            viewModel.getListDetailsResident(resident.getId(),1);
                        }
                    }
                });
    }

    private void initDatePicker() {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener()
        {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                LocalDate localDate = LocalDate.of(year, month + 1, dayOfMonth);
                Date date1 = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
                binding.edtResidentBirth.setText(Extensions.FormatDate(date1));
                date =date1;
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

    @Override
    public void addDataObserve() {
        super.addDataObserve();
        viewModel.DetailsResident().observe(this,list->{
            if (list != null && !list.isEmpty()) {
                // Clear the existing list and add all new items
                if (listdetailsResident == null) {
                    listdetailsResident = list;
                } else {
                    listdetailsResident.clear();
                    listdetailsResident.addAll(list);
                }
            }
            for(DetailsResident dt : list){
                int childCount = binding.tableLayout.getChildCount();
                if (childCount > 1) { // Nếu có ít nhất 1 hàng tiêu đề và 1 hàng dữ liệu
                    binding.tableLayout.removeViews(1, childCount - 1);
                }
                 tableRow = (TableRow) LayoutInflater.from(this).inflate(R.layout.table_row, null);
                TextView building = tableRow.findViewById(R.id.textViewId);
                TextView apartment = tableRow.findViewById(R.id.textViewName);
                TextView relative = tableRow.findViewById(R.id.textViewDescription);

                building.setText(dt.getBuildingName());
                apartment.setText(dt.getApartmentName());

                switch (dt.getRelationShip()){
                    case 0:
                        relative.setText("Chủ Hộ");
                        break;
                    case 1:
                        relative.setText("Chồng");
                        break;
                    case 2:
                        relative.setText("Vợ");
                        break;
                    case 3:
                        relative.setText("Con trai");
                        break;
                    case 4:
                        relative.setText("Con gái");
                        break;
                    case 5:
                        relative.setText("Bố Vợ");
                        break;
                    case 6:
                        relative.setText("Mẹ Vợ");
                        break;
                    case 7:
                        relative.setText("Anh");
                        break;
                    case 8:
                        relative.setText("Em");
                        break;
                    case 9:
                        relative.setText("Chị");
                        break;
                }
                tableRow.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(DetailsResidentActivity.this, EditDetailsResidentActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putSerializable(Constant.GO_TO_DetailsResidentFragment, dt);
                        intent.putExtras(bundle);
                        editDetailsActivityResultLauncher.launch(intent);
                    }
                });
                tableRow.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        Runnable listenPositive = () -> {
//                                viewModel.deleteDetailsResident(dt.getId());
                        };
                        DialogView.showDialogDescriptionByHtml(DetailsResidentActivity.this,"Thông báo","Bạn có chắc chắn muốn xóa không",
                                listenPositive);
                        return true;
                    }
                });
                binding.tableLayout.addView(tableRow);
            }

        });
        viewModel.isDeleteResidentall.observe(this,isDelete->{
            if(isDelete){
                Extensions.showToastShort(this,"Xóa thành công");
                finish();
            }
        });
        viewModel.isUpdateResidentall.observe(this, isUpdate->{
            if(isUpdate){
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
        binding.edtResidentBirth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               datePickerDialog.show();
            }
        });


    }



    @Override
    protected FragmentDetailsResidentBinding getViewBinding() {
        return FragmentDetailsResidentBinding.inflate(getLayoutInflater());
    }

    @Override
    protected Class<ResidentViewModel> getViewModelClass() {
        return ResidentViewModel.class;
    }



    private void setSpinnerSex() {
        spinnerSexAdapter = ArrayAdapter.createFromResource(this, R.array.spinner_sex, R.layout.spinner_item);
        spinnerSexAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        binding.spinSex.setAdapter(spinnerSexAdapter);
        binding.spinSex.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                sex = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

}