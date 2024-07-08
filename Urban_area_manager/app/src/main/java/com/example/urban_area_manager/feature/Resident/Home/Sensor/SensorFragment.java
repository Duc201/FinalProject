package com.example.urban_area_manager.feature.Resident.Home.Sensor;

import android.view.LayoutInflater;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.urban_area_manager.core.base.BaseFragment;
import com.example.urban_area_manager.databinding.FragmentSensorBinding;
import com.example.urban_area_manager.feature.Admin.Home.Sensor.Model.Sensor;
import com.example.urban_area_manager.feature.Admin.MainViewModel;

import java.util.ArrayList;


public class SensorFragment extends BaseFragment<FragmentSensorBinding, MainViewModel> {

    private AdapterSensor adapterSensor = new AdapterSensor()  ;
    private LinearLayoutManager linearLayoutManager ;

    private ArrayList<Sensor> mlist = new ArrayList<Sensor>();
    @Override
    public void onCommonViewLoaded() {

        viewBinding.rcvListsensor.setAdapter(adapterSensor);
        linearLayoutManager = new LinearLayoutManager(getContext());
        viewBinding.rcvListsensor.setLayoutManager(linearLayoutManager);
        adapterSensor.submitList(mlist);

    }

    @Override
    public void addViewListener() {
        adapterSensor.setOnItemClickListener(new AdapterSensor.OnItemClickListener() {
            @Override
            public void onItemClick(Sensor sensor) {

            }
        });

        viewBinding.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });
    }

    @Override
    protected FragmentSensorBinding getBinding(LayoutInflater inflater) {
        return FragmentSensorBinding.inflate(getLayoutInflater());
    }

    @Override
    protected Class<MainViewModel> getViewModelClass() {
        return MainViewModel.class;
    }

    @Override
    protected String getTagFragment() {
        return SensorFragment.class.getSimpleName();
    }
}