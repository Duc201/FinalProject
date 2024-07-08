package com.example.urban_area_manager.feature.Admin.Home.Employee.ViewModel;

import android.graphics.Bitmap;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.urban_area_manager.R;
import com.example.urban_area_manager.core.base.BaseViewModel;
import com.example.urban_area_manager.feature.Admin.Home.Employee.Model.Employee;
import com.example.urban_area_manager.feature.Admin.Home.Employee.Respository.EmployeeRespository;
import com.example.urban_area_manager.feature.Admin.Home.Employee.Respository.EmployeeRespositoryImpl;
import com.example.urban_area_manager.utils.livedata.SingleLiveEvent;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class EmployeeViewModel extends BaseViewModel {

    public EmployeeRespository employeeRespository = new EmployeeRespositoryImpl();
    private final MutableLiveData<List<Employee>> _listEmployee = new MutableLiveData<>();
    public LiveData<List<Employee>> listEmployee() {
        return _listEmployee;
    }

    public SingleLiveEvent<String> _link = new SingleLiveEvent<>();
    public SingleLiveEvent<Boolean> isAddSuccess = new SingleLiveEvent<>();
    public SingleLiveEvent<Boolean> isUpdateSuccess = new SingleLiveEvent<>();
    public SingleLiveEvent<Boolean> deleteResultLiveData = new SingleLiveEvent<>();




    public void getlistEmployee(int department){
        setLoading(true);
        compositeDisposable.add(
                employeeRespository.getlistEmployee(department)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                listEmployee -> {
                                    _listEmployee.setValue(listEmployee);
                                    setLoading(false);
                                },
                                throwable -> {
                                    setErrorString("Nhân viên không tồn tại");
                                    Log.e("Error", throwable.getMessage());
                                    setLoading(false);
                                }

                        )
        );
    }
    public void uploadImage(Bitmap data){
        setLoading(true);
        compositeDisposable.add(
                employeeRespository.uploadImage(data)
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
    public void deleteEmployee(Employee employee){

    }
    public void addEmployee(Employee employee){
        compositeDisposable.add(
                employeeRespository.uploadEmloyee(employee)
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

    public void updateEmployee(Employee employee){
        compositeDisposable.add(
                employeeRespository.updateEmloyee(employee)
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
    public void deleteImageAndEmployee(String uri,Employee employee){
        setLoading(true);
        compositeDisposable.add(
                employeeRespository.deleteImageAndEmployee(uri,employee)
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

//    private void getlinkImage(StorageReference ref) {
//        compositeDisposable.add(
//                employeeRespository.getlinkImage(ref)
//                        .subscribeOn(Schedulers.io())
//                        .observeOn(AndroidSchedulers.mainThread())
//                        .subscribe(
//                                link -> {
//
//                                },
//                                throwable -> {
//                                    setErrorStringId(R.string.error_authentication);
//                                    Log.e("Error", throwable.getMessage());
//                                }
//                        )
//        );
//    }
}
