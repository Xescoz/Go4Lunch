package com.example.go4lunch.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.go4lunch.model.Workmate;
import com.example.go4lunch.repository.WorkmateRepository;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.List;

public class WorkmateViewModel extends ViewModel {
    private final WorkmateRepository workmateRepository;
    private MutableLiveData<Workmate> mutableLiveDataWorkmate;
    private MutableLiveData<List<Workmate>> mutableLiveDataListWorkmate;
    public MutableLiveData<Boolean> mutableLiveDataCurrentRestaurantIsUpdated = new MutableLiveData<>();
    public MutableLiveData<Boolean> mutableLiveDataLikesIsUpdated = new MutableLiveData<>();

    public WorkmateViewModel() {
        workmateRepository = new WorkmateRepository();
    }

    public WorkmateViewModel(WorkmateRepository workmateRepository){
        this.workmateRepository = workmateRepository;
    }

    public LiveData<Workmate> getCurrentUserFromDB(String uid){
        mutableLiveDataWorkmate = workmateRepository.getCurrentUserFromDB(uid);

        return mutableLiveDataWorkmate;
    }

    public void writeNewUser(Workmate workmate,String uid){
        workmateRepository.writeNewUser(workmate,uid);
    }

    public void updateUserDB(String pictureUrl){
        workmateRepository.updateUserDB(pictureUrl);
    }

    public void updateLikes(List<String> likes){
        workmateRepository.updateLikes(likes)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        mutableLiveDataLikesIsUpdated.setValue(true);
                    }
                });
    }

    public void updateNotification(Boolean notification){
        workmateRepository.updateNotification(notification);
    }

    public void updateCurrentRestaurant(String restaurantId, String restaurantName){
        workmateRepository.updateCurrentRestaurant(restaurantId, restaurantName)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        mutableLiveDataCurrentRestaurantIsUpdated.setValue(true);
                    }
                });
    }
    public MutableLiveData<List<Workmate>> getAllUserFromDB(Boolean isWorkmateList){
        mutableLiveDataListWorkmate = workmateRepository.getAllUserFromDB(isWorkmateList);

        return mutableLiveDataListWorkmate;
    }
}
