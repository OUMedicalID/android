package edu.oaklandstudent.medicalid;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static edu.oaklandstudent.medicalid.AESEncryption.MEDICAL_ID_DOMAIN;

public class ExportData {



    public static void getSavedPrefsData(Context context){
        SharedPreferences prefs = context.getSharedPreferences("edu.oaklandstudent.medicalid", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        final OkHttpClient client = new OkHttpClient();


        final HashMap<String, String> params = new HashMap<>();
        Map<String, ?> allEntries = prefs.getAll();
        for (Map.Entry<String, ?> entry : allEntries.entrySet()) {
            params.put( entry.getKey(), entry.getValue().toString() );
        }


        Thread thread = new Thread(new Runnable(){
            @Override
            public void run() {
                try {
                    FormBody.Builder builder = new FormBody.Builder();
                    for ( Map.Entry<String, String> entry : params.entrySet() ) {
                        //(entry.getKey().equals("isLoggedIn") || entry.getKey().equals("sha512Key") )continue;
                        if(entry.getKey().startsWith("MID_") == false && !entry.getKey().equals("email")){continue;}
                        builder.add( entry.getKey(), entry.getValue() );
                    }

                    RequestBody formBody = builder.build();

                    Request request = new Request.Builder()
                            .url( "https://medicalidou.com/receiveData.php" )
                            .post( formBody )
                            .build();

                    Call call = client.newCall(request);
                    try {
                        Response response = call.execute();

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } catch (Exception e) {
                    ///Log.e(TAG, e.getMessage());
                }
            }
        });
        thread.start();












    }







}
