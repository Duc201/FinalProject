package com.example.urban_area_manager.feature.Admin.Home.Camera.Respository;

import androidx.annotation.NonNull;

import com.example.urban_area_manager.feature.Admin.Home.Camera.Model.Camera;
import com.example.urban_area_manager.utils.Constant;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;

public class CameraRespositoryImp implements CameraRespository{
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseStorage storage = FirebaseStorage.getInstance();
    @Override
    public Completable addCamera(Camera camera) {
        return Completable.create(emitter -> {
            db.collection(Constant.CAMERA).document(camera.getId()).set(camera).addOnSuccessListener(new OnSuccessListener<Void>() {
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
    public Completable deleteCamera(Camera camera) {
        return Completable.create(emitter -> {
            db.collection(Constant.CAMERA).document(camera.getId()).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
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
    public Completable editCamera(Camera camera) {
        return Completable.create(emitter -> {
            db.collection(Constant.CAMERA).document(camera.getId()).set(camera).addOnSuccessListener(new OnSuccessListener<Void>() {
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
    public Single<List<Camera>> getListCamera() {
        return Single.create(emitter ->
                db.collection(Constant.CAMERA)
                        .get()
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                List<Camera> CameraList = new ArrayList<>();
                                for (QueryDocumentSnapshot doc : task.getResult()) {
                                    Camera camera1 = doc.toObject(Camera.class);
                                    CameraList.add(camera1);
                                }
                                emitter.onSuccess(CameraList);
                            } else {
                                emitter.onError(task.getException());
                            }
                        })
        );
    }

    @Override
    public Single<List<Camera>> getListCameraPulic() {
        return Single.create(emitter ->
                db.collection(Constant.CAMERA).whereEqualTo("public",true)
                        .get()
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                List<Camera> CameraList = new ArrayList<>();
                                for (QueryDocumentSnapshot doc : task.getResult()) {
                                    Camera camera1 = doc.toObject(Camera.class);
                                    CameraList.add(camera1);
                                }
                                emitter.onSuccess(CameraList);
                            } else {
                                emitter.onError(task.getException());
                            }
                        })
        );
    }


}
