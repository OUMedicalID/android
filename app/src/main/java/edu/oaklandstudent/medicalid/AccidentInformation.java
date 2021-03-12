package edu.oaklandstudent.medicalid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.google.android.material.snackbar.Snackbar;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class AccidentInformation extends AppCompatActivity implements View.OnClickListener{

    LinearLayout layoutList;
    Button buttonAdd;
    Button buttonSubmitList;
    ArrayList<String> accidentList = new ArrayList<String>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.accident_information);

        layoutList = findViewById(R.id.layout_list);
        buttonAdd = findViewById(R.id.button_add);
        buttonSubmitList = findViewById(R.id.button_submit_list);

        buttonAdd.setOnClickListener(this);
        buttonSubmitList.setOnClickListener(this);

        restore();
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.button_add:

                addView();

                break;

            case R.id.button_submit_list:

                if(checkIfValidAndRead()){
                    SharedPreferences prefs = getSharedPreferences("edu.oaklandstudent.medicalid", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = prefs.edit();
                    String key = prefs.getString("sha512Key", "");
                    HashMap<String,String> myMap = new HashMap<>();
                    for(int j = 0;j < accidentList.size();j++) {
                        myMap.put(Integer.toString(j),AESEncryption.encrypt(accidentList.get(j), key));
                    }
                    saveMap(myMap);
                    editor.commit();
                    Snackbar.make(findViewById(android.R.id.content), "Information Saved!", Snackbar.LENGTH_SHORT).show();
                }

                break;

        }


    }

    private boolean checkIfValidAndRead() {

        accidentList.clear();
        boolean result = true;

        for(int i=0;i<layoutList.getChildCount();i++){

            View conditionsView = layoutList.getChildAt(i);

            EditText editTextName = (EditText)conditionsView.findViewById(R.id.row_add);

            if(!editTextName.getText().toString().equals("")){
                accidentList.add(editTextName.getText().toString());
            }else {
                result = false;
                break;
            }

        }

        if(accidentList.size()==0){
            SharedPreferences prefs = getSharedPreferences("edu.oaklandstudent.medicalid", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();
            editor.remove("MID_Injuries");
            editor.apply();
            result = false;
            Snackbar.make(findViewById(android.R.id.content), "Add Condition First!", Snackbar.LENGTH_SHORT).show();
        }else if(!result){
            Snackbar.make(findViewById(android.R.id.content), "Enter All Details Correctly!", Snackbar.LENGTH_SHORT).show();
        }

        return result;
    }

    private void addView() {

        final View conditionsView = getLayoutInflater().inflate(R.layout.row_add,null,false);
        EditText editText = (EditText)conditionsView.findViewById(R.id.row_add);
        ImageView imageClose = (ImageView)conditionsView.findViewById(R.id.image_remove);

        imageClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeView(conditionsView);
            }
        });

        layoutList.addView(conditionsView);

    }

    private void removeView(View view){

        layoutList.removeView(view);

    }
    public void restore(){

        SharedPreferences prefs = getSharedPreferences("edu.oaklandstudent.medicalid", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        String key = prefs.getString("sha512Key", "");
        Map<String,String> restoreMap = new HashMap<>();
        restoreMap = loadMap();

        if(restoreMap == null) return;
        else{
            for(int i = 0;i < restoreMap.size();i++) {
                final View conditionsView = getLayoutInflater().inflate(R.layout.row_add, null, false);

                EditText editText = (EditText) conditionsView.findViewById(R.id.row_add);
                editText.setText(AESEncryption.decrypt(restoreMap.get(Integer.toString(i)), key));
                ImageView imageClose = (ImageView) conditionsView.findViewById(R.id.image_remove);

                imageClose.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        removeView(conditionsView);
                    }
                });

                layoutList.addView(conditionsView);
            }
        }
    }
    private void saveMap(Map<String,String> inputMap){

        SharedPreferences pSharedPref = getApplicationContext().getSharedPreferences("edu.oaklandstudent.medicalid", Context.MODE_PRIVATE);

        if (pSharedPref != null){
            JSONObject jsonObject = new JSONObject(inputMap);
            String jsonString = jsonObject.toString();
            SharedPreferences.Editor editor = pSharedPref.edit();
            editor.remove("MID_Injuries").commit();
            editor.putString("MID_Injuries", jsonString);
            editor.commit();
        }
    }

    private Map<String,String> loadMap(){

        Map<String,String> outputMap = new HashMap<String,String>();
        SharedPreferences pSharedPref = getApplicationContext().getSharedPreferences("edu.oaklandstudent.medicalid", Context.MODE_PRIVATE);

        try{
            if (pSharedPref != null){
                String jsonString = pSharedPref.getString("MID_Injuries", (new JSONObject()).toString());
                JSONObject jsonObject = new JSONObject(jsonString);
                Iterator<String> keysItr = jsonObject.keys();
                while(keysItr.hasNext()) {
                    String key = keysItr.next();
                    String value = (String) jsonObject.get(key);
                    outputMap.put(key, value);
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return outputMap;
    }
}
