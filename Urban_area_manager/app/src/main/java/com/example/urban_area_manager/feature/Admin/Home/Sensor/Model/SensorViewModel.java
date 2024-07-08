package com.example.urban_area_manager.feature.Admin.Home.Sensor.Model;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.urban_area_manager.core.base.BaseViewModel;
import com.example.urban_area_manager.feature.Admin.Home.Sensor.Respository.SensorRespository;
import com.example.urban_area_manager.feature.Admin.Home.Sensor.Respository.SensorRespositoryImp;
import com.example.urban_area_manager.utils.livedata.SingleLiveEvent;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class SensorViewModel extends BaseViewModel {
    public SensorRespository sensorRespository = new SensorRespositoryImp();

//    private MutableLiveData<Sensor> _deviceModel = new MutableLiveData<>();
//    public LiveData<Sensor> deviceModel() {
//        return _deviceModel;
//    }
//
    public SingleLiveEvent<Sensor> _deviceModel = new SingleLiveEvent<>();

    private MutableLiveData<Sensor> _deviceItem = new MutableLiveData<>();
    public LiveData<Sensor> deviceItem() {
        return _deviceItem;
    }
//    private MutableLiveData<Sensor> _device = new MutableLiveData<>();
//    public LiveData<Sensor> device() {
//        return _device;
//    }
    private MutableLiveData<Sensor> _deviceItemRT = new MutableLiveData<>();
    public LiveData<Sensor> deviceItemRT() {
        return _deviceItemRT;
    }
    private MutableLiveData<List<Sensor>> _listdeviceModelUser = new MutableLiveData<>();
    public LiveData<List<Sensor>> listdeviceModelUser() {
        return _listdeviceModelUser;
    }

    public SingleLiveEvent<Boolean> _isAdd = new SingleLiveEvent<>();
    private MutableLiveData<List<Sensor>> _listSensorHistory = new MutableLiveData<>();
    public LiveData<List<Sensor>> listSensorHistory() {
        return _listSensorHistory;
    }
    public void getDeviceBySerial(String serial) {
        setLoading(true);
        compositeDisposable.add(
                sensorRespository.queryDeviceBySerial(serial)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                device -> {
                                    _deviceModel.setValue(device);
                                    setLoading(false);
                                },
                                throwable -> {
                                    setErrorString("Không tìm thấy thiết bị");
                                    setLoading(false);
                                }
                        )
        );
    }
    public void getListSensorUser() {
        compositeDisposable.add(
                sensorRespository.getListSensorUser()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                device -> _listdeviceModelUser.setValue(device),
                                throwable -> {
                                    setErrorString("Không tìm thấy thiết bị");
                                    setLoading(false);
                                }
                        )
        );
    }
    public void addDeviceUser(Sensor sensor) {
        compositeDisposable.add(
                sensorRespository.addSensorUrban(sensor)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                () -> _isAdd.setValue(true),
                                throwable -> {
                                    setErrorString("Không tìm thấy thiết bị");
                                    setLoading(false);
                                }
                        )
        );
    }

    public void getListSensorRealtime(List<String> listSerial) {
        compositeDisposable.add(
                sensorRespository. observeHumidTemperature(listSerial)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                device -> _deviceItem.setValue(device),
                                throwable -> {
                                    setErrorString("Không tìm thấy thiết bị");
                                    setLoading(false);
                                }
                        )
        );
    }

    public void getSensorRealtime(String serial) {
        compositeDisposable.add(
                sensorRespository. getSensorRealtime(serial)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                device -> _deviceItemRT.setValue(device),
                                throwable -> {
                                    setErrorString("Không tìm thấy thiết bị");
                                    setLoading(false);
                                }
                        )
        );
    }

    public void getListHistory(Date date, String serial) {
        compositeDisposable.add(
                sensorRespository.getListHistoricSensor(date, serial)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                listdevice -> _listSensorHistory.setValue(listdevice),
                                throwable -> {
                                    setErrorString("Không tìm thấy thiết bị");
                                    _listSensorHistory.setValue(Collections.emptyList());
                                    setLoading(false);
                                }
                        )
        );
    }
}
