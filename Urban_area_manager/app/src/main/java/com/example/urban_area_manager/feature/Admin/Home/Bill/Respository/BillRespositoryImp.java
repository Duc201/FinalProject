package com.example.urban_area_manager.feature.Admin.Home.Bill.Respository;

import androidx.annotation.NonNull;

import com.example.urban_area_manager.feature.Admin.Home.Bill.Model.Bill;
import com.example.urban_area_manager.feature.Admin.Home.Bill.Model.DetailsBillStep;
import com.example.urban_area_manager.feature.Admin.Home.Bill.Model.DetailsBillFix;
import com.example.urban_area_manager.feature.Admin.Home.Bill.Model.DetailsService;
import com.example.urban_area_manager.feature.Admin.Home.Bill.Model.FixedService;
import com.example.urban_area_manager.feature.Admin.Home.Bill.Model.Step;
import com.example.urban_area_manager.feature.Admin.Home.Bill.Model.StepService;
import com.example.urban_area_manager.feature.Admin.Home.Building.Model.Building;
import com.example.urban_area_manager.utils.Constant;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import io.reactivex.Completable;
import io.reactivex.Single;

public class BillRespositoryImp implements BillRespository{
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageRef = storage.getReference();


    @Override
    public Single<List<Building>> getlistBuilding() {
        return Single.create(emitter ->
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
    public Completable addService(FixedService fixedService) {
        return Completable.create(emitter -> {
            db.collection(Constant.SERVICE).document(fixedService.getId()).set(fixedService).addOnSuccessListener(new OnSuccessListener<Void>() {
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
    public Completable addDetailsService(DetailsService detailsService) {
        return Completable.create(emitter -> {
            db.collection(Constant.DETAILSERVICE).document(detailsService.getId()).set(detailsService).addOnSuccessListener(new OnSuccessListener<Void>() {
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
    public Completable deleteService(FixedService fixedService) {
        return Completable.create(emitter -> {
            db.collection(Constant.SERVICE).document(fixedService.getId()).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
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
    public Completable updateBill(Bill bill) {
        return Completable.create(emitter -> {
            db.collection(Constant.BILL).document(bill.getIdBill()).set(bill).addOnSuccessListener(new OnSuccessListener<Void>() {
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



    public Completable deleteStepService(StepService stepService) {
        return Completable.create(emitter -> {
            db.collection(Constant.STEPSERVICE).document(stepService.getId()).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
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
//    public Completable deleteStepServiceAll(StepService stepService) {
//        return getListStep(stepService.getId()) // Lấy danh sách các Step liên quan đến StepService
//                .flatMapCompletable(steps -> removeListStep(steps)) // Xóa các Step liên quan
//                .andThen(deleteStepService(stepService)); // Xóa StepService sau khi các Step đã được xóa
//    }

    public Completable deleteStepServiceAll(StepService stepService) {
        return getListStep(stepService.getId()) // Lấy danh sách các Step liên quan đến StepService
                .flatMapCompletable(steps -> {
                    if (steps.isEmpty()) {
                        // Nếu không có bước nào, chỉ cần trả về Completable.complete()
                        return Completable.complete();
                    } else {
                        // Xóa các Step liên quan
                        return removeListStep(steps);
                    }
                })
                .andThen(deleteStepService(stepService)); // Xóa StepService sau khi các Step đã được xóa hoặc không có bước nào
    }
    @Override
    public Single<List<StepService>> getListStepService() {
        return Single.create(emitter ->
                db.collection(Constant.STEPSERVICE)
                        .get()
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                List<StepService> StepServiceList = new ArrayList<>();
                                for (QueryDocumentSnapshot doc : task.getResult()) {
                                    StepService stepService = doc.toObject(StepService.class);
                                    StepServiceList.add(stepService);
                                }
                                emitter.onSuccess(StepServiceList);
                            } else {
                                emitter.onError(task.getException());
                            }
                        })
        );
    }

    @Override
    public Single<List<DetailsService>> getlistDetailsService(String idApartment) {
        return Single.create(emitter ->
                db.collection(Constant.DETAILSERVICE)
                        .whereEqualTo("idApartment", idApartment)
                        .whereEqualTo("delete", false)
                        .get()
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful() && task.getResult() != null) {
                                List<DetailsService> detailsServiceList = new ArrayList<>();
                                for (QueryDocumentSnapshot doc : task.getResult()) {
                                    DetailsService detailsService = doc.toObject(DetailsService.class);
                                    detailsServiceList.add(detailsService);
                                }
                                emitter.onSuccess(detailsServiceList);
                            } else {
                                emitter.onError(new Exception("No results found for the specified query."));
                            }
                        })
                        .addOnFailureListener(emitter::onError)
        );
    }

    public Single<List<DetailsService>> getlistDetailsServiceNotPay(String idApartment) {
        return Single.create(emitter ->
                db.collection(Constant.DETAILSERVICE)
                        .whereEqualTo("idApartment", idApartment)
                        .whereEqualTo("delete", true)
                        .whereEqualTo("pay", false)
                        .get()
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful() && task.getResult() != null) {
                                List<DetailsService> detailsServiceList = new ArrayList<>();
                                for (QueryDocumentSnapshot doc : task.getResult()) {
                                    DetailsService detailsService = doc.toObject(DetailsService.class);
                                    detailsServiceList.add(detailsService);
                                }
                                emitter.onSuccess(detailsServiceList);
                            } else {
                                emitter.onError(new Exception("No results found for the specified query."));
                            }
                        })
                        .addOnFailureListener(emitter::onError)
        );
    }

    @Override
    public Single<List<DetailsService>> getListDetailsServiceForBill(String idApartment) {
        Single<List<DetailsService>> listDetailsService = getlistDetailsService(idApartment)
                .onErrorResumeNext(throwable -> Single.just(Collections.emptyList()));
        Single<List<DetailsService>> listDetailsServiceNotPay = getlistDetailsServiceNotPay(idApartment)
                .onErrorResumeNext(throwable -> Single.just(Collections.emptyList()));

        return Single.zip(listDetailsService, listDetailsServiceNotPay, (services, notPaidServices) -> {
            List<DetailsService> combinedList = new ArrayList<>();
            combinedList.addAll(services);
            combinedList.addAll(notPaidServices);
            return combinedList;
        });
    }


    @Override
    public Single<List<DetailsBillStep>> getListStepServiceApartment(String idApartment, int month, int year) {
        return Single.create(emitter ->
                db.collection(Constant.DETAILSBILL)
                        .whereEqualTo("idApartment",idApartment)
                        .whereEqualTo("month",month)
                        .whereEqualTo("year",year)
                        .whereEqualTo("deleted",false)
                        .get()
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                List<DetailsBillStep> detailsBillStepList = new ArrayList<>();
                                for (QueryDocumentSnapshot doc : task.getResult()) {
                                    DetailsBillStep detailsBilleStep = doc.toObject(DetailsBillStep.class);
                                    detailsBillStepList.add(detailsBilleStep);
                                }
                                if (detailsBillStepList.isEmpty()) {
                                    emitter.onError(new Exception("No results found for the specified query."));
                                } else {
                                    emitter.onSuccess(detailsBillStepList);
                                }
                            } else {
                                emitter.onError(task.getException());
                            }
                        })
        );
    }
    @Override
    public Completable checkCreatBill(String idApartment, int month, int year) {
        return Completable.create(emitter ->
                db.collection(Constant.BILL)
                        .whereEqualTo("idApartment",idApartment)
                        .whereEqualTo("month",month)
                        .whereEqualTo("year",year)
                        .whereEqualTo("deleted",false)
                        .get()
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                List<DetailsBillStep> detailsBillStepList = new ArrayList<>();
                                for (QueryDocumentSnapshot doc : task.getResult()) {
                                    DetailsBillStep detailsBilleStep = doc.toObject(DetailsBillStep.class);
                                    detailsBillStepList.add(detailsBilleStep);
                                }
                                if (detailsBillStepList.isEmpty()) {
                                    emitter.onComplete();
                                } else {
                                    emitter.onError(new Exception("No results found for the specified query."));
                                }
                            } else {
                                emitter.onError(task.getException());
                            }
                        })
        );
    }
    @Override
    public Single<List<DetailsBillStep>> getListStepServiceUser(String idBill) {
        return Single.create(emitter ->
                db.collection(Constant.DETAILSBILL)
                        .whereEqualTo("idBill",idBill)
                        .whereEqualTo("deleted",false)
                        .get()
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                List<DetailsBillStep> detailsBillStepList = new ArrayList<>();
                                for (QueryDocumentSnapshot doc : task.getResult()) {
                                    DetailsBillStep detailsBilleStep = doc.toObject(DetailsBillStep.class);
                                    detailsBillStepList.add(detailsBilleStep);
                                }
                                if (detailsBillStepList.isEmpty()) {
                                    emitter.onError(new Exception("No results found for the specified query."));
                                } else {
                                    emitter.onSuccess(detailsBillStepList);
                                }
                            } else {
                                emitter.onError(task.getException());
                            }
                        })
        );
    }

    @Override
    public Single<List<DetailsBillFix>> getListDetailBillFix(String idApartment, int month, int year) {
        return Single.create(emitter ->
                db.collection(Constant.DETAILSBILLFIX)
                        .whereEqualTo("idApartment",idApartment)
                        .whereEqualTo("month",month)
                        .whereEqualTo("year",year)
                        .get()
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                List<DetailsBillFix> DetailsBillList = new ArrayList<>();
                                for (QueryDocumentSnapshot doc : task.getResult()) {
                                    DetailsBillFix detailsBille = doc.toObject(DetailsBillFix.class);
                                    DetailsBillList.add(detailsBille);
                                }
                                if (DetailsBillList.isEmpty()) {
                                    emitter.onError(new Exception("No results found for the specified query."));
                                } else {
                                    emitter.onSuccess(DetailsBillList);
                                }
                            } else {
                                emitter.onError(task.getException());
                            }
                        })
        );
    }


    public Single<List<DetailsBillFix>> getListFixedServiceApartment(String idBill) {
        return Single.create(emitter ->
                db.collection(Constant.DETAILSBILLFIX)
                        .whereEqualTo("idBill",idBill)
//                        .whereEqualTo("deleted",false)
                        .get()
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                List<DetailsBillFix> DetailsBillList = new ArrayList<>();
                                for (QueryDocumentSnapshot doc : task.getResult()) {
                                    DetailsBillFix detailsBille = doc.toObject(DetailsBillFix.class);
                                    DetailsBillList.add(detailsBille);
                                }
                                if (DetailsBillList.isEmpty()) {
                                    emitter.onError(new Exception("No results found for the specified query."));
                                } else {
                                    emitter.onSuccess(DetailsBillList);
                                }
                            } else {
                                emitter.onError(task.getException());
                            }
                        })
        );
    }
