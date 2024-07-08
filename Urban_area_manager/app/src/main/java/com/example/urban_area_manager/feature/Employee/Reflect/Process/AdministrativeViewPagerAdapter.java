package com.example.urban_area_manager.feature.Employee.Reflect.Process;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.urban_area_manager.feature.Employee.Reflect.Complete.EmployeeCompleteReflectFragment;

public class AdministrativeViewPagerAdapter extends FragmentStateAdapter {
    public AdministrativeViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0:
                return new AdministrativeProcessFragment();
            case 1:
                return new EmployeeCompleteReflectFragment();
            default:
                return new AdministrativeProcessFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 2 ;
    }
}
