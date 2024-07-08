package com.example.urban_area_manager.feature.Admin.Home.Building.ViewModel;

import android.graphics.Bitmap;
import android.util.Log;

import com.example.urban_area_manager.R;
import com.example.urban_area_manager.core.base.BaseViewModel;
import com.example.urban_area_manager.feature.Admin.Home.Building.Model.Building;
import com.example.urban_area_manager.feature.Admin.Home.Building.Respository.BuildingRespository;
import com.example.urban_area_manager.feature.Admin.Home.Building.Respository.BuildingRespositoryImp;
import com.example.urban_area_manager.utils.livedata.SingleLiveEvent;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class BuildingViewModel extends BaseViewModel {
    public BuildingRespository buildingRespository = new BuildingRespositoryImp();
//    private final MutableLiveData<List<Building>> _listBuilding = new MutableLiveData<>();
//    public LiveData<List<Building>> listBuilding() {
//        return _listBuilding;
//    }

    public SingleLiveEvent<List<Building>> _listBuilding = new SingleLiveEvent<>();

    public SingleLiveEvent<String> _link = new SingleLiveEvent<>();
    public SingleLiveEvent<Boolean> isAddSuccess = new SingleLiveEvent<>();
    public SingleLiveEvent<Boolean> isUpdateSuccess = new SingleLiveEvent<>();
    public SingleLiveEvent<Boolean> deleteResultLiveData = new SingleLiveEvent<>();





    public void getlistBuilding(){
//        setLoading(true);
        compositeDisposable.add(
                buildingRespository.getlistBuilding()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                listBuilding -> {
                                    _listBuilding.setValue(listBuilding);
                                    setLoading(false);
                                },
                                throwable -> {
                                    setErrorStringId(R.string.error_Building);
                                    Log.e("Error", throwable.getMessage());
                                    setLoading(false);
                                },
                                () -> {
                                    // No employee found
                                    setErrorStringId(R.string.no_information);
                                    setLoading(false);

                                }
                        )
        );
    }
    public void uploadImage(Bitmap data){
        setLoading(true);
        compositeDisposable.add(
                buildingRespository.uploadImage(data)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                uri -> {
                                    String link = uri.toString();
                                    _link.setValue(link);

                                },
                                throwable -> {
                                    String link = "null";
                                    _link.setValue(link);
                                }
                        )
        );
    }

    public void addBuilding(Building building){
        compositeDisposable.add(
                buildingRespository.uploadBuilding(building)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                () -> {
                                    isAddSuccess.setValue(true);
                                    setErrorString("Thêm thành công");
                                    setLoading(false);
                                },
                                throwable -> {
                                    setErrorStringId(R.string.error_upload_employee);
                                }

                        )
        );
    }

    public void updateBuilding(Building building){
        compositeDisposable.add(
                buildingRespository.updateBuilding(building)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                () -> {
                                    isUpdateSuccess.setValue(true);
                                    setErrorString("Cập nhật thành công");
                                    setLoading(false);
                                },
                                throwable -> {
                                    setErrorStringId(R.string.error_upload_employee);
                                }

                        )
        );
    }
    public void deleteImageAndBuilding(String uri,Building building){
        setLoading(true);
        compositeDisposable.add(
                buildingRespository.deleteImageAndBuilding(uri,building)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                result -> {
                                    deleteResultLiveData.setValue(result);
                                    setLoading(false);
                                },
                                throwable -> {
                                    deleteResultLiveData.setValue(false);
                                    setLoading(false);
                                }
                        )
        );
    }


}