//    public Single<List<DetailsBillFix>> getListFixedServiceApartment2(String idApartment) {
//        return Single.create(emitter ->
//                db.collection(Constant.DETAILSERVICEFIXED)
//                        .whereEqualTo("idService",true)
//                        .whereEqualTo("idApartment",idApartment)
//                        .get()
//                        .addOnCompleteListener(task -> {
//                            if (task.isSuccessful()) {
//                                List<DetailsBillFix> DetailsBillList = new ArrayList<>();
//                                for (QueryDocumentSnapshot doc : task.getResult()) {
//                                    DetailsBillFix detailsBille = doc.toObject(DetailsBillFix.class);
//                                    DetailsBillList.add(detailsBille);
//                                }
//                                if (DetailsBillList.isEmpty()) {
//                                    emitter.onSuccess(DetailsBillList);
//                                } else {
//                                    emitter.onSuccess(DetailsBillList);
//                                }
//                            } else {
//                                emitter.onError(task.getException());
//                            }
//                        })
//        );
//    }
//    @Override
//    public Single<List<DetailsBillFix>> getCombinedFixedServiceApartments(String idApartment, int month, int year) {
//        return Single.zip(
//                getListFixedServiceApartment(idApartment, month, year),
//                getListFixedServiceApartment2(idApartment),
//                (list1, list2) -> {
//                    List<DetailsBillFix> combinedList = new ArrayList<>();
//                    combinedList.addAll(list1);
//                    combinedList.addAll(list2);
//                    return combinedList;
//                }
//        );
//    }

