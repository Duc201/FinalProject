package com.example.urban_area_manager.feature.Admin.Home.Sensor.Respository;

import com.example.urban_area_manager.feature.Admin.Home.Sensor.Model.Sensor;

import java.util.Date;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;

public interface SensorRespository {
    Single<Sensor> queryDeviceBySerial(String serial);

    Completable addSensorUrban(Sensor sensor);
    Completable updateSensorUrban(Sensor sensor);
    Completable delteSensorUrban(String id);

    Single<List<Sensor>> getListSensorUser();
    Observable<Sensor> getSensorRealtime(String realtime);
    Single<List<Sensor>> getListHistoricSensor(Date date,String serial);

    Observable<Sensor> observeHumidTemperature(List<String> serials);

}
