package edu.oaklandstudent.medicalid;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

import com.ybs.passwordstrengthmeter.PasswordStrength;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class password extends AppCompatActivity implements TextWatcher{

    private String passwordText;
    private String confirmPasswordText;
    private LinearLayout layoutList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.password);

        final View setView = getLayoutInflater().inflate(R.layout.passwordbar,null,false);
        layoutList = findViewById(R.id.passwordBarLayout);

        final SharedPreferences prefs = getSharedPreferences("edu.oaklandstudent.medicalid", Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = prefs.edit();
        final String key = prefs.getString("sha512Key", "");

        final EditText password = findViewById(R.id.passwordEditText);
        passwordText = AESEncryption.decrypt(prefs.getString("password",null), key);
        password.setText(getPassword());
        TextView passwordLabel = findViewById(R.id.passwordLabel);
        passwordLabel.setText("Password");

        final EditText confirmPassword = findViewById(R.id.confirmPasswordEditText);
        TextView confirmPasswordLabel = findViewById(R.id.confirmPasswordLabel);
        confirmPasswordLabel.setText("Confirm Password");

        Button saveButton = findViewById(R.id.savePassword);
        saveButton.setText("Save Password");
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                passwordText = password.getText().toString();
                confirmPasswordText = confirmPassword.getText().toString();

                hideKeyboard();
                if(passwordText.equals(confirmPasswordText)){
                    if(Password_Validation(passwordText)){
                        editor.putString("bioAuth", null); // When we enable password auth, disable biometrics.
                        savePassword(AESEncryption.encrypt(passwordText, key),"Password Set!");
                    }else {
                        Snackbar.make(findViewById(android.R.id.content), "The password is not strong enough!", Snackbar.LENGTH_SHORT).show();
                    }
                }else {
                    Snackbar.make(findViewById(android.R.id.content), "The passwords do not match!", Snackbar.LENGTH_SHORT).show();
                }
            }
        });

        Button clearButton = findViewById(R.id.clearPassword);
        clearButton.setText("Clear Password");
        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideKeyboard();
                savePassword(null,"Password Cleared!");
            }
        });

        password.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                view = setView;
                if(hasFocus){
                    addView(view,password);
                } else{
                    removeView(view);
                }
            }
        });

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

        ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressBar);
        TextView strengthView = (TextView) findViewById(R.id.password_strength);
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

    public String getPassword(){
        return passwordText;
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

    private void refreshPassword(){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                finish();
                startActivity(getIntent());
            }
        }, 1000);
    }

    private void savePassword(@Nullable String thisPassword, String snackResponse){
        SharedPreferences prefs = getSharedPreferences("edu.oaklandstudent.medicalid", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("password", thisPassword);
        editor.commit();
        editor.apply();
        Snackbar.make(findViewById(android.R.id.content), snackResponse, Snackbar.LENGTH_SHORT).show();
        refreshPassword();
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
}