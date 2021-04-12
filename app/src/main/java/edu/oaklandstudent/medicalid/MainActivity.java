package edu.oaklandstudent.medicalid;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.nfc.FormatException;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.Tag;
import android.nfc.TagLostException;
import android.nfc.tech.Ndef;
import android.os.Build;
import android.os.Bundle;
import android.nfc.NfcAdapter;
import android.os.Vibrator;
import android.text.InputType;
import android.util.Log;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.an.biometric.BiometricCallback;
import com.an.biometric.BiometricManager;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.thejuki.kformmaster.model.FormPasswordEditTextElement;

import java.io.IOException;
import java.util.Map;

import edu.oaklandstudent.medicalid.NFC.DataStoreUtil;
import edu.oaklandstudent.medicalid.NFC.NFCDialog;

import edu.oaklandstudent.medicalid.R;

public class MainActivity extends AppCompatActivity implements NfcAdapter.ReaderCallback {
    private NfcAdapter mNfcAdapter;

    FormPasswordEditTextElement x = new FormPasswordEditTextElement();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SharedPreferences prefs = getSharedPreferences("edu.oaklandstudent.medicalid", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        final String isLoggedIn = prefs.getString("isLoggedIn", "false");

        new Thread(new Runnable() {
            public void run() {
                if (isLoggedIn.equals("false")) {
                    Intent myIntent = new Intent(MainActivity.this.getApplicationContext(), Login.class);
                    startActivity(myIntent);
                    // Print out all records.
                }
            }
        }).start();




        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mNfcAdapter = NfcAdapter.getDefaultAdapter(this);

        String key = prefs.getString("sha512Key", "");
        String myPassword = AESEncryption.decrypt(prefs.getString("password", null), key);
        String bioAuth = prefs.getString("bioAuth", "false");

        if (myPassword != null && !myPassword.equals("")) {
            enableBottomBar(false); // Prevent an exploit where users can move during the split second alertdialog is gone.
            promptPassword();
        }

        if (bioAuth.equals("true")) {
            bioAuthentication();
        }





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
        } else {
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


    public void promptPassword() {
        enableBottomBar(false);
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
                String key = prefs.getString("sha512Key", "");
                String m_Text = input.getText().toString();
                String pass = AESEncryption.decrypt(prefs.getString("password", null), key);


                if (m_Text.equals(pass)) {
                    enableBottomBar(true);
                    dialog.cancel();
                } else {
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
        Log.v("Main", "password length: " + pass.getPassword());

        builder.show();
    }


    public void bioAuthentication() {
        new BiometricManager.BiometricBuilder(MainActivity.this)
                .setTitle("Authentication")
                .setSubtitle("Please authenticate yourself.")
                .setDescription("Biometric authentication is turned on which means that a valid fingerprint or equivalent biometric is required.\n\nIf you do not have a valid biometric, you will need to clear all app data.")
                .setNegativeButtonText("Cancel")
                .build()
                .authenticate(new BiometricCallback() {
                    @Override
                    public void onSdkVersionNotSupported() {
                        Log.wtf("AUTH", "onSdkVersionNotSupported()");
                    }

                    @Override
                    public void onBiometricAuthenticationNotSupported() {
                        Log.wtf("AUTH", "onBiometricAuthenticationNotSupported()");
                    }

                    @Override
                    public void onBiometricAuthenticationNotAvailable() {
                        Log.wtf("AUTH", "onBiometricAuthenticationNotAvailable()");
                    }

                    @Override
                    public void onBiometricAuthenticationPermissionNotGranted() {
                        Log.wtf("AUTH", "onBiometricAuthenticationPermissionNotGranted()");
                    }

                    @Override
                    public void onBiometricAuthenticationInternalError(String error) {
                        Log.wtf("AUTH", "onBiometricAuthenticationInternalError()");
                    }

                    @Override
                    public void onAuthenticationSuccessful() {
                        Log.wtf("AUTH", "onAuthenticationSuccessful()");
                    }

                    @Override
                    public void onAuthenticationHelp(int helpCode, CharSequence helpString) {
                        Log.wtf("AUTH", " onAuthenticationHelp()");
                    }

                    @Override
                    public void onAuthenticationFailed() {
                        Log.wtf("AUTH", "onAuthenticationFailed()");
                    }

                    @Override
                    public void onAuthenticationCancelled() {
                        Intent myIntent = new Intent(MainActivity.this.getApplicationContext(), authFailed.class);
                        startActivity(myIntent);
                    }

                    @Override
                    public void onAuthenticationError(int errorCode, CharSequence errString) {
                        Intent myIntent = new Intent(MainActivity.this.getApplicationContext(), authFailed.class);
                        startActivity(myIntent);
                    }

                });
    }

    private void enableBottomBar(boolean enable) {

        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        for (int i = 0; i < bottomNav.getMenu().size(); i++) {
            bottomNav.getMenu().getItem(i).setEnabled(enable);
        }
    }


    @Override
    protected void onResume() {
        super.onResume();

        if (mNfcAdapter != null) {
            Bundle options = new Bundle();
            // Work around for some broken Nfc firmware implementations that poll the card too fast
            options.putInt(NfcAdapter.EXTRA_READER_PRESENCE_CHECK_DELAY, 250);

            // Enable ReaderMode for all types of card and disable platform sounds
            mNfcAdapter.enableReaderMode(this,
                    this,
                    NfcAdapter.FLAG_READER_NFC_A |
                            NfcAdapter.FLAG_READER_NFC_B |
                            NfcAdapter.FLAG_READER_NFC_F |
                            NfcAdapter.FLAG_READER_NFC_V |
                            NfcAdapter.FLAG_READER_NFC_BARCODE |
                            NfcAdapter.FLAG_READER_NO_PLATFORM_SOUNDS,
                    options);
        }

    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mNfcAdapter != null)
            mNfcAdapter.disableReaderMode(this);
    }

    // This method is run in another thread when a card is discovered
    // !!!! This method cannot cannot direct interact with the UI Thread
    // Use `runOnUiThread` method to change the UI from this method
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void onTagDiscovered(Tag tag) {

        // Read and or write to Tag here to the appropriate Tag Technology type class
        // in this example the card should be an Ndef Technology Type
        Ndef mNdef = Ndef.get(tag);

        // Check that it is an Ndef capable card
        if (mNdef != null) {

            // If we want to read
            // As we did not turn on the NfcAdapter.FLAG_READER_SKIP_NDEF_CHECK
            // We can get the cached Ndef message the system read for us.

            NdefMessage mNdefMessage = mNdef.getCachedNdefMessage();


            // Or if we want to write a Ndef message

            // Create a Ndef Record

            SharedPreferences prefs = getSharedPreferences("edu.oaklandstudent.medicalid", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();

            String msg = "[[" + prefs.getString("email", "") + ":" + prefs.getString("sha512Key", "").substring(0, 32) + "]]";

            NdefRecord mRecord = NdefRecord.createTextRecord("en", msg);

            // Add to a NdefMessage
            NdefMessage mMsg = new NdefMessage(mRecord);

            // Catch errors
            try {
                mNdef.connect();
                mNdef.writeNdefMessage(mMsg);

          /*      // Success if got to here
                runOnUiThread(() -> {
                    Toast.makeText(getApplicationContext(),
                            "Write to NFC Success",
                            Toast.LENGTH_SHORT).show();
                });*/

                Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                v.vibrate(400);

                // Make a Sound
                try {
                    Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                    Ringtone r = RingtoneManager.getRingtone(getApplicationContext(),
                            notification);
                    r.play();
                } catch (Exception e) {
                    // Some error playing sound
                }

            } catch (FormatException e) {
                // if the NDEF Message to write is malformed
            } catch (TagLostException e) {
                // Tag went out of range before operations were complete
            } catch (IOException e) {
                // if there is an I/O failure, or the operation is cancelled
            } finally {
                // Be nice and try and close the tag to
                // Disable I/O operations to the tag from this TagTechnology object, and release resources.
                try {
                    mNdef.close();
                } catch (IOException e) {
                    // if there is an I/O failure, or the operation is cancelled
                }
            }

        }


    }

}


