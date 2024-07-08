package com.example.urban_area_manager.utils.view;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;

import com.bumptech.glide.Glide;
import com.example.urban_area_manager.R;
import com.example.urban_area_manager.databinding.BottomSheetBillBinding;
import com.example.urban_area_manager.databinding.BottomSheetDetailsbillBinding;
import com.example.urban_area_manager.databinding.BottomSheetDetailsbillResidentBinding;
import com.example.urban_area_manager.databinding.BottomSheetIotBinding;
import com.example.urban_area_manager.databinding.DialogAddDetailsSensorBinding;
import com.example.urban_area_manager.databinding.DialogChangeNameBinding;
import com.example.urban_area_manager.databinding.DialogChangePasswordBinding;
import com.example.urban_area_manager.databinding.DialogChangePhoneBinding;
import com.example.urban_area_manager.databinding.DialogChangeSexBinding;
import com.example.urban_area_manager.databinding.DialogConfirmWithDescriptionBinding;
import com.example.urban_area_manager.databinding.DialogDevicePasswordBinding;
import com.example.urban_area_manager.databinding.DialogRejectBinding;
import com.example.urban_area_manager.feature.Admin.Home.Bill.Model.DetailsBillStep;
import com.example.urban_area_manager.utils.Constant;
import com.example.urban_area_manager.utils.extensions.Extensions;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.text.SimpleDateFormat;
import java.util.function.Consumer;

public class DialogView {

