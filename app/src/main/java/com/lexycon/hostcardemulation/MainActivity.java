package com.lexycon.hostcardemulation;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.nfc.NfcAdapter;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DataStoreUtil dataStore = new DataStoreUtil(this);
        String uid = dataStore.getID();
        TextView tv1 = (TextView)findViewById(R.id.textID);
        tv1.setText(uid);


        NfcAdapter nfcAdapter = NfcAdapter.getDefaultAdapter(this);
        if (nfcAdapter != null) {
            if (!nfcAdapter.isEnabled()) {
                new NFCDialog(this).showNFCDisabled();
           }
        } else{
                new NFCDialog(this).showNFCUnsupported();
        }


    }
}