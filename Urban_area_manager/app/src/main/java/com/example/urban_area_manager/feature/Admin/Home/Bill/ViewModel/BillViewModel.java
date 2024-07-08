package com.example.urban_area_manager.feature.Admin.Home.Bill.ViewModel;

import android.graphics.Bitmap;
import android.net.Uri;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.urban_area_manager.R;
import com.example.urban_area_manager.core.base.BaseViewModel;
import com.example.urban_area_manager.core.component.DataLocalManager;
import com.example.urban_area_manager.feature.Auth.viewmodel.ImageWrapper;
import com.example.urban_area_manager.feature.Admin.Home.Apartment.Model.Apartment;
import com.example.urban_area_manager.feature.Admin.Home.Apartment.Respository.ApartmentRespository;
import com.example.urban_area_manager.feature.Admin.Home.Apartment.Respository.ApartmentRespositoryImp;
import com.example.urban_area_manager.feature.Admin.Home.Bill.Model.Bill;
import com.example.urban_area_manager.feature.Admin.Home.Bill.Model.DetailsBillStep;
import com.example.urban_area_manager.feature.Admin.Home.Bill.Model.DetailsBillFix;
import com.example.urban_area_manager.feature.Admin.Home.Bill.Model.DetailsService;
import com.example.urban_area_manager.feature.Admin.Home.Bill.Model.FixedService;
import com.example.urban_area_manager.feature.Admin.Home.Bill.Model.Step;
import com.example.urban_area_manager.feature.Admin.Home.Bill.Model.StepService;
import com.example.urban_area_manager.feature.Admin.Home.Bill.Respository.BillRespository;
import com.example.urban_area_manager.feature.Admin.Home.Bill.Respository.BillRespositoryImp;
import com.example.urban_area_manager.feature.Admin.Home.Building.Model.Building;
import com.example.urban_area_manager.feature.Admin.Reflect.Respository.ReflectRespository;
import com.example.urban_area_manager.feature.Admin.Reflect.Respository.ReflectRespositoryImp;
import com.example.urban_area_manager.utils.extensions.Extensions;
import com.example.urban_area_manager.utils.livedata.SingleLiveEvent;
import com.google.firebase.Timestamp;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class BillViewModel extends BaseViewModel {

    private final MutableLiveData<List<Building>> _listBuilding = new MutableLiveData<>();
//    private final MutableLiveData<List<DetailsBill>> _listStepServiceApartment = new MutableLiveData<>();
//
//    public LiveData<List<DetailsBill>> listStepServiceApartment(){return _listStepServiceApartment;}
   public SingleLiveEvent<List<DetailsBillStep>> _listStepServiceApartment = new SingleLiveEvent<>();
    public SingleLiveEvent<List<DetailsBillStep>> DetailsBillApartments = new SingleLiveEvent<>();
    public SingleLiveEvent<FixedService> _FixedService = new SingleLiveEvent<>();
    public SingleLiveEvent<List<DetailsBillFix>> _listServiceFixedApartment = new SingleLiveEvent<>();
    public SingleLiveEvent<Boolean> isUpdateBill = new SingleLiveEvent<>();
    public SingleLiveEvent<List<Bill>> billResidentList = new SingleLiveEvent<>();
    public SingleLiveEvent<List<DetailsBillStep>> _listStepServiceUse = new SingleLiveEvent<>();
    public SingleLiveEvent<List<DetailsBillFix>> listServiceFixedResident = new SingleLiveEvent<>();
    public SingleLiveEvent<List<Step>> listStep = new SingleLiveEvent<>();
    public SingleLiveEvent<Boolean> isRemoveStepService = new SingleLiveEvent<>();
    public SingleLiveEvent<List<FixedService>> _listServiceType = new SingleLiveEvent<>();
    public SingleLiveEvent<List<DetailsService>> listDetailsService = new SingleLiveEvent<>();
    public SingleLiveEvent<Boolean> isAddDetailsService = new SingleLiveEvent<>();
    public SingleLiveEvent<Boolean> isAddDetailsBillFix = new SingleLiveEvent<>();
    public SingleLiveEvent<Boolean> isCreatBill = new SingleLiveEvent<>();

    public LiveData<List<Building>> listBuilding() {
        return _listBuilding;
    }

    private final MutableLiveData<DetailsBillStep> _DetailElectricLast = new MutableLiveData<>();
    public LiveData<DetailsBillStep> DetailElectricLast(){return _DetailElectricLast;}
    private final MutableLiveData<DetailsBillStep> _DetailWaterLast = new MutableLiveData<>();
    public LiveData<DetailsBillStep> DetailWaterLast(){return _DetailWaterLast;}
    private final MutableLiveData<List<FixedService>> _listService = new MutableLiveData<>();
    public LiveData<List<FixedService>> listService() {
        return _listService;
    }

    private final MutableLiveData<List<Apartment>> _listApartment = new MutableLiveData<>();
    public LiveData<List<Apartment>> listApartment() {
        return _listApartment;
    }

    private final MutableLiveData<List<DetailsBillStep>> _listUniqueApartment = new MutableLiveData<>();
    public LiveData<List<DetailsBillStep>> listUniqueApartment() {
        return _listUniqueApartment;
    }
    private final MutableLiveData<List<StepService>> _listStepService = new MutableLiveData<>();
    public LiveData<List<StepService>> listStepService() {
        return _listStepService;
    }

    private final MutableLiveData<StepService> _StepService = new MutableLiveData<>();
    public LiveData<StepService> StepService() {
        return _StepService;
    }

    public SingleLiveEvent<Boolean> isAddService = new SingleLiveEvent<>();

    public SingleLiveEvent<Boolean> isUpdateService = new SingleLiveEvent<>();
    public SingleLiveEvent<Boolean> isDeleteService = new SingleLiveEvent<>();

    public SingleLiveEvent<Boolean> isAddStepService = new SingleLiveEvent<>();
    public SingleLiveEvent<Boolean> isUpdateStepService = new SingleLiveEvent<>();
    public SingleLiveEvent<Boolean> isDeleteStepService = new SingleLiveEvent<>();

    public SingleLiveEvent<Boolean> isAddDetails = new SingleLiveEvent<>();
    public SingleLiveEvent<Boolean> isAddDetailsEdit = new SingleLiveEvent<>();
    public SingleLiveEvent<List<Bill>> billList = new SingleLiveEvent<>();


    private DetailsBillStep electriBill;
    private DetailsBillStep watterBill;
//    private DetailsBillFix managerBill;
    private Bill sumBill;

    private DetailsBillStep electriBillEdit;
    private DetailsBillStep watterBillEdit;
    private Bill sumBillEdit;

    public BillRespository billRespository = new BillRespositoryImp();
    public ApartmentRespository apartmentRespository = new ApartmentRespositoryImp();
    public ReflectRespository reflectRespository = new ReflectRespositoryImp();

    public void getlistApartment(String idbuilding){
        compositeDisposable.add(
                apartmentRespository.getlistApartmentSold(idbuilding)
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
    public void addService(FixedService fixedService){
        setLoading(true);
        compositeDisposable.add(
                billRespository.addService(fixedService)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                () -> {
                                    isAddService.setValue(true);
                                    setLoading(false);
                                },
                                throwable -> {
                                    setErrorString(throwable.getMessage());
                                    setLoading(false);
                                }
                        )
        );
    }
    public void addDetailsService(DetailsService detailsService){
        setLoading(true);
        compositeDisposable.add(
                billRespository.addDetailsService(detailsService)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                () -> {
                                    isAddDetailsService.setValue(true);
                                    setLoading(false);
                                },
                                throwable -> {
                                    setErrorString(throwable.getMessage());
                                    setLoading(false);
                                }
                        )
        );
    }
    public void updateService(FixedService fixedService){
        setLoading(true);
        compositeDisposable.add(
                billRespository.addService(fixedService)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                () -> {
                                    isUpdateService.setValue(true);
                                    setLoading(false);
                                },
                                throwable -> {
                                    setErrorString(throwable.getMessage());
                                    setLoading(false);
                                }
                        )
        );
    }
    public void deleteService(FixedService fixedService){
        setLoading(true);
        compositeDisposable.add(
                billRespository.deleteService(fixedService)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                () -> {
                                    isDeleteService.setValue(true);
                                    setLoading(false);
                                },
                                throwable -> {
                                    setErrorString(throwable.getMessage());
                                    setLoading(false);
                                }
                        )
        );
    }
    public void deleteStepService(StepService stepService){
        setLoading(true);
        compositeDisposable.add(
                billRespository.deleteStepServiceAll(stepService)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                () -> {
                                    isDeleteStepService.setValue(true);
                                    setLoading(false);
                                },
                                throwable -> {
                                    setErrorString(throwable.getMessage());
                                    setLoading(false);
                                }
                        )
        );
    }
    public void getlistBuilding(){
        compositeDisposable.add(
                billRespository.getlistBuilding()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                listBuilding -> {
                                    _listBuilding.setValue(listBuilding);
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
    public void getlistService(){
        compositeDisposable.add(
                billRespository.getListService()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                listService -> {
                                    _listService.setValue(listService);
                                    setLoading(false);
                                },
                                throwable -> {
                                    setErrorString("Không tìm thấy dịch vụ ");
                                    Log.e("Error", throwable.getMessage());
                                    setLoading(false);
                                }
                        )
        );
    }
    public void getlistService(int type){
        compositeDisposable.add(
                billRespository.getListService( type)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                listService -> {
                                    _listServiceType.setValue(listService);
                                    setLoading(false);
                                },
                                throwable -> {
                                    setErrorString("Không tìm thấy dịch vụ ");
                                    Log.e("Error", throwable.getMessage());
                                    setLoading(false);
                                }
                        )
        );
    }
    public void getlistStepService(){
        setLoading(true);
        compositeDisposable.add(
                billRespository.getListStepService()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                listService -> {
                                    _listStepService.setValue(listService);
                                    setLoading(false);
                                },
                                throwable -> {
                                    setErrorString("Không tìm thấy dịch vụ ");
                                    Log.e("Error", throwable.getMessage());
                                    setLoading(false);
                                }
                        )
        );
    }
    public void getListStepServiceApartment(String idApartment,int month , int year){
        setLoading(true);
        compositeDisposable.add(
                billRespository.getListStepServiceApartment(idApartment,month,year)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                listDetail -> {
                                    _listStepServiceApartment.setValue(listDetail);
                                    setLoading(false);
                                },
                                throwable -> {
                                    _listStepServiceApartment.setValue(null);
//                                    setErrorString("Không tìm thấy dịch vụ ");
//                                    Log.e("Error", throwable.getMessage());
                                    setLoading(false);
                                }
                        )
        );
    }

    public void checkCreatBill(String idApartment,int month , int year){
        setLoading(true);
        compositeDisposable.add(
                billRespository.checkCreatBill(idApartment,month,year)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                () -> {
                                    isCreatBill.setValue(true);
                                    setLoading(false);
                                },
                                throwable -> {
                                   setErrorString("Hóa đơn đã được tạo trước đó");
                                    setLoading(false);
                                }
                        )
        );
    }

    public void getListStepServiceUser(String idBill){
        setLoading(true);
        compositeDisposable.add(
                billRespository.getListStepServiceUser(idBill)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                listDetail -> {
                                    _listStepServiceUse.setValue(listDetail);
                                    setLoading(false);
                                },
                                throwable -> {
                                    _listStepServiceUse.setValue(null);
                                    setLoading(false);
                                }
                        )
        );
    }
