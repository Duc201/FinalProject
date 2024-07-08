package com.example.urban_area_manager.feature.Resident;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.urban_area_manager.feature.Resident.UserInfor.ResidentInforFragment;
import com.example.urban_area_manager.feature.Resident.Home.ResidentHomeFragment;
import com.example.urban_area_manager.feature.Resident.Reflect.ResidentReflectFragment;

public class ResidentMainViewPagerAdapter extends FragmentStateAdapter {


    public ResidentMainViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0:
                return new ResidentHomeFragment();
            case 1:
                return new ResidentReflectFragment();
            case 2:
                return new ResidentInforFragment();
            default:
                return new ResidentHomeFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}