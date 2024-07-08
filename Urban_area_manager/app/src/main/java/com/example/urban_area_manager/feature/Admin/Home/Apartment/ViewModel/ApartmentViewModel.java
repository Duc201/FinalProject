package com.example.urban_area_manager.feature.Admin.Home.Apartment.ViewModel;

import android.graphics.Bitmap;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.urban_area_manager.R;
import com.example.urban_area_manager.core.base.BaseViewModel;
import com.example.urban_area_manager.feature.Admin.Home.Apartment.Model.Apartment;
import com.example.urban_area_manager.feature.Admin.Home.Apartment.Respository.ApartmentRespository;
import com.example.urban_area_manager.feature.Admin.Home.Apartment.Respository.ApartmentRespositoryImp;
import com.example.urban_area_manager.feature.Admin.Home.Building.Model.Building;
import com.example.urban_area_manager.feature.Admin.Home.Resident.Model.DetailsResident;
import com.example.urban_area_manager.utils.livedata.SingleLiveEvent;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class ApartmentViewModel extends BaseViewModel {
    public ApartmentRespository apartmentRespository = new ApartmentRespositoryImp();
//    private final MutableLiveData<List<Apartment>> _listApartment = new MutableLiveData<>();
//    public LiveData<List<Apartment>> listApartment() {
//        return _listApartment;
//    }

    public SingleLiveEvent<List<Apartment>> _listApartment = new SingleLiveEvent<>();

    private final MutableLiveData<List<String>> _listNameBuilding = new MutableLiveData<>();
    public LiveData<List<String>> listNameBuilding() {
        return _listNameBuilding;
    }
    private final MutableLiveData<List<DetailsResident>> _listResdient = new MutableLiveData<>();
    public LiveData<List<DetailsResident>> listResdient() {
        return _listResdient;
    }

    public SingleLiveEvent<String> _link = new SingleLiveEvent<>();
    public SingleLiveEvent<Boolean> isAddSuccess = new SingleLiveEvent<>();
    public SingleLiveEvent<Boolean> isUpdateSuccess = new SingleLiveEvent<>();
    public SingleLiveEvent<Boolean> deleteResultLiveData = new SingleLiveEvent<>();
    public SingleLiveEvent<String> idBuilding = new SingleLiveEvent<>();


    public void getlistApartment(String idbuilding){
        compositeDisposable.add(
                apartmentRespository.getlistApartment(idbuilding)
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
                                    // No employee found
                                    setErrorStringId(R.string.no_information);
                                    setLoading(false);

                                }
                        )
        );
    }
    public void getlistNameBuilding(){
        compositeDisposable.add(
                apartmentRespository.getlistBuilding()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                listBuilding -> {
                                    _listNameBuilding.setValue(listBuilding);
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
    public void getidBuilding(String nameBuilding){
        compositeDisposable.add(
                apartmentRespository.getIDBuilding(nameBuilding)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                id -> {
                                    idBuilding.setValue(id);
                                    setLoading(false);
                                },
                                throwable -> {
                                    setErrorStringId(R.string.error_Building);
                                    Log.e("Error", throwable.getMessage());
                                    setLoading(false);
                                }
                        )
        );
    }
    public void uploadImage(Bitmap data){
        setLoading(true);
        compositeDisposable.add(
                apartmentRespository.uploadImage(data)
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

    public void addApartment(Apartment apartment){
        compositeDisposable.add(
                apartmentRespository.uploadApartment(apartment)
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

    public void updateApartment(Apartment apartment){
        compositeDisposable.add(
                apartmentRespository.updateBApartment(apartment)
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
    public void deleteImageAndApartment(String uri,Apartment apartment){
        setLoading(true);
        compositeDisposable.add(
                apartmentRespository.deleteImageAndApartment(uri,apartment)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                () -> {
                                    deleteResultLiveData.setValue(true);
                                    setLoading(false);
                                },
                                throwable -> {
                                    deleteResultLiveData.setValue(false);
                                    setLoading(false);
                                }
                        )
        );
    }
    public void getResidentInApartment(String idApartment){
        compositeDisposable.add(
                apartmentRespository.getlistResident(idApartment)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                listResdient -> {
                                    _listResdient.setValue(listResdient);
                                    setLoading(false);
                                },
                                throwable -> {
                                    Log.e("Error", throwable.getMessage());
                                    setLoading(false);
                                }
                        )
        );
    }
}
