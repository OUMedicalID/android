package edu.oaklandstudent.medicalid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.validator.routines.EmailValidator;

import com.google.android.material.snackbar.Snackbar;
import com.ybs.passwordstrengthmeter.PasswordStrength;

import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static edu.oaklandstudent.medicalid.AESEncryption.MEDICAL_ID_DOMAIN;
import static edu.oaklandstudent.medicalid.AESEncryption.getSHA512;

public class Registration extends AppCompatActivity implements TextWatcher {

    private String email;
    private String password;
    private LinearLayout layoutList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration);

        final View setView = getLayoutInflater().inflate(R.layout.registrationpasswordbar,null,false);
        layoutList = findViewById(R.id.passwordBarLayout);
        final ScrollView scrollview = findViewById(R.id.scrollPassword);

        final EditText emailText = findViewById(R.id.email);
        final EditText passwordText = findViewById(R.id.password);
        Button registerInfo = findViewById(R.id.registerButton);

        passwordText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                view = setView;
                if(hasFocus){

                    addView(view,passwordText);

                    // Introduce a timer that will scroll down again after .5 seconds.
                    // Perhaps the view is being created too quickly before it could actually scroll?
                    final Handler handler = new Handler(Looper.getMainLooper());
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            scrollview.fullScroll(ScrollView.FOCUS_DOWN);
                        }
                    }, 1000);

                } else{
                    removeView(view);
                }
            }
        });

        registerInfo.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                String registerEmail = emailText.getText().toString();
                String registerPassword = passwordText.getText().toString();

                emailText.clearFocus();
                passwordText.clearFocus();

                hideKeyboard();
                if(Password_Validation(registerPassword) && Email_Validation(registerEmail)){
                    email = registerEmail;
                    password = registerPassword;

                    Snackbar.make(findViewById(android.R.id.content), "The email and password have been saved!", Snackbar.LENGTH_SHORT).show();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Intent myIntent = new Intent(Registration.this.getApplicationContext(), Login.class);
                            startActivity(myIntent);
                            finish();
                        }
                    }, 500);
                }else {
                    if(!Password_Validation(registerPassword) && !Email_Validation(registerEmail)){
                        Snackbar.make(findViewById(android.R.id.content), "The password and email are not valid!", Snackbar.LENGTH_SHORT).show();
                    }
                    else if(!Password_Validation(registerPassword)){
                        Snackbar.make(findViewById(android.R.id.content), "The password is not valid!", Snackbar.LENGTH_SHORT).show();
                    }
                    else
                        Snackbar.make(findViewById(android.R.id.content), "The email is not valid!", Snackbar.LENGTH_SHORT).show();
                }

                Log.v("Main","The saved email was: " + getEmail());
                Log.v("Main","The saved password was: " + getPassword());

                if(getPassword() == null)return;

                String sha512Password = getSHA512(getPassword()).substring(0, 32); // Generate a SHA512 of password


/*
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    //handle
                }
*/


                // Store the sha512.
                SharedPreferences prefs = getSharedPreferences("edu.oaklandstudent.medicalid", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = prefs.edit();
                editor.putString("sha512Key",sha512Password);
                editor.apply();
                editor.commit();


                final String encryptedEmail = AESEncryption.encryptEmail(getEmail(), sha512Password);



                Thread thread = new Thread(new Runnable(){
                    @Override
                    public void run() {
                        try {
                            final OkHttpClient client = new OkHttpClient();
                            RequestBody formBody = new FormBody.Builder()
                                    .add("email", encryptedEmail)
                                    //.add("password", "test")
                                    .build();

                            Request request = new Request.Builder()
                                    .url("https://medicalidou.com/register.php")
                                    .post(formBody)
                                    .build();

                             Call call = client.newCall(request);
                             Response response = call.execute();
                        } catch (Exception e) {
                            ///Log.e(TAG, e.getMessage());
                        }
                    }
                });
                thread.start();



            }
        });
    }
    public String getEmail(){
        return email;
    }
    public String getPassword(){
        return password;
    }

    @Override
    public void afterTextChanged(Editable s) {
    }
    @Override
    public void beforeTextChanged(
            CharSequence s, int start, int count, int after) {
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        updatePasswordStrengthView(s.toString());
    }

    private void updatePasswordStrengthView(String password) {

        ProgressBar progressBar = findViewById(R.id.progressBar);
        TextView strengthView = findViewById(R.id.password_strength);
        if (TextView.VISIBLE != strengthView.getVisibility())
            return;

        if (password.isEmpty()) {
            strengthView.setText("");
            progressBar.setProgress(0);
            return;
        }

        PasswordStrength str = PasswordStrength.calculateStrength(password);
        strengthView.setText(str.getText(this));
        strengthView.setTextColor(str.getColor());

        progressBar.getProgressDrawable().setColorFilter(str.getColor(), android.graphics.PorterDuff.Mode.SRC_IN);
        if (str.getText(this).equals("Weak")) {
            progressBar.setProgress(25);
        } else if (str.getText(this).equals("Medium")) {
            progressBar.setProgress(50);
        } else if (str.getText(this).equals("Strong")) {
            progressBar.setProgress(75);
        } else {
            progressBar.setProgress(100);
        }
    }

    private void addView(View view, EditText password) {
        password.addTextChangedListener(this);
        layoutList.addView(view);

    }

    private void removeView(View view){

        layoutList.removeView(view);

    }

    private void hideKeyboard(){
        try {
            InputMethodManager imm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    private static boolean Password_Validation(String password)
    {

        if(password.length() > 8)
        {
            Pattern letter = Pattern.compile("[a-zA-z]");
            Pattern digit = Pattern.compile("[0-9]");
            Pattern special = Pattern.compile ("[!@#$%&*()_+=|<>?{}\\[\\]~-]");


            Matcher hasLetter = letter.matcher(password);
            Matcher hasDigit = digit.matcher(password);
            Matcher hasSpecial = special.matcher(password);

            return hasLetter.find() && hasDigit.find() && hasSpecial.find();

        }
        else
            return false;

    }

    private static boolean Email_Validation(String email){
        boolean valid = EmailValidator.getInstance().isValid(email);
        boolean allowLocal = true;
        boolean localValid = EmailValidator.getInstance(allowLocal).isValid(email);

        if(valid && localValid){
            return true;
        }
        else return false;
    }

    @Override
    public void onBackPressed() {
        // Do Here what ever you want do on back press;
    }
}

