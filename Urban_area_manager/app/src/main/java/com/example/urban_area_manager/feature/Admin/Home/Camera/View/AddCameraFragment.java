package com.example.urban_area_manager.feature.Admin.Home.Camera.View;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.example.urban_area_manager.R;
import com.example.urban_area_manager.core.base.BaseFragment;
import com.example.urban_area_manager.databinding.FragmentAddApartmentBinding;
import com.example.urban_area_manager.databinding.FragmentAddCameraBinding;
import com.example.urban_area_manager.feature.Admin.Home.Camera.Model.Camera;
import com.example.urban_area_manager.feature.Admin.Home.Camera.ViewModel.CameraViewModel;
import com.example.urban_area_manager.utils.extensions.Extensions;

import java.util.UUID;


public class AddCameraFragment extends BaseFragment<FragmentAddCameraBinding, CameraViewModel> {
    private boolean state;
    private ArrayAdapter<CharSequence> spinnerStateAdapter;

    @Override
    public void onCommonViewLoaded() {
        setSpinnerState();
    }

    @Override
    public void addDataObserve() {
        super.addDataObserve();
        viewModel.isAddSucess.observe(this,ischeck->{
            if(ischeck){
                Extensions.showToastShort(getActivity(),"Thêm thành công");
                getParentFragmentManager().popBackStack();
            }
        });
    }

    @Override
    public void addViewListener() {
        viewBinding.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requireActivity().getSupportFragmentManager().popBackStack();
            }
        });
        viewBinding.btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Camera camera = new Camera();
                camera.setId(UUID.randomUUID().toString());
                camera.setName(viewBinding.edtProducerName.getText().toString());
                camera.setSerial(viewBinding.edtCameraSerial.getText().toString());
                camera.setArea(viewBinding.edtCameraArea.getText().toString());
                camera.setLink(viewBinding.edtCameraRTSP.getText().toString().trim());
                camera.setPublic(state);
                viewModel.addCamera(camera);
            }
        });
    }
    private void setSpinnerState() {
        spinnerStateAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.spinner_camera_state, R.layout.spinner_item);
        spinnerStateAdapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        viewBinding.spinSate.setAdapter(spinnerStateAdapter);
        viewBinding.spinSate.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position == 0){
                    state = true;
                }
                else
                    state = false;
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }


    @Override
    protected FragmentAddCameraBinding getBinding(LayoutInflater inflater) {
        return FragmentAddCameraBinding.inflate(getLayoutInflater());
    }

    @Override
    protected Class<CameraViewModel> getViewModelClass() {
        return CameraViewModel.class;
    }

    @Override
    protected String getTagFragment() {
        return AddCameraFragment.class.getSimpleName();
    }
}