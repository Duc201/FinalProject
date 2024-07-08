package com.example.urban_area_manager.feature.Admin.Home.Resident.ViewModel;

import android.util.Log;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.urban_area_manager.R;
import com.example.urban_area_manager.core.base.BaseViewModel;
import com.example.urban_area_manager.feature.Auth.respository.AuthRespository;
import com.example.urban_area_manager.feature.Auth.respository.AuthRespostoryImpl;
import com.example.urban_area_manager.feature.Admin.Home.Apartment.Model.Apartment;
import com.example.urban_area_manager.feature.Admin.Home.Building.Model.Building;
import com.example.urban_area_manager.feature.Admin.Home.Resident.Model.DetailsResident;
import com.example.urban_area_manager.feature.Admin.Home.Resident.Model.Resident;
import com.example.urban_area_manager.feature.Admin.Home.Resident.Respository.ResidentRespository;
import com.example.urban_area_manager.feature.Admin.Home.Resident.Respository.ResidentRespositoryImpl;
import com.example.urban_area_manager.utils.livedata.SingleLiveEvent;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class ResidentViewModel extends BaseViewModel {

    ResidentRespository residentRespository = new ResidentRespositoryImpl();
    private AuthRespository authRepository = new AuthRespostoryImpl();

    private final MutableLiveData<List<Resident>> _listResident = new MutableLiveData<>();
    public SingleLiveEvent<String> idapartment = new SingleLiveEvent<>();
    public SingleLiveEvent<Boolean> isDeleteResidentall = new SingleLiveEvent<>();
    public SingleLiveEvent<Boolean> isUpdateResidentall = new SingleLiveEvent<>();
    private SingleLiveEvent<DetailsResident> detailsResident = new SingleLiveEvent<>();
    public SingleLiveEvent<List<Apartment>> _listApartment = new SingleLiveEvent<>();
    public SingleLiveEvent<List<Building>> _listbuilding = new SingleLiveEvent<>();
    public SingleLiveEvent<Boolean> isUpdateDetailsResident = new SingleLiveEvent<>();

    public LiveData<List<Resident>> listResident() {
        return _listResident;
    }

    public SingleLiveEvent<List<Resident>> _listResidented = new SingleLiveEvent<>();

    private final MutableLiveData<List<DetailsResident>> _DetailsResident = new MutableLiveData<>();
    public LiveData<List<DetailsResident>> DetailsResident() {
        return _DetailsResident;
    }
    private final MutableLiveData<DetailsResident> _DetailResident = new MutableLiveData<>();
    public LiveData<DetailsResident> DetailResident() {
        return _DetailResident;
    }
    public SingleLiveEvent<Boolean> isUpdateStateSuccess = new SingleLiveEvent<>();


    public void getListResidentAuthen(){
        compositeDisposable.add(
                residentRespository.getListResidentAuthen()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                listResident -> {
                                    _listResident.setValue(listResident);
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
    public void getListResidentAuthened(){
        compositeDisposable.add(
                residentRespository.getListResidentAuthened()
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
    public void getListDetailsResident(String idUser, int state){
        compositeDisposable.add(
                residentRespository.getListDetailsResident(idUser,state)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                DetailsResident -> {
                                    _DetailsResident.setValue(DetailsResident);
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
    public void getDetailsResident(String idResident,int state){
        compositeDisposable.add(
                residentRespository.getDetailResident( idResident)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                _detailsResident -> {
                                    detailsResident.setValue(_detailsResident);
                                    setLoading(false);
                                },
                                throwable -> {
                                    setErrorString(throwable.getMessage());
                                    setLoading(false);
                                }

                        )
        );
    }
    public void getListDetailResident(String idUser, int state){
        compositeDisposable.add(
                residentRespository.getListDetailResident(idUser,state)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                DetailResident -> {
                                    _DetailResident.setValue(DetailResident);
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
    public void updateStateResident(String idResident, String idDetailsResident, String email, int state){
        setLoading(true);
        compositeDisposable.add(
                residentRespository.updateStateResident(idResident,idDetailsResident,email,state)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                () -> {
                                    setLoading(false);
                                    isUpdateStateSuccess.setValue(true);
                                },
                                throwable -> {
                                    setErrorString("Xét duyệt thất bại");
                                    setLoading(false);
                                }

                        )
        );
    }
    public void deleteDetailsResident(String idDetails){

    }

    public void getIdApartment(String idUser) {
        setLoading(true);
        compositeDisposable.add(
                residentRespository.getIdApartment(idUser)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                idApartment -> {
                                    idapartment.setValue(idApartment);
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

    public void deleteResidentall(String id) {
        setLoading(true);
        compositeDisposable.add(
                residentRespository.deleteResidentall(id)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                () -> {
                                    isDeleteResidentall.setValue(true);
                                    setLoading(false);
                                },
                                throwable -> {
                                    setErrorString(throwable.getMessage());
                                    setLoading(false);
                                }

                        )
        );
    }
    public void updateResidentall(Resident resident, List<DetailsResident> listdetailsResiden) {
        setLoading(true);
        compositeDisposable.add(
                residentRespository.updateResidentAll(resident,listdetailsResiden)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                () -> {
                                    isUpdateResidentall.setValue(true);
                                    setLoading(false);
                                },
                                throwable -> {
                                    setErrorString(throwable.getMessage());
                                    setLoading(false);
                                }

                        )
        );
    }

    public void getListApartment(String idBuilding) {
        compositeDisposable.add(
                authRepository.getlistApartment(idBuilding)
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

    public void updateDetailsResident(DetailsResident detailsResident) {
        compositeDisposable.add(
                residentRespository.updateDetailsResident(detailsResident)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                () -> {
                                    isUpdateDetailsResident.setValue(true);
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
