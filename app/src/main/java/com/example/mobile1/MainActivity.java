package com.example.mobile1;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Context;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import android.content.Intent;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import androidx.core.content.ContextCompat;
import androidx.core.app.ActivityCompat;

import java.util.List;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private static List<ScanResult> scanResultList = new ArrayList <ScanResult>();
    private ListView wifiList;
    private WifiManager wifiManager;
    private final int MY_PERMISSIONS_ACCESS_COARSE_LOCATION = 1;
    WifiReceiver receiverWifi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        wifiList = findViewById(R.id.wifiList);
        Button buttonScan = findViewById(R.id.scanBtn);
        wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
//turning on a WIFI service if it's off
        if (!wifiManager.isWifiEnabled()) {
            Toast.makeText(getApplicationContext(), "Turning WiFi ON...", Toast.LENGTH_LONG).show();
            wifiManager.setWifiEnabled(true);
        }
        buttonScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //access_coarse_location get :Allows an app to access approximate location.
                if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, MY_PERMISSIONS_ACCESS_COARSE_LOCATION);
                } else {
                    Toast.makeText(MainActivity.this, "scanning", Toast.LENGTH_SHORT).show();
                    wifiManager.startScan();
                    if (scanResultList.size() != 0 ){
                        scanResultList.clear();
                        List<ScanResult> newWifiList = wifiManager.getScanResults();
                        for (ScanResult it : newWifiList){
                            if (it.SSID.length() != 0)
                                scanResultList.add(it);
                        }
                        //scanResultList.addAll(wifiManager.getScanResults());
                    }
                    else {
                        List<ScanResult> newWifiList = wifiManager.getScanResults();
                        for (ScanResult it : newWifiList){
                            if (it.SSID.length() != 0)
                                scanResultList.add(it);
                        }
                        //scanResultList.addAll(wifiManager.getScanResults());
                    }
                }
            }
        });

        wifiList.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Intent list_itemIntent = new Intent(view.getContext(), list_item.class);
                if (scanResultList.size() == 0 ){
                    //scanResultList.addAll(receiverWifi.listWifiScanResult);
                    scanResultList.addAll(wifiManager.getScanResults());
                }
//                System.out.println("--------------");
                //System.out.println(position);
                //System.out.println(receiverWifi.listWifiScanResult.size());
                ScanResult itemScanResult = scanResultList.get(position);//receiverWifi.listWifiScanResult.get(position);
                String item_SSID = itemScanResult.SSID;
                String item_BSSID = itemScanResult.BSSID;
                String item_capabilities = itemScanResult.capabilities;
                int item_signalLevel = itemScanResult.level;
                int item_frequency = itemScanResult.frequency;

                list_itemIntent.putExtra("SSID", item_SSID);
                list_itemIntent.putExtra("BSSID", item_BSSID);
                list_itemIntent.putExtra("capabilities", item_capabilities);
                list_itemIntent.putExtra("level", item_signalLevel);
                list_itemIntent.putExtra("frequency", item_frequency);
//*/
                startActivity(list_itemIntent);
            }

        });
    }

    /*@Override
    protected void onResume() {
        super.onResume();
        Toast.makeText(MainActivity.this, "ON RESUME", Toast.LENGTH_SHORT).show();
    };//*/
    @Override
    protected void onPostResume() {
        super.onPostResume();
        receiverWifi = new WifiReceiver(wifiManager, wifiList);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION);
        registerReceiver(receiverWifi, intentFilter);
        //Toast.makeText(MainActivity.this, "ON POST RESUME", Toast.LENGTH_SHORT).show();
        getWifi();
    }
    private void getWifi() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            //Toast.makeText(MainActivity.this, "version> = marshmallow", Toast.LENGTH_SHORT).show();
            if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                //Toast.makeText(MainActivity.this, "location turned off", Toast.LENGTH_SHORT).show();
                ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                        MY_PERMISSIONS_ACCESS_COARSE_LOCATION);
            } else {
                //Toast.makeText(MainActivity.this, "location turned on", Toast.LENGTH_SHORT).show();
                wifiManager.startScan();
                if (scanResultList.size() != 0 ){
                    scanResultList.clear();
                    //scanResultList.addAll(wifiManager.getScanResults());
                    List<ScanResult> newWifiList = wifiManager.getScanResults();
                    for (ScanResult it : newWifiList){
                        if (it.SSID.length() != 0)
                            scanResultList.add(it);
                    }
                }
                else {
                    //scanResultList.addAll(wifiManager.getScanResults());
                    List<ScanResult> newWifiList = wifiManager.getScanResults();
                    for (ScanResult it : newWifiList){
                        if (it.SSID.length() != 0)
                            scanResultList.add(it);
                    }
                }
            }
        } else {
            //Toast.makeText(MainActivity.this, "scanning", Toast.LENGTH_SHORT).show();
            wifiManager.startScan();
            if (scanResultList.size() != 0 ){
                scanResultList.clear();
                //scanResultList.addAll(wifiManager.getScanResults());
                List<ScanResult> newWifiList = wifiManager.getScanResults();
                for (ScanResult it : newWifiList){
                    if (it.SSID.length() != 0)
                        scanResultList.add(it);
                }
            }
            else {
                //scanResultList.addAll(wifiManager.getScanResults());
                List<ScanResult> newWifiList = wifiManager.getScanResults();
                for (ScanResult it : newWifiList){
                    if (it.SSID.length() != 0)
                        scanResultList.add(it);
                }
            }
        }
    }
    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(receiverWifi);
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case MY_PERMISSIONS_ACCESS_COARSE_LOCATION:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(MainActivity.this, "permission granted", Toast.LENGTH_SHORT).show();
                wifiManager.startScan();
                    if (scanResultList.size() != 0 ){
                        scanResultList.clear();
                        //scanResultList.addAll(wifiManager.getScanResults());
                        List<ScanResult> newWifiList = wifiManager.getScanResults();
                        for (ScanResult it : newWifiList){
                            if (it.SSID.length() != 0)
                                scanResultList.add(it);
                        }
                    }
                    else {
                        //scanResultList.addAll(wifiManager.getScanResults());
                        List<ScanResult> newWifiList = wifiManager.getScanResults();
                        for (ScanResult it : newWifiList){
                            if (it.SSID.length() != 0)
                                scanResultList.add(it);
                        }
                    }
            } else {
                Toast.makeText(MainActivity.this, "permission not granted", Toast.LENGTH_SHORT).show();
                return;
            }
            break;
        }
    }
}