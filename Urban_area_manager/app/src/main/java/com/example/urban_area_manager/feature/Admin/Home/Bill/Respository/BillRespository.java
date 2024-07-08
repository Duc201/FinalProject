package com.example.urban_area_manager.feature.Admin.Home.Bill.Respository;

import com.example.urban_area_manager.feature.Admin.Home.Bill.Model.Bill;
import com.example.urban_area_manager.feature.Admin.Home.Bill.Model.DetailsBillStep;
import com.example.urban_area_manager.feature.Admin.Home.Bill.Model.DetailsBillFix;
import com.example.urban_area_manager.feature.Admin.Home.Bill.Model.DetailsService;
import com.example.urban_area_manager.feature.Admin.Home.Bill.Model.FixedService;
import com.example.urban_area_manager.feature.Admin.Home.Bill.Model.Step;
import com.example.urban_area_manager.feature.Admin.Home.Bill.Model.StepService;
import com.example.urban_area_manager.feature.Admin.Home.Building.Model.Building;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;

public interface  BillRespository {
   Single<List<Building>> getlistBuilding();
   Completable addService(FixedService fixedService);

   Completable deleteService(FixedService fixedService);
   Single<List<FixedService>> getListService();
   Single<List<FixedService>> getListService(int type);

   Single<List<Bill>> getListBill(String idBuilding, int month , int year);

   Single<List<Bill>> getBillUser(String idApartment);
   Completable updateBill(Bill bill);
   Completable deleteStepServiceAll(StepService stepService);
   Completable addDetailBillEmployee(DetailsBillStep electricBill, DetailsBillStep waterBill, Bill bill);
   Completable addDetailBillEmployeeEdit(DetailsBillStep electricBill, DetailsBillStep waterBill);

   Single<List<StepService>> getListStepService();
   Single<List<DetailsService>> getlistDetailsService(String idApartment);
   Single<List<DetailsService>> getListDetailsServiceForBill(String idApartment);

   Completable addDetailsService(DetailsService detailsService);



   Single<List<DetailsBillStep>> getListStepServiceApartment(String idApartment, int month , int year);
   Single<List<DetailsBillStep>> getListStepServiceUser(String idBill);
   Single<List<DetailsBillFix>> getListDetailBillFix(String idApartment, int month, int year);
   Single<StepService> getStepService(String idService);
   Single<FixedService> getFixedService(String idService);

   Single<DetailsBillStep> getDetailServiceLast(String idApartment, String idService , int month , int year);

   Single<List<DetailsBillStep>> getUniqueApartments(int month, int year,String idBuilding);


    Single<List<Step>> getListStep(String id);
    Completable addAllStepService(StepService stepService,List<Step> stepList);
    Completable addStepService(StepService stepService);
    Completable removeListStep(List<Step> stepList);
    Completable checkCreatBill(String idApartment, int monthCreat, int yearCreat);

   Completable addListDettailsBillFix(List<DetailsBillFix> mlist);
}
