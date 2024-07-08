package com.example.urban_area_manager.feature.Admin.Home.Building.Respository;

import android.graphics.Bitmap;
import android.net.Uri;

import com.example.urban_area_manager.feature.Admin.Home.Building.Model.Building;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Maybe;
import io.reactivex.Single;

public interface BuildingRespository {
    Maybe<List<Building>> getlistBuilding();
    Single<Uri> uploadImage(Bitmap data);
    Single<Boolean> deleteImage(String uri);
    Single<Boolean> deleteBuilding(Building building);

    Single<Boolean> deleteImageAndBuilding(String uri,Building building);

    Completable uploadBuilding(Building building);
    Completable updateBuilding(Building building);
}
