package com.example.urban_area_manager.feature.Resident.Reflect.Complete;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.urban_area_manager.R;
import com.example.urban_area_manager.core.base.BaseActivity;
import com.example.urban_area_manager.core.component.DataLocalManager;
import com.example.urban_area_manager.databinding.ActivityResidentDetailsCompleteReflectBinding;
import com.example.urban_area_manager.feature.Admin.Home.Resident.View.Authen.VisibleImageFragment;
import com.example.urban_area_manager.feature.Admin.Reflect.Model.Photo;
import com.example.urban_area_manager.feature.Admin.Reflect.Model.Reflect;
import com.example.urban_area_manager.feature.Admin.Reflect.ViewModel.ReflectViewModel;
import com.example.urban_area_manager.feature.Admin.Reflect.adapter.PhotoViewpager2Adapter;
import com.example.urban_area_manager.utils.Constant;
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

public class ResidentDetailsCompleteReflectActivity extends BaseActivity<ActivityResidentDetailsCompleteReflectBinding, ReflectViewModel>  implements OnMapReadyCallback {

    private Reflect reflect;
    private List<Photo> photoList = new ArrayList<>();
    private List<Photo> photoList1 = new ArrayList<>();
    private PhotoViewpager2Adapter photoViewpager2AdapterNot;
    private  PhotoViewpager2Adapter photoViewpager2AdapterComplete;

    private GoogleMap mMap;
    private static final int DEFAULT_ZOOM = 18;
    @Override
    protected ActivityResidentDetailsCompleteReflectBinding getViewBinding() {
        return ActivityResidentDetailsCompleteReflectBinding.inflate(getLayoutInflater());
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
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map_complete);
        mapFragment.getMapAsync(this);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            reflect = (Reflect) bundle.getSerializable(Constant.GO_TO_ResidentDetailsCompleteReflectActivity);
        }

        photoList.add(new Photo(reflect.getImage1()));
        photoList.add(new Photo(reflect.getImage2()));


        photoList1.add(new Photo(reflect.getRespondimg1()));
        photoList1.add(new Photo(reflect.getRespondimg2()));

        photoViewpager2AdapterNot = new PhotoViewpager2Adapter();
        photoViewpager2AdapterNot.submitList(photoList);
        photoViewpager2AdapterNot.setOnItemClickListener(new PhotoViewpager2Adapter.OnItemClickListener() {
            @Override
            public void onItemClick(Photo photo) {
                Bundle bundle = new Bundle();
                bundle.putString(Constant.GO_TO_VisibleImageFragment,reflect.getImage1());
                // Tạo Fragment và đặt Bundle vào
                VisibleImageFragment visibleImageFragment = new VisibleImageFragment();
                visibleImageFragment.setArguments(bundle);

                // Hiển thị Fragment
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_assigned_reflect, visibleImageFragment);
                transaction.commit();
            }
        });

        photoViewpager2AdapterComplete = new PhotoViewpager2Adapter();
        photoViewpager2AdapterComplete.submitList(photoList1);
        photoViewpager2AdapterComplete.setOnItemClickListener(new PhotoViewpager2Adapter.OnItemClickListener() {
            @Override
            public void onItemClick(Photo photo) {
                Bundle bundle = new Bundle();
                bundle.putString(Constant.GO_TO_VisibleImageFragment,reflect.getImage1());
                // Tạo Fragment và đặt Bundle vào
                VisibleImageFragment visibleImageFragment = new VisibleImageFragment();
                visibleImageFragment.setArguments(bundle);

                // Hiển thị Fragment
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_assigned_reflect, visibleImageFragment).addToBackStack(null);
                transaction.commit();
            }
        });

        CustomButtonclickComplete();

        binding.imagesVp2.setAdapter(photoViewpager2AdapterComplete);
        binding.circleIndicator3.setViewPager(binding.imagesVp2);


        binding.tvContent.setText(reflect.getContent());
        binding.field.setText(reflect.getField());
        binding.tvLoaction.setText(reflect.getLoaction());
        binding.tvSpecificLocation.setText(reflect.getSpecificLocation());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.getDefault());
        String dateString = simpleDateFormat.format(reflect.getTimeCreator());
        binding.timeCreator.setText(dateString);

        SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.getDefault());
        String dateString1 = simpleDateFormat1.format(reflect.getTimeHandle());
        binding.timefinish.setText(dateString1);
        // convert
        binding.nameEmployee.setText(reflect.getNameHandler());
        binding.result.setText(reflect.getResult());
        binding.feelback.setText(reflect.getFeelback());

        if(! reflect.getIdCreator().equals(DataLocalManager.getEmail())){
            binding.save.setVisibility(View.GONE);
            binding.edit.setVisibility(View.GONE);
            binding.feelback.setInputType(InputType.TYPE_NULL);
        }
        else {
            binding.save.setVisibility(View.GONE);
            binding.edit.setVisibility(View.VISIBLE);
            EditResult();
        }
        binding.toolbar.setTitle(reflect.getContent());

    }

    private void EditResult() {
       binding.edit.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               binding.edit.setVisibility(View.GONE);
               binding.save.setVisibility(View.VISIBLE);
           }
       });
       binding.save.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               if(binding.feelback.getText().toString().trim().equals(reflect.getFeelback())){
                   binding.save.setVisibility(View.GONE);
                   binding.edit.setVisibility(View.VISIBLE);
               }
               else {
                   String feelback =  binding.feelback.getText().toString().trim();
                   viewModel.updateFeedback(feelback, reflect.getId());
               }
           }
       });
    }

    @Override
    public void addDataObserve() {
        super.addDataObserve();
        viewModel.updateFeedback.observe(this,isUpdate->{
            if(isUpdate){
                Toast.makeText(this, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                binding.save.setVisibility(View.GONE);
                binding.edit.setVisibility(View.VISIBLE);
                binding.feelback.setInputType(InputType.TYPE_NULL);
            }
        });
    }

    @Override
    public void addViewListener() {
        binding.complete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.imagesVp2.setAdapter(photoViewpager2AdapterComplete);
                binding.circleIndicator3.setViewPager(binding.imagesVp2);
                CustomButtonclickComplete();
            }
        });
        binding.not.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.imagesVp2.setAdapter(photoViewpager2AdapterNot);
                binding.circleIndicator3.setViewPager(binding.imagesVp2);
                CustomButtonclickNot();
            }
        });
    }
    public void CustomButtonclickComplete(){
        binding.complete.setBackgroundColor(ContextCompat.getColor(this, R.color.blue_background));
        binding.complete.setTextColor(ContextCompat.getColor(this, R.color.whiteTextColor));
        binding.not.setBackgroundColor(ContextCompat.getColor(this, R.color.cv_background_color));
        binding.not.setTextColor(ContextCompat.getColor(this, R.color.grey_image));
    }

    public void CustomButtonclickNot(){
        binding.not.setBackgroundColor(ContextCompat.getColor(this, R.color.blue_background));
        binding.not.setTextColor(ContextCompat.getColor(this, R.color.whiteTextColor));
        binding.complete.setBackgroundColor(ContextCompat.getColor(this, R.color.cv_background_color));
        binding.complete.setTextColor(ContextCompat.getColor(this, R.color.grey_image));
    }


    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;
        LatLng myPos = new LatLng(reflect.getLatitude(), reflect.getLongitude());
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(myPos, DEFAULT_ZOOM));
        mMap.addMarker(new MarkerOptions().title("Vị trí phản ánh").position(myPos));
    }
}