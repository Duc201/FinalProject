package com.example.urban_area_manager.feature.Admin.Home.Resident.View;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.urban_area_manager.feature.Admin.Home.Resident.View.Authen.ResidentAuthenFragment;


public class ResidentViewPagerAdapter  extends FragmentStateAdapter {
    public ResidentViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0:
                return new ListResidentFragment();
            case 1:
                return new ResidentAuthenFragment();
            default:
                return new ListResidentFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
