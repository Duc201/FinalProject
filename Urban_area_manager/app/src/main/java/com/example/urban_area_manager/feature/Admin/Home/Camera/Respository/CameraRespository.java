package com.example.urban_area_manager.feature.Admin.Home.Camera.Respository;

import com.example.urban_area_manager.feature.Admin.Home.Camera.Model.Camera;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;

public interface CameraRespository {

    Completable addCamera(Camera camera);
    Completable deleteCamera(Camera camera);
    Completable editCamera(Camera camera);

    Single<List<Camera>> getListCamera();
    Single<List<Camera>> getListCameraPulic();

}
