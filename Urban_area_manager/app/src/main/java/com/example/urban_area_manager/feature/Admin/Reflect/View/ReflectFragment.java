package com.example.urban_area_manager.feature.Admin.Reflect.View;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.urban_area_manager.core.base.BaseFragment;
import com.example.urban_area_manager.databinding.FragmentReflectBinding;
import com.example.urban_area_manager.feature.Admin.Reflect.ViewModel.ReflectViewModel;
import com.example.urban_area_manager.feature.Admin.Reflect.adapter.ReflectViewPagerAdapter;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class ReflectFragment extends BaseFragment<FragmentReflectBinding, ReflectViewModel>  {
    private  ReflectViewPagerAdapter reflectViewPagerAdapter;

    @Override
    public void onCommonViewLoaded() {
         reflectViewPagerAdapter = new ReflectViewPagerAdapter(getActivity());
        viewBinding.reflectVp2.setAdapter(reflectViewPagerAdapter);
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
                }
            }
        }).attach();


    }

    @Override
    public void addViewListener() {
            viewBinding.btnAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    AddReflectFragment addReflectFragment = new AddReflectFragment();
//                    openFragment(addReflectFragment,R.id.framelayout);
                    openActivity(AddReflectFragment.class);
                }
            });
    }

    @Override
    protected FragmentReflectBinding getBinding(LayoutInflater inflater) {
        return FragmentReflectBinding.inflate(getLayoutInflater());
    }

    @Override
    protected Class<ReflectViewModel> getViewModelClass() {
        return ReflectViewModel.class;
    }

    @Override
    protected String getTagFragment() {
        return ReflectFragment.class.getSimpleName();
    }

    @Override
    public void addDataObserve() {
        super.addDataObserve();

    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        Log.d(TAG, "onAttach4: ");
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate4: ");
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d(TAG, "onActivityCreated4: ");
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.d(TAG,"onViewCreated4");
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "onStart4: ");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume4: ");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "onPause4: ");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG, "onStop4: ");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d(TAG, "onDestroyView4: ");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy4: ");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.d(TAG, "onDetach4: ");
    }

}
