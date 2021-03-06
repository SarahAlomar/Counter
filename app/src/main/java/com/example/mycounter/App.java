package com.example.mycounter;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;

public class App extends Application {

    public static final String CHANNEL_1_ID = "channel1";
    public static final String CHANNEL_2_ID = "channel2";

    @Override
    public void onCreate(){
        super.onCreate();
        createNotificationChannels();
    }
    public void createNotificationChannels(){

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel channel1 = new NotificationChannel(
                    CHANNEL_1_ID,
                    "My Channel 1",
                    NotificationManager.IMPORTANCE_HIGH
            );
            channel1.setDescription("This is Channel 1");
            channel1.setShowBadge(true);

            NotificationChannel channel2 = new NotificationChannel(
                    CHANNEL_2_ID,
                    "My Channel 2",
                    NotificationManager.IMPORTANCE_LOW
            );
            channel2.setDescription("This is Channel 2");

            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel1);
            manager.createNotificationChannel(channel2);
        }
    }
}
