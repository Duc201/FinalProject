package com.example.urban_area_manager.feature.Resident.Home.Bill.ZaloPay;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.core.content.ContextCompat;

import com.example.urban_area_manager.R;
import com.example.urban_area_manager.core.base.BaseActivity;
import com.example.urban_area_manager.core.component.DataLocalManager;
import com.example.urban_area_manager.databinding.ActivityPayBinding;
import com.example.urban_area_manager.feature.Admin.Home.Bill.Model.Bill;
import com.example.urban_area_manager.feature.Admin.Home.Bill.ViewModel.BillViewModel;
import com.example.urban_area_manager.feature.Resident.Home.Bill.ZaloPay.Api.CreateOrder;
import com.example.urban_area_manager.utils.Constant;
import com.example.urban_area_manager.utils.extensions.Extensions;
import com.google.firebase.Timestamp;

import org.json.JSONObject;

import vn.zalopay.sdk.Environment;
import vn.zalopay.sdk.ZaloPayError;
import vn.zalopay.sdk.ZaloPaySDK;
import vn.zalopay.sdk.listeners.PayOrderListener;

public class PayActivity extends BaseActivity<ActivityPayBinding, BillViewModel> {
    private Bill bill;
    @Override
    protected ActivityPayBinding getViewBinding() {
        return ActivityPayBinding.inflate(getLayoutInflater());
    }

    @Override
    protected Class<BillViewModel> getViewModelClass() {
        return BillViewModel.class;
    }

    @Override
    public void onCommonViewLoaded() {
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.light_background));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
        Bundle bundle = getIntent().getExtras();
        if(bundle!=null){
            bill = (Bill) bundle.getSerializable(Constant.GO_TO_PAY);
        }
        StrictMode.ThreadPolicy policy = new
                StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        // ZaloPay SDK Init
        ZaloPaySDK.init(2553, Environment.SANDBOX);
    }

    @Override
    public void addViewListener() {
        binding.zalopay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onLoading(true);
                CreateOrder orderApi = new CreateOrder();
                try {
                    JSONObject data = orderApi.createOrder(String.valueOf(bill.getSumBill()));
                    String code = data.getString("return_code");

                    if (code.equals("1")) {

                        String token = data.getString("zp_trans_token");
                        ZaloPaySDK.getInstance().payOrder(PayActivity.this, token, "demozpdk://app", new PayOrderListener() {
                            @Override
                            public void onPaymentSucceeded(String s, String s1, String s2) {
                                Extensions.showToastShort(PayActivity.this,"Thanh toán thành công");
                                bill.setPayBillTime(Timestamp.now().toDate());
                                bill.setIdPayResident(DataLocalManager.getIdUser());
                                bill.setPay(true);
                                viewModel.updateBill(bill);
                                onLoading(false);

                            }

                            @Override
                            public void onPaymentCanceled(String s, String s1) {
                                Extensions.showToastShort(PayActivity.this,"Thanh toán bị hủy");
                                onLoading(false);

                            }

                            @Override
                            public void onPaymentError(ZaloPayError zaloPayError, String s, String s1) {
                                Extensions.showToastShort(PayActivity.this,"Thanh toán Lỗi");
                                onLoading(false);

                            }
                        });
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }

    @Override
    public void addDataObserve() {
        super.addDataObserve();
        viewModel.isUpdateBill.observe(this,isCheck->{
            if(isCheck = true){
                Intent returnIntent = new Intent();
                returnIntent.putExtra("data_result", "thành công"); // Truyền dữ liệu trở lại Fragment nếu cần
                setResult(RESULT_OK, returnIntent);
                finish();
            }
            else{
                Extensions.showToastShort(this,"Đã trừ ZaloPay nhưng chưa trừ Server");
            }
        });
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        ZaloPaySDK.getInstance().onResult(intent);
    }
}