//    public void getCombinedFixedServiceApartments(String idApartment, int month, int year){
//        setLoading(true);
//        compositeDisposable.add(
//                billRespository.getCombinedFixedServiceApartments(idApartment,month,year)
//                        .subscribeOn(Schedulers.io())
//                        .observeOn(AndroidSchedulers.mainThread())
//                        .subscribe(
//                                listDetailFixed -> {
//                                    _listServiceFixedApartment.setValue(listDetailFixed);
//                                    setLoading(false);
//                                },
//                                throwable -> {
//                                    _listServiceFixedApartment.setValue(null);
////                                    setErrorString("Không tìm thấy dịch vụ ");
////                                    Log.e("Error", throwable.getMessage());
//                                    setLoading(false);
//                                }
//                        )
//        );
//    }
//    public void getListFixedServiceResident(String idBill , String idApartment){
//        setLoading(true);
//        compositeDisposable.add(
//                billRespository.getListFixedServiceResident(idBill , idApartment)
//                        .subscribeOn(Schedulers.io())
//                        .observeOn(AndroidSchedulers.mainThread())
//                        .subscribe(
//                                listDetailFixed -> {
//                                    listServiceFixedResident.setValue(listDetailFixed);
//                                    setLoading(false);
//                                },
//                                throwable -> {
//                                    listServiceFixedResident.setValue(null);
////                                    setErrorString("Không tìm thấy dịch vụ ");
////                                    Log.e("Error", throwable.getMessage());
//                                    setLoading(false);
//                                }
//                        )
//        );
//    }
    public void getStepService(String idStepService){
        compositeDisposable.add(
                billRespository.getStepService(idStepService)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                StepService -> {
                                    _StepService.setValue(StepService);
                                    setLoading(false);
                                },
                                throwable -> {
                                    setErrorString("Không tìm thấy dịch vụ ");
                                    Log.e("Error", throwable.getMessage());
                                    setLoading(false);
                                }
                        )
        );
    }
    public void getFixedService(String idFixService){
        compositeDisposable.add(
                billRespository.getFixedService(idFixService)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                FixedService -> {
                                    _FixedService.setValue(FixedService);
                                    setLoading(false);
                                },
                                throwable -> {
                                    setErrorString("Không tìm thấy dịch vụ ");
                                    Log.e("Error", throwable.getMessage());
                                    setLoading(false);
                                }
                        )
        );
    }
    public void getDetailElectricLast(String idApartment, String idService , int month , int year){
        compositeDisposable.add(
                billRespository.getDetailServiceLast(idApartment,idService,month,year)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                list -> {
                                    _DetailElectricLast.setValue(list);
                                    setLoading(false);
                                },
                                throwable -> {
//                                    setErrorString("Không tìm thấy chỉ số kỳ trước ");
//                                    Log.e("Error", throwable.getMessage());
                                    setLoading(false);
                                }
                        )
        );
    }
    public void getDetuailWaterLast(String idApartment, String idService , int month , int year){
        compositeDisposable.add(
                billRespository.getDetailServiceLast(idApartment,idService,month,year)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                list -> {
                                    _DetailWaterLast.setValue(list);
                                    setLoading(false);
                                },
                                throwable -> {
//                                    setErrorString("Không tìm thấy dịch vụ ");
                                    Log.e("Error", throwable.getMessage());
                                    setLoading(false);
                                }
                        )
        );
    }

    public void updateListImageEdit(Bitmap bitmap1, Bitmap bitmap2) {
        setLoading(true);
        List<ImageWrapper> images = new ArrayList<>();
        if(bitmap1!=null){
            images.add( new ImageWrapper(bitmap1, "elec"));
        }
        if(bitmap2!=null){
            images.add( new ImageWrapper(bitmap2, "water"));
        }

        compositeDisposable.add(
                reflectRespository.uploadImages(images)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                pair -> {
                                    String id = pair.first;
                                    Uri uri = pair.second;
                                    if(id.equals("elec")){
                                        electriBillEdit.setPathNewImage(uri.toString());
                                    } else if (id.equals("water")) {
                                        watterBillEdit.setPathNewImage(uri.toString());
                                    }
                                    setLoading(false);
                                },
                                throwable -> {
                                    // Xử lý lỗi tại đây
                                    Log.e("ImageUpload", "Error uploading images", throwable);
                                    setLoading(false);

                                },
                                () -> {
                                    addDetailBillEmployeeEdit(electriBillEdit, watterBillEdit);
                                }
                        )
        );
    }
    public void updateListImage(Bitmap bitmap1, Bitmap bitmap2) {
        setLoading(true);
        List<ImageWrapper> images = new ArrayList<>();
        images.add( new ImageWrapper(bitmap1, "elec"));
        images.add( new ImageWrapper(bitmap2, "water"));

        compositeDisposable.add(
                reflectRespository.uploadImages(images)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                pair -> {
                                    String id = pair.first;
                                    Uri uri = pair.second;
                                    if(id.equals("elec")){
                                        electriBill.setPathNewImage(uri.toString());
                                    } else if (id.equals("water")) {
                                        watterBill.setPathNewImage(uri.toString());
                                    }
                                    setLoading(false);
                                },
                                throwable -> {
                                    // Xử lý lỗi tại đây
                                    Log.e("ImageUpload", "Error uploading images", throwable);
                                    setLoading(false);

                                },
                                () -> {
                                    addDetailBillEmployee(electriBill, watterBill, sumBill);
                                }
                        )
        );
    }

    public void addBill(Bill bill) {
        sumBill = bill;
    }

    public void addDeatilsElec(DetailsBillStep detailsBillStepElec) {
        electriBill = detailsBillStepElec;
    }

    public void addDetailsWater(DetailsBillStep detailsBillStepWater) {
        watterBill = detailsBillStepWater;
    }
