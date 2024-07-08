package com.example.urban_area_manager.feature.Admin.Reflect.ViewModel;

import android.graphics.Bitmap;
import android.net.Uri;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.urban_area_manager.R;
import com.example.urban_area_manager.core.base.BaseViewModel;
import com.example.urban_area_manager.feature.Auth.viewmodel.ImageWrapper;
import com.example.urban_area_manager.feature.Admin.Home.Apartment.Respository.ApartmentRespository;
import com.example.urban_area_manager.feature.Admin.Home.Apartment.Respository.ApartmentRespositoryImp;
import com.example.urban_area_manager.feature.Admin.Home.Employee.Model.Employee;
import com.example.urban_area_manager.feature.Admin.Home.Employee.Respository.EmployeeRespository;
import com.example.urban_area_manager.feature.Admin.Home.Employee.Respository.EmployeeRespositoryImpl;
import com.example.urban_area_manager.feature.Admin.Reflect.Model.Reflect;
import com.example.urban_area_manager.feature.Admin.Reflect.Respository.ReflectRespository;
import com.example.urban_area_manager.feature.Admin.Reflect.Respository.ReflectRespositoryImp;
import com.example.urban_area_manager.utils.livedata.SingleLiveEvent;

import java.util.Arrays;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class ReflectViewModel extends BaseViewModel {

    private ReflectRespository reflectRespository = new ReflectRespositoryImp();
    private ApartmentRespository apartmentRespository = new ApartmentRespositoryImp();
    public EmployeeRespository employeeRespository = new EmployeeRespositoryImpl();

    private final MutableLiveData<List<Reflect>> _listReflectAssigned = new MutableLiveData<>();
    private String _idReflect;
    private String _result;
    public SingleLiveEvent<Boolean> isUpdateReflectEmployee = new SingleLiveEvent<>();
    public SingleLiveEvent<Boolean> updateReflectResident = new SingleLiveEvent<>();

    public SingleLiveEvent<Boolean> updateEmployeeProcessReflectNo = new SingleLiveEvent<>();

    public SingleLiveEvent<Boolean> updateFeedback = new SingleLiveEvent<>();

    private final MutableLiveData<List<Reflect>> _listEmployeeCompleteReflect = new MutableLiveData<>();
    public LiveData<List<Reflect>> listEmployeeCompleteReflect() {
        return _listEmployeeCompleteReflect;
    }
    public LiveData<Boolean> updateReflectViewModel() {
        return _updateReflectViewModel;
    }

    private final MutableLiveData<Boolean> _updateReflectViewModel = new MutableLiveData<>();

    public LiveData<List<Reflect>> ListMyReflect() {
        return _ListMyReflect;
    }

    private final MutableLiveData<List<Reflect>> _ListMyReflect = new MutableLiveData<>();
    public LiveData<List<Reflect>> listReflectAssigned() {
        return _listReflectAssigned;
    }

    private final MutableLiveData<List<Reflect>> _listReflectProces = new MutableLiveData<>();
    public LiveData<List<Reflect>> listReflectProces() {
        return _listReflectProces;
    }

    private final MutableLiveData<List<Reflect>> _listReflectComplete = new MutableLiveData<>();
    public LiveData<List<Reflect>> listReflectComplete() {
        return _listReflectComplete;
    }


    private final MutableLiveData<List<Reflect>> _listProcesEmployeeReflect = new MutableLiveData<>();
    public LiveData<List<Reflect>> listProcesEmployeeReflect() {
        return _listProcesEmployeeReflect;
    }
    private final MutableLiveData<List<Employee>> _listEmployee = new MutableLiveData<>();
    public LiveData<List<Employee>> listEmployee() {
        return _listEmployee;
    }

    private final MutableLiveData<List<String>> _listNameBuilding = new MutableLiveData<>();
    public LiveData<List<String>> listNameBuilding() {
        return _listNameBuilding;
    }
    public SingleLiveEvent<Boolean> isAddSuccess = new SingleLiveEvent<>();
    public SingleLiveEvent<Boolean> updateProcessReflectNo = new SingleLiveEvent<>();
    public SingleLiveEvent<Boolean> updateProcessReflectYes = new SingleLiveEvent<>();

    public Reflect reflect_not_image;

    public String pathImageRespon1;
    public String pathImageRespon2;


    public void clear(){
        reflect_not_image = null;
    }
    public void getListAssignedReflect(){
        compositeDisposable.add(
                reflectRespository.getListAssignedReflect()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                listReflect->{
                                    _listReflectAssigned.setValue(listReflect);
                                    setLoading(false);
                                },
                                throwable -> {
                                    setErrorString("Không Tồn tại danh sách phản ánh");
                                    setLoading(false);
                                }

                        )
        );
    }
    public void getListAssignedProcess(){
//        setLoading(true);
        compositeDisposable.add(
                reflectRespository.getListProcesReflect()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                listReflect->{
                                    _listReflectProces.setValue(listReflect);
                                    setLoading(false);
                                },
                                throwable -> {
                                    setErrorString("Không Tồn tại danh sách phản ánh");
                                    setLoading(false);
                                }

                        )
        );
    }
    public void getListCompleteReflect(){
        setLoading(true);
        compositeDisposable.add(
                reflectRespository.getListCompleteReflect()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                listReflect->{
                                    _listReflectComplete.setValue(listReflect);
                                    setLoading(false);
                                },
                                throwable -> {
                                    setErrorString("Không Tồn tại danh sách phản ánh");
                                    setLoading(false);
                                }

                        )
        );
    }
    public void getlistEmployeeCompleteReflect(String idHandle){
        setLoading(true);
        compositeDisposable.add(
                reflectRespository.getListEmployeeCompleteReflect(idHandle)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                listReflect->{
                                    _listEmployeeCompleteReflect.setValue(listReflect);
                                    setLoading(false);
                                },
                                throwable -> {
                                    setErrorString("Không Tồn tại danh sách phản ánh");
                                    setLoading(false);
                                }

                        )
        );
    }
    public void updateListImage(Bitmap bitmap1, Bitmap bitmap2) {
        setLoading(true);
        List<ImageWrapper> images = Arrays.asList(
                new ImageWrapper(bitmap1, "1"),
                new ImageWrapper(bitmap2, "2")
        );

        compositeDisposable.add(
                reflectRespository.uploadImages(images)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                pair -> {
                                    String id = pair.first;
                                    Uri uri = pair.second;
                                    if(id.equals("1")){
                                        reflect_not_image.setImage1(uri.toString());
                                    } else if (id.equals("2")) {
                                        reflect_not_image.setImage2(uri.toString());
                                    }
                                    setLoading(false);
                                },
                                throwable -> {
                                    // Xử lý lỗi tại đây
                                    Log.e("ImageUpload", "Error uploading images", throwable);
                                    setLoading(false);

                                },
                                () -> {
                                    // Xử lý hoàn thành tại đây
                                    addReflect(reflect_not_image);
                                }
                        )
        );
    }
    public void updateListImageRespon(Bitmap bitmap1, Bitmap bitmap2) {
        setLoading(true);
        List<ImageWrapper> images = Arrays.asList(
                new ImageWrapper(bitmap1, "1"),
                new ImageWrapper(bitmap2, "2")
        );

        compositeDisposable.add(
                reflectRespository.uploadImages(images)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                pair -> {
                                    String id = pair.first;
                                    Uri uri = pair.second;
                                    if(id.equals("1")){
                                        pathImageRespon1 = uri.toString() ;
                                    } else if (id.equals("2")) {
                                        pathImageRespon2 = uri.toString();
                                    }
                                    setLoading(false);
                                },
                                throwable -> {
                                    // Xử lý lỗi tại đây
                                    Log.e("ImageUpload", "Error uploading images", throwable);
                                    setLoading(false);

                                },
                                () -> {
                                    // Xử lý hoàn thành tại đây
                                    updateReflectEmployee(pathImageRespon1,pathImageRespon2,_result,_idReflect);
                                }
                        )
        );
    }

    private void updateReflectEmployee(String pathImageRespon1, String pathImageRespon2, String result, String idReflect) {
        setLoading(true);
        compositeDisposable.add(
                reflectRespository. updateReflectEmployee(pathImageRespon1,pathImageRespon2, result, idReflect ,2)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                () -> {
                                    isUpdateReflectEmployee.setValue(true);
                                    setLoading(false);
                                    _idReflect = null;
                                    _result = null;
                                },
                                throwable -> {
                                    setErrorString(throwable.getMessage());
                                    setLoading(false);
                                    _idReflect = null;
                                    _result = null;
                                }

                        )
        );
    }

    public void updateEmployeeProcess(String result, String idReflect){
         _result = result;
        _idReflect= idReflect;
    }
    public void addReflect(Reflect reflect){
        compositeDisposable.add(
                reflectRespository.addReflect(reflect)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                () -> {
                                    isAddSuccess.setValue(true);
                                    setLoading(false);
                                },
                                throwable -> {
                                    setErrorStringId(R.string.no_information);
                                    setLoading(false);
                                }
                        )
        );
    }
    public void addReflectNotImage(Reflect reflect){
        reflect_not_image = reflect;
    }
    public void getlistNameBuilding(){
        setLoading(true);
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
    public void getListProcesEmployeeReflect(String idHandler){
        setLoading(true);
        compositeDisposable.add(
                reflectRespository.getListProcesEmployeeReflect(idHandler)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                listEmployee -> {
                                    _listProcesEmployeeReflect.setValue(listEmployee);
                                    setLoading(false);
                                },
                                throwable -> {
                                    setErrorString("Không có phản ánh nào");
                                    Log.e("Error", throwable.getMessage());
                                    setLoading(false);
                                }

                        )
        );
    }
