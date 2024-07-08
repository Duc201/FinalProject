package com.example.urban_area_manager.feature.Admin.Reflect.View.Process;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.urban_area_manager.R;

import com.example.urban_area_manager.core.base.BaseActivity;
import com.example.urban_area_manager.databinding.ActivityDetailsProcessReflectBinding;
import com.example.urban_area_manager.feature.Admin.Home.Employee.Model.Employee;
import com.example.urban_area_manager.feature.Admin.Home.Resident.View.Authen.VisibleImageFragment;
import com.example.urban_area_manager.feature.Admin.Reflect.Model.Reflect;
import com.example.urban_area_manager.feature.Admin.Reflect.Model.Photo;
import com.example.urban_area_manager.feature.Admin.Reflect.ViewModel.ReflectViewModel;
import com.example.urban_area_manager.feature.Admin.Reflect.adapter.PhotoViewpager2Adapter;
import com.example.urban_area_manager.utils.Constant;
import com.example.urban_area_manager.utils.view.DialogView;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
public class DetailsProcessReflectActivity extends BaseActivity<ActivityDetailsProcessReflectBinding, ReflectViewModel> implements OnMapReadyCallback {

    private Reflect reflect;
    private List<Photo> photoList = new ArrayList<>();
    private GoogleMap mMap;
    private int department;
    private ArrayAdapter<CharSequence> spinnerDepartmentAdapter;

    private EmployeeAdapter employeeAdapter;
    private String nameEmployee;
    private String idEmployee;
    private static final int DEFAULT_ZOOM = 18;


    @Override
    protected ActivityDetailsProcessReflectBinding getViewBinding() {
        return ActivityDetailsProcessReflectBinding.inflate(getLayoutInflater());
    }

    @Override
    protected Class<ReflectViewModel> getViewModelClass() {
        return ReflectViewModel.class;
    }

    @Override
    public void onCommonViewLoaded() {
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.light_background));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
        SupportMapFragment mapFragment = (SupportMapFragment)
                getSupportFragmentManager().findFragmentById(R.id.map_process);
        mapFragment.getMapAsync(this);

        setSpinnerDepartment();
        setSpinnerEmpolyee();

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            reflect = (Reflect) bundle.getSerializable("123");
        }

        photoList.add(new Photo(reflect.getImage1()));
        photoList.add(new Photo(reflect.getImage2()));

        PhotoViewpager2Adapter photoViewpager2Adapter = new PhotoViewpager2Adapter();
        binding.imagesVp2.setAdapter(photoViewpager2Adapter);
        binding.circleIndicator3.setViewPager(binding.imagesVp2);
        photoViewpager2Adapter.submitList(photoList);
        photoViewpager2Adapter.setOnItemClickListener(new PhotoViewpager2Adapter.OnItemClickListener() {
            @Override
            public void onItemClick(Photo photo) {
                Bundle bundle = new Bundle();
                bundle.putString(Constant.GO_TO_VisibleImageFragment,reflect.getImage1());
                VisibleImageFragment visibleImageFragment = new VisibleImageFragment();
                visibleImageFragment.setArguments(bundle);
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_assigned_reflect_admin, visibleImageFragment).addToBackStack(null);
                transaction.commit();
            }
        });

        binding.tvContent.setText(reflect.getContent());
        binding.field.setText(reflect.getField());
        binding.tvLoaction.setText(reflect.getLoaction());
        binding.tvSpecificLocation.setText(reflect.getSpecificLocation());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.getDefault());
        String dateString = simpleDateFormat.format(reflect.getTimeCreator());
        binding.timeCreator.setText(dateString);
        binding.toolbar.setTitle(reflect.getContent());
    }

    @Override
    public void addDataObserve() {
        super.addDataObserve();
        viewModel.listEmployee().observe(this,list->{
            employeeAdapter.clear();
            employeeAdapter.addAll(list);
            employeeAdapter.notifyDataSetChanged();
        });
        viewModel.updateProcessReflectNo.observe(this,list->{
            Toast.makeText(this, "Từ chối thành oông", Toast.LENGTH_SHORT).show();
            finish();
        });
        viewModel.updateProcessReflectYes.observe(this,list->{
            Toast.makeText(this, "Phân công thành oông", Toast.LENGTH_SHORT).show();
            finish();
        });
    }


    @Override
    public void addViewListener() {
        binding.btnNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogView.showDialogReject(DetailsProcessReflectActivity.this, "Lý do từ chối", new DialogView.OnAcceptListener() {
                    @Override
                    public void onAccept(String rejectReason) {
                        viewModel.updateProcessReflectNo(rejectReason,2,reflect.getId());
                    }
                });
            }
        });
        binding.btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Employee Employee = (com.example.urban_area_manager.feature.Admin.Home.Employee.Model.Employee) binding.spEmployee.getSelectedItem();
                String Employeename = Employee.getFullName();
                viewModel.updateProcessReflectYes(department,Employee.getEmail(),Employee.getFullName(),1,reflect.getId());
            }
        });
        binding.spDepartment.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                department = position;
                viewModel.getlistEmployee(position);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                department = 0;
                viewModel.getlistEmployee(0);
            }
        });
        binding.spEmployee.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;
        LatLng myPos = new LatLng(reflect.getLatitude(), reflect.getLongitude());
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(myPos, DEFAULT_ZOOM));
        mMap.addMarker(new MarkerOptions().title("Vị trí phản ánh").position(myPos));

    }

    private void setSpinnerEmpolyee() {
        employeeAdapter = new EmployeeAdapter(this, R.layout.item_selected_spinner);
        binding.spEmployee.setAdapter(employeeAdapter);
    }
    private void setSpinnerDepartment(){
        spinnerDepartmentAdapter = ArrayAdapter.createFromResource(this, R.array.spinner_department, R.layout.spinner_item);
        spinnerDepartmentAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        binding.spDepartment.setAdapter(spinnerDepartmentAdapter);
    }


}