package com.example.urban_area_manager.feature.Auth.respository;

import android.graphics.Bitmap;
import android.net.Uri;
import android.util.Pair;

import androidx.annotation.NonNull;

import com.example.urban_area_manager.feature.Auth.viewmodel.ImageWrapper;
import com.example.urban_area_manager.feature.Admin.Home.Apartment.Model.Apartment;
import com.example.urban_area_manager.feature.Admin.Home.Building.Model.Building;
import com.example.urban_area_manager.feature.Admin.Home.Employee.Model.Employee;
import com.example.urban_area_manager.feature.Admin.Home.Resident.Model.DetailsResident;
import com.example.urban_area_manager.feature.Admin.Home.Resident.Model.Resident;
import com.example.urban_area_manager.utils.Constant;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

import io.reactivex.Completable;
import io.reactivex.Maybe;
import io.reactivex.Observable;
import io.reactivex.Single;

public class AuthRespostoryImpl implements AuthRespository{

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageRef = storage.getReference();
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    @Override
    public Maybe<Employee> login(String email, String password) {
        return Maybe.create(emitter ->
                db.collection("Admin")
                        .whereEqualTo("email", email)
                        .whereEqualTo("pass", password)
                        .get()
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                QuerySnapshot querySnapshot = task.getResult();
                                if (querySnapshot != null && !querySnapshot.isEmpty()) {
                                    for (QueryDocumentSnapshot document : querySnapshot) {
                                        Employee employee = document.toObject(Employee.class);
                                        if (employee != null) {
                                            emitter.onSuccess(employee);
                                            return; // Exit once a match is found
                                        }
                                    }
                                } else {
                                    // No matching document found
                                    emitter.onComplete();
                                }
                            } else {
                                emitter.onError(new Exception("Authentication failed"));
                            }
                        })
        );
    }

    @Override
    public Maybe<Employee> loginEmployee(String email, String password) {
        return Maybe.create(emitter ->
                db.collection(Constant.EMPLOYEE)
                        .whereEqualTo("email", email)
                        .whereEqualTo("pass", password)
                        .get()
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                QuerySnapshot querySnapshot = task.getResult();
                                if (querySnapshot != null && !querySnapshot.isEmpty()) {
                                    for (QueryDocumentSnapshot document : querySnapshot) {
                                        Employee employee = document.toObject(Employee.class);
                                        if (employee != null) {
                                            emitter.onSuccess(employee);
                                            return; // Exit once a match is found
                                        }
                                    }
                                } else {
                                    // No matching document found
                                    emitter.onComplete();
                                }
                            } else {
                                emitter.onError(new Exception("Authentication failed"));
                            }
                        })
        );
    }

    @Override
    public Single<Boolean> registerAccount(String email, String pass, String repass) {
        return Single.create(emitter -> {
            mAuth.createUserWithEmailAndPassword(email, pass)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                FirebaseUser user = mAuth.getCurrentUser();
                                if (user != null) {
                                    user.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                emitter.onSuccess(true);
                                            } else {
                                                emitter.onError(task.getException());
                                            }
                                        }
                                    });
                                } else {
                                    emitter.onError(new Exception("User is null"));
                                }
                            } else {
                                emitter.onError(task.getException());
                            }
                        }
                    });
        });
    }

    public Single<Boolean> loginResident(String email, String password) {
        return Single.create(emitter -> {
            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();
                            if (user != null) {
                                if (user.isEmailVerified()) {
                                    emitter.onSuccess(true);
                                } else {
                                    emitter.onError(new Exception("Email is not verified"));
                                }
                            } else {
                                emitter.onError(new Exception("User is null"));
                            }
                        } else {
                            if (task.getException() instanceof FirebaseAuthInvalidUserException || task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                emitter.onError(new Exception("Invalid email or password"));
                            } else {
                                emitter.onError(task.getException());
                            }
                        }
                    });
        });
    }

    @Override
    public Single<Resident> checkAccontResident(String email) {
        return Single.create(emitter -> {
            db.collection(Constant.RESIDENT)
                    .whereEqualTo("email", email)
                    .limit(1) // To get only one document
                    .get()
                    .addOnSuccessListener(queryDocumentSnapshots -> {
                        if (!queryDocumentSnapshots.isEmpty()) {
                            Resident resident = queryDocumentSnapshots.getDocuments().get(0).toObject(Resident.class);
                            emitter.onSuccess(resident);
                        } else {
                            emitter.onError(new NoSuchElementException("No employee found with the given email and isAuthen = true"));
                        }
                    })
                    .addOnFailureListener(emitter::onError);
        });
    }

    @Override
    public Single<List<Building>> getBuilding() {
        return Single.create(emitter -> {
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
                    });
        });
    }
    @Override
    public Maybe<List<Apartment>> getlistApartment(String idBuilding) {
        return Maybe.create(emitter ->
                db.collection(Constant.APARTMENT).whereEqualTo("idBuilding",idBuilding)
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
    public Completable addResidentall(Resident resident, DetailsResident detailsResident) {
        Completable addResident = addResident(resident);
        Completable addDetailsResident = addDetailsResident(detailsResident);

        return Completable.mergeArray(addResident, addDetailsResident);
    }



    @Override
    public Completable updatePhone(String phone, String id) {
        return Completable.create(emitter -> {
            db.collection(Constant.ADMIN).document(id).update("phoneNumber",phone)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            emitter.onComplete();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            emitter.onError(e);
                        }
                    });
        });
    }

    @Override
    public Completable updatePhoneEmployee(String phone, String id) {
        return Completable.create(emitter -> {
            db.collection(Constant.EMPLOYEE).document(id).update("phoneNumber",phone)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            emitter.onComplete();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            emitter.onError(e);
                        }
                    });
        });
    }

    @Override
    public Completable updatePhoneResident(String phone, String id) {
        return Completable.create(emitter -> {
            db.collection(Constant.RESIDENT).document(id).update("phoneNumber",phone)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            emitter.onComplete();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            emitter.onError(e);
                        }
                    });
        });
    }

    @Override
    public Completable updateSex(int sex, String id) {
        return Completable.create(emitter -> {
            db.collection(Constant.ADMIN).document(id).update("sex",sex)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            emitter.onComplete();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            emitter.onError(e);
                        }
                    });
        });
    }

    @Override
    public Completable updateSexEmployee(int sex, String id) {
        return Completable.create(emitter -> {
            db.collection(Constant.EMPLOYEE).document(id).update("sex",sex)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            emitter.onComplete();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            emitter.onError(e);
                        }
                    });
        });
    }

    @Override
    public Completable updateSexResident(int sex, String id) {
        return Completable.create(emitter -> {
            db.collection(Constant.RESIDENT).document(id).update("sex",sex)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            emitter.onComplete();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            emitter.onError(e);
                        }
                    });
        });
    }

    @Override
    public Completable updateName(String name, String id) {
        return Completable.create(emitter -> {
            db.collection(Constant.ADMIN).document(id).update("fullName",name)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            emitter.onComplete();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            emitter.onError(e);
                        }
                    });
        });
    }

    @Override
    public Completable updateNameEmployee(String name, String id) {
        return Completable.create(emitter -> {
            db.collection(Constant.EMPLOYEE).document(id).update("fullName",name)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            emitter.onComplete();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            emitter.onError(e);
                        }
                    });
        });
    }

    @Override
    public Completable updateNameResident(String name, String id) {
        return Completable.create(emitter -> {
            db.collection(Constant.RESIDENT).document(id).update("fullName",name)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            emitter.onComplete();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            emitter.onError(e);
                        }
                    });
        });
    }

    @Override
    public Completable updateBirth(Date date, String id) {
        return Completable.create(emitter -> {
            db.collection(Constant.ADMIN).document(id).update("birth",date)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            emitter.onComplete();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            emitter.onError(e);
                        }
                    });
        });
    }

    @Override
    public Completable updateBirthEmployee(Date date, String id) {
        return Completable.create(emitter -> {
            db.collection(Constant.EMPLOYEE).document(id).update("birth",date)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            emitter.onComplete();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            emitter.onError(e);
                        }
                    });
        });
    }

    @Override
    public Completable updateBirthResident(Date date, String id) {
        return Completable.create(emitter -> {
            db.collection(Constant.RESIDENT).document(id).update("birth",date)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            emitter.onComplete();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            emitter.onError(e);
                        }
                    });
        });
    }

    @Override
    public Completable updatePassResident(String pass) {
        return Completable.create(emitter -> {
            FirebaseUser user = mAuth.getCurrentUser();
            user.updatePassword(pass)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                    emitter.onComplete();
                            }
                            else {
                                emitter.onError(task.getException());
                            }
                        }
                    });
        });
    }

    @Override
    public Completable updatePassEmployee(String pass, String idUser) {
        return Completable.create(emitter -> {
            db.collection(Constant.EMPLOYEE).document(idUser).update("pass",pass)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            emitter.onComplete();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            emitter.onError(e);
                        }
                    });
        });
    }

    @Override
    public Completable updatePassAdmin(String pass, String idUser) {
        return Completable.create(emitter -> {
            db.collection(Constant.ADMIN).document(idUser).update("pass",pass)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            emitter.onComplete();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            emitter.onError(e);
                        }
                    });
        });
    }

    @Override
    public Single<Employee> getInforAdmin(String id) {
        return Single.create(emitter -> {
            db.collection(Constant.ADMIN).document(id)
                    .get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                            Employee employee = documentSnapshot.toObject(Employee.class);
                            emitter.onSuccess(employee);
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
    public Single<Employee> getInforEmployee(String id) {
        return Single.create(emitter -> {
            db.collection(Constant.EMPLOYEE).document(id)
                    .get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                            Employee employee = documentSnapshot.toObject(Employee.class);
                            emitter.onSuccess(employee);
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
    public Single<Resident> getInforResident(String id) {
        return Single.create(emitter -> {
            db.collection(Constant.RESIDENT).document(id)
                    .get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                            Resident employee = documentSnapshot.toObject(Resident.class);
                            emitter.onSuccess(employee);
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
    public Completable forgotEmail(String email) {
        return Completable.create(emitter -> {
            mAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
                        emitter.onComplete();
                    }
                    else
                        emitter.onError(task.getException());
                }
            });
        });
    }



    public Completable addResident(Resident resident) {
        return Completable.create(emitter -> {
            resident.setCreationTime(Timestamp.now().toDate());
            resident.setLastModificationTime(Timestamp.now().toDate());
            db.collection(Constant.RESIDENT).document(resident.getId()).set(resident).addOnSuccessListener(new OnSuccessListener<Void>() {
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
    public Completable addDetailsResident(DetailsResident detailsresident) {
        return Completable.create(emitter -> {
            detailsresident.setCreationTime(Timestamp.now().toDate());
            detailsresident.setLastModificationTime(Timestamp.now().toDate());
            db.collection(Constant.DETAILSRESIDENT).document(detailsresident.getId()).set(detailsresident).addOnSuccessListener(new OnSuccessListener<Void>() {
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
