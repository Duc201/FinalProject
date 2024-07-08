package com.example.urban_area_manager.feature.Admin.Home.Notification.Respository;

import androidx.annotation.NonNull;

import com.example.urban_area_manager.feature.Admin.Home.Notification.Model.Notification;
import com.example.urban_area_manager.utils.Constant;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.WriteBatch;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Completable;
import io.reactivex.Single;

public class NotificationRespositoryImp implements NotificationRespository{
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageRef = storage.getReference();
    @Override
    public Completable addNotification(Notification notification) {
        return Completable.create(emitter -> {
            db.collection(Constant.NOTIFICATION).document(notification.getId()).set(notification).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {
                    emitter.onComplete();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    emitter.onError(e);
                }
            });
        });
    }

    @Override
    public Completable addListNotification(List<Notification> listNotification) {
        List<Completable> completables = new ArrayList<>();

        for (Notification notification : listNotification) {
            completables.add(addNotification(notification));
        }

        return Completable.merge(completables);
    }

    @Override
    public Single<List<Notification>> getAllNotification() {
        return Single.create(emitter -> {

            db.collection(Constant.NOTIFICATION)
                    .whereEqualTo("deleted", false)
                    .get()
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            List<Notification> notificationList = new ArrayList<>();
                            Map<String, Notification> notificationMap = new HashMap<>();
                            for (QueryDocumentSnapshot doc : task.getResult()) {
                                Notification notification = doc.toObject(Notification.class);
                                String key = notification.getTitle() + "_" + notification.getContent();
                                if (!notificationMap.containsKey(key)) {
                                    notificationMap.put(key, notification);
                                    notificationList.add(notification);
                                }
                            }
                            if (notificationList.isEmpty()) {
                                emitter.onError(new Exception("No results found for the specified query."));
                            } else {
                                emitter.onSuccess(notificationList);
                            }
                        } else {
                            emitter.onError(task.getException());
                        }
                    });
        });
    }

    @Override
    public Single<List<Notification>> getAllNotificationEmploy(int type) {
        return Single.create(emitter -> {

            db.collection(Constant.NOTIFICATION)
                    .whereEqualTo("deleted", false)
                    .whereEqualTo("type",type)
                    .get()
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            List<Notification> notificationList = new ArrayList<>();
                            Map<String, Notification> notificationMap = new HashMap<>();
                            for (QueryDocumentSnapshot doc : task.getResult()) {
                                Notification notification = doc.toObject(Notification.class);
                                String key = notification.getTitle() + "_" + notification.getContent();
                                if (!notificationMap.containsKey(key)) {
                                    notificationMap.put(key, notification);
                                    notificationList.add(notification);
                                }
                            }
                            if (notificationList.isEmpty()) {
                                emitter.onError(new Exception("No results found for the specified query."));
                            } else {
                                emitter.onSuccess(notificationList);
                            }
                        } else {
                            emitter.onError(task.getException());
                        }
                    });
        });
    }

    @Override
    public Single<List<Notification>> getAllNotificationEmploy1(int type1, int type2) {
        Single<List<Notification>> query1 = Single.create(emitter -> {
            db.collection(Constant.NOTIFICATION)
                    .whereEqualTo("type", type1)
                    .get()
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            List<Notification> notificationList = new ArrayList<>();
                            for (QueryDocumentSnapshot doc : task.getResult()) {
                                Notification notification = doc.toObject(Notification.class);
                                notificationList.add(notification);
                            }
                            emitter.onSuccess(notificationList);
                        } else {
                            emitter.onError(task.getException());
                        }
                    });
        });

        Single<List<Notification>> query2 = Single.create(emitter -> {
            db.collection(Constant.BILL)
                    .whereEqualTo("type", type2)
                    .get()
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            List<Notification> notificationList = new ArrayList<>();
                            for (QueryDocumentSnapshot doc : task.getResult()) {
                                Notification notification = doc.toObject(Notification.class);
                                notificationList.add(notification);
                            }
                            emitter.onSuccess(notificationList);
                        } else {
                            emitter.onError(task.getException());
                        }
                    });
        });

        return Single.zip(query1, query2, (list1, list2) -> {
            List<Notification> combinedList = new ArrayList<>();
            combinedList.addAll(list1);
            combinedList.addAll(list2);
            return combinedList;
        });
    }

    @Override
    public Single<List<Notification>> getAllNotificationResident(String idUser) {
        return Single.create(emitter -> {

            db.collection(Constant.NOTIFICATION)
                    .whereEqualTo("deleted", false)
                    .whereEqualTo("idreceiver",idUser)
                    .get()
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            List<Notification> notificationList = new ArrayList<>();
                            Map<String, Notification> notificationMap = new HashMap<>();
                            for (QueryDocumentSnapshot doc : task.getResult()) {
                                Notification notification = doc.toObject(Notification.class);
                                String key = notification.getTitle() + "_" + notification.getContent();
                                if (!notificationMap.containsKey(key)) {
                                    notificationMap.put(key, notification);
                                    notificationList.add(notification);
                                }
                            }
                            if (notificationList.isEmpty()) {
                                emitter.onError(new Exception("No results found for the specified query."));
                            } else {
                                emitter.onSuccess(notificationList);
                            }
                        } else {
                            emitter.onError(task.getException());
                        }
                    });
        });
    }


    @Override
    public Completable deleteNotification(Notification notification) {
        return Completable.create(emitter -> {

            db.collection(Constant.NOTIFICATION)
                    .whereEqualTo("title", notification.getTitle())
                    .whereEqualTo("content", notification.getContent())
                    .get()
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            WriteBatch batch = db.batch();
                            for (QueryDocumentSnapshot doc : task.getResult()) {
                                DocumentReference docRef = doc.getReference();
                                batch.update(docRef, "deleted", true);
                            }
                            batch.commit().addOnCompleteListener(commitTask -> {
                                if (commitTask.isSuccessful()) {
                                    emitter.onComplete();
                                } else {
                                    emitter.onError(commitTask.getException());
                                }
                            });
                        } else {
                            emitter.onError(task.getException());
                        }
                    });
        });
    }




}
