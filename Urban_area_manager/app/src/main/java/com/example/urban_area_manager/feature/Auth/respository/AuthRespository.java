package com.example.urban_area_manager.feature.Auth.respository;

import android.net.Uri;
import android.util.Pair;

import com.example.urban_area_manager.feature.Auth.viewmodel.ImageWrapper;
import com.example.urban_area_manager.feature.Admin.Home.Apartment.Model.Apartment;
import com.example.urban_area_manager.feature.Admin.Home.Building.Model.Building;
import com.example.urban_area_manager.feature.Admin.Home.Employee.Model.Employee;
import com.example.urban_area_manager.feature.Admin.Home.Resident.Model.DetailsResident;
import com.example.urban_area_manager.feature.Admin.Home.Resident.Model.Resident;

import java.util.Date;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Maybe;
import io.reactivex.Observable;
import io.reactivex.Single;

public interface AuthRespository {
    Maybe<Employee> login(String email, String password);

    Maybe<Employee> loginEmployee(String email, String password);
    Single<Boolean> registerAccount(String email , String pass, String repass);

    Single<Boolean> loginResident(String email, String password);
    Single<Resident> checkAccontResident(String email);

    Single<List<Building>> getBuilding();
    Maybe<List<Apartment>> getlistApartment(String idApartment);
//    Single<Uri> uploadImage(Bitmap bitmap);
//    Observable<Uri> uploadImages(Bitmap bitmap1,Bitmap bitmap2,Bitmap bitmap3 );
    Observable<Pair<String, Uri>> uploadImages(List<ImageWrapper> images);
    Completable addResidentall(Resident resident, DetailsResident detailsResident);

    Completable forgotEmail(String email);

    Completable updatePhone(String phone, String id);
    Completable updatePhoneEmployee(String phone, String id);
    Completable updatePhoneResident(String phone, String id);

    Completable updateSex(int sex,String id);
    Completable updateSexEmployee(int sex,String id);
    Completable updateSexResident(int sex,String id);

    Completable updateName(String name, String id);
    Completable updateNameEmployee(String name, String id);
    Completable updateNameResident(String name, String id);

    Completable updateBirth(Date date, String id);
    Completable updateBirthEmployee(Date date, String id);
    Completable updateBirthResident(Date date, String id);


    Completable updatePassResident(String pass);
    Completable updatePassEmployee(String pass, String idUser);
    Completable updatePassAdmin(String pass, String idUser);

    Single<Employee> getInforAdmin(String id);
    Single<Employee> getInforEmployee(String id);
    Single<Resident> getInforResident(String id);
}
