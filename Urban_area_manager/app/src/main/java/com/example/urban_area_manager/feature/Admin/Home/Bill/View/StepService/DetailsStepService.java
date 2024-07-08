package com.example.urban_area_manager.feature.Admin.Home.Bill.View.StepService;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

import com.example.urban_area_manager.R;
import com.example.urban_area_manager.core.base.BaseActivity;
import com.example.urban_area_manager.databinding.ActivityDetailsStepServiceBinding;
import com.example.urban_area_manager.databinding.DialogEditStepServiceBinding;
import com.example.urban_area_manager.feature.Admin.Home.Bill.Model.Step;
import com.example.urban_area_manager.feature.Admin.Home.Bill.Model.StepService;
import com.example.urban_area_manager.feature.Admin.Home.Bill.ViewModel.BillViewModel;
import com.example.urban_area_manager.utils.Constant;
import com.example.urban_area_manager.utils.view.DialogView;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class DetailsStepService extends BaseActivity<ActivityDetailsStepServiceBinding, BillViewModel> {
    private StepAdapter stepAdapter =  new StepAdapter();
    private LinearLayoutManager linearLayoutManager ;
    private List<Step> mlistStep = new ArrayList<>();
    private List<Step> mlistStepOld = new ArrayList<>();

    private StepService stepService = new StepService();
    Dialog dialogadd;
    Dialog dialogedit;

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
            if(mlistStep.size() >= mlistStepOld.size()){
                stepService.setName(binding.edtServiceName.getText().toString().trim());
                stepService.setUnit(binding.edtServiceUnit.getText().toString().trim());
                viewModel.updateAllStepService(stepService,mlistStep);
            }
            else {
                mlistStepOld.remove(mlistStep);
                viewModel.removeListStepService(mlistStepOld);
            }
        }
        else if (id == R.id.delete) {
            Runnable listenPositive = () -> {
                if(stepService.getId().equals(Constant.ID_ELECTRIC) || stepService.getId().equals(Constant.ID_WATER)){
                    Toast.makeText(this, "Dịch vụ này không cho phép xóa", Toast.LENGTH_SHORT).show();
                }
                else {
                    viewModel.deleteStepService(stepService);
                }
            };
            DialogView.showDialogDescriptionByHtml(this,"Xóa dịch vụ","Bạn có chắc chắn " +
                    "muốn xóa dịch vụ này không ?", listenPositive);
        }

        return super.onOptionsItemSelected(item);
    }
    @Override
    protected ActivityDetailsStepServiceBinding getViewBinding() {
        return ActivityDetailsStepServiceBinding.inflate(getLayoutInflater());
    }

    @Override
    protected Class<BillViewModel> getViewModelClass() {
        return BillViewModel.class;
    }

    @Override
    public void onCommonViewLoaded() {
        Bundle bundle = getIntent().getExtras();
        if(bundle!=null){
            stepService = (StepService) bundle.getSerializable("Duc123");
        }
        setSupportActionBar(binding.toolbar);
        binding.rcvListStep.setAdapter(stepAdapter);
        linearLayoutManager = new LinearLayoutManager(this);
        binding.rcvListStep.setLayoutManager(linearLayoutManager);
        binding.edtServiceName.setText(stepService.getName());
        binding.edtServiceUnit.setText(stepService.getUnit());
        viewModel.getStep(stepService.getId());
        stepAdapter.setOnItemClickListener(new StepAdapter.OnItemClickListener() {
            @Override
            public void onItemClickEdit(Step step) {
                openDialogEdit(step);
            }

            @Override
            public void onItemClickDelete(Step step) {
              if(isStepHigher(step)){
                  Toast.makeText(DetailsStepService.this, "Bậc này chỉ có thể sửa", Toast.LENGTH_SHORT).show();
              }
              else {
                  mlistStep.remove(step);
                  stepAdapter.clearList();
                  stepAdapter.submitList(mlistStep);
              }
            }
        });
    }

    @Override
    public void addDataObserve() {
        super.addDataObserve();
        viewModel.listStep.observe(this,listStep->{
            mlistStep = new ArrayList<>(listStep);
            mlistStepOld = new ArrayList<>(listStep);
            stepAdapter.submitList(listStep);
        });
        viewModel.isUpdateStepService.observe(this,isUpdate->{
            Toast.makeText(this, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
            finish();
        });
        viewModel.isRemoveStepService.observe(this,  isDelete->{
            if (isDelete == true){
                stepService.setName(binding.edtServiceName.getText().toString().trim());
                stepService.setUnit(binding.edtServiceUnit.getText().toString().trim());
                viewModel.updateAllStepService(stepService,mlistStep);
            }
        });
        viewModel.isDeleteStepService.observe(this,isDelete ->{
            finish();
        });
    }

    Boolean isStepHigher(Step step){
        for(Step step2 :mlistStep ){
            if(step2.getNameStep()>step.getNameStep()){
                return true;
            }
        }
        return false;
    }
    @Override
    public void addViewListener() {
        binding.btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialogAdd();
            }
        });
    }

        private void openDialogAdd() {
        DialogEditStepServiceBinding binding =  DialogEditStepServiceBinding.inflate(LayoutInflater.from(this));
        dialogadd = new Dialog(this);
        dialogadd.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogadd.setCancelable(false);
        dialogadd.setContentView(binding.getRoot());
        dialogadd.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        binding.nameStep.setText(String.valueOf(getNextNameStep()));

            if (!mlistStep.isEmpty()) {
                Step lastStep = mlistStep.get(mlistStep.size() - 1);
                binding.startvalue.setText(String.valueOf(lastStep.getEndValue()+1));
            }
            else {
                binding.startvalue.setText("0");
            }
            binding.btnAccept.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String id = null;
                    int nextNameStep = getNextNameStep();
                    for (Step step : mlistStepOld) {
                        if (nextNameStep == step.getNameStep()) {
                            id = step.getIdStep();
                            break;
                        }
                    }
                    if (id == null) {
                        id = UUID.randomUUID().toString();
                    }
                    int valueStart = 0;
                    int valueEnd = 0;
                    long price = 0;

                    try {
                        valueStart = Integer.valueOf(binding.startvalue.getText().toString().trim());
                        price = Long.parseLong(binding.priceStep.getText().toString().trim());
                    } catch (NumberFormatException e) {
                        Toast.makeText(DetailsStepService.this, "Vui lòng nhập số hợp lệ", Toast.LENGTH_SHORT).show();
                        return; // Kết thúc phương thức onClick để ngăn ngừa lỗi tiếp theo
                    }

                    String valueEndText = binding.endValue.getText().toString().trim();
                    if (valueEndText.isEmpty()) {
                        valueEnd = Integer.MAX_VALUE;
                    } else {
                        try {
                            valueEnd = Integer.valueOf(valueEndText);
                        } catch (NumberFormatException e) {
                            Toast.makeText(DetailsStepService.this, "Vui lòng nhập số hợp lệ", Toast.LENGTH_SHORT).show();
                            return; // Kết thúc phương thức onClick để ngăn ngừa lỗi tiếp theo
                        }
                    }

                    if (valueEnd < valueStart) {
                        Toast.makeText(DetailsStepService.this, "Giá chỉ cuối phải lớn hơn giá trị đầu", Toast.LENGTH_SHORT).show();
                    } else {
                        Step step = new Step(id, getNextNameStep(), valueStart, valueEnd, price, stepService.getId());
                        mlistStep.add(step);
                        stepAdapter.clearList();
                        stepAdapter.submitList(mlistStep);
                        dialogadd.dismiss();
                    }
                }
            });


        binding.btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogadd.dismiss();
            }
        });
        dialogadd.show();
    }
    private void openDialogEdit(Step step) {
        DialogEditStepServiceBinding binding =  DialogEditStepServiceBinding.inflate(LayoutInflater.from(this));
        dialogedit = new Dialog(this);
        dialogedit.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogedit.setCancelable(false);
        dialogedit.setContentView(binding.getRoot());
        dialogedit.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        binding.dialogName.setText("Chỉnh sửa bậc: "+ step.getNameStep());
        binding.nameStep.setText("Bậc" + step.getNameStep());
        binding.endValue.setText(String.valueOf(step.getEndValue()));
        binding.startvalue.setText(String.valueOf(step.getStartValue()));
        binding.priceStep.setText(String.valueOf(step.getPrice()));

        binding.btnAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.nameStep.setText(String.valueOf(getNextNameStep()));
                int valueStart = Integer.valueOf(binding.startvalue.getText().toString().trim());
                int valueEnd = Integer.valueOf(binding.endValue.getText().toString().trim());
                long price = Long.valueOf(binding.priceStep.getText().toString().trim());
                mlistStep.remove(step);
                Step step1 = new Step(step.getIdStep(),getNextNameStep(),valueStart,valueEnd,price,stepService.getId());
                if(valueEnd<valueStart){
                    Toast.makeText(DetailsStepService.this, "Giá chỉ cuối phải lớn hơn giá trị đầu", Toast.LENGTH_SHORT).show();
                }
                else {
                    mlistStep.add(step1);
                    //update Adapter
                    stepAdapter.clearList();
                    stepAdapter.submitList(mlistStep);
                    dialogedit.dismiss();
                }
            }
        });

        binding.btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogedit.dismiss();
            }
        });
        dialogedit.show();
    }
    private int getNextNameStep() {
        List<Integer> existingNameSteps = new ArrayList<>();
            for (Step step : mlistStep) {
                existingNameSteps.add(step.getNameStep());
            }
        int nextNameStep = 1;
        while (existingNameSteps.contains(nextNameStep)) {
            nextNameStep++;
        }
        return nextNameStep;
    }
}