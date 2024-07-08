package com.example.urban_area_manager.feature.Admin;


import android.os.Build;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.core.content.ContextCompat;
import androidx.viewpager2.widget.ViewPager2;



import com.example.urban_area_manager.R;
import com.example.urban_area_manager.core.base.BaseActivity;
import com.example.urban_area_manager.databinding.ActivityMainBinding;
import com.example.urban_area_manager.feature.Admin.UserInfor.UserInformationActivity;


public class MainActivity extends BaseActivity<ActivityMainBinding, MainViewModel> {

    @Override
    protected ActivityMainBinding getViewBinding() {
        return ActivityMainBinding.inflate(getLayoutInflater());
    }

    @Override
    protected Class<MainViewModel> getViewModelClass() {
        return MainViewModel.class;
    }

    @Override
    public void onCommonViewLoaded() {
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.light_background));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }

        MainViewPagerAdapter mainViewPagerAdapter = new MainViewPagerAdapter(this);
        binding.mainVp2.setAdapter(mainViewPagerAdapter);
        binding.mainVp2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
            }

            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                switch (position){
                    case 1:
                        binding.toolbarTitle.setText("Phản ánh");
                        binding.bottomNavigation.getMenu().findItem(R.id.menu_reflect).setChecked(true);
                        break;
                    case 2:
                        binding.toolbarTitle.setText("Tài Khoản");
                        binding.bottomNavigation.getMenu().findItem(R.id.menu_account).setChecked(true);
                        break;
                    case 0:
                    default:
                        binding.toolbarTitle.setText("Trang Chủ");
                        binding.bottomNavigation.getMenu().findItem(R.id.menu_home).setChecked(true);
                        break;
                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {
                super.onPageScrollStateChanged(state);
            }
        });

        binding.bottomNavigation.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.menu_home) {
                binding.toolbarTitle.setText("Trang chủ");
                binding.mainVp2.setCurrentItem(0);
            } else if (itemId == R.id.menu_reflect) {
                binding.toolbarTitle.setText("Phản ánh");
                binding.mainVp2.setCurrentItem(1);
            } else if (itemId == R.id.menu_account) {
                binding.toolbarTitle.setText("Tài Khoản");
                binding.mainVp2.setCurrentItem(2);
            }
            return true;
        });
    }

    @Override
    public void addViewListener() {
        binding.btnDrawer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity(UserInformationActivity.class);
            }
        });
    }

}