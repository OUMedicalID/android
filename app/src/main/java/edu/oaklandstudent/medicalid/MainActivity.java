package edu.oaklandstudent.medicalid;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.nfc.NfcAdapter;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import edu.oaklandstudent.medicalid.NFC.DataStoreUtil;
import edu.oaklandstudent.medicalid.NFC.NFCDialog;

import edu.oaklandstudent.medicalid.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DataStoreUtil dataStore = new DataStoreUtil(this);
        String uid = dataStore.getID();
        //TextView tv1 = (TextView)findViewById(R.id.textID);
        //tv1.setText(uid);

        // Begin nav stuff
        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);
        //I added this if statement to keep the selected fragment when rotating the device

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new Home()).commit();
        }

        // End Nav Stuff




        NfcAdapter nfcAdapter = NfcAdapter.getDefaultAdapter(this);
        if (nfcAdapter != null) {
            if (!nfcAdapter.isEnabled()) {
                new NFCDialog(this).showNFCDisabled();
           }
        } else{
                new NFCDialog(this).showNFCUnsupported();
        }



    }
    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment = null;
                    switch (item.getItemId()) {
                        case R.id.nav_home:
                            selectedFragment = new Home();
                            break;
                        case R.id.nav_tab2:
                            selectedFragment = new Questions();
                            break;
                        case R.id.nav_tab3:
                            selectedFragment = new Settings();
                            break;
                    }
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            selectedFragment).commit();
                    return true;
                }
            };
}



