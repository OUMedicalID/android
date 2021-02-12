package edu.oaklandstudent.medicalid;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.nfc.NfcAdapter;
import android.text.InputType;
import android.util.Log;
import android.view.MenuItem;
import android.widget.EditText;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.thejuki.kformmaster.model.FormPasswordEditTextElement;

import java.util.Map;

import edu.oaklandstudent.medicalid.NFC.DataStoreUtil;
import edu.oaklandstudent.medicalid.NFC.NFCDialog;

import edu.oaklandstudent.medicalid.R;

public class MainActivity extends AppCompatActivity {

    FormPasswordEditTextElement x = new FormPasswordEditTextElement();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        SharedPreferences prefs = getSharedPreferences("edu.oaklandstudent.medicalid", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        String myPassword =  AESEncryption.decrypt(prefs.getString("password", null));


        Log.wtf("MAINDEBUG", "Pass: "+myPassword);

        if(myPassword != null && !myPassword.equals("")){
            promptPassword();
        }


        /*
        // Use this to check all items saved are encrypted.
        Map<String, ?> allEntries = prefs.getAll();
        for (Map.Entry<String, ?> entry : allEntries.entrySet()) {
            Log.wtf("MAINDEBUG", entry.getKey() + ": " + entry.getValue().toString());
        }
        */






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



    public void promptPassword(){
        final password pass = new password();
        x.setValue("");
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Password");
        builder.setCancelable(false);

        // Set up the input
        final EditText input = new EditText(this);
        // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
        input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        builder.setView(input);

        // Set up the buttons
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                SharedPreferences prefs = getSharedPreferences("edu.oaklandstudent.medicalid", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = prefs.edit();
                String m_Text = input.getText().toString();
                String pass = AESEncryption.decrypt(prefs.getString("password", null));


                if(m_Text.equals(pass)){
                    dialog.cancel();
                }else{
                    promptPassword();
                }


            }
        });

//            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface dialog, int which) {
//
//                }
//            });
        Log.v("Main","password length: " + pass.getPassword());

        builder.show();
    }


}



