package edu.oaklandstudent.medicalid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static edu.oaklandstudent.medicalid.AESEncryption.getSHA512;


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

                String sha512Password = getSHA512(getPassword()).substring(0, 32); // Generate a SHA512 of password
                final String encryptedEmail = AESEncryption.encrypt(getEmail(), sha512Password);

                SharedPreferences prefs = getSharedPreferences("edu.oaklandstudent.medicalid", Context.MODE_PRIVATE);
                final SharedPreferences.Editor editor = prefs.edit();
                editor.putString("sha512Key",sha512Password);
                editor.putString("isLoggedIn","true");
                editor.putString("email",encryptedEmail);
                editor.apply();
                editor.commit();



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
                                    .url("https://med.mathew.me/login.php")
                                    .post(formBody)
                                    .build();

                            Call call = client.newCall(request);
                            Response response = call.execute();
                            String body = response.body().string();


                            Map<String, String> map = new Gson().fromJson(body, new TypeToken<HashMap<String, String>>() {}.getType());

                            if(map.containsKey("error")){
                                Snackbar.make(findViewById(android.R.id.content), "Invalid Login", Snackbar.LENGTH_SHORT).show();
                                return;
                            }

                            for (Map.Entry<String, String> entry : map.entrySet() ) {
                                //Log.wtf("RESPONDER", entry.getKey()+":"+entry.getValue());
                                if(!entry.getKey().startsWith("MID_"))continue;
                                editor.putString(entry.getKey(),entry.getValue());
                            }
                            editor.commit();
                            finish();


                        } catch (Exception e) {
                            ///Log.e(TAG, e.getMessage());
                        }
                    }
                });
                thread.start();







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

