package com.example.urban_area_manager.feature.Auth.viewmodel;

import android.graphics.Bitmap;
import android.net.Uri;
import android.util.Log;
import android.util.Patterns;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.urban_area_manager.R;
import com.example.urban_area_manager.core.base.BaseViewModel;
import com.example.urban_area_manager.feature.Auth.respository.AuthRespository;
import com.example.urban_area_manager.feature.Auth.respository.AuthRespostoryImpl;
import com.example.urban_area_manager.feature.Admin.Home.Apartment.Model.Apartment;
import com.example.urban_area_manager.feature.Admin.Home.Building.Model.Building;
import com.example.urban_area_manager.feature.Admin.Home.Employee.Model.Employee;
import com.example.urban_area_manager.feature.Admin.Home.Resident.Model.DetailsResident;
import com.example.urban_area_manager.feature.Admin.Home.Resident.Model.Resident;
import com.example.urban_area_manager.utils.livedata.SingleLiveEvent;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class AuthViewModel extends BaseViewModel {
    private AuthRespository authRepository = new AuthRespostoryImpl();
    private final MutableLiveData<Employee> _userResponse = new MutableLiveData<>();
    public SingleLiveEvent<Employee> adminInfor = new SingleLiveEvent<>();
    public SingleLiveEvent<Boolean> isUpdatePhone = new SingleLiveEvent<>();
    public SingleLiveEvent<Boolean> isUpdateBirth = new SingleLiveEvent<>();
    public SingleLiveEvent<Boolean> isUpdatePass = new SingleLiveEvent<>();
    public SingleLiveEvent<Boolean> isUpdateName = new SingleLiveEvent<>();
    public SingleLiveEvent<Resident> residentInfor = new SingleLiveEvent<>();

    public LiveData<Employee> userResponse() {
        return _userResponse;
    }

    private final MutableLiveData<Employee> _employeeResponse = new MutableLiveData<>();

    public LiveData<List<Building>> listbuilding() {
        return _listbuilding;
    }

    private final MutableLiveData<List<Building>> _listbuilding = new MutableLiveData<>();
    public LiveData<Employee> employeeResponse() {
        return _employeeResponse;
    }

    private final MutableLiveData<List<Apartment>> _listApartment = new MutableLiveData<>();
    public LiveData<List<Apartment>> listApartment() {
        return _listApartment;
    }

    public SingleLiveEvent<Boolean> isRegister = new SingleLiveEvent<>();
    public SingleLiveEvent<Boolean> isLogin = new SingleLiveEvent<>();

    public SingleLiveEvent<Resident> ischeckAccontResident = new SingleLiveEvent<>();
    public SingleLiveEvent<Boolean> isAddSuccess = new SingleLiveEvent<>();

    public SingleLiveEvent<Boolean> isForgotpass = new SingleLiveEvent<>();

    public SingleLiveEvent<Boolean> isUpdateSex = new SingleLiveEvent<>();


    public Resident resident_not_image;
    public DetailsResident detailsResident_not_image;


    public void clear(){
        resident_not_image = null;
        detailsResident_not_image = null;
    }
    public Boolean checkValidPassword(String password) {
        if (password.isEmpty()) {
            return false;
        }
        if (password.length() < 8) {
            return false;
        } else {
            if (password.matches(".*[a-zA-Z]+.*") && password.matches(".*\\d+.*")) {
                return true;
            } else {
                return false;
            }
        }
    }

    public Boolean checkValidEmail( String email) {
        if (email.isEmpty()) {
            return false;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            return false;
        }
        return true;
    }

    public void registerAccount(String email , String pass, String repass){
        compositeDisposable.add(
                authRepository.registerAccount(email, pass,repass)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                success -> {
                                    // Xử lý khi đăng ký thành công
                                    if (success) {
                                        isRegister.setValue(true);
                                    }
                                },
                                throwable -> {
                                    // Xử lý lỗi khi đăng ký
                                    Log.e("Register", "Registration failed", throwable);
                                    // Thêm bất kỳ hành động nào khác bạn muốn thực hiện ở đây
                                }
                        )
        );
    }
    public void loginRequest(String email, String password) {
        compositeDisposable.add(
                authRepository.login(email, password)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                employee -> {
                                    _userResponse.setValue(employee);
                                },
                                throwable -> {
                                    setErrorStringId(R.string.error_authentication);
                                    Log.e("Error", throwable.getMessage());
                                },
                                () -> {
                                    // No employee found
                                    setErrorStringId(R.string.no_information);
                                }
                        )
        );
    }
    public void loginRequestEmployee(String email, String password) {
        compositeDisposable.add(
                authRepository.loginEmployee(email, password)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                employee -> {
                                    _employeeResponse.setValue(employee);
                                },
                                throwable -> {
                                    setErrorStringId(R.string.error_authentication);
                                    Log.e("Error", throwable.getMessage());
                                },
                                () -> {
                                    // No employee found
                                    setErrorStringId(R.string.no_information);
                                }
                        )
        );
    }

    public void loginResident(String email, String password){
        setLoading(true);
        compositeDisposable.add(
                authRepository.loginResident(email, password)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                success -> {
                                    // Đăng nhập thành công và email đã được xác minh
                                    Log.d("Login", "Login successful and email verified");
                                    // Cập nhật UI hoặc thực hiện hành động tiếp theo
                                    isLogin.setValue(true);
//                                    setErrorString("Đăng nhập thành công");
                                },
                                throwable -> {
                                    // Xử lý lỗi đăng nhập
                                    Log.e("Login", "Login failed", throwable);
                                    // Cập nhật UI hoặc thông báo lỗi cho người dùng
                                    setErrorString(throwable.toString());
                                }
                        )
        );
    }
    public void checkAccontResident(String email){
        compositeDisposable.add(
                authRepository.checkAccontResident(email)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                resident -> {
                                    // Đăng nhập thành công và email đã được xác minh
                                    Log.d("Login", "Login successful and email verified");
                                    // Cập nhật UI hoặc thực hiện hành động tiếp theo
                                    ischeckAccontResident.setValue(resident);
                                    setLoading(false);
                                },
                                throwable -> {
                                    ischeckAccontResident.setValue(null);
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
                                    // No employee found
                                    setErrorStringId(R.string.no_information);
                                    setLoading(false);

                                }
                        )
        );
    }