//    @Override
//    public Single<List<DetailsBillFix>> getListFixedServiceResident(String idBill, String idApartment) {
//        return Single.zip(
//                getListFixedServiceApartment(idBill),
//                getListFixedServiceApartment2(idApartment),
//                (list1, list2) -> {
//                    List<DetailsBillFix> combinedList = new ArrayList<>();
//                    combinedList.addAll(list1);
//                    combinedList.addAll(list2);
//                    return combinedList;
//                }
//        );
//    }

    @Override
    public Single<StepService> getStepService(String idService) {
        return Single.create(emitter ->
                db.collection(Constant.STEPSERVICE)
                        .whereEqualTo("id",idService)
                        .get()
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot doc : task.getResult()) {
                                    StepService stepService = doc.toObject(StepService.class);
                                    emitter.onSuccess(stepService);
                                }
                            } else {
                                emitter.onError(task.getException());
                            }
                        })
        );
    }

    @Override
    public Single<FixedService> getFixedService(String idService) {
        return Single.create(emitter ->
                db.collection(Constant.SERVICE)
                        .whereEqualTo("id",idService)
                        .get()
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot doc : task.getResult()) {
                                    FixedService fixedService = doc.toObject(FixedService.class);
                                    emitter.onSuccess(fixedService);
                                }
                            } else {
                                emitter.onError(task.getException());
                            }
                        })
        );
    }