    public static void showDialogDescriptionByHtml(Activity activity, String title, String description, final Runnable listenerPositive) {
        try {
            DialogConfirmWithDescriptionBinding binding = DialogConfirmWithDescriptionBinding.inflate(LayoutInflater.from(activity));
            Dialog dialog = new Dialog(activity);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setCancelable(false);
            dialog.setContentView(binding.getRoot());
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

            binding.btnAccept.setOnClickListener(v -> {
                listenerPositive.run();
                dialog.dismiss();
            });

            binding.btnCancel.setOnClickListener(v -> dialog.dismiss());

            binding.tvTitle.setText(title);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                binding.tvDescription.setText(Html.fromHtml(description, Html.FROM_HTML_MODE_LEGACY));
            } else {
                binding.tvDescription.setText(Html.fromHtml(description));
            }

            dialog.show();
        } catch (Exception exception) {

        }
    }

    public static void showDialogOptions(Context context, String title, String[] options, DialogInterface.OnClickListener listener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setItems(options, listener);
        builder.create().show();
    }
    public static void showDialogSerial(
            Activity activity,
            final Consumer<String> listenerPositive
    ) {
        try {
            DialogDevicePasswordBinding binding = DialogDevicePasswordBinding.inflate(LayoutInflater.from(activity));

            final Dialog dialog = new Dialog(activity, R.style.AppThemeNew_DialogTheme);
            dialog.setCancelable(false);
            dialog.setContentView(binding.getRoot());

            binding.btnAccept.setOnClickListener(v -> listenerPositive.accept(binding.edtSerial.getText().toString().trim()));

            binding.btnCancel.setOnClickListener(v -> dialog.dismiss());

            dialog.show();
        } catch (Exception e) {
            // Handle exception
        }
    }
    public static void showDialogReject(Activity activity, String title, final OnAcceptListener listenerPositive) {
        try {
            DialogRejectBinding binding =  DialogRejectBinding.inflate(LayoutInflater.from(activity));
            Dialog dialog = new Dialog(activity);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setCancelable(false);
            dialog.setContentView(binding.getRoot());
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

            binding.btnAccept.setOnClickListener(v -> {
                String rejectReason = binding.edtReject.getText().toString();
                listenerPositive.onAccept(rejectReason);
                dialog.dismiss();
            });

            binding.btnCancel.setOnClickListener(v -> dialog.dismiss());

            binding.tvTitle.setText(title);

            dialog.show();
        } catch (Exception exception) {

        }
    }
    public interface OnAcceptListener {
        void onAccept(String rejectReason);
    }
    public static void showDialogChangePhone(Activity activity, String title, final OnAcceptListener listenerPositive) {
        try {
            DialogChangePhoneBinding binding =  DialogChangePhoneBinding.inflate(LayoutInflater.from(activity));
            Dialog dialog = new Dialog(activity);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setCancelable(false);
            dialog.setContentView(binding.getRoot());
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

            binding.btnAccept.setOnClickListener(v -> {
                String phone = binding.edtReject.getText().toString();
                listenerPositive.onAccept(phone);
                dialog.dismiss();
            });

            binding.btnCancel.setOnClickListener(v -> dialog.dismiss());

            binding.tvTitle.setText(title);

            dialog.show();
        } catch (Exception exception) {

        }
    }
    public static void showDialogAddDetailsSensor(Activity activity,String title, final OnAcceptListener1 listenerPositive){
        try {
            DialogAddDetailsSensorBinding binding =  DialogAddDetailsSensorBinding.inflate(LayoutInflater.from(activity));
            Dialog dialog = new Dialog(activity);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setCancelable(false);
            dialog.setContentView(binding.getRoot());
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

            binding.btnAccept.setOnClickListener(v -> {
                String name = binding.edtName.getText().toString();
                String location = binding.edtLocation.getText().toString();
                listenerPositive.onAccept(name,location);
                dialog.dismiss();
            });

            binding.btnCancel.setOnClickListener(v -> dialog.dismiss());

            binding.tvTitle.setText(title);

            dialog.show();
        } catch (Exception exception) {

        }
    }
    public interface OnAcceptListener1 {
        void onAccept(String location, String name);
    }
    public static void showDialogChangeName(Activity activity, String title, final OnAcceptListener listenerPositive) {
        try {
            DialogChangeNameBinding binding =  DialogChangeNameBinding.inflate(LayoutInflater.from(activity));
            Dialog dialog = new Dialog(activity);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setCancelable(false);
            dialog.setContentView(binding.getRoot());
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

            binding.btnAccept.setOnClickListener(v -> {
                String phone = binding.edtReject.getText().toString();
                listenerPositive.onAccept(phone);
                dialog.dismiss();
            });

            binding.btnCancel.setOnClickListener(v -> dialog.dismiss());

            binding.tvTitle.setText(title);

            dialog.show();
        } catch (Exception exception) {

        }
    }
    public static void showDialogChangePass(Activity activity, String title, final OnAcceptListenerChangePass listenerPositive) {
        try {
            DialogChangePasswordBinding binding =  DialogChangePasswordBinding.inflate(LayoutInflater.from(activity));
            Dialog dialog = new Dialog(activity);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setCancelable(false);
            dialog.setContentView(binding.getRoot());
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));


            binding.btnAccept.setOnClickListener(v -> {
                String oldpass = binding.oldPass.getText().toString().trim();
                String newpass = binding.newPass.getText().toString().trim();
                String renewpass = binding.reNewPass.getText().toString().trim();
                listenerPositive.onAccept(oldpass,newpass,renewpass);
                dialog.dismiss();
            });

            binding.btnCancel.setOnClickListener(v -> dialog.dismiss());

            binding.tvTitle.setText(title);

            dialog.show();
        } catch (Exception exception) {

        }
    }
    public interface OnAcceptListenerChangePass {
        void onAccept(String oldpass , String newPass , String renewPass);
    }
    public static void showBottomSheetIot(Activity activity, String title, final OnOpenListener listenerPositive) {
        try {
            BottomSheetIotBinding binding =  BottomSheetIotBinding.inflate(LayoutInflater.from(activity));
            BottomSheetDialog dialog = new BottomSheetDialog(activity);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(binding.getRoot());
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

            binding.imageCamera.setOnClickListener(v -> {
                listenerPositive.openCamera();
                dialog.dismiss();
            });
            binding.imageDevice.setOnClickListener(v -> {
                listenerPositive.openIOT();
                dialog.dismiss();
            });

            dialog.show();
        } catch (Exception exception) {

        }
    }
    public interface OnOpenListener {
        void openCamera();
        void openIOT();
    }

    public static void showBottomSheetBill(Activity activity, String title, final OnOpenBillListener listenerPositive) {
        try {
            BottomSheetBillBinding binding =  BottomSheetBillBinding.inflate(LayoutInflater.from(activity));
            BottomSheetDialog dialog = new BottomSheetDialog(activity);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(binding.getRoot());
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

            binding.imageService.setOnClickListener(v -> {
                listenerPositive.openService();
                dialog.dismiss();
            });
            binding.imageBill.setOnClickListener(v -> {
                listenerPositive.openBill();
                dialog.dismiss();
            });

            dialog.show();
        } catch (Exception exception) {

        }
    }
    public interface OnOpenBillListener {
        void openService();
        void openBill();
    }
    public static void showBottomSheetSex(Activity activity, String title, final OnOpenSexListener listenerPositive) {
        try {
            int sex;
            DialogChangeSexBinding binding =  DialogChangeSexBinding.inflate(LayoutInflater.from(activity));
            BottomSheetDialog dialog = new BottomSheetDialog(activity);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(binding.getRoot());
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

            binding.man.setOnClickListener(v -> {
                listenerPositive.saveSex(0);
                dialog.dismiss();
            });
            binding.girl.setOnClickListener(v -> {
                listenerPositive.saveSex(1);
                dialog.dismiss();
            });
            binding.lgbt.setOnClickListener(v->{
                listenerPositive.saveSex(2);
                dialog.dismiss();
            });

            dialog.show();
        } catch (Exception exception) {

        }
    }
    public interface OnOpenSexListener {
        void saveSex(int sex);
    }
    public static void showBottomSheetDetails(Activity activity, DetailsBillStep electricbill , DetailsBillStep waterbill, final OnOpenDetailsBillListener listenerPositive){
        BottomSheetDetailsbillBinding binding = BottomSheetDetailsbillBinding.inflate(LayoutInflater.from(activity));
        BottomSheetDialog dialog = new BottomSheetDialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(binding.getRoot());
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));


        binding.indexElectricOld.setText(String.valueOf(electricbill.getOldIndex()));
        binding.indexElectricNew.setText(String.valueOf(electricbill.getNewIndex()));
        binding.indexWaterOld.setText(String.valueOf(waterbill.getOldIndex()));
        binding.indexWaterNew.setText(String.valueOf(waterbill.getNewIndex()));

        Glide.with(activity).load(waterbill.getPathNewImage()).into(binding.imgWater);
        Glide.with(activity).load(electricbill.getPathNewImage()).into(binding.imgElectri);

        binding.nameApartment.setText(electricbill.getNameApartment());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String date = simpleDateFormat.format(electricbill.getCreationTime());
        binding.timeDetails.setText(date);
        StringBuilder sumElectric = new StringBuilder();
        sumElectric.append(electricbill.getNewIndex() - electricbill.getOldIndex()).append(" ").append("kwH");
        binding.sumElectric.setText(sumElectric);

        StringBuilder waterBuilder = new StringBuilder();
        waterBuilder.append(waterbill.getNewIndex() - waterbill.getOldIndex()).append(" ").append("m³");
        binding.sumWater.setText(waterBuilder);
        binding.btnEdit.setOnClickListener(v->{
            listenerPositive.openEdit();
            dialog.dismiss();
        });
        binding.btnDelete.setOnClickListener(v->{
            listenerPositive.delete();
            dialog.dismiss();
        });
        binding.imgElectri.setOnClickListener(v->{
            listenerPositive.openImage(electricbill.getPathNewImage());
            dialog.dismiss();

        });
        binding.imgWater.setOnClickListener(v->{
            listenerPositive.openImage(waterbill.getPathNewImage());
            dialog.dismiss();

        });
        dialog.show();
    }

    public interface  OnOpenDetailsBillListener {
        void openEdit();
        void delete();
        void openImage(String path);
    }

    public static void showBottomSheetDetailsResident(Activity activity, DetailsBillStep electricbill, final OnOpenDetailsBillListener1 listenerPositive){
        BottomSheetDetailsbillResidentBinding binding = BottomSheetDetailsbillResidentBinding.inflate(LayoutInflater.from(activity));
        BottomSheetDialog dialog = new BottomSheetDialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(binding.getRoot());
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));


        binding.indexElectricOld.setText(String.valueOf(electricbill.getOldIndex()));
        binding.indexElectricNew.setText(String.valueOf(electricbill.getNewIndex()));


        Glide.with(activity).load(electricbill.getPathNewImage()).into(binding.imgElectriNew);
        Glide.with(activity).load(electricbill.getPathOldImage()).into(binding.imgElectricOld);


        binding.nameApartment.setText(electricbill.getNameApartment());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String date = simpleDateFormat.format(electricbill.getCreationTime());
        binding.timeDetails.setText(date);
        StringBuilder sumElectric = new StringBuilder();
        if(electricbill.getIdService().equals(Constant.ID_WATER)){
            sumElectric.append(electricbill.getNewIndex() - electricbill.getOldIndex()).append(" ").append("Khối");
        }
        else {
            sumElectric.append(electricbill.getNewIndex() - electricbill.getOldIndex()).append(" ").append("kwH");

        }
        binding.sumElectric.setText(sumElectric);
        binding.sumBill.setText(Extensions.formatMoney(electricbill.getSumDetailBill()));
        binding.titleBill.setText(electricbill.getNamService());

        binding.btnEdit.setOnClickListener(v->{
            dialog.dismiss();
        });
        binding.imgElectricOld.setOnClickListener(v->{
            listenerPositive.openImage(electricbill.getPathOldImage());
            dialog.dismiss();
        });
        binding.imgElectriNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listenerPositive.openImage(electricbill.getPathNewImage());
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    public interface  OnOpenDetailsBillListener1 {
        void openImage(String path);
    }
}
