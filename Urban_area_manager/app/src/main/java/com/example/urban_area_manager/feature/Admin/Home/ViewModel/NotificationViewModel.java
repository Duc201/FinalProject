package com.example.urban_area_manager.feature.Admin.Home.ViewModel;

import android.graphics.Bitmap;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.urban_area_manager.R;
import com.example.urban_area_manager.core.base.BaseViewModel;
import com.example.urban_area_manager.feature.Auth.respository.AuthRespository;
import com.example.urban_area_manager.feature.Auth.respository.AuthRespostoryImpl;
import com.example.urban_area_manager.feature.Admin.Home.Apartment.Model.Apartment;
import com.example.urban_area_manager.feature.Admin.Home.Building.Model.Building;
import com.example.urban_area_manager.feature.Admin.Home.Building.Respository.BuildingRespository;
import com.example.urban_area_manager.feature.Admin.Home.Building.Respository.BuildingRespositoryImp;
import com.example.urban_area_manager.feature.Admin.Home.Notification.Model.Notification;
import com.example.urban_area_manager.feature.Admin.Home.Notification.Respository.NotificationRespository;
import com.example.urban_area_manager.feature.Admin.Home.Notification.Respository.NotificationRespositoryImp;
import com.example.urban_area_manager.feature.Admin.Home.Resident.Model.DetailsResident;
import com.example.urban_area_manager.feature.Admin.Home.Resident.Respository.ResidentRespository;
import com.example.urban_area_manager.feature.Admin.Home.Resident.Respository.ResidentRespositoryImpl;
import com.example.urban_area_manager.utils.livedata.SingleLiveEvent;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class NotificationViewModel extends BaseViewModel {
    private AuthRespository authRepository = new AuthRespostoryImpl();
    public BuildingRespository buildingRespository = new BuildingRespositoryImp();
    public ResidentRespository residentRespository = new ResidentRespositoryImpl();
    public NotificationRespository notificationRespository = new NotificationRespositoryImp();

    public SingleLiveEvent<String> _link = new SingleLiveEvent<>();
    public SingleLiveEvent<List<DetailsResident>> ListDetailsAll = new SingleLiveEvent<>();
    public SingleLiveEvent<Boolean> isAddListNoti = new SingleLiveEvent<>();
    public SingleLiveEvent<Boolean> isAddNoti = new SingleLiveEvent<>();
    public SingleLiveEvent<List<Notification>> listNotification = new SingleLiveEvent<>();
    public SingleLiveEvent<Boolean> deleteNotification = new SingleLiveEvent<>();

    public LiveData<List<Building>> listbuilding() {
        return _listbuilding;
    }

    private final MutableLiveData<List<Building>> _listbuilding = new MutableLiveData<>();

    private final MutableLiveData<List<Apartment>> _listApartment = new MutableLiveData<>();
    public LiveData<List<Apartment>> listApartment() {
        return _listApartment;
    }

    private final MutableLiveData<List<DetailsResident>> _listResidented = new MutableLiveData<>();
    public LiveData<List<DetailsResident>> listResidented() {
        return _listResidented;
    }
    public SingleLiveEvent<List<DetailsResident>> ListDetails = new SingleLiveEvent<>();



    public void uploadImage(Bitmap bitmapimage) {
        setLoading(true);
        compositeDisposable.add(
                buildingRespository.uploadImage(bitmapimage)
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
    public void getlistApartment(String idbuilding){
        compositeDisposable.add(
                authRepository.getlistApartment(idbuilding)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                listApartment -> {
                                    _listApartment.setValue(listApartment);
                                    setLoading(false);
                                },
                                throwable -> {
                                    setErrorStringId(R.string.error_Building);
                                    Log.e("Error", throwable.getMessage());
                                    setLoading(false);
                                },
                                () -> {
                                    setErrorStringId(R.string.no_information);
                                    setLoading(false);
                                }
                        )
        );
    }
    public void getlistBuilding(){
        compositeDisposable.add(
                authRepository.getBuilding()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                buildingList -> {
                                    _listbuilding.setValue(buildingList);
                                },
                                throwable -> {
                                    setErrorString("Không tồn tại tòa nhà");
                                }
                        )
        );
    }
    public void getListDetailResidentApartment(String idApartment){
        compositeDisposable.add(
                residentRespository.getListDetailResidentApartment(idApartment)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                listResident -> {
                                    _listResidented.setValue(listResident);
                                    setLoading(false);
                                },
                                throwable -> {
                                    setErrorString("Không có cư dân cần xác thực");
                                    Log.e("Error", throwable.getMessage());
                                    setLoading(false);
                                }

                        )
        );
    }
    public void getListDetailsResidenInBuilding(String idBuilding){
        compositeDisposable.add(
                residentRespository.getListDetailsResidenInBuilding(idBuilding)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                _listDetails -> {
                                    ListDetails.setValue(_listDetails);
                                    setLoading(false);
                                },
                                throwable -> {
                                    setErrorString(throwable.getMessage());
                                    setLoading(false);
                                }

                        )
        );
    }

    public void getListDetailsResiden(){
        compositeDisposable.add(
                residentRespository.getListDetailsResiden()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                _listDetails -> {
                                    ListDetailsAll.setValue(_listDetails);
                                    setLoading(false);
                                },
                                throwable -> {
                                    setErrorString(throwable.getMessage());
                                    setLoading(false);
                                }

                        )
        );
    }

    public void addListNotifi(List<Notification> notificationList) {
        compositeDisposable.add(
                notificationRespository.addListNotification(notificationList)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                ()-> {
                                    isAddListNoti.setValue(true);
                                    setLoading(false);
                                },
                                throwable -> {
                                    setErrorString(throwable.getMessage());
                                    setLoading(false);
                                }

                        )
        );
    }

    public void addNotifi(Notification notification) {
        compositeDisposable.add(
                notificationRespository.addNotification(notification)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                ()-> {
                                    isAddNoti.setValue(true);
                                    setLoading(false);
                                },
                                throwable -> {
                                    setErrorString(throwable.getMessage());
                                    setLoading(false);
                                }

                        )
        );
    }

    public void getAllNotification() {
        compositeDisposable.add(
                notificationRespository.getAllNotification()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                _listNotification-> {
                                    listNotification.setValue(_listNotification);
                                    setLoading(false);
                                },
                                throwable -> {
                                    setErrorString(throwable.getMessage());
                                    setLoading(false);
                                }

                        )
        );
    }

    public void deleteNotification(Notification notification) {
        compositeDisposable.add(
                notificationRespository.deleteNotification(notification)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                ()-> {
                                    deleteNotification.setValue(true);
                                    setLoading(false);
                                },
                                throwable -> {

                                    setLoading(false);
                                }

                        )
        );
    }



    public void getAllNotificationEmploy(int i) {
        compositeDisposable.add(
                notificationRespository.getAllNotificationEmploy(i)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                _listNotification-> {
                                    listNotification.setValue(_listNotification);
                                    setLoading(false);
                                },
                                throwable -> {
                                    setErrorString(throwable.getMessage());
                                    setLoading(false);
                                }

                        )
        );
    }

    public void getAllNotificationEmploy1(int i, int i1) {
        compositeDisposable.add(
                notificationRespository.getAllNotificationEmploy1(i,i1)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                _listNotification-> {
                                    listNotification.setValue(_listNotification);
                                    setLoading(false);
                                },
                                throwable -> {
                                    setErrorString(throwable.getMessage());
                                    setLoading(false);
                                }

                        )
        );
    }

    public void getAllNotificationResident(String idUser) {
        compositeDisposable.add(
                notificationRespository.getAllNotificationResident(idUser)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                _listNotification-> {
                                    listNotification.setValue(_listNotification);
                                    setLoading(false);
                                },
                                throwable -> {
                                    setErrorString(throwable.getMessage());
                                    setLoading(false);
                                }

                        )
        );
    }
}
