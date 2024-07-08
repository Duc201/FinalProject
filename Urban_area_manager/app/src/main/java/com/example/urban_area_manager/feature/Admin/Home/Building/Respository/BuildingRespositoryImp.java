package com.example.urban_area_manager.feature.Admin.Home.Building.Respository;

import android.graphics.Bitmap;
import android.net.Uri;

import androidx.annotation.NonNull;

import com.example.urban_area_manager.feature.Admin.Home.Building.Model.Building;
import com.example.urban_area_manager.utils.Constant;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import io.reactivex.Completable;
import io.reactivex.Maybe;
import io.reactivex.Single;

public class BuildingRespositoryImp implements BuildingRespository{
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageRef = storage.getReference();
    @Override
    public Maybe<List<Building>> getlistBuilding() {
        return Maybe.create(emitter ->
                db.collection(Constant.BUILDING)
                        .whereEqualTo("deleted",false)
                        .get()
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                List<Building> buildingList = new ArrayList<>();
                                for (QueryDocumentSnapshot doc : task.getResult()) {
                                    Building building = doc.toObject(Building.class);
                                    buildingList.add(building);
                                }
                                emitter.onSuccess(buildingList);
                            } else {
                                emitter.onError(task.getException());
                            }
                        })
        );
    }

    @Override
    public Single<Uri> uploadImage(Bitmap image) {
        return Single.create(emitter -> {

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            image.compress(Bitmap.CompressFormat.JPEG, 70, baos);
            byte[] data = baos.toByteArray();
            String fileName = UUID.randomUUID().toString();
            final StorageReference imageRef = storageRef.child("images/" + fileName);


            UploadTask uploadTask = imageRef.putBytes(data);
            uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                @Override
                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                    if (!task.isSuccessful()) {
                        throw task.getException();
                    }

                    // Continue with the task to get the download URL
                    return imageRef.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if (task.isSuccessful()) {
                        Uri downloadUri = task.getResult();
                        emitter.onSuccess(downloadUri);
                    } else {
                        if (!emitter.isDisposed()) {
                            emitter.onError(task.getException());
                        }
                    }
                }
            });

        });
    }

    @Override
    public Single<Boolean> deleteImage(String uri) {
        return Single.create(emitter -> {
            // Chuyển đổi đường dẫn URI thành StorageReference
            StorageReference storageRef = FirebaseStorage.getInstance().getReferenceFromUrl(uri);

            // Xóa tệp ảnh
            storageRef.delete()
                    .addOnSuccessListener(aVoid -> {
                        emitter.onSuccess(true);
                    })
                    .addOnFailureListener(exception -> {
                        emitter.onError(exception);
                    });
        });
    }

    @Override
    public Single<Boolean> deleteBuilding(Building building) {
        return Single.create(emitter -> {
            building.setLastModificationTime(Timestamp.now().toDate());
            db.collection(Constant.BUILDING).document(building.getIdBuilding()).set(building).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {
                    emitter.onSuccess(true);
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
    public Single<Boolean> deleteImageAndBuilding(String uri, Building building) {
        Single<Boolean> deleteImageSingle = deleteImage(uri);
        Single<Boolean> deleteEmployeeSingle = deleteBuilding(building);

        return Single.zip(
                        deleteImageSingle,
                        deleteEmployeeSingle,
                        (imageDeleted, employeeDeleted) -> imageDeleted && employeeDeleted
                );
    }

    @Override
    public Completable uploadBuilding(Building building) {
        return Completable.create(emitter -> {
            String id = UUID.randomUUID().toString();
            building.setCreationTime(Timestamp.now().toDate());
            building.setLastModificationTime(Timestamp.now().toDate());
            building.setIdBuilding(id);
            db.collection(Constant.BUILDING).document(id).set(building).addOnSuccessListener(new OnSuccessListener<Void>() {
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
    public Completable updateBuilding(Building building) {
        return Completable.create(emitter -> {
            db.collection(Constant.BUILDING).document(building.getIdBuilding()).set(building).addOnSuccessListener(new OnSuccessListener<Void>() {
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
