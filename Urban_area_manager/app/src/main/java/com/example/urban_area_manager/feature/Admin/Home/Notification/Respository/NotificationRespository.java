package com.example.urban_area_manager.feature.Admin.Home.Notification.Respository;

import com.example.urban_area_manager.feature.Admin.Home.Notification.Model.Notification;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;

public interface NotificationRespository {
    Completable addNotification(Notification notification);
    Completable addListNotification(List<Notification> listNotification);
    Single<List<Notification>> getAllNotification();
    Single<List<Notification>> getAllNotificationEmploy(int type);
    Single<List<Notification>> getAllNotificationEmploy1(int type1, int type2);
    Single<List<Notification>> getAllNotificationResident(String idUser);


    Completable deleteNotification(Notification notification);


}
