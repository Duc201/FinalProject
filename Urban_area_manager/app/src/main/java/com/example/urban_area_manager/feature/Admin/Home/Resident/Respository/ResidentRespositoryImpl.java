package com.example.urban_area_manager.feature.Admin.Home.Resident.Respository;

import androidx.annotation.NonNull;

import com.example.urban_area_manager.feature.Admin.Home.Resident.Model.DetailsResident;
import com.example.urban_area_manager.feature.Admin.Home.Resident.Model.Resident;
import com.example.urban_area_manager.utils.Constant;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import io.reactivex.Completable;
import io.reactivex.Single;

public class ResidentRespositoryImpl implements ResidentRespository {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageRef = storage.getReference();

    @Override
    public Single<List<Resident>> getListResidentAuthen() {
        return Single.create(emitter ->
                db.collection(Constant.RESIDENT)
                        .whereEqualTo("delete",false)
                        .whereEqualTo("state",0)
                        .get()
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                List<Resident> residentList = new ArrayList<>();
                                for (QueryDocumentSnapshot doc : task.getResult()) {
                                    Resident resident = doc.toObject(Resident.class);
                                    residentList.add(resident);
                                }
                                emitter.onSuccess(residentList);
                            } else {
                                emitter.onError(task.getException());
                            }
                        })
        );
    }

    @Override
    public Single<List<Resident>> getListResidentAuthened() {
        return Single.create(emitter ->
                db.collection(Constant.RESIDENT)
                        .whereEqualTo("delete",false)
                        .whereEqualTo("state",1)
                        .get()
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                List<Resident> residentList = new ArrayList<>();
                                for (QueryDocumentSnapshot doc : task.getResult()) {
                                    Resident resident = doc.toObject(Resident.class);
                                    residentList.add(resident);
                                }
                                emitter.onSuccess(residentList);
                            } else {
                                emitter.onError(task.getException());
                            }
                        })
        );
    }
    @Override
    public Single<DetailsResident> getListDetailResident(String idUser, int state) {
        return Single.create(emitter ->
                db.collection(Constant.DETAILSRESIDENT)
                        .whereEqualTo("delete",false)
                        .whereEqualTo("state",state)
                        .whereEqualTo("idUser",idUser)
                        .get()
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot doc : task.getResult()) {
                                    DetailsResident detailsResident = doc.toObject(DetailsResident.class);
                                    emitter.onSuccess(detailsResident);                                }
                                }

                            else {
                                emitter.onError(task.getException());
                            }
                        })
        );
    }

    @Override
    public Single<List<DetailsResident>> getListDetailsResidenInBuilding(String idBuilding) {
        return Single.create(emitter -> {
            db.collection(Constant.DETAILSRESIDENT)
                    .whereEqualTo("buildingCode", idBuilding)
                    .whereEqualTo("delete", false)
                    .get()
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            Set<String> idUserSet = new HashSet<>();
                            List<DetailsResident> uniqueResidents = new ArrayList<>();

                            for (QueryDocumentSnapshot document : task.getResult()) {
                                DetailsResident resident = document.toObject(DetailsResident.class);
                                if (!idUserSet.contains(resident.getIdUser())) {
                                    idUserSet.add(resident.getIdUser());
                                    uniqueResidents.add(resident);
                                }
                            }

                            if (uniqueResidents.isEmpty()) {
                                emitter.onError(new Exception("No residents found in the building"));
                            } else {
                                emitter.onSuccess(uniqueResidents);
                            }
                        } else {
                            emitter.onError(task.getException());
                        }
                    });
        });
    }

    @Override
    public Single<List<DetailsResident>> getListDetailsResiden() {
        return Single.create(emitter -> {
            db.collection(Constant.DETAILSRESIDENT)
                    .whereEqualTo("delete", false) // sử dụng idBuilding thay vì "123"
                    .get()
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            Set<String> idUserSet = new HashSet<>();
                            List<DetailsResident> uniqueResidents = new ArrayList<>();

                            for (QueryDocumentSnapshot document : task.getResult()) {
                                DetailsResident resident = document.toObject(DetailsResident.class);
                                if (!idUserSet.contains(resident.getIdUser())) {
                                    idUserSet.add(resident.getIdUser());
                                    uniqueResidents.add(resident);
                                }
                            }

                            if (uniqueResidents.isEmpty()) {
                                emitter.onError(new Exception("No residents found in the building"));
                            } else {
                                emitter.onSuccess(uniqueResidents);
                            }
                        } else {
                            emitter.onError(task.getException());
                        }
                    });
        });
    }


    @Override
    public Single<List<DetailsResident>> getListDetailsResident(String idUser, int sate) {
        return Single.create(emitter ->
                db.collection(Constant.DETAILSRESIDENT)
                        .whereEqualTo("delete",false)
                        .whereEqualTo("state",sate)
                        .whereEqualTo("idUser",idUser)
                        .get()
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                List<DetailsResident> detailsResidents = new ArrayList<>();
                                for (QueryDocumentSnapshot doc : task.getResult()) {
                                    DetailsResident detailsResident = doc.toObject(DetailsResident.class);
                                    detailsResidents.add(detailsResident);
                            }
                                emitter.onSuccess(detailsResidents);                                }

                            else {
                                emitter.onError(task.getException());
                            }
                        })
        );
    }




    @Override
    public Completable updateStateResident(String idResident, String idDetailsResident, String email, int state) {
       Completable updateStateResident = updateStateResident(idResident,state,email);
       Completable updateStateDetailsResident = updateStateDetailsResident(idDetailsResident,state,email);
        return Completable.mergeArray(updateStateResident, updateStateDetailsResident);
    }

    @Override
    public Single<String> getIdApartment(String idUser) {
        return Single.create(emitter ->
                db.collection(Constant.DETAILSRESIDENT)
                        .whereEqualTo("delete",false)
                        .whereEqualTo("idUser",idUser)
                        .get()
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot doc : task.getResult()) {
                                    DetailsResident detailsResident = doc.toObject(DetailsResident.class);
                                    emitter.onSuccess(detailsResident.getApartmentCode());
                                }
                            }
                            else {
                                emitter.onError(task.getException());
                            }
                        })
        );
    }

    public Completable deleteResident(String idResident){
        return Completable.create(emitter -> {
            db.collection(Constant.RESIDENT).document(idResident).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
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

    public Completable deleteDetailsResident(String idResident) {
        return Completable.create(emitter -> {
            // Lấy collection từ Firestore
            db.collection(Constant.DETAILSRESIDENT)
                    .whereEqualTo("idUser", idResident)
                    .get()
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                String documentId = document.getId();
                                db.collection(Constant.DETAILSRESIDENT)
                                        .document(documentId)
                                        .delete()
                                        .addOnSuccessListener(aVoid -> {
                                            emitter.onComplete();
                                        })
                                        .addOnFailureListener(e -> {
                                            emitter.onError(e);
                                        });
                            }
                        } else {
                            emitter.onError(task.getException());
                        }
                    });
        });
    }

    @Override
    public Completable deleteResidentall(String idResident) {
        return Completable.mergeArray(
                deleteResident(idResident),
                deleteDetailsResident(idResident)
        );
    }

    Completable updateResident(Resident resident){
        return Completable.create(emitter -> {
           db.collection(Constant.RESIDENT).document(resident.getId()).set(resident).addOnSuccessListener(new OnSuccessListener<Void>() {
               @Override
               public void onSuccess(Void unused) {
                    emitter.onComplete();
               }
           }) .addOnFailureListener(new OnFailureListener() {
               @Override
               public void onFailure(@NonNull Exception e) {
                   emitter.onError(e);
               }
           });
        });
    }
    @Override
    public Completable updateDetailsResident(DetailsResident detailsResident){
        return Completable.create(emitter -> {
            db.collection(Constant.DETAILSRESIDENT).document(detailsResident.getId()).set(detailsResident).addOnSuccessListener(new OnSuccessListener<Void>() {
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
    public Completable updateDetailsResident(List<DetailsResident> detailsResidentList) {
        List<Completable> completableList = new ArrayList<>();
        for (DetailsResident detailsResident : detailsResidentList) {
            Completable completable = Completable.create(emitter -> {
                db.collection(Constant.DETAILSRESIDENT).document(detailsResident.getId()).set(detailsResident)
                        .addOnSuccessListener(unused -> emitter.onComplete())
                        .addOnFailureListener(emitter::onError);
            });
            completableList.add(completable);
        }
        return Completable.merge(completableList);
    }
    @Override
    public Completable updateResidentAll(Resident resident, List<DetailsResident> detailsResident) {
        return Completable.mergeArray(
                updateResident(resident),
                updateDetailsResident(detailsResident)
        );
    }

    @Override
    public Single<DetailsResident> getDetailResident(String idResident) {
        return Single.create(emitter -> {
            db.collection(Constant.DETAILSRESIDENT).document(idResident).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if(task.isSuccessful()){
                        DocumentSnapshot documentSnapshot = task.getResult();
                        if(documentSnapshot!=null && documentSnapshot.exists()){
                            DetailsResident detailsResident = documentSnapshot.toObject(DetailsResident.class);
                            emitter.onSuccess(detailsResident);
                        }
                        else
                            emitter.onError(new Exception("No Serach Details Employee"));
                    }
                    else {
                        emitter.onError(task.getException());
                    }
                }
            });
        });
    }

    @Override
    public Single<List<DetailsResident>> getListDetailResidentApartment(String idApartment) {
        return Single.create(emitter ->
                db.collection(Constant.DETAILSRESIDENT)
                        .whereEqualTo("apartmentCode",idApartment)
                        .whereEqualTo("state",1)
                        .get()
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                List<DetailsResident> detailsResidents = new ArrayList<>();
                                for (QueryDocumentSnapshot doc : task.getResult()) {
                                    DetailsResident detailsResident = doc.toObject(DetailsResident.class);
                                    detailsResidents.add(detailsResident);
                                }
                                emitter.onSuccess(detailsResidents);                                }

                            else {
                                emitter.onError(task.getException());
                            }
                        })
        );
    }


    public Completable updateStateResident(String idResident ,int state , String email ) {
        return Completable.create(emitter -> {
            Map<String, Object> updates = new HashMap<>();
            updates.put("state", state);
            updates.put("lastModifierUserId", email);
            updates.put("lastModificationTime", Timestamp.now().toDate());
            db.collection(Constant.RESIDENT).document(idResident)
                    .update(updates)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
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
    public Completable updateStateDetailsResident(String idDetailsResident ,int state , String email ) {
        return Completable.create(emitter -> {
            Map<String, Object> updates = new HashMap<>();
            updates.put("state", state);
            updates.put("lastModifierUserId", email);
            updates.put("lastModificationTime", Timestamp.now().toDate());
            db.collection(Constant.DETAILSRESIDENT).document(idDetailsResident)
                    .update(updates)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
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
