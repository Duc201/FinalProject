package com.example.urban_area_manager.feature.Admin.Home.Sensor.Respository;

import androidx.annotation.NonNull;

import com.example.urban_area_manager.feature.Admin.Home.Sensor.Model.Sensor;
import com.example.urban_area_manager.utils.Constant;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;

public class SensorRespositoryImp implements SensorRespository{
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageRef = storage.getReference();
    private List<ListenerRegistration> listenerRegistrations = new ArrayList<>();

    @Override
    public Single<Sensor> queryDeviceBySerial(String serial) {
        return Single.create(emitter ->
                db.collection(Constant.DEVICE_REALTIME)
                        .whereEqualTo("serial", serial)
                        .get()
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                QuerySnapshot querySnapshot = task.getResult();
                                if (querySnapshot != null && !querySnapshot.isEmpty()) {
                                    for (QueryDocumentSnapshot doc : querySnapshot) {
                                        Sensor sensor = doc.toObject(Sensor.class);
                                        emitter.onSuccess(sensor);
                                        return; // Exit the loop and method once a sensor is found
                                    }
                                } else {
                                    emitter.onError(new NoSuchElementException("Không tìm thấy cảm biến"));
                                }
                            } else {
                                emitter.onError(task.getException());
                            }
                        })
        );
    }

    @Override
    public Completable addSensorUrban(Sensor sensor) {
        return Completable.create(emitter -> {
            db.collection(Constant.SENSOR).document(sensor.getId()).set(sensor).addOnSuccessListener(new OnSuccessListener<Void>() {
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
    public Completable updateSensorUrban(Sensor sensor) {
        return Completable.create(emitter -> {
            db.collection(Constant.SENSOR).document(sensor.getId()).set(sensor).addOnSuccessListener(new OnSuccessListener<Void>() {
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
    public Completable delteSensorUrban(String  id) {
        return Completable.create(emitter -> {
            db.collection(Constant.SENSOR).document(id).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
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
    public Single<List<Sensor>> getListSensorUser() {
        return Single.create(emitter ->
                db.collection(Constant.SENSOR)
                        .get()
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                QuerySnapshot querySnapshot = task.getResult();
                                if (querySnapshot != null && !querySnapshot.isEmpty()) {
                                    List<Sensor> sensorList = new ArrayList<>();
                                    for (QueryDocumentSnapshot doc : querySnapshot) {
                                        Sensor sensor = doc.toObject(Sensor.class);
                                        sensorList.add(sensor);
                                    }
                                    emitter.onSuccess(sensorList);
                                } else {
                                    emitter.onError(new NoSuchElementException("Không tìm thấy cảm biến"));
                                }
                            } else {
                                emitter.onError(task.getException());
                            }
                        })
        );
    }

    @Override
    public Observable<Sensor> getSensorRealtime(String serial ) {
        return Observable.create(emitter -> {
            db.collection(Constant.DEVICE_REALTIME)
                    .whereEqualTo("serial", serial)
                    .addSnapshotListener((querySnapshot, e) -> {
                        if (e != null) {
                            emitter.onError(e);
                            return;
                        }

                        if (querySnapshot != null && !querySnapshot.isEmpty()) {
                            for (QueryDocumentSnapshot doc : querySnapshot) {
                                Sensor sensor = doc.toObject(Sensor.class);
                                emitter.onNext(sensor);
                            }
                        }
                    });
        });
    }


    @Override
    public Single<List<Sensor>> getListHistoricSensor(Date date , String serial) {
        return Single.create(emitter -> {
            // Tạo đối tượng Calendar để xác định thời gian bắt đầu và kết thúc của ngày
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);

            // Thiết lập thời gian bắt đầu của ngày (0h00)
            calendar.set(Calendar.HOUR_OF_DAY, 0);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.MILLISECOND, 0);
            Date startDate = calendar.getTime();

            // Thiết lập thời gian kết thúc của ngày (23h59)
            calendar.set(Calendar.HOUR_OF_DAY, 23);
            calendar.set(Calendar.MINUTE, 59);
            calendar.set(Calendar.SECOND, 59);
            calendar.set(Calendar.MILLISECOND, 999);
            Date endDate = calendar.getTime();

            // Thực hiện truy vấn Firestore
            db.collection(Constant.DEVICE_SENSOR).document(serial).collection(serial)
                    .whereGreaterThanOrEqualTo("time", startDate)
                    .whereLessThanOrEqualTo("time", endDate)
                    .get()
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            List<Sensor> sensors = new ArrayList<>();
                            for (QueryDocumentSnapshot doc : task.getResult()) {
                                Sensor sensor = doc.toObject(Sensor.class);
                                sensors.add(sensor);
                            }
                            if (!sensors.isEmpty()) {
                                emitter.onSuccess(sensors);
                            } else {
                                emitter.onSuccess(null);
//                                emitter.onError(new NoSuchElementException("No sensors found for the given date range"));
                            }
                        } else {
                            emitter.onError(task.getException());
                        }
                    });
        });
    }

    @Override
    public Observable<Sensor> observeHumidTemperature(List<String> serials) {
        return Observable.create(emitter -> {
            for (String serial : serials) {
                 db.collection(Constant.DEVICE_REALTIME)
                        .whereEqualTo("serial", serial)
                        .addSnapshotListener((querySnapshot, e) -> {
                            if (e != null) {
                                emitter.onError(e);
                                return;
                            }

                            if (querySnapshot != null && !querySnapshot.isEmpty()) {
                                for (QueryDocumentSnapshot doc : querySnapshot) {
                                    Sensor sensor = doc.toObject(Sensor.class);
                                    emitter.onNext(sensor);
                                }
                            }
                        });
//                listenerRegistrations.add(listenerRegistration);
            }
        });
    }

//    public void removeListeners() {
//        for (ListenerRegistration listenerRegistration : listenerRegistrations) {
//            listenerRegistration.remove();
//        }
//        listenerRegistrations.clear();
//    }


}
