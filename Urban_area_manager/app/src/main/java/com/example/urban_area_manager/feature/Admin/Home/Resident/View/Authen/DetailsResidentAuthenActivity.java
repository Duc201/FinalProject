package com.example.urban_area_manager.feature.Admin.Home.Resident.View.Authen;


import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.example.urban_area_manager.R;
import com.example.urban_area_manager.core.base.BaseActivity;
import com.example.urban_area_manager.core.component.DataLocalManager;
import com.example.urban_area_manager.feature.Admin.Home.Resident.Model.DetailsResident;
import com.example.urban_area_manager.feature.Admin.Home.Resident.Model.Resident;
import com.example.urban_area_manager.databinding.FragmentDetailsResidentAuthenBinding;
import com.example.urban_area_manager.feature.Admin.Home.Resident.ViewModel.ResidentViewModel;
import com.example.urban_area_manager.utils.Constant;
import com.example.urban_area_manager.utils.view.DialogView;

import java.text.SimpleDateFormat;


public class DetailsResidentAuthenActivity extends BaseActivity<FragmentDetailsResidentAuthenBinding, ResidentViewModel> {
    private Resident resident;
    private DetailsResident detailsResident;
    private int sex;
    private int country_name;
    int relative;
    private ArrayAdapter<CharSequence> spinnerSexAdapter;
    private ArrayAdapter<CharSequence> spinnerRelativeAdapter;


    @Override
    public void onCommonViewLoaded() {
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.light_background));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            resident = (Resident) bundle.getSerializable(Constant.GO_TO_DetailsResidentAuthenFragment);
        }

        viewModel.getListDetailResident(resident.getId(),0);
//        viewModel.getDetailsResident(resident.getId(),0);


        binding.toolbar.setTitle(resident.getFullName());
        setSpinnerSex();
        setSpinnerDepartment();
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
        Glide.with(this).load(resident.getImgIdentityCardFront()).into(binding.imgCccdtruoc);
        Glide.with(this).load(resident.getImgIndentityCardBehind()).into(binding.imgCccdsau);
        binding.spinNationality.setClickable(false);
    }

    @Override
    public void addDataObserve() {
        super.addDataObserve();
        viewModel.DetailResident().observe(this,DetailsResident->{
            detailsResident = DetailsResident;
            binding.spinRelative.setSelection(detailsResident.getRelationShip());
            Glide.with(this).load(detailsResident.getImageContract()).into(binding.imgContract);
            binding.edtResidentApartment.setText(detailsResident.getApartmentName());
            binding.edtResidentBuilding.setText(detailsResident.getBuildingName());
        });
        viewModel.isUpdateStateSuccess.observe(this,check->{
            if(check == true){
                Toast.makeText(this,"Xác thực thành công",Toast.LENGTH_SHORT);
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
        binding.imgCccdtruoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                VisibleImageFragment visibleImageFragment = new VisibleImageFragment();
                Bundle bundle = new Bundle();
                bundle.putString(Constant.GO_TO_VisibleImageFragment,resident.getImgIdentityCardFront());
                replaceFragmentBundle(visibleImageFragment,R.id.frame_details_resident_authen,true,true,bundle);
            }
        });
        binding.imgCccdsau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                VisibleImageFragment visibleImageFragment = new VisibleImageFragment();
                Bundle bundle = new Bundle();
                bundle.putString(Constant.GO_TO_VisibleImageFragment,resident.getImgIndentityCardBehind());
                replaceFragmentBundle(visibleImageFragment,R.id.frame_details_resident_authen,true,true,bundle);
            }
        });
        binding.imgContract.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                VisibleImageFragment visibleImageFragment = new VisibleImageFragment();
                Bundle bundle = new Bundle();
                bundle.putString(Constant.GO_TO_VisibleImageFragment,detailsResident.getImageContract());
                replaceFragmentBundle(visibleImageFragment,R.id.frame_details_resident_authen,true,true,bundle);
            }
        });
        binding.btnNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogView.showDialogReject(DetailsResidentAuthenActivity.this, getString(R.string.RejectResult), new DialogView.OnAcceptListener() {
                    @Override
                    public void onAccept(String rejectReason) {
                        String email = resident.getEmail();
                        viewModel.deleteResidentall(resident.getId());
                         openEmail(rejectReason,email );
                        // Bổ xung để xóa thông tin viewModel.deleteUser

                    }
                });
            }
        });
        binding.btnYesd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.updateStateResident(resident.getId(),detailsResident.getId(), DataLocalManager.getEmail(),1);
            }
        });
    }



    @Override
    protected FragmentDetailsResidentAuthenBinding getViewBinding() {
        return FragmentDetailsResidentAuthenBinding.inflate(getLayoutInflater());
    }

    @Override
    protected Class<ResidentViewModel> getViewModelClass() {
        return ResidentViewModel.class;
    }





    private void setSpinnerDepartment() {
        spinnerRelativeAdapter = ArrayAdapter.createFromResource(this, R.array.spinner_relative, R.layout.spinner_item);
        spinnerRelativeAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        binding.spinRelative.setAdapter(spinnerRelativeAdapter);
        binding.spinRelative.setEnabled(false);

    }

    private void setSpinnerSex() {
        spinnerSexAdapter = ArrayAdapter.createFromResource(this, R.array.spinner_sex, R.layout.spinner_item);
        spinnerSexAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        binding.spinSex.setAdapter(spinnerSexAdapter);
        binding.spinSex.setEnabled(false);
    }
    private void openEmail(String result , String email){
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:"));
        intent.putExtra(Intent.EXTRA_EMAIL, new String[] {email});
        intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.Title_Email));
        intent.putExtra(Intent.EXTRA_TEXT, result);
        startActivity(intent);
    }
}