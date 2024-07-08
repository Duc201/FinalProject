package com.example.urban_area_manager.feature.Employee.Reflect.Process;

import android.view.LayoutInflater;

import androidx.annotation.NonNull;

import com.example.urban_area_manager.core.base.BaseFragment;
import com.example.urban_area_manager.databinding.FragmentAdministrativeReflectBinding;
import com.example.urban_area_manager.feature.Admin.Reflect.ViewModel.ReflectViewModel;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;


public class AdministrativeReflectFragment extends BaseFragment<FragmentAdministrativeReflectBinding, ReflectViewModel> {


    @Override
    public void onCommonViewLoaded() {
        AdministrativeViewPagerAdapter administrativeViewPagerAdapter = new AdministrativeViewPagerAdapter(getActivity());
        viewBinding.reflectVp2.setAdapter(administrativeViewPagerAdapter);
        new TabLayoutMediator(viewBinding.reflectTl, viewBinding.reflectVp2, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                switch (position){
                    case 0:
                        tab.setText("Chưa Xử lý");
                        break;
                    case 1:
                        tab.setText("Hoàn Thành");
                        break;
                }
            }
        }).attach();
    }

    @Override
    public void addViewListener() {

    }

    @Override
    protected FragmentAdministrativeReflectBinding getBinding(LayoutInflater inflater) {
        return FragmentAdministrativeReflectBinding.inflate(getLayoutInflater());
    }

    @Override
    protected Class<ReflectViewModel> getViewModelClass() {
        return ReflectViewModel.class;
    }

    @Override
    protected String getTagFragment() {
        return AdministrativeReflectFragment.class.getSimpleName();
    }
}