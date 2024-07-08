package com.example.urban_area_manager.feature.Admin.Reflect.View.Assigned;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.urban_area_manager.R;
import com.example.urban_area_manager.core.base.BaseActivity;
import com.example.urban_area_manager.databinding.ActivityDetailsAssignedReflectBinding;
import com.example.urban_area_manager.feature.Admin.Home.Resident.View.Authen.VisibleImageFragment;
import com.example.urban_area_manager.feature.Admin.Reflect.Model.Reflect;
import com.example.urban_area_manager.feature.Admin.Reflect.Model.Photo;
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

public class DetailsAssignedReflectActivity extends BaseActivity<ActivityDetailsAssignedReflectBinding, ReflectViewModel> implements OnMapReadyCallback {
    private Reflect reflect;
    private List<Photo> photoList = new ArrayList<>();
    private GoogleMap mMap;
    private static final int DEFAULT_ZOOM = 18;



    @Override
    protected ActivityDetailsAssignedReflectBinding getViewBinding() {
        return ActivityDetailsAssignedReflectBinding.inflate(getLayoutInflater());
    }

    @Override
    protected Class<ReflectViewModel> getViewModelClass() {
        return ReflectViewModel.class;
    }

    @Override
    public void onCommonViewLoaded() {
        SupportMapFragment mapFragment = (SupportMapFragment)
                getSupportFragmentManager().findFragmentById(R.id.map_assig);
        mapFragment.getMapAsync(this);


        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            reflect = (Reflect) bundle.getSerializable(Constant.GO_TO_DetailsAssignedReflectActivity);
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
//                VisibleImageFragment visibleImageFragment = new VisibleImageFragment();
//                replaceFragmentBundle(visibleImageFragment,R.id.frame_assigned_reflect,true,true,bundle);
                VisibleImageFragment visibleImageFragment = new VisibleImageFragment();
                visibleImageFragment.setArguments(bundle);

                // Hiển thị Fragment
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
        binding.nameEmployee.setText(reflect.getNameHandler());

    }

    @Override
    public void addViewListener() {
        binding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;
        mMap.getUiSettings().setZoomGesturesEnabled(true);
        mMap.getUiSettings().setScrollGesturesEnabled(true);
        LatLng myPos = new LatLng(reflect.getLatitude(), reflect.getLongitude());
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(myPos, DEFAULT_ZOOM));
        mMap.addMarker(new MarkerOptions().title("Vị trí phản ánh").position(myPos));
    }
}