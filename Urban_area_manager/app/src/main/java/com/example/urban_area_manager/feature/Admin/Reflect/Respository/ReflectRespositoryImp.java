package com.example.urban_area_manager.feature.Admin.Reflect.Respository;

import android.graphics.Bitmap;
import android.net.Uri;
import android.util.Pair;

import androidx.annotation.NonNull;

import com.example.urban_area_manager.feature.Auth.viewmodel.ImageWrapper;
import com.example.urban_area_manager.feature.Admin.Reflect.Model.Reflect;
import com.example.urban_area_manager.utils.Constant;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;

public class ReflectRespositoryImp implements ReflectRespository{
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageRef = storage.getReference();
    @Override
    public Single<List<Reflect>> getListAssignedReflect() {
        return Single.create(emitter ->
                db.collection(Constant.REFLECT)
                        .whereEqualTo("deleted", false)
                        .whereEqualTo("state", 1)
                        .orderBy("timeCreator", Query.Direction.DESCENDING)
                        .get()
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                List<Reflect> reflectList = new ArrayList<>();
                                for (QueryDocumentSnapshot doc : task.getResult()) {
                                    Reflect reflect = doc.toObject(Reflect.class);
                                    reflectList.add(reflect);
                                }
                                emitter.onSuccess(reflectList);
                            } else {
                                emitter.onError(task.getException());
                            }
                        })
        );
    }

    @Override
    public Single<List<Reflect>> getListProcesReflect() {
        return Single.create(emitter ->
                db.collection(Constant.REFLECT)
                        .whereEqualTo("deleted", false)
                        .whereEqualTo("state", 0)
                        .orderBy("timeCreator", Query.Direction.DESCENDING)
                        .get()
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                List<Reflect> reflectList = new ArrayList<>();
                                for (QueryDocumentSnapshot doc : task.getResult()) {
                                    Reflect reflect = doc.toObject(Reflect.class);
                                    reflectList.add(reflect);
                                }
                                emitter.onSuccess(reflectList);
                            } else {
                                emitter.onError(task.getException());
                            }
                        })
        );
    }

    @Override
    public Single<List<Reflect>> getListProcesEmployeeReflect(String idHandler) {
        return Single.create(emitter ->
                db.collection(Constant.REFLECT)
                        .whereEqualTo("deleted", false)
                        .whereEqualTo("state", 1)
                        .whereEqualTo("idHandler",idHandler)
                        .get()
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                List<Reflect> reflectList = new ArrayList<>();
                                for (QueryDocumentSnapshot doc : task.getResult()) {
                                    Reflect reflect = doc.toObject(Reflect.class);
                                    reflectList.add(reflect);
                                }
                                emitter.onSuccess(reflectList);
                            } else {
                                emitter.onError(task.getException());
                            }
                        })
        );
    }

    @Override
    public Single<List<Reflect>> getListCompleteReflect() {
        return Single.create(emitter ->
                db.collection(Constant.REFLECT)
                        .whereEqualTo("deleted", false)
                        .whereEqualTo("state", 2)
                        .get()
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                List<Reflect> reflectList = new ArrayList<>();
                                for (QueryDocumentSnapshot doc : task.getResult()) {
                                    Reflect reflect = doc.toObject(Reflect.class);
                                    reflectList.add(reflect);
                                }
                                emitter.onSuccess(reflectList);
                            } else {
                                emitter.onError(task.getException());
                            }
                        })
        );
    }

    @Override
    public Single<List<Reflect>> getListEmployeeCompleteReflect(String idHandle) {
        return Single.create(emitter ->
                db.collection(Constant.REFLECT)
                        .whereEqualTo("deleted", false)
                        .whereEqualTo("state", 2)
                        .whereEqualTo("idHandler",idHandle)
                        .get()
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                List<Reflect> reflectList = new ArrayList<>();
                                for (QueryDocumentSnapshot doc : task.getResult()) {
                                    Reflect reflect = doc.toObject(Reflect.class);
                                    reflectList.add(reflect);
                                }
                                emitter.onSuccess(reflectList);
                            } else {
                                emitter.onError(task.getException());
                            }
                        })
        );
    }

    @Override
    public Single<List<Reflect>> getListMyReflect(String email) {
        return Single.create(emitter ->
                db.collection(Constant.REFLECT)
                        .whereEqualTo("idCreator", email)
                        .whereEqualTo("deleted", false)
                        .orderBy("timeCreator", Query.Direction.DESCENDING)
                        .get()
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                List<Reflect> reflectList = new ArrayList<>();
                                for (QueryDocumentSnapshot doc : task.getResult()) {
                                    Reflect reflect = doc.toObject(Reflect.class);
                                    reflectList.add(reflect);
                                }
                                emitter.onSuccess(reflectList);
                            } else {
                                emitter.onError(task.getException());
                            }
                        })
        );
    }

    public Single<Pair<String, Uri>> uploadImage(ImageWrapper imageWrapper) {
        return Single.create(emitter -> {

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            imageWrapper.getBitmap().compress(Bitmap.CompressFormat.JPEG, 30, baos);
            byte[] data = baos.toByteArray();
            String fileName = UUID.randomUUID().toString();
            final StorageReference imageRef = storageRef.child("images/" + fileName);


            UploadTask uploadTask = imageRef.putBytes(data);

            uploadTask.continueWithTask(task -> {
                if (!task.isSuccessful()) {
                    throw task.getException();
                }
                return imageRef.getDownloadUrl();
            }).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    Uri downloadUri = task.getResult();
                    if (!emitter.isDisposed()) {
                        emitter.onSuccess(new Pair<>(imageWrapper.getId(), downloadUri));
                    }
                } else {
                    if (!emitter.isDisposed()) {
                        emitter.onError(task.getException());
                    }
                }
            });
        });
    }
    public Observable<Pair<String, Uri>> uploadImages(List<ImageWrapper> images) {
        List<Observable<Pair<String, Uri>>> observables = new ArrayList<>();
        for (ImageWrapper image : images) {
            observables.add(uploadImage(image).toObservable());
        }
        return Observable.merge(observables);
    }

    @Override
    public Completable addReflect(Reflect reflect) {
        return Completable.create(emitter -> {
            db.collection(Constant.REFLECT).document(reflect.getId()).set(reflect).addOnSuccessListener(new OnSuccessListener<Void>() {
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
    public Completable updateProcessReflectNo(String rejectReason, int state, String idRelect) {
        return Completable.create(emitter -> {
            Map<String,Object> data = new HashMap<>();
            data.put("result",rejectReason);
            data.put("state",state);
            db.collection(Constant.REFLECT).document(idRelect).update(data).addOnSuccessListener(new OnSuccessListener<Void>() {
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
    public Completable updateProcessReflectYes(int department, String idEmployee, String nameEmployee, int state, String idReflect) {
        return Completable.create(emitter -> {
            Map<String,Object> data = new HashMap<>();
            data.put("department",department);
            data.put("idHandler",idEmployee);
            data.put("nameHandler",nameEmployee);
            data.put("state",state);
            db.collection(Constant.REFLECT).document(idReflect).update(data).addOnSuccessListener(new OnSuccessListener<Void>() {
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
    public Completable updateReflectEmployee(String pathImageRespon1, String pathImageRespon2, String result, String idReflect
    , int state) {
        return Completable.create(emitter -> {
            Map<String,Object> data = new HashMap<>();
            data.put("respondimg1",pathImageRespon1);
            data.put("respondimg2",pathImageRespon2);
            data.put("result",result);
            data.put("state",state);
            data.put("timeHandle",Timestamp.now().toDate());
            db.collection(Constant.REFLECT).document(idReflect).update(data).addOnSuccessListener(new OnSuccessListener<Void>() {
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
    public Completable updateReflectResident(String content, String specificLocation, String ifReflect) {
        return Completable.create(emitter -> {
            Map<String,Object> data = new HashMap<>();
            data.put("specificLocation",specificLocation);
            data.put("content",content);
            db.collection(Constant.REFLECT).document(ifReflect).update(data).addOnSuccessListener(new OnSuccessListener<Void>() {
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
    public Completable updateFeedback(String feedback, String idReflect) {
        return Completable.create(emitter -> {
            Map<String,Object> data = new HashMap<>();
            data.put("feelback",feedback);
            db.collection(Constant.REFLECT).document(idReflect).update(data).addOnSuccessListener(new OnSuccessListener<Void>() {
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

}
