package com.example.urban_area_manager.feature.Admin.Home.Apartment.Respository;

import android.graphics.Bitmap;
import android.net.Uri;

import androidx.annotation.NonNull;

import com.example.urban_area_manager.feature.Admin.Home.Apartment.Model.Apartment;
import com.example.urban_area_manager.feature.Admin.Home.Resident.Model.DetailsResident;
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
import io.reactivex.schedulers.Schedulers;

public class ApartmentRespositoryImp implements ApartmentRespository {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageRef = storage.getReference();
    @Override
    public Maybe<List<Apartment>> getlistApartment(String idBuilding) {
        return Maybe.create(emitter ->
                db.collection(Constant.APARTMENT)
                        .whereEqualTo("idBuilding",idBuilding)
                        .whereEqualTo("deleted",false)
                        .get()
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                List<Apartment> apartmentList = new ArrayList<>();
                                for (QueryDocumentSnapshot doc : task.getResult()) {
                                    Apartment apartment = doc.toObject(Apartment.class);
                                    apartmentList.add(apartment);
                                }
                                emitter.onSuccess(apartmentList);
                            } else {
                                emitter.onError(task.getException());
                            }
                        })
        );
    }

    @Override
    public Maybe<List<Apartment>> getlistApartmentSold(String idBuilding) {
        return Maybe.create(emitter ->
                db.collection(Constant.APARTMENT)
                        .whereEqualTo("idBuilding",idBuilding)
                        .whereEqualTo("state",0)
                        .whereEqualTo("deleted",false)
                        .get()
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                List<Apartment> apartmentList = new ArrayList<>();
                                for (QueryDocumentSnapshot doc : task.getResult()) {
                                    Apartment apartment = doc.toObject(Apartment.class);
                                    apartmentList.add(apartment);
                                }
                                emitter.onSuccess(apartmentList);
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
                    return imageRef.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if (task.isSuccessful()) {
                        Uri downloadUri = task.getResult();
                        if (!emitter.isDisposed()) {
                            emitter.onSuccess(downloadUri);
                        }
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
    public Completable deleteImage(String uri) {
        return Completable.create(emitter -> {
            // Chuyển đổi đường dẫn URI thành StorageReference
            StorageReference storageRef = FirebaseStorage.getInstance().getReferenceFromUrl(uri);

            // Xóa tệp ảnh
            storageRef.delete()
                    .addOnSuccessListener(aVoid -> emitter.onComplete())
                    .addOnFailureListener(emitter::onError);
        });
    }

    @Override
    public Completable deleteApartment(Apartment apartment) {
        return Completable.create(emitter -> {
            db.collection(Constant.APARTMENT).document(apartment.getIdApartment()).set(apartment)
                    .addOnSuccessListener(unused -> emitter.onComplete())
                    .addOnFailureListener(emitter::onError);
        });
    }

    @Override
    public Completable deleteImageAndApartment(String uri, Apartment apartment) {
        Completable deleteImageCompletable = deleteImage(uri);
        Completable deleteApartmentCompletable = deleteApartment(apartment);

        return Completable.mergeArray(deleteImageCompletable, deleteApartmentCompletable)
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Completable uploadApartment(Apartment Apartment) {
        return Completable.create(emitter -> {
            String id = UUID.randomUUID().toString();
            Apartment.setCreationTime(Timestamp.now().toDate());
            Apartment.setLastModificationTime(Timestamp.now().toDate());
            Apartment.setIdApartment(id);
            db.collection(Constant.APARTMENT).document(id).set(Apartment).addOnSuccessListener(new OnSuccessListener<Void>() {
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
    public Completable updateBApartment(Apartment apartment) {
        return Completable.create(emitter -> {
            db.collection(Constant.APARTMENT).document(apartment.getIdApartment()).set(apartment).addOnSuccessListener(new OnSuccessListener<Void>() {
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
    public Maybe<List<String>> getlistBuilding() {
        return Maybe.create(emitter ->
                db.collection(Constant.BUILDING)
                        .get()
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                List<String> buildingList = new ArrayList<>();
                                for (QueryDocumentSnapshot doc : task.getResult()) {
                                    String name = doc.getString("nameBuilding");
                                    buildingList.add(name);
                                }
                                emitter.onSuccess(buildingList);
                            } else {
                                emitter.onError(task.getException());
                            }
                        })
        );
    }

    @Override
    public Single<String> getIDBuilding(String nameBuilding) {
        return Single.create(emitter ->
                db.collection(Constant.BUILDING)
                        .whereEqualTo("nameBuilding",nameBuilding)
                        .get()
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot doc : task.getResult()) {
                                    String id = doc.getString("idBuilding");
                                    emitter.onSuccess(id);
                                }
                            } else {
                                emitter.onError(task.getException());
                            }
                        })
        );
    }

    @Override
    public Maybe<List<DetailsResident>> getlistResident(String idApartment) {
        return Maybe.create(emitter ->
                db.collection(Constant.DETAILSRESIDENT)
                        .whereEqualTo("apartmentCode", idApartment)
                        .get()
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                List<DetailsResident> detailsResidentlist = new ArrayList<>();
                                for (QueryDocumentSnapshot doc : task.getResult()) {
                                    DetailsResident detailsResident = doc.toObject(DetailsResident.class);
                                    detailsResidentlist.add(detailsResident);
                                }
                                emitter.onSuccess(detailsResidentlist);
                            } else {
                                emitter.onError(task.getException());
                            }
                        })
        );
    }

}
