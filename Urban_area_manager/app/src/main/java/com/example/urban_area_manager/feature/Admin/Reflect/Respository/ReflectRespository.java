package com.example.urban_area_manager.feature.Admin.Reflect.Respository;

import android.net.Uri;
import android.util.Pair;

import com.example.urban_area_manager.feature.Auth.viewmodel.ImageWrapper;
import com.example.urban_area_manager.feature.Admin.Reflect.Model.Reflect;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;

public interface ReflectRespository {
    Single<List<Reflect>> getListAssignedReflect();
    Single<List<Reflect>> getListProcesReflect();
    Single<List<Reflect>> getListProcesEmployeeReflect(String idHandler);

    Single<List<Reflect>> getListCompleteReflect();
    Single<List<Reflect>> getListEmployeeCompleteReflect(String idHandle);

    Single<List<Reflect>> getListMyReflect(String email);

    Observable<Pair<String, Uri>> uploadImages(List<ImageWrapper> images);


    Completable addReflect(Reflect reflect);
    Completable updateProcessReflectNo(String rejectReason, int state, String idReflect);
    Completable updateProcessReflectYes(int department, String idEmployee, String nameEmployee, int state, String idReflect);

    Completable updateReflectEmployee(String pathImageRespon1, String pathImageRespon2, String result, String idReflect , int state);

    Completable updateReflectResident(String content , String specificLocation , String ifReflect);

    Completable updateFeedback (String feedback , String idReflect);


}
