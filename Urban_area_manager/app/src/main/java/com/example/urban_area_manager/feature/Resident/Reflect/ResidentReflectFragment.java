package com.example.urban_area_manager.feature.Resident.Reflect;

import androidx.annotation.NonNull;

import android.view.LayoutInflater;
import android.view.View;

import com.example.urban_area_manager.core.base.BaseFragment;
import com.example.urban_area_manager.databinding.FragmentResidentReflectBinding;
import com.example.urban_area_manager.feature.Admin.Reflect.View.AddReflectFragment;
import com.example.urban_area_manager.feature.Resident.ResidentMainViewModel;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;


public class ResidentReflectFragment extends BaseFragment<FragmentResidentReflectBinding, ResidentMainViewModel> {


    @Override
    public void onCommonViewLoaded() {
        ResidentReflectViewPagerAdapter residentReflectViewPagerAdapter = new ResidentReflectViewPagerAdapter(getActivity());
        viewBinding.reflectVp2.setAdapter(residentReflectViewPagerAdapter);
        new TabLayoutMediator(viewBinding.reflectTl, viewBinding.reflectVp2, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                switch (position){
                    case 0:
                        tab.setText("Chưa xử lý");
                        break;
                    case 1:
                        tab.setText("Đã phân công");
                        break;
                    case 2:
                        tab.setText("Đã xử lý");
                        break;
                    case 3:
                        tab.setText("Phản ánh của tôi");
                }
            }
        }).attach();
    }

    @Override
    public void addViewListener() {
        viewBinding.btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                AddReflectFragment addReflectFragment = new AddReflectFragment();
                openActivity(AddReflectFragment.class);
            }
        });
    }

    @Override
    protected FragmentResidentReflectBinding getBinding(LayoutInflater inflater) {

        return FragmentResidentReflectBinding.inflate(getLayoutInflater());
    }

    @Override
    protected Class<ResidentMainViewModel> getViewModelClass() {

        return ResidentMainViewModel.class;
    }

    @Override
    protected String getTagFragment() {
        return ResidentReflectFragment.class.getSimpleName();
    }
}