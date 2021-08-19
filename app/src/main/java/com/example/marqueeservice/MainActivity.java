package com.example.marqueeservice;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;
import android.widget.Button;

/**
 * @author leon
 */
public class MainActivity extends AppCompatActivity {
    private LedStatusService ledStatusService;
    Boolean flag = false;
    ServiceConnection connection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        connection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                ledStatusService = (LedStatusService) service;
                flag = true;
            }
            @Override
            public void onServiceDisconnected(ComponentName name) {
            }
        };
        bindService(new Intent(MainActivity.this, LedService.class), connection, Context.BIND_AUTO_CREATE);
    }
    @Override
    protected void onResume() {

//        Button button = findViewById(R.id.button);
//
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
        if (flag){
            try {
                ledStatusService.changeLedStatus(new LedStatus(1, false));
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
//            }
//        });
        Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
        if (flag){
            try {
                ledStatusService.changeLedStatus(new LedStatus(1, false));
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
            }
        });
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        unbindService(connection);
    }
}