@Override
public Single<DetailsBillStep> getDetailServiceLast(String idApartment, String idService, int month, int year) {
    return Single.create(emitter ->
            db.collection(Constant.DETAILSBILL)
                    .whereEqualTo("idApartment",idApartment)
                    .whereEqualTo("idService",idService)
                    .whereEqualTo("month",month)
                    .whereEqualTo("year",year)
                    .get()
                    .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                        @Override
                        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                            if (!queryDocumentSnapshots.isEmpty()) {
                                DetailsBillStep detailsBillStep = queryDocumentSnapshots.getDocuments().get(0).toObject(DetailsBillStep.class);
                                emitter.onSuccess(detailsBillStep);
                            } else {
                                emitter.onError(new Exception("No details found"));
                            }
                        }
                    }).addOnFailureListener(emitter::onError)
    );
}

    @Override
    public Single<List<DetailsBillStep>> getUniqueApartments(int month, int year,String idBuilding) {
        return Single.create(emitter ->
                db.collection(Constant.DETAILSBILL)
                        .whereEqualTo("month",month)
                        .whereEqualTo("year",year)
                        .whereEqualTo("idBuilding",idBuilding)
                        .get()
                        .addOnSuccessListener(queryDocumentSnapshots -> {
                            Set<String> uniqueApartments = new HashSet<>();
                            List<DetailsBillStep> uniqueDetailsBillSteps = queryDocumentSnapshots.getDocuments().stream()
                                    .map(document -> document.toObject(DetailsBillStep.class))
                                    .filter(detailsBill -> {
                                        assert detailsBill != null;
                                        return uniqueApartments.add(detailsBill.getIdApartment());
                                    })
                                    .collect(Collectors.toList());

                            emitter.onSuccess(uniqueDetailsBillSteps);
                        })
                        .addOnFailureListener(emitter::onError)
        );
    }

    @Override
    public Single<List<Step>> getListStep(String id) {
        return Single.create(emitter -> {
            db.collection(Constant.STEP).whereEqualTo("idServiceStep",id)
                    .orderBy("nameStep")
                    .get()
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            List<Step> StepList = new ArrayList<>();
                            for (QueryDocumentSnapshot doc : task.getResult()) {
                                Step detailsBill = doc.toObject(Step.class);
                                StepList.add(detailsBill);
                            }
                            if (StepList.isEmpty()) {
                                emitter.onError(new Exception("No results found for the specified query."));
                            } else {
                                emitter.onSuccess(StepList);
                            }
                        } else {
                            emitter.onError(task.getException());
                        }
                    });
        });
    }
    public Completable removeStep(Step step) {
        return Completable.create(emitter -> {
            db.collection(Constant.STEP).document(step.getIdStep()).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
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
    public Completable removeListStep(List<Step> steps) {
        List<Completable> deleteTasks = new ArrayList<>();
        for (Step step : steps) {
            deleteTasks.add(removeStep(step));
        }
        return Completable.merge(deleteTasks);
    }

    public Completable addDettailsBillFix(DetailsBillFix detailsBillFix) {
        return Completable.create(emitter -> {
            db.collection(Constant.DETAILSBILLFIX).document(detailsBillFix.getId()).set(detailsBillFix).addOnSuccessListener(new OnSuccessListener<Void>() {
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
    public Completable addListDettailsBillFix(List<DetailsBillFix> mlist) {
        List<Completable> completableList = new ArrayList<>();
        for (DetailsBillFix detailsBillFix : mlist) {
            completableList.add(addDettailsBillFix(detailsBillFix));
        }
        return Completable.concat(completableList);
    }


    public Completable addStep(Step step) {
        return Completable.create(emitter -> {
            db.collection(Constant.STEP).document(step.getIdStep()).set(step).addOnSuccessListener(new OnSuccessListener<Void>() {
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
    public Completable addStepService(StepService stepService) {
        return Completable.create(emitter -> {
            db.collection(Constant.STEPSERVICE).document(stepService.getId()).set(stepService).addOnSuccessListener(new OnSuccessListener<Void>() {
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
    public Completable addAllStepService(StepService stepService, List<Step> stepList) {
        List<Completable> completables = new ArrayList<>();

        Completable stepServiceCompletable = addStepService(stepService);
        completables.add(stepServiceCompletable);

        for (Step step : stepList) {
            completables.add(addStep(step));
        }

        return Completable.merge(completables);
    }


    @Override
    public Single<List<FixedService>> getListService() {
        return Single.create(emitter ->
                db.collection(Constant.SERVICE)
                        .get()
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                List<FixedService> fixedServiceList = new ArrayList<>();
                                for (QueryDocumentSnapshot doc : task.getResult()) {
                                    FixedService fixedService = doc.toObject(FixedService.class);
                                    fixedServiceList.add(fixedService);
                                }
                                emitter.onSuccess(fixedServiceList);
                            } else {
                                emitter.onError(task.getException());
                            }
                        })
        );
    }

    @Override
    public Single<List<FixedService>> getListService(int type) {
        return Single.create(emitter ->
                db.collection(Constant.SERVICE).whereEqualTo("type",type)
                        .get()
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                List<FixedService> fixedServiceList = new ArrayList<>();
                                for (QueryDocumentSnapshot doc : task.getResult()) {
                                    FixedService fixedService = doc.toObject(FixedService.class);
                                    fixedServiceList.add(fixedService);
                                }
                                emitter.onSuccess(fixedServiceList);
                            } else {
                                emitter.onError(task.getException());
                            }
                        })
        );
    }

    @Override
    public Single<List<Bill>> getListBill(String idBuilding, int month , int year) {
        return Single.create(emitter ->{

            db.collection(Constant.BILL)
                .whereEqualTo("idBuilding",idBuilding)
                    .whereEqualTo("month",month)
                    .whereEqualTo("year",year)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        List<Bill> BillList = new ArrayList<>();
                        for (QueryDocumentSnapshot doc : task.getResult()) {
                            Bill detailsBille = doc.toObject(Bill.class);
                            BillList.add(detailsBille);
                        }
                        if (BillList.isEmpty()) {
                            emitter.onError(new Exception("No results found for the specified query."));
                        } else {
                            emitter.onSuccess(BillList);
                        }
                    } else {
                        emitter.onError(task.getException());
                    }
                });
        });
    }

    @Override
    public Single<List<Bill>> getBillUser(String idApartment) {
        return Single.create(emitter -> {
            db.collection(Constant.BILL)
                    .whereEqualTo("idApartment",idApartment)
                    .whereEqualTo("deleted",false)
                    .whereEqualTo("notifi",true)
                    .get()
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            List<Bill> BillList = new ArrayList<>();
                            for (QueryDocumentSnapshot doc : task.getResult()) {
                                Bill detailsBill = doc.toObject(Bill.class);
                                BillList.add(detailsBill);
                            }
                            if (BillList.isEmpty()) {
                                emitter.onError(new Exception("No results found for the specified query."));
                            } else {
                                emitter.onSuccess(BillList);
                            }
                        } else {
                            emitter.onError(task.getException());
                        }
                    });
        });

    }

    public Completable addBill(Bill bill){
        return Completable.create(emitter -> {
            db.collection(Constant.BILL).document(bill.getIdBill()).set(bill).addOnSuccessListener(new OnSuccessListener<Void>() {
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
    public Completable addElectricBill(DetailsBillStep electricBill){
        return Completable.create(emitter -> {
            db.collection(Constant.DETAILSBILL).document(electricBill.getId()).set(electricBill).addOnSuccessListener(new OnSuccessListener<Void>() {
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
    public Completable addWaterBill(DetailsBillStep waterBill){
        return Completable.create(emitter -> {
            db.collection(Constant.DETAILSBILL).document(waterBill.getId()).set(waterBill).addOnSuccessListener(new OnSuccessListener<Void>() {
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
//    public Completable addManagerBill(DetailsBillFix managerBill){
//        return Completable.create(emitter -> {
//            db.collection(Constant.DETAILSBILLFIX).document(managerBill.getId()).set(managerBill).addOnSuccessListener(new OnSuccessListener<Void>() {
//                @Override
//                public void onSuccess(Void unused) {
//                    emitter.onComplete();
//                }
//            }).addOnFailureListener(new OnFailureListener() {
//                @Override
//                public void onFailure(@NonNull Exception e) {
//                    emitter.onError(e);
//                }
//            });
//        });
//    }
    @Override
    public Completable addDetailBillEmployee(DetailsBillStep electricBill, DetailsBillStep waterBill, Bill bill) {
        return Completable.mergeArray(
                addBill(bill),
                addElectricBill(electricBill),
                addWaterBill(waterBill)
//                addManagerBill(managerBill)

        );
    }

    @Override
    public Completable addDetailBillEmployeeEdit(DetailsBillStep electricBill, DetailsBillStep waterBill) {
        return Completable.mergeArray(
                addElectricBill(electricBill),
                addWaterBill(waterBill)
        );
    }

}
