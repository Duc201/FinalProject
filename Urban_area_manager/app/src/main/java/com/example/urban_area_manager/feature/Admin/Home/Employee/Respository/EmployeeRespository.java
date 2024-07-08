package com.example.urban_area_manager.feature.Admin.Home.Employee.Respository;

import android.graphics.Bitmap;
import android.net.Uri;

import com.example.urban_area_manager.feature.Admin.Home.Employee.Model.Employee;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;

public interface EmployeeRespository {
    Single<List<Employee>> getlistEmployee(int department);
    Single<Uri> uploadImage(Bitmap data);
    Single<Boolean> deleteImage(String uri);
    Single<Boolean> deleteEmployee(Employee employee);

    Single<Boolean> deleteImageAndEmployee(String uri,Employee employee);

    Completable uploadEmloyee(Employee employee);
    Completable updateEmloyee(Employee employee);


//    Single<StorageReference> getlinkImage(StorageReference ref);
}
