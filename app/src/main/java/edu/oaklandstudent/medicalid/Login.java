package edu.oaklandstudent.medicalid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class Login extends AppCompatActivity{

    private String email;
    private String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        final EditText emailText = (findViewById(R.id.email));
        final EditText passwordText = findViewById(R.id.password);
        Button loginButton = findViewById(R.id.loginButton);
        TextView createAccount = findViewById(R.id.createAccount);

        loginButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                email = emailText.getText().toString();
                password = passwordText.getText().toString();

                Log.v("Main","The saved email was: " + getEmail());
                Log.v("Main","The saved password was: " + getPassword());

                finish();
            }
        });
        createAccount.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Log.v("Main","The create textview was clicked");

                Intent myIntent = new Intent(Login.this.getApplicationContext(), Registration.class);
                startActivity(myIntent);
                finish();
            }
        });
    }
    public String getEmail(){
        return email;
    }
    public String getPassword(){
        return password;
    }
}

