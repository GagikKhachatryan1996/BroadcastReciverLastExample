package com.aca.broadcastreciverlastexample;

import androidx.appcompat.app.AppCompatActivity;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.wifi.WifiManager;
import android.os.BatteryManager;
import android.os.Bundle;
import android.provider.SyncStateContract;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private TextView text;
    private TextView textWifi;
    private TextView textBluetooth;
    private TextView textCharging;
    private TextView batareylevell;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        text = findViewById(R.id.textt);
        textWifi = findViewById(R.id.textt2);
        textBluetooth = findViewById(R.id.textt3);
        textCharging = findViewById(R.id.textt4);
        batareylevell=findViewById(R.id.textt5);



    }


    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            if (ConnectivityManager.CONNECTIVITY_ACTION.equals(intent.getAction())) {

                boolean noConnected = intent.getBooleanExtra(ConnectivityManager.EXTRA_NO_CONNECTIVITY, false);

                if (noConnected) {

                    text.setText("Off");
                } else {
                    text.setText("On");
                }


            }

            int wifiStateExtra = intent.getIntExtra(WifiManager.EXTRA_WIFI_STATE, WifiManager.WIFI_STATE_UNKNOWN);

            switch (wifiStateExtra) {

                case WifiManager.WIFI_STATE_ENABLED:
                    textWifi.setText("On");
                    break;
                case WifiManager.WIFI_STATE_DISABLED:
                    textWifi.setText("Off");
                    break;

            }

            final String action = intent.getAction();

            if (action.equals(BluetoothAdapter.ACTION_STATE_CHANGED)) {
                final int state = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, BluetoothAdapter.ERROR);
                switch (state) {
                    case BluetoothAdapter.STATE_OFF:

                        textBluetooth.setText("Off");

                        break;

                    case BluetoothAdapter.STATE_ON:
                        textBluetooth.setText("On");

                        break;

                }

            }



        }
    };


    private BroadcastReceiver broadcastReceiver2=new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            if (intent.getAction().equals(Intent.ACTION_POWER_CONNECTED)) {
                textCharging.setText("true");
            } else {
                intent.getAction().equals(Intent.ACTION_POWER_DISCONNECTED);
                textCharging.setText("false");

            }




            }





    };




    @Override
    protected void onStart() {
        super.onStart();

        IntentFilter intentFilter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        intentFilter.addAction(WifiManager.WIFI_STATE_CHANGED_ACTION);
        intentFilter.addAction(BluetoothAdapter.ACTION_STATE_CHANGED);

        IntentFilter ifilter = new IntentFilter();
        ifilter.addAction(Intent.ACTION_POWER_CONNECTED);
        ifilter.addAction(Intent.ACTION_POWER_DISCONNECTED);



        registerReceiver(broadcastReceiver, intentFilter);
          registerReceiver(broadcastReceiver2, ifilter);




    }


    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(broadcastReceiver);
           unregisterReceiver(broadcastReceiver2);



    }


}
