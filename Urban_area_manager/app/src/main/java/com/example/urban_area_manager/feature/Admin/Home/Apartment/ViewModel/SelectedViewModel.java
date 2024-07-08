package com.example.urban_area_manager.feature.Admin.Home.Apartment.ViewModel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.urban_area_manager.R;
import com.example.urban_area_manager.core.base.BaseViewModel;
import com.example.urban_area_manager.feature.Admin.Home.Apartment.Model.Apartment;
import com.example.urban_area_manager.feature.Admin.Home.Apartment.Respository.ApartmentRespository;
import com.example.urban_area_manager.feature.Admin.Home.Apartment.Respository.ApartmentRespositoryImp;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class SelectedViewModel extends BaseViewModel {
    private final MutableLiveData<List<Apartment>> _listApartment = new MutableLiveData<>();
    public LiveData<List<Apartment>> listApartment() {
        return _listApartment;
    }
    public ApartmentRespository apartmentRespository = new ApartmentRespositoryImp();
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
}