public void updateListImage(Bitmap bitmap1, Bitmap bitmap2, Bitmap bitmap3, Bitmap bitmap4) {
        setLoading(true);
    List<ImageWrapper> images = Arrays.asList(
            new ImageWrapper(bitmap1, "cccdt"),
            new ImageWrapper(bitmap2, "cccds"),
            new ImageWrapper(bitmap3, "user"),
            new ImageWrapper(bitmap4, "contract")
    );

    compositeDisposable.add(
            authRepository.uploadImages(images)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            pair -> {
                                // Xử lý mỗi URI được phát ra kèm theo ID
                                String id = pair.first;
                                Uri uri = pair.second;
                                Log.d("ImageUpload", "Uploaded image " + id + " URI: " + uri.toString());
                                if(id.equals("cccdt")){
                                    resident_not_image.setImgIdentityCardFront(uri.toString());
                                } else if (id.equals("cccds")) {
                                    resident_not_image.setImgIndentityCardBehind(uri.toString());
                                } else if (id.equals("user")) {
                                    resident_not_image.setImageUrl(uri.toString());
                                } else if (id.equals("contract")){
                                    {
                                        detailsResident_not_image.setImageContract(uri.toString());
                                    }
                                }
                            },
                            throwable -> {
                                // Xử lý lỗi tại đây
                                Log.e("ImageUpload", "Error uploading images", throwable);
                            },
                            () -> {
                                // Xử lý hoàn thành tại đây
                                addResident(resident_not_image,detailsResident_not_image);
                            }
                    )
    );
}
    public void addResidentViewModel(Resident resident, DetailsResident detailsResident){
        resident_not_image = resident;
        detailsResident_not_image = detailsResident;
    }
    public void addResident(Resident resident, DetailsResident detailsResident){
        compositeDisposable.add(
                authRepository.addResidentall(resident,detailsResident)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                () -> {
                                    isAddSuccess.setValue(true);
                                },
                                throwable -> {
                                    setErrorStringId(R.string.no_information);
                                    setLoading(false);
                                }
                        )
        );
    }

    public void ResetPassword(String email){
        setLoading(true);
        compositeDisposable.add(
                authRepository.forgotEmail(email)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                ()->{
                                    isForgotpass.setValue(true);
                                    setLoading(false);
                                },
                                throwable -> {
                                    setErrorString(throwable.toString());
                                    setLoading(false);
                                }

                        )
        );
    }


    public void updateSex(int sex, String idUser) {
        compositeDisposable.add(
                authRepository.updateSex(sex,idUser)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                ()->{
                                    isUpdateSex.setValue(true);
                                },
                                throwable -> {
                                    setErrorString(throwable.toString());
                                }
                        )
        );
    }
    public void updateSexEmployee(int sex, String idUser) {
        compositeDisposable.add(
                authRepository.updateSexEmployee(sex,idUser)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                ()->{
                                    isUpdateSex.setValue(true);
                                },
                                throwable -> {
                                    setErrorString(throwable.toString());
                                }
                        )
        );
    }
    public void updateSexResident(int sex, String idUser) {
        compositeDisposable.add(
                authRepository.updateSexResident(sex,idUser)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                ()->{
                                    isUpdateSex.setValue(true);
                                },
                                throwable -> {
                                    setErrorString(throwable.toString());
                                }
                        )
        );
    }
    public void getInforUser(String id) {
        compositeDisposable.add(
                authRepository.getInforAdmin(id)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                _adminInfor->{
                                    adminInfor.setValue(_adminInfor);
                                },
                                throwable -> {
                                    setErrorString(throwable.toString());
                                }
                        )
        );
    }
    public void getInforUserEmployee(String id) {
        compositeDisposable.add(
                authRepository.getInforEmployee(id)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                _adminInfor->{
                                    adminInfor.setValue(_adminInfor);
                                },
                                throwable -> {
                                    setErrorString(throwable.toString());
                                }
                        )
        );
    }
    public void getInforUserResident(String id) {
        compositeDisposable.add(
                authRepository.getInforResident(id)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                _adminInfor->{
                                    residentInfor.setValue(_adminInfor);
                                },
                                throwable -> {
                                    setErrorString(throwable.toString());
                                }
                        )
        );
    }
    public void updatePhone(String phone, String idUser) {
        compositeDisposable.add(
                authRepository.updatePhone(phone,idUser)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                ()->{
                                    isUpdatePhone.setValue(true);
                                },
                                throwable -> {
                                    setErrorString(throwable.toString());
                                }
                        )
        );
    }
    public void updatePhoneEmployee(String phone, String idUser) {
        compositeDisposable.add(
                authRepository.updatePhoneEmployee(phone,idUser)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                ()->{
                                    isUpdatePhone.setValue(true);
                                },
                                throwable -> {
                                    setErrorString(throwable.toString());
                                }
                        )
        );
    }
    public void updatePhoneResident(String phone, String idUser) {
        compositeDisposable.add(
                authRepository.updatePhoneResident(phone,idUser)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                ()->{
                                    isUpdatePhone.setValue(true);
                                },
                                throwable -> {
                                    setErrorString(throwable.toString());
                                }
                        )
        );
    }
    public void updateBirth(Date date, String idUser) {
        compositeDisposable.add(
                authRepository.updateBirth(date,idUser)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                ()->{
                                    isUpdateBirth.setValue(true);
                                },
                                throwable -> {
                                    setErrorString(throwable.toString());
                                }
                        )
        );
    }
    public void updateBirthEmployee(Date date, String idUser) {
        compositeDisposable.add(
                authRepository.updateBirthEmployee(date,idUser)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                ()->{
                                    isUpdateBirth.setValue(true);
                                },
                                throwable -> {
                                    setErrorString(throwable.toString());
                                }
                        )
        );
    }
    public void updateBirthResident(Date date, String idUser) {
        compositeDisposable.add(
                authRepository.updateBirthResident(date,idUser)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                ()->{
                                    isUpdateBirth.setValue(true);
                                },
                                throwable -> {
                                    setErrorString(throwable.toString());
                                }
                        )
        );
    }
    public void updatepass(String oldpass, String newPass, String renewPass, String pass,String idUser) {
        if(checkValidPassword(oldpass) && checkValidPassword(newPass) && checkValidPassword(renewPass)){
            if(newPass.equals(renewPass)){
                if(pass.equals(oldpass)){
                    compositeDisposable.add(
                            authRepository.updatePassAdmin(newPass,idUser)
                                    .subscribeOn(Schedulers.io())
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribe(
                                            ()->{
                                                isUpdatePass.setValue(true);
                                            },
                                            throwable -> {
                                                setErrorString(throwable.toString());
                                            }
                                    )
                    );
                }
                else
                    setErrorString("Mật khẩu cũ sai");
            }
            else {
                setErrorString("Mật khẩu không khớp");
            }
        }
        else {
            setErrorString("Mật khẩu có ít nhất 8 ký tự bao gồm cả chữ và số ");
        }
    }
    public void updatepassEmployee(String oldpass, String newPass, String renewPass, String pass,String idUser) {
        if(checkValidPassword(oldpass) && checkValidPassword(newPass) && checkValidPassword(renewPass)){
            if(newPass.equals(renewPass)){
                if(pass.equals(oldpass)){
                    compositeDisposable.add(
                            authRepository.updatePassEmployee(newPass,idUser)
                                    .subscribeOn(Schedulers.io())
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribe(
                                            ()->{
                                                isUpdatePass.setValue(true);
                                            },
                                            throwable -> {
                                                setErrorString(throwable.toString());
                                            }
                                    )
                    );
                }
                else
                    setErrorString("Mật khẩu cũ sai");
            }
            else {
                setErrorString("Mật khẩu không khớp");
            }
        }
        else {
            setErrorString("Mật khẩu có ít nhất 8 ký tự bao gồm cả chữ và số ");
        }
    }
    public void updatepassResident(String oldpass, String newPass, String renewPass, String pass) {
        if(checkValidPassword(oldpass) && checkValidPassword(newPass) && checkValidPassword(renewPass)){
            if(newPass.equals(renewPass)){
                if(pass.equals(oldpass)){
                    compositeDisposable.add(
                            authRepository.updatePassResident(newPass)
                                    .subscribeOn(Schedulers.io())
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribe(
                                            ()->{
                                                isUpdatePass.setValue(true);
                                            },
                                            throwable -> {
                                                setErrorString(throwable.toString());
                                            }
                                    )
                    );
                }
                else
                    setErrorString("Mật khẩu cũ sai");
            }
            else {
                setErrorString("Mật khẩu không khớp");
            }
        }
        else {
            setErrorString("Mật khẩu có ít nhất 8 ký tự bao gồm cả chữ và số ");
        }
    }
    public void updateName(String name, String idUser) {
        compositeDisposable.add(
                authRepository.updateName(name, idUser)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                ()->{
                                    isUpdateName.setValue(true);
                                },
                                throwable -> {
                                    setErrorString(throwable.toString());
                                }
                        )
        );
    }
    public void updateNameEmployee(String name, String idUser) {
        compositeDisposable.add(
                authRepository.updateNameEmployee(name, idUser)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                ()->{
                                    isUpdateName.setValue(true);
                                },
                                throwable -> {
                                    setErrorString(throwable.toString());
                                }
                        )
        );
    }
    public void updateNameResident(String name, String idUser) {
        compositeDisposable.add(
                authRepository.updateNameResident(name, idUser)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                ()->{
                                    isUpdateName.setValue(true);
                                },
                                throwable -> {
                                    setErrorString(throwable.toString());
                                }
                        )
        );
    }
}