public void getListMyReflect(String email){
    setLoading(true);
    compositeDisposable.add(
            reflectRespository.getListMyReflect(email)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            listEmployee -> {
                                _ListMyReflect.setValue(listEmployee);
                                setLoading(false);
                            },
                            throwable -> {
                                setErrorString("Không có phản ánh nào");
                                Log.e("Error", throwable.getMessage());
                                setLoading(false);
                            }

                    )
    );
}

    public void updateProcessReflectNo(String rejectReason, int state, String id) {
        setLoading(true);
        compositeDisposable.add(
                reflectRespository.updateProcessReflectNo(rejectReason,state,id)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                () -> {
                                    updateProcessReflectNo.setValue(true);
                                    setLoading(false);
                                },
                                throwable -> {
                                    setErrorString(throwable.getMessage());
                                    setLoading(false);
                                }

                        )
        );
    }
    public void updateEmployeeProcessReflectNo(String rejectReason, int state, String id) {
        setLoading(true);
        compositeDisposable.add(
                reflectRespository.updateProcessReflectNo(rejectReason,state,id)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                () -> {
                                    updateEmployeeProcessReflectNo.setValue(true);
                                    setLoading(false);
                                },
                                throwable -> {
                                    setErrorString(throwable.getMessage());
                                    setLoading(false);
                                }

                        )
        );
    }
    public void updateProcessReflectYes(int department, String idEmployee, String nameEmployee, int state, String id) {
        setLoading(true);
        compositeDisposable.add(
                reflectRespository.updateProcessReflectYes(department,idEmployee,nameEmployee,state,id)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                () -> {
                                    updateProcessReflectYes.setValue(true);
                                    _updateReflectViewModel.setValue(true);
                                    setLoading(false);
                                },
                                throwable -> {
                                    setErrorString(throwable.getMessage());
                                    setLoading(false);
                                }

                        )
        );
    }

    public void updateReflectResident(String content , String specificLocation , String ifReflect) {
        setLoading(true);
        compositeDisposable.add(
                reflectRespository.updateReflectResident(content , specificLocation , ifReflect)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                () -> {
                                    updateReflectResident.setValue(true);
                                    setLoading(false);
                                },
                                throwable -> {
                                    setErrorString(throwable.getMessage());
                                    setLoading(false);
                                }

                        )
        );
    }
    public void updateFeedback(String feedback , String idReflect ) {
        setLoading(true);
        compositeDisposable.add(
                reflectRespository.updateFeedback (feedback , idReflect)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                () -> {
                                    updateFeedback.setValue(true);
                                    setLoading(false);
                                },
                                throwable -> {
                                    setLoading(false);
                                }

                        )
        );
    }
}
