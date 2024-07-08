package com.example.urban_area_manager.feature.Employee.Administrative;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.urban_area_manager.feature.Employee.EmployeeInfor.EmployeeInforFragment;
import com.example.urban_area_manager.feature.Employee.Reflect.Process.AdministrativeReflectFragment;

public class AdministrativeMainViewPagerAdapter extends FragmentStateAdapter  {
    public AdministrativeMainViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0:
                return new AdministrativeHomeFragment();
            case 1:
                return new AdministrativeReflectFragment();
            case 2:
                return new EmployeeInforFragment();
            default:
                return new  AdministrativeHomeFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