//    public void addDetailsManager(DetailsBillFix detailsBillManager) {
//        managerBill = detailsBillManager;
//    }

    public void addDetailsWaterEdit(DetailsBillStep detailsBillStepWater) {
        watterBillEdit = detailsBillStepWater;
    }


    public void addDeatilsElecEdit(DetailsBillStep detailsBillStepElec) {
        electriBillEdit = detailsBillStepElec;
    }


    public void addDetailBillEmployee(DetailsBillStep electricBill, DetailsBillStep waterBill, Bill bill){
        compositeDisposable.add(
                billRespository.addDetailBillEmployee(electricBill,waterBill,bill)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                () -> {
                                    isAddDetails.setValue(true);
                                    setLoading(false);
                                },
                                throwable -> {

                                    setErrorString(throwable.getMessage());
                                    setLoading(false);
                                }
                        )
        );
    }
    public void addDetailBillEmployeeEdit(DetailsBillStep electricBill, DetailsBillStep waterBill){
        compositeDisposable.add(
                billRespository.addDetailBillEmployeeEdit(electricBill,waterBill)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                () -> {
                                    isAddDetailsEdit.setValue(true);
                                    setLoading(false);
                                },
                                throwable -> {

                                    setErrorString(throwable.getMessage());
                                    setLoading(false);
                                }
                        )
        );
    }
    public void getUniqueApartments(int month, int year,String idBuilding){
        compositeDisposable.add(
                billRespository.getUniqueApartments(month,year,idBuilding)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                DetailsBill -> {
                                    DetailsBillApartments.setValue(DetailsBill);
                                    setLoading(false);
                                },
                                throwable -> {

                                    setErrorString(throwable.getMessage());
                                    setLoading(false);
                                }
                        )
        );
    }


    public void updateBill(Bill bill) {
        setLoading(true);
        compositeDisposable.add(
                billRespository. updateBill(bill)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                () -> {
                                    isUpdateBill.setValue(true);
                                    setLoading(false);
                                },
                                throwable -> {
                                    setErrorString(throwable.getMessage());
                                    isUpdateBill.setValue(false);
                                    setLoading(false);
                                }
                        )
        );
    }
    public void  getListBill(String idBuilding, int month , int year){
        setLoading(true);
        compositeDisposable.add(
                billRespository.getListBill(idBuilding, month , year)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                list->{
                                    billList.setValue(list);
                                    setLoading(false);
                                },
                                throwable -> {
                                    setErrorString(throwable.getMessage());
                                    setLoading(false);
                                }
                        )
        );
    }


    public void getBillResident(String idApartment) {
        setLoading(true);
        compositeDisposable.add(
                billRespository.getBillUser(idApartment)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                list->{
                                    billResidentList.setValue(list);
                                    setLoading(false);
                                },
                                throwable -> {
                                    setErrorString(throwable.getMessage());
                                    setLoading(false);
                                }
                        )
        );
    }


    public void getStep(String id) {
        compositeDisposable.add(
                billRespository.getListStep(id)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                list->{
                                    listStep.setValue(list);
                                    setLoading(false);
                                },
                                throwable -> {
                                    setErrorString(throwable.getMessage());
                                    setLoading(false);
                                }
                        )
        );
    }

    public void updateAllStepService(StepService stepService, List<Step> mlistStep) {
        compositeDisposable.add(
                billRespository.addAllStepService(stepService,mlistStep)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                ()->{
                                    isUpdateStepService.setValue(true);
                                    setLoading(false);
                                },
                                throwable -> {
                                    setErrorString(throwable.getMessage());
                                    setLoading(false);
                                }
                        )
        );
    }
    public void addStepService(StepService stepService) {
        compositeDisposable.add(
                billRespository.addStepService(stepService)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                ()->{
                                    isAddStepService.setValue(true);
                                    setLoading(false);
                                },
                                throwable -> {
                                    setErrorString(throwable.getMessage());
                                    setLoading(false);
                                }
                        )
        );
    }

    public void removeListStepService( List<Step> mlistStep) {
        compositeDisposable.add(
                billRespository.removeListStep(mlistStep)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                ()->{
                                    isRemoveStepService.setValue(true);
                                    setLoading(false);
                                },
                                throwable -> {
                                    setErrorString(throwable.getMessage());
                                    setLoading(false);
                                }
                        )
        );
    }

    public void getlistDetailsService(String idApartment) {
        compositeDisposable.add(
                billRespository.getlistDetailsService(idApartment)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                list->{
                                   listDetailsService.setValue(list);
                                    setLoading(false);
                                },
                                throwable -> {
                                    setErrorString(throwable.getMessage());
                                    setLoading(false);
                                }
                        )
        );
    }
    public void getlistDetailsServiceForBill(String idApartment, int month, int year, String idBill, String idBuilding, String apartmentName) {
        compositeDisposable.add(
                billRespository.getListDetailsServiceForBill(idApartment)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                list -> {
                                    updateDetailBillService(idApartment, list, month, year, idBill, idBuilding, apartmentName);
//                                    listDetailsService.setValue(list != null ? list : Collections.emptyList());
                                    setLoading(false);
                                },
                                throwable -> {
                                    setErrorString(throwable.getMessage());
//                                    listDetailsService.setValue(Collections.emptyList());

                                    setLoading(false);
                                }
                        )
        );
    }

    public void updateDetailBillService(String idApartment, List<DetailsService> list, int month, int year, String idBill, String idBuilding, String apartmentName) {
        List<DetailsBillFix> mlist = new ArrayList<>();
        if (list != null) {
            for (DetailsService detailsService : list) {
                DetailsBillFix detailsBillManager = new DetailsBillFix();
                detailsBillManager.setId(UUID.randomUUID().toString());
                detailsBillManager.setIdBill(idBill);
                detailsBillManager.setIdService(detailsService.getIdService());
                detailsBillManager.setMonth(month);
                detailsBillManager.setYear(year);
                detailsBillManager.setCount(detailsService.getQuality());
                detailsBillManager.setPrice(detailsService.getPrice());
                if(detailsService.getDelete()){
                    int dayUse = Extensions.calculateServiceUsageDays(detailsService.getTimeCreat(),detailsService.getTimeDelete());
                    detailsBillManager.setSumDetailBill(dayUse*detailsService.getPrice());
//                    detailsService.setPay(true);
                }
                else {
                    detailsBillManager.setSumDetailBill(detailsService.getPrice() * detailsService.getQuality());
                }
                detailsBillManager.setNamService(detailsService.getNameService());
                detailsBillManager.setIdBuilding(idBuilding);
                detailsBillManager.setIdApartment(idApartment);
                detailsBillManager.setNameApartment(apartmentName);
                detailsBillManager.setCreationTime(Timestamp.now().toDate());
                detailsBillManager.setCreatorUserId(DataLocalManager.getEmail());
                mlist.add(detailsBillManager);
            }
        }
        compositeDisposable.add(
                billRespository.addListDettailsBillFix(mlist)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                () -> {
                                    isAddDetailsBillFix.setValue(true);
                                    setLoading(false);
                                },
                                throwable -> {
                                    setErrorString(throwable.getMessage());
                                    setLoading(false);
                                }
                        )
        );
    }

    public void getListDetailsBillFixed(String idApartment, int monthCreat, int yearCreat) {
        compositeDisposable.add(
                billRespository.getListDetailBillFix(idApartment,monthCreat,yearCreat)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                list->{
                                    _listServiceFixedApartment.setValue(list);
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
