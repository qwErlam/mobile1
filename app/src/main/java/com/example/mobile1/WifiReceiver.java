package com.example.mobile1;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;

/*public class WifiReceiver {
}*/

class WifiReceiver extends BroadcastReceiver {
    WifiManager wifiManager;
    ListView wifiDeviceList;
    List<ScanResult> listWifiScanResult;
    public WifiReceiver(WifiManager wifiManager, ListView wifiDeviceList) {
        this.wifiManager = wifiManager;
        this.wifiDeviceList = wifiDeviceList;
    }
    public void onReceive(Context context, Intent intent) {
        System.out.println("----start scan");
        String action = intent.getAction();
        if (WifiManager.SCAN_RESULTS_AVAILABLE_ACTION.equals(action)) {
            List<ScanResult> newWifiList = wifiManager.getScanResults();
            List<ScanResult> wifiList = new ArrayList<ScanResult>();
            for (ScanResult it : newWifiList){
                if (it.SSID.length() != 0)
                    wifiList.add(it);
            }
            listWifiScanResult = wifiList;
            ArrayList<String> deviceList = new ArrayList<>();
            for (ScanResult scanResult : wifiList) {
                deviceList.add(scanResult.SSID );//+ " - " + scanResult.capabilities);
            }
            ArrayAdapter arrayAdapter = new ArrayAdapter(context, android.R.layout.simple_list_item_1, deviceList.toArray());
            wifiDeviceList.setAdapter(arrayAdapter);
        }
        System.out.println("end scan----");
    }

}