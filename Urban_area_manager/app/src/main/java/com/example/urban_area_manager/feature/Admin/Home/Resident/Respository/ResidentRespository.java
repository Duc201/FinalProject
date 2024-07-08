package com.example.urban_area_manager.feature.Admin.Home.Resident.Respository;

import com.example.urban_area_manager.feature.Admin.Home.Resident.Model.DetailsResident;
import com.example.urban_area_manager.feature.Admin.Home.Resident.Model.Resident;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;

public interface ResidentRespository {
 Single<List<Resident>> getListResidentAuthen();

 Single<List<Resident>> getListResidentAuthened();
 Single<List<DetailsResident>> getListDetailsResident(String idUser,int state);
 Single<DetailsResident> getListDetailResident(String idUser,int state);

Single<List<DetailsResident>> getListDetailsResidenInBuilding(String idBuilding);
 Single<List<DetailsResident>> getListDetailsResiden();


 Completable updateStateResident(String idResident , String idDetailsResident , String email , int state);

 Single<String> getIdApartment(String idUser);

 Completable deleteResidentall(String idResident);
 Completable updateResidentAll(Resident resident,List<DetailsResident> listdetailsResident);
 Completable updateDetailsResident(DetailsResident detailsResident);

 Single<DetailsResident> getDetailResident(String idResident);

 Single<List<DetailsResident>> getListDetailResidentApartment(String idApartment);


}
