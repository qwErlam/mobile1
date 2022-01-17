package com.example.mobile1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;
import android.content.Intent;

public class list_item extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_item);

        Intent intent = getIntent();
        String item_SSID = intent.getStringExtra("SSID");
        String item_BSSID = intent.getStringExtra("BSSID");
        String item_capabilities = intent.getStringExtra("capabilities");
        int item_int_level = intent.getIntExtra("level",0);
        String item_level = Integer.toString(item_int_level);
        String strLevel="No signal";
        if(item_int_level>-50)
        {
            strLevel="Excellent";
        } else if ((item_int_level>-60))
        {
            strLevel="Good";
        } else if((item_int_level>-70)){
            strLevel="Fair";
        } else if(item_int_level==-70)
        {
            strLevel="Weak";
        };
        item_level = strLevel+" ("+item_level+")";

        int item_int_frequency = intent.getIntExtra("frequency", 0);
        String item_frequency = Integer.toString(item_int_frequency);

        TextView tvSSID, tvBSSID, tvCapabilities, tvLevel, tvFrequency;
        tvSSID = (TextView)findViewById(R.id.ssid);
        tvBSSID = (TextView)findViewById(R.id.bssid);
        tvCapabilities = (TextView)findViewById(R.id.capabilities);
        tvLevel = (TextView)findViewById(R.id.level);
        tvFrequency = (TextView)findViewById(R.id.frequency);

        tvSSID.setText(item_SSID);
        tvBSSID.setText(item_BSSID);
        tvCapabilities.setText(item_capabilities);
        tvLevel.setText(item_level);
        tvFrequency.setText(item_frequency);//*/
    }
}