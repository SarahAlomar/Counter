package com.example.mycounter;


import static java.lang.Integer.parseInt;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import android.app.AlertDialog;
import android.app.Notification;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import static com.example.mycounter.App.CHANNEL_1_ID;
import static com.example.mycounter.App.CHANNEL_2_ID;


public class MainActivity extends AppCompatActivity {
    private NotificationManagerCompat notificationManger;

    TextView text;
    Button pll, mis, re;
    EditText editMax, editMin, editTitleMAX, editTitleMIN;
    int count;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        text = (TextView) findViewById(R.id.textView);
        pll = (Button) findViewById(R.id.plus);
        pll.setOnClickListener(ClickListener);
        mis = (Button) findViewById(R.id.mines);
        mis.setOnClickListener(ClickListener);
        re = (Button) findViewById(R.id.reset);
        re.setOnClickListener(ClickListener);


        //////notification variables
        editMax = (EditText) findViewById(R.id.mx);
        editMin = (EditText) findViewById(R.id.mn);
        editTitleMAX = (EditText) findViewById(R.id.notificationMAX);
        editTitleMIN = (EditText) findViewById(R.id.notificationMIN);
        notificationManger = NotificationManagerCompat.from(this);


        if (savedInstanceState != null) {
            count = savedInstanceState.getInt("count");
            text.setText(count + "");
        } else {
            //////to set the counter to 0 on creating the app
            set();
        }


    }


    private View.OnClickListener ClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            switch (view.getId()) {

                case R.id.plus:
                    plus(view);
                    break;

                case R.id.mines:
                    minus(view);
                    break;

                case R.id.reset:

                    new AlertDialog.Builder(MainActivity.this)
                            .setTitle("Reste counter")
                            .setMessage("Are you sure you want to reset counter?")
                            .setPositiveButton("Reset", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    set();
                                }
                            })
                            .setNegativeButton("Cancel", null)
                            .show();
                    break;

            }


        }
    };

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("count", count);
    }


    public void plus(View view) {
        count++;
        text.setText(count + "");
        if (count == parseInt(editMax.getText().toString())){
            sendOnChannel1(view);}
        if (count == parseInt(editMin.getText().toString())) {
            sendOnChannel2(view);
        }
    }

    public void minus(View view) {
        count--;
        text.setText(count + "");
        if (count == parseInt(editMin.getText().toString())) {
            sendOnChannel2(view);
        }
        if (count == parseInt(editMax.getText().toString())){
            sendOnChannel1(view);}
    }

    public void set() {
        count = 0;
        text.setText(count + "");
    }


    public void sendOnChannel1(View v){
        String title = editTitleMAX.getText().toString();
        Notification notification = new NotificationCompat.Builder(this, CHANNEL_1_ID)
                .setSmallIcon(R.drawable.ic_baseline_notifications_active_24)
                .setContentTitle(title)
                .setContentText("you reached " + count)
                .setDefaults(Notification.DEFAULT_ALL)
                .setPriority(Notification.PRIORITY_HIGH)
                .setCategory(Notification.CATEGORY_MESSAGE)
                .build();
        notificationManger.notify(1,notification );
    }

    public void sendOnChannel2(View v){
        String title = editTitleMIN.getText().toString();
        Notification notification = new NotificationCompat.Builder(this, CHANNEL_2_ID)
                .setSmallIcon(R.drawable.ic_baseline_notification_important_24)
                .setContentTitle(title)
                .setContentText("you reached " + count)
                .setPriority(Notification.PRIORITY_LOW)
                .build();
        notificationManger.notify(2,notification );
    }


}
