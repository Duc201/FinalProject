package com.example.urban_area_manager.feature.Admin.Reflect.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.urban_area_manager.feature.Admin.Reflect.View.Assigned.AssignedProcessReflectFragment;
import com.example.urban_area_manager.feature.Admin.Reflect.View.Complete.CompleteReflectFragment;
import com.example.urban_area_manager.feature.Admin.Reflect.View.Process.ProcessReflectFragment;


public class ReflectViewPagerAdapter extends FragmentStateAdapter {


        public ReflectViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
            super(fragmentActivity);
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            switch (position){
                case 0:
                    return new ProcessReflectFragment();
                case 1:
                    return new AssignedProcessReflectFragment();
                case 2:
                    return new CompleteReflectFragment();
                default:
                    return new ProcessReflectFragment();
            }
        }

        @Override
        public int getItemCount() {
            return 3;
        }

    }


