package com.example.urban_area_manager.feature.Resident.Reflect.Process;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.example.urban_area_manager.R;
import com.example.urban_area_manager.core.base.BaseActivity;
import com.example.urban_area_manager.core.component.DataLocalManager;
import com.example.urban_area_manager.databinding.ActivityResidentDetailsProcessReflectBinding;
import com.example.urban_area_manager.feature.Admin.Home.Resident.View.Authen.VisibleImageFragment;
import com.example.urban_area_manager.feature.Admin.Reflect.Model.Photo;
import com.example.urban_area_manager.feature.Admin.Reflect.Model.Reflect;
import com.example.urban_area_manager.feature.Admin.Reflect.ViewModel.ReflectViewModel;
import com.example.urban_area_manager.feature.Admin.Reflect.adapter.PhotoViewpager2Adapter;
import com.example.urban_area_manager.feature.Resident.Reflect.MyReflect.EditResidentProcessReflectFragment;
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

public class ResidentDetailsProcessReflectActivity extends BaseActivity<ActivityResidentDetailsProcessReflectBinding, ReflectViewModel> implements OnMapReadyCallback {
    private Reflect reflect;
    private List<Photo> photoList = new ArrayList<>();
    private GoogleMap mMap;
    private PhotoViewpager2Adapter photoViewpager2Adapter;

    private static final int DEFAULT_ZOOM = 18;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_toolbar_edit, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.edit) {
            // Tạo một Bundle và đặt dữ liệu vào
            Bundle bundle = new Bundle();
            bundle.putSerializable(Constant.GO_TO_EditResidentProcessReflectFragment, reflect);
            EditResidentProcessReflectFragment fragment = new EditResidentProcessReflectFragment();
            fragment.setArguments(bundle);
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.frame_reflect_details_process, fragment);
            transaction.commit();


        }
        else if (id == R.id.delete) {
            Runnable listenerPositive = () -> {

            };
            DialogView.showDialogDescriptionByHtml(this,"Xác nhận","Bạn có muốn xóa toa nha này không",listenerPositive);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    protected ActivityResidentDetailsProcessReflectBinding getViewBinding() {
        return ActivityResidentDetailsProcessReflectBinding.inflate(getLayoutInflater());
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

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            reflect = (Reflect) bundle.getSerializable(Constant.GO_TO_ResidentDetailsProcessReflectActivity);
            Log.d("haha",reflect.toString());
        }
        if(reflect.getIdCreator().equals(DataLocalManager.getEmail())){
            setSupportActionBar(binding.toolbar);
        }
        photoList.add(new Photo(reflect.getImage1()));
        photoList.add(new Photo(reflect.getImage2()));

        photoViewpager2Adapter = new PhotoViewpager2Adapter();
        photoViewpager2Adapter.submitList(photoList);
        binding.imagesVp2.setAdapter(photoViewpager2Adapter);
        binding.circleIndicator3.setViewPager(binding.imagesVp2);

        photoViewpager2Adapter.setOnItemClickListener(new PhotoViewpager2Adapter.OnItemClickListener() {
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

        binding.tvContent.setText(reflect.getContent());
        binding.field.setText(reflect.getField());
        binding.tvLoaction.setText(reflect.getLoaction());
        binding.tvSpecificLocation.setText(reflect.getSpecificLocation());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.getDefault());
        String dateString = simpleDateFormat.format(reflect.getTimeCreator());
        binding.timeCreator.setText(dateString);
    }

    @Override
    public void addDataObserve() {

    }

    @Override
    public void addViewListener() {

    }
    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;
        LatLng myPos = new LatLng(reflect.getLatitude(), reflect.getLongitude());
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(myPos, DEFAULT_ZOOM));
        mMap.addMarker(new MarkerOptions().title("Vị trí phản ánh").position(myPos));

    }
}