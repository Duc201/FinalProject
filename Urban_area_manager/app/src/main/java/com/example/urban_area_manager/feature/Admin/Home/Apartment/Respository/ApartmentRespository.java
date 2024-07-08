package com.example.urban_area_manager.feature.Admin.Home.Apartment.Respository;

import android.graphics.Bitmap;
import android.net.Uri;

import com.example.urban_area_manager.feature.Admin.Home.Apartment.Model.Apartment;
import com.example.urban_area_manager.feature.Admin.Home.Resident.Model.DetailsResident;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Maybe;
import io.reactivex.Single;

public interface ApartmentRespository {
    Maybe<List<Apartment>> getlistApartment(String idBuilding);
    Maybe<List<Apartment>> getlistApartmentSold(String idBuilding);

    Single<Uri> uploadImage(Bitmap data);
    Completable deleteImage(String uri);
    Completable deleteApartment(Apartment Apartment);

    Completable deleteImageAndApartment(String uri,Apartment apartment);

    Completable uploadApartment(Apartment Apartment);
    Completable updateBApartment(Apartment Apartment);
    Maybe<List<String>> getlistBuilding();
    Single<String> getIDBuilding(String nameBuilding);

    Maybe<List<DetailsResident>> getlistResident(String idApartment);


}
