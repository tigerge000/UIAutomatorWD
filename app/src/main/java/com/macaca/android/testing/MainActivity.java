package com.macaca.android.testing;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Color;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Looper;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        Button btnFinish = (Button) findViewById(R.id.start_uiautomator);
        btnFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        Button btnIdentify = (Button) findViewById(R.id.stop_uiautomator);
        btnIdentify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


        WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        int ip = wifiManager.getConnectionInfo().getIpAddress();
        String ipStr = (ip & 0xFF) + "." + ((ip >> 8) & 0xFF) + "." + ((ip >> 16) & 0xFF) + "." + ((ip >> 24) & 0xFF);
        TextView textViewIP = (TextView) findViewById(R.id.ip_address);
        textViewIP.setText("IP地址:" + ipStr);
        textViewIP.setTextColor(Color.WHITE);

        TextView textViewAtx = (TextView) findViewById(R.id.ip_port);
        textViewAtx.setText("端口:");
        textViewAtx.setTextColor(Color.WHITE);


    }

//    @Override
//    protected void onRestart() {
//        super.onRestart();
//    }
//
//    @Override
//    public void onBackPressed() {
//        moveTaskToBack(true);
//    }
//
//
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//    }
}
