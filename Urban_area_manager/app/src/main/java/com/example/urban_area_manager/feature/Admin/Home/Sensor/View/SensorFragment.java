package com.example.urban_area_manager.feature.Admin.Home.Sensor.View;

import static com.example.urban_area_manager.utils.extensions.Extensions.showToastShort;

import androidx.recyclerview.widget.GridLayoutManager;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import com.example.urban_area_manager.R;
import com.example.urban_area_manager.core.base.BaseFragment;
import com.example.urban_area_manager.databinding.DialogDevicePasswordBinding;
import com.example.urban_area_manager.databinding.FragmentSensorBinding;
import com.example.urban_area_manager.feature.Admin.Home.Sensor.Model.Sensor;
import com.example.urban_area_manager.feature.Admin.Home.Sensor.Model.SensorViewModel;
import com.example.urban_area_manager.utils.Constant;
import com.example.urban_area_manager.utils.extensions.Extensions;
import com.example.urban_area_manager.utils.view.DialogView;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;


public class SensorFragment extends BaseFragment<FragmentSensorBinding, SensorViewModel> {

    private AdapterSensor adapterSensor = new AdapterSensor()  ;
    private  GridLayoutManager gridLayoutManager;
    private  Dialog dialog;


    @Override
    public void onCommonViewLoaded() {
        gridLayoutManager  = new GridLayoutManager(getActivity(), 2);
            viewBinding.rcvListsensor.setAdapter(adapterSensor);
            viewBinding.rcvListsensor.setLayoutManager(gridLayoutManager);
            viewModel.getListSensorUser();
    }


    @Override
    public void addViewListener() {
        adapterSensor.setOnItemClickListener(new AdapterSensor.OnItemClickListener() {
            @Override
            public void onItemClick(Sensor sensor) {
                     Bundle bundle = new Bundle();
                    bundle.putSerializable(Constant.GO_TO_ItemSensorFragment,sensor);
                    ItemSensorFragment itemSensorFragment = new ItemSensorFragment();
                    openFragment(itemSensorFragment,R.id.frame_sensor,bundle);
            }
        });

        viewBinding.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });
        viewBinding.btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    DialogDevicePasswordBinding binding = DialogDevicePasswordBinding.inflate(LayoutInflater.from(getActivity()));

                    dialog = new Dialog(getActivity(), R.style.AppThemeNew_DialogTheme);
                    dialog.setCancelable(false);
                    dialog.setContentView(binding.getRoot());

                    binding.btnAccept.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String s = binding.edtSerial.getText().toString().trim();
                            if (s.isEmpty()) {
                                showToastShort(getContext(),getString(R.string.serial_empty));
                            } else {
                                viewModel.getDeviceBySerial(s);
                            }
                        }
                    });

                    binding.btnCancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });

                    dialog.show();
                } catch (Exception e) {
                    // Handle exception
                }
            }
        });
    }

    @Override
    public void addDataObserve() {
        super.addDataObserve();
        viewModel._deviceModel.observe(this, Sensor->{
            dialog.dismiss();
//            DialogView.showDialogAddDetailsSensor(getActivity(), "Thêm chi tiết thiết bị", new DialogView.OnAcceptListener1() {
//                @Override
//                public void onAccept(String location, String name) {
//                    Sensor.setId(UUID.randomUUID().toString());
//                    Sensor.setName(name);
//                    Sensor.setLocation(location);
//                    viewModel.addDeviceUser(Sensor);
//                }
//            });



            AddDeatilsSensorFragment addDeatilsSensorFragment = new AddDeatilsSensorFragment();
            Bundle bundle = new Bundle();
            bundle.putSerializable(Constant.GO_TO_AddDeatilsSensorFragment,Sensor);
            openFragment(addDeatilsSensorFragment,R.id.frame_sensor,bundle);

        });
//        viewModel._isAdd.observe(this,isAdd->{
//            Extensions.showToastShort(getActivity(),"Thêm thành công");
//            viewModel.getListSensorUser();
//        });
        viewModel.listdeviceModelUser().observe(this,listSensor->{
            adapterSensor.clearList();
            adapterSensor.submitList(listSensor);
            List<String> listSerial = new ArrayList<>();
            for(Sensor sensor :listSensor){
                listSerial.add(sensor.getSerial());
            }
            viewModel.getListSensorRealtime(listSerial);
        });
        viewModel.deviceItem().observe(this, sensor -> {
            adapterSensor.notifyItemsChanged(sensor);
        });
    }

    @Override
    protected FragmentSensorBinding getBinding(LayoutInflater inflater) {
        return FragmentSensorBinding.inflate(getLayoutInflater());
    }

    @Override
    protected Class<SensorViewModel> getViewModelClass() {
        return SensorViewModel.class;
    }

    @Override
    protected String getTagFragment() {
        return SensorFragment.class.getSimpleName();
    }

}