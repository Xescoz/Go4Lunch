package com.example.go4lunch.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.lifecycle.ViewModelProvider;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.CompoundButton;

import com.example.go4lunch.R;
import com.example.go4lunch.databinding.ActivityMainBinding;
import com.example.go4lunch.databinding.ActivitySettingsBinding;
import com.example.go4lunch.model.Workmate;
import com.example.go4lunch.viewmodel.WorkmateViewModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Calendar;

public class SettingsActivity extends AppCompatActivity {

    private ActivitySettingsBinding binding;
    private WorkmateViewModel workmateViewModel;
    private Workmate workmate;
    private AlarmManager alarmManager;
    private PendingIntent pendingIntent;
    private FirebaseUser user;
    private static final int HOUR_OF_DAY = 12;
    private static final int MINUTE = 0;
    private static final int TIME_MILLIS = 5000;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySettingsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        workmateViewModel = new ViewModelProvider(this).get(WorkmateViewModel.class);
        user = FirebaseAuth.getInstance().getCurrentUser();

        initDB();

        initListener();

    }

    /** Init the button listener */
    private void initListener(){
        binding.notificationSwitchButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                workmateViewModel.updateNotification(isChecked);
                if(isChecked) {
                    if (workmate != null && workmate.getCurrentRestaurant() != null){
                        showWorkmatesEatingNotification();
                    }
                    else{
                        binding.notificationSwitchButton.setChecked(false);
                    }
                }
                else{
                    scheduleNotification(getNotification(""));
                    alarmManager.cancel(pendingIntent);
                }
            }
        });

        binding.backButtonSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    /** Init workmate from DB */
    private void initDB(){
        workmateViewModel.getCurrentUserFromDB(user.getUid()).observe(this, workmate -> {
            this.workmate = workmate;
            if(workmate.getNotification())
                binding.notificationSwitchButton.setChecked(true);
        });
    }

    /** Show notification for the current workmate and search if other workmate will eat here */
    public void showWorkmatesEatingNotification(){

        workmateViewModel.getAllUserFromDB(true).observe(this, workmateList -> {
            StringBuilder workmateEating = new StringBuilder("will eat here.");
            for (int i = 0; i<workmateList.size();i++){
                if(workmate.getCurrentRestaurant().equals(workmateList.get(i).getCurrentRestaurant()) && !workmate.getName().equals(workmateList.get(i).getName())){
                    workmateEating.insert(0, workmateList.get(i).getName() + " ,");
                }
            }
            scheduleNotification(getNotification(workmateEating.toString()));
        });
    }

    /** Schedule the notification for a certain time */
    private void scheduleNotification(Notification notification) {

        Intent notificationIntent = new Intent(this, NotificationPublisher.class);
        notificationIntent.putExtra(NotificationPublisher.NOTIFICATION_ID, 1);
        notificationIntent.putExtra(NotificationPublisher.NOTIFICATION, notification);
        pendingIntent = PendingIntent.getBroadcast(this, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);


        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, HOUR_OF_DAY);
        calendar.set(Calendar.MINUTE, MINUTE);

        long futureInMillis = SystemClock.elapsedRealtime() + TIME_MILLIS;

        alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        //alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
        alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, futureInMillis, pendingIntent);
    }

    /** Build the notification */
    private Notification getNotification(String workmateEatingHere) {
        if(workmateEatingHere.equals("will eat here."))
            workmateEatingHere = getString(R.string.no_one_eating);
        else
            workmateEatingHere = workmateEatingHere.replace(",will eat here.",getString(R.string.will_eat_here));

        String name = getString(R.string.channel_name);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, name)
                .setSmallIcon(R.drawable.notification_icon)
                .setContentTitle("Go4Lunch")
                .setContentText(workmateEatingHere)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        return builder.build();
    }
}