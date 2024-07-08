package com.example.urban_area_manager.feature.Employee.Repair;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.urban_area_manager.feature.Employee.Reflect.Process.AdministrativeReflectFragment;
import com.example.urban_area_manager.feature.Admin.UserInfor.UserFragment;


public class RepairMainViewPagerAdapter extends FragmentStateAdapter {
    public RepairMainViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0:
                return new RepairHomeFragment();
            case 1:
                return new AdministrativeReflectFragment();
            case 2:
                return new UserFragment();
            default:
                return new RepairHomeFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
