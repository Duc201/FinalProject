package com.example.urban_area_manager.feature.Resident.Reflect;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.urban_area_manager.feature.Admin.Reflect.View.Assigned.AssignedProcessReflectFragment;
import com.example.urban_area_manager.feature.Resident.Reflect.Complete.ResidentCompleteReflectFragment;
import com.example.urban_area_manager.feature.Resident.Reflect.MyReflect.MyResidentReflectFragment;
import com.example.urban_area_manager.feature.Resident.Reflect.Process.ResidentProcesReflectFragment;

public class ResidentReflectViewPagerAdapter extends FragmentStateAdapter  {
    public ResidentReflectViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {

        switch (position){
            case 0:
                return new ResidentProcesReflectFragment();
            case 1:
                return new AssignedProcessReflectFragment();
            case 2:
                return new ResidentCompleteReflectFragment();
            case 3:
                return new MyResidentReflectFragment();
            default:
                return new ResidentProcesReflectFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 4;
    }
}
