package com.example.urban_area_manager.feature.Resident.Reflect.MyReflect;

import android.os.Bundle;

import androidx.annotation.NonNull;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.example.urban_area_manager.R;
import com.example.urban_area_manager.core.base.BaseFragment;
import com.example.urban_area_manager.databinding.FragmentEditResidentProcessReflectBinding;
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


public class EditResidentProcessReflectFragment extends BaseFragment<FragmentEditResidentProcessReflectBinding, ReflectViewModel>implements OnMapReadyCallback {
    private Reflect reflect;
    private List<Photo> photoList = new ArrayList<>();
    private GoogleMap mMap;
    private static final int DEFAULT_ZOOM = 18;

    @Override
    public void onCommonViewLoaded() {
        // Lấy dữ liệu từ Bundle
        Bundle bundle = getArguments();
        if (bundle != null) {
            reflect = (Reflect) bundle.getSerializable(Constant.GO_TO_EditResidentProcessReflectFragment);
        }
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map_edit_resident);
        mapFragment.getMapAsync(this);

        photoList.add(new Photo(reflect.getImage1()));
        photoList.add(new Photo(reflect.getImage2()));

        PhotoViewpager2Adapter photoViewpager2Adapter = new PhotoViewpager2Adapter();
        viewBinding.imagesVp2.setAdapter(photoViewpager2Adapter);
        viewBinding.circleIndicator3.setViewPager(viewBinding.imagesVp2);
        photoViewpager2Adapter.submitList(photoList);
        photoViewpager2Adapter.setOnItemClickListener(new PhotoViewpager2Adapter.OnItemClickListener() {
            @Override
            public void onItemClick(Photo photo) {
                Bundle bundle = new Bundle();
                bundle.putString(Constant.GO_TO_VisibleImageFragment,reflect.getImage1());
                VisibleImageFragment visibleImageFragment = new VisibleImageFragment();
                openFragment(visibleImageFragment,R.id.frame_reflect_details_process,bundle);
            }
        });
        viewBinding.edtContent.setText(reflect.getContent());
        viewBinding.field.setText(reflect.getField());
        viewBinding.tvLoaction.setText(reflect.getLoaction());
        viewBinding.edtSpecificLocation.setText(reflect.getSpecificLocation());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.getDefault());
        String dateString = simpleDateFormat.format(reflect.getTimeCreator());
        viewBinding.timeCreator.setText(dateString);
    }

    @Override
    public void addDataObserve() {
        viewModel.updateReflectResident.observe(this,isCheck->{
            if(isCheck == true){
                Toast.makeText(getActivity(), "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                getActivity().finish();
            }
        });
    }

    @Override
    public void addViewListener() {
        viewBinding.btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content = viewBinding.edtContent.getText().toString();
                String specificLocation = viewBinding.edtSpecificLocation.getText().toString();
                viewModel.updateReflectResident(content , specificLocation , reflect.getId());
            }
        });
    }

    @Override
    protected FragmentEditResidentProcessReflectBinding getBinding(LayoutInflater inflater) {
        return FragmentEditResidentProcessReflectBinding.inflate(getLayoutInflater());
    }

    @Override
    protected Class<ReflectViewModel> getViewModelClass() {
        return ReflectViewModel.class;
    }

    @Override
    protected String getTagFragment() {
        return EditResidentProcessReflectFragment.class.getSimpleName();
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;
        LatLng myPos = new LatLng(reflect.getLatitude(), reflect.getLongitude());
        mMap.getUiSettings().setScrollGesturesEnabled(true);
        mMap.getUiSettings().setZoomGesturesEnabled(true);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(myPos, DEFAULT_ZOOM));
        mMap.addMarker(new MarkerOptions().title("Vị trí phản ánh").position(myPos));
    }
}