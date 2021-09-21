package com.example.go4lunch.repository;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.go4lunch.model.Workmate;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class WorkmateRepository {

    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private final MutableLiveData<Workmate> mutableLiveDataWorkmate = new MutableLiveData<>();
    private final MutableLiveData<List<Workmate>> mutableLiveDataListWorkmate = new MutableLiveData<>();
    private final List<Workmate> workmateList = new ArrayList<>();
    private final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private final DocumentReference docRefCurrentUser = db.collection("workmates").document(user.getUid());

    public void writeNewUser(Workmate workmate, String uid) {
        db.collection("workmates").document(uid).set(workmate);
    }

    public MutableLiveData<Workmate> getCurrentUserFromDB(String uid){
        db.collection("workmates").document(uid).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                mutableLiveDataWorkmate.setValue(documentSnapshot.toObject(Workmate.class));
            }
        });
        return mutableLiveDataWorkmate;
    }

    public MutableLiveData<List<Workmate>> getAllUserFromDB(Boolean isWorkmateList){
        db.collection("workmates")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                if (!isWorkmateList || !document.getId().equals(Objects.requireNonNull(user).getUid()))
                                    workmateList.add(document.toObject(Workmate.class));
                                mutableLiveDataListWorkmate.setValue(workmateList);
                            }
                        }
                    }
                });
        return mutableLiveDataListWorkmate;
    }

    public void updateUserDB(String pictureUrl){
        docRefCurrentUser.update("name",user.getDisplayName(),"picture", pictureUrl);
    }

    public Task<Void> updateCurrentRestaurant(String restaurantId, String restaurantName){
        return docRefCurrentUser.update("currentRestaurant",restaurantId,"currentRestaurantName",restaurantName);
    }

    public Task<Void> updateLikes(List<String> likes){
        return docRefCurrentUser.update("likes",likes);
    }

    public void updateNotification(Boolean notification){
        docRefCurrentUser.update("notification",notification);
    }
}
