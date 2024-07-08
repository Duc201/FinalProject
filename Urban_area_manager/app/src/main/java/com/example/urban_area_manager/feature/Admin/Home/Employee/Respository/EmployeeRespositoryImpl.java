package com.example.urban_area_manager.feature.Admin.Home.Employee.Respository;

import android.graphics.Bitmap;
import android.net.Uri;

import androidx.annotation.NonNull;

import com.example.urban_area_manager.feature.Admin.Home.Employee.Model.Employee;
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
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;

public class EmployeeRespositoryImpl implements EmployeeRespository {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageRef = storage.getReference();


    @Override
    public Single<List<Employee>> getlistEmployee(int department) {
        return Single.create(emitter ->
                db.collection(Constant.EMPLOYEE).whereEqualTo("delete",false)
                        .whereEqualTo(Constant.DEPARTMENT, department)
                        .get()
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                List<Employee> employeeList = new ArrayList<>();
                                for (QueryDocumentSnapshot doc : task.getResult()) {
                                    Employee employee = doc.toObject(Employee.class);
                                    employeeList.add(employee);
                                }
                                emitter.onSuccess(employeeList);
                            } else {
                                emitter.onError(task.getException());
                            }
                        })
        );
    }

    //    @Override
//    public Maybe<List<Employee>> getlistEmployee(int department) {
//        return Maybe.create(emitter ->
//                db.collection("Employee")
//                        .whereEqualTo("department", department)
//                        .addSnapshotListener(new EventListener<QuerySnapshot>() {
//                            @Override
//                            public void onEvent(@Nullable QuerySnapshot value,
//                                                @Nullable FirebaseFirestoreException e) {
//                                if (e != null) {
//                                    emitter.onError(e);
//                                    return;
//                                }
//
//                                List<Employee> employeeList = new ArrayList<>();
//                                for (QueryDocumentSnapshot doc : value) {
//                                    Employee employee = doc.toObject(Employee.class);
//                                    if (employee != null) {
//                                        employeeList.add(employee);
//                                    }
//                                }
//                                emitter.onSuccess(employeeList);
//                            }
//                        })
//                );
//    }
    @Override
    public Single<Uri> uploadImage(Bitmap data) {
        return Single.create(emitter -> {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            data.compress(Bitmap.CompressFormat.JPEG, 30, baos);
            byte[] data1 = baos.toByteArray();
            String fileName = UUID.randomUUID().toString();
            final StorageReference ref = storageRef.child("images/" + fileName);
            UploadTask uploadTask = ref.putBytes(data1);

            Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                @Override
                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                    if (!task.isSuccessful()) {
                        throw task.getException();
                    }

                    // Continue with the task to get the download URL
                    return ref.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if (task.isSuccessful()) {
                        Uri downloadUri = task.getResult();
                        emitter.onSuccess(downloadUri);
                    } else {
                        // Handle failures
                        // ...
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
    public Single<Boolean> deleteEmployee(Employee employee) {
        return Single.create(emitter -> {
            employee.setLastModificationTime(Timestamp.now().toDate());
            db.collection("Employee").document(employee.getId()).set(employee).addOnSuccessListener(new OnSuccessListener<Void>() {
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

    public Single<Boolean> deleteImageAndEmployee(String imageUri, Employee employee) {
        Single<Boolean> deleteImageSingle = deleteImage(imageUri);
        Single<Boolean> deleteEmployeeSingle = deleteEmployee(employee);

        return Single.zip(
                        deleteImageSingle,
                        deleteEmployeeSingle,
                        (imageDeleted, employeeDeleted) -> imageDeleted && employeeDeleted
                )
                .subscribeOn(Schedulers.io());
    }


    @Override
    public Completable uploadEmloyee(Employee employee) {
        return Completable.create(emitter -> {
            String id = UUID.randomUUID().toString();
            employee.setCreationTime(Timestamp.now().toDate());
            employee.setLastModificationTime(Timestamp.now().toDate());
            employee.setId(id);
            db.collection("Employee").document(id).set(employee).addOnSuccessListener(new OnSuccessListener<Void>() {
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
    public Completable updateEmloyee(Employee employee) {
        return Completable.create(emitter -> {
            employee.setLastModificationTime(Timestamp.now().toDate());
            db.collection("Employee").document(employee.getId()).set(employee).addOnSuccessListener(new OnSuccessListener<Void>() {
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
//    @Override
//    public Single<StorageReference> uploadImage(Uri data) {
//        return Single.create(emitter -> {
//            String fileName = UUID.randomUUID().toString();
//            StorageReference ref = storageRef.child("images/" + fileName);
//            storageRef.putFile(data).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//                @Override
//                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                        emitter.onSuccess(ref);
//                }
//            }).addOnFailureListener(new OnFailureListener() {
//                @Override
//                public void onFailure(@NonNull Exception e) {
//                        emitter.onError(e);
//                }
//            });
//
//        });
//    }

//    @Override
//    public Single<String> getlinkImage(StorageReference ref) {
//        return Single.create(emitter -> {
////            Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
////                @Override
////                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
////                    if (!task.isSuccessful()) {
////                        throw task.getException();
////                    }
////
////                    // Continue with the task to get the download URL
////                    return ref.getDownloadUrl();
////                }
////            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
////                @Override
////                public void onComplete(@NonNull Task<Uri> task) {
////                    if (task.isSuccessful()) {
////                        Uri downloadUri = task.getResult();
////                    } else {
////
////                    }
////                }
////            });
//
//        });
//    }


}
