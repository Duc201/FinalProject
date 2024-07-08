package com.example.urban_area_manager.feature.Admin.Home.Camera.ViewModel;

import android.util.Log;

import com.example.urban_area_manager.core.base.BaseViewModel;
import com.example.urban_area_manager.feature.Admin.Home.Camera.Model.Camera;
import com.example.urban_area_manager.feature.Admin.Home.Camera.Respository.CameraRespository;
import com.example.urban_area_manager.feature.Admin.Home.Camera.Respository.CameraRespositoryImp;
import com.example.urban_area_manager.utils.livedata.SingleLiveEvent;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class CameraViewModel extends BaseViewModel {
    private CameraRespository cameraRespository = new CameraRespositoryImp();
    public SingleLiveEvent<List<Camera>> _listCamera = new SingleLiveEvent<>();
    public SingleLiveEvent<List<Camera>> _listCameraPublic = new SingleLiveEvent<>();
    public SingleLiveEvent<Boolean> isAddSucess = new SingleLiveEvent<>();
    public SingleLiveEvent<Boolean> isEditSucess = new SingleLiveEvent<>();
    public SingleLiveEvent<Boolean> isDeleteSucess = new SingleLiveEvent<>();

    public void getListCamera(){
        compositeDisposable.add(
                cameraRespository.getListCamera()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                listCamera -> {
                                    _listCamera.setValue(listCamera);
                                    setLoading(false);
                                },
                                throwable -> {
                                    setErrorString("Không có camera");
                                    Log.e("Error", throwable.getMessage());
                                    setLoading(false);
                                }
                        )
        );
    }
    public void getListCameraPublic(){
        compositeDisposable.add(
                cameraRespository.getListCameraPulic()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                listCamera -> {
                                    _listCameraPublic.setValue(listCamera);
                                    setLoading(false);
                                },
                                throwable -> {
                                    setErrorString("Không có camera");
                                    Log.e("Error", throwable.getMessage());
                                    setLoading(false);
                                }
                        )
        );
    }
    public void addCamera(Camera camera){
        compositeDisposable.add(
                cameraRespository.addCamera(camera)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                () -> {
                                    isAddSucess.setValue(true);
                                    setLoading(false);
                                },
                                throwable -> {
                                    setErrorString("Thêm camera thất bại!");
                                    Log.e("Error", throwable.getMessage());
                                    setLoading(false);
                                }
                        )
        );
    }
    public void editCamera(Camera camera){
        compositeDisposable.add(
                cameraRespository.editCamera(camera)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                () -> {
                                    isEditSucess.setValue(true);
                                    setLoading(false);
                                },
                                throwable -> {
                                    setErrorString("Cập nhật thất bại !");
                                    Log.e("Error", throwable.getMessage());
                                    setLoading(false);
                                }
                        )
        );
    }
    public void deleteCamera(Camera camera){
        compositeDisposable.add(
                cameraRespository.deleteCamera(camera)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                () -> {
                                    isDeleteSucess.setValue(true);
                                    setLoading(false);
                                },
                                throwable -> {
                                    setErrorString("Xóa thất bại");
                                    Log.e("Error", throwable.getMessage());
                                    setLoading(false);
                                }
                        )
        );
    }
}
