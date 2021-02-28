package edu.oaklandstudent.medicalid;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.google.android.material.snackbar.Snackbar;
import com.ybs.passwordstrengthmeter.PasswordStrength;

public class Registration extends AppCompatActivity implements TextWatcher {

    private String username;
    private String password;
    private LinearLayout layoutList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration);

        final View setView = getLayoutInflater().inflate(R.layout.registrationpasswordbar,null,false);
        layoutList = findViewById(R.id.passwordBarLayout);
        final ScrollView scrollview = findViewById(R.id.scrollPassword);

        final EditText usernameText = findViewById(R.id.username);
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

                username = usernameText.getText().toString();
                password = passwordText.getText().toString();

                usernameText.clearFocus();
                passwordText.clearFocus();

                hideKeyboard();
                if(Password_Validation(password)){
                    Snackbar.make(findViewById(android.R.id.content), "The passwords is saved!", Snackbar.LENGTH_SHORT).show();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Intent myIntent = new Intent(Registration.this.getApplicationContext(), Login.class);
                            startActivity(myIntent);
                            finish();
                        }
                    }, 500);
                }else {
                    Snackbar.make(findViewById(android.R.id.content), "The password is not strong enough!", Snackbar.LENGTH_SHORT).show();
                }

                Log.v("Main","The saved username was: " + getUsername());
                Log.v("Main","The saved password was: " + getPassword());

            }
        });
    }
    public String getUsername(){
        return username;
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
}